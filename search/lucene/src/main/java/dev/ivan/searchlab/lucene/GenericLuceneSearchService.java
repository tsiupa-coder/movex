package dev.ivan.searchlab.lucene;

import dev.ivan.searchlab.lucene.model.LuceneSearchRequest;
import dev.ivan.searchlab.lucene.model.LuceneSearchResponse;
import dev.ivan.searchlab.lucene.utill.LuceneQueryStrategy;
import dev.ivan.searchlab.lucene.utill.LuceneSortStrategy;
import dev.ivan.searchlab.search.core.SearchService;
import dev.ivan.searchlab.search.core.model.SearchResponse;
import dev.ivan.searchlab.search.core.model.SearchResult;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;

import java.util.List;
import java.util.Optional;

import static java.lang.Math.addExact;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class GenericLuceneSearchService<T extends SearchResult> extends SearchService<T, LuceneSearchRequest> {

    public record Constraints(int defaultPage, int defaultSize, int maxSize) {
        public static Constraints of(int defaultPage, int defaultSize, int maxSize) {
            return new Constraints(defaultPage, defaultSize, maxSize);
        }

        public static Constraints defaults() {
            return new Constraints(1, 10, 100);
        }
    }

    private final LuceneComponents lc;
    private final LuceneQueryStrategy queryStrategy;
    private final LuceneSortStrategy sortStrategy;
    private final LuceneHitMapper<T> hitMapper;
    private final Constraints constraints;

    public GenericLuceneSearchService(LuceneComponents lc,
                                      LuceneQueryStrategy queryStrategy,
                                      LuceneSortStrategy sortStrategy,
                                      LuceneHitMapper<T> hitMapper,
                                      Constraints constraints) {
        this.lc = lc;
        this.queryStrategy = queryStrategy;
        this.sortStrategy = sortStrategy;
        this.hitMapper = hitMapper;
        this.constraints = (constraints == null) ? Constraints.defaults() : constraints;
    }

    @Override
    public SearchResponse<T> search(LuceneSearchRequest req) throws Exception {
        final int page = max(1, Optional.ofNullable(req.getPage()).orElse(constraints.defaultPage()));
        final int size = min(constraints.maxSize(), max(1, Optional.ofNullable(req.getSize()).orElse(constraints.defaultSize())));
        final int start = (page - 1) * size;

        lc.searcherManager.maybeRefresh();
        IndexSearcher searcher = lc.searcherManager.acquire();
        try {
            final Query query = queryStrategy.build(req);
            final Sort sort = sortStrategy.buildSort(req.getSort());

            final int numHits = addExact(start, size);
            final TopDocs top = (sort == null)
                    ? searcher.search(query, numHits)
                    : searcher.search(query, numHits, sort);

            final long total = top.totalHits.value();
            final List<T> items = LucenePageExtractor.page(searcher, top, start, size, hitMapper);

            return new LuceneSearchResponse<>(total, page, size, items);
        } finally {
            lc.searcherManager.release(searcher);
        }
    }
}
