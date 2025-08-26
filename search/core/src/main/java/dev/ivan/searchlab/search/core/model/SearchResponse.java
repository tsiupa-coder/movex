package dev.ivan.searchlab.search.core.model;

import java.util.List;

public abstract class SearchResponse<T extends SearchResult> {

    abstract public Long getTotal();

    abstract public Long getPage();

    abstract public Long getSize();

    abstract public List<T> getItems();
}
