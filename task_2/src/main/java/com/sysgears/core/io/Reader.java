package com.sysgears.core.io;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface Reader<T> {

    T readDataByPath(@Nonnull String path);
}
