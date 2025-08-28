package dev.ivan.searchlab.bridge.movies.lucene.sort;

import static dev.ivan.searchlab.bridge.movies.lucene.spec.MovieFields.RATING;
import static dev.ivan.searchlab.bridge.movies.lucene.spec.MovieFields.RELEVANCE;
import static dev.ivan.searchlab.bridge.movies.lucene.spec.MovieFields.TITLE;
import static dev.ivan.searchlab.bridge.movies.lucene.spec.MovieFields.YEAR;

public final class MovieSortParser {

    private MovieSortParser() {
    }

    public static MovieSortKey parseOrDefault(String key, MovieSortKey defaultKey) {
        if (key == null || key.isBlank()) {
            return defaultKey;
        }
        String lowerCaseKey = key.trim().toLowerCase();
        return switch (lowerCaseKey) {
            case RELEVANCE -> MovieSortKey.RELEVANCE;
            case TITLE -> MovieSortKey.TITLE;
            case YEAR -> MovieSortKey.YEAR;
            case RATING -> MovieSortKey.RATING;
            default -> throw new InvalidSortKeyException(key);
        };
    }
}
