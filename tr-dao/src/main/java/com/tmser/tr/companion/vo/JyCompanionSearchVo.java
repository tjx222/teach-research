package com.tmser.tr.companion.vo;

import java.io.Serializable;
import java.util.List;

import com.tmser.tr.common.page.Page;

public class JyCompanionSearchVo extends Page implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 当前用户id
	 */
	private Integer currentUserId;
	
	/**
	 * 当前用户角色id
	 */
	private Integer currentRoleId;
	
	/**
	 * 当前用户学科id
	 */
	private Integer currentSubjectId;
	
	/**
	 * 当前用户年级id
	 */
	private Integer currentGradeId;

	/**
	 * 角色id
	 */
	private Integer roleId;
	
	/**
	 * 角色id列表
	 */
	private List<Integer> roleIds;
	
	/**
	 * 学校id
	 */
	private Integer schoolId;
	
	/**
	 * 学校id列表
	 */
	private List<Integer> schoolIds;
	
	/**
	 * 学校名称
	 */
	private String schoolName;
	
	/**
	 * 是否同一学校
	 */
	private Boolean isSameSchool;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 昵称
	 */
	private Integer userNickName;
	
	/**
	 * 学科id
	 */
	private Integer subjectId;
	
	/**
	 * 年级id
	 */
	private Integer gradeId;
	
	/**
	 * 职称
	 */
	private String profession;
	
	/**
	 * 教龄
	 */
	private Integer schoolAge;
	
	/**
	 * 学段id
	 */
	private Integer phaseId;
	
	/**
	 * 学年
	 */
	private Integer schoolYear;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public List<Integer> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Integer> roleIds) {
		this.roleIds = roleIds;
	}

	public Integer getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	public List<Integer> getSchoolIds() {
		return schoolIds;
	}

	public void setSchoolIds(List<Integer> schoolIds) {
		this.schoolIds = schoolIds;
	}

	public Boolean getIsSameSchool() {
		return isSameSchool;
	}

	public void setIsSameSchool(Boolean isSameSchool) {
		this.isSameSchool = isSameSchool;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserNickName() {
		return userNickName;
	}

	public void setUserNickName(Integer userNickName) {
		this.userNickName = userNickName;
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

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public Integer getSchoolAge() {
		return schoolAge;
	}

	public void setSchoolAge(Integer schoolAge) {
		this.schoolAge = schoolAge;
	}

	public Integer getCurrentRoleId() {
		return currentRoleId;
	}

	public void setCurrentRoleId(Integer currentRoleId) {
		this.currentRoleId = currentRoleId;
	}

	public Integer getCurrentSubjectId() {
		return currentSubjectId;
	}

	public void setCurrentSubjectId(Integer currentSubjectId) {
		this.currentSubjectId = currentSubjectId;
	}

	public Integer getCurrentGradeId() {
		return currentGradeId;
	}

	public void setCurrentGradeId(Integer currentGradeId) {
		this.currentGradeId = currentGradeId;
	}

	public Integer getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(Integer currentUserId) {
		this.currentUserId = currentUserId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public Integer getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(Integer phaseId) {
		this.phaseId = phaseId;
	}

	public Integer getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Integer schoolYear) {
		this.schoolYear = schoolYear;
	}
}
