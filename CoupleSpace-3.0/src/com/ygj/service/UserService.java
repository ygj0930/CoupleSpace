package com.ygj.service;

import com.ygj.bo.UserBO;
import com.ygj.dao.UserDAO;

public class UserService {
	
	public UserService() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int dologin(UserBO user){
  	  return new UserDAO().doQuery(user);
	}
	public void doregister(UserBO user){
	  	   new UserDAO().doAdd(user);
	}
}
