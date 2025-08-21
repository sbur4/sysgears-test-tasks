package com.sysgears.core.filter;

import com.sysgears.core.model.Condition;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CompositeFilterStrategy<T> implements FilterStrategy<T> {

    private final List<Predicate<T>> includePredicates;
    private final List<Predicate<T>> excludePredicates;

    public CompositeFilterStrategy() {
        this.includePredicates = new ArrayList<>();
        this.excludePredicates = new ArrayList<>();
    }

    public CompositeFilterStrategy<T> addIncludePredicate(Predicate<T> predicate) {
        includePredicates.add(predicate);
        return this;
    }

    public CompositeFilterStrategy<T> addExcludePredicate(Predicate<T> predicate) {
        excludePredicates.add(predicate);
        return this;
    }

    @Override
    public List<T> filter(List<T> items, Condition condition) {
        Predicate<T> finalPredicate = createFinalPredicate();

        return items.stream()
                .filter(finalPredicate)
                .collect(Collectors.toList());
    }

    private Predicate<T> createFinalPredicate() {
        Predicate<T> includePredicate = includePredicates.isEmpty()
                ? item -> true
                : includePredicates.stream().reduce(Predicate::and).orElse(item -> true);

        Predicate<T> excludePredicate = excludePredicates.isEmpty()
                ? item -> true
                : excludePredicates.stream().reduce(Predicate::or).orElse(item -> false).negate();

        return includePredicate.and(excludePredicate);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
