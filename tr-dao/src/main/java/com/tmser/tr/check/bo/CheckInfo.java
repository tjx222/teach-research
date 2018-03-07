/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.check.bo;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import com.tmser.tr.common.bo.QueryObject;

 /**
 * 查阅记录 Entity
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: CheckInfo.java, v 1.0 2016-01-14 tmser Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = CheckInfo.TABLE_NAME)
public class CheckInfo extends QueryObject {
	public static final String TABLE_NAME="check_info";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	/**
	 *资源标题
	 **/
	@Column(name="title",length=128)
	private String title;

	/**
	 *资源所属年级
	 **/
	@Column(name="grade_id")
	private Integer gradeId;

	/**
	 *资源所属学科
	 **/
	@Column(name="subject_id")
	private Integer subjectId;

	/**
	 *被查阅资源id
	 **/
	@Column(name="res_id")
	private Integer resId;

	/**
	 *资源类型
	 **/
	@Column(name="res_type")
	private Integer resType;

	/**
	 *资源作者
	 **/
	@Column(name="author",length=32)
	private String author;

	/**
	 *资源作者id
	 **/
	@Column(name="author_id")
	private Integer authorId;

	/**
	 *查阅者
	 **/
	@Column(name="username",length=32)
	private String username;

	/**
	 *查阅者id
	 **/
	@Column(name="user_id")
	private Integer userId;

	/**
	 *是否有更新,0 无更新  1 有更新
	 **/
	@Column(name="is_update")
	private Boolean isUpdate;

	@Column(name="createtime")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createtime;

	/**
	 *所属学年
	 **/
	@Column(name="school_year")
	private Integer schoolYear;

	/**
	 *查阅者用户空间id
	 **/
	@Column(name="space_id")
	private Integer spaceId;

	/**
	 *学段
	 **/
	@Column(name="phase")
	private Integer phase;

	@Column(name="term")
	private Integer term;

	@Column(name="has_optinion")
	private Boolean hasOptinion;
	
	/**
	 * 查阅等级
	 */
	@Column(name="level")
	private Integer level;
	
	/**
	 * 资源类型
	 */
	private List<Integer> resTypes;

	/**
	 * 用户空间id
	 */
	private Integer userSpaceId;
	
	private Integer roleId;
	
	
	/** 
	 * Getter method for property <tt>roleId</tt>. 
	 * @return property value of roleId 
	 */
	public Integer getRoleId() {
		return roleId;
	}

	/**
	 * Setter method for property <tt>roleId</tt>.
	 * @param roleId value to be assigned to property roleId
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	/**
	 * 是否只显示（展开收起等头部信息）
	 */
	@Transient
	private Boolean titleShow;

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getId(){
		return this.id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return this.title;
	}

	public void setGradeId(Integer gradeId){
		this.gradeId = gradeId;
	}

	public Integer getGradeId(){
		return this.gradeId;
	}

	public void setSubjectId(Integer subjectId){
		this.subjectId = subjectId;
	}

	public Integer getSubjectId(){
		return this.subjectId;
	}

	public void setResId(Integer resId){
		this.resId = resId;
	}

	public Integer getResId(){
		return this.resId;
	}

	public void setResType(Integer resType){
		this.resType = resType;
	}

	public Integer getResType(){
		return this.resType;
	}

	public void setAuthor(String author){
		this.author = author;
	}

	public String getAuthor(){
		return this.author;
	}

	public void setAuthorId(Integer authorId){
		this.authorId = authorId;
	}

	public Integer getAuthorId(){
		return this.authorId;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return this.username;
	}

	public void setUserId(Integer userId){
		this.userId = userId;
	}

	public Integer getUserId(){
		return this.userId;
	}

	public void setIsUpdate(Boolean isUpdate){
		this.isUpdate = isUpdate;
	}

	public Boolean getIsUpdate(){
		return this.isUpdate;
	}

	public void setCreatetime(Date createtime){
		this.createtime = createtime;
	}

	public Date getCreatetime(){
		return this.createtime;
	}

	public void setSchoolYear(Integer schoolYear){
		this.schoolYear = schoolYear;
	}

	public Integer getSchoolYear(){
		return this.schoolYear;
	}

	public void setSpaceId(Integer spaceId){
		this.spaceId = spaceId;
	}

	public Integer getSpaceId(){
		return this.spaceId;
	}

	public void setPhase(Integer phase){
		this.phase = phase;
	}

	public Integer getPhase(){
		return this.phase;
	}

	public void setTerm(Integer term){
		this.term = term;
	}

	public Integer getTerm(){
		return this.term;
	}

	public void setHasOptinion(Boolean hasOptinion){
		this.hasOptinion = hasOptinion;
	}

	public Boolean getHasOptinion(){
		return this.hasOptinion;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof CheckInfo))
				return false;
			CheckInfo castOther = (CheckInfo) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
	public List<Integer> getResTypes() {
		return resTypes;
	}

	public void setResTypes(List<Integer> resTypes) {
		this.resTypes = resTypes;
	}

	public Integer getUserSpaceId() {
		return userSpaceId;
	}

	public void setUserSpaceId(Integer userSpaceId) {
		this.userSpaceId = userSpaceId;
	}

	public Boolean getTitleShow() {
		return titleShow;
	}

	public void setTitleShow(Boolean titleShow) {
		this.titleShow = titleShow;
	}
	
}


