package com.sysgears.core.io;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface Writer<T> {

    void readDataByPath(@Nonnull String path, T value);
}
