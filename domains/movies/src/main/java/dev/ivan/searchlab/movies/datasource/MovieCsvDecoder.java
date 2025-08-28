package dev.ivan.searchlab.movies.datasource;

import dev.ivan.searchlab.datasource.csv.BaseCsvRowDecoder;
import dev.ivan.searchlab.movies.models.Movie;

import java.util.List;
import java.util.Map;

public final class MovieCsvDecoder extends BaseCsvRowDecoder<Movie> {

    @Override
    public Movie decode(String[] r, Map<String, Integer> i) {
        Movie movie = new Movie();

        String adult = get(r, i, "adult");
        boolean isAdult = bool(adult);
        movie.setAdult(isAdult);

        String belongsToCollection = get(r, i, "belongs_to_collection");
        Map<String, Object> belongToCollectionObj = obj(belongsToCollection);
        movie.setBelongsToCollection(belongToCollectionObj);

        String budget = get(r, i, "budget");
        Long longBudget = longNum(budget);
        movie.setBudget(longBudget);

        String genres = get(r, i, "genres");
        List<Map<String, Object>> arrGenres = arr(genres);
        movie.setGenres(arrGenres);

        String homepage = get(r, i, "homepage");
        movie.setHomepage(homepage);

        String id = get(r, i, "id");
        Long longId = longNum(id);
        movie.setId(longId);

        String imdbId = get(r, i, "imdb_id");
        movie.setImdbId(imdbId);

        String originalLanguage = get(r, i, "original_language");
        movie.setOriginalLanguage(originalLanguage);

        String originalTitle = get(r, i, "original_title");
        movie.setOriginalTitle(originalTitle);

        String overview = get(r, i, "overview");
        movie.setOverview(overview);

        String popularity = get(r, i, "popularity");
        Double doublePopularity = doubleNum(popularity);
        movie.setPopularity(doublePopularity);

        String posterPath = get(r, i, "poster_path");
        movie.setPosterPath(posterPath);

        String productionCompanies = get(r, i, "production_companies");
        List<Map<String, Object>> arrProductionCompanies = arr(productionCompanies);
        movie.setProductionCompanies(arrProductionCompanies);

        String productionCountries = get(r, i, "production_countries");
        List<Map<String, Object>> arrProductionCountries = arr(productionCountries);
        movie.setProductionCountries(arrProductionCountries);

        String releaseDate = get(r, i, "release_date");
        movie.setReleaseDate(releaseDate);

        String revenue = get(r, i, "revenue");
        Long longRevenue = longNum(revenue);
        movie.setRevenue(longRevenue);

        String runtime = get(r, i, "runtime");
        Integer intRunTime = intNum(runtime);
        movie.setRuntime(intRunTime);

        String spokenLanguages = get(r, i, "spoken_languages");
        List<Map<String, Object>> arrSpokenLanguages = arr(spokenLanguages);
        movie.setSpokenLanguages(arrSpokenLanguages);

        String status = get(r, i, "status");
        movie.setStatus(status);

        String tagline = get(r, i, "tagline");
        movie.setTagline(tagline);

        String title = get(r, i, "title");
        movie.setTitle(title);

        String video = get(r, i, "video");
        boolean isVideo = bool(video);
        movie.setVideo(isVideo);

        String voteAverage = get(r, i, "vote_average");
        Double DoubleVoteAverage = doubleNum(voteAverage);
        movie.setVoteAverage(DoubleVoteAverage);

        String voteCount = get(r, i, "vote_count");
        Integer intVoteCount = intNum(voteCount);
        movie.setVoteCount(intVoteCount);
        return movie;
    }
}
