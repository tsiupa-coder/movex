package dev.ivan.searchlab.web.controller;

import dev.ivan.searchlab.bridge.movies.lucene.model.MovieSearchResult;
import dev.ivan.searchlab.lucene.model.LuceneSearchRequest;
import dev.ivan.searchlab.search.core.SearchService;
import dev.ivan.searchlab.search.core.model.SearchResponse;
import io.micronaut.http.annotation.*;

@Controller("/search")
public class SearchController {
    private final SearchService<MovieSearchResult, LuceneSearchRequest> searchService;

    public SearchController(SearchService<MovieSearchResult, LuceneSearchRequest> service) {
        this.searchService = service;
    }

    @Get
    public SearchResponse<MovieSearchResult> search(LuceneSearchRequest request) throws Exception {
        return searchService.search(request);
    }
}
