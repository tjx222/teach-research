/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.bo;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.QueryObject;

 /**
 * app_param Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: SysAppParam.java, v 1.0 2016-08-03 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = AppParam.TABLE_NAME)
public class AppParam extends QueryObject {
	public static final String TABLE_NAME="sys_app_param";
	
	/**
	 *appid
	 **/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id",length=12)
	private Integer id;
	
	@Column(name="appid",length=64)
	private String appid;

	@Column(name="name",nullable=false,length=255)
	private String name;

	@Column(name="val",length=255)
	private String val;


	public void setAppid(String appid){
		this.appid = appid;
	}

	public String getAppid(){
		return this.appid;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setVal(String val){
		this.val = val;
	}

	public String getVal(){
		return this.val;
	}

	/** 
	 * Getter method for property <tt>id</tt>. 
	 * @return property value of id 
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Setter method for property <tt>id</tt>.
	 * @param id value to be assigned to property id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof AppParam))
				return false;
			AppParam castOther = (AppParam) other;
			return new EqualsBuilder().append(appid, castOther.appid).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(appid).toHashCode();
	}
}


