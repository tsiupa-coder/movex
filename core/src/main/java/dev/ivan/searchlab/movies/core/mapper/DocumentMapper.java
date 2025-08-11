package dev.ivan.searchlab.movies.core.mapper;

import dev.ivan.searchlab.movies.core.model.AbstractDocument;
import org.apache.lucene.document.Document;

public interface DocumentMapper<T extends AbstractDocument> {
    Document toDocument(T value);
}