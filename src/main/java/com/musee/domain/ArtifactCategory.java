package com.musee.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "ArtifactCategory")
@NamedQuery(name = "ArtifactCategory.findAll", query = "SELECT r FROM ArtifactCategory r order by artifactCatid desc")
public class ArtifactCategory extends CommonDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "artifactCatid")
	private int artifactCatid;
	
	@Column(name = "artifactCatName", unique = true)
	private String artifactCatName;
	
	@Column(name = "categoryImage")
	private String categoryImage;

	@Transient
	private String action;

	public int getArtifactCatid() {
		return artifactCatid;
	}

	public void setArtifactCatid(int artifactCatid) {
		this.artifactCatid = artifactCatid;
	}

	public String getArtifactCatName() {
		return artifactCatName;
	}

	public void setArtifactCatName(String artifactCatName) {
		this.artifactCatName = artifactCatName;
	}

	public String getCategoryImage() {
		return categoryImage;
	}

	public void setCategoryImage(String categoryImage) {
		this.categoryImage = categoryImage;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
}
