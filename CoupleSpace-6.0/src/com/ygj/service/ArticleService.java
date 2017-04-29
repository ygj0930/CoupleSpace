package com.ygj.service;

import java.util.Vector;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;

import org.hibernate.Session;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.ygj.bo.ArticleBO;
import com.ygj.dao.ArticleDAO;
import com.ygj.dao.HibernateSessionFactory;

public class ArticleService {
	private ArticleDAO articleDAO=ArticleDAO.getArticleDAO();
	private static ArticleService articleService=new ArticleService();
	Session session=HibernateSessionFactory.getSession();
	Transaction transaction=null;
	public static ArticleService getArticleService() {
		return articleService;
	}
	private ArticleService() {
		super();
		// TODO Auto-generated constructor stub
	}
	public boolean addArticle(ArticleBO article){
		transaction=(Transaction) session.beginTransaction();
		Boolean res=articleDAO.doAdd(article);
		try {
			transaction.commit();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicMixedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	public ArticleBO doChaxun(String id){
		return  articleDAO.doChaxun(id);
	}
	public Vector<ArticleBO> getFenye(int uid,int pages,int limits){	
		return  articleDAO.ChaFenye(uid,pages, limits);		
	}
	public boolean doDel(String id){
		transaction=(Transaction) session.beginTransaction();
		boolean res=articleDAO.doDelete(id);
		try {
			transaction.commit();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicMixedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	public boolean UpArticle(ArticleBO article){
		transaction=(Transaction) session.beginTransaction();
		boolean res=articleDAO.doUpdate(article);	
		try {
			transaction.commit();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicMixedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

}
