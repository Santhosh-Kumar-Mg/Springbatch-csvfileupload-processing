package com.springbatch.csvfileprocessing.configuration;

import org.springframework.batch.item.ItemProcessor;

import com.springbatch.csvfileprocessing.model.CsvModel;

public class CsvProcessor implements ItemProcessor<CsvModel, CsvModel>{

    @Override
    public CsvModel process(final CsvModel model) {
        final String anzsic06 = model.getAnzsic06();
        final String area = model.getArea();
        final String year = model.getYear();
        final String geo_count = model.getGeo_count();
        final String ec_count = model.getEc_count();

        final CsvModel csvmodel = new CsvModel();
        csvmodel.setAnzsic06(anzsic06);
        csvmodel.setArea(area);
        csvmodel.setYear(year);
        csvmodel.setGeo_count(geo_count);
        csvmodel.setEc_count(ec_count);
        return csvmodel;
    }
}
