package dev.ivan.searchlab.web.controller;

import dev.ivan.searchlab.web.api.SearchRequest;
import dev.ivan.searchlab.web.api.SearchResponse;
import dev.ivan.searchlab.web.service.SearchService;
import io.micronaut.http.annotation.*;

@Controller("/search")
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService service) {
        this.searchService = service;
    }

    // GET with query params → Micronaut binds to SearchRequest automatically
    @Get
    public SearchResponse search(@RequestBean SearchRequest req) throws Exception {
        return searchService.search(req);
    }
}
