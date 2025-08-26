package dev.ivan.searchlab.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public final class LucenePageExtractor {

    private LucenePageExtractor() {
    }

    /**
     * Slice TopDocs and map each hit using the provided mapper.
     */
    public static <T> List<T> page(IndexSearcher searcher,
                                   TopDocs top,
                                   int start,
                                   int size,
                                   LuceneHitMapper<T> mapper) throws Exception {
        final int end = min(top.scoreDocs.length, start + size);
        final int capacity = max(0, end - start);
        final List<T> out = new ArrayList<>(capacity);

        final var stored = searcher.storedFields();

        for (int i = start; i < end; i++) {
            final ScoreDoc sd = top.scoreDocs[i];
            final Document d = stored.document(sd.doc);
            out.add(mapper.map(d, sd.score));
        }
        return out;
    }
}
