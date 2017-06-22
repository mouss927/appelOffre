package iut.fr.monappeloffre.web.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import iut.fr.monappeloffre.domain.Provider;
import iut.fr.monappeloffre.service.ProviderService;
import iut.fr.monappeloffre.web.rest.dto.ProviderActivityDTO;
import iut.fr.monappeloffre.web.rest.util.HeaderUtil;

@RestController
@RequestMapping("/api")
public class FormulaireProviderResource {
	
	private static final Logger log = LoggerFactory.getLogger(FormulaireProviderResource.class);

	private static final String ENTITY_NAME = "provider";
	
	private final ProviderService providerService;
	
	public FormulaireProviderResource(ProviderService providerService) {
		this.providerService = providerService;
	}

	@PostMapping("/formulaireProvider")
	@Timed
	public ResponseEntity<Provider> save(@RequestBody ProviderActivityDTO providerActivityDTO
			) throws URISyntaxException, IOException {
		
		log.debug("REST request to save Provider : {}", providerActivityDTO);

		Provider result = providerService.saveProvider(providerActivityDTO);

		return ResponseEntity.created(new URI("/api/formulaire/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

}
