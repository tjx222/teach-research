/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.bo;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.QueryObject;

 /**
 * 用户功能历史记录 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: UsermenuHistory.java, v 1.0 2016-05-19 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = UsermenuHistory.TABLE_NAME)
public class UsermenuHistory extends QueryObject {
	public static final String TABLE_NAME="sys_usermenu_history";
	
		@Id
	@Column(name="id")
	private Integer id;

	/**
	 *功能code 传递
	 **/
	@Column(name="menus",nullable=false,length=1024)
	private String menus;

	/**
	 *用户id
	 **/
	@Column(name="user_id",nullable=false)
	private Integer userId;

	/**
	 *学年
	 **/
	@Column(name="school_year",nullable=false)
	private Integer schoolYear;


	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setMenus(String menus){
		this.menus = menus;
	}

	public String getMenus(){
		return this.menus;
	}

	public void setUserId(Integer userId){
		this.userId = userId;
	}

	public Integer getUserId(){
		return this.userId;
	}

	public void setSchoolYear(Integer schoolYear){
		this.schoolYear = schoolYear;
	}

	public Integer getSchoolYear(){
		return this.schoolYear;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof UsermenuHistory))
				return false;
			UsermenuHistory castOther = (UsermenuHistory) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


