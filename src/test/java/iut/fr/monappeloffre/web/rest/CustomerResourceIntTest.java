package iut.fr.monappeloffre.web.rest;

import iut.fr.monappeloffre.MonAppelOffreApp;

import iut.fr.monappeloffre.domain.Customer;
import iut.fr.monappeloffre.repository.CustomerRepository;
import iut.fr.monappeloffre.repository.search.CustomerSearchRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CustomerResource REST controller.
 *
 * @see CustomerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MonAppelOffreApp.class)
public class CustomerResourceIntTest {

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_MAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_REGISTRATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REGISTRATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_STREET_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLEMENT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENT_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE_SITE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE_SITE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY_SITE = "AAAAAAAAAA";
    private static final String UPDATED_CITY_SITE = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_SITE = "AAAAAAAAAA";
    private static final String UPDATED_STREET_SITE = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_NUMBER_SITE = "AAAAAAAAAA";
    private static final String UPDATED_STREET_NUMBER_SITE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLEMENT_STREET_SITE = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENT_STREET_SITE = "BBBBBBBBBB";

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerSearchRepository customerSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomerMockMvc;

    private Customer customer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CustomerResource customerResource = new CustomerResource(customerRepository, customerSearchRepository);
        this.restCustomerMockMvc = MockMvcBuilders.standaloneSetup(customerResource)
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
    public static Customer createEntity(EntityManager em) {
        Customer customer = new Customer()
            .lastName(DEFAULT_LAST_NAME)
            .firstName(DEFAULT_FIRST_NAME)
            .mail(DEFAULT_MAIL)
            .password(DEFAULT_PASSWORD)
            .registrationDate(DEFAULT_REGISTRATION_DATE)
            .postalCode(DEFAULT_POSTAL_CODE)
            .city(DEFAULT_CITY)
            .street(DEFAULT_STREET)
            .streetNumber(DEFAULT_STREET_NUMBER)
            .complementStreet(DEFAULT_COMPLEMENT_STREET)
            .postalCodeSite(DEFAULT_POSTAL_CODE_SITE)
            .citySite(DEFAULT_CITY_SITE)
            .streetSite(DEFAULT_STREET_SITE)
            .streetNumberSite(DEFAULT_STREET_NUMBER_SITE)
            .complementStreetSite(DEFAULT_COMPLEMENT_STREET_SITE);
        return customer;
    }

    @Before
    public void initTest() {
        customerSearchRepository.deleteAll();
        customer = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomer() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // Create the Customer
        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isCreated());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate + 1);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCustomer.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCustomer.getMail()).isEqualTo(DEFAULT_MAIL);
        assertThat(testCustomer.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testCustomer.getRegistrationDate()).isEqualTo(DEFAULT_REGISTRATION_DATE);
        assertThat(testCustomer.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testCustomer.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCustomer.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testCustomer.getStreetNumber()).isEqualTo(DEFAULT_STREET_NUMBER);
        assertThat(testCustomer.getComplementStreet()).isEqualTo(DEFAULT_COMPLEMENT_STREET);
        assertThat(testCustomer.getPostalCodeSite()).isEqualTo(DEFAULT_POSTAL_CODE_SITE);
        assertThat(testCustomer.getCitySite()).isEqualTo(DEFAULT_CITY_SITE);
        assertThat(testCustomer.getStreetSite()).isEqualTo(DEFAULT_STREET_SITE);
        assertThat(testCustomer.getStreetNumberSite()).isEqualTo(DEFAULT_STREET_NUMBER_SITE);
        assertThat(testCustomer.getComplementStreetSite()).isEqualTo(DEFAULT_COMPLEMENT_STREET_SITE);

        // Validate the Customer in Elasticsearch
        Customer customerEs = customerSearchRepository.findOne(testCustomer.getId());
        assertThat(customerEs).isEqualToComparingFieldByField(testCustomer);
    }

    @Test
    @Transactional
    public void createCustomerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // Create the Customer with an existing ID
        customer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCustomers() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList
        restCustomerMockMvc.perform(get("/api/customers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].registrationDate").value(hasItem(DEFAULT_REGISTRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
            .andExpect(jsonPath("$.[*].streetNumber").value(hasItem(DEFAULT_STREET_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].complementStreet").value(hasItem(DEFAULT_COMPLEMENT_STREET.toString())))
            .andExpect(jsonPath("$.[*].postalCodeSite").value(hasItem(DEFAULT_POSTAL_CODE_SITE.toString())))
            .andExpect(jsonPath("$.[*].citySite").value(hasItem(DEFAULT_CITY_SITE.toString())))
            .andExpect(jsonPath("$.[*].streetSite").value(hasItem(DEFAULT_STREET_SITE.toString())))
            .andExpect(jsonPath("$.[*].streetNumberSite").value(hasItem(DEFAULT_STREET_NUMBER_SITE.toString())))
            .andExpect(jsonPath("$.[*].complementStreetSite").value(hasItem(DEFAULT_COMPLEMENT_STREET_SITE.toString())));
    }

    @Test
    @Transactional
    public void getCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", customer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customer.getId().intValue()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.registrationDate").value(DEFAULT_REGISTRATION_DATE.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.streetNumber").value(DEFAULT_STREET_NUMBER.toString()))
            .andExpect(jsonPath("$.complementStreet").value(DEFAULT_COMPLEMENT_STREET.toString()))
            .andExpect(jsonPath("$.postalCodeSite").value(DEFAULT_POSTAL_CODE_SITE.toString()))
            .andExpect(jsonPath("$.citySite").value(DEFAULT_CITY_SITE.toString()))
            .andExpect(jsonPath("$.streetSite").value(DEFAULT_STREET_SITE.toString()))
            .andExpect(jsonPath("$.streetNumberSite").value(DEFAULT_STREET_NUMBER_SITE.toString()))
            .andExpect(jsonPath("$.complementStreetSite").value(DEFAULT_COMPLEMENT_STREET_SITE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomer() throws Exception {
        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);
        customerSearchRepository.save(customer);
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Update the customer
        Customer updatedCustomer = customerRepository.findOne(customer.getId());
        updatedCustomer
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .mail(UPDATED_MAIL)
            .password(UPDATED_PASSWORD)
            .registrationDate(UPDATED_REGISTRATION_DATE)
            .postalCode(UPDATED_POSTAL_CODE)
            .city(UPDATED_CITY)
            .street(UPDATED_STREET)
            .streetNumber(UPDATED_STREET_NUMBER)
            .complementStreet(UPDATED_COMPLEMENT_STREET)
            .postalCodeSite(UPDATED_POSTAL_CODE_SITE)
            .citySite(UPDATED_CITY_SITE)
            .streetSite(UPDATED_STREET_SITE)
            .streetNumberSite(UPDATED_STREET_NUMBER_SITE)
            .complementStreetSite(UPDATED_COMPLEMENT_STREET_SITE);

        restCustomerMockMvc.perform(put("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomer)))
            .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCustomer.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCustomer.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testCustomer.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testCustomer.getRegistrationDate()).isEqualTo(UPDATED_REGISTRATION_DATE);
        assertThat(testCustomer.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testCustomer.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCustomer.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testCustomer.getStreetNumber()).isEqualTo(UPDATED_STREET_NUMBER);
        assertThat(testCustomer.getComplementStreet()).isEqualTo(UPDATED_COMPLEMENT_STREET);
        assertThat(testCustomer.getPostalCodeSite()).isEqualTo(UPDATED_POSTAL_CODE_SITE);
        assertThat(testCustomer.getCitySite()).isEqualTo(UPDATED_CITY_SITE);
        assertThat(testCustomer.getStreetSite()).isEqualTo(UPDATED_STREET_SITE);
        assertThat(testCustomer.getStreetNumberSite()).isEqualTo(UPDATED_STREET_NUMBER_SITE);
        assertThat(testCustomer.getComplementStreetSite()).isEqualTo(UPDATED_COMPLEMENT_STREET_SITE);

        // Validate the Customer in Elasticsearch
        Customer customerEs = customerSearchRepository.findOne(testCustomer.getId());
        assertThat(customerEs).isEqualToComparingFieldByField(testCustomer);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Create the Customer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCustomerMockMvc.perform(put("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isCreated());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);
        customerSearchRepository.save(customer);
        int databaseSizeBeforeDelete = customerRepository.findAll().size();

        // Get the customer
        restCustomerMockMvc.perform(delete("/api/customers/{id}", customer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean customerExistsInEs = customerSearchRepository.exists(customer.getId());
        assertThat(customerExistsInEs).isFalse();

        // Validate the database is empty
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);
        customerSearchRepository.save(customer);

        // Search the customer
        restCustomerMockMvc.perform(get("/api/_search/customers?query=id:" + customer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].registrationDate").value(hasItem(DEFAULT_REGISTRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
            .andExpect(jsonPath("$.[*].streetNumber").value(hasItem(DEFAULT_STREET_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].complementStreet").value(hasItem(DEFAULT_COMPLEMENT_STREET.toString())))
            .andExpect(jsonPath("$.[*].postalCodeSite").value(hasItem(DEFAULT_POSTAL_CODE_SITE.toString())))
            .andExpect(jsonPath("$.[*].citySite").value(hasItem(DEFAULT_CITY_SITE.toString())))
            .andExpect(jsonPath("$.[*].streetSite").value(hasItem(DEFAULT_STREET_SITE.toString())))
            .andExpect(jsonPath("$.[*].streetNumberSite").value(hasItem(DEFAULT_STREET_NUMBER_SITE.toString())))
            .andExpect(jsonPath("$.[*].complementStreetSite").value(hasItem(DEFAULT_COMPLEMENT_STREET_SITE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Customer.class);
        Customer customer1 = new Customer();
        customer1.setId(1L);
        Customer customer2 = new Customer();
        customer2.setId(customer1.getId());
        assertThat(customer1).isEqualTo(customer2);
        customer2.setId(2L);
        assertThat(customer1).isNotEqualTo(customer2);
        customer1.setId(null);
        assertThat(customer1).isNotEqualTo(customer2);
    }
}
