package iut.fr.monappeloffre.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ProviderEligibility.
 */
@Entity
@Table(name = "provider_eligibility")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "providereligibility")
public class ProviderEligibility implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Project projectPR;

    @ManyToOne
    private Provider providerEL;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProjectPR() {
        return projectPR;
    }

    public ProviderEligibility projectPR(Project project) {
        this.projectPR = project;
        return this;
    }

    public void setProjectPR(Project project) {
        this.projectPR = project;
    }

    public Provider getProviderEL() {
        return providerEL;
    }

    public ProviderEligibility providerEL(Provider provider) {
        this.providerEL = provider;
        return this;
    }

    public void setProviderEL(Provider provider) {
        this.providerEL = provider;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProviderEligibility providerEligibility = (ProviderEligibility) o;
        if (providerEligibility.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), providerEligibility.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProviderEligibility{" +
            "id=" + getId() +
            "}";
    }
}
