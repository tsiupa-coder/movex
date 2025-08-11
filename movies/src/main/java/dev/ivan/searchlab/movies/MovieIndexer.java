package dev.ivan.searchlab.movies;

import org.apache.lucene.document.Document;
import dev.ivan.searchlab.movies.core.LuceneComponents;

import java.io.Reader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

public class MovieIndexer {

    private final int ramMb;

    /** @param ramMb RAM buffer for Lucene (e.g., 1024) */
    public MovieIndexer(int ramMb) { this.ramMb = ramMb; }

    /** Build (or rebuild) an index from a CSV file on disk. */
    public void indexCsv(Path csvPath, Path indexDir, boolean create) throws Exception {
        try (var lc = new LuceneComponents(indexDir, ramMb, create)) {
            indexIntoExistingWriter(csvPath, lc);
            lc.commitAndRefresh();
        }
    }

    /** Same, but from a provided Reader (e.g., uploaded file stream). */
    public void indexCsv(Reader reader, Path indexDir, boolean create) throws Exception {
        try (var lc = new LuceneComponents(indexDir, ramMb, create)) {
            indexIntoExistingWriter(reader, lc);
            lc.commitAndRefresh();
        }
    }

    // ---------- internals ----------
    private void indexIntoExistingWriter(Path csvPath, LuceneComponents lc) throws Exception {
        var mapper = new MovieDocumentMapper();
        var batch = new ArrayList<Document>(1000);

        MovieCsvReader.stream(csvPath, m -> {
            var doc = mapper.toDocument(Objects.requireNonNull(m));
            batch.add(doc);
            if (batch.size() >= 1000) {
                lc.writer.addDocuments(batch);
                batch.clear();
            }
        });
        if (!batch.isEmpty()) lc.writer.addDocuments(batch);
    }

    private void indexIntoExistingWriter(Reader reader, LuceneComponents lc) throws Exception {
        var mapper = new MovieDocumentMapper();
        var batch = new ArrayList<Document>(1000);

        MovieCsvReader.stream(reader, m -> {
            var doc = mapper.toDocument(Objects.requireNonNull(m));
            batch.add(doc);
            if (batch.size() >= 1000) {
                lc.writer.addDocuments(batch);
                batch.clear();
            }
        });
        if (!batch.isEmpty()) lc.writer.addDocuments(batch);
    }
}
