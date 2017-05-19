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
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "customer")
public class Customer implements Serializable {

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

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "street_number")
    private String streetNumber;

    @Column(name = "complement_street")
    private String complementStreet;

    @Column(name = "postal_code_site")
    private String postalCodeSite;

    @Column(name = "city_site")
    private String citySite;

    @Column(name = "street_site")
    private String streetSite;

    @Column(name = "street_number_site")
    private String streetNumberSite;

    @Column(name = "complement_street_site")
    private String complementStreetSite;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "customerP")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Project> projectCS = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public Customer lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Customer firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMail() {
        return mail;
    }

    public Customer mail(String mail) {
        this.mail = mail;
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public Customer password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public Customer registrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
        return this;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Customer postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public Customer city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public Customer street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public Customer streetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
        return this;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getComplementStreet() {
        return complementStreet;
    }

    public Customer complementStreet(String complementStreet) {
        this.complementStreet = complementStreet;
        return this;
    }

    public void setComplementStreet(String complementStreet) {
        this.complementStreet = complementStreet;
    }

    public String getPostalCodeSite() {
        return postalCodeSite;
    }

    public Customer postalCodeSite(String postalCodeSite) {
        this.postalCodeSite = postalCodeSite;
        return this;
    }

    public void setPostalCodeSite(String postalCodeSite) {
        this.postalCodeSite = postalCodeSite;
    }

    public String getCitySite() {
        return citySite;
    }

    public Customer citySite(String citySite) {
        this.citySite = citySite;
        return this;
    }

    public void setCitySite(String citySite) {
        this.citySite = citySite;
    }

    public String getStreetSite() {
        return streetSite;
    }

    public Customer streetSite(String streetSite) {
        this.streetSite = streetSite;
        return this;
    }

    public void setStreetSite(String streetSite) {
        this.streetSite = streetSite;
    }

    public String getStreetNumberSite() {
        return streetNumberSite;
    }

    public Customer streetNumberSite(String streetNumberSite) {
        this.streetNumberSite = streetNumberSite;
        return this;
    }

    public void setStreetNumberSite(String streetNumberSite) {
        this.streetNumberSite = streetNumberSite;
    }

    public String getComplementStreetSite() {
        return complementStreetSite;
    }

    public Customer complementStreetSite(String complementStreetSite) {
        this.complementStreetSite = complementStreetSite;
        return this;
    }

    public void setComplementStreetSite(String complementStreetSite) {
        this.complementStreetSite = complementStreetSite;
    }

    public User getUser() {
        return user;
    }

    public Customer user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Project> getProjectCS() {
        return projectCS;
    }

    public Customer projectCS(Set<Project> projects) {
        this.projectCS = projects;
        return this;
    }

    public Customer addProjectC(Project project) {
        this.projectCS.add(project);
        project.setCustomerP(this);
        return this;
    }

    public Customer removeProjectC(Project project) {
        this.projectCS.remove(project);
        project.setCustomerP(null);
        return this;
    }

    public void setProjectCS(Set<Project> projects) {
        this.projectCS = projects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        if (customer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", lastName='" + getLastName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", mail='" + getMail() + "'" +
            ", password='" + getPassword() + "'" +
            ", registrationDate='" + getRegistrationDate() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", city='" + getCity() + "'" +
            ", street='" + getStreet() + "'" +
            ", streetNumber='" + getStreetNumber() + "'" +
            ", complementStreet='" + getComplementStreet() + "'" +
            ", postalCodeSite='" + getPostalCodeSite() + "'" +
            ", citySite='" + getCitySite() + "'" +
            ", streetSite='" + getStreetSite() + "'" +
            ", streetNumberSite='" + getStreetNumberSite() + "'" +
            ", complementStreetSite='" + getComplementStreetSite() + "'" +
            "}";
    }
}
