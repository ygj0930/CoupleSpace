package com.ygj.service;

import java.util.Vector;

import com.ygj.bo.ArticleBO;
import com.ygj.dao.ArticleDAO;

public class ArticleService {
	
	
	public ArticleService() {
		super();
		// TODO Auto-generated constructor stub
	}
	public boolean addArticle(ArticleBO article){
		return new ArticleDAO().doAdd(article);
	}
	public ArticleBO doChaxun(String id){
		return new ArticleDAO().doChaxun(id);
	}
	public Vector<ArticleBO> getFenye(int uid,int pages,int limits){	
		return new ArticleDAO().ChaFenye(uid,pages, limits);		
	}
	public boolean doDel(String id){
		return new ArticleDAO().doDelete(id);
	}
	public boolean UpArticle(ArticleBO article){
		return new ArticleDAO().doUpdate(article);		
	}

}
