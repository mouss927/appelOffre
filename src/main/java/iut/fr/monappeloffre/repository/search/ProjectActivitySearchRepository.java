package iut.fr.monappeloffre.repository.search;

import iut.fr.monappeloffre.domain.ProjectActivity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProjectActivity entity.
 */
public interface ProjectActivitySearchRepository extends ElasticsearchRepository<ProjectActivity, Long> {
}
