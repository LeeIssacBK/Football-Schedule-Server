package com.fs.configs.jpa.converters;

import jakarta.persistence.Converter;

import java.util.List;

@Converter(autoApply = true)
public class ListAttributeConverter extends ObjectAttributeConverter<List> {

    @Override
    protected Class<List> getInstance() {
        return List.class;
    }

}
