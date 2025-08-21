package com.sysgears.core.sorter;

import com.sysgears.core.model.Condition;

import java.util.List;

public interface SortStrategy<T> {

    List<T> sort(List<T> items, Condition condition);

    boolean supports(Class<?> clazz);
}
