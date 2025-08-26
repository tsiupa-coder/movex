package dev.ivan.searchlab.lucene.model;

import dev.ivan.searchlab.search.core.model.SearchResponse;
import dev.ivan.searchlab.search.core.model.SearchResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class LuceneSearchResponse<T extends SearchResult>
        extends SearchResponse<T> {

    private final long total;
    private final long page;
    private final long size;
    private final List<T> items;
    private final Map<String, Object> metadata;

    public LuceneSearchResponse(long total, long page, long size,
                                List<T> items, Map<String, Object> metadata) {
        this.total = total;
        this.page = page;
        this.size = size;
        this.items = List.copyOf(items);
        this.metadata = (metadata == null) ? Map.of() : Map.copyOf(metadata);
    }

    public LuceneSearchResponse(long total, long page, long size, List<T> items) {
        this(total, page, size, items, Map.of());
    }

    public LuceneSearchResponse(long total, long page, long size,
                                List<? extends T> items, Map<String, Object> metadata, boolean normalize) {
        this(total, page, size, new ArrayList<>(items), metadata);
    }

    @Override
    public Long getTotal() {
        return total;
    }

    @Override
    public Long getPage() {
        return page;
    }

    @Override
    public Long getSize() {
        return size;
    }

    @Override
    public List<T> getItems() {
        return items;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }
}