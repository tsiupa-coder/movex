// src/main/java/dev/ivan/searchlab/util/BatchingSink.java
package dev.ivan.searchlab.search.core.util;

import java.util.ArrayList;
import java.util.List;

public final class BatchingSink<U> implements Sink<U> {
    private final int batchSize;
    private final Flusher<U> flusher;
    private final List<U> batch;

    public BatchingSink(int batchSize, Flusher<U> flusher) {
        this.batchSize = Math.max(1, batchSize);
        this.flusher = flusher;
        this.batch = new ArrayList<>(this.batchSize);
    }

    @Override public void add(U u) throws Exception {
        batch.add(u);
        if (batch.size() >= batchSize) {
            flusher.flush(batch);
            batch.clear();
        }
    }

    @Override public void close() throws Exception {
        if (!batch.isEmpty()) {
            flusher.flush(batch);
            batch.clear();
        }
    }
}

