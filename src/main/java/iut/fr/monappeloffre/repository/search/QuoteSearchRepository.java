package iut.fr.monappeloffre.repository.search;

import iut.fr.monappeloffre.domain.Quote;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Quote entity.
 */
public interface QuoteSearchRepository extends ElasticsearchRepository<Quote, Long> {
}
