package iut.fr.monappeloffre.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "project")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "images")
    private byte[] images;

    @Column(name = "images_content_type")
    private String imagesContentType;

    @Column(name = "date_send")
    private LocalDate dateSend;

    @OneToMany(mappedBy = "projectQU")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Quote> quotePRS = new HashSet<>();

    @OneToMany(mappedBy = "projectPR")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProviderEligibility> providerEligibilityPS = new HashSet<>();

    @OneToMany(mappedBy = "projectACTIVITY")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProjectActivity> projectactivityPROJECTS = new HashSet<>();

    @ManyToOne
    private Customer customerP;

    @OneToMany(mappedBy = "projectPIC")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProjectPic> projectpicPROJETS = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Project title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Project description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImages() {
        return images;
    }

    public Project images(byte[] images) {
        this.images = images;
        return this;
    }

    public void setImages(byte[] images) {
        this.images = images;
    }

    public String getImagesContentType() {
        return imagesContentType;
    }

    public Project imagesContentType(String imagesContentType) {
        this.imagesContentType = imagesContentType;
        return this;
    }

    public void setImagesContentType(String imagesContentType) {
        this.imagesContentType = imagesContentType;
    }

    public LocalDate getDateSend() {
        return dateSend;
    }

    public Project dateSend(LocalDate dateSend) {
        this.dateSend = dateSend;
        return this;
    }

    public void setDateSend(LocalDate dateSend) {
        this.dateSend = dateSend;
    }

    public Set<Quote> getQuotePRS() {
        return quotePRS;
    }

    public Project quotePRS(Set<Quote> quotes) {
        this.quotePRS = quotes;
        return this;
    }

    public Project addQuotePR(Quote quote) {
        this.quotePRS.add(quote);
        quote.setProjectQU(this);
        return this;
    }

    public Project removeQuotePR(Quote quote) {
        this.quotePRS.remove(quote);
        quote.setProjectQU(null);
        return this;
    }

    public void setQuotePRS(Set<Quote> quotes) {
        this.quotePRS = quotes;
    }

    public Set<ProviderEligibility> getProviderEligibilityPS() {
        return providerEligibilityPS;
    }

    public Project providerEligibilityPS(Set<ProviderEligibility> providerEligibilities) {
        this.providerEligibilityPS = providerEligibilities;
        return this;
    }

    public Project addProviderEligibilityP(ProviderEligibility providerEligibility) {
        this.providerEligibilityPS.add(providerEligibility);
        providerEligibility.setProjectPR(this);
        return this;
    }

    public Project removeProviderEligibilityP(ProviderEligibility providerEligibility) {
        this.providerEligibilityPS.remove(providerEligibility);
        providerEligibility.setProjectPR(null);
        return this;
    }

    public void setProviderEligibilityPS(Set<ProviderEligibility> providerEligibilities) {
        this.providerEligibilityPS = providerEligibilities;
    }

    public Set<ProjectActivity> getProjectactivityPROJECTS() {
        return projectactivityPROJECTS;
    }

    public Project projectactivityPROJECTS(Set<ProjectActivity> projectActivities) {
        this.projectactivityPROJECTS = projectActivities;
        return this;
    }

    public Project addProjectactivityPROJECT(ProjectActivity projectActivity) {
        this.projectactivityPROJECTS.add(projectActivity);
        projectActivity.setProjectACTIVITY(this);
        return this;
    }

    public Project removeProjectactivityPROJECT(ProjectActivity projectActivity) {
        this.projectactivityPROJECTS.remove(projectActivity);
        projectActivity.setProjectACTIVITY(null);
        return this;
    }

    public void setProjectactivityPROJECTS(Set<ProjectActivity> projectActivities) {
        this.projectactivityPROJECTS = projectActivities;
    }

    public Customer getCustomerP() {
        return customerP;
    }

    public Project customerP(Customer customer) {
        this.customerP = customer;
        return this;
    }

    public void setCustomerP(Customer customer) {
        this.customerP = customer;
    }

    public Set<ProjectPic> getProjectpicPROJETS() {
        return projectpicPROJETS;
    }

    public Project projectpicPROJETS(Set<ProjectPic> projectPics) {
        this.projectpicPROJETS = projectPics;
        return this;
    }

    public Project addProjectpicPROJET(ProjectPic projectPic) {
        this.projectpicPROJETS.add(projectPic);
        projectPic.setProjectPIC(this);
        return this;
    }

    public Project removeProjectpicPROJET(ProjectPic projectPic) {
        this.projectpicPROJETS.remove(projectPic);
        projectPic.setProjectPIC(null);
        return this;
    }

    public void setProjectpicPROJETS(Set<ProjectPic> projectPics) {
        this.projectpicPROJETS = projectPics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Project project = (Project) o;
        if (project.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), project.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", images='" + getImages() + "'" +
            ", imagesContentType='" + imagesContentType + "'" +
            ", dateSend='" + getDateSend() + "'" +
            "}";
    }
}
