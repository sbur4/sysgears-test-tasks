package com.sysgears.core.io;

@FunctionalInterface
public interface BaseReader<T> {

    T readDataByPath(String path);
}
