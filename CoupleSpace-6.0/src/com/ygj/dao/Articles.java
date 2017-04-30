package com.ygj.dao;

/**
 * Articles entity. @author MyEclipse Persistence Tools
 */

public class Articles implements java.io.Serializable {

	// Fields

	private String AId;
	private String ATitle;
	private String ASummarize;
	private String AContent;
	private String ATime;
	private String APhoto;
	private Integer UId;

	// Constructors

	/** default constructor */
	public Articles() {
	}

	/** minimal constructor */
	public Articles(String AId) {
		this.AId = AId;
	}

	/** full constructor */
	public Articles(String AId, String ATitle, String ASummarize,
			String AContent, String ATime, String APhoto, Integer UId) {
		this.AId = AId;
		this.ATitle = ATitle;
		this.ASummarize = ASummarize;
		this.AContent = AContent;
		this.ATime = ATime;
		this.APhoto = APhoto;
		this.UId = UId;
	}

	// Property accessors

	public String getAId() {
		return this.AId;
	}

	public void setAId(String AId) {
		this.AId = AId;
	}

	public String getATitle() {
		return this.ATitle;
	}

	public void setATitle(String ATitle) {
		this.ATitle = ATitle;
	}

	public String getASummarize() {
		return this.ASummarize;
	}

	public void setASummarize(String ASummarize) {
		this.ASummarize = ASummarize;
	}

	public String getAContent() {
		return this.AContent;
	}

	public void setAContent(String AContent) {
		this.AContent = AContent;
	}

	public String getATime() {
		return this.ATime;
	}

	public void setATime(String ATime) {
		this.ATime = ATime;
	}

	public String getAPhoto() {
		return this.APhoto;
	}

	public void setAPhoto(String APhoto) {
		this.APhoto = APhoto;
	}

	public Integer getUId() {
		return this.UId;
	}

	public void setUId(Integer UId) {
		this.UId = UId;
	}

}