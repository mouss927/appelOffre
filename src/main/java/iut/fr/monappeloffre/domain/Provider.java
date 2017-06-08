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
 * A Provider.
 */
@Entity
@Table(name = "provider")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "provider")
public class Provider implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "mail")
    private String mail;

    @Column(name = "jhi_password")
    private String password;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "siret")
    private String siret;

    @Column(name = "intervention_zone")
    private String interventionZone;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "providerQ")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Quote> quotePS = new HashSet<>();

    @OneToMany(mappedBy = "providerEL")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProviderEligibility> providerEligibilityPROS = new HashSet<>();

    @OneToMany(mappedBy = "providerProviderativity")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProviderActivity> providerativityPROVIDERS = new HashSet<>();

    @OneToMany(mappedBy = "providerRE")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Registration> registrationPROVIDERS = new HashSet<>();
    
    @Transient
    private Activity activity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public Provider lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Provider firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMail() {
        return mail;
    }

    public Provider mail(String mail) {
        this.mail = mail;
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public Provider password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public Provider registrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
        return this;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Provider companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSiret() {
        return siret;
    }

    public Provider siret(String siret) {
        this.siret = siret;
        return this;
    }

    public void setSiret(String siret) {
        this.siret = siret;
    }

    public String getInterventionZone() {
        return interventionZone;
    }

    public Provider interventionZone(String interventionZone) {
        this.interventionZone = interventionZone;
        return this;
    }

    public void setInterventionZone(String interventionZone) {
        this.interventionZone = interventionZone;
    }

    public User getUser() {
        return user;
    }

    public Provider user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Quote> getQuotePS() {
        return quotePS;
    }

    public Provider quotePS(Set<Quote> quotes) {
        this.quotePS = quotes;
        return this;
    }

    public Provider addQuoteP(Quote quote) {
        this.quotePS.add(quote);
        quote.setProviderQ(this);
        return this;
    }

    public Provider removeQuoteP(Quote quote) {
        this.quotePS.remove(quote);
        quote.setProviderQ(null);
        return this;
    }

    public void setQuotePS(Set<Quote> quotes) {
        this.quotePS = quotes;
    }

    public Set<ProviderEligibility> getProviderEligibilityPROS() {
        return providerEligibilityPROS;
    }

    public Provider providerEligibilityPROS(Set<ProviderEligibility> providerEligibilities) {
        this.providerEligibilityPROS = providerEligibilities;
        return this;
    }

    public Provider addProviderEligibilityPRO(ProviderEligibility providerEligibility) {
        this.providerEligibilityPROS.add(providerEligibility);
        providerEligibility.setProviderEL(this);
        return this;
    }

    public Provider removeProviderEligibilityPRO(ProviderEligibility providerEligibility) {
        this.providerEligibilityPROS.remove(providerEligibility);
        providerEligibility.setProviderEL(null);
        return this;
    }

    public void setProviderEligibilityPROS(Set<ProviderEligibility> providerEligibilities) {
        this.providerEligibilityPROS = providerEligibilities;
    }

    public Set<ProviderActivity> getProviderativityPROVIDERS() {
        return providerativityPROVIDERS;
    }

    public Provider providerativityPROVIDERS(Set<ProviderActivity> providerActivities) {
        this.providerativityPROVIDERS = providerActivities;
        return this;
    }

    public Provider addProviderativityPROVIDER(ProviderActivity providerActivity) {
        this.providerativityPROVIDERS.add(providerActivity);
        providerActivity.setProviderProviderativity(this);
        return this;
    }

    public Provider removeProviderativityPROVIDER(ProviderActivity providerActivity) {
        this.providerativityPROVIDERS.remove(providerActivity);
        providerActivity.setProviderProviderativity(null);
        return this;
    }

    public void setProviderativityPROVIDERS(Set<ProviderActivity> providerActivities) {
        this.providerativityPROVIDERS = providerActivities;
    }

    public Set<Registration> getRegistrationPROVIDERS() {
        return registrationPROVIDERS;
    }

    public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Provider registrationPROVIDERS(Set<Registration> registrations) {
        this.registrationPROVIDERS = registrations;
        return this;
    }

    public Provider addRegistrationPROVIDER(Registration registration) {
        this.registrationPROVIDERS.add(registration);
        registration.setProviderRE(this);
        return this;
    }

    public Provider removeRegistrationPROVIDER(Registration registration) {
        this.registrationPROVIDERS.remove(registration);
        registration.setProviderRE(null);
        return this;
    }

    public void setRegistrationPROVIDERS(Set<Registration> registrations) {
        this.registrationPROVIDERS = registrations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Provider provider = (Provider) o;
        if (provider.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), provider.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Provider{" +
            "id=" + getId() +
            ", lastName='" + getLastName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", mail='" + getMail() + "'" +
            ", password='" + getPassword() + "'" +
            ", registrationDate='" + getRegistrationDate() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", siret='" + getSiret() + "'" +
            ", interventionZone='" + getInterventionZone() + "'" +
            "}";
    }
}
