package dev.ivan.searchlab.lucene.utill.contributors;

import dev.ivan.searchlab.lucene.model.LuceneSearchRequest;
import org.apache.lucene.search.BooleanQuery;

/** Small interface for adding query parts to a BooleanQuery. */
@FunctionalInterface
public interface QueryContributor {
    void contribute(BooleanQuery.Builder builder, LuceneSearchRequest request) throws Exception;
}
