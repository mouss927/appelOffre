package iut.fr.monappeloffre.repository.search;

import iut.fr.monappeloffre.domain.ProviderEligibility;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProviderEligibility entity.
 */
public interface ProviderEligibilitySearchRepository extends ElasticsearchRepository<ProviderEligibility, Long> {
}
