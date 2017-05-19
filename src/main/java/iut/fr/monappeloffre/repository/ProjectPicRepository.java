package iut.fr.monappeloffre.repository;

import iut.fr.monappeloffre.domain.ProjectPic;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProjectPic entity.
 */
@SuppressWarnings("unused")
public interface ProjectPicRepository extends JpaRepository<ProjectPic,Long> {

}
