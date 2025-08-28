package dev.ivan.searchlab.lucene.utill.contributors;

import dev.ivan.searchlab.lucene.model.LuceneSearchRequest;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;

import java.util.List;

public final class FullTextOrMatchAllContributor implements QueryContributor {

    private final String[] fields;
    private final Analyzer analyzer;

    public FullTextOrMatchAllContributor(List<String> fields, Analyzer analyzer) {
        this.fields = fields.toArray(new String[0]);
        this.analyzer = analyzer;
    }

    @Override
    public void contribute(BooleanQuery.Builder builder, LuceneSearchRequest request) throws Exception {
        String requestQuery = request.getQ();
        final String trimmedRequestQuery = trimOrNull(requestQuery);
        if (trimmedRequestQuery == null || trimmedRequestQuery.isBlank()) {
            Query matchAll = new MatchAllDocsQuery();
            builder.add(matchAll, BooleanClause.Occur.MUST);
            return;
        }
        MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, analyzer);
        parser.setDefaultOperator(QueryParser.Operator.AND);
        Query query = parser.parse(trimmedRequestQuery);
        builder.add(query, BooleanClause.Occur.MUST);
    }

    private static String trimOrNull(String s) { return s == null ? null : s.trim(); }
}
