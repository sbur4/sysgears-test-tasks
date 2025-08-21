package com.sysgears.core.sorter;

public class SortFactory {

    public static SortContext createDefaultSortContext() {
        SortContext context = new SortContext();
        context.registerStrategy(new GenericSortStrategy());
        return context;
    }

    public static GenericSortStrategy createGenericSortStrategy() {
        return new GenericSortStrategy();
    }

    public static CompositeSortStrategy createCompositeSortStrategy() {
        return new CompositeSortStrategy();
    }
}
