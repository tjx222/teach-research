/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.operationmanage.vo;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import com.tmser.tr.common.bo.QueryObject;

/**
 * <pre>
 * 搜索vo
 * </pre>
 *
 * @author daweiwbs
 * @version $Id: SearchVo.java, v 1.0 2015年11月2日 上午11:04:21 daweiwbs Exp $
 */
public class SearchVo extends QueryObject{
	
	/**
	 * <pre>
	 *
	 * </pre>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 地区id
	 */
	public Integer areaId;
	
	/**
	 * 机构id
	 */
	public Integer orgId;
	
	/**
	 * 职务id
	 */
	public Integer roleId;

	/**
	 * 学段id
	 */
	public Integer phaseId;
	
	/**
	 * 学科id
	 */
	public Integer subjectId;
	
	/**
	 * 年级id
	 */
	public Integer gradeId;
	
	public Integer userId;
	/**
	 * 开始时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	public Date startTime;
	
	/**
	 * 结束时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	public Date endTime;

	/**
	 * 用户姓名
	 */
	public String userName;
	
	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(Integer phaseId) {
		this.phaseId = phaseId;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
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

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof SearchVo))
				return false;
			SearchVo castOther = (SearchVo) other;
			return new EqualsBuilder().append(areaId, castOther.areaId)
					.append(orgId, castOther.orgId).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(areaId).append(orgId).toHashCode();
	}
	
	
	
}
