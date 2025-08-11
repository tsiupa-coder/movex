package dev.ivan.searchlab.web.controller;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import dev.ivan.searchlab.web.service.MovieIndexService;

import java.nio.file.Path;

@Controller("/index")
public class IndexController {
    private final MovieIndexService service;

    public IndexController(MovieIndexService service) {
        this.service = service;
    }

    // Kick off indexing from a CSV path (simplest)
    @Post("/from-path")
    public HttpStatus fromPath(@QueryValue String csvPath,
                               @QueryValue String indexDir,
                               @QueryValue(defaultValue = "true") boolean create) throws Exception {
        service.indexCsv(Path.of(csvPath), Path.of(indexDir), create);
        return HttpStatus.OK;
    }
}
