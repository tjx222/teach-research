/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.lessonplan.bo;



import java.util.Date;

import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.BaseObject;

 /**
 * 课题信息表 Entity
 * <pre>
 *
 * </pre>
 *
 * @author wangdawei
 * @version $Id: LessonInfo.java, v 1.0 2015-03-05 wangdawei Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = LessonInfo.TABLE_NAME)
public class LessonInfo extends BaseObject {
	public static final String TABLE_NAME="lesson_info";
	public static final Integer FROM_ME=0;  //来自自发
	public static final Integer FROM_ACTIVITY=1; //来自集体备课
	public static final Integer FROM_SCHOOLACTIVITY=2;  //来自校际教研
	
		/**
	 *id
	 **/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	/**
	 *课题id(元数据id)
	 **/
	@Column(name="lesson_id",nullable=false)
	private String lessonId;

	/**
	 *课题名称
	 **/
	@Column(name="lesson_name")
	private String lessonName;

	/**
	 *书id
	 **/
	@Column(name="book_id")
	private String bookId;

	/**
	 * 书的简称
	 */
	@Column(name="book_shortname")
	private String bookShortname;
	/**
	 *用户id
	 **/
	@Column(name="user_id",nullable=false)
	private Integer userId;

	/**
	 *学年
	 **/
	@Column(name="school_year")
	private Integer schoolYear;

	/**
	 * 年级id
	 */
	@Column(name="grade_id")
	private Integer gradeId;
	
	/**
	 * 科目id
	 */
	@Column(name="subject_id")
	private Integer subjectId;
	
	/**
	 * 机构id
	 */
	@Column(name="org_id")
	private Integer orgId;
	/**
	 * 册别id
	 */
	@Column(name="fascicule_id")
	private Integer fasciculeId;
	
	/**
	 * 学期id
	 */
	@Column(name="term_id")
	private Integer termId;
	
	/**
	 * 学段id
	 */
	@Column(name="phase_id")
	private Integer phaseId;
	
	/**
	 *审阅意见数量
	 **/
	@Column(name="scan_count")
	private Integer scanCount;

	/**
	 *听课意见数量
	 **/
	@Column(name="visit_count")
	private Integer visitCount;

	/**
	 *评论意见数量
	 **/
	@Column(name="comment_count")
	private Integer commentCount;

	/**
	 * 已分享的教案数
	 */
	@Column(name="jiaoan_shareCount")
	private Integer jiaoanShareCount;
	
	/**
	 * 已分享的课后反思数
	 */
	@Column(name="fansi_shareCount")
	private Integer fansiShareCount;
	
	/**
	 * 已分享的课件数
	 */
	@Column(name="kejian_shareCount")
	private Integer kejianShareCount;
	
	/**
	 * 教按的提交数量
	 */
	@Column(name="jiaoan_submitCount")
	private Integer jiaoanSubmitCount;
	
	/**
	 * 课件的提交数量
	 */
	@Column(name="kejian_submitCount")
	private Integer kejianSubmitCount;
	
	/**
	 * 反思的提交数量
	 */
	@Column(name="fansi_submitCount")
	private Integer fansiSubmitCount;
	
	/**
	 * 教案数量
	 */
	@Column(name="jiaoan_count")
	private Integer jiaoanCount;
	
	/**
	 * 课件数量
	 */
	@Column(name="kejian_count")
	private Integer kejianCount;
	
	/**
	 * 反思数量
	 */
	@Column(name="fansi_count")
	private Integer fansiCount;
	
	/**
	 * 提交时间
	 */
	@Column(name="submit_time")
	private Date submitTime;
	
	/**
	 * 分享时间
	 */
	@Column(name="share_time")
	private Date shareTime;
	/**
	 *审阅意见已更新
	 **/
	@Column(name="scan_up")
	private Boolean scanUp;

	/**
	 *听课意见已更新
	 **/
	@Column(name="visit_up")
	private Boolean visitUp;

	/**
	 *评论意见已更新
	 **/
	@Column(name="comment_up")
	private Boolean commentUp;

	/**
	 * 课题的来源  0：自发，1：来自接收集体备课 2：来自接收校际教研
	 */
	@Column(name="current_from")
	private Integer currentFrom;  
	
	/**
	 * 客户端id  判断是否为离线端过来的数据 
	 */
	@Column(name="client_id")
	private String clientId;
	
	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getBookShortname() {
		return bookShortname;
	}

	public void setBookShortname(String bookShortname) {
		this.bookShortname = bookShortname;
	}

	public Integer getFasciculeId() {
		return fasciculeId;
	}

	public void setFasciculeId(Integer fasciculeId) {
		this.fasciculeId = fasciculeId;
	}

	public Integer getGradeId() {
		return gradeId;
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

	public Integer getJiaoanShareCount() {
		return jiaoanShareCount;
	}

	public void setJiaoanShareCount(Integer jiaoanShareCount) {
		this.jiaoanShareCount = jiaoanShareCount;
	}

	public Integer getFansiShareCount() {
		return fansiShareCount;
	}

	public void setFansiShareCount(Integer fansiShareCount) {
		this.fansiShareCount = fansiShareCount;
	}

	public Integer getKejianShareCount() {
		return kejianShareCount;
	}

	public void setKejianShareCount(Integer kejianShareCount) {
		this.kejianShareCount = kejianShareCount;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public Date getShareTime() {
		return shareTime;
	}

	public void setShareTime(Date shareTime) {
		this.shareTime = shareTime;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setLessonId(String lessonId){
		this.lessonId = lessonId;
	}

	public String getLessonId(){
		return this.lessonId;
	}

	public void setLessonName(String lessonName){
		this.lessonName = lessonName;
	}

	public String getLessonName(){
		return this.lessonName;
	}

	public void setBookId(String bookId){
		this.bookId = bookId;
	}

	public String getBookId(){
		return this.bookId;
	}

	public void setUserId(Integer userId){
		this.userId = userId;
	}

	public Integer getUserId(){
		return this.userId;
	}

	public void setSchoolYear(Integer schoolYear){
		this.schoolYear = schoolYear;
	}

	public Integer getSchoolYear(){
		return this.schoolYear;
	}

	public void setScanCount(Integer scanCount){
		this.scanCount = scanCount;
	}

	public Integer getScanCount(){
		return this.scanCount;
	}

	public void setVisitCount(Integer visitCount){
		this.visitCount = visitCount;
	}

	public Integer getVisitCount(){
		return this.visitCount;
	}

	public void setCommentCount(Integer commentCount){
		this.commentCount = commentCount;
	}

	public Integer getCommentCount(){
		return this.commentCount;
	}

	public Boolean getScanUp() {
		return scanUp;
	}

	public void setScanUp(Boolean scanUp) {
		this.scanUp = scanUp;
	}

	public Boolean getVisitUp() {
		return visitUp;
	}

	public void setVisitUp(Boolean visitUp) {
		this.visitUp = visitUp;
	}

	public Boolean getCommentUp() {
		return commentUp;
	}

	public void setCommentUp(Boolean commentUp) {
		this.commentUp = commentUp;
	}

	public Integer getJiaoanSubmitCount() {
		return jiaoanSubmitCount;
	}

	public void setJiaoanSubmitCount(Integer jiaoanSubmitCount) {
		this.jiaoanSubmitCount = jiaoanSubmitCount;
	}

	public Integer getKejianSubmitCount() {
		return kejianSubmitCount;
	}

	public void setKejianSubmitCount(Integer kejianSubmitCount) {
		this.kejianSubmitCount = kejianSubmitCount;
	}

	public Integer getFansiSubmitCount() {
		return fansiSubmitCount;
	}

	public void setFansiSubmitCount(Integer fansiSubmitCount) {
		this.fansiSubmitCount = fansiSubmitCount;
	}
	
	public Integer getJiaoanCount() {
		return jiaoanCount;
	}

	public void setJiaoanCount(Integer jiaoanCount) {
		this.jiaoanCount = jiaoanCount;
	}

	public Integer getKejianCount() {
		return kejianCount;
	}

	public void setKejianCount(Integer kejianCount) {
		this.kejianCount = kejianCount;
	}

	public Integer getFansiCount() {
		return fansiCount;
	}

	public void setFansiCount(Integer fansiCount) {
		this.fansiCount = fansiCount;
	}

	public Integer getTermId() {
		return termId;
	}

	public void setTermId(Integer termId) {
		this.termId = termId;
	}

	public Integer getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(Integer phaseId) {
		this.phaseId = phaseId;
	}

	public Integer getCurrentFrom() {
		return currentFrom;
	}

	public void setCurrentFrom(Integer currentFrom) {
		this.currentFrom = currentFrom;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof LessonInfo))
				return false;
			LessonInfo castOther = (LessonInfo) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


