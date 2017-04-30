package com.ygj.dao;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ygj.bo.Pagecount;

public class PagecountDAO extends HibernateDaoSupport{
	private SessionFactory sessionFactory;
	public int getCount(){
		 List<Pagecount> list=getHibernateTemplate().find("from Pagecount");
		return list.iterator().next().getArticleTotal();		
	}

}
