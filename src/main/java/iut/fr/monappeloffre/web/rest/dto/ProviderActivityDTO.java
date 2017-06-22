package iut.fr.monappeloffre.web.rest.dto;

import java.io.Serializable;


public class ProviderActivityDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String lastName;
	private String firstName;
	private String companyName;
	private String siret;
	
	private Long activity;
	
	
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setSiret(String siret) {
		this.siret = siret;
	}

	public String getLastName() {
		return lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public String getCompanyName() {
		return companyName;
	}
	
	public String getSiret() {
		return siret;
	}
	
	public Long getActivity() {
		return activity;
	}
	public void setActivity(Long activity) {
		this.activity = activity;
	}

	
	
	
	
	

}
