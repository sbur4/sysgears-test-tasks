package com.sysgears.core.filter;

import com.sysgears.core.model.Condition;

import java.util.List;

public interface FilterStrategy<T> {

    List<T> filter(List<T> items, Condition condition);

    boolean supports(Class<?> clazz);
}
