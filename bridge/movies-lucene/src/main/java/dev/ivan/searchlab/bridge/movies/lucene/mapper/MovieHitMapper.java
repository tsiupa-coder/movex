package dev.ivan.searchlab.bridge.movies.lucene.mapper;

import dev.ivan.searchlab.bridge.movies.lucene.model.MovieSearchResult;
import dev.ivan.searchlab.lucene.LuceneHitMapper;
import org.apache.lucene.document.Document;

import static dev.ivan.searchlab.bridge.movies.lucene.spec.MovieFields.HOMEPAGE;
import static dev.ivan.searchlab.bridge.movies.lucene.spec.MovieFields.ID;
import static dev.ivan.searchlab.bridge.movies.lucene.spec.MovieFields.POSTER_PATH;
import static dev.ivan.searchlab.bridge.movies.lucene.spec.MovieFields.TITLE;

public final class MovieHitMapper implements LuceneHitMapper<MovieSearchResult> {

    @Override
    public MovieSearchResult map(Document d, float score) {
        MovieSearchResult m = new MovieSearchResult();
        m.setId(d.get(ID));
        m.setTitle(d.get(TITLE));
        m.setScore(score);
        m.setPosterPath(d.get(POSTER_PATH));
        m.setHomepage(d.get(HOMEPAGE));
        return m;
    }
}
