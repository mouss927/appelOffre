package iut.fr.monappeloffre.repository;

import iut.fr.monappeloffre.domain.Provider;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Provider entity.
 */
@SuppressWarnings("unused")
public interface ProviderRepository extends JpaRepository<Provider,Long> {

}
