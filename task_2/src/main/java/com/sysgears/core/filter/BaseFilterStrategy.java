package com.sysgears.core.filter;

import com.sysgears.core.exception.FilterException;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.Predicate;

@Log4j2
public abstract class BaseFilterStrategy<T> implements FilterStrategy<T> {

    protected Predicate<T> createIncludePredicate(Map<String, Object> includeCriteria) {
        return item -> includeCriteria.entrySet().stream()
                .allMatch(entry -> matchesField(item, entry.getKey(), entry.getValue()));
    }

    protected Predicate<T> createExcludePredicate(Map<String, Object> excludeCriteria) {
        return item -> excludeCriteria.entrySet().stream()
                .noneMatch(entry -> matchesField(item, entry.getKey(), entry.getValue()));
    }

    protected abstract boolean matchesField(T item, String fieldName, Object expectedValue);

    protected Object getFieldValue(T item, String fieldName) {
        try {
            Field field = item.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(item);
        } catch (Exception ex) {
            log.error("Unexpected error while accessing field '{}' on item: {}", fieldName, item, ex);
            throw new FilterException("Field not found: " + fieldName, ex);
        }
    }
}
