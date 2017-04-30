package com.ygj.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


public class UserDAO extends HibernateDaoSupport{
	private SessionFactory sessionFactory;
	Logger logger=Logger.getLogger(getClass().getName());

	public int doQuery(Users user) {
		int uid=-1;
		String name=user.getUsername();
		String password=user.getPassword();
		System.out.println(name);
		System.out.println(password);
		String sql="select * from users where username=? and password=?";
		SQLQuery query=getSession().createSQLQuery(sql).addEntity(Users.class);
	    query.setString(0,user.getUsername());
	    query.setString(1,user.getPassword());
	    Users chaUsers=(Users) query.uniqueResult();
	    uid=chaUsers.getUserid();
	    System.out.println("=====in dao"+uid);
		return uid;
	}

	public void doAdd(Users user) {
		getHibernateTemplate().save(user);
	}
}
