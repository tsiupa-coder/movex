package dev.ivan.searchlab.movies.models;


import dev.ivan.searchlab.movies.core.model.AbstractDocument;

import java.util.List;
import java.util.Map;

public class Movie extends AbstractDocument {

    private boolean adult;
    private Map<String, Object> belongsToCollection;
    private long budget;
    private List<Map<String, Object>> genres;
    private String homepage;
    private long id;
    private String imdbId;
    private String originalLanguage;
    private String originalTitle;
    private String overview;
    private double popularity;
    private String posterPath;
    private List<Map<String, Object>> productionCompanies;
    private List<Map<String, Object>> productionCountries;
    private String releaseDate;
    private long revenue;
    private int runtime;
    private List<Map<String, Object>> spokenLanguages;
    private String status;
    private String tagline;
    private String title;
    private boolean video;
    private double voteAverage;
    private int voteCount;

    // Getters & Setters
    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public Map<String, Object> getBelongsToCollection() {
        return belongsToCollection;
    }

    public void setBelongsToCollection(Map<String, Object> belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public List<Map<String, Object>> getGenres() {
        return genres;
    }

    public void setGenres(List<Map<String, Object>> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public List<Map<String, Object>> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<Map<String, Object>> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public List<Map<String, Object>> getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(List<Map<String, Object>> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public List<Map<String, Object>> getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(List<Map<String, Object>> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}
