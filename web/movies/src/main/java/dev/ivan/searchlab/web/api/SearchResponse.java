package dev.ivan.searchlab.web.api;

import java.util.List;
import java.util.Map;

public record SearchResponse(long total, int page, int size, List<Map<String, Object>> items) { }
