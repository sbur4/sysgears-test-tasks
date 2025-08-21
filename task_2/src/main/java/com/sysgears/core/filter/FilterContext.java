package com.sysgears.core.filter;

import com.sysgears.core.exception.FilterException;
import com.sysgears.core.model.Condition;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log4j2
public class FilterContext {

    private final List<FilterStrategy<?>> strategies;

    public FilterContext() {
        this.strategies = new ArrayList<>();
        registerStrategy(new GenericFilterStrategy());
    }

    public void registerStrategy(FilterStrategy<?> strategy) {
        if (Objects.nonNull(strategy) && !strategies.contains(strategy)) {
            strategies.add(strategy);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> filter(List<T> items, Condition condition) {
        if (CollectionUtils.isEmpty(items)) {
            return items;
        }

        Class<?> itemClass = items.get(0).getClass();

        FilterStrategy<T> strategy = (FilterStrategy<T>) strategies.stream()
                .filter(s -> s.supports(itemClass))
                .findFirst()
                .orElseThrow(() -> new FilterException(
                        "No filter strategy found for class: " + itemClass.getName()));

        return strategy.filter(items, condition);
    }

    public <T> List<T> filter(List<T> items, Condition condition, FilterStrategy<T> customStrategy) {
        return customStrategy.filter(items, condition);
    }
}
