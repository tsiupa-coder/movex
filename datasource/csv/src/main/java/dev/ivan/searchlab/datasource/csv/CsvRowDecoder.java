package dev.ivan.searchlab.datasource.csv;

import dev.ivan.searchlab.movies.core.model.AbstractDataModel;

import java.util.Map;

public interface CsvRowDecoder<T extends AbstractDataModel> {
    T decode(String[] row, Map<String, Integer> headerIndex) throws Exception;
}