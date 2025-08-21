package com.sysgears.core.sorter;

import lombok.extern.log4j.Log4j2;

import java.util.Comparator;

@Log4j2
public class GenericSortStrategy extends BaseSortStrategy<Object> {

    @Override
    protected Comparator<Object> createFieldComparator(String fieldName, SortDirection direction) {
        return (obj1, obj2) -> {
            try {
                Comparable<?> value1 = (Comparable<?>) getFieldValue(obj1, fieldName);
                Comparable<?> value2 = (Comparable<?>) getFieldValue(obj2, fieldName);

                if (value1 == null && value2 == null) {
                    return 0;
                }
                if (value1 == null) {
                    return direction == SortDirection.ASC ? -1 : 1;
                }
                if (value2 == null) {
                    return direction == SortDirection.ASC ? 1 : -1;
                }

                @SuppressWarnings("unchecked")
                int result = ((Comparable<Object>) value1).compareTo(value2);
                return direction == SortDirection.ASC ? result : -result;

            } catch (Exception e) {
                log.warn("Failed to compare field {}: {}", fieldName, e.getMessage());
                return 0;
            }
        };
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
