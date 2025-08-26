// src/main/java/dev/ivan/searchlab/util/Flusher.java
package dev.ivan.searchlab.search.core.util;

import java.util.List;

@FunctionalInterface
public interface Flusher<U> {
    void flush(List<U> batch) throws Exception;
}
