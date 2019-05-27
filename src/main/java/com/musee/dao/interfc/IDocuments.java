/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.musee.dao.interfc;

import java.util.List;

import com.musee.domain.Documents;

/**
 *
 * @author Ngango
 */
public interface IDocuments {
	public Documents saveDocuments(Documents document);

	public List<Documents> getListDocuments();

	public Documents getDocumentById(int DocId, String primaryKeyclomunName);

	public Documents UpdateDocuments(Documents document);

	public Documents getDocumentWithQuery(final String[] propertyName, final Object[] value, final String hqlStatement);
}