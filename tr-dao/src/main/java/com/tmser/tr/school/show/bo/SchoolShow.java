/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.school.show.bo;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.BaseObject;

 /**
 * 为学校首页提供数据 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: SchoolShow.java, v 1.0 2015-09-23 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = SchoolShow.TABLE_NAME)
public class SchoolShow extends BaseObject {
	public static final String TABLE_NAME="school_show";
	
	@Id
	@Column(name="id",length=32)
	private String id;
	
	/**
	 *类型（master、overview、teacher）
	 **/
	@Column(name="type",nullable=false,length=32)
	private String type;

	/**
	 *介绍
	 **/
	@Column(name="introduction",nullable=false,length=21845)
	private String introduction;

	/**
	 *简介
	 **/
	@Column(name="introduction_mini",length=1024)
	private String introductionMini;

	/**
	 *标题
	 **/
	@Column(name="title",length=512)
	private String title;

	/**
	 *图片资源（逗号分隔）
	 **/
	@Column(name="images",length=21845)
	private String images;
	/**
	 * 从属ID
	 */
	@Column(name="parent_id",nullable=false)
	private Integer orgId;

	/**
	 * 作者
	 **/
	@Column(name="author",length=64)
	private String author;
	/**
	 * 置顶排序标示符
	 */
	@Column(name="top_tag")
	private Integer topTag;
	
	public String getAuthor() {
		return author;
	}


	public Integer getTopTag() {
		return topTag;
	}


	public void setTopTag(Integer topTag) {
		this.topTag = topTag;
	}


	public void setAuthor(String author) {
		this.author = author;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return this.type;
	}

	public void setIntroduction(String introduction){
		this.introduction = introduction;
	}

	public String getIntroduction(){
		return this.introduction;
	}

	public void setIntroductionMini(String introductionMini){
		this.introductionMini = introductionMini;
	}

	public String getIntroductionMini(){
		return this.introductionMini;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return this.title;
	}

	public void setImages(String images){
		this.images = images;
	}

	public String getImages(){
		return this.images;
	}

	public void setOrgId(Integer orgId){
		this.orgId = orgId;
	}

	public Integer getOrgId(){
		return this.orgId;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof SchoolShow))
				return false;
			SchoolShow castOther = (SchoolShow) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


