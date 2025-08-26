package dev.ivan.searchlab.lucene.utill;

import org.apache.lucene.search.Sort;

public interface LuceneSortStrategy {
    Sort buildSort(String sortKey);
}
