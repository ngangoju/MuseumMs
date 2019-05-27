package com.musee.dao.impl;

import java.util.List;

import com.musee.dao.generic.AbstractDao;
import com.musee.dao.interfc.IArtifactCategory;
import com.musee.domain.ArtifactCategory;

public class ArtifactCategoryImpl extends AbstractDao<Long, ArtifactCategory> implements IArtifactCategory {

	public ArtifactCategory saveArtifactcategory(ArtifactCategory artifactCategory) {

		return saveIntable(artifactCategory);
	}

	public List<ArtifactCategory> getListArtifactcategory() {

		return (List<ArtifactCategory>) (Object) getModelList();
	}

	public ArtifactCategory getArtifactcategoryById(int artifactcatId, String primaryKeyColumn) {
		return (ArtifactCategory) getModelById(artifactcatId, primaryKeyColumn);

	}

	public ArtifactCategory UpdateArtifactcategory(ArtifactCategory artifactCategory) {

		return updateIntable(artifactCategory);
	}

}
