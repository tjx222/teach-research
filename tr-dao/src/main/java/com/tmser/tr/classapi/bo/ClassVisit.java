/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.classapi.bo;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import com.tmser.tr.common.bo.QueryObject;

 /**
 * 课堂附件信息 Entity
 * <pre>
 *
 * </pre>
 *
 * @author ljh
 * @version $Id: ClassUser.java, v 1.0 2016年12月21日10:13:34 ljh Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = ClassVisit.TABLE_NAME)
public class ClassVisit extends QueryObject {
	public static final String TABLE_NAME="area_video_classvisit";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	/**
	 *课堂ID
	 **/
	@Column(name="class_id",length=32)
	private String classId;

	/**
	 *用户Id
	 **/
	@Id
	@Column(name="user_id")
	private Integer userId;

	/**
	 *用户名
	 **/
	@Column(name="user_name")
	private String userName;

	/**
	 *机构id
	 **/
	@Column(name="org_id")
	private Integer orgId;
	/**
	 *机构id
	 **/
	@Column(name="org_name")
	private String orgName;
	/**
	 *机构id
	 **/
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name="join_time")
	private Date joinTime;

	/**
	 *开始时间
	 **/
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Transient
	private Date startTime;
	
	/**
	 *结束时间
	 **/
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Transient
	private Date endTime;
	
	@Transient
	private Integer count;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Date getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}

	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof ClassVisit))
				return false;
			ClassVisit castOther = (ClassVisit) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


