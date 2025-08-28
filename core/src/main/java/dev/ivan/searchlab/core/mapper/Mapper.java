package dev.ivan.searchlab.core.mapper;

public interface Mapper<T, D> {
    D map(T source);
}