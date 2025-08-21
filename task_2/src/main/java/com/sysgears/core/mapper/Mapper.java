package com.sysgears.core.mapper;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface Mapper<T, V> {

    T mapData(@Nonnull V value);
}
