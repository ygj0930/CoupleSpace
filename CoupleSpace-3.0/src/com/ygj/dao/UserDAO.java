package com.ygj.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.apache.log4j.Logger;

import com.ygj.bo.UserBO;
import com.ygj.error.MyError;
import com.ygj.error.MyException;

public class UserDAO {
	Connection con;
	PreparedStatement st;
	ResultSet rs;
	Properties properties;
	Logger logger=Logger.getLogger(getClass().getName());
	public UserDAO() {
		super();
		properties=new Properties();
		InputStream in=ArticleDAO.class.getClassLoader().getResourceAsStream("config.properties");
		try {
			properties.load(in);
		} catch (Exception e) {
			logger.error("Exception in UserDAO!", e);
			throw new MyError("加载配置文件出错!", e);
			
		}
	}

	public int doQuery(UserBO user) {
		int uid = -1;
		String name = user.getUsername();
		String pass = user.getPassword();

		try {
			Class.forName(properties.getProperty("dbDriver"));
			con = DriverManager.getConnection(
					properties.getProperty("dbUrl"), properties.getProperty("dbName"), properties.getProperty("dbPass"));
			String sql = "select * from users where username=? and password=?";
            st = con.prepareStatement(sql);
            st.setString(1, name);
            st.setString(2, pass);			
			rs = st.executeQuery();
			if (rs.next()) {
				uid = rs.getInt("userid");
			}
			rs.close();
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException in UserDAO!", e);
			throw new MyException("ClassNotFoundError in UserDAO!", e);
			
		} catch (SQLException e) {
			logger.error("SQLException in UserDAO!", e);
			throw new MyException("SQLError in UserDAO!", e);
			
		}
		return uid;
	}

	public void doAdd(UserBO user) {
		String name = user.getUsername();
		String pass = user.getPassword();
		String email = user.getEmail();
		try {
			Class.forName(properties.getProperty("dbDriver"));
			con = DriverManager.getConnection(
					properties.getProperty("dbUrl"), properties.getProperty("dbName"), properties.getProperty("dbPass"));
			String sql = "insert into users(username,password,email) values(?,?,?)";
			st = con.prepareStatement(sql);
			st.setString(1, name);
			st.setString(2, pass);
			st.setString(3, email);			
			st.executeUpdate();
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException in UserDAO!", e);
			throw new MyException("ClassNotFoundError in UserDAO!", e);
			
		} catch (SQLException e) {
			logger.error("SQLException in UserDAO!", e);
			throw new MyException("SQLError in UserDAO!", e);
			
		}
	}
}
