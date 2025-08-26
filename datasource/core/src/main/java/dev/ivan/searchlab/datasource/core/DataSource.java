package dev.ivan.searchlab.datasource.core;

import java.io.Closeable;

public abstract class DataSource<T> implements Closeable {

    /** Stream all records to the consumer. Implementations should be resource-safe. */
    public abstract void stream(RowConsumer<T> consumer) throws Exception;

    /** Optional: override if you want to expose record count cheaply. */
    public long estimateCount() { return -1; }

    @Override
    public void close() {}
}

