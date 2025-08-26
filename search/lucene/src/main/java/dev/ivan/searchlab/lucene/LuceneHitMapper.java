package dev.ivan.searchlab.lucene;

import org.apache.lucene.document.Document;

/** Maps a Lucene stored Document (+score) to your domain model. */
@FunctionalInterface
public interface LuceneHitMapper<T> {
    T map(Document doc, float score) throws Exception;
}
