// src/main/java/dev/ivan/searchlab/lucene/model/_IntrospectionConfig.java
package dev.ivan.searchlab.web.config;

import dev.ivan.searchlab.lucene.model.LuceneSearchRequest;
import io.micronaut.core.annotation.Introspected;

/**
 * Registers Bean Introspection + Serde for LuceneSearchRequest
 * without touching the target class.
 */
@Introspected(
    classes = LuceneSearchRequest.class,
    accessKind = { Introspected.AccessKind.FIELD, Introspected.AccessKind.METHOD }
)
final class _IntrospectionConfig {
    private _IntrospectionConfig() {}
}
