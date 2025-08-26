package dev.ivan.searchlab.movies.datasource;

import dev.ivan.searchlab.datasource.csv.BaseCsvRowDecoder;
import dev.ivan.searchlab.movies.models.Movie;

import java.util.Map;

public final class MovieCsvDecoder extends BaseCsvRowDecoder<Movie> {

    @Override
    public Movie decode(String[] r, Map<String,Integer> i) {
        var m = new Movie();
        m.setAdult(bool(get(r, i, "adult")));
        m.setBelongsToCollection(obj(get(r, i, "belongs_to_collection")));
        m.setBudget(longNum(get(r, i, "budget")));
        m.setGenres(arr(get(r, i, "genres")));
        m.setHomepage(get(r, i, "homepage"));
        m.setId(longNum(get(r, i, "id")));
        m.setImdbId(get(r, i, "imdb_id"));
        m.setOriginalLanguage(get(r, i, "original_language"));
        m.setOriginalTitle(get(r, i, "original_title"));
        m.setOverview(get(r, i, "overview"));
        m.setPopularity(doubleNum(get(r, i, "popularity")));
        m.setPosterPath(get(r, i, "poster_path"));
        m.setProductionCompanies(arr(get(r, i, "production_companies")));
        m.setProductionCountries(arr(get(r, i, "production_countries")));
        m.setReleaseDate(get(r, i, "release_date"));
        m.setRevenue(longNum(get(r, i, "revenue")));
        m.setRuntime(intNum(get(r, i, "runtime")));
        m.setSpokenLanguages(arr(get(r, i, "spoken_languages")));
        m.setStatus(get(r, i, "status"));
        m.setTagline(get(r, i, "tagline"));
        m.setTitle(get(r, i, "title"));
        m.setVideo(bool(get(r, i, "video")));
        m.setVoteAverage(doubleNum(get(r, i, "vote_average")));
        m.setVoteCount(intNum(get(r, i, "vote_count")));
        return m;
    }
}
