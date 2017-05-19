package iut.fr.monappeloffre.web.rest;

import iut.fr.monappeloffre.MonAppelOffreApp;

import iut.fr.monappeloffre.domain.ProviderEligibility;
import iut.fr.monappeloffre.repository.ProviderEligibilityRepository;
import iut.fr.monappeloffre.repository.search.ProviderEligibilitySearchRepository;
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
 * Test class for the ProviderEligibilityResource REST controller.
 *
 * @see ProviderEligibilityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MonAppelOffreApp.class)
public class ProviderEligibilityResourceIntTest {

    @Autowired
    private ProviderEligibilityRepository providerEligibilityRepository;

    @Autowired
    private ProviderEligibilitySearchRepository providerEligibilitySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProviderEligibilityMockMvc;

    private ProviderEligibility providerEligibility;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProviderEligibilityResource providerEligibilityResource = new ProviderEligibilityResource(providerEligibilityRepository, providerEligibilitySearchRepository);
        this.restProviderEligibilityMockMvc = MockMvcBuilders.standaloneSetup(providerEligibilityResource)
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
    public static ProviderEligibility createEntity(EntityManager em) {
        ProviderEligibility providerEligibility = new ProviderEligibility();
        return providerEligibility;
    }

    @Before
    public void initTest() {
        providerEligibilitySearchRepository.deleteAll();
        providerEligibility = createEntity(em);
    }

    @Test
    @Transactional
    public void createProviderEligibility() throws Exception {
        int databaseSizeBeforeCreate = providerEligibilityRepository.findAll().size();

        // Create the ProviderEligibility
        restProviderEligibilityMockMvc.perform(post("/api/provider-eligibilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerEligibility)))
            .andExpect(status().isCreated());

        // Validate the ProviderEligibility in the database
        List<ProviderEligibility> providerEligibilityList = providerEligibilityRepository.findAll();
        assertThat(providerEligibilityList).hasSize(databaseSizeBeforeCreate + 1);
        ProviderEligibility testProviderEligibility = providerEligibilityList.get(providerEligibilityList.size() - 1);

        // Validate the ProviderEligibility in Elasticsearch
        ProviderEligibility providerEligibilityEs = providerEligibilitySearchRepository.findOne(testProviderEligibility.getId());
        assertThat(providerEligibilityEs).isEqualToComparingFieldByField(testProviderEligibility);
    }

    @Test
    @Transactional
    public void createProviderEligibilityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = providerEligibilityRepository.findAll().size();

        // Create the ProviderEligibility with an existing ID
        providerEligibility.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProviderEligibilityMockMvc.perform(post("/api/provider-eligibilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerEligibility)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProviderEligibility> providerEligibilityList = providerEligibilityRepository.findAll();
        assertThat(providerEligibilityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProviderEligibilities() throws Exception {
        // Initialize the database
        providerEligibilityRepository.saveAndFlush(providerEligibility);

        // Get all the providerEligibilityList
        restProviderEligibilityMockMvc.perform(get("/api/provider-eligibilities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(providerEligibility.getId().intValue())));
    }

    @Test
    @Transactional
    public void getProviderEligibility() throws Exception {
        // Initialize the database
        providerEligibilityRepository.saveAndFlush(providerEligibility);

        // Get the providerEligibility
        restProviderEligibilityMockMvc.perform(get("/api/provider-eligibilities/{id}", providerEligibility.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(providerEligibility.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProviderEligibility() throws Exception {
        // Get the providerEligibility
        restProviderEligibilityMockMvc.perform(get("/api/provider-eligibilities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProviderEligibility() throws Exception {
        // Initialize the database
        providerEligibilityRepository.saveAndFlush(providerEligibility);
        providerEligibilitySearchRepository.save(providerEligibility);
        int databaseSizeBeforeUpdate = providerEligibilityRepository.findAll().size();

        // Update the providerEligibility
        ProviderEligibility updatedProviderEligibility = providerEligibilityRepository.findOne(providerEligibility.getId());

        restProviderEligibilityMockMvc.perform(put("/api/provider-eligibilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProviderEligibility)))
            .andExpect(status().isOk());

        // Validate the ProviderEligibility in the database
        List<ProviderEligibility> providerEligibilityList = providerEligibilityRepository.findAll();
        assertThat(providerEligibilityList).hasSize(databaseSizeBeforeUpdate);
        ProviderEligibility testProviderEligibility = providerEligibilityList.get(providerEligibilityList.size() - 1);

        // Validate the ProviderEligibility in Elasticsearch
        ProviderEligibility providerEligibilityEs = providerEligibilitySearchRepository.findOne(testProviderEligibility.getId());
        assertThat(providerEligibilityEs).isEqualToComparingFieldByField(testProviderEligibility);
    }

    @Test
    @Transactional
    public void updateNonExistingProviderEligibility() throws Exception {
        int databaseSizeBeforeUpdate = providerEligibilityRepository.findAll().size();

        // Create the ProviderEligibility

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProviderEligibilityMockMvc.perform(put("/api/provider-eligibilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerEligibility)))
            .andExpect(status().isCreated());

        // Validate the ProviderEligibility in the database
        List<ProviderEligibility> providerEligibilityList = providerEligibilityRepository.findAll();
        assertThat(providerEligibilityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProviderEligibility() throws Exception {
        // Initialize the database
        providerEligibilityRepository.saveAndFlush(providerEligibility);
        providerEligibilitySearchRepository.save(providerEligibility);
        int databaseSizeBeforeDelete = providerEligibilityRepository.findAll().size();

        // Get the providerEligibility
        restProviderEligibilityMockMvc.perform(delete("/api/provider-eligibilities/{id}", providerEligibility.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean providerEligibilityExistsInEs = providerEligibilitySearchRepository.exists(providerEligibility.getId());
        assertThat(providerEligibilityExistsInEs).isFalse();

        // Validate the database is empty
        List<ProviderEligibility> providerEligibilityList = providerEligibilityRepository.findAll();
        assertThat(providerEligibilityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProviderEligibility() throws Exception {
        // Initialize the database
        providerEligibilityRepository.saveAndFlush(providerEligibility);
        providerEligibilitySearchRepository.save(providerEligibility);

        // Search the providerEligibility
        restProviderEligibilityMockMvc.perform(get("/api/_search/provider-eligibilities?query=id:" + providerEligibility.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(providerEligibility.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProviderEligibility.class);
        ProviderEligibility providerEligibility1 = new ProviderEligibility();
        providerEligibility1.setId(1L);
        ProviderEligibility providerEligibility2 = new ProviderEligibility();
        providerEligibility2.setId(providerEligibility1.getId());
        assertThat(providerEligibility1).isEqualTo(providerEligibility2);
        providerEligibility2.setId(2L);
        assertThat(providerEligibility1).isNotEqualTo(providerEligibility2);
        providerEligibility1.setId(null);
        assertThat(providerEligibility1).isNotEqualTo(providerEligibility2);
    }
}
