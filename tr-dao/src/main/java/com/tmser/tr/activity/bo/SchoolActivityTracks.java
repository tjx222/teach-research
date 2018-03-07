/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.bo;



import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.BaseObject;

 /**
 * 校际教研教案修改留痕 Entity
 * <pre>
 *
 * </pre>
 *
 * @author wangdawei
 * @version $Id: SchoolActivityTracks.java, v 1.0 2015-05-28 wangdawei Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = SchoolActivityTracks.TABLE_NAME)
public class SchoolActivityTracks extends BaseObject {
	public static final String TABLE_NAME="school_activity_tracks";
	public static final Integer YIJIAN = 0; //教案意见
	public static final Integer ZHENGLI = 1; //教案整理
	public static final Integer ZHUBEI = 2; //原始教案
		/**
	 *主键id
	 **/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	/**
	 *集体备课id
	 **/
	@Column(name="activity_id")
	private Integer activityId;

	/**
	 *教案id
	 **/
	@Column(name="plan_id")
	private Integer planId;

	/**
	 *教案名称
	 **/
	@Column(name="plan_name",length=100)
	private String planName;

	/**
	 *修改类型  0：教案意见，1：教案整理
	 **/
	@Column(name="edit_type")
	private Integer editType;

	/**
	 *课题id
	 **/
	@Column(name="lesson_id",length=32)
	private String lessonId;

	/**
	 *资源id
	 **/
	@Column(name="res_id",length=32)
	private String resId;

	/**
	 *课时id
	 **/
	@Column(name="hours_id",length=10)
	private String hoursId;

	/**
	 *课时排序值
	 **/
	@Column(name="order_value")
	private Integer orderValue;

	/**
	 *用户id
	 **/
	@Column(name="user_id")
	private Integer userId;

	/**
	 *用户真实姓名
	 **/
	@Column(name="user_name",length=20)
	private String userName;

	/**
	 *科目id
	 **/
	@Column(name="subject_id")
	private Integer subjectId;
	
	/**
	 * 年级id
	 */
	@Column(name="grade_id")
	private Integer gradeId;

	/**
	 * 用户空间id
	 */
	@Column(name="space_id")
	private Integer spaceId;
	
	/**
	 *学年
	 **/
	@Column(name="school_year")
	private Integer schoolYear;

	/**
	 * 机构id
	 */
	@Column(name="org_id")
	private Integer orgId;

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setActivityId(Integer activityId){
		this.activityId = activityId;
	}

	public Integer getActivityId(){
		return this.activityId;
	}

	public void setPlanId(Integer planId){
		this.planId = planId;
	}

	public Integer getPlanId(){
		return this.planId;
	}

	public void setPlanName(String planName){
		this.planName = planName;
	}

	public String getPlanName(){
		return this.planName;
	}

	public void setEditType(Integer editType){
		this.editType = editType;
	}

	public Integer getEditType(){
		return this.editType;
	}

	public void setLessonId(String lessonId){
		this.lessonId = lessonId;
	}

	public String getLessonId(){
		return this.lessonId;
	}

	public void setResId(String resId){
		this.resId = resId;
	}

	public String getResId(){
		return this.resId;
	}

	public void setHoursId(String hoursId){
		this.hoursId = hoursId;
	}

	public String getHoursId(){
		return this.hoursId;
	}

	public void setOrderValue(Integer orderValue){
		this.orderValue = orderValue;
	}

	public Integer getOrderValue(){
		return this.orderValue;
	}

	public void setUserId(Integer userId){
		this.userId = userId;
	}

	public Integer getUserId(){
		return this.userId;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return this.userName;
	}

	public void setSubjectId(Integer subjectId){
		this.subjectId = subjectId;
	}

	public Integer getSubjectId(){
		return this.subjectId;
	}

	public void setSchoolYear(Integer schoolYear){
		this.schoolYear = schoolYear;
	}

	public Integer getSchoolYear(){
		return this.schoolYear;
	}
	
	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public Integer getSpaceId() {
		return spaceId;
	}

	public void setSpaceId(Integer spaceId) {
		this.spaceId = spaceId;
	}

	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof SchoolActivityTracks))
				return false;
			SchoolActivityTracks castOther = (SchoolActivityTracks) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


