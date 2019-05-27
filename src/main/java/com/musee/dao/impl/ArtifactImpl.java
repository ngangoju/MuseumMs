package com.musee.dao.impl;

import java.util.List;
import java.util.logging.Logger;

import com.musee.dao.generic.AbstractDao;
import com.musee.dao.interfc.IArtifact;
import com.musee.domain.Artifact;

public class ArtifactImpl extends AbstractDao<Long, Artifact> implements IArtifact {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());

	public Artifact saveArtifact(Artifact artifact) {
		return saveIntable(artifact);
	}

	public List<Artifact> getListArtifact() {
		return (List<Artifact>) (Object) getModelList();
	}

	public Artifact gettArtifactById(int artifactId, String primaryKeyclomunName) {
		return (Artifact) getModelById(artifactId, primaryKeyclomunName);
	}

	public Artifact UpdateArtifact(Artifact artifact) {
		return updateIntable(artifact);
	}

	public Artifact getArtifactWithQuery(String[] propertyName, Object[] value, String hqlStatement) {
		try {
			return (Artifact) getModelWithMyHQL(propertyName, value, hqlStatement);
		} catch (Exception ex) {
			LOGGER.info("getArtifactWithQuery  Query error ::::" + ex.getMessage());
		}
		return null;
	}

}
