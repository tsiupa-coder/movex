package dev.ivan.searchlab.bridge.movies.lucene.model;

import dev.ivan.searchlab.lucene.model.LuceneSearchResult;

import java.util.Objects;

public class MovieSearchResult extends LuceneSearchResult {

    private String id;
    private String title;
    private float score;
    private String posterPath;
    private String homepage;

    public MovieSearchResult() {
    }

    public MovieSearchResult(String id, String title, float score, String posterPath, String homepage) {
        this.id = id;
        this.title = title;
        this.score = score;
        this.posterPath = posterPath;
        this.homepage = homepage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        MovieSearchResult that = (MovieSearchResult) o;
        return Float.compare(score, that.score) == 0
                && Objects.equals(id, that.id)
                && Objects.equals(title, that.title)
                && Objects.equals(posterPath, that.posterPath)
                && Objects.equals(homepage, that.homepage);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(title);
        result = 31 * result + Float.hashCode(score);
        result = 31 * result + Objects.hashCode(posterPath);
        result = 31 * result + Objects.hashCode(homepage);
        return result;
    }


    @Override
    public String toString() {
        return "MovieSearchResult{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", score=" + score +
                ", posterPath='" + posterPath + '\'' +
                ", homepage='" + homepage + '\'' +
                '}';
    }
}
