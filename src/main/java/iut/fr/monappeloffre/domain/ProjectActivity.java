package iut.fr.monappeloffre.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ProjectActivity.
 */
@Entity
@Table(name = "project_activity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "projectactivity")
public class ProjectActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Project projectACTIVITY;

    @ManyToOne
    private Activity activityProject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProjectACTIVITY() {
        return projectACTIVITY;
    }

    public ProjectActivity projectACTIVITY(Project project) {
        this.projectACTIVITY = project;
        return this;
    }

    public void setProjectACTIVITY(Project project) {
        this.projectACTIVITY = project;
    }

    public Activity getActivityProject() {
        return activityProject;
    }

    public ProjectActivity activityProject(Activity activity) {
        this.activityProject = activity;
        return this;
    }

    public void setActivityProject(Activity activity) {
        this.activityProject = activity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProjectActivity projectActivity = (ProjectActivity) o;
        if (projectActivity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projectActivity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProjectActivity{" +
            "id=" + getId() +
            "}";
    }
}
