package iut.fr.monappeloffre.repository.search;

import iut.fr.monappeloffre.domain.ProjectPic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProjectPic entity.
 */
public interface ProjectPicSearchRepository extends ElasticsearchRepository<ProjectPic, Long> {
}
