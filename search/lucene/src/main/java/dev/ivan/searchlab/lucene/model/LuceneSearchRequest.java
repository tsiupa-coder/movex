package dev.ivan.searchlab.lucene.model;

import dev.ivan.searchlab.search.core.model.SearchRequest;
import java.util.List;

public class LuceneSearchRequest extends SearchRequest {
    private String q;

    private List<String> genre;
    private List<String> country;
    private String lang;
    private String status;
    private Boolean adult;
    private Boolean video;

    private Integer yearFrom;
    private Integer yearTo;
    private Integer runtimeFrom;
     private Integer runtimeTo;
    private Double ratingMin;
    private Integer votesMin;

    private Integer page = 1;
    private Integer size = 10;
    private String sort = "relevance";

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
