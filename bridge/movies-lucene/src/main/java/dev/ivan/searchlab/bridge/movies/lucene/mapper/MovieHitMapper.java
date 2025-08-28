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
    public MovieSearchResult map(Document document, float score) {
        MovieSearchResult movieSearchResult = new MovieSearchResult();

        String id = document.get(ID);
        movieSearchResult.setId(id);

        String title = document.get(TITLE);
        movieSearchResult.setTitle(title);

        movieSearchResult.setScore(score);

        String posterPath = document.get(POSTER_PATH);
        movieSearchResult.setPosterPath(posterPath);

        String homepage = document.get(HOMEPAGE);
        movieSearchResult.setHomepage(homepage);

        return movieSearchResult;
    }
}
