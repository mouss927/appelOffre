package iut.fr.monappeloffre.web.rest;

import iut.fr.monappeloffre.MonAppelOffreApp;

import iut.fr.monappeloffre.domain.ProviderActivity;
import iut.fr.monappeloffre.repository.ProviderActivityRepository;
import iut.fr.monappeloffre.repository.search.ProviderActivitySearchRepository;
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
 * Test class for the ProviderActivityResource REST controller.
 *
 * @see ProviderActivityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MonAppelOffreApp.class)
public class ProviderActivityResourceIntTest {

    @Autowired
    private ProviderActivityRepository providerActivityRepository;

    @Autowired
    private ProviderActivitySearchRepository providerActivitySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProviderActivityMockMvc;

    private ProviderActivity providerActivity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProviderActivityResource providerActivityResource = new ProviderActivityResource(providerActivityRepository, providerActivitySearchRepository);
        this.restProviderActivityMockMvc = MockMvcBuilders.standaloneSetup(providerActivityResource)
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
    public static ProviderActivity createEntity(EntityManager em) {
        ProviderActivity providerActivity = new ProviderActivity();
        return providerActivity;
    }

    @Before
    public void initTest() {
        providerActivitySearchRepository.deleteAll();
        providerActivity = createEntity(em);
    }

    @Test
    @Transactional
    public void createProviderActivity() throws Exception {
        int databaseSizeBeforeCreate = providerActivityRepository.findAll().size();

        // Create the ProviderActivity
        restProviderActivityMockMvc.perform(post("/api/provider-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerActivity)))
            .andExpect(status().isCreated());

        // Validate the ProviderActivity in the database
        List<ProviderActivity> providerActivityList = providerActivityRepository.findAll();
        assertThat(providerActivityList).hasSize(databaseSizeBeforeCreate + 1);
        ProviderActivity testProviderActivity = providerActivityList.get(providerActivityList.size() - 1);

        // Validate the ProviderActivity in Elasticsearch
        ProviderActivity providerActivityEs = providerActivitySearchRepository.findOne(testProviderActivity.getId());
        assertThat(providerActivityEs).isEqualToComparingFieldByField(testProviderActivity);
    }

    @Test
    @Transactional
    public void createProviderActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = providerActivityRepository.findAll().size();

        // Create the ProviderActivity with an existing ID
        providerActivity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProviderActivityMockMvc.perform(post("/api/provider-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerActivity)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProviderActivity> providerActivityList = providerActivityRepository.findAll();
        assertThat(providerActivityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProviderActivities() throws Exception {
        // Initialize the database
        providerActivityRepository.saveAndFlush(providerActivity);

        // Get all the providerActivityList
        restProviderActivityMockMvc.perform(get("/api/provider-activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(providerActivity.getId().intValue())));
    }

    @Test
    @Transactional
    public void getProviderActivity() throws Exception {
        // Initialize the database
        providerActivityRepository.saveAndFlush(providerActivity);

        // Get the providerActivity
        restProviderActivityMockMvc.perform(get("/api/provider-activities/{id}", providerActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(providerActivity.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProviderActivity() throws Exception {
        // Get the providerActivity
        restProviderActivityMockMvc.perform(get("/api/provider-activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProviderActivity() throws Exception {
        // Initialize the database
        providerActivityRepository.saveAndFlush(providerActivity);
        providerActivitySearchRepository.save(providerActivity);
        int databaseSizeBeforeUpdate = providerActivityRepository.findAll().size();

        // Update the providerActivity
        ProviderActivity updatedProviderActivity = providerActivityRepository.findOne(providerActivity.getId());

        restProviderActivityMockMvc.perform(put("/api/provider-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProviderActivity)))
            .andExpect(status().isOk());

        // Validate the ProviderActivity in the database
        List<ProviderActivity> providerActivityList = providerActivityRepository.findAll();
        assertThat(providerActivityList).hasSize(databaseSizeBeforeUpdate);
        ProviderActivity testProviderActivity = providerActivityList.get(providerActivityList.size() - 1);

        // Validate the ProviderActivity in Elasticsearch
        ProviderActivity providerActivityEs = providerActivitySearchRepository.findOne(testProviderActivity.getId());
        assertThat(providerActivityEs).isEqualToComparingFieldByField(testProviderActivity);
    }

    @Test
    @Transactional
    public void updateNonExistingProviderActivity() throws Exception {
        int databaseSizeBeforeUpdate = providerActivityRepository.findAll().size();

        // Create the ProviderActivity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProviderActivityMockMvc.perform(put("/api/provider-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerActivity)))
            .andExpect(status().isCreated());

        // Validate the ProviderActivity in the database
        List<ProviderActivity> providerActivityList = providerActivityRepository.findAll();
        assertThat(providerActivityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProviderActivity() throws Exception {
        // Initialize the database
        providerActivityRepository.saveAndFlush(providerActivity);
        providerActivitySearchRepository.save(providerActivity);
        int databaseSizeBeforeDelete = providerActivityRepository.findAll().size();

        // Get the providerActivity
        restProviderActivityMockMvc.perform(delete("/api/provider-activities/{id}", providerActivity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean providerActivityExistsInEs = providerActivitySearchRepository.exists(providerActivity.getId());
        assertThat(providerActivityExistsInEs).isFalse();

        // Validate the database is empty
        List<ProviderActivity> providerActivityList = providerActivityRepository.findAll();
        assertThat(providerActivityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProviderActivity() throws Exception {
        // Initialize the database
        providerActivityRepository.saveAndFlush(providerActivity);
        providerActivitySearchRepository.save(providerActivity);

        // Search the providerActivity
        restProviderActivityMockMvc.perform(get("/api/_search/provider-activities?query=id:" + providerActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(providerActivity.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProviderActivity.class);
        ProviderActivity providerActivity1 = new ProviderActivity();
        providerActivity1.setId(1L);
        ProviderActivity providerActivity2 = new ProviderActivity();
        providerActivity2.setId(providerActivity1.getId());
        assertThat(providerActivity1).isEqualTo(providerActivity2);
        providerActivity2.setId(2L);
        assertThat(providerActivity1).isNotEqualTo(providerActivity2);
        providerActivity1.setId(null);
        assertThat(providerActivity1).isNotEqualTo(providerActivity2);
    }
}
