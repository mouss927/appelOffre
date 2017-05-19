package iut.fr.monappeloffre.web.rest;

import iut.fr.monappeloffre.MonAppelOffreApp;

import iut.fr.monappeloffre.domain.ProjectActivity;
import iut.fr.monappeloffre.repository.ProjectActivityRepository;
import iut.fr.monappeloffre.repository.search.ProjectActivitySearchRepository;
import iut.fr.monappeloffre.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProjectActivityResource REST controller.
 *
 * @see ProjectActivityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MonAppelOffreApp.class)
public class ProjectActivityResourceIntTest {

    @Autowired
    private ProjectActivityRepository projectActivityRepository;

    @Autowired
    private ProjectActivitySearchRepository projectActivitySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProjectActivityMockMvc;

    private ProjectActivity projectActivity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProjectActivityResource projectActivityResource = new ProjectActivityResource(projectActivityRepository, projectActivitySearchRepository);
        this.restProjectActivityMockMvc = MockMvcBuilders.standaloneSetup(projectActivityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectActivity createEntity(EntityManager em) {
        ProjectActivity projectActivity = new ProjectActivity();
        return projectActivity;
    }

    @Before
    public void initTest() {
        projectActivitySearchRepository.deleteAll();
        projectActivity = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectActivity() throws Exception {
        int databaseSizeBeforeCreate = projectActivityRepository.findAll().size();

        // Create the ProjectActivity
        restProjectActivityMockMvc.perform(post("/api/project-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectActivity)))
            .andExpect(status().isCreated());

        // Validate the ProjectActivity in the database
        List<ProjectActivity> projectActivityList = projectActivityRepository.findAll();
        assertThat(projectActivityList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectActivity testProjectActivity = projectActivityList.get(projectActivityList.size() - 1);

        // Validate the ProjectActivity in Elasticsearch
        ProjectActivity projectActivityEs = projectActivitySearchRepository.findOne(testProjectActivity.getId());
        assertThat(projectActivityEs).isEqualToComparingFieldByField(testProjectActivity);
    }

    @Test
    @Transactional
    public void createProjectActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectActivityRepository.findAll().size();

        // Create the ProjectActivity with an existing ID
        projectActivity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectActivityMockMvc.perform(post("/api/project-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectActivity)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProjectActivity> projectActivityList = projectActivityRepository.findAll();
        assertThat(projectActivityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProjectActivities() throws Exception {
        // Initialize the database
        projectActivityRepository.saveAndFlush(projectActivity);

        // Get all the projectActivityList
        restProjectActivityMockMvc.perform(get("/api/project-activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectActivity.getId().intValue())));
    }

    @Test
    @Transactional
    public void getProjectActivity() throws Exception {
        // Initialize the database
        projectActivityRepository.saveAndFlush(projectActivity);

        // Get the projectActivity
        restProjectActivityMockMvc.perform(get("/api/project-activities/{id}", projectActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectActivity.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProjectActivity() throws Exception {
        // Get the projectActivity
        restProjectActivityMockMvc.perform(get("/api/project-activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectActivity() throws Exception {
        // Initialize the database
        projectActivityRepository.saveAndFlush(projectActivity);
        projectActivitySearchRepository.save(projectActivity);
        int databaseSizeBeforeUpdate = projectActivityRepository.findAll().size();

        // Update the projectActivity
        ProjectActivity updatedProjectActivity = projectActivityRepository.findOne(projectActivity.getId());

        restProjectActivityMockMvc.perform(put("/api/project-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjectActivity)))
            .andExpect(status().isOk());

        // Validate the ProjectActivity in the database
        List<ProjectActivity> projectActivityList = projectActivityRepository.findAll();
        assertThat(projectActivityList).hasSize(databaseSizeBeforeUpdate);
        ProjectActivity testProjectActivity = projectActivityList.get(projectActivityList.size() - 1);

        // Validate the ProjectActivity in Elasticsearch
        ProjectActivity projectActivityEs = projectActivitySearchRepository.findOne(testProjectActivity.getId());
        assertThat(projectActivityEs).isEqualToComparingFieldByField(testProjectActivity);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectActivity() throws Exception {
        int databaseSizeBeforeUpdate = projectActivityRepository.findAll().size();

        // Create the ProjectActivity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProjectActivityMockMvc.perform(put("/api/project-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectActivity)))
            .andExpect(status().isCreated());

        // Validate the ProjectActivity in the database
        List<ProjectActivity> projectActivityList = projectActivityRepository.findAll();
        assertThat(projectActivityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProjectActivity() throws Exception {
        // Initialize the database
        projectActivityRepository.saveAndFlush(projectActivity);
        projectActivitySearchRepository.save(projectActivity);
        int databaseSizeBeforeDelete = projectActivityRepository.findAll().size();

        // Get the projectActivity
        restProjectActivityMockMvc.perform(delete("/api/project-activities/{id}", projectActivity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean projectActivityExistsInEs = projectActivitySearchRepository.exists(projectActivity.getId());
        assertThat(projectActivityExistsInEs).isFalse();

        // Validate the database is empty
        List<ProjectActivity> projectActivityList = projectActivityRepository.findAll();
        assertThat(projectActivityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProjectActivity() throws Exception {
        // Initialize the database
        projectActivityRepository.saveAndFlush(projectActivity);
        projectActivitySearchRepository.save(projectActivity);

        // Search the projectActivity
        restProjectActivityMockMvc.perform(get("/api/_search/project-activities?query=id:" + projectActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectActivity.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectActivity.class);
        ProjectActivity projectActivity1 = new ProjectActivity();
        projectActivity1.setId(1L);
        ProjectActivity projectActivity2 = new ProjectActivity();
        projectActivity2.setId(projectActivity1.getId());
        assertThat(projectActivity1).isEqualTo(projectActivity2);
        projectActivity2.setId(2L);
        assertThat(projectActivity1).isNotEqualTo(projectActivity2);
        projectActivity1.setId(null);
        assertThat(projectActivity1).isNotEqualTo(projectActivity2);
    }
}
