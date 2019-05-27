package com.musee.dao.impl;
/**
 * author Ngango
 * */

import java.util.List;

import org.hibernate.HibernateException;

import com.musee.dao.generic.AbstractDao;
import com.musee.dao.interfc.IContact;
import com.musee.domain.Contact;


public class ContactImpl extends AbstractDao<Long, Contact> implements IContact {

	public List<Contact> getListContacts() {
		return (List<Contact>) (Object) getModelList();
	}

	public Contact getContactById(int contactId, String primaryKeyclomunName) {
		return (Contact) getModelById(contactId, primaryKeyclomunName);
	}

	public Contact UpdateContact(Contact contact) {
		return updateIntable(contact);
	}

	public Contact saveContact(Contact contact) throws HibernateException {
		return saveIntable(contact);

	}

}
