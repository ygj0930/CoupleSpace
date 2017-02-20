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

import com.ygj.bo.DBUtil_BO;
import com.ygj.bo.UserBO;
import com.ygj.control.C3p0Utils;
import com.ygj.control.DBUtils;

import com.ygj.error.MyException;

public class UserDAO {
	
	private static UserDAO userDAO=new UserDAO();
	public static UserDAO getuserDAO() {
		return userDAO;
	}
	private UserDAO() {
		super();
	}
	private DBUtil_BO dbBo=new DBUtil_BO();
	private  DBUtils dbUtils=DBUtils.getDbUtils();
	Logger logger=Logger.getLogger(getClass().getName());

	public int doQuery(UserBO user) {
		int uid = -1;
		String name = user.getUsername();
		String pass = user.getPassword();

		try {
			dbBo.conn=C3p0Utils.getConnection();
			String sql = "select * from users where username=? and password=?";
			dbBo.st=dbBo.conn.prepareStatement(sql);
			dbBo.st.setString(1, name);
			dbBo.st.setString(2, pass);			
			dbUtils.executeQuery(dbBo);
			if (dbBo.rs.next()) {
				uid =dbBo.rs.getInt("userid");
			}
			dbUtils.realseSource(dbBo);
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
			dbBo.conn=C3p0Utils.getConnection();
			String sql = "insert into users(username,password,email) values(?,?,?)";
			dbBo.st=dbBo.conn.prepareStatement(sql);
			dbBo.st.setString(1, name);
			dbBo.st.setString(2, pass);
			dbBo.st.setString(3, email);			
			dbUtils.executeUpdate(dbBo);		
		} catch (SQLException e) {
			logger.error("SQLException in UserDAO!", e);
			throw new MyException("SQLError in UserDAO!", e);
			
		}
	}
}
