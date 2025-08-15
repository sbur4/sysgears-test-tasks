package com.sysgears.core.builder;

@FunctionalInterface
public interface Builder<T> {

    T build(Object... objects);
}
