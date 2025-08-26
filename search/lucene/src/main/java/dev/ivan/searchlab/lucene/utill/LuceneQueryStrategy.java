package dev.ivan.searchlab.lucene.utill;

import dev.ivan.searchlab.lucene.model.LuceneSearchRequest;
import org.apache.lucene.search.Query;

public interface LuceneQueryStrategy {
    Query build(LuceneSearchRequest request) throws Exception;
}
