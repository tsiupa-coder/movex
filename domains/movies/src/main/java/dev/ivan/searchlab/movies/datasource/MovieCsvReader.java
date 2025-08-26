package dev.ivan.searchlab.movies.datasource;

import dev.ivan.searchlab.datasource.core.RowConsumer;
import dev.ivan.searchlab.movies.models.Movie;

import java.io.Reader;
import java.nio.file.Path;

public final class MovieCsvReader {

    private MovieCsvReader() {
    }

    public static void stream(Path csv, RowConsumer<Movie> consumer) throws Exception {
        try (var ds = MovieCsvDataSource.fromPath(csv)) {
            ds.stream(consumer);
        }
    }

    public static void stream(Reader reader, RowConsumer<Movie> consumer) throws Exception {
        try (var ds = new MovieCsvDataSource(reader)) {
            ds.stream(consumer);
        }
    }
}
