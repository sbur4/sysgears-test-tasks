package com.sysgears.core.io;

import com.sysgears.core.exception.CustomIoException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
public abstract class IoBase<V, T, U> implements IoReader<V, T>, IoWriter<U, T> {

    private final ReentrantReadWriteLock LOCKER;
    private final ExecutorService EXECUTOR;

    protected IoBase() {
        this.LOCKER = new ReentrantReadWriteLock();
        this.EXECUTOR = Executors.newCachedThreadPool();
    }

    void shutdown() {
        EXECUTOR.shutdownNow();
    }

    @Override
    public CompletableFuture<V> readFrom(T from) {
        return CompletableFuture.supplyAsync(() -> {
            LOCKER.readLock().lock();
            try {
                log.info("{} is attempting to read data from: {}", Thread.currentThread().getName(), from);
                V result = doRead(from);
                log.info("{} finished reading data.", Thread.currentThread().getName());
                return result;
            } catch (Exception ex) {
                log.error("{} encountered an error during read: {}", Thread.currentThread().getName(), ex.getMessage());
                throw new CustomIoException("Error during read operation", ex);
            } finally {
                LOCKER.readLock().unlock();
                shutdown();
            }
        }, EXECUTOR);
    }

    @Override
    public CompletableFuture<Void> writeTo(U value, T to) {
        return CompletableFuture.runAsync(() -> {
            LOCKER.writeLock().lock();
            try {
                log.info("{} is attempting to write data to: {}", Thread.currentThread().getName(), to);
                doWrite(value, to);
                log.info("{} finished writing data.", Thread.currentThread().getName());
            } catch (Exception e) {
                log.error("{} encountered an error during write: {}", Thread.currentThread().getName(), e.getMessage());
                throw new CustomIoException("Error during write operation", e);
            } finally {
                LOCKER.writeLock().unlock();
                shutdown();
            }
        }, EXECUTOR);
    }

    protected abstract void doWrite(U value, T to);

    protected abstract V doRead(T from) throws IOException;
}
