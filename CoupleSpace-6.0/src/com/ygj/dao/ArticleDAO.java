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
import java.util.Vector;
import java.util.Properties;

import javax.transaction.Transaction;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.sun.imageio.plugins.common.LZWStringTable;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.ygj.bo.ArticleBO;
import com.ygj.bo.DBUtil_BO;
import com.ygj.control.C3p0Utils;
import com.ygj.control.DBUtils;

import com.ygj.error.MyException;

public class ArticleDAO {
	
	private static ArticleDAO articleDAO=new ArticleDAO();
	Session session=HibernateSessionFactory.getSession();
	public static ArticleDAO getArticleDAO() {
		return articleDAO;
	}
	private ArticleDAO() {
		super();
	}

	Logger logger=Logger.getLogger(getClass().getName());
	
	
	public boolean doAdd(ArticleBO article){
		boolean flag=false;
		try {
			    session.save(article);
			    flag=true;
		} catch (Exception e) {
			logger.error("SQLException in ArticleDAO!", e);
			throw new MyException("SQLError in ArticleDAO!", e);
			
		}
		return flag;
	}
	
	public ArticleBO doChaxun(String id){
		ArticleBO article = null;
		try {
		    Criteria criteria=session.createCriteria(ArticleBO.class);
		    Criterion criterion=Restrictions.idEq(id);
		    criteria.add(criterion);
		    List<ArticleBO> list=criteria.list();
		    Iterator iterator=list.iterator();
		    while(iterator.hasNext()){
		    	article=(ArticleBO) iterator.next();
		    }
		}catch (Exception e) {
			logger.error("SQLException in ArticleDAO!", e);
			throw new MyException("SQLError in ArticleDAO!", e);
			
		}
		return article;
	}
	
	public Vector<ArticleBO> ChaFenye(int uid,int pages,int limit){
		Vector<ArticleBO> arts=new Vector<ArticleBO>();
		ArticleBO article=null;
		try {
			String sql=null;
			if(uid==0){
			sql="from ArticleBO";
			}else{
			sql = "from ArticleBO where u_id="+uid;}
			Query query=session.createQuery(sql);
			query.setFirstResult((pages-1)*limit);
			query.setMaxResults(limit);
			List<ArticleBO> list=query.list();
			Iterator iterator=list.iterator();
			while(iterator.hasNext()){
				article=(ArticleBO) iterator.next();
				arts.add(article);
			}
		} catch (Exception e) {
			logger.error("SQLException in ArticleDAO!", e);
			throw new MyException("SQLError in ArticleDAO!", e);			
		}
		
		return arts;
		
	}
	
	public boolean doUpdate(ArticleBO article){

		boolean flag=false;
		try {			
		    session.update(article);
			flag=true;
		} catch (Exception e) {
			logger.error("SQLException in ArticleDAO!", e);
			throw new MyException("SQLError in ArticleDAO!", e);
			
		}
		return flag;
	}
	
	public boolean doDelete(String id){
		boolean flag=false;
		try {
		    String sql="delete ArticleBO where a_id="+id;	
		    Query query=session.createQuery(sql);
		    query.executeUpdate();					
			flag=true;	
		} catch (Exception e) {
			logger.error("SQLException in ArticleDAO!", e);
			throw new MyException("SQLError in ArticleDAO!", e);			
		}
		return flag;
	}

}
