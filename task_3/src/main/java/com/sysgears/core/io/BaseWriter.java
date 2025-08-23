package com.sysgears.core.io;

@FunctionalInterface
public interface BaseWriter<V> {

    void writeDataByPath(V value, String pathToFile);
}
