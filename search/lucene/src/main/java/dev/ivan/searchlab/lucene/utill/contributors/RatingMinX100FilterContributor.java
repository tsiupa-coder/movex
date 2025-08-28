package dev.ivan.searchlab.lucene.utill.contributors;

import dev.ivan.searchlab.lucene.model.LuceneSearchRequest;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.search.*;

import java.util.function.Function;

public final class RatingMinX100FilterContributor implements QueryContributor {

    private final String field;
    private final Function<LuceneSearchRequest, Double> minExtractor;

    public RatingMinX100FilterContributor(String field, Function<LuceneSearchRequest, Double> minExtractor) {
        this.field = field;
        this.minExtractor = minExtractor;
    }

    @Override
    public void contribute(BooleanQuery.Builder builder, LuceneSearchRequest request) {
        Double min = minExtractor.apply(request);
        if (min == null) {
            return;
        }
        int minX100 = (int) Math.round(min * 100.0);
        Query rangeQuery = IntPoint.newRangeQuery(field, minX100, Integer.MAX_VALUE);
        builder.add(rangeQuery, BooleanClause.Occur.FILTER);
    }
}
