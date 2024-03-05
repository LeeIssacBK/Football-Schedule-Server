package com.fs.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Objects;

@Slf4j
public class ObjectComparator {

    public static boolean equals(Object origin, Object target) {
        if (origin == target) {
            return true;
        }
        if (origin == null || target == null) {
            return false;
        }
        if (origin.getClass() != target.getClass()) {
            return false;
        }
        for (Field field : origin.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                if (!Objects.equals(field.get(origin), field.get(target))) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                log.error(e.getMessage());
            }
        }
        return true;
    }

    public static Object getFieldData(Object origin, String fieldName) {
        try {
            Field field = origin.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(origin);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return fieldName;
        }
    }

}
