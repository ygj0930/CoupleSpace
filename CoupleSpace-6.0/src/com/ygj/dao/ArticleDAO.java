package com.ygj.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.ygj.error.MyException;

public class ArticleDAO extends HibernateDaoSupport{
	private SessionFactory sessionFactory;
	Logger logger=Logger.getLogger(getClass().getName());	
	
	public boolean doAdd(Articles article){
	   getHibernateTemplate().save(article);
	   return true;
	}
	
	public Articles doChaxun(String id){
		Articles article = null;
		try {
		    Criteria criteria=getHibernateTemplate().getSessionFactory().openSession().createCriteria(Articles.class);
		    Criterion criterion=Restrictions.idEq(id);
		    criteria.add(criterion);
		    List<Articles> list=criteria.list();
		    Iterator iterator=list.iterator();
		    while(iterator.hasNext()){
		    	article=(Articles) iterator.next();
		    }
		}catch (Exception e) {
			logger.error("SQLException in ArticleDAO!", e);
			throw new MyException("SQLError in ArticleDAO!", e);
			
		}
		return article;
		
	}
	
	public Vector<Articles> ChaFenye(int uid,int pages,int limit){
		Vector<Articles> arts=new Vector<Articles>();
		Articles article=null;
		try {
			String sql=null;
			if(uid==0){
			sql="from Articles";
			}else{
			sql = "from Articles where UId="+uid;}
			Query query=getHibernateTemplate().getSessionFactory().openSession().createQuery(sql);
			query.setFirstResult((pages-1)*limit);
			query.setMaxResults(limit);
			List<Articles> list=query.list();
			Iterator iterator=list.iterator();
			while(iterator.hasNext()){
				article=(Articles) iterator.next();
				arts.add(article);
			}
		} catch (Exception e) {
			logger.error("SQLException in ArticleDAO!", e);
			throw new MyException("SQLError in ArticleDAO!", e);			
		}
		
		return arts;
		
	}
	public boolean doUpdate(Articles article){
		 getHibernateTemplate().update(article);
		return true;
	}
	
	public boolean doDelete(String id){
		boolean flag=false;
		try {
		    String sql="delete Articles where a_id='"+id+"'";	
		    Query query=getHibernateTemplate().getSessionFactory().openSession().createQuery(sql);
		    query.executeUpdate();					
			flag=true;	
		} catch (Exception e) {
			logger.error("SQLException in ArticleDAO!", e);
			throw new MyException("SQLError in ArticleDAO!", e);			
		}
		return flag;
	}

}
