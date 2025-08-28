package dev.ivan.searchlab.movies.models;

import dev.ivan.searchlab.core.model.AbstractDataModel;

import java.util.List;
import java.util.Map;

public class Movie extends AbstractDataModel {

    private boolean adult;
    private Map<String, Object> belongsToCollection;
    private Long budget;
    private List<Map<String, Object>> genres;
    private String homepage;
    private Long id;
    private String imdbId;
    private String originalLanguage;
    private String originalTitle;
    private String overview;
    private Double popularity;
    private String posterPath;
    private List<Map<String, Object>> productionCompanies;
    private List<Map<String, Object>> productionCountries;
    private String releaseDate;
    private Long revenue;
    private Integer runtime;
    private List<Map<String, Object>> spokenLanguages;
    private String status;
    private String tagline;
    private String title;
    private Boolean video;
    private Double voteAverage;
    private Integer voteCount;

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

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
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

    public Long getRevenue() {
        return revenue;
    }

    public void setRevenue(Long revenue) {
        this.revenue = revenue;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
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

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }
}
