package dev.ivan.searchlab.lucene.indexing;

import dev.ivan.searchlab.datasource.core.DataSource;
import dev.ivan.searchlab.lucene.utill.LuceneDocumentFlusher;
import dev.ivan.searchlab.core.mapper.AbstractDataModelMapper;
import dev.ivan.searchlab.core.model.AbstractDataModel;
import dev.ivan.searchlab.search.core.Indexer;
import dev.ivan.searchlab.search.core.util.BatchingSink;
import dev.ivan.searchlab.search.core.util.Sink;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;

import java.util.Objects;

public class LuceneIndexer<T extends AbstractDataModel> implements Indexer<T> {

    private final IndexWriter writer;
    private final AbstractDataModelMapper<T, Document> mapper;
    private final int batchSize;

    public LuceneIndexer(IndexWriter writer,
                         AbstractDataModelMapper<T, Document> mapper,
                         int batchSize) {
        this.writer = writer;
        this.mapper = mapper;
        this.batchSize = Math.max(1, batchSize);
    }

    @Override
    public void index(DataSource<T> source) throws Exception {
        try (Sink<Document> sink = new BatchingSink<>(batchSize, new LuceneDocumentFlusher(writer))) {
            source.stream(item -> {
                Document doc = mapper.map(Objects.requireNonNull(item));
                sink.add(doc);
            });
        }
    }
}

