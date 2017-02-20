package com.ygj.service;

import java.util.Vector;

import com.ygj.dao.ArticleDAO;
import com.ygj.vo.Article;

public class ArticleService {
	
	
	public ArticleService() {
		super();
		// TODO Auto-generated constructor stub
	}
	public boolean addArticle(Article article){
		return new ArticleDAO().doAdd(article);
	}
	public Article doChaxun(String id){
		return new ArticleDAO().doChaxun(id);
	}
	public Vector<Article> getFenye(int uid,int pages,int limits){	
		return new ArticleDAO().ChaFenye(uid,pages, limits);		
	}
	public boolean doDel(String id){
		return new ArticleDAO().doDelete(id);
	}
	public boolean UpArticle(Article article){
		return new ArticleDAO().doUpdate(article);		
	}

}
