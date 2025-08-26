package dev.ivan.searchlab.bridge.movies.lucene.service;

import dev.ivan.searchlab.bridge.movies.lucene.spec.MovieFields;
import dev.ivan.searchlab.lucene.model.LuceneSearchRequest;
import dev.ivan.searchlab.lucene.utill.LuceneQueryStrategy;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;

import java.util.List;

import static dev.ivan.searchlab.bridge.movies.lucene.spec.MovieFields.*;

public class MovieQueryStrategy implements LuceneQueryStrategy {

    @Override
    public Query build(LuceneSearchRequest r) throws Exception {
        final BooleanQuery.Builder b = new BooleanQuery.Builder();

        // full-text (AND) across multiple fields; match-all if no q
        final String q = trimOrNull(r.getQ());
        if (q != null && !q.isBlank()) {
            MultiFieldQueryParser parser = new MultiFieldQueryParser(
                MovieFields.FULLTEXT_FIELDS.toArray(new String[0]),
                new StandardAnalyzer()
            );
            parser.setDefaultOperator(QueryParser.Operator.AND);
            b.add(parser.parse(q), BooleanClause.Occur.MUST);
        } else {
            b.add(new MatchAllDocsQuery(), BooleanClause.Occur.MUST);
        }

        // exact (keyword) filters
        if (r.getLang() != null)   b.add(new TermQuery(new Term(LANG, r.getLang())), BooleanClause.Occur.FILTER);
        if (r.getStatus() != null) b.add(new TermQuery(new Term(STATUS, r.getStatus())), BooleanClause.Occur.FILTER);

        // boolean flags ("T"/"F")
        if (r.getAdult() != null)  b.add(boolFlag(ADULT, r.getAdult()), BooleanClause.Occur.FILTER);
        if (r.getVideo() != null)  b.add(boolFlag(VIDEO, r.getVideo()), BooleanClause.Occur.FILTER);

        // multi-valued (ANY)
        if (notEmpty(r.getGenre()))   b.add(termsAny(GENRE, r.getGenre()), BooleanClause.Occur.FILTER);
        if (notEmpty(r.getCountry())) b.add(termsAny(COUNTRY, r.getCountry()), BooleanClause.Occur.FILTER);

        // numeric ranges
        if (r.getYearFrom() != null || r.getYearTo() != null) {
            int from = (r.getYearFrom() != null) ? r.getYearFrom() : Integer.MIN_VALUE;
            int to   = (r.getYearTo()   != null) ? r.getYearTo()   : Integer.MAX_VALUE;
            b.add(IntPoint.newRangeQuery(YEAR, from, to), BooleanClause.Occur.FILTER);
        }
        if (r.getRuntimeFrom() != null || r.getRuntimeTo() != null) {
            int from = (r.getRuntimeFrom() != null) ? r.getRuntimeFrom() : Integer.MIN_VALUE;
            int to   = (r.getRuntimeTo()   != null) ? r.getRuntimeTo()   : Integer.MAX_VALUE;
            b.add(IntPoint.newRangeQuery(RUNTIME, from, to), BooleanClause.Occur.FILTER);
        }
        if (r.getRatingMin() != null) {
            int minX100 = (int) Math.round(r.getRatingMin() * 100.0);
            b.add(IntPoint.newRangeQuery(RATING_X100, minX100, Integer.MAX_VALUE), BooleanClause.Occur.FILTER);
        }
        if (r.getVotesMin() != null) {
            b.add(IntPoint.newRangeQuery(VOTES, r.getVotesMin(), Integer.MAX_VALUE), BooleanClause.Occur.FILTER);
        }

        return b.build();
    }

    // ---- local helpers (same behavior as before)
    private static Query boolFlag(String field, boolean value) {
        return new TermQuery(new Term(field, value ? "T" : "F"));
    }
    private static boolean notEmpty(List<?> l) { return l != null && !l.isEmpty(); }
    private static Query termsAny(String field, List<String> vals) {
        BooleanQuery.Builder b = new BooleanQuery.Builder();
        for (String v : vals) if (v != null && !v.isBlank())
            b.add(new TermQuery(new Term(field, v)), BooleanClause.Occur.SHOULD);
        BooleanQuery q = b.build();
        return q.clauses().isEmpty() ? new MatchAllDocsQuery() : q; // no-op when used as FILTER
    }
    private static String trimOrNull(String s) { return s == null ? null : s.trim(); }
}
