/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.thesis.bo;

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
 * 教学文章 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Thesis.java, v 1.0 2016-01-14 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = Thesis.TABLE_NAME)
public class Thesis extends BaseObject {
	public static final String TABLE_NAME="teaching_thesis";
	
	public static final Integer SUBMIT = 1;//已经提交
	
	public static final Integer NOT_SUBMIT = 0;//未提交
	
	public static final Integer SCAN = 1;//已查阅 || 查阅意见已更新
	
	public static final Integer NOT_SCAN = 0;//未查阅 || 查阅意见未更新
	
		@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	/**
	 *用户id
	 **/
	@Column(name="user_id",nullable=false)
	private Integer userId;

	/**
	 *学科id
	 **/
	@Column(name="subject_id")
	private Integer subjectId;

	/**
	 *资源id
	 **/
	@Column(name="res_id",length=32)
	private String resId;

	/**
	 *论文标题
	 **/
	@Column(name="thesis_title",length=100)
	private String thesisTitle;

	/**
	 *论文类型
	 **/
	@Column(name="thesis_type",length=20)
	private String thesisType;

	/**
	 *文件名
	 **/
	@Column(name="file_name",length=200)
	private String fileName;

	/**
	 *文件后缀
	 **/
	@Column(name="file_suffix",length=10)
	private String fileSuffix;

	/**
	 *是否已分享   0：未分享  1：已分享
	 **/
	@Column(name="is_share")
	private Integer isShare;

	/**
	 *分享时间
	 **/
	@Column(name="share_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date shareTime;

	/**
	 *是否已评论   0：未评论  1：已评论未查看  2：已评论并已查看
	 **/
	@Column(name="is_comment")
	private Integer isComment;

	/**
	 *学期：0 上学期，1：下学期
	 **/
	@Column(name="school_term")
	private Integer schoolTerm;

	/**
	 *学段id
	 **/
	@Column(name="phase_id")
	private Integer phaseId;

	/**
	 *机构id
	 **/
	@Column(name="org_id")
	private Integer orgId;

	/**
	 *当前学年
	 **/
	@Column(name="school_year")
	private Integer schoolYear;
	
	/**
	 * 是否已查阅  0：未  1：已
	 */
	@Column(name="is_scan")
	private Integer isScan;
	/**
	 * 查阅意见已更新  0：未  1：已
	 */
	@Column(name="scan_up")
	private Integer scanUp;
	/**
	 * 查阅意见已更新  0：未  1：已
	 */
	@Column(name="scan_count")
	private Integer scanCount;
	/**
	 * 下载量
	 */
	@Column(name="down_num")
	private Integer downNum;
	/**
	 * 是否已提交（0：未提交，1：已提交）
	 */
	@Column(name="is_submit")
	private Integer isSubmit;
	/**
	 * 提交时间
	 */
	@Column(name="submit_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date submitTime;
	/** 
	 * Getter method for property <tt>scanCount</tt>. 
	 * @return property value of scanCount 
	 */
	public Integer getScanCount() {
		return scanCount;
	}

	/**
	 * Setter method for property <tt>scanCount</tt>.
	 * @param scanCount value to be assigned to property scanCount
	 */
	public void setScanCount(Integer scanCount) {
		this.scanCount = scanCount;
	}

	/** 
	 * Getter method for property <tt>isScan</tt>. 
	 * @return property value of isScan 
	 */
	public Integer getIsScan() {
		return isScan;
	}

	/**
	 * Setter method for property <tt>isScan</tt>.
	 * @param isScan value to be assigned to property isScan
	 */
	public void setIsScan(Integer isScan) {
		this.isScan = isScan;
	}

	/** 
	 * Getter method for property <tt>scanUp</tt>. 
	 * @return property value of scanUp 
	 */
	public Integer getScanUp() {
		return scanUp;
	}

	/**
	 * Setter method for property <tt>scanUp</tt>.
	 * @param scanUp value to be assigned to property scanUp
	 */
	public void setScanUp(Integer scanUp) {
		this.scanUp = scanUp;
	}

	/** 
	 * Getter method for property <tt>downNum</tt>. 
	 * @return property value of downNum 
	 */
	public Integer getDownNum() {
		return downNum;
	}

	/**
	 * Setter method for property <tt>downNum</tt>.
	 * @param downNum value to be assigned to property downNum
	 */
	public void setDownNum(Integer downNum) {
		this.downNum = downNum;
	}

	/** 
	 * Getter method for property <tt>isSubmit</tt>. 
	 * @return property value of isSubmit 
	 */
	public Integer getIsSubmit() {
		return isSubmit;
	}

	/**
	 * Setter method for property <tt>isSubmit</tt>.
	 * @param isSubmit value to be assigned to property isSubmit
	 */
	public void setIsSubmit(Integer isSubmit) {
		this.isSubmit = isSubmit;
	}

	/** 
	 * Getter method for property <tt>submitTime</tt>. 
	 * @return property value of submitTime 
	 */
	public Date getSubmitTime() {
		return submitTime;
	}

	/**
	 * Setter method for property <tt>submitTime</tt>.
	 * @param submitTime value to be assigned to property submitTime
	 */
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
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

	public void setSubjectId(Integer subjectId){
		this.subjectId = subjectId;
	}

	public Integer getSubjectId(){
		return this.subjectId;
	}

	public void setResId(String resId){
		this.resId = resId;
	}

	public String getResId(){
		return this.resId;
	}

	public void setThesisTitle(String thesisTitle){
		this.thesisTitle = thesisTitle;
	}

	public String getThesisTitle(){
		return this.thesisTitle;
	}

	public void setThesisType(String thesisType){
		this.thesisType = thesisType;
	}

	public String getThesisType(){
		return this.thesisType;
	}

	public void setFileName(String fileName){
		this.fileName = fileName;
	}

	public String getFileName(){
		return this.fileName;
	}

	public void setFileSuffix(String fileSuffix){
		this.fileSuffix = fileSuffix;
	}

	public String getFileSuffix(){
		return this.fileSuffix;
	}

	public void setIsShare(Integer isShare){
		this.isShare = isShare;
	}

	public Integer getIsShare(){
		return this.isShare;
	}

	public void setShareTime(Date shareTime){
		this.shareTime = shareTime;
	}

	public Date getShareTime(){
		return this.shareTime;
	}

	public void setIsComment(Integer isComment){
		this.isComment = isComment;
	}

	public Integer getIsComment(){
		return this.isComment;
	}

	public void setSchoolTerm(Integer schoolTerm){
		this.schoolTerm = schoolTerm;
	}

	public Integer getSchoolTerm(){
		return this.schoolTerm;
	}

	public void setPhaseId(Integer phaseId){
		this.phaseId = phaseId;
	}

	public Integer getPhaseId(){
		return this.phaseId;
	}

	public void setOrgId(Integer orgId){
		this.orgId = orgId;
	}

	public Integer getOrgId(){
		return this.orgId;
	}

	public void setSchoolYear(Integer schoolYear){
		this.schoolYear = schoolYear;
	}

	public Integer getSchoolYear(){
		return this.schoolYear;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof Thesis))
				return false;
			Thesis castOther = (Thesis) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


