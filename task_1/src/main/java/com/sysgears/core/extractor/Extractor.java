package com.sysgears.core.extractor;

public interface Extractor<T, V> {

    T extractData(V value);
}
