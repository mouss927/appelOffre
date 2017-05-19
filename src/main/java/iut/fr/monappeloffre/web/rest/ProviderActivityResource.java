package iut.fr.monappeloffre.web.rest;

import com.codahale.metrics.annotation.Timed;
import iut.fr.monappeloffre.domain.ProviderActivity;

import iut.fr.monappeloffre.repository.ProviderActivityRepository;
import iut.fr.monappeloffre.repository.search.ProviderActivitySearchRepository;
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
 * REST controller for managing ProviderActivity.
 */
@RestController
@RequestMapping("/api")
public class ProviderActivityResource {

    private final Logger log = LoggerFactory.getLogger(ProviderActivityResource.class);

    private static final String ENTITY_NAME = "providerActivity";
        
    private final ProviderActivityRepository providerActivityRepository;

    private final ProviderActivitySearchRepository providerActivitySearchRepository;

    public ProviderActivityResource(ProviderActivityRepository providerActivityRepository, ProviderActivitySearchRepository providerActivitySearchRepository) {
        this.providerActivityRepository = providerActivityRepository;
        this.providerActivitySearchRepository = providerActivitySearchRepository;
    }

    /**
     * POST  /provider-activities : Create a new providerActivity.
     *
     * @param providerActivity the providerActivity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new providerActivity, or with status 400 (Bad Request) if the providerActivity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/provider-activities")
    @Timed
    public ResponseEntity<ProviderActivity> createProviderActivity(@RequestBody ProviderActivity providerActivity) throws URISyntaxException {
        log.debug("REST request to save ProviderActivity : {}", providerActivity);
        if (providerActivity.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new providerActivity cannot already have an ID")).body(null);
        }
        ProviderActivity result = providerActivityRepository.save(providerActivity);
        providerActivitySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/provider-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /provider-activities : Updates an existing providerActivity.
     *
     * @param providerActivity the providerActivity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated providerActivity,
     * or with status 400 (Bad Request) if the providerActivity is not valid,
     * or with status 500 (Internal Server Error) if the providerActivity couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/provider-activities")
    @Timed
    public ResponseEntity<ProviderActivity> updateProviderActivity(@RequestBody ProviderActivity providerActivity) throws URISyntaxException {
        log.debug("REST request to update ProviderActivity : {}", providerActivity);
        if (providerActivity.getId() == null) {
            return createProviderActivity(providerActivity);
        }
        ProviderActivity result = providerActivityRepository.save(providerActivity);
        providerActivitySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, providerActivity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /provider-activities : get all the providerActivities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of providerActivities in body
     */
    @GetMapping("/provider-activities")
    @Timed
    public List<ProviderActivity> getAllProviderActivities() {
        log.debug("REST request to get all ProviderActivities");
        List<ProviderActivity> providerActivities = providerActivityRepository.findAll();
        return providerActivities;
    }

    /**
     * GET  /provider-activities/:id : get the "id" providerActivity.
     *
     * @param id the id of the providerActivity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the providerActivity, or with status 404 (Not Found)
     */
    @GetMapping("/provider-activities/{id}")
    @Timed
    public ResponseEntity<ProviderActivity> getProviderActivity(@PathVariable Long id) {
        log.debug("REST request to get ProviderActivity : {}", id);
        ProviderActivity providerActivity = providerActivityRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(providerActivity));
    }

    /**
     * DELETE  /provider-activities/:id : delete the "id" providerActivity.
     *
     * @param id the id of the providerActivity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/provider-activities/{id}")
    @Timed
    public ResponseEntity<Void> deleteProviderActivity(@PathVariable Long id) {
        log.debug("REST request to delete ProviderActivity : {}", id);
        providerActivityRepository.delete(id);
        providerActivitySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/provider-activities?query=:query : search for the providerActivity corresponding
     * to the query.
     *
     * @param query the query of the providerActivity search 
     * @return the result of the search
     */
    @GetMapping("/_search/provider-activities")
    @Timed
    public List<ProviderActivity> searchProviderActivities(@RequestParam String query) {
        log.debug("REST request to search ProviderActivities for query {}", query);
        return StreamSupport
            .stream(providerActivitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
