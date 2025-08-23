package com.sysgears.core.mapper;

@FunctionalInterface
public interface BaseMapper<T, V> {

    T map(V value);
}
