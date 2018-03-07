/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.classapi.bo;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.QueryObject;

 /**
 * 课堂附件信息 Entity
 * <pre>
 *
 * </pre>
 *
 * @author yc
 * @version $Id: ClassUser.java, v 1.0 2016-09-20 yc Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = ClassUser.TABLE_NAME)
public class ClassUser extends QueryObject {
	public static final String TABLE_NAME="area_video_classuser";
	
	/**
	 *课堂ID
	 **/
	@Column(name="class_id",nullable=false,length=32)
	private String classId;

	/**
	 *用户Id
	 **/
	@Id
	@Column(name="user_id")
	private Integer userId;

	/**
	 *课堂url
	 **/
	@Column(name="class_url",length=2048)
	private String classUrl;

	/**
	 *用户类型
	 **/
	@Column(name="user_type")
	private Integer userType;


	public void setClassId(String classId){
		this.classId = classId;
	}

	public String getClassId(){
		return this.classId;
	}

	public void setUserId(Integer userId){
		this.userId = userId;
	}

	public Integer getUserId(){
		return this.userId;
	}

	public void setClassUrl(String classUrl){
		this.classUrl = classUrl;
	}

	public String getClassUrl(){
		return this.classUrl;
	}

	public void setUserType(Integer userType){
		this.userType = userType;
	}

	public Integer getUserType(){
		return this.userType;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof ClassUser))
				return false;
			ClassUser castOther = (ClassUser) other;
			return new EqualsBuilder().append(userId, castOther.userId).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(userId).toHashCode();
	}
}


