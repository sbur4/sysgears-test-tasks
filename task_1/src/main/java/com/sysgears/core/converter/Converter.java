package com.sysgears.core.converter;

@FunctionalInterface
public interface Converter<T, V> {

    T convert(V value);
}
