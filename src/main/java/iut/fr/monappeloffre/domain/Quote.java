package iut.fr.monappeloffre.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Quote.
 */
@Entity
@Table(name = "quote")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "quote")
public class Quote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_file")
    private String file;

    @OneToOne
    @JoinColumn(unique = true)
    private Review reviewQ;

    @ManyToOne
    private Provider providerQ;

    @ManyToOne
    private Project projectQU;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public Quote file(String file) {
        this.file = file;
        return this;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Review getReviewQ() {
        return reviewQ;
    }

    public Quote reviewQ(Review review) {
        this.reviewQ = review;
        return this;
    }

    public void setReviewQ(Review review) {
        this.reviewQ = review;
    }

    public Provider getProviderQ() {
        return providerQ;
    }

    public Quote providerQ(Provider provider) {
        this.providerQ = provider;
        return this;
    }

    public void setProviderQ(Provider provider) {
        this.providerQ = provider;
    }

    public Project getProjectQU() {
        return projectQU;
    }

    public Quote projectQU(Project project) {
        this.projectQU = project;
        return this;
    }

    public void setProjectQU(Project project) {
        this.projectQU = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Quote quote = (Quote) o;
        if (quote.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), quote.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Quote{" +
            "id=" + getId() +
            ", file='" + getFile() + "'" +
            "}";
    }
}
