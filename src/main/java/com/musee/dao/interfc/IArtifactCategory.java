package com.musee.dao.interfc;

import java.util.List;

import com.musee.domain.ArtifactCategory;


public interface IArtifactCategory {
	public ArtifactCategory saveArtifactcategory(ArtifactCategory artifactcategory);

	public List<ArtifactCategory> getListArtifactcategory();

	public ArtifactCategory UpdateArtifactcategory(ArtifactCategory artifactcategory);

}
