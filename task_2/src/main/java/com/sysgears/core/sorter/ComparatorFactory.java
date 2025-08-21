package com.sysgears.core.sorter;

import java.util.Comparator;

@FunctionalInterface
public interface ComparatorFactory<T> {

    Comparator<T> createComparator(String field, SortDirection direction);
}
