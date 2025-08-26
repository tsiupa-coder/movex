package dev.ivan.searchlab.bridge.movies.lucene.spec;

public final class SearchConstraints {

    public static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_SIZE = 10;
    public static final int MAX_SIZE     = 100;

    private SearchConstraints() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }
}
