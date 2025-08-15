package com.sysgears.core.io;

import java.util.concurrent.CompletableFuture;

@FunctionalInterface
public interface IoWriter<U, T> {

    CompletableFuture<Void> writeTo(U value, T to);
}
