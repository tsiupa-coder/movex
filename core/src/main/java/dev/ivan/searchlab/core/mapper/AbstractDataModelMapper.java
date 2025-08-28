package dev.ivan.searchlab.core.mapper;

import dev.ivan.searchlab.core.model.AbstractDataModel;

public abstract class AbstractDataModelMapper<T extends AbstractDataModel, D> implements Mapper<T, D> {

    @Override
    public abstract D map(T source);}
