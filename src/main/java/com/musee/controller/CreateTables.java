package com.musee.controller;

import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.musee.dao.impl.UserImpl;

@ManagedBean
@SessionScoped
public class CreateTables {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());

	public void cretaAllTable() {
		UserImpl userImpl = new UserImpl();

		userImpl.creatingNewTable();
		LOGGER.info("table created");

	}

}
