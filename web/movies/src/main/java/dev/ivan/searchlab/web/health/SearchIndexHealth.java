package dev.ivan.searchlab.web.health;

import dev.ivan.searchlab.web.runtime.IndexState;
import io.micronaut.health.HealthStatus;
import io.micronaut.management.health.indicator.annotation.Liveness;
import io.micronaut.management.health.indicator.annotation.Readiness;
import io.micronaut.management.health.indicator.HealthIndicator;
import io.micronaut.core.async.publisher.Publishers;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;

import java.util.Map;

@Singleton
@Readiness @Liveness
public class SearchIndexHealth implements HealthIndicator {
    private final IndexState state;
    public SearchIndexHealth(IndexState state) { this.state = state; }

    @Override
    public Publisher<io.micronaut.management.health.indicator.HealthResult> getResult() {
        boolean up = state.isReady();
        return Publishers.just(io.micronaut.management.health.indicator.HealthResult
                .builder("searchIndex", up ? HealthStatus.UP : HealthStatus.DOWN)
                .details(Map.of("ready", up))
                .build());
    }
}
