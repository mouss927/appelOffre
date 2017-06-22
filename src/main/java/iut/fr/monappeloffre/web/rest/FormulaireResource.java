package iut.fr.monappeloffre.web.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codahale.metrics.annotation.Timed;

import iut.fr.monappeloffre.domain.Activity;
import iut.fr.monappeloffre.domain.Project;
import iut.fr.monappeloffre.domain.ProjectActivity;
import iut.fr.monappeloffre.repository.ProjectActivityRepository;
import iut.fr.monappeloffre.repository.ProjectRepository;
import iut.fr.monappeloffre.web.rest.dto.ProjectActivityDTO;
import iut.fr.monappeloffre.web.rest.util.HeaderUtil;



//project actvity 
@RestController
@RequestMapping("/api")
public class FormulaireResource {

	private final Logger log = LoggerFactory.getLogger(FormulaireResource.class);

	private static final String ENTITY_NAME = "project";

	private final ProjectRepository projectRepository;
	private final ProjectActivityRepository projectActivityRepository;

	public FormulaireResource(ProjectRepository projectRepository,
			ProjectActivityRepository projectActivityRepository) {

		this.projectRepository = projectRepository;
		this.projectActivityRepository = projectActivityRepository;
	}

	@PostMapping("/formulaire")
	@Timed
	public ResponseEntity<Project> save(@ModelAttribute ProjectActivityDTO dto,
			@RequestParam("images") MultipartFile file) throws URISyntaxException, IOException {
		log.debug("REST request to save Provider : {}", dto);

		Project project = new Project();
		project.setTitle(dto.getTitle());
		project.setDescription(dto.getDescription());

		if (file != null) {
			project.setImages(file.getBytes());
		}

		Project result = projectRepository.save(project);

		if (dto.getActivity() != null && result.getId() != null) {
			ProjectActivity projectActivity = new ProjectActivity();
			projectActivity.setProjectACTIVITY(project);
			Activity activityDomain = new Activity();
			activityDomain.setId(dto.getActivity());
			projectActivity.setActivityProject(activityDomain);

			projectActivityRepository.save(projectActivity);
		}

		return ResponseEntity.created(new URI("/api/formulaire/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}
}
