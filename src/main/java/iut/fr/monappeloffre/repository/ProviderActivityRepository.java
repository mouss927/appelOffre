package iut.fr.monappeloffre.repository;

import iut.fr.monappeloffre.domain.Project;
import iut.fr.monappeloffre.domain.ProviderActivity;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProviderActivity entity.
 */
@SuppressWarnings("unused")
public interface ProviderActivityRepository extends JpaRepository<ProviderActivity,Long> {

}
