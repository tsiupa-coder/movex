package dev.ivan.searchlab.bridge.movies.lucene.service;

import dev.ivan.searchlab.lucene.indexing.AbstractLuceneIndexer;
import dev.ivan.searchlab.core.mapper.AbstractDataModelMapper;
import dev.ivan.searchlab.movies.datasource.MovieCsvDataSource;
import dev.ivan.searchlab.movies.models.Movie;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;

import java.nio.file.Files;
import java.nio.file.Path;

public final class LuceneMovieIndexer extends AbstractLuceneIndexer<Movie> {

    public LuceneMovieIndexer(IndexWriter writer,
                              AbstractDataModelMapper<Movie, Document> mapper,
                              int batchSize) {
        super(writer, mapper, batchSize);
    }

    public void index(Path csvPath) throws Exception {
        try (MovieCsvDataSource dataSource = new MovieCsvDataSource(Files.newBufferedReader(csvPath))) {
            index(dataSource);
        }
    }
}

