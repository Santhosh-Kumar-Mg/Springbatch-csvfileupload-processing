package com.springbatch.csvfileprocessing.configuration;

import java.util.Arrays;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.springbatch.csvfileprocessing.model.CsvModel;

/**
 * This configuration class configures the Spring Batch job that
 * is used to demonstrate that our item reader reads the correct
 * information from the CSV file.
 */
@Configuration
public class SpringBatchJobConfig {
	
	@Autowired
    private JobBuilderFactory jobBuilderFactory;
     
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    @StepScope
    public FlatFileItemReader<CsvModel> reader(@Value("#{jobParameters['fullPathFileName']}") String pathToFile) {
        return new FlatFileItemReaderBuilder<CsvModel>()
                .name("CsvModelReader")
                .resource(new FileSystemResource(pathToFile))
                .delimited()
                .names(new String[]{"anzsic06", "area", "year", "geo_count", "ec_count"})
                .lineMapper(lineMapper())
                .fieldSetMapper(new BeanWrapperFieldSetMapper<CsvModel>() {{
                    setTargetType(CsvModel.class);
                }})
                .build();
    }

    @Bean
    public LineMapper<CsvModel> lineMapper() {

        final DefaultLineMapper<CsvModel> defaultLineMapper = new DefaultLineMapper<>();
        final DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[] {"anzsic06", "area", "year", "geo_count", "ec_count"});

        final CsvFieldSetMapper fieldSetMapper = new CsvFieldSetMapper();
        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }
    
    @Bean
    public CsvProcessor processor() {
        return new CsvProcessor();
    }
    
    @Bean
    public FlatFileItemWriter<CsvModel> textWriter() {
      BeanWrapperFieldExtractor<CsvModel> fieldExtractor = new BeanWrapperFieldExtractor<>();
      fieldExtractor.setNames(new String[] { "anzsic06", "area", "year", "geo_count", "ec_count" });
      fieldExtractor.afterPropertiesSet();

      DelimitedLineAggregator<CsvModel> lineAggregator = new DelimitedLineAggregator<>();
      lineAggregator.setDelimiter(",");
      lineAggregator.setFieldExtractor(fieldExtractor);

      FlatFileItemWriter<CsvModel> flatFileItemWriter = new FlatFileItemWriter<>();
      flatFileItemWriter.setName("CsvModelWriter");
      flatFileItemWriter.setResource(new FileSystemResource("src/main/resources/geographic.txt"));
      flatFileItemWriter.setLineAggregator(lineAggregator);

      return flatFileItemWriter;
    }
    
    @Bean
    public ItemWriter<CsvModel> xmlWriter(){
    	Resource exportFileResource = new FileSystemResource("src/main/resources/geographic.xml");
    	
    	Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    	
    	marshaller.setClassesToBeBound(CsvModel.class);
    	
    	return new StaxEventItemWriterBuilder<CsvModel>()
    			.name("CsvModelWriter")
    			.marshaller(marshaller)
    			.resource(exportFileResource)
    			.rootTagName("GeographicUnit")
    			.build();

    }
    
    @Bean
    public FlatFileItemWriter<CsvModel> excelWriter() 
    {

        FlatFileItemWriter<CsvModel> writer = new FlatFileItemWriter<>();
        
        Resource exportFileResource = new FileSystemResource("src/main/resources/geographic.csv");
         
        writer.setResource(exportFileResource);

        writer.setLineAggregator(new DelimitedLineAggregator<CsvModel>() {
            {
                setFieldExtractor(new BeanWrapperFieldExtractor<CsvModel>() {
                    {
                        setNames(new String[] { "anzsic06", "area", "year", "geo_count", "ec_count" });
                    }
                });
            }
        });
        return writer;
    }
    
    @SuppressWarnings("unchecked")
	public CompositeItemWriter<CsvModel> compositeItemWriter(){
        @SuppressWarnings("rawtypes")
		CompositeItemWriter writer = new CompositeItemWriter();
        writer.setDelegates(Arrays.asList(textWriter(),xmlWriter(),excelWriter()));
        return writer;
    }

    @Bean
    public Job readCSVFilesJob(FlatFileItemReader<CsvModel> reader) {
        return jobBuilderFactory
                .get("readCSVFilesJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1(reader))
                .end()
                .build();
    }
 
    @Bean
    public Step step1(FlatFileItemReader<CsvModel> reader) {
        return stepBuilderFactory.get("step1").<CsvModel, CsvModel>chunk(100)
                .reader(reader)
                .processor(processor())
                .writer(compositeItemWriter())
                .build();
    }
}
