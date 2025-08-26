package dev.ivan.searchlab.search.core;

import dev.ivan.searchlab.datasource.core.DataSource;

public interface Indexer<T> {
    void index(DataSource<T> source) throws Exception;
}