package dev.ivan.searchlab.lucene.indexing;

import dev.ivan.searchlab.datasource.core.DataSource;
import dev.ivan.searchlab.core.mapper.AbstractDataModelMapper;
import dev.ivan.searchlab.core.model.AbstractDataModel;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;

public abstract class AbstractLuceneIndexer<T extends AbstractDataModel> {

    private final LuceneIndexer<T> delegate;

    protected AbstractLuceneIndexer(IndexWriter writer,
                                    AbstractDataModelMapper<T, Document> mapper,
                                    int batchSize) {
        this.delegate = new LuceneIndexer<>(writer, mapper, batchSize);
    }

    /** Index from any DataSource<T>. No CSV assumptions here. */
    public final void index(DataSource<T> source) throws Exception {
        delegate.index(source);
    }
}
