package dev.ivan.searchlab.bridge.movies.lucene.service;

import dev.ivan.searchlab.bridge.movies.lucene.spec.MovieFields;
import dev.ivan.searchlab.lucene.model.LuceneSearchRequest;
import dev.ivan.searchlab.lucene.utill.LuceneQueryStrategy;
import dev.ivan.searchlab.lucene.utill.contributors.BoolFlagFilterContributor;
import dev.ivan.searchlab.lucene.utill.contributors.FullTextOrMatchAllContributor;
import dev.ivan.searchlab.lucene.utill.contributors.IntRangeFilterContributor;
import dev.ivan.searchlab.lucene.utill.contributors.MinIntFilterContributor;
import dev.ivan.searchlab.lucene.utill.contributors.QueryContributor;
import dev.ivan.searchlab.lucene.utill.contributors.RatingMinX100FilterContributor;
import dev.ivan.searchlab.lucene.utill.contributors.TermFilterContributor;
import dev.ivan.searchlab.lucene.utill.contributors.TermsAnyFilterContributor;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;

import java.util.ArrayList;
import java.util.List;

import static dev.ivan.searchlab.bridge.movies.lucene.spec.MovieFields.*;

public class MovieQueryStrategy implements LuceneQueryStrategy {

    private final Analyzer analyzer;
    private final List<QueryContributor> contributors;

    /** Default constructor (keeps old behavior). */
    public MovieQueryStrategy() {
        this(new StandardAnalyzer(), MovieFields.FULLTEXT_FIELDS);
    }

    /** Preferred constructor for DI/testing. */
    public MovieQueryStrategy(Analyzer analyzer, List<String> fulltextFields) {
        this.analyzer = analyzer;
        this.contributors = buildDefaultContributors(fulltextFields);
    }

    @Override
    public Query build(LuceneSearchRequest r) throws Exception {
        final BooleanQuery.Builder b = new BooleanQuery.Builder();
        for (QueryContributor c : contributors) {
            c.contribute(b, r);
        }
        return b.build();
    }

    private List<QueryContributor> buildDefaultContributors(List<String> fulltextFields) {
        List<QueryContributor> list = new ArrayList<>();

        // MUST: full-text (AND) across multiple fields; match-all if q is blank
        list.add(new FullTextOrMatchAllContributor(fulltextFields, analyzer));

        // Exact (keyword) filters
        list.add(new TermFilterContributor(LANG, LuceneSearchRequest::getLang));
        list.add(new TermFilterContributor(STATUS, LuceneSearchRequest::getStatus));

        // Boolean flags ("T"/"F")
        list.add(new BoolFlagFilterContributor(ADULT, LuceneSearchRequest::getAdult));
        list.add(new BoolFlagFilterContributor(VIDEO, LuceneSearchRequest::getVideo));

        // Multi-valued (ANY)
        list.add(new TermsAnyFilterContributor(GENRE, LuceneSearchRequest::getGenre));
        list.add(new TermsAnyFilterContributor(COUNTRY, LuceneSearchRequest::getCountry));

        // Numeric ranges
        list.add(new IntRangeFilterContributor(YEAR, LuceneSearchRequest::getYearFrom, LuceneSearchRequest::getYearTo));
        list.add(new IntRangeFilterContributor(RUNTIME, LuceneSearchRequest::getRuntimeFrom, LuceneSearchRequest::getRuntimeTo));

        // Derived numeric (rating x100) and mins
        list.add(new RatingMinX100FilterContributor(RATING_X100, LuceneSearchRequest::getRatingMin));
        list.add(new MinIntFilterContributor(VOTES, LuceneSearchRequest::getVotesMin));

        return list;
    }
}
