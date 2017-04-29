package com.ygj.bo;

public class ArticleBO {

	public String a_id;
	public String a_title;
	public String a_summarize;
	public String a_content;
	public String a_time;
	public String a_photo;
	public int a_uid;
	
	public ArticleBO() {
		super();
	}
	

	public ArticleBO(String a_id, String a_title, String a_summarize,
			String a_content, String a_time, String a_photo,int a_uid) {
		super();
		this.a_id = a_id;
		this.a_title = a_title;
		this.a_summarize = a_summarize;
		this.a_content = a_content;
		this.a_time = a_time;
		this.a_photo = a_photo;
		this.a_uid=a_uid;
	}
	


	public int getA_uid() {
		return a_uid;
	}


	public void setA_uid(int a_uid) {
		this.a_uid = a_uid;
	}


	public String getA_id() {
		return a_id;
	}

	public void setA_id(String a_id) {
		this.a_id = a_id;
	}

	public String getA_title() {
		return a_title;
	}

	public void setA_title(String a_title) {
		this.a_title = a_title;
	}

	public String getA_summarize() {
		return a_summarize;
	}

	public void setA_summarize(String a_summarize) {
		this.a_summarize = a_summarize;
	}

	public String getA_content() {
		return a_content;
	}

	public void setA_content(String a_content) {
		this.a_content = a_content;
	}

	public String getA_time() {
		return a_time;
	}

	public void setA_time(String a_time) {
		this.a_time = a_time;
	}

	public String getA_photo() {
		return a_photo;
	}

	public void setA_photo(String a_photo) {
		this.a_photo = a_photo;
	}
	

}
