package dev.ivan.searchlab.bridge.movies.lucene.sort;

public class InvalidSortKeyException extends IllegalArgumentException {
    public InvalidSortKeyException(String key) {
        super("Unsupported sort key: " + key);
    }
}
