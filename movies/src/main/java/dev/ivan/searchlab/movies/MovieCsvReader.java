// movies/src/main/java/org/example/movies/MovieCsvReader.java
package dev.ivan.searchlab.movies;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import dev.ivan.searchlab.movies.models.Movie;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public final class MovieCsvReader {
    public interface RowConsumer { void accept(Movie m) throws Exception; }

    private static final ObjectMapper M = new ObjectMapper();

    // 1) Existing: from file path
    public static void stream(Path csv, RowConsumer consumer) throws Exception {
        try (var br = Files.newBufferedReader(csv)) {
            stream(br, consumer); // delegate to the Reader overload
        }
    }

    // 2) New: from Reader (e.g., upload stream in web)
    public static void stream(Reader reader, RowConsumer consumer) throws Exception {
        var settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        settings.setLineSeparatorDetectionEnabled(true);
        settings.setMaxCharsPerColumn(10_000_000);

        var parser = new CsvParser(settings);
        parser.beginParsing(reader);

        // headers are available when headerExtractionEnabled = true
        String[] headers = parser.getContext().headers();
        Map<String,Integer> idx = new HashMap<>();
        for (int i = 0; i < headers.length; i++) idx.put(headers[i], i);

        String[] row;
        while ((row = parser.parseNext()) != null) {
            consumer.accept(from(row, idx));
        }
    }

    // --- mapping helpers (same as before) ---
    private static Movie from(String[] r, Map<String,Integer> i) {
        var m = new Movie();
        m.setAdult(bool(get(r,i,"adult")));
        m.setBelongsToCollection(obj(get(r,i,"belongs_to_collection")));
        m.setBudget(longNum(get(r,i,"budget")));
        m.setGenres(arr(get(r,i,"genres")));
        m.setHomepage(get(r,i,"homepage"));
        m.setId(longNum(get(r,i,"id")));
        m.setImdbId(get(r,i,"imdb_id"));
        m.setOriginalLanguage(get(r,i,"original_language"));
        m.setOriginalTitle(get(r,i,"original_title"));
        m.setOverview(get(r,i,"overview"));
        m.setPopularity(doubleNum(get(r,i,"popularity")));
        m.setPosterPath(get(r,i,"poster_path"));
        m.setProductionCompanies(arr(get(r,i,"production_companies")));
        m.setProductionCountries(arr(get(r,i,"production_countries")));
        m.setReleaseDate(get(r,i,"release_date"));
        m.setRevenue(longNum(get(r,i,"revenue")));
        m.setRuntime(intNum(get(r,i,"runtime")));
        m.setSpokenLanguages(arr(get(r,i,"spoken_languages")));
        m.setStatus(get(r,i,"status"));
        m.setTagline(get(r,i,"tagline"));
        m.setTitle(get(r,i,"title"));
        m.setVideo(bool(get(r,i,"video")));
        m.setVoteAverage(doubleNum(get(r,i,"vote_average")));
        m.setVoteCount(intNum(get(r,i,"vote_count")));
        return m;
    }

    private static String get(String[] r, Map<String,Integer> i, String k){
        Integer p = i.get(k); return (p==null||p<0||p>=r.length)? null : r[p];
    }
    private static boolean bool(String s){ return s!=null && (s.equalsIgnoreCase("true") || s.equals("1")); }
    private static Integer intNum(String s){ try { return (s==null||s.isBlank())? null : (int)Double.parseDouble(s); } catch(Exception e){ return null; } }
    private static Long longNum(String s){ try { return (s==null||s.isBlank())? null : (long)Double.parseDouble(s); } catch(Exception e){ return null; } }
    private static Double doubleNum(String s){ try { return (s==null||s.isBlank())? null : Double.parseDouble(s); } catch(Exception e){ return null; } }

    private static JsonNode parseJson(String s){
        if (s==null || s.isBlank()) return null;
        try { return M.readTree(s); }
        catch(Exception e){ try { return M.readTree(s.replace('\'','"')); } catch(Exception ignore){ return null; } }
    }
    @SuppressWarnings("unchecked")
    private static Map<String,Object> obj(String s){
        var n = parseJson(s);
        if (n==null || !n.isObject()) return null;
        return M.convertValue(n, Map.class);
    }
    @SuppressWarnings("unchecked")
    private static List<Map<String,Object>> arr(String s){
        var n = parseJson(s);
        if (n==null || !n.isArray()) return List.of();
        return M.convertValue(n, List.class);
    }
}
