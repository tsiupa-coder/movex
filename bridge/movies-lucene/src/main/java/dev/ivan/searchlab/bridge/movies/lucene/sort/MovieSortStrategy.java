package dev.ivan.searchlab.bridge.movies.lucene.sort;

import dev.ivan.searchlab.lucene.utill.LuceneSortStrategy;
import org.apache.lucene.search.Sort;

public class MovieSortStrategy implements LuceneSortStrategy {

    private static final MovieSortKey DEFAULT_KEY = MovieSortKey.RELEVANCE;

    @Override
    public Sort buildSort(String sortKey) {
        MovieSortKey key = MovieSortParser.parseOrDefault(sortKey, DEFAULT_KEY);
        return MovieSortFactory.create(key);
    }
}
