/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.vo;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.QueryObject;

/**
 * <pre>
 * 用户查询模型
 * </pre>
 * 
 * @author tmser
 * @version $Id: UserSearchModel.java, v 1.0 2016年1月22日 下午2:13:05 tmser Exp $
 */
public class UserSearchModel extends QueryObject {

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	private static final long serialVersionUID = 2897412539652458186L;

	private Integer id;

	private Integer areaId;

	/**
	 * 真实姓名
	 */
	private String name;

	/**
	 * 区域名称
	 */
	private String areaName;

	/**
	 * 学校名称
	 */
	private String orgName;

	private Integer subjectId;

	/**
	 * 系统角色
	 */
	private Integer sysRoleId;
	/**
	 * 年级
	 */
	private Integer gradeId;

	/**
	 * 学段
	 */
	private Integer phaseId;

	private Integer orgId;

	private Integer roleId;

	private Integer userType;

	/**
	 * 所属应用
	 */
	private Integer appId;

	/**
	 * 状态
	 */
	private Boolean state;
	
	private Integer schoolYear;

	/**
	 * 登录账号
	 */
	private String account;

	private List<Integer> orgIds;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
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

	public Integer getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(Integer phaseId) {
		this.phaseId = phaseId;
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

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public List<Integer> getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(List<Integer> orgIds) {
		this.orgIds = orgIds;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/** 
	 * Getter method for property <tt>schoolYear</tt>. 
	 * @return property value of schoolYear 
	 */
	public Integer getSchoolYear() {
		return schoolYear;
	}

	/**
	 * Setter method for property <tt>schoolYear</tt>.
	 * @param schoolYear value to be assigned to property schoolYear
	 */
	public void setSchoolYear(Integer schoolYear) {
		this.schoolYear = schoolYear;
	}

	/**
	 * Getter method for property <tt>sysRoleId</tt>.
	 * 
	 * @return property value of sysRoleId
	 */
	public Integer getSysRoleId() {
		return sysRoleId;
	}

	/**
	 * Setter method for property <tt>sysRoleId</tt>.
	 * 
	 * @param sysRoleId
	 *            value to be assigned to property sysRoleId
	 */
	public void setSysRoleId(Integer sysRoleId) {
		this.sysRoleId = sysRoleId;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof UserSearchModel))
			return false;
		UserSearchModel castOther = (UserSearchModel) other;
		return new EqualsBuilder().append(name, castOther.name).append(account, castOther.account).append(roleId, castOther.roleId).append(orgId, castOther.orgId).append(phaseId, castOther.phaseId)
				.append(subjectId, castOther.subjectId).append(orgName, castOther.orgName).append(gradeId, castOther.gradeId).append(state, castOther.state).append(appId, castOther.appId)
				.append(userType, castOther.userType).append(sysRoleId, castOther.sysRoleId).append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(name).append(account).append(roleId).append(orgId).append(phaseId).append(subjectId).append(id).append(orgName).append(userType).append(state)
				.append(appId).append(sysRoleId).append(gradeId).toHashCode();
	}

}
