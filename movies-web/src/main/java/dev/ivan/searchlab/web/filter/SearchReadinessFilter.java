package dev.ivan.searchlab.web.filter;

import dev.ivan.searchlab.web.runtime.IndexState;
import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.http.*;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;

import java.util.Map;

@Singleton
@Filter("/search/**")
public class SearchReadinessFilter implements HttpServerFilter {

    private final IndexState state;

    public SearchReadinessFilter(IndexState state) {
        this.state = state;
    }

    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
        if (!state.isReady()) {
            MutableHttpResponse<?> res = HttpResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(Map.of(
                            "error", "index_not_ready",
                            "message", "Search is unavailable until initial indexing completes."
                    ));
            res.getHeaders().add(HttpHeaders.RETRY_AFTER, "60"); // seconds
            return Publishers.just(res);
        }
        return chain.proceed(request);
    }
}
