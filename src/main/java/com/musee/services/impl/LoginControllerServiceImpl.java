package com.musee.services.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.musee.dao.impl.UserImpl;
import com.musee.services.interfaces.ILoginControllerService;

@Stateless
public class LoginControllerServiceImpl implements ILoginControllerService {

	@Inject
	public transient UserImpl usersImpl;

	public String getMyNgaboName() {

		return usersImpl.myNane();
	}

}
