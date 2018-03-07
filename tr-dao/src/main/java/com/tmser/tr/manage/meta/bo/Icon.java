/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.meta.bo;



import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.BaseObject;

 /**
 * 系统图标资源 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Icon.java, v 1.0 2015-02-11 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = Icon.TABLE_NAME)
public class Icon extends BaseObject {
	public static final String TABLE_NAME="sys_icon";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="identity")
	private String identity;

	@Column(name="css_class")
	private String cssClass;

	@Column(name="img_src")
	private String imgSrc;

	@Column(name="width")
	private Integer width;

	@Column(name="height")
	private Integer height;

	@Column(name="left")
	private Integer left;

	@Column(name="top")
	private Integer top;

	@Column(name="type")
	private String type;

	@Column(name="user_id")
	private Integer userId;

	@Column(name="description")
	private String description;


	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setIdentity(String identity){
		this.identity = identity;
	}

	public String getIdentity(){
		return this.identity;
	}

	public void setCssClass(String cssClass){
		this.cssClass = cssClass;
	}

	public String getCssClass(){
		return this.cssClass;
	}

	public void setImgSrc(String imgSrc){
		this.imgSrc = imgSrc;
	}

	public String getImgSrc(){
		return this.imgSrc;
	}

	public void setWidth(Integer width){
		this.width = width;
	}

	public Integer getWidth(){
		return this.width;
	}

	public void setHeight(Integer height){
		this.height = height;
	}

	public Integer getHeight(){
		return this.height;
	}

	public void setLeft(Integer left){
		this.left = left;
	}

	public Integer getLeft(){
		return this.left;
	}

	public void setTop(Integer top){
		this.top = top;
	}

	public Integer getTop(){
		return this.top;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return this.type;
	}

	public void setUserId(Integer userId){
		this.userId = userId;
	}

	public Integer getUserId(){
		return this.userId;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return this.description;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof Icon))
				return false;
			Icon castOther = (Icon) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


