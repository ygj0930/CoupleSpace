package com.ygj.service;

import com.ygj.dao.UserDAO;
import com.ygj.vo.User;

public class UserService {
	
	public UserService() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int dologin(User user){
  	  return new UserDAO().doQuery(user);
	}
	public void doregister(User user){
	  	   new UserDAO().doAdd(user);
	}
}
