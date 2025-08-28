package dev.ivan.searchlab.lucene.utill.contributors;

import dev.ivan.searchlab.lucene.model.LuceneSearchRequest;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;

import java.util.function.Function;

public final class IntRangeFilterContributor implements QueryContributor {

    private final String field;
    private final Function<LuceneSearchRequest, Integer> fromExtractor;
    private final Function<LuceneSearchRequest, Integer> toExtractor;

    public IntRangeFilterContributor(
            String field,
            Function<LuceneSearchRequest, Integer> fromExtractor,
            Function<LuceneSearchRequest, Integer> toExtractor) {
        this.field = field;
        this.fromExtractor = fromExtractor;
        this.toExtractor = toExtractor;
    }

    @Override
    public void contribute(BooleanQuery.Builder builder, LuceneSearchRequest request) {
        Integer from = fromExtractor.apply(request);
        Integer to = toExtractor.apply(request);
        if (from == null && to == null) {
            return;
        }

        int lower = (from != null) ? from : Integer.MIN_VALUE;
        int upperValue = (to != null) ? to : Integer.MAX_VALUE;

        Query rangeQuery = IntPoint.newRangeQuery(field, lower, upperValue);
        builder.add(rangeQuery, BooleanClause.Occur.FILTER);
    }
}
