package com.sysgears.core.parser;

@FunctionalInterface
public interface DataParser<T, D> {

    T parseData(D data);
}
