package dev.ivan.searchlab.bridge.movies.lucene.sort;

import dev.ivan.searchlab.bridge.movies.lucene.spec.MovieFields;
import dev.ivan.searchlab.lucene.utill.LuceneSortStrategy;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;

import static dev.ivan.searchlab.bridge.movies.lucene.spec.MovieFields.RATING;
import static dev.ivan.searchlab.bridge.movies.lucene.spec.MovieFields.RATING_SORT;
import static dev.ivan.searchlab.bridge.movies.lucene.spec.MovieFields.RELEVANCE;
import static dev.ivan.searchlab.bridge.movies.lucene.spec.MovieFields.TITLE;
import static dev.ivan.searchlab.bridge.movies.lucene.spec.MovieFields.TITLE_SORT;
import static dev.ivan.searchlab.bridge.movies.lucene.spec.MovieFields.YEAR;
import static dev.ivan.searchlab.bridge.movies.lucene.spec.MovieFields.YEAR_SORT;

public class MovieSortStrategy implements LuceneSortStrategy {

    private final Sort DEFAULT_SORT = Sort.RELEVANCE;

    @Override
    public Sort buildSort(String sortKey) {
        if (sortKey == null || sortKey.isBlank()) {
            return DEFAULT_SORT;
        }

        return switch (sortKey.trim().toLowerCase()) {
            case RELEVANCE -> Sort.RELEVANCE;
            case TITLE -> {
                SortField titleSort = new SortField(TITLE_SORT, SortField.Type.STRING);
                yield new Sort(titleSort);
            }
            case YEAR -> {
                SortField yearSort = new SortField(YEAR_SORT, SortField.Type.INT, true);
                yield new Sort(yearSort);
            }
            case RATING -> {
                SortField ratingSort = new SortField(RATING_SORT, SortField.Type.INT, true);
                yield new Sort(ratingSort);
            }
            default -> DEFAULT_SORT;
        };
    }
}
