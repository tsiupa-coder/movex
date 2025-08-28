package dev.ivan.searchlab.datasource.csv;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import dev.ivan.searchlab.datasource.core.DataSource;
import dev.ivan.searchlab.datasource.core.RowConsumer;
import dev.ivan.searchlab.core.model.AbstractDataModel;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class CsvDataSource<T extends AbstractDataModel> extends DataSource<T> {

    private final Reader reader;
    private final CsvRowDecoder<T> decoder;
    private final CsvParserSettings settings;

    public CsvDataSource(Reader reader, CsvRowDecoder<T> decoder) {
        this(reader, decoder, defaultSettings());
    }

    public CsvDataSource(Reader reader, CsvRowDecoder<T> decoder, CsvParserSettings settings) {
        this.reader = reader;
        this.decoder = decoder;
        this.settings = settings;
    }

    @Override
    public void stream(RowConsumer<T> consumer) throws Exception {
        CsvParser parser = new CsvParser(settings);

        try (reader) {
            parser.beginParsing(reader);

            String[] headers = parser.getContext().headers();
            Map<String, Integer> idx = new HashMap<>();
            if (headers != null) {
                for (int i = 0; i < headers.length; i++) {
                    idx.put(headers[i], i);
                }
            }

            String[] row;
            while ((row = parser.parseNext()) != null) {
                consumer.accept(decoder.decode(row, idx));
            }
        }
    }

    private static CsvParserSettings defaultSettings() {
        var s = new CsvParserSettings();
        s.setHeaderExtractionEnabled(true);
        s.setLineSeparatorDetectionEnabled(true);
        s.setMaxCharsPerColumn(10_000_000);
        return s;
    }
}
