package iut.fr.monappeloffre.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;
import iut.fr.monappeloffre.domain.Project;
import iut.fr.monappeloffre.domain.Provider;
import iut.fr.monappeloffre.domain.ProviderActivity;
import iut.fr.monappeloffre.repository.ProjectRepository;
import iut.fr.monappeloffre.repository.ProviderRepository;
import iut.fr.monappeloffre.repository.search.ProjectSearchRepository;
import iut.fr.monappeloffre.web.rest.util.HeaderUtil;

/**
 * REST controller for managing Project.
 */
@RestController
@RequestMapping("/api")
public class ProjectResource {

	private final Logger log = LoggerFactory.getLogger(ProjectResource.class);

	private static final String ENTITY_NAME = "project";

	private final ProjectRepository projectRepository;

	private final ProjectSearchRepository projectSearchRepository;
	
	// Injecter le bean spring providerRepository dans ce controlleur
	@Autowired
	ProviderRepository providerRepository;

	public ProjectResource(ProjectRepository projectRepository, ProjectSearchRepository projectSearchRepository) {
		this.projectRepository = projectRepository;
		this.projectSearchRepository = projectSearchRepository;
	}

	/**
	 * POST /projects : Create a new project.
	 *
	 * @param project
	 *            the project to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new project, or with status 400 (Bad Request) if the project has
	 *         already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/projects")
	@Timed
	public ResponseEntity<Project> createProject(@RequestBody Project project) throws URISyntaxException {
		log.debug("REST request to save Project : {}", project);
		if (project.getId() != null) {
			return ResponseEntity.badRequest().headers(
					HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new project cannot already have an ID"))
					.body(null);
		}
		Project result = projectRepository.save(project);
		projectSearchRepository.save(result);
		return ResponseEntity.created(new URI("/api/projects/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /projects : Updates an existing project.
	 *
	 * @param project
	 *            the project to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         project, or with status 400 (Bad Request) if the project is not
	 *         valid, or with status 500 (Internal Server Error) if the project
	 *         couldnt be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/projects")
	@Timed
	public ResponseEntity<Project> updateProject(@RequestBody Project project) throws URISyntaxException {
		log.debug("REST request to update Project : {}", project);
		if (project.getId() == null) {
			return createProject(project);
		}
		Project result = projectRepository.save(project);
		projectSearchRepository.save(result);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, project.getId().toString()))
				.body(result);
	}

	/**
	 * GET /projects : get all the projects.
	 *
	 * @return the ResponseEntity with status 200 (OK) and the list of projects
	 *         in body
	 */
	@GetMapping("/projects")
	@Timed
	public List<Project> getAllProjects() {
		log.debug("REST request to get all Projects");
		List<Project> projects = projectRepository.findAll();
		return projects;
	}
// return tous les projets
	/**
	 * GET /projects/:id : get the "id" project.
	 *
	 * @param id
	 *            the id of the project to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         project, or with status 404 (Not Found)
	 */
	@GetMapping("/projects/{id}")
	@Timed
	public ResponseEntity<Project> getProject(@PathVariable Long id) {
		log.debug("REST request to get Project : {}", id);
		Project project = projectRepository.findOne(id);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(project));
	}

	/**
	 * DELETE /projects/:id : delete the "id" project.
	 *
	 * @param id
	 *            the id of the project to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/projects/{id}")
	@Timed
	public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
		log.debug("REST request to delete Project : {}", id);
		projectRepository.delete(id);
		projectSearchRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}

	/**
	 * SEARCH /_search/projects?query=:query : search for the project
	 * corresponding to the query.
	 *
	 * @param query
	 *            the query of the project search
	 * @return the result of the search
	 */
	@GetMapping("/_search/projects")
	@Timed
	public List<Project> searchProjects(@RequestParam String query) {
		log.debug("REST request to search Projects for query {}", query);
		return StreamSupport.stream(projectSearchRepository.search(queryStringQuery(query)).spliterator(), false)
				.collect(Collectors.toList());
	}

	@GetMapping("/eligiblePojects")
	@Timed
	@Transactional
	public List<Project> getEligibleProjects() {
		log.debug("REST request to get eligible Projects");
		Provider curr = providerRepository.findOne((long) 33);
		//instancier une liste
		List<Long> activityIds = new ArrayList<>();
		// parcourir les providerActivity 
		for(ProviderActivity providerActivity : curr.getProviderativityPROVIDERS()){
			activityIds.add(providerActivity.getActivityProvider().getId());
		}
		List<Project> projects = projectRepository.findByProjectactivityPROJECTS_ActivityProjectIdIn(activityIds);
		return projects;
	}

}
