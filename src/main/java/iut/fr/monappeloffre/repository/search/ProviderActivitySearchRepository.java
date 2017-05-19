package iut.fr.monappeloffre.repository.search;

import iut.fr.monappeloffre.domain.ProviderActivity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProviderActivity entity.
 */
public interface ProviderActivitySearchRepository extends ElasticsearchRepository<ProviderActivity, Long> {
}
