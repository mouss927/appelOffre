package iut.fr.monappeloffre.web.rest;

import com.codahale.metrics.annotation.Timed;
import iut.fr.monappeloffre.domain.Quote;

import iut.fr.monappeloffre.repository.QuoteRepository;
import iut.fr.monappeloffre.repository.search.QuoteSearchRepository;
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
 * REST controller for managing Quote.
 */
@RestController
@RequestMapping("/api")
public class QuoteResource {

    private final Logger log = LoggerFactory.getLogger(QuoteResource.class);

    private static final String ENTITY_NAME = "quote";
        
    private final QuoteRepository quoteRepository;

    private final QuoteSearchRepository quoteSearchRepository;

    public QuoteResource(QuoteRepository quoteRepository, QuoteSearchRepository quoteSearchRepository) {
        this.quoteRepository = quoteRepository;
        this.quoteSearchRepository = quoteSearchRepository;
    }

    /**
     * POST  /quotes : Create a new quote.
     *
     * @param quote the quote to create
     * @return the ResponseEntity with status 201 (Created) and with body the new quote, or with status 400 (Bad Request) if the quote has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/quotes")
    @Timed
    public ResponseEntity<Quote> createQuote(@RequestBody Quote quote) throws URISyntaxException {
        log.debug("REST request to save Quote : {}", quote);
        if (quote.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new quote cannot already have an ID")).body(null);
        }
        Quote result = quoteRepository.save(quote);
        quoteSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/quotes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /quotes : Updates an existing quote.
     *
     * @param quote the quote to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated quote,
     * or with status 400 (Bad Request) if the quote is not valid,
     * or with status 500 (Internal Server Error) if the quote couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/quotes")
    @Timed
    public ResponseEntity<Quote> updateQuote(@RequestBody Quote quote) throws URISyntaxException {
        log.debug("REST request to update Quote : {}", quote);
        if (quote.getId() == null) {
            return createQuote(quote);
        }
        Quote result = quoteRepository.save(quote);
        quoteSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, quote.getId().toString()))
            .body(result);
    }

    /**
     * GET  /quotes : get all the quotes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of quotes in body
     */
    @GetMapping("/quotes")
    @Timed
    public List<Quote> getAllQuotes() {
        log.debug("REST request to get all Quotes");
        List<Quote> quotes = quoteRepository.findAll();
        return quotes;
    }

    /**
     * GET  /quotes/:id : get the "id" quote.
     *
     * @param id the id of the quote to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the quote, or with status 404 (Not Found)
     */
    @GetMapping("/quotes/{id}")
    @Timed
    public ResponseEntity<Quote> getQuote(@PathVariable Long id) {
        log.debug("REST request to get Quote : {}", id);
        Quote quote = quoteRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(quote));
    }

    /**
     * DELETE  /quotes/:id : delete the "id" quote.
     *
     * @param id the id of the quote to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/quotes/{id}")
    @Timed
    public ResponseEntity<Void> deleteQuote(@PathVariable Long id) {
        log.debug("REST request to delete Quote : {}", id);
        quoteRepository.delete(id);
        quoteSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/quotes?query=:query : search for the quote corresponding
     * to the query.
     *
     * @param query the query of the quote search 
     * @return the result of the search
     */
    @GetMapping("/_search/quotes")
    @Timed
    public List<Quote> searchQuotes(@RequestParam String query) {
        log.debug("REST request to search Quotes for query {}", query);
        return StreamSupport
            .stream(quoteSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
