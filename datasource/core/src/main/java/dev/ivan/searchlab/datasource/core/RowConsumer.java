package dev.ivan.searchlab.datasource.core;

@FunctionalInterface
    public interface RowConsumer<T> {
        void accept(T value) throws Exception;
    }