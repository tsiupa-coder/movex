package dev.ivan.searchlab.search.core.util;

public interface Sink<U> extends AutoCloseable {
    void add(U u) throws Exception;
    @Override void close() throws Exception;
}