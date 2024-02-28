package com.fs.configs.jpa.converters;

import jakarta.persistence.Converter;

import java.util.Map;

@Converter(autoApply = true)
public class MapAttributeConverter extends ObjectAttributeConverter<Map> {

    @Override
    protected Class<Map> getInstance() {
        return Map.class;
    }

}
