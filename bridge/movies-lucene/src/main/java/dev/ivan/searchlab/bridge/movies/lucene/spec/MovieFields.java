package dev.ivan.searchlab.bridge.movies.lucene.spec;

import java.util.List;
import java.util.Map;

public final class MovieFields {

    private MovieFields() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static final String ID             = "id";
    public static final String TITLE          = "title";
    public static final String TITLE_SORT     = "title_sort";
    public static final String ORIGINAL_TITLE = "original_title";
    public static final String OVERVIEW       = "overview";
    public static final String TAGLINE        = "tagline";
    public static final String LANG           = "lang";
    public static final String STATUS         = "status";
    public static final String ADULT          = "adult";
    public static final String VIDEO          = "video";
    public static final String GENRE          = "genre";
    public static final String COUNTRY        = "country";
    public static final String YEAR           = "year";
    public static final String YEAR_SORT      = "year_sort";
    public static final String RUNTIME        = "runtime";
    public static final String RATING_X100    = "rating_x100";
    public static final String RATING_SORT    = "rating_sort";
    public static final String VOTES          = "votes";
    public static final String POSTER_PATH    = "poster_path";
    public static final String HOMEPAGE       = "homepage";

    public static final String RELEVANCE      = "relevance";
    public static final String RATING         = "rating";

    public static final List<String> FULLTEXT_FIELDS =
        List.of(TITLE, ORIGINAL_TITLE, OVERVIEW, TAGLINE);

    public static final Map<String, String> SORT_FIELDS = Map.of(
        "title",  TITLE_SORT,
        "year",   YEAR_SORT,
        "rating", RATING_SORT
    );
}
