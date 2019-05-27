package com.musee.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Artifact")
@NamedQuery(name = "Artifact.findAll", query = "SELECT r FROM Artifact r order by artifactId desc")
public class Artifact extends CommonDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "artifactId")
	private int artifactId;
	
	@Column(name = "artifactName", unique = true)
	private String artifactName;
	
	@Column(name = "artifactImage")
	private String artifactImage;
	
	@ManyToOne
	@JoinColumn(name = "artifactCategory")
	private ArtifactCategory artifactCategory;

	@Column(name = "ManufactDate", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date ManufactDate;

	@Column(name = "quantity")
	private double quantity;

	public int getArtifactId() {
		return artifactId;
	}

	public String getArtifactName() {
		return artifactName;
	}

	public void setArtifactName(String artifactName) {
		this.artifactName = artifactName;
	}

	public String getArtifactImage() {
		return artifactImage;
	}

	public void setArtifactImage(String artifactImage) {
		this.artifactImage = artifactImage;
	}

	public void setArtifactId(int artifactId) {
		this.artifactId = artifactId;
	}

	public ArtifactCategory getArtifactCategory() {
		return artifactCategory;
	}

	public void setArtifactCategory(ArtifactCategory artifactCategory) {
		this.artifactCategory = artifactCategory;
	}

	public Date getManufactDate() {
		return ManufactDate;
	}

	public void setManufactDate(Date manufactDate) {
		ManufactDate = manufactDate;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
}
