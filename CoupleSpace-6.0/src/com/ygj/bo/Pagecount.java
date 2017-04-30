package com.ygj.bo;

/**
 * Pagecount entity. @author MyEclipse Persistence Tools
 */

public class Pagecount implements java.io.Serializable {

	// Fields

	private Integer articleTotal;

	// Constructors

	/** default constructor */
	public Pagecount() {
	}

	/** full constructor */
	public Pagecount(Integer articleTotal) {
		this.articleTotal = articleTotal;
	}

	// Property accessors

	public Integer getArticleTotal() {
		return this.articleTotal;
	}

	public void setArticleTotal(Integer articleTotal) {
		this.articleTotal = articleTotal;
	}

}