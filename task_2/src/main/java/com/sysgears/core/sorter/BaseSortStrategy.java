package com.sysgears.core.sorter;

import com.sysgears.core.exception.SorterException;
import com.sysgears.core.model.Condition;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class BaseSortStrategy<T> implements SortStrategy<T> {

    protected Comparator<T> createComparator(List<String> sortFields) {
        if (CollectionUtils.isEmpty(sortFields)) {
            return Comparator.comparing(Object::toString);
        }

        Comparator<T> comparator = null;

        for (String sortField : sortFields) {
            String fieldName = extractFieldName(sortField);
            SortDirection direction = extractSortDirection(sortField);

            Comparator<T> fieldComparator = createFieldComparator(fieldName, direction);

            if (comparator == null) {
                comparator = fieldComparator;
            } else {
                comparator = comparator.thenComparing(fieldComparator);
            }
        }

        return comparator != null ? comparator : Comparator.comparing(Object::toString);
    }

    protected String extractFieldName(String sortExpression) {
        if (StringUtils.isBlank(sortExpression)) {
            throw new SorterException("Sort expression cannot be null or empty");
        }
        return StringUtils.trim(sortExpression);
    }

    protected SortDirection extractSortDirection(String sortExpression) {
        if (StringUtils.isBlank(sortExpression)) {
            return SortDirection.ASC;
        }

        String expression = StringUtils.trim(sortExpression);
        if (Objects.equals(expression, SortDirection.DESC.getValue())) {
            return SortDirection.DESC;
        }
        return SortDirection.ASC;
    }

    protected abstract Comparator<T> createFieldComparator(String fieldName, SortDirection direction);

    protected Object getFieldValue(T item, String fieldName) {
        try {
            Field field = item.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(item);
        } catch (Exception e) {
            throw new SorterException("Field not found: " + fieldName, e);
        }
    }

    @Override
    public List<T> sort(List<T> items, Condition condition) {
        if (CollectionUtils.isEmpty(items) || condition == null) {
            return items;
        }

        List<String> sortFields = condition.getSortBy();
        if (CollectionUtils.isEmpty(sortFields)) {
            return items;
        }

        Comparator<T> comparator = createComparator(sortFields);
        return items.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
