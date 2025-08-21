package com.sysgears.core.sorter;

import com.sysgears.core.exception.SorterException;
import com.sysgears.core.model.Condition;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SortContext {

    private final List<SortStrategy<?>> strategies;

    public SortContext() {
        this.strategies = new ArrayList<>();
        registerStrategy(new GenericSortStrategy());
    }

    public void registerStrategy(SortStrategy<?> strategy) {
        if (Objects.nonNull(strategy) && !strategies.contains(strategy)) {
            strategies.add(strategy);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> sort(List<T> items, Condition condition) {
        if (CollectionUtils.isEmpty(items) || condition == null) {
            return items;
        }

        Class<?> itemClass = items.get(0).getClass();

        SortStrategy<T> strategy = (SortStrategy<T>) strategies.stream()
                .filter(s -> s.supports(itemClass))
                .findFirst()
                .orElseThrow(() -> new SorterException(
                        "No sort strategy found for class: " + itemClass.getName()));

        return strategy.sort(items, condition);
    }

    public <T> List<T> sort(List<T> items, Condition condition, SortStrategy<T> customStrategy) {
        return customStrategy.sort(items, condition);
    }
}
