/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.annunciate.bo;



import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.BaseObject;

 /**
 * 红头文件表头 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: JyRedTitle.java, v 1.0 2015-06-12 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = JyRedTitle.TABLE_NAME)
public class JyRedTitle extends BaseObject {
	public static final String TABLE_NAME="jy_red_title";
	
		@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	/**
	 *组织id
	 **/
	@Column(name="org_id",nullable=false)
	private Integer orgId;

	/**
	 *红头标题
	 **/
	@Column(name="title",nullable=false,length=200)
	private String title;

	
	/**
	 *是否可用：1、可用，0、不可用
	 **/
	@Column(name="is_enable",nullable=false)
	private Integer isEnable;

	/**
	 *是否已删除：1、已删除，0未删除
	 **/
	@Column(name="is_delete",nullable=false)
	private Integer isDelete;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setOrgId(Integer orgId){
		this.orgId = orgId;
	}

	public Integer getOrgId(){
		return this.orgId;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return this.title;
	}

	public void setIsEnable(Integer isEnable){
		this.isEnable = isEnable;
	}

	public Integer getIsEnable(){
		return this.isEnable;
	}

	public void setIsDelete(Integer isDelete){
		this.isDelete = isDelete;
	}

	public Integer getIsDelete(){
		return this.isDelete;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof JyRedTitle))
				return false;
			JyRedTitle castOther = (JyRedTitle) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


