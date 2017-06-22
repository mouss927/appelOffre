package iut.fr.monappeloffre.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import iut.fr.monappeloffre.domain.Activity;
import iut.fr.monappeloffre.domain.Provider;
import iut.fr.monappeloffre.domain.ProviderActivity;
import iut.fr.monappeloffre.repository.ProviderActivityRepository;
import iut.fr.monappeloffre.repository.ProviderRepository;
import iut.fr.monappeloffre.web.rest.dto.ProviderActivityDTO;

@Component
@Transactional
public class ProviderService {
	
	private final ProviderActivityRepository providerActivityRepository;
	
	private final ProviderRepository providerRepository;
	
	public ProviderService(
			ProviderActivityRepository providerActivityRepository,
			ProviderRepository providerRepository) {
		this.providerActivityRepository = providerActivityRepository;
		this.providerRepository = providerRepository;
	}

	public Provider saveProvider(ProviderActivityDTO providerActivityDTO) {
		Provider provider = new Provider();
		provider.setFirstName(providerActivityDTO.getFirstName());
		provider.setLastName(providerActivityDTO.getLastName());
		provider.setCompanyName(providerActivityDTO.getCompanyName());
		provider.setSiret(providerActivityDTO.getSiret());
		
		Provider result = providerRepository.save(provider);

		if (providerActivityDTO.getActivity() != null && result.getId() != null) {
			ProviderActivity providerActivity = new ProviderActivity();
			providerActivity.setProviderProviderativity(provider);			
			Activity activityDomain = new Activity();
			activityDomain.setId(providerActivityDTO.getActivity());
			providerActivity.setActivityProvider(activityDomain);

			providerActivityRepository.save(providerActivity);
		}
		return result;
	}
	
}
