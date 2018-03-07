package com.tmser.tr.companion.vo;

import com.tmser.tr.companion.bo.JyCompanion;

public class JyCompanionVo extends JyCompanion{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 性别
	 */
	private Integer sex;
	
	/**
	 * 职位类型
	 */
	private String roleType;
	
	/**
	 * 同伴学校名称
	 */
	private String schoolNameCompanion;
	
	/**
	 * 同伴学校id
	 */
	private Integer schoolIdCompanion;
	
	/**
	 * 最高角色名称
	 */
	private String highestRoleName;
	
	/**
	 * 最高角色id
	 */
	private Integer highestRoleId;
	
	/**
	 * 最优先学科id
	 */
	private Integer highestSubjectId;
	
	/**
	 * 最优先学科名称
	 */
	private String highestSubjectName;
	
	/**
	 * 最优先年级id
	 */
	private Integer highestGradeId;
	
	/**
	 * 最优先年级名称
	 */
	private String highestGradeName;
	
	/**
	 * 最优先版本名称
	 */
	private String highestFormatName;
	
	/**
	 * 学段
	 */
	private Integer highestPhaseId;
	
	/**
	 * 学段名称
	 */
	private String phaseNames;
	
	/**
	 * 最优学段名称
	 */
	private String highestPhaseName;
	
	/**
	 * 机构id
	 */
	private Integer orgId;
	
	/**
	 * 机构名称
	 */
	private String orgName;
	
	/**
	 * 职称
	 */
	private String profession;
	
	/**
	 * 教龄
	 */
	private Integer schoolAge;
	
	/**
	 * 角色名列表
	 */
	private String roleNames;
	
	/**
	 * 版本列表
	 */
	private String formatNames;
	
	/**
	 * 学科名称列表
	 */
	private String subjectNames;
	
	/**
	 * 年纪列表
	 */
	private String gradeNames;
	
	/**
	 * 用户图像
	 */
	private String photo;

	public String getHighestRoleName() {
		return highestRoleName;
	}

	public void setHighestRoleName(String highestRoleName) {
		this.highestRoleName = highestRoleName;
	}

	public Integer getHighestRoleId() {
		return highestRoleId;
	}

	public void setHighestRoleId(Integer highestRoleId) {
		this.highestRoleId = highestRoleId;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getSchoolNameCompanion() {
		return schoolNameCompanion;
	}

	public void setSchoolNameCompanion(String schoolNameCompanion) {
		this.schoolNameCompanion = schoolNameCompanion;
	}

	public Integer getSchoolIdCompanion() {
		return schoolIdCompanion;
	}

	public void setSchoolIdCompanion(Integer schoolIdCompanion) {
		this.schoolIdCompanion = schoolIdCompanion;
	}

	public Integer getSchoolAge() {
		return schoolAge;
	}

	public void setSchoolAge(Integer schoolAge) {
		this.schoolAge = schoolAge;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getFormatNames() {
		return formatNames;
	}

	public void setFormatNames(String formatNames) {
		this.formatNames = formatNames;
	}

	public String getSubjectNames() {
		return subjectNames;
	}

	public void setSubjectNames(String subjectNames) {
		this.subjectNames = subjectNames;
	}

	public String getGradeNames() {
		return gradeNames;
	}

	public void setGradeNames(String gradeNames) {
		this.gradeNames = gradeNames;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getHighestSubjectName() {
		return highestSubjectName;
	}

	public void setHighestSubjectName(String highestSubjectName) {
		this.highestSubjectName = highestSubjectName;
	}

	public String getHighestGradeName() {
		return highestGradeName;
	}

	public void setHighestGradeName(String highestGradeName) {
		this.highestGradeName = highestGradeName;
	}

	public String getHighestFormatName() {
		return highestFormatName;
	}

	public void setHighestFormatName(String highestFormatName) {
		this.highestFormatName = highestFormatName;
	}

	public Integer getHighestSubjectId() {
		return highestSubjectId;
	}

	public void setHighestSubjectId(Integer highestSubjectId) {
		this.highestSubjectId = highestSubjectId;
	}

	public Integer getHighestGradeId() {
		return highestGradeId;
	}

	public void setHighestGradeId(Integer highestGradeId) {
		this.highestGradeId = highestGradeId;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public Integer getHighestPhaseId() {
		return highestPhaseId;
	}

	public void setHighestPhaseId(Integer highestPhaseId) {
		this.highestPhaseId = highestPhaseId;
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

	public String getPhaseNames() {
		return phaseNames;
	}

	public void setPhaseNames(String phaseNames) {
		this.phaseNames = phaseNames;
	}

	public String getHighestPhaseName() {
		return highestPhaseName;
	}

	public void setHighestPhaseName(String highestPhaseName) {
		this.highestPhaseName = highestPhaseName;
	}

}
