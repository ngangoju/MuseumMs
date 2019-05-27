/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.musee.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import com.musee.dao.generic.AbstractDao;
import com.musee.dao.interfc.IUsers;
import com.musee.domain.Users;

/**
 *
 * @author Ngango
 */

public class UserImpl extends AbstractDao<Long, Users> implements IUsers {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());

	public Users saveUsers(Users users) {
		return saveIntable(users);
	}

	public List<Users> getListUsers() {
		return (List<Users>) (Object) getModelList();
	}

	public Users gettUserById(int userId, String primaryKeyclomunName) {
		return (Users) getModelById(userId, primaryKeyclomunName);
	}

	public Users UpdateUsers(Users users) {
		return updateIntable(users);
	}

	public Users getUsersWithQuery(String[] propertyName, Object[] value, String hqlStatement) {
		try {
			return (Users) getModelWithMyHQL(propertyName, value, hqlStatement);
		} catch (Exception ex) {
			LOGGER.info("getUsersWithQuery  Query error ::::" + ex.getMessage());
		}
		return null;
	}

	public String myNane() {
		return "Ribakare Fred";
	}

	public UserImpl() {

	}
}
