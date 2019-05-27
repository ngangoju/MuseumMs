package com.musee.dao.interfc;

import java.util.List;

import com.musee.domain.UserCategory;


public interface IUserCategory {
	public UserCategory saveUsercategory(UserCategory usercategory);

	public List<UserCategory> getListUsercategory();

	public UserCategory UpdateUsercategory(UserCategory usercategory);

}
