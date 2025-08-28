package dev.ivan.searchlab.lucene.utill.contributors;

import dev.ivan.searchlab.lucene.model.LuceneSearchRequest;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.search.*;

import java.util.function.Function;

public final class MinIntFilterContributor implements QueryContributor {

    private final String field;
    private final Function<LuceneSearchRequest, Integer> minExtractor;

    public MinIntFilterContributor(String field, Function<LuceneSearchRequest, Integer> minExtractor) {
        this.field = field;
        this.minExtractor = minExtractor;
    }

    @Override
    public void contribute(BooleanQuery.Builder builder, LuceneSearchRequest request) {
        Integer min = minExtractor.apply(request);
        if (min == null) {
            return;
        }
        Query rangeQuery = IntPoint.newRangeQuery(field, min, Integer.MAX_VALUE);
        builder.add(rangeQuery, BooleanClause.Occur.FILTER);
    }
}
