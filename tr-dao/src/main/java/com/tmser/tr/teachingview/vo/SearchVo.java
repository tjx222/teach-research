package com.tmser.tr.teachingview.vo;

import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.QueryObject;
import com.tmser.tr.uc.bo.UserSpace;

/**
 * 
 * <pre>
 *  查询条件vo
 * </pre>
 *
 * @author wangdawei
 * @version $Id: SearchVo.java, v 1.0 2016年4月8日 下午5:37:05 wangdawei Exp $
 */
@SuppressWarnings("serial")
public class SearchVo extends QueryObject{
	
	public static String TW_TEACHER_LIST = "tw_teacher_list"; //教师教研集合一览
	public static String TW_TEACHER_DETAIL = "tw_teacher_detail"; //教师教研详情一览
	public static String TW_GRADE = "tw_grade";     //年级教研情况一览
	public static String TW_SUBJECT = "tw_subject"; //学科教研情况一览
	public static String TW_MANAGER = "tw_manager"; //教学管理情况一览
	public static String TW_MANAGER_LIST = "tw_manager_list"; //教学管理情况详情

	private Integer userId; //用户id
	private Integer termId;//学期Id
	private Integer gradeId;//年级id
	private Integer subjectId;//学科id
	private Integer orgId; //机构id
	private Integer phaseId; //学段id
	private Integer schoolYear; //学段id
	private List<Integer> spaceIds; //用户空间ids
	private List<Integer> roleIds; //用户空间ids
	private String startTime; //学期开始时间
	private String endTime; //学期结束时间
	private Integer spaceId;//空间id
	private List<Integer> gradeIds;//年级集合
	private List<Integer> subjectIds;//学科集合
	private List<UserSpace> userSpaceList;//用户身份集合
	private String orderFlag;
	private String orderMode;//排列方式，升序or降序
	private String flagz;
	
	public List<UserSpace> getUserSpaceList() {
		return userSpaceList;
	}

	public Integer getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Integer schoolYear) {
		this.schoolYear = schoolYear;
	}

	public void setUserSpaceList(List<UserSpace> userSpaceList) {
		this.userSpaceList = userSpaceList;
	}

	public Integer getUserId() {
		return userId;
	}

	public List<Integer> getGradeIds() {
		return gradeIds;
	}

	public void setGradeIds(List<Integer> gradeIds) {
		this.gradeIds = gradeIds;
	}

	public List<Integer> getSubjectIds() {
		return subjectIds;
	}

	public void setSubjectIds(List<Integer> subjectIds) {
		this.subjectIds = subjectIds;
	}

	public Integer getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(Integer phaseId) {
		this.phaseId = phaseId;
	}

	public List<Integer> getSpaceIds() {
		return spaceIds;
	}

	public void setSpaceIds(List<Integer> spaceIds) {
		this.spaceIds = spaceIds;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getTermId() {
		return termId;
	}

	public void setTermId(Integer termId) {
		if(this.termId==null){
			this.termId = termId;
		}
	}

	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeIdIfNull(Integer gradeId) {
		if(this.gradeId==null){
			this.gradeId = gradeId;
		}
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}
	
	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}
	
	public void setSubjectIdIfNull(Integer subjectId) {
		if(this.subjectId==null){
			this.subjectId = subjectId;
		}
	}
	

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getSpaceId() {
		return spaceId;
	}

	public void setSpaceId(Integer spaceId) {
		this.spaceId = spaceId;
	}

	/** 
	 * Getter method for property <tt>flagz</tt>. 
	 * @return property value of flagz 
	 */
	public String getFlagz() {
		return flagz;
	}

	/**
	 * Setter method for property <tt>flagz</tt>.
	 * @param flagz value to be assigned to property flagz
	 */
	public void setFlagz(String flagz) {
		this.flagz = flagz;
	}

	/** 
	 * Getter method for property <tt>orderFlag</tt>. 
	 * @return property value of orderFlag 
	 */
	public String getOrderFlag() {
		return orderFlag;
	}

	/**
	 * Setter method for property <tt>orderFlag</tt>.
	 * @param orderFlag value to be assigned to property orderFlag
	 */
	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}

	/** 
	 * Getter method for property <tt>orderMode</tt>. 
	 * @return property value of orderMode 
	 */
	public String getOrderMode() {
		return orderMode;
	}

	/**
	 * Setter method for property <tt>orderMode</tt>.
	 * @param orderMode value to be assigned to property orderMode
	 */
	public void setOrderMode(String orderMode) {
		this.orderMode = orderMode;
	}
	/** 
	 * Getter method for property <tt>roleIds</tt>. 
	 * @return property value of roleIds 
	 */
	public List<Integer> getRoleIds() {
		return roleIds;
	}

	/**
	 * Setter method for property <tt>roleIds</tt>.
	 * @param roleIds value to be assigned to property roleIds
	 */
	public void setRoleIds(List<Integer> roleIds) {
		this.roleIds = roleIds;
	}

	@Override
	public boolean equals(final Object other) {
		  return (this == other);
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(userId)
					.append(termId)
					.append(gradeId)
					.append(subjectId)
					.append(phaseId)
					.append(orgId)
					.append(schoolYear)
					.append(startTime)
					.toHashCode();
	}

}
