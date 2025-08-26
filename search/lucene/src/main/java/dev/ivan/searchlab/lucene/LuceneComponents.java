package dev.ivan.searchlab.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.SearcherFactory;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.store.MMapDirectory;

import java.io.Closeable;
import java.nio.file.Path;

public final class LuceneComponents implements Closeable {

    public final MMapDirectory dir;
    public final Analyzer analyzer = new StandardAnalyzer();
    public final IndexWriter writer;
    public final SearcherManager searcherManager;

    public LuceneComponents(Path indexPath, int ramMb, boolean create) throws Exception {
        dir = new MMapDirectory(indexPath);
        var iwc = new IndexWriterConfig(analyzer)
                .setOpenMode(create ? IndexWriterConfig.OpenMode.CREATE
                        : IndexWriterConfig.OpenMode.CREATE_OR_APPEND)
                .setRAMBufferSizeMB(ramMb)
                .setUseCompoundFile(false);
        writer = new IndexWriter(dir, iwc);
        searcherManager = new SearcherManager(writer, new SearcherFactory());
    }

    public void commitAndRefresh() throws Exception {
        writer.commit();
        searcherManager.maybeRefresh();
    }

    @Override
    public void close() throws java.io.IOException {
        searcherManager.close();
        writer.close();
        dir.close();
    }
}
