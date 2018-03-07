/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.bo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import com.tmser.tr.common.bo.BaseObject;

 /**
 * 集体备课活动实体 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Activity.java, v 1.0 2015-03-06 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = Activity.TABLE_NAME)
public class Activity extends BaseObject {
	public static final String TABLE_NAME="jy_activity";
	public static final Integer TBJA = 1; //同备教案
	public static final Integer ZTYT = 2; //主题研讨
	public static final Integer SPJY = 3; //视频教研
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="type_id",nullable=false)
	private Integer typeId;

	@Column(name="type_name")
	private String typeName;

	@Column(name="activity_name",length=100)
	private String activityName;

	/**
	 * 活动发文状态，0：草稿，1：正式发文
	 */
	@Column(name="status")
	private Integer status;
	/**
	 *示例：
            &11&
            &11&12&
	 **/
	@Column(name="subject_ids")
	private String subjectIds;

	/**
	 *示例：
            语文、数学
	 **/
	@Column(name="subject_name")
	private String subjectName;

	/**
	 *示例：
            &11&
            &11&12&
	 **/
	@Column(name="grade_ids")
	private String gradeIds;
	
	/**
	 *示例：
            一、二
	 **/
	@Column(name="grade_name")
	private String gradeName;
	
	@Column(name="info_id")
	private Integer infoId;
	
	@Column(name="organize_user_id")
	private Integer organizeUserId;

	@Column(name="organize_user_name")
	private String organizeUserName;

	@Column(name="space_id")
	private Integer spaceId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@Column(name="start_time")
	private Date startTime;

	@Column(name="main_user_id")
	private Integer mainUserId;

	@Column(name="main_user_name")
	private String mainUserName;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@Column(name="end_time")
	private Date endTime;

	@Column(name="comments_num")
	private Integer commentsNum;

	@Column(name="create_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	@Column(name="release_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date releaseTime;

	@Column(name="remark",length=300)
	private String remark;

	@Column(name="url",length=200)
	private String url;
	/**
	 *	0 未结束
		1 已结束
	 **/
	@Column(name="is_over")
	private Boolean isOver;

	/**
	 * 是否提交  0：未，1：已
	 */
	@Column(name="is_submit")
	private Boolean isSubmit;
	/**
	 *0 未批阅（审核）查阅
	  1 已批阅（审核）查阅
	 **/
	@Column(name="is_audit")
	private Boolean isAudit;

	/**
	 * 查阅意见已更新
	 */
	@Column(name="audit_up")
	private Boolean auditUp;
	
	@Column(name="main_user_grade_id")
	private Integer mainUserGradeId;
	
	@Column(name="main_user_subject_id")
	private Integer mainUserSubjectId; 
	
	@Column(name="organize_subject_id")
	private Integer organizeSubjectId;
	
	@Column(name="organize_grade_id")
	private Integer organizeGradeId;
	
	
	
	/** 
	 * Getter method for property <tt>organizeSubjectId</tt>. 
	 * @return property value of organizeSubjectId 
	 */
	public Integer getOrganizeSubjectId() {
		return organizeSubjectId;
	}

	/**
	 * Setter method for property <tt>organizeSubjectId</tt>.
	 * @param organizeSubjectId value to be assigned to property organizeSubjectId
	 */
	public void setOrganizeSubjectId(Integer organizeSubjectId) {
		this.organizeSubjectId = organizeSubjectId;
	}

	/** 
	 * Getter method for property <tt>organizeGradeId</tt>. 
	 * @return property value of organizeGradeId 
	 */
	public Integer getOrganizeGradeId() {
		return organizeGradeId;
	}

	/**
	 * Setter method for property <tt>organizeGradeId</tt>.
	 * @param organizeGradeId value to be assigned to property organizeGradeId
	 */
	public void setOrganizeGradeId(Integer organizeGradeId) {
		this.organizeGradeId = organizeGradeId;
	}

	public Boolean getAuditUp() {
		return auditUp;
	}

	public void setAuditUp(Boolean auditUp) {
		this.auditUp = auditUp;
	}

	/**
	 * 是否分享
	 * false：未分享，true：已分享 
	 **/
	@Column(name="is_share")
	private Boolean isShare;
	
	/**
	 * 分享时间
	 */
	@Column(name="share_time")
	private Date shareTime;
	
	/**
	 * 是否评论
	 * false：未评论，true：已评论
	 **/
	@Column(name="is_comment")
	private Boolean isComment;
	
	/**
	 * 是否已将整理好的教案发送给参与人
	 */
	@Column(name="is_send")
	private Boolean isSend;
	
	/**
	 * 学年
	 */
	@Column(name="school_year")
	private Integer schoolYear;
	
	/**
	 * 学期  0：上  1：下
	 */
	@Column(name="term")
	private Integer term;
	
	/**
	 * 学段id
	 */
	@Column(name="phase_id")
	private Integer phaseId;
	
	/**
	 * 机构id
	 */
	@Column(name="org_id")
	private Integer orgId;
	
	public Boolean getIsSend() {
		return isSend;
	}

	public void setIsSend(Boolean isSend) {
		this.isSend = isSend;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public Integer getMainUserGradeId() {
		return mainUserGradeId;
	}

	public void setMainUserGradeId(Integer mainUserGradeId) {
		this.mainUserGradeId = mainUserGradeId;
	}

	public Integer getMainUserSubjectId() {
		return mainUserSubjectId;
	}

	public void setMainUserSubjectId(Integer mainUserSubjectId) {
		this.mainUserSubjectId = mainUserSubjectId;
	}

	public void setTypeId(Integer typeId){
		this.typeId = typeId;
	}

	public Integer getTypeId(){
		return this.typeId;
	}

	public void setTypeName(String typeName){
		this.typeName = typeName;
	}

	public String getTypeName(){
		return this.typeName;
	}

	public void setActivityName(String activityName){
		this.activityName = activityName;
	}

	public String getActivityName(){
		return this.activityName;
	}

	public void setSubjectIds(String subjectIds){
		this.subjectIds = subjectIds;
	}

	public String getSubjectIds(){
		return this.subjectIds;
	}

	public void setSubjectName(String subjectName){
		this.subjectName = subjectName;
	}

	public String getSubjectName(){
		return this.subjectName;
	}

	public void setGradeIds(String gradeIds){
		this.gradeIds = gradeIds;
	}

	public String getGradeIds(){
		return this.gradeIds;
	}

	public void setGradeName(String gradeName){
		this.gradeName = gradeName;
	}

	public String getGradeName(){
		return this.gradeName;
	}

	public void setOrganizeUserId(Integer organizeUserId){
		this.organizeUserId = organizeUserId;
	}

	public Integer getOrganizeUserId(){
		return this.organizeUserId;
	}

	public void setOrganizeUserName(String organizeUserName){
		this.organizeUserName = organizeUserName;
	}

	public String getOrganizeUserName(){
		return this.organizeUserName;
	}

	public void setStartTime(Date startTime){
		this.startTime = startTime;
	}

	public Date getStartTime(){
		return this.startTime;
	}

	public void setMainUserId(Integer mainUserId){
		this.mainUserId = mainUserId;
	}

	public Integer getMainUserId(){
		return this.mainUserId;
	}

	public void setMainUserName(String mainUserName){
		this.mainUserName = mainUserName;
	}

	public String getMainUserName(){
		return this.mainUserName;
	}

	public void setEndTime(Date endTime){
		this.endTime = endTime;
	}

	public Date getEndTime(){
		return this.endTime;
	}

	public void setCommentsNum(Integer commentsNum){
		this.commentsNum = commentsNum;
	}

	public Integer getCommentsNum(){
		return this.commentsNum;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return this.createTime;
	}

	public void setReleaseTime(Date releaseTime){
		this.releaseTime = releaseTime;
	}

	public Date getReleaseTime(){
		return this.releaseTime;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return this.remark;
	}

	public Boolean getIsShare() {
		return isShare;
	}

	public void setIsShare(Boolean isShare) {
		this.isShare = isShare;
	}

	public Boolean getIsComment() {
		return isComment;
	}

	public void setIsComment(Boolean isComment) {
		this.isComment = isComment;
	}

	public Boolean getIsOver() {
		return isOver;
	}

	public void setIsOver(Boolean isOver) {
		this.isOver = isOver;
	}

	public Boolean getIsSubmit() {
		return isSubmit;
	}

	public void setIsSubmit(Boolean isSubmit) {
		this.isSubmit = isSubmit;
	}

	public Boolean getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(Boolean isAudit) {
		this.isAudit = isAudit;
	}

	public Integer getInfoId() {
		return infoId;
	}

	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	public Integer getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Integer schoolYear) {
		this.schoolYear = schoolYear;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Integer getSpaceId() {
		return spaceId;
	}

	public void setSpaceId(Integer spaceId) {
		this.spaceId = spaceId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(Integer phaseId) {
		this.phaseId = phaseId;
	}

	public Date getShareTime() {
		return shareTime;
	}

	public void setShareTime(Date shareTime) {
		this.shareTime = shareTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof Activity))
				return false;
			Activity castOther = (Activity) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


