package iut.fr.monappeloffre.repository.search;

import iut.fr.monappeloffre.domain.Registration;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Registration entity.
 */
public interface RegistrationSearchRepository extends ElasticsearchRepository<Registration, Long> {
}
