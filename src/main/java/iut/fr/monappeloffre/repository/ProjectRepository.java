package iut.fr.monappeloffre.repository;

import iut.fr.monappeloffre.domain.Project;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Project entity.
 */
@SuppressWarnings("unused")
public interface ProjectRepository extends JpaRepository<Project,Long> {
	
	//methode permettant de pouvoir recupere les projets par rapport au activité
	public List<Project> findByProjectactivityPROJECTS_ActivityProjectIdIn(List<Long> activityList);


}
