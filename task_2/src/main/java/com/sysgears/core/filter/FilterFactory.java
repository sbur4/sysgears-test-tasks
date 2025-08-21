package com.sysgears.core.filter;

public class FilterFactory {

    public static FilterContext createDefaultFilterContext() {
        FilterContext context = new FilterContext();
        context.registerStrategy(new GenericFilterStrategy());
        return context;
    }

    public static FilterStrategy<Object> createGenericFilterStrategy() {
        return new GenericFilterStrategy();
    }

    public static <T> CompositeFilterStrategy<T> createCompositeFilterStrategy() {
        return new CompositeFilterStrategy<>();
    }
}
