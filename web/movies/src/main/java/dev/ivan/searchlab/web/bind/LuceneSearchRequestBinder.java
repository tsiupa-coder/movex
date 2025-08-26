package dev.ivan.searchlab.web.bind;

//package dev.ivan.searchlab.web.bind;

import dev.ivan.searchlab.lucene.model.LuceneSearchRequest;
import io.micronaut.core.convert.ArgumentConversionContext;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpParameters;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.bind.binders.TypedRequestArgumentBinder;

import jakarta.inject.Singleton;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Singleton
public class LuceneSearchRequestBinder implements TypedRequestArgumentBinder<LuceneSearchRequest> {

    @Override
    public Argument<LuceneSearchRequest> argumentType() {
        return Argument.of(LuceneSearchRequest.class);
    }

    @Override
    public BindingResult<LuceneSearchRequest> bind(ArgumentConversionContext<LuceneSearchRequest> ctx,
                                                   HttpRequest<?> request) {
        HttpParameters p = request.getParameters();
        LuceneSearchRequest r = new LuceneSearchRequest();

        // strings
        setIfPresent(p, "q", r::setQ);
        setIfPresent(p, "lang", r::setLang);
        setIfPresent(p, "status", r::setStatus);
        setIfPresent(p, "sort", r::setSort);

        // booleans
        setIfPresent(p, "adult", Boolean.class, r::setAdult);
        setIfPresent(p, "video", Boolean.class, r::setVideo);

        // ints
        setIfPresent(p, "yearFrom", Integer.class, r::setYearFrom);
        setIfPresent(p, "yearTo", Integer.class, r::setYearTo);
        setIfPresent(p, "runtimeFrom", Integer.class, r::setRuntimeFrom);
        setIfPresent(p, "runtimeTo", Integer.class, r::setRuntimeTo);
        setIfPresent(p, "votesMin", Integer.class, r::setVotesMin);
        setIfPresent(p, "page", Integer.class, r::setPage);
        setIfPresent(p, "size", Integer.class, r::setSize);

        // doubles
        setIfPresent(p, "ratingMin", Double.class, r::setRatingMin);

        // lists: support repeated params & comma-separated
        setIfPresentList(p, "genre", r::setGenre);
        setIfPresentList(p, "country", r::setCountry);

        return () -> Optional.of(r);
    }

    private static void setIfPresent(HttpParameters p, String name, Consumer<String> setter) {
        p.getFirst(name).ifPresent(setter); // Optional<String>
    }

    private static <T> void setIfPresent(HttpParameters p, String name, Class<T> type, Consumer<T> setter) {
        p.getFirst(name, type).ifPresent(setter); // Optional<T> with conversion
    }

    private static void setIfPresentList(HttpParameters p, String name, Consumer<List<String>> setter) {
        List<String> raw = p.getAll(name);
        if (raw == null || raw.isEmpty()) return;

        List<String> values = raw.stream()
                .flatMap(s -> Stream.of(s.split(","))) // split comma-separated too
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        if (!values.isEmpty()) setter.accept(values);
    }
}



