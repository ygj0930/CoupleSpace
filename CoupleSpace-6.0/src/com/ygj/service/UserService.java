package com.ygj.service;

import com.ygj.dao.UserDAO;
import com.ygj.dao.Users;

public class UserService {
	private UserDAO userDAO;
	
	public UserDAO getUserDAO() {
		return userDAO;
	}
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	public int dologin(Users user){
  	  return userDAO.doQuery(user);
	}
	public void doregister(Users user){
		userDAO.doAdd(user);
	}
}
