package com.sysgears.core.parser;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface Parser<T, V> {

    T parseData(@Nonnull V value);
}
