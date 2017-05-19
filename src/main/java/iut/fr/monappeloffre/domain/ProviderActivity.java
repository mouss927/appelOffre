package iut.fr.monappeloffre.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ProviderActivity.
 */
@Entity
@Table(name = "provider_activity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "provideractivity")
public class ProviderActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Provider providerProviderativity;

    @ManyToOne
    private Activity activityProvider;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Provider getProviderProviderativity() {
        return providerProviderativity;
    }

    public ProviderActivity providerProviderativity(Provider provider) {
        this.providerProviderativity = provider;
        return this;
    }

    public void setProviderProviderativity(Provider provider) {
        this.providerProviderativity = provider;
    }

    public Activity getActivityProvider() {
        return activityProvider;
    }

    public ProviderActivity activityProvider(Activity activity) {
        this.activityProvider = activity;
        return this;
    }

    public void setActivityProvider(Activity activity) {
        this.activityProvider = activity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProviderActivity providerActivity = (ProviderActivity) o;
        if (providerActivity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), providerActivity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProviderActivity{" +
            "id=" + getId() +
            "}";
    }
}
