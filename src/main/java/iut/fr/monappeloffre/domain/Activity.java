package iut.fr.monappeloffre.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Activity.
 */
@Entity
@Table(name = "activity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "activity")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "activityProvider")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProviderActivity> providerActivityACTIVITIES = new HashSet<>();

    @OneToMany(mappedBy = "activityProject")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProjectActivity> projectActivityACTIVITIES = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Activity name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ProviderActivity> getProviderActivityACTIVITIES() {
        return providerActivityACTIVITIES;
    }

    public Activity providerActivityACTIVITIES(Set<ProviderActivity> providerActivities) {
        this.providerActivityACTIVITIES = providerActivities;
        return this;
    }

    public Activity addProviderActivityACTIVITY(ProviderActivity providerActivity) {
        this.providerActivityACTIVITIES.add(providerActivity);
        providerActivity.setActivityProvider(this);
        return this;
    }

    public Activity removeProviderActivityACTIVITY(ProviderActivity providerActivity) {
        this.providerActivityACTIVITIES.remove(providerActivity);
        providerActivity.setActivityProvider(null);
        return this;
    }

    public void setProviderActivityACTIVITIES(Set<ProviderActivity> providerActivities) {
        this.providerActivityACTIVITIES = providerActivities;
    }

    public Set<ProjectActivity> getProjectActivityACTIVITIES() {
        return projectActivityACTIVITIES;
    }

    public Activity projectActivityACTIVITIES(Set<ProjectActivity> projectActivities) {
        this.projectActivityACTIVITIES = projectActivities;
        return this;
    }

    public Activity addProjectActivityACTIVITY(ProjectActivity projectActivity) {
        this.projectActivityACTIVITIES.add(projectActivity);
        projectActivity.setActivityProject(this);
        return this;
    }

    public Activity removeProjectActivityACTIVITY(ProjectActivity projectActivity) {
        this.projectActivityACTIVITIES.remove(projectActivity);
        projectActivity.setActivityProject(null);
        return this;
    }

    public void setProjectActivityACTIVITIES(Set<ProjectActivity> projectActivities) {
        this.projectActivityACTIVITIES = projectActivities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Activity activity = (Activity) o;
        if (activity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), activity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Activity{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
