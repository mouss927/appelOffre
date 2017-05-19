package iut.fr.monappeloffre.repository;

import iut.fr.monappeloffre.domain.ProjectActivity;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProjectActivity entity.
 */
@SuppressWarnings("unused")
public interface ProjectActivityRepository extends JpaRepository<ProjectActivity,Long> {

}
