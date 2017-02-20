package com.ygj.service;

import java.util.Vector;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.ygj.bo.ArticleBO;
import com.ygj.dao.ArticleDAO;

public class ArticleService {
	private ArticleDAO articleDAO=ArticleDAO.getArticleDAO();
	private static ArticleService articleService=new ArticleService();
	public static ArticleService getArticleService() {
		return articleService;
	}
	private ArticleService() {
		super();
		// TODO Auto-generated constructor stub
	}
	public boolean addArticle(ArticleBO article){
		return  articleDAO.doAdd(article);
	}
	public ArticleBO doChaxun(String id){
		return  articleDAO.doChaxun(id);
	}
	public Vector<ArticleBO> getFenye(int uid,int pages,int limits){	
		return  articleDAO.ChaFenye(uid,pages, limits);		
	}
	public boolean doDel(String id){
		return  articleDAO.doDelete(id);
	}
	public boolean UpArticle(ArticleBO article){
		return  articleDAO.doUpdate(article);		
	}

}
