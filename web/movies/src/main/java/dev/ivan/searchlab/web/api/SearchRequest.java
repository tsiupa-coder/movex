package dev.ivan.searchlab.web.api;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;

import java.util.List;

@Introspected
public class SearchRequest {
    @Nullable
    private String q;

    @Nullable private List<String> genre;
    @Nullable private List<String> country;
    @Nullable private String lang;
    @Nullable private String status;
    @Nullable private Boolean adult;
    @Nullable private Boolean video;

    @Nullable private Integer yearFrom;
    @Nullable private Integer yearTo;
    @Nullable private Integer runtimeFrom;
    @Nullable private Integer runtimeTo;
    @Nullable private Double ratingMin;
    @Nullable private Integer votesMin;

    @Nullable private Integer page = 1;
    @Nullable private Integer size = 10;
    @Nullable private String sort = "relevance";

    // getters/setters ...
    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public List<String> getCountry() {
        return country;
    }

    public void setCountry(List<String> country) {
        this.country = country;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Integer getYearFrom() {
        return yearFrom;
    }

    public void setYearFrom(Integer yearFrom) {
        this.yearFrom = yearFrom;
    }

    public Integer getYearTo() {
        return yearTo;
    }

    public void setYearTo(Integer yearTo) {
        this.yearTo = yearTo;
    }

    public Integer getRuntimeFrom() {
        return runtimeFrom;
    }

    public void setRuntimeFrom(Integer runtimeFrom) {
        this.runtimeFrom = runtimeFrom;
    }

    public Integer getRuntimeTo() {
        return runtimeTo;
    }

    public void setRuntimeTo(Integer runtimeTo) {
        this.runtimeTo = runtimeTo;
    }

    public Double getRatingMin() {
        return ratingMin;
    }

    public void setRatingMin(Double ratingMin) {
        this.ratingMin = ratingMin;
    }

    public Integer getVotesMin() {
        return votesMin;
    }

    public void setVotesMin(Integer votesMin) {
        this.votesMin = votesMin;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
