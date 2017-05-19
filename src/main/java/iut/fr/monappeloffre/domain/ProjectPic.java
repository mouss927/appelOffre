package iut.fr.monappeloffre.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ProjectPic.
 */
@Entity
@Table(name = "project_pic")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "projectpic")
public class ProjectPic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_link")
    private String link;

    @ManyToOne
    private Project projectPIC;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public ProjectPic link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Project getProjectPIC() {
        return projectPIC;
    }

    public ProjectPic projectPIC(Project project) {
        this.projectPIC = project;
        return this;
    }

    public void setProjectPIC(Project project) {
        this.projectPIC = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProjectPic projectPic = (ProjectPic) o;
        if (projectPic.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projectPic.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProjectPic{" +
            "id=" + getId() +
            ", link='" + getLink() + "'" +
            "}";
    }
}
