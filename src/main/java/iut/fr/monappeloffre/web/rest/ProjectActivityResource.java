package iut.fr.monappeloffre.web.rest;

import com.codahale.metrics.annotation.Timed;
import iut.fr.monappeloffre.domain.ProjectActivity;

import iut.fr.monappeloffre.repository.ProjectActivityRepository;
import iut.fr.monappeloffre.repository.search.ProjectActivitySearchRepository;
import iut.fr.monappeloffre.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ProjectActivity.
 */
@RestController
@RequestMapping("/api")
public class ProjectActivityResource {

    private final Logger log = LoggerFactory.getLogger(ProjectActivityResource.class);

    private static final String ENTITY_NAME = "projectActivity";
        
    private final ProjectActivityRepository projectActivityRepository;

    private final ProjectActivitySearchRepository projectActivitySearchRepository;

    public ProjectActivityResource(ProjectActivityRepository projectActivityRepository, ProjectActivitySearchRepository projectActivitySearchRepository) {
        this.projectActivityRepository = projectActivityRepository;
        this.projectActivitySearchRepository = projectActivitySearchRepository;
    }

    /**
     * POST  /project-activities : Create a new projectActivity.
     *
     * @param projectActivity the projectActivity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new projectActivity, or with status 400 (Bad Request) if the projectActivity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/project-activities")
    @Timed
    public ResponseEntity<ProjectActivity> createProjectActivity(@RequestBody ProjectActivity projectActivity) throws URISyntaxException {
        log.debug("REST request to save ProjectActivity : {}", projectActivity);
        if (projectActivity.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new projectActivity cannot already have an ID")).body(null);
        }
        ProjectActivity result = projectActivityRepository.save(projectActivity);
        projectActivitySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/project-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /project-activities : Updates an existing projectActivity.
     *
     * @param projectActivity the projectActivity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated projectActivity,
     * or with status 400 (Bad Request) if the projectActivity is not valid,
     * or with status 500 (Internal Server Error) if the projectActivity couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/project-activities")
    @Timed
    public ResponseEntity<ProjectActivity> updateProjectActivity(@RequestBody ProjectActivity projectActivity) throws URISyntaxException {
        log.debug("REST request to update ProjectActivity : {}", projectActivity);
        if (projectActivity.getId() == null) {
            return createProjectActivity(projectActivity);
        }
        ProjectActivity result = projectActivityRepository.save(projectActivity);
        projectActivitySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, projectActivity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /project-activities : get all the projectActivities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of projectActivities in body
     */
    @GetMapping("/project-activities")
    @Timed
    public List<ProjectActivity> getAllProjectActivities() {
        log.debug("REST request to get all ProjectActivities");
        List<ProjectActivity> projectActivities = projectActivityRepository.findAll();
        return projectActivities;
    }

    /**
     * GET  /project-activities/:id : get the "id" projectActivity.
     *
     * @param id the id of the projectActivity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the projectActivity, or with status 404 (Not Found)
     */
    @GetMapping("/project-activities/{id}")
    @Timed
    public ResponseEntity<ProjectActivity> getProjectActivity(@PathVariable Long id) {
        log.debug("REST request to get ProjectActivity : {}", id);
        ProjectActivity projectActivity = projectActivityRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(projectActivity));
    }

    /**
     * DELETE  /project-activities/:id : delete the "id" projectActivity.
     *
     * @param id the id of the projectActivity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/project-activities/{id}")
    @Timed
    public ResponseEntity<Void> deleteProjectActivity(@PathVariable Long id) {
        log.debug("REST request to delete ProjectActivity : {}", id);
        projectActivityRepository.delete(id);
        projectActivitySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/project-activities?query=:query : search for the projectActivity corresponding
     * to the query.
     *
     * @param query the query of the projectActivity search 
     * @return the result of the search
     */
    @GetMapping("/_search/project-activities")
    @Timed
    public List<ProjectActivity> searchProjectActivities(@RequestParam String query) {
        log.debug("REST request to search ProjectActivities for query {}", query);
        return StreamSupport
            .stream(projectActivitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
