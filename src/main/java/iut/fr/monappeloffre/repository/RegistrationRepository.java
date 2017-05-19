package iut.fr.monappeloffre.repository;

import iut.fr.monappeloffre.domain.Registration;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Registration entity.
 */
@SuppressWarnings("unused")
public interface RegistrationRepository extends JpaRepository<Registration,Long> {

}
