package dev.ivan.searchlab.search.core;

import dev.ivan.searchlab.search.core.model.SearchRequest;
import dev.ivan.searchlab.search.core.model.SearchResponse;
import dev.ivan.searchlab.search.core.model.SearchResult;

public abstract class SearchService<T extends SearchResult, Q extends SearchRequest> {
    public abstract SearchResponse<T> search(Q request) throws Exception;
}

