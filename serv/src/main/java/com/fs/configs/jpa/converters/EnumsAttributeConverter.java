package com.fs.configs.jpa.converters;

import jakarta.persistence.AttributeConverter;
import lombok.SneakyThrows;
import net.sf.json.JSONArray;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class EnumsAttributeConverter<T extends Enum> implements AttributeConverter<Set<T>, String> {

    private T t;

    @Override
    public String convertToDatabaseColumn(Set<T> attribute) {
        try {
            return JSONArray.fromObject(attribute).toString();
        } catch (Exception e) {
        }
        return null;
    }

    @SneakyThrows
    @Override
    public Set<T> convertToEntityAttribute(String dbData) {
        try {
            Type type = getClass().getGenericSuperclass();
            while (!(type instanceof ParameterizedType)) {
                type = ((Class<?>) type).getGenericSuperclass();
            }

            Class<T> result = (Class<T>) ((ParameterizedType) type)
                    .getActualTypeArguments()[0];

            return (Set<T>) JSONArray.fromObject(dbData).stream()
                    .map(v -> Enum.valueOf(result, String.valueOf(v)))
                    .collect(Collectors.toSet());

        } catch (Exception e) {
        }
        return null;
    }
}
