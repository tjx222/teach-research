/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.plainsummary.bo;



import java.util.Date;

import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.BaseObject;

 /**
 * 计划总结 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: PlainSummary.java, v 1.0 2015-03-19 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = PlainSummary.TABLE_NAME)
public class PlainSummary extends BaseObject {
	public static final String TABLE_NAME="jy_plain_summary";
	
	@Id
	@Column(name="id")
	private Integer id;

	/**
	 *用户id
	 **/
	@Column(name="user_id",nullable=false)
	private Integer userId;
	
	@Column(name="user_role_id",nullable=false)
	private Integer userRoleId;
	
	@Transient 
	private Integer userSpaceId;

	/**
	 *标题
	 **/
	@Column(name="title",nullable=false)
	private String title;

	/**
	 *类别,1:个人计划；2：个人总结；3、当前身份计划；4、当前身份总结
	 **/
	@Column(name="category",nullable=false)
	private Integer category;
	
	/**
	 * 学科
	 */
	@Column(name="subject_id",nullable=false)
	private Integer subjectId;
	
	/**
	 *创建时间
	 **/
	@Column(name="share_time")
	private Date shareTime;
	/**
	 * 角色
	 */
	@Column(name="role_id",nullable=false)
	private Integer roleId;
	
	/**
	 * 年级
	 */
	@Column(name="grade_id",nullable=false)
	private Integer gradeId;
	
	/**
	 *内容key
	 **/
	@Column(name="content_file_key",nullable=false)
	private String contentFileKey;

	/**
	 *内容文件的名称
	 **/
	@Column(name="content_file_name",nullable=false)
	private String contentFileName;

	/**
	 *内容文件的类型
	 **/
	@Column(name="content_file_type",nullable=false)
	private String contentFileType;

	/**
	 *是否已提交，1：已提交，0未提交
	 **/
	@Column(name="is_submit",nullable=false)
	private Integer isSubmit;

	/**
	 *是否已分享，1：已分享，0未分享
	 **/
	@Column(name="is_share",nullable=false)
	private Integer isShare;

	/**
	 *是否已发布，1：已发布，0未发布
	 **/
	@Column(name="is_punish",nullable=false)
	private Integer isPunish;

	/**
	 *是否已查阅，2、存在已阅，1：存在新查阅，0未查阅
	 **/
	@Column(name="is_check",nullable=false)
	private Integer isCheck;

	/**
	 *是否已评论，2、存在已评论，1：存在新评论，0未评论
	 **/
	@Column(name="is_review",nullable=false)
	private Integer isReview;
	
	/**
	 * 学期，1：下学期，0上学期
	 **/
	@Column(name="term")
	private Integer term;
	
	/**
	 * 当前学年
	 **/
	@Column(name="school_year")
	private Integer schoolYear;

	/**
	 * 组织机构id
	 **/
	@Column(name="org_id")
	private Integer orgId;
	
	/**
	 * 学段id
	 **/
	@Column(name="phase_id")
	private Integer phaseId;
	
	/**
	 * 评论数目
	 **/
	@Column(name="review_num")
	private Integer reviewNum;
	
	/**
	 * 提交时间
	 */
	@Column(name="submit_time")
	private Date submitTime;
	
	/**
	 * 发布时间
	 */
	@Column(name="punish_time")
	private Date punishTime;
	
	/**
	 * 发布范围
	 */
	@Column(name="punish_range")
	private Integer punishRange;
	
	/**
	 * 教研圈id
	 */
	@Column(name="school_circle_id")
	private Integer schoolCircleId;
	
	/**
	 * 标签
	 */
	@Column(name="label")
	private String label;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setUserId(Integer userId){
		this.userId = userId;
	}

	public Integer getUserId(){
		return this.userId;
	}



	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return this.title;
	}

	public void setCategory(Integer category){
		this.category = category;
	}

	public Integer getCategory(){
		return this.category;
	}

	public void setContentFileKey(String contentFileKey){
		this.contentFileKey = contentFileKey;
	}

	public String getContentFileKey(){
		return this.contentFileKey;
	}

	public Date getShareTime() {
		return shareTime;
	}

	public void setShareTime(Date shareTime) {
		this.shareTime = shareTime;
	}

	public void setContentFileName(String contentFileName){
		this.contentFileName = contentFileName;
	}

	public String getContentFileName(){
		return this.contentFileName;
	}

	public void setContentFileType(String contentFileType){
		this.contentFileType = contentFileType;
	}

	public String getContentFileType(){
		return this.contentFileType;
	}

	public void setIsSubmit(Integer isSubmit){
		this.isSubmit = isSubmit;
	}

	public Integer getIsSubmit(){
		return this.isSubmit;
	}

	public void setIsShare(Integer isShare){
		this.isShare = isShare;
	}

	public Integer getIsShare(){
		return this.isShare;
	}

	public void setIsPunish(Integer isPunish){
		this.isPunish = isPunish;
	}

	public Integer getIsPunish(){
		return this.isPunish;
	}

	public void setIsCheck(Integer isCheck){
		this.isCheck = isCheck;
	}

	public Integer getIsCheck(){
		return this.isCheck;
	}

	public void setIsReview(Integer isReview){
		this.isReview = isReview;
	}

	public Integer getIsReview(){
		return this.isReview;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof PlainSummary))
				return false;
			PlainSummary castOther = (PlainSummary) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Integer schoolYear) {
		this.schoolYear = schoolYear;
	}

	public Integer getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(Integer phaseId) {
		this.phaseId = phaseId;
	}

	public Integer getReviewNum() {
		return reviewNum;
	}

	public void setReviewNum(Integer reviewNum) {
		this.reviewNum = reviewNum;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public Date getPunishTime() {
		return punishTime;
	}

	public void setPunishTime(Date punishTime) {
		this.punishTime = punishTime;
	}

	public Integer getPunishRange() {
		return punishRange;
	}

	public void setPunishRange(Integer punishRange) {
		this.punishRange = punishRange;
	}

	public Integer getSchoolCircleId() {
		return schoolCircleId;
	}

	public void setSchoolCircleId(Integer schoolCircleId) {
		this.schoolCircleId = schoolCircleId;
	}


	/** 
	 * Getter method for property <tt>userSpaceId</tt>. 
	 * @return property value of userSpaceId 
	 */
	public Integer getUserSpaceId() {
		return userSpaceId;
	}

	/**
	 * Setter method for property <tt>userSpaceId</tt>.
	 * @param userSpaceId value to be assigned to property userSpaceId
	 */
	public void setUserSpaceId(Integer userSpaceId) {
		this.userSpaceId = userSpaceId;
	}

	/** 
	 * Getter method for property <tt>userRoleId</tt>. 
	 * @return property value of userRoleId 
	 */
	public Integer getUserRoleId() {
		return userRoleId;
	}

	/**
	 * Setter method for property <tt>userRoleId</tt>.
	 * @param userRoleId value to be assigned to property userRoleId
	 */
	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}
}


