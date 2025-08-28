package dev.ivan.searchlab.bridge.movies.lucene.sort;

import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;

import static dev.ivan.searchlab.bridge.movies.lucene.spec.MovieFields.RATING_SORT;
import static dev.ivan.searchlab.bridge.movies.lucene.spec.MovieFields.TITLE_SORT;
import static dev.ivan.searchlab.bridge.movies.lucene.spec.MovieFields.YEAR_SORT;

public final class MovieSortFactory {

    private MovieSortFactory() { }

    public static Sort create(MovieSortKey key) {
        return switch (key) {
            case RELEVANCE -> Sort.RELEVANCE;
            case TITLE     -> new Sort(new SortField(TITLE_SORT, SortField.Type.STRING));
            case YEAR      -> new Sort(new SortField(YEAR_SORT, SortField.Type.INT, true));   // desc
            case RATING    -> new Sort(new SortField(RATING_SORT, SortField.Type.INT, true)); // desc
        };
    }
}
