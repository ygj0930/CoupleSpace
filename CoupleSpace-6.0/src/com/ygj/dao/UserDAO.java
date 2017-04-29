package com.ygj.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
	Logger logger=Logger.getLogger(getClass().getName());

	public int doQuery(UserBO user) {
		String name = user.getUsername();
		String pass = user.getPassword();
		UserBO userBO=null;
		try {
			Session session=HibernateSessionFactory.getSession();
			String sql = "from UserBO where username='"+name+"' and password='"+pass+"'";
			Query query=session.createQuery(sql);
			List<UserBO> list=query.list();
			
			Iterator<UserBO> iterator=list.iterator();
			userBO=iterator.next();
			
		} catch (Exception e) {
			logger.error("SQLException in UserDAO!", e);
			throw new MyException("SQLError in UserDAO!", e);
			
		}
		return userBO.getUserid();
	}

	public void doAdd(UserBO user) {
		try {
			    Session session=HibernateSessionFactory.getSession();
			    Transaction transaction=(Transaction) session.beginTransaction();
			    session.save(user);
			    transaction.commit();
			    session.close();
		} catch (Exception e) {
			logger.error("SQLException in ArticleDAO!", e);
			throw new MyException("SQLError in ArticleDAO!", e);
			
		}
	}
}
