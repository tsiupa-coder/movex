// src/main/java/dev/ivan/searchlab/lucene/flush/LuceneDocumentFlusher.java
package dev.ivan.searchlab.lucene.utill;

import dev.ivan.searchlab.search.core.util.Flusher;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;

import java.io.IOException;
import java.util.List;

public final class LuceneDocumentFlusher implements Flusher<Document> {
    private final IndexWriter writer;

    public LuceneDocumentFlusher(IndexWriter writer) {
        this.writer = writer;
    }

    @Override
    public void flush(List<Document> batch) throws IOException {
        writer.addDocuments(batch);
    }
}
