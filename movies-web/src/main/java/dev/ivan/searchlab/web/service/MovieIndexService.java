package dev.ivan.searchlab.web.service;

import jakarta.inject.Singleton;
import dev.ivan.searchlab.movies.MovieIndexer;

import java.nio.file.Path;

@Singleton
public class MovieIndexService {
    private final MovieIndexer indexer = new MovieIndexer(1024);

    public void indexCsv(Path csv, Path indexDir, boolean create) throws Exception {
        indexer.indexCsv(csv, indexDir, create);
    }
}