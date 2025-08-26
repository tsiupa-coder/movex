package dev.ivan.searchlab.web.bootstrap;

import dev.ivan.searchlab.bridge.movies.lucene.mapper.LuceneMovieDocumentMapper;
import dev.ivan.searchlab.bridge.movies.lucene.mapper.MovieHitMapper;
import dev.ivan.searchlab.bridge.movies.lucene.model.MovieSearchResult;
import dev.ivan.searchlab.bridge.movies.lucene.service.LuceneMovieIndexer;
import dev.ivan.searchlab.bridge.movies.lucene.service.MovieQueryStrategy;
import dev.ivan.searchlab.bridge.movies.lucene.sort.MovieSortStrategy;
import dev.ivan.searchlab.lucene.GenericLuceneSearchService;
import dev.ivan.searchlab.lucene.LuceneComponents;
import dev.ivan.searchlab.lucene.model.LuceneSearchRequest;
import dev.ivan.searchlab.search.core.SearchService;
import dev.ivan.searchlab.web.config.LuceneIndexConfig;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;

import java.nio.file.Path;

@Factory
public class LuceneMovieIndexerFactory {

    @Bean
    @Singleton
    public LuceneMovieDocumentMapper luceneMovieDocumentMapper() {
        return new LuceneMovieDocumentMapper();
    }

    @Bean
    @Singleton
    public LuceneMovieIndexer luceneMovieIndexer(LuceneComponents components,
                                                 LuceneMovieDocumentMapper mapper,
                                                 LuceneIndexConfig config) {
        return new LuceneMovieIndexer(components.writer, mapper, config.getBatchSize());
    }

    @Bean
    @Singleton
    public LuceneComponents luceneComponents(LuceneIndexConfig config) throws Exception {
        Path indexPath = Path.of(config.getIndexDir());
        return new LuceneComponents(indexPath, config.getRamMb(), config.isCreate());
    }

    @Bean
    @Singleton
    public SearchService<MovieSearchResult, LuceneSearchRequest> searchService(LuceneComponents luceneComponents) {
        return new GenericLuceneSearchService<>(
                luceneComponents,
                new MovieQueryStrategy(),
                new MovieSortStrategy(),
                new MovieHitMapper(),
                GenericLuceneSearchService.Constraints.of(1, 10, 100)
        );
    }
}
