/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.recordbag.bo;

import java.util.Date;




import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.BaseObject;

 /**
 * 成长档案袋 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Recordbag.java, v 1.0 2015-04-13 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = Recordbag.TABLE_NAME)
public class Recordbag extends BaseObject {
	public static final String TABLE_NAME="teacher_record_bag";
	// 原名教学设计
	public static String JXSJ = "我的教案";
	
	public static String ZZKJ = "自制课件";
	
	public static String JXFS = "教学反思";
	
	public static String JYHD = "教研活动";
	
	public static String JXWZ = "教学文章";
	
	public static String JHZJ = "计划总结";
	
	public static String TKJL = "听课记录";
	
	/**
	 *主键
	 **/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	/**
	 *档案袋的名称
	 **/
	@Column(name="name",nullable=false)
	private String name;

	/**
	 *排序字段
	 **/
	@Column(name="sort")
	private Integer sort;

	/**
	 *区别系统档案袋（0）和自己创建的档案袋（1）
	 **/
	@Column(name="type",nullable=false)
	private Integer type;

	/**
	 *分享状态：未分享（0）、已分享（1）
	 **/
	@Column(name="share",nullable=false)
	private Integer share;

	/**
	 *是否已删除：未删除（0）、已删除（1）
	 **/
	@Column(name="del",nullable=false)
	private Integer delete;

	/**
	 *简介
	 **/
	@Column(name="long_desc")
	private String desc;

	/**
	 *查阅状态：0未查阅、1已查阅
	 **/
	@Column(name="status",nullable=false)
	private Integer status;

	/**
	 *教师Id
	 **/
	@Column(name="teacher_id",nullable=false)
	private String teacherId;

	/**
	 *上级是否可见：0不可见、1可见
	 **/
	@Column(name="org_status",nullable=false)
	private Integer orgStatus;
	
	/**
	 *学校id
	 **/
	@Column(name="org_id",nullable=false)
	private Integer orgId;
	
	/**
	 *分享时间
	 **/
	@Column(name="share_time")
	private Date shareTime;

	/**
	 *创建时间
	 **/
	@Column(name="create_time")
	private Date createTime;

	/**
	 *修改时间
	 **/
	@Column(name="modify_time")
	private Date modifyTime;

	/**
	 *对应菜单的id(用于权限的判断)
	 **/
	@Column(name="menu_id")
	private Integer menuId;
	
	/**
	 *用户空间id
	 **/
	@Transient
	private Integer spaceId;
	
	/**
	 *分享状态：未提交（0）、已提交（1）
	 **/
	@Column(name="submit",nullable=false)
	private Integer submit;
	
	/**
	 *是否被评论过  0未评论 1已评论
	 **/
	@Column(name="pinglun",nullable=false)
	private Integer pinglun;
	
	/**
	 *是否已查看过评阅意见：0未查看，1已查看
	 **/
	@Column(name="is_status",nullable=false)
	private Integer isStatus;
	
	/**
	 *是否查看过评论状态：0未查看,1已查看
	 **/
	@Column(name="is_pinglun",nullable=false)
	private Integer isPinglun;
	
	/**
	 *年级ID
	 **/
	@Column(name="grade_id")
	private Integer gradeId;

	/**
	 *年级
	 **/
	@Column(name="grade")
	private String grade;

	/**
	 *学科ID
	 **/
	@Column(name="subject_id")
	private Integer subjectId;

	/**
	 *学科
	 **/
	@Column(name="subject")
	private String subject;
	
	
	/**
	 * 档案袋资源数
	 * @param id
	 */
	@Column(name="res_count")
	private Integer resCount;
	

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public Date getShareTime() {
		return shareTime;
	}

	public void setShareTime(Date shareTime) {
		this.shareTime = shareTime;
	}

	public void setSort(Integer sort){
		this.sort = sort;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getSort(){
		return this.sort;
	}

	public void setType(Integer type){
		this.type = type;
	}

	public Integer getType(){
		return this.type;
	}

	public void setShare(Integer share){
		this.share = share;
	}

	public Integer getShare(){
		return this.share;
	}

	public void setDelete(Integer delete){
		this.delete = delete;
	}

	public Integer getDelete(){
		return this.delete;
	}

	public void setDesc(String desc){
		this.desc = desc;
	}

	public String getDesc(){
		return this.desc;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setTeacherId(String teacherId){
		this.teacherId = teacherId;
	}

	public String getTeacherId(){
		return this.teacherId;
	}

	public void setOrgStatus(Integer orgStatus){
		this.orgStatus = orgStatus;
	}

	public Integer getOrgStatus(){
		return this.orgStatus;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return this.createTime;
	}

	public void setModifyTime(Date modifyTime){
		this.modifyTime = modifyTime;
	}

	public Date getModifyTime(){
		return this.modifyTime;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	
	public Integer getSpaceId() {
		return spaceId;
	}

	public void setSpaceId(Integer spaceId) {
		this.spaceId = spaceId;
	}
	
	public Integer getSubmit() {
		return submit;
	}

	public void setSubmit(Integer submit) {
		this.submit = submit;
	}
	
	public Integer getPinglun() {
		return pinglun;
	}

	public void setPinglun(Integer pinglun) {
		this.pinglun = pinglun;
	}

	public Integer getIsStatus() {
		return isStatus;
	}

	public void setIsStatus(Integer isStatus) {
		this.isStatus = isStatus;
	}

	public Integer getIsPinglun() {
		return isPinglun;
	}

	public void setIsPinglun(Integer isPinglun) {
		this.isPinglun = isPinglun;
	}
	
	

	/** 
	 * Getter method for property <tt>gradeId</tt>. 
	 * @return property value of gradeId 
	 */
	public Integer getGradeId() {
		return gradeId;
	}

	/**
	 * Setter method for property <tt>gradeId</tt>.
	 * @param gradeId value to be assigned to property gradeId
	 */
	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	/** 
	 * Getter method for property <tt>grade</tt>. 
	 * @return property value of grade 
	 */
	public String getGrade() {
		return grade;
	}

	/**
	 * Setter method for property <tt>grade</tt>.
	 * @param grade value to be assigned to property grade
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}

	/** 
	 * Getter method for property <tt>subjectId</tt>. 
	 * @return property value of subjectId 
	 */
	public Integer getSubjectId() {
		return subjectId;
	}

	/**
	 * Setter method for property <tt>subjectId</tt>.
	 * @param subjectId value to be assigned to property subjectId
	 */
	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	/** 
	 * Getter method for property <tt>subject</tt>. 
	 * @return property value of subject 
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Setter method for property <tt>subject</tt>.
	 * @param subject value to be assigned to property subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof Recordbag))
				return false;
			Recordbag castOther = (Recordbag) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
	
	public Integer getResCount() {
		return resCount;
	}

	public void setResCount(Integer resCount) {
		this.resCount = resCount;
	}

	public static int  switchResType(String name){
		//0 教学设计，1 自制课件，2 教学反思，3 教研活动，4 教学文章，5 计划总结，6 听课记录
		if(name.equals(Recordbag.JXSJ)){
			return 0;
		}else	if(name.equals(Recordbag.ZZKJ)){
			return 1;
		}else	if(name.equals(Recordbag.JXFS)){
			return 2;
		}else	if(name.equals(Recordbag.JYHD)){
			return 3;
		}else	if(name.equals(Recordbag.JXWZ)){
			return 4;
		}else	if(name.equals(Recordbag.JHZJ)){
			return 5;
		}else if(name.equals(Recordbag.TKJL)){
			return 6;
		}else {
			return 7;
		}
		
	}
}


