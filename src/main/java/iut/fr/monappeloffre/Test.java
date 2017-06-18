package iut.fr.monappeloffre;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import iut.fr.monappeloffre.domain.Project;
import iut.fr.monappeloffre.domain.Provider;
import iut.fr.monappeloffre.domain.ProviderActivity;
import iut.fr.monappeloffre.repository.ProjectRepository;
import iut.fr.monappeloffre.repository.ProviderRepository;

@Component
public class Test implements CommandLineRunner{


	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private ProviderRepository providerRepository;

	@Override
	@Transactional
	public void run(String... arg0) throws Exception {
		try{
			// TODO Auto-generated method stub
			//recuperation du provider
			Provider curr = providerRepository.findOne((long) 33);
			//instancier une liste
			List<Long> activityIds = new ArrayList<>();
			// parcourir les providerActivity 
			for(ProviderActivity providerActivity : curr.getProviderativityPROVIDERS()){
				activityIds.add(providerActivity.getActivityProvider().getId());
			}
			//liste des project par rapport au activité du provider
			List<Project> res = projectRepository.findByProjectactivityPROJECTS_ActivityProjectIdIn(activityIds);}
		catch (Exception ex)
		{}
	}







}

