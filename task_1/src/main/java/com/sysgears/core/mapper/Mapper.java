package com.sysgears.core.mapper;

@FunctionalInterface
public interface Mapper<T> {

    T map(Object... objects);
}
