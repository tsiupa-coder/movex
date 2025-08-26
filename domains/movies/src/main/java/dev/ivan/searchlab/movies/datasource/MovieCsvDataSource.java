package dev.ivan.searchlab.movies.datasource;

import dev.ivan.searchlab.datasource.csv.CsvDataSource;
import dev.ivan.searchlab.movies.models.Movie;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

public final class MovieCsvDataSource extends CsvDataSource<Movie> {

    public MovieCsvDataSource(Reader reader) {
        super(reader, new MovieCsvDecoder());
    }

    /** Convenience factory from file path */
    public static MovieCsvDataSource fromPath(Path csv) throws Exception {
        return new MovieCsvDataSource(Files.newBufferedReader(csv));
    }
}
