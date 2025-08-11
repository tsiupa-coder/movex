package dev.ivan.searchlab.movies;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.util.BytesRef;
import dev.ivan.searchlab.movies.core.mapper.DocumentMapper;
import dev.ivan.searchlab.movies.models.Movie;

public final class MovieDocumentMapper implements DocumentMapper<Movie> {

    @Override
    public Document toDocument(Movie m) {
        var d = new Document();

        // Stored ID only
        d.add(new StringField("id", String.valueOf(m.getId()), Field.Store.YES));

        // Text (no norms to save space)
        var TXT = new FieldType(TextField.TYPE_NOT_STORED) {{
            setOmitNorms(true);
        }};
        if (m.getTitle() != null) {
            d.add(new Field("title", m.getTitle(), TXT));
            d.add(new SortedDocValuesField("title_sort", new BytesRef(m.getTitle())));
        }
        if (m.getOriginalTitle() != null) d.add(new Field("original_title", m.getOriginalTitle(), TXT));
        if (m.getOverview() != null) d.add(new Field("overview", m.getOverview(), TXT));
        if (m.getTagline() != null) d.add(new Field("tagline", m.getTagline(), TXT));

        // Keywords (filters)
        var KW = new FieldType(StringField.TYPE_NOT_STORED) {{
            setOmitNorms(true);
        }};
        if (m.getOriginalLanguage() != null) d.add(new Field("lang", m.getOriginalLanguage(), KW));
        if (m.getProductionCountries() != null) {
            for (var c : m.getProductionCountries()) {
                Object iso = c.get("iso_3166_1");
                if (iso != null) d.add(new Field("country", String.valueOf(iso), KW));
            }
        }
        if (m.getGenres() != null) {
            for (var g : m.getGenres()) {
                Object name = g.get("name");
                if (name != null) d.add(new Field("genre", String.valueOf(name), KW));
            }
        }

        // Year (from YYYY-MM-DD)
        Integer year = null;
        if (m.getReleaseDate() != null && m.getReleaseDate().length() >= 4) {
            try {
                year = Integer.parseInt(m.getReleaseDate().substring(0, 4));
            } catch (Exception ignore) {
            }
        }
        if (year != null) {
            d.add(new IntPoint("year", year));
            d.add(new NumericDocValuesField("year_sort", year));
        }

        // Numeric ranges/sorts
        if (m.getRuntime() != 0) d.add(new IntPoint("runtime", m.getRuntime()));
        if (m.getVoteAverage() != 0) {
            int r = (int) Math.round(m.getVoteAverage() * 100);
            d.add(new IntPoint("rating", r));
            d.add(new NumericDocValuesField("rating_sort", r));
        }

        return d;
    }
}
