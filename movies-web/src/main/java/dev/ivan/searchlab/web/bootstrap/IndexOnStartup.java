package dev.ivan.searchlab.web.bootstrap;

import dev.ivan.searchlab.web.config.LuceneIndexConfig;
import dev.ivan.searchlab.movies.MovieIndexer;
import dev.ivan.searchlab.web.runtime.IndexState;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

@Singleton
public class IndexOnStartup implements ApplicationEventListener<ServerStartupEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(IndexOnStartup.class);

    private final LuceneIndexConfig cfg;
    private final IndexState indexState;

    public IndexOnStartup(LuceneIndexConfig cfg, IndexState indexState) {
        this.cfg = cfg;
        this.indexState = indexState;
    }

    @Override
    public void onApplicationEvent(ServerStartupEvent event) {
        if (!cfg.isOnStartup()) {
            LOG.debug("Startup indexing disabled");
            return;
        }
        try {
            indexState.markNotReady();
            LOG.info("Starting indexing: csv={}, indexDir={}, create={}, ramMb={}",
                    cfg.getCsvPath(), cfg.getIndexDir(), cfg.isCreate(), cfg.getRamMb());

            new MovieIndexer(cfg.getRamMb())
                    .indexCsv(Path.of(cfg.getCsvPath()), Path.of(cfg.getIndexDir()), cfg.isCreate());

            indexState.markReady();
            LOG.info("Indexing complete, search is now ready.");
        } catch (Exception e) {
            LOG.error("Indexing failed; search remains unavailable.", e);
            // keep NOT ready
        }
    }
}
