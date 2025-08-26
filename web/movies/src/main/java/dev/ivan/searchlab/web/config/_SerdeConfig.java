package dev.ivan.searchlab.web.config;

import dev.ivan.searchlab.lucene.model.LuceneSearchRequest;
import dev.ivan.searchlab.lucene.model.LuceneSearchResponse;
import dev.ivan.searchlab.bridge.movies.lucene.model.MovieSearchResult;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;

@Introspected(classes = {
        LuceneSearchRequest.class,
        LuceneSearchResponse.class,
        MovieSearchResult.class
})
@SerdeImport(LuceneSearchResponse.class)
@SerdeImport(MovieSearchResult.class)
@SerdeImport(LuceneSearchRequest.class)
final class _SerdeConfig { private _SerdeConfig() {} }

