package dev.ivan.searchlab.web.service;

import dev.ivan.searchlab.movies.core.LuceneComponents;
import dev.ivan.searchlab.web.api.SearchRequest;
import dev.ivan.searchlab.web.api.SearchResponse;
import dev.ivan.searchlab.web.config.LuceneIndexConfig;

import jakarta.inject.Singleton;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;            // <-- make sure THIS is imported
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;

import java.nio.file.Path;
import java.util.*;

import static java.lang.classfile.TypeKind.DOUBLE;
import static java.lang.classfile.TypeKind.INT;
import static java.sql.JDBCType.FLOAT;
import static java.util.TimeZone.LONG;
import static javax.management.openmbean.SimpleType.STRING;

@Singleton
public class SearchService {

    private final LuceneIndexConfig cfg;

    public SearchService(LuceneIndexConfig cfg) {
        this.cfg = cfg;
    }

    public SearchResponse search(SearchRequest req) throws Exception {
        final int page = Math.max(1, Optional.ofNullable(req.getPage()).orElse(1));
        final int size = Math.min(100, Math.max(1, Optional.ofNullable(req.getSize()).orElse(10)));
        final int start = (page - 1) * size;

        try (var lc = new LuceneComponents(Path.of(cfg.getIndexDir()), 256, false)) {
            lc.searcherManager.maybeRefresh();
            IndexSearcher searcher = lc.searcherManager.acquire();
            try {
                Query query = buildQuery(req);
                Sort sort = buildSort(req);

                final TopDocs top = (sort == null)
                        ? searcher.search(query, start + size)
                        : searcher.search(query, start + size, sort);

                final long total = top.totalHits.value();
                final List<Map<String, Object>> items = new ArrayList<>(
                        Math.max(0, Math.min(size, top.scoreDocs.length - start)));

                final var stored = searcher.storedFields(); // Lucene 10+

                for (int i = start; i < Math.min(top.scoreDocs.length, start + size); i++) {
                    int docId = top.scoreDocs[i].doc;
                    Document d = stored.document(docId);


                    Map<String, Object> item = new LinkedHashMap<>();

                    item.put("id", d.get("id"));
                    item.put("title", d.get("title"));
                    item.put("score", top.scoreDocs[i].score);
                    if (d.getField("poster_path") != null) item.put("poster_path", d.get("poster_path"));
                    if (d.getField("homepage") != null)    item.put("homepage", d.get("homepage"));


                    items.add(item);
                }

                return new SearchResponse(total, page, size, items);
            } finally {
                lc.searcherManager.release(searcher);
            }
        }
    }


    // ------------------- helpers -------------------

    private Query buildQuery(SearchRequest r) throws Exception {
        BooleanQuery.Builder b = new BooleanQuery.Builder();

        // full-text across multiple fields
        String q = safe(r.getQ());
        if (q != null && !q.isBlank()) {
            var analyzer = new StandardAnalyzer();
            var parser = new MultiFieldQueryParser(
                    new String[]{"title", "original_title", "overview", "tagline"},
                    analyzer
            );
            parser.setDefaultOperator(QueryParser.Operator.AND);
            b.add(parser.parse(q), BooleanClause.Occur.MUST);
        } else {
            b.add(new MatchAllDocsQuery(), BooleanClause.Occur.MUST);
        }

        // keyword filters (exact match)
        if (r.getLang() != null)   b.add(new TermQuery(new Term("lang", r.getLang())), BooleanClause.Occur.FILTER);
        if (r.getStatus() != null) b.add(new TermQuery(new Term("status", r.getStatus())), BooleanClause.Occur.FILTER);

        // boolean flags ("T"/"F")
        if (r.getAdult() != null)  b.add(new TermQuery(new Term("adult", r.getAdult() ? "T" : "F")), BooleanClause.Occur.FILTER);
        if (r.getVideo() != null)  b.add(new TermQuery(new Term("video", r.getVideo() ? "T" : "F")), BooleanClause.Occur.FILTER);

        // multi-valued filters
        if (listNotEmpty(r.getGenre()))   b.add(termsAny("genre", r.getGenre()), BooleanClause.Occur.FILTER);
        if (listNotEmpty(r.getCountry())) b.add(termsAny("country", r.getCountry()), BooleanClause.Occur.FILTER);

        // numeric ranges
        if (r.getYearFrom() != null || r.getYearTo() != null) {
            int from = r.getYearFrom() != null ? r.getYearFrom() : Integer.MIN_VALUE;
            int to   = r.getYearTo()   != null ? r.getYearTo()   : Integer.MAX_VALUE;
            b.add(IntPoint.newRangeQuery("year", from, to), BooleanClause.Occur.FILTER);
        }
        if (r.getRuntimeFrom() != null || r.getRuntimeTo() != null) {
            int from = r.getRuntimeFrom() != null ? r.getRuntimeFrom() : Integer.MIN_VALUE;
            int to   = r.getRuntimeTo()   != null ? r.getRuntimeTo()   : Integer.MAX_VALUE;
            b.add(IntPoint.newRangeQuery("runtime", from, to), BooleanClause.Occur.FILTER);
        }
        if (r.getRatingMin() != null) {
            int minX100 = (int) Math.round(r.getRatingMin() * 100.0);
            b.add(IntPoint.newRangeQuery("rating_x100", minX100, Integer.MAX_VALUE), BooleanClause.Occur.FILTER);
        }
        if (r.getVotesMin() != null) {
            b.add(IntPoint.newRangeQuery("votes", r.getVotesMin(), Integer.MAX_VALUE), BooleanClause.Occur.FILTER);
        }

        return b.build();
    }

    private Sort buildSort(SearchRequest r) {
        String s = safe(r.getSort());
        if (s == null || s.isBlank() || s.equalsIgnoreCase("relevance")) {
            return null; // relevance
        }
        return switch (s.toLowerCase()) {
            case "title"  -> new Sort(new SortField("title_sort", SortField.Type.STRING));
            case "year"   -> new Sort(new SortField("year_sort", SortField.Type.INT, true));    // desc
            case "rating" -> new Sort(new SortField("rating_sort", SortField.Type.INT, true));  // desc
            default       -> null;
        };
    }

    private static String safe(String s) { return s == null ? null : s.trim(); }

    private static boolean listNotEmpty(List<?> l) { return l != null && !l.isEmpty(); }

    private static Query termsAny(String field, List<String> vals) {
        BooleanQuery.Builder b = new BooleanQuery.Builder();
        for (String v : vals) {
            if (v != null && !v.isBlank()) {
                b.add(new TermQuery(new Term(field, v)), BooleanClause.Occur.SHOULD);
            }
        }
        BooleanQuery q = b.build();
        return q.clauses().isEmpty() ? new MatchAllDocsQuery() : q;
    }
}
