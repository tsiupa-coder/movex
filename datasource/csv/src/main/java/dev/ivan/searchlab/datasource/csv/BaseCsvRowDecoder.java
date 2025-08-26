package dev.ivan.searchlab.datasource.csv;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ivan.searchlab.movies.core.model.AbstractDataModel;

import java.util.List;
import java.util.Map;

public abstract class BaseCsvRowDecoder<T extends AbstractDataModel>
        implements CsvRowDecoder<T> {

    protected static final ObjectMapper M = new ObjectMapper();

    protected String get(String[] r, Map<String, Integer> i, String k) {
        Integer p = i.get(k);
        return (p == null || p < 0 || p >= r.length) ? null : r[p];
    }

    protected boolean bool(String s) {
        return s != null && (s.equalsIgnoreCase("true") || s.equals("1"));
    }

    protected Integer intNum(String s) {
        try {
            return (s == null || s.isBlank()) ? null : (int) Double.parseDouble(s);
        } catch (Exception e) {
            return null;
        }
    }

    protected Long longNum(String s) {
        try {
            return (s == null || s.isBlank()) ? null : (long) Double.parseDouble(s);
        } catch (Exception e) {
            return null;
        }
    }

    protected Double doubleNum(String s) {
        try {
            return (s == null || s.isBlank()) ? null : Double.parseDouble(s);
        } catch (Exception e) {
            return null;
        }
    }

    protected JsonNode parseJson(String s) {
        if (s == null || s.isBlank()) return null;
        try {
            return M.readTree(s);
        } catch (Exception e) {
            try {
                return M.readTree(s.replace('\'', '"'));
            } catch (Exception ignore) {
                return null;
            }
        }
    }

    @SuppressWarnings("unchecked")
    protected Map<String, Object> obj(String s) {
        var n = parseJson(s);
        if (n == null || !n.isObject()) return null;
        return M.convertValue(n, Map.class);
    }

    @SuppressWarnings("unchecked")
    protected List<Map<String, Object>> arr(String s) {
        var n = parseJson(s);
        if (n == null || !n.isArray()) return List.of();
        return M.convertValue(n, List.class);
    }
}
