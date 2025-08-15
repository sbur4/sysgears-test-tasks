package com.sysgears.core.io;

import java.util.concurrent.CompletableFuture;

@FunctionalInterface
public interface IoReader<V, T> {

    CompletableFuture<V> readFrom(T from);
}
