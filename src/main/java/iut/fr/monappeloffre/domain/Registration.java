package iut.fr.monappeloffre.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Registration.
 */
@Entity
@Table(name = "registration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "registration")
public class Registration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subcription_date")
    private LocalDate subcriptionDate;

    @ManyToOne
    private Provider providerRE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getSubcriptionDate() {
        return subcriptionDate;
    }

    public Registration subcriptionDate(LocalDate subcriptionDate) {
        this.subcriptionDate = subcriptionDate;
        return this;
    }

    public void setSubcriptionDate(LocalDate subcriptionDate) {
        this.subcriptionDate = subcriptionDate;
    }

    public Provider getProviderRE() {
        return providerRE;
    }

    public Registration providerRE(Provider provider) {
        this.providerRE = provider;
        return this;
    }

    public void setProviderRE(Provider provider) {
        this.providerRE = provider;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Registration registration = (Registration) o;
        if (registration.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), registration.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Registration{" +
            "id=" + getId() +
            ", subcriptionDate='" + getSubcriptionDate() + "'" +
            "}";
    }
}
