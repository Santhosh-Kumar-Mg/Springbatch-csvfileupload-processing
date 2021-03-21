package com.springbatch.csvfileprocessing.configuration;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

import com.springbatch.csvfileprocessing.model.CsvModel;

@Component
public class CsvFieldSetMapper implements FieldSetMapper<CsvModel> {

    @Override
    public CsvModel mapFieldSet(FieldSet fieldSet) {
        final CsvModel model = new CsvModel();

        model.setAnzsic06(fieldSet.readString("anzsic06"));
        model.setArea(fieldSet.readString("area"));
        model.setYear(fieldSet.readString("year"));
        model.setGeo_count(fieldSet.readString("geo_count"));
        model.setEc_count(fieldSet.readString("ec_count"));
        return model;

    }
}
