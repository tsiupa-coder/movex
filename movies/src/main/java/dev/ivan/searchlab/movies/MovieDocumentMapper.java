package dev.ivan.searchlab.movies;

import dev.ivan.searchlab.movies.core.mapper.DocumentMapper;
import dev.ivan.searchlab.movies.models.Movie;
import org.apache.lucene.document.*;
import org.apache.lucene.util.BytesRef;

public final class MovieDocumentMapper implements DocumentMapper<Movie> {

    // ---- storage toggles ----------------------------------------------------
    private static final boolean STORE_TITLE       = true;
    private static final boolean STORE_POSTER_PATH = true;
    private static final boolean STORE_HOMEPAGE    = true;
    private static final boolean STORE_OVERVIEW    = false; // set true if you need to return it

    // ---- reusable field types ----------------------------------------------
    private static final FieldType TEXT_NO_NORMS = new FieldType(TextField.TYPE_NOT_STORED);
    private static final FieldType KW_NO_NORMS   = new FieldType(StringField.TYPE_NOT_STORED);
    static {
        TEXT_NO_NORMS.setOmitNorms(true);
        TEXT_NO_NORMS.freeze();
        KW_NO_NORMS.setOmitNorms(true);
        KW_NO_NORMS.freeze();
    }

    @Override
    public Document toDocument(Movie m) {
        var d = new Document();

        // ------------------------ IDs & small stored fields ------------------
        if (m.getId() != null) {
            d.add(new StringField("id", String.valueOf(m.getId()), Field.Store.YES));
        }
        if (m.getTitle() != null) {
            var titleField = new Field("title", m.getTitle(),
                    STORE_TITLE ? TextField.TYPE_STORED : TEXT_NO_NORMS);
            d.add(titleField);
            d.add(new SortedDocValuesField("title_sort", new BytesRef(m.getTitle())));
        }
        if (m.getPosterPath() != null && STORE_POSTER_PATH) {
            d.add(new StoredField("poster_path", m.getPosterPath()));
        }
        if (m.getHomepage() != null && STORE_HOMEPAGE) {
            d.add(new StoredField("homepage", m.getHomepage()));
        }

        // ------------------------ Text content --------------------------------
        if (m.getOriginalTitle() != null) d.add(new Field("original_title", m.getOriginalTitle(), TEXT_NO_NORMS));
        if (m.getOverview() != null)      d.add(STORE_OVERVIEW
                ? new TextField("overview", m.getOverview(), Field.Store.YES)
                : new Field("overview", m.getOverview(), TEXT_NO_NORMS));
        if (m.getTagline() != null)       d.add(new Field("tagline", m.getTagline(), TEXT_NO_NORMS));

        // ------------------------ Simple keywords/flags -----------------------
        if (m.getOriginalLanguage() != null) d.add(new Field("lang", m.getOriginalLanguage(), KW_NO_NORMS));
        if (m.getStatus() != null)           d.add(new Field("status", m.getStatus(), KW_NO_NORMS));

       d.add(new Field("adult", m.isAdult() ? "T" : "F", KW_NO_NORMS));
        d.add(new Field("video", m.getVideo() ? "T" : "F", KW_NO_NORMS));

        // ------------------------ Nested: countries / genres ------------------
        if (m.getProductionCountries() != null) {
            for (var c : m.getProductionCountries()) {
                var iso = c.get("iso_3166_1");
                if (iso != null) d.add(new Field("country", String.valueOf(iso), KW_NO_NORMS));
            }
        }
        if (m.getGenres() != null) {
            for (var g : m.getGenres()) {
                var name = g.get("name");
                if (name != null) d.add(new Field("genre", String.valueOf(name), KW_NO_NORMS));
            }
        }

        // ------------------------ Nested: spoken languages --------------------
        if (m.getSpokenLanguages() != null) {
            for (var l : m.getSpokenLanguages()) {
                var iso = l.get("iso_639_1");
                if (iso != null) d.add(new Field("spoken", String.valueOf(iso), KW_NO_NORMS));
            }
        }

        // ------------------------ Nested: collection & companies --------------
        if (m.getBelongsToCollection() != null) {
            var cn = m.getBelongsToCollection().get("name");
            var cid = m.getBelongsToCollection().get("id");
            if (cn != null)  d.add(new Field("collection", String.valueOf(cn), KW_NO_NORMS));
            if (cid != null) d.add(new Field("collection_id", String.valueOf(cid), KW_NO_NORMS));
        }
        if (m.getProductionCompanies() != null) {
            for (var pc : m.getProductionCompanies()) {
                var name = pc.get("name");
                if (name != null) d.add(new Field("company", String.valueOf(name), KW_NO_NORMS));
            }
        }

        // ------------------------ Dates → year --------------------------------
        if (m.getReleaseDate() != null && m.getReleaseDate().length() >= 4) {
            try {
                int year = Integer.parseInt(m.getReleaseDate().substring(0, 4));
                d.add(new IntPoint("year", year));
                d.add(new NumericDocValuesField("year_sort", year));
            } catch (NumberFormatException ignore) {}
        }

        // ------------------------ Numerics with Points + optional sorts -------
        if (m.getRuntime() != null) {
            d.add(new IntPoint("runtime", m.getRuntime()));
            // d.add(new NumericDocValuesField("runtime_sort", m.getRuntime()));
        }
        if (m.getVoteAverage() != null) {
            int r = (int) Math.round(m.getVoteAverage() * 100);
            d.add(new IntPoint("rating_x100", r));
            d.add(new NumericDocValuesField("rating_sort", r));
        }
        if (m.getVoteCount() != null) {
            d.add(new IntPoint("votes", m.getVoteCount()));
            // d.add(new NumericDocValuesField("votes_sort", m.getVoteCount()));
        }
        if (m.getPopularity() != null) {
            d.add(new DoublePoint("popularity", m.getPopularity()));
            // d.add(new DoubleDocValuesField("popularity_sort", m.getPopularity()));
        }
        if (m.getBudget() != null) {
            d.add(new LongPoint("budget", m.getBudget()));
            // d.add(new NumericDocValuesField("budget_sort", m.getBudget()));
        }
        if (m.getRevenue() != null) {
            d.add(new LongPoint("revenue", m.getRevenue()));
            // d.add(new NumericDocValuesField("revenue_sort", m.getRevenue()));
        }

        // ------------------------ Extra IDs/links ------------------------------
        if (m.getImdbId() != null) d.add(new Field("imdb_id", m.getImdbId(), KW_NO_NORMS));

        return d;
    }
}
