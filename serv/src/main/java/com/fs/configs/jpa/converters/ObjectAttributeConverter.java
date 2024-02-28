package com.fs.configs.jpa.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

public abstract class ObjectAttributeConverter<T> implements AttributeConverter<T, String> {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Gets instance.
     *
     * @return the instance
     */
    protected abstract Class<T> getInstance();

    @Override
    public String convertToDatabaseColumn(Object attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, getInstance());
        } catch (Exception e) {
        }
        return null;
    }
}
