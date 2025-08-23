package com.sysgears.core.parser;

@FunctionalInterface
public interface BaseParser<T, V> {

    T parse(V value);
}
