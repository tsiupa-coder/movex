package dev.ivan.searchlab.lucene.utill.contributors;

import dev.ivan.searchlab.lucene.model.LuceneSearchRequest;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

import java.util.function.Function;

public final class BoolFlagFilterContributor implements QueryContributor {

    private final String field;
    private final Function<LuceneSearchRequest, Boolean> extractor;

    public BoolFlagFilterContributor(String field, Function<LuceneSearchRequest, Boolean> extractor) {
        this.field = field;
        this.extractor = extractor;
    }

    @Override
    public void contribute(BooleanQuery.Builder builder, LuceneSearchRequest request) {
        Boolean value = extractor.apply(request);
        if (value == null) {
            return;
        }
        String strValue = value ? "T" : "F";
        Term term = new Term(field, strValue);
        Query query = new TermQuery(term);
        builder.add(query, BooleanClause.Occur.FILTER);
    }
}
