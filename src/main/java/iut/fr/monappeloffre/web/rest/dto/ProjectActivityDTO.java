package iut.fr.monappeloffre.web.rest.dto;

import java.io.Serializable;

public class ProjectActivityDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String title;
	private String description;
	private Long activity;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getActivity() {
		return activity;
	}

	public void setActivity(Long activity) {
		this.activity = activity;
	}

}
