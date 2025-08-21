package com.sysgears.core.filter;

import com.sysgears.core.model.Condition;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Log4j2
public class GenericFilterStrategy extends BaseFilterStrategy<Object> {

    @Override
    public List<Object> filter(List<Object> items, Condition condition) {
        if (CollectionUtils.isEmpty(items)) {
            return items;
        }

        return items.stream()
                .filter(createIncludePredicate(condition))
                .filter(createExcludePredicate(condition))
                .collect(Collectors.toList());
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected boolean matchesField(Object item, String fieldName, Object expectedValue) {
        try {
            Object actualValue = getFieldValue(item, fieldName);
            return compareValues(actualValue, expectedValue);
        } catch (Exception e) {
            log.warn("Field access failed for {}: {}", fieldName, e.getMessage());
            return false;
        }
    }

    private boolean compareValues(Object actual, Object expected) {
        if (actual == null && expected == null) {
            return true;
        }
        if (actual == null || expected == null) {
            return false;
        }

        return Objects.equals(actual, expected);
    }

    private Predicate<Object> createIncludePredicate(Condition condition) {
        return item -> CollectionUtils.isEmpty(condition.getInclude()) ||
                condition.getInclude().stream()
                        .allMatch(criteria -> createIncludePredicate(criteria).test(item));
    }

    private Predicate<Object> createExcludePredicate(Condition condition) {
        return item -> CollectionUtils.isEmpty(condition.getExclude()) ||
                condition.getExclude().stream()
                        .allMatch(criteria -> createExcludePredicate(criteria).test(item));
    }
}
