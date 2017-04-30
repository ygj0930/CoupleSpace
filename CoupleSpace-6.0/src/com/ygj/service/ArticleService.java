package com.ygj.service;

import java.util.Vector;
import com.ygj.dao.ArticleDAO;
import com.ygj.dao.Articles;

public class ArticleService {
	private ArticleDAO articleDAO;
	
	public ArticleDAO getArticleDAO() {
		return articleDAO;
	}
	public void setArticleDAO(ArticleDAO articleDAO) {
		this.articleDAO = articleDAO;
	}
	public boolean addArticle(Articles article){
		return  articleDAO.doAdd(article);
	}
	public Articles doChaxun(String id){
		return  articleDAO.doChaxun(id);
	}
	public Vector<Articles> getFenye(int uid,int pages,int limits){	
		return  articleDAO.ChaFenye(uid,pages, limits);		
	}
	public boolean doDel(String id){
		return  articleDAO.doDelete(id);
	}
	public boolean UpArticle(Articles article){
		return  articleDAO.doUpdate(article);		
	}

}
