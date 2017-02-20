package com.ygj.vo;

public class User {
	private String username;
	private String password;
	private String email;	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String name,String pass,String e) {
	this.username=name;
	this.password=pass;
	this.email=e;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	
}
