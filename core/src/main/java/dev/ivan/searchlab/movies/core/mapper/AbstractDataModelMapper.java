package dev.ivan.searchlab.movies.core.mapper;

import dev.ivan.searchlab.movies.core.model.AbstractDataModel;

public abstract class AbstractDataModelMapper<T extends AbstractDataModel, D> implements Mapper<T, D> {

    @Override
    public abstract D map(T source);}
