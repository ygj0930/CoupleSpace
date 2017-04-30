package com.ygj.dao;

/**
 * Users entity. @author MyEclipse Persistence Tools
 */

public class Users implements java.io.Serializable {

	private String email;
    private String username;
    private int userid;
    private String password;
    
	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public Users(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}


	public Users(String username, String password,String email) {
		super();
		this.email = email;
		this.username = username;
		this.password = password;
	}
	public Users(String email, String username, Integer userid, String password) {
		super();
		this.email = email;
		this.username = username;
		this.userid = userid;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
}