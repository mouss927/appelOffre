package iut.fr.monappeloffre.web.rest;

import iut.fr.monappeloffre.MonAppelOffreApp;

import iut.fr.monappeloffre.domain.ProjectPic;
import iut.fr.monappeloffre.repository.ProjectPicRepository;
import iut.fr.monappeloffre.repository.search.ProjectPicSearchRepository;
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
 * Test class for the ProjectPicResource REST controller.
 *
 * @see ProjectPicResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MonAppelOffreApp.class)
public class ProjectPicResourceIntTest {

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    @Autowired
    private ProjectPicRepository projectPicRepository;

    @Autowired
    private ProjectPicSearchRepository projectPicSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProjectPicMockMvc;

    private ProjectPic projectPic;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProjectPicResource projectPicResource = new ProjectPicResource(projectPicRepository, projectPicSearchRepository);
        this.restProjectPicMockMvc = MockMvcBuilders.standaloneSetup(projectPicResource)
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
    public static ProjectPic createEntity(EntityManager em) {
        ProjectPic projectPic = new ProjectPic()
            .link(DEFAULT_LINK);
        return projectPic;
    }

    @Before
    public void initTest() {
        projectPicSearchRepository.deleteAll();
        projectPic = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectPic() throws Exception {
        int databaseSizeBeforeCreate = projectPicRepository.findAll().size();

        // Create the ProjectPic
        restProjectPicMockMvc.perform(post("/api/project-pics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectPic)))
            .andExpect(status().isCreated());

        // Validate the ProjectPic in the database
        List<ProjectPic> projectPicList = projectPicRepository.findAll();
        assertThat(projectPicList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectPic testProjectPic = projectPicList.get(projectPicList.size() - 1);
        assertThat(testProjectPic.getLink()).isEqualTo(DEFAULT_LINK);

        // Validate the ProjectPic in Elasticsearch
        ProjectPic projectPicEs = projectPicSearchRepository.findOne(testProjectPic.getId());
        assertThat(projectPicEs).isEqualToComparingFieldByField(testProjectPic);
    }

    @Test
    @Transactional
    public void createProjectPicWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectPicRepository.findAll().size();

        // Create the ProjectPic with an existing ID
        projectPic.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectPicMockMvc.perform(post("/api/project-pics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectPic)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProjectPic> projectPicList = projectPicRepository.findAll();
        assertThat(projectPicList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProjectPics() throws Exception {
        // Initialize the database
        projectPicRepository.saveAndFlush(projectPic);

        // Get all the projectPicList
        restProjectPicMockMvc.perform(get("/api/project-pics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectPic.getId().intValue())))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK.toString())));
    }

    @Test
    @Transactional
    public void getProjectPic() throws Exception {
        // Initialize the database
        projectPicRepository.saveAndFlush(projectPic);

        // Get the projectPic
        restProjectPicMockMvc.perform(get("/api/project-pics/{id}", projectPic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectPic.getId().intValue()))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProjectPic() throws Exception {
        // Get the projectPic
        restProjectPicMockMvc.perform(get("/api/project-pics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectPic() throws Exception {
        // Initialize the database
        projectPicRepository.saveAndFlush(projectPic);
        projectPicSearchRepository.save(projectPic);
        int databaseSizeBeforeUpdate = projectPicRepository.findAll().size();

        // Update the projectPic
        ProjectPic updatedProjectPic = projectPicRepository.findOne(projectPic.getId());
        updatedProjectPic
            .link(UPDATED_LINK);

        restProjectPicMockMvc.perform(put("/api/project-pics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjectPic)))
            .andExpect(status().isOk());

        // Validate the ProjectPic in the database
        List<ProjectPic> projectPicList = projectPicRepository.findAll();
        assertThat(projectPicList).hasSize(databaseSizeBeforeUpdate);
        ProjectPic testProjectPic = projectPicList.get(projectPicList.size() - 1);
        assertThat(testProjectPic.getLink()).isEqualTo(UPDATED_LINK);

        // Validate the ProjectPic in Elasticsearch
        ProjectPic projectPicEs = projectPicSearchRepository.findOne(testProjectPic.getId());
        assertThat(projectPicEs).isEqualToComparingFieldByField(testProjectPic);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectPic() throws Exception {
        int databaseSizeBeforeUpdate = projectPicRepository.findAll().size();

        // Create the ProjectPic

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProjectPicMockMvc.perform(put("/api/project-pics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectPic)))
            .andExpect(status().isCreated());

        // Validate the ProjectPic in the database
        List<ProjectPic> projectPicList = projectPicRepository.findAll();
        assertThat(projectPicList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProjectPic() throws Exception {
        // Initialize the database
        projectPicRepository.saveAndFlush(projectPic);
        projectPicSearchRepository.save(projectPic);
        int databaseSizeBeforeDelete = projectPicRepository.findAll().size();

        // Get the projectPic
        restProjectPicMockMvc.perform(delete("/api/project-pics/{id}", projectPic.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean projectPicExistsInEs = projectPicSearchRepository.exists(projectPic.getId());
        assertThat(projectPicExistsInEs).isFalse();

        // Validate the database is empty
        List<ProjectPic> projectPicList = projectPicRepository.findAll();
        assertThat(projectPicList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProjectPic() throws Exception {
        // Initialize the database
        projectPicRepository.saveAndFlush(projectPic);
        projectPicSearchRepository.save(projectPic);

        // Search the projectPic
        restProjectPicMockMvc.perform(get("/api/_search/project-pics?query=id:" + projectPic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectPic.getId().intValue())))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectPic.class);
        ProjectPic projectPic1 = new ProjectPic();
        projectPic1.setId(1L);
        ProjectPic projectPic2 = new ProjectPic();
        projectPic2.setId(projectPic1.getId());
        assertThat(projectPic1).isEqualTo(projectPic2);
        projectPic2.setId(2L);
        assertThat(projectPic1).isNotEqualTo(projectPic2);
        projectPic1.setId(null);
        assertThat(projectPic1).isNotEqualTo(projectPic2);
    }
}
