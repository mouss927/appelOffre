package iut.fr.monappeloffre.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import iut.fr.monappeloffre.domain.Project;
import iut.fr.monappeloffre.domain.ProjectActivity;

/**
 * Spring Data JPA repository for the ProjectActivity entity.
 */
@SuppressWarnings("unused")
public interface ProjectActivityRepository extends JpaRepository<ProjectActivity,Long> {
	
}
