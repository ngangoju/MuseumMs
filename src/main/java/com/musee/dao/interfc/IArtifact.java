/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.musee.dao.interfc;

import java.util.List;

import com.musee.domain.Artifact;

/**
 *
 * @author Ngango
 */
public interface IArtifact {
	public Artifact saveArtifact(Artifact artifact);

	public List<Artifact> getListArtifact();

	public Artifact gettArtifactById(int artifactId, String primaryKeyclomunName);

	public Artifact UpdateArtifact(Artifact artifact);

	public Artifact getArtifactWithQuery(final String[] propertyName, final Object[] value, final String hqlStatement);
}
