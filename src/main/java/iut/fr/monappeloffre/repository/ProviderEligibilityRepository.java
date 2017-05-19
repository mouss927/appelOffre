package iut.fr.monappeloffre.repository;

import iut.fr.monappeloffre.domain.ProviderEligibility;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProviderEligibility entity.
 */
@SuppressWarnings("unused")
public interface ProviderEligibilityRepository extends JpaRepository<ProviderEligibility,Long> {

}
