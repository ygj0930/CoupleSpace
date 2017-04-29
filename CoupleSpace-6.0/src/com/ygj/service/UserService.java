package com.ygj.service;

import com.ygj.bo.UserBO;
import com.ygj.dao.UserDAO;

public class UserService {
	private UserDAO userDAO=UserDAO.getuserDAO();
	private static UserService userService=new UserService();
	public static UserService getUserService() {
		return userService;
	}
	private UserService() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int dologin(UserBO user){
  	  return userDAO.doQuery(user);
	}
	public void doregister(UserBO user){
		userDAO.doAdd(user);
	}
}
