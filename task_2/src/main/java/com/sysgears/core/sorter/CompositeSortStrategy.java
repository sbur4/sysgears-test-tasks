package com.sysgears.core.sorter;

import com.sysgears.core.model.Condition;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CompositeSortStrategy implements SortStrategy<Object> {

    private final List<SortStrategy<?>> strategies = new ArrayList<>();

    public CompositeSortStrategy addStrategy(SortStrategy<?> strategy) {
        strategies.add(strategy);
        return this;
    }

    @Override
    public List<Object> sort(List<Object> items, Condition condition) {
        if (CollectionUtils.isEmpty(items)) {
            return items;
        }

        return items.stream()
                .sorted(createCompositeComparator(condition))
                .collect(Collectors.toList());
    }

    private Comparator<Object> createCompositeComparator(Condition condition) {
        return (obj1, obj2) -> strategies.stream()
                .filter(strategy -> strategy.supports(obj1.getClass()) && strategy.supports(obj2.getClass()))
                .map(strategy -> (SortStrategy<Object>) strategy)
                .map(typedStrategy -> typedStrategy.sort(Arrays.asList(obj1, obj2), condition))
                .findFirst().map(sorted -> sorted.get(0) == obj1 ? -1 : 1)
                .orElse(0);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return strategies.stream().anyMatch(s -> s.supports(clazz));
    }
}
