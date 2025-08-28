package dev.ivan.searchlab.lucene.utill.contributors;

import dev.ivan.searchlab.lucene.model.LuceneSearchRequest;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.TermQuery;

import java.util.List;
import java.util.function.Function;

public final class TermsAnyFilterContributor implements QueryContributor {

    private final String field;
    private final Function<LuceneSearchRequest, List<String>> extractor;

    public TermsAnyFilterContributor(String field, Function<LuceneSearchRequest, List<String>> extractor) {
        this.field = field;
        this.extractor = extractor;
    }

    @Override
    public void contribute(BooleanQuery.Builder builder, LuceneSearchRequest request) {
        List<String> vals = extractor.apply(request);
        if (vals == null || vals.isEmpty()) {
            return;
        }

        BooleanQuery.Builder any = new BooleanQuery.Builder();
        int added = 0;
        for (String val : vals) {
            if (val == null || val.isBlank()) {
                continue;
            }
            Term term = new Term(field, val);
            TermQuery termQuery = new TermQuery(term);
            any.add(termQuery, BooleanClause.Occur.SHOULD);
            added++;
        }
        if (added == 0) {
            return;
        }
        BooleanQuery innerBooleanQuery = any.build();
        builder.add(innerBooleanQuery, BooleanClause.Occur.FILTER);
    }
}
