package dev.ivan.searchlab.bridge.movies.lucene.mapper;

import dev.ivan.searchlab.core.mapper.AbstractDataModelMapper;
import dev.ivan.searchlab.movies.models.Movie;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoublePoint;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.util.BytesRef;

public final class LuceneMovieDocumentMapper extends AbstractDataModelMapper<Movie, Document> {

    // ---- storage toggles ----------------------------------------------------
    private static final boolean STORE_TITLE = true;
    private static final boolean STORE_POSTER_PATH = true;
    private static final boolean STORE_HOMEPAGE = true;
    private static final boolean STORE_OVERVIEW = false; // set true if you need to return it

    // ---- reusable field types ----------------------------------------------
    private static final FieldType TEXT_NO_NORMS = new FieldType(TextField.TYPE_NOT_STORED);
    private static final FieldType KW_NO_NORMS = new FieldType(StringField.TYPE_NOT_STORED);

    static {
        TEXT_NO_NORMS.setOmitNorms(true);
        TEXT_NO_NORMS.freeze();
        KW_NO_NORMS.setOmitNorms(true);
        KW_NO_NORMS.freeze();
    }

    @Override
    public Document map(Movie movie) {
        Document document = new Document();

        // ------------------------ IDs & small stored fields ------------------
        if (movie.getId() != null) {
            Long movieId = movie.getId();
            String movieIdStr = String.valueOf(movieId);
            StringField movieIdStrField = new StringField("id", movieIdStr, Field.Store.YES);
            document.add(movieIdStrField);
        }

        if (movie.getTitle() != null) {
            String movieTitle = movie.getTitle();
            FieldType titleFieldType = STORE_TITLE
                    ? TextField.TYPE_STORED
                    : TEXT_NO_NORMS;
            Field titleField = new Field("title", movieTitle, titleFieldType);
            document.add(titleField);

            BytesRef titleBytesRef = new BytesRef(movieTitle);
            SortedDocValuesField titleSortField = new SortedDocValuesField("title_sort", titleBytesRef);
            document.add(titleSortField);
        }

        if (movie.getPosterPath() != null && STORE_POSTER_PATH) {
            String posterPath = movie.getPosterPath();
            StoredField posterPathField = new StoredField("poster_path", posterPath);
            document.add(posterPathField);
        }

        if (movie.getHomepage() != null && STORE_HOMEPAGE) {
            String homepage = movie.getHomepage();
            StoredField homepageField = new StoredField("homepage", homepage);
            document.add(homepageField);
        }

        // ------------------------ Text content --------------------------------
        if (movie.getOriginalTitle() != null) {
            String originalTitle = movie.getOriginalTitle();
            Field originalTitleField = new Field("original_title", originalTitle, TEXT_NO_NORMS);
            document.add(originalTitleField);
        }

        if (movie.getOverview() != null) {
            String overview = movie.getOverview();
            Field overviewField;
            if (STORE_OVERVIEW) {
                overviewField = new TextField("overview", overview, Field.Store.YES);
            } else {
                overviewField = new Field("overview", overview, TEXT_NO_NORMS);
            }
            document.add(overviewField);
        }

        if (movie.getTagline() != null) {
            String tagline = movie.getTagline();
            Field taglineField = new Field("tagline", tagline, TEXT_NO_NORMS);
            document.add(taglineField);
        }

        if (movie.getOriginalLanguage() != null) {
            String originalLanguage = movie.getOriginalLanguage();
            Field originalLenguegeField = new Field("lang", originalLanguage, KW_NO_NORMS);
            document.add(originalLenguegeField);
        }

        if (movie.getStatus() != null) {
            String movieStatus = movie.getStatus();
            Field moviewStatusField = new Field("status", movieStatus, KW_NO_NORMS);
            document.add(moviewStatusField);
        }

        boolean isAdult = movie.isAdult();
        String adultStr = isAdult ? "T" : "F";
        Field adultField = new Field("adult", adultStr, KW_NO_NORMS);
        document.add(adultField);

        Boolean isVideo = movie.getVideo();
        String isVideoStr = isVideo ? "T" : "F";
        Field videoField = new Field("video", isVideoStr, KW_NO_NORMS);
        document.add(videoField);

        // ------------------------ Nested: countries / genres ------------------
        if (movie.getProductionCountries() != null) {
            for (var c : movie.getProductionCountries()) {
                var iso = c.get("iso_3166_1");
                if (iso != null) {
                    String isoStr = String.valueOf(iso);
                    Field countryField = new Field("country", isoStr, KW_NO_NORMS);
                    document.add(countryField);
                }
            }
        }

        if (movie.getGenres() != null) {
            for (var g : movie.getGenres()) {
                var name = g.get("name");
                if (name != null) {
                    String genreStr = String.valueOf(name);
                    Field genreField = new Field("genre", genreStr, KW_NO_NORMS);
                    document.add(genreField);
                }
            }
        }

        // ------------------------ Nested: spoken languages --------------------
        if (movie.getSpokenLanguages() != null) {
            for (var l : movie.getSpokenLanguages()) {
                var iso = l.get("iso_639_1");
                if (iso != null) {
                    String spokenLangStr = String.valueOf(iso);
                    Field spokenField = new Field("spoken", spokenLangStr, KW_NO_NORMS);
                    document.add(spokenField);
                }
            }
        }

        // ------------------------ Nested: collection & companies --------------
        if (movie.getBelongsToCollection() != null) {
            var collectionName = movie.getBelongsToCollection().get("name");
            if (collectionName != null) {
                String collectionNameStr = String.valueOf(collectionName);
                Field collectionField = new Field("collection", collectionNameStr, KW_NO_NORMS);
                document.add(collectionField);
            }
            var collectionId = movie.getBelongsToCollection().get("id");
            if (collectionId != null) {
                String collectionIdStr = String.valueOf(collectionId);
                Field collectionIdField = new Field("collection_id", collectionIdStr, KW_NO_NORMS);
                document.add(collectionIdField);
            }
        }

        if (movie.getProductionCompanies() != null) {
            for (var pc : movie.getProductionCompanies()) {
                var name = pc.get("name");
                if (name != null) {
                    String companyName = String.valueOf(name);
                    Field companyField = new Field("company", companyName, KW_NO_NORMS);
                    document.add(companyField);
                }
            }
        }

        // ------------------------ Dates → year --------------------------------
        if (movie.getReleaseDate() != null && movie.getReleaseDate().length() >= 4) {
            try {
                String releaseDate = movie.getReleaseDate();
                String yearStr = releaseDate.substring(0, 4);
                int year = Integer.parseInt(yearStr);

                IntPoint yearField = new IntPoint("year", year);
                document.add(yearField);

                NumericDocValuesField yearSortField = new NumericDocValuesField("year_sort", year);
                document.add(yearSortField);
            } catch (NumberFormatException ignore) {

            }
        }

        // ------------------------ Numerics with Points + optional sorts -------
        if (movie.getRuntime() != null) {
            Integer runtime = movie.getRuntime();
            IntPoint runtimeField = new IntPoint("runtime", runtime);
            document.add(runtimeField);
            // document.add(new NumericDocValuesField("runtime_sort", m.getRuntime()));
        }

        if (movie.getVoteAverage() != null) {
            int r = (int) Math.round(movie.getVoteAverage() * 100);
            IntPoint ratingX100 = new IntPoint("rating_x100", r);
            document.add(ratingX100);
            NumericDocValuesField ratingSort = new NumericDocValuesField("rating_sort", r);
            document.add(ratingSort);
        }
        if (movie.getVoteCount() != null) {
            Integer voteCount = movie.getVoteCount();
            IntPoint votesField = new IntPoint("votes", voteCount);
            document.add(votesField);
            // document.add(new NumericDocValuesField("votes_sort", m.getVoteCount()));
        }
        if (movie.getPopularity() != null) {
            Double popularity = movie.getPopularity();
            DoublePoint popularityField = new DoublePoint("popularity", popularity);
            document.add(popularityField);
            // document.add(new DoubleDocValuesField("popularity_sort", m.getPopularity()));
        }
        if (movie.getBudget() != null) {
            Long budget = movie.getBudget();
            LongPoint budgetField = new LongPoint("budget", budget);
            document.add(budgetField);
            // document.add(new NumericDocValuesField("budget_sort", m.getBudget()));
        }
        if (movie.getRevenue() != null) {
            Long revenue = movie.getRevenue();
            LongPoint revenueField = new LongPoint("revenue", revenue);
            document.add(revenueField);
            // document.add(new NumericDocValuesField("revenue_sort", m.getRevenue()));
        }

        // ------------------------ Extra IDs/links ------------------------------
        if (movie.getImdbId() != null) {
            String imdbId = movie.getImdbId();
            Field imdbIdField = new Field("imdb_id", imdbId, KW_NO_NORMS);
            document.add(imdbIdField);
        }

        return document;
    }
}
