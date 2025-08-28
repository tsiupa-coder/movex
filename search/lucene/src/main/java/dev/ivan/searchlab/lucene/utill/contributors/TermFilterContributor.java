package dev.ivan.searchlab.lucene.utill.contributors;

import dev.ivan.searchlab.lucene.model.LuceneSearchRequest;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

import java.util.function.Function;

public final class TermFilterContributor implements QueryContributor {

    private final String field;
    private final Function<LuceneSearchRequest, String> extractor;

    public TermFilterContributor(String field, Function<LuceneSearchRequest, String> extractor) {
        this.field = field;
        this.extractor = extractor;
    }

    @Override
    public void contribute(BooleanQuery.Builder builder, LuceneSearchRequest request) {
        String value = extractor.apply(request);
        if (value == null) {
            return;
        }
        Term term = new Term(field, value);
        Query termQuery = new TermQuery(term);
        builder.add(termQuery, BooleanClause.Occur.FILTER);
    }
}
