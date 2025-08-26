package dev.ivan.searchlab.movies.core.mapper;

import dev.ivan.searchlab.movies.core.model.AbstractDataModel;

public interface Mapper<T, D> {
    D map(T source);
}