/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.recordbag.bo;

import java.util.Date;
import java.util.Map;

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
 * 精选档案 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Record.java, v 1.0 2015-04-16 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = Record.TABLE_NAME)
public class Record extends BaseObject {
	public static final String TABLE_NAME="teacher_record";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="record_id")
	private Integer recordId;
	
	/**
	 *作者
	 **/
	@Column(name="user_id",nullable=false)
	private Integer userId;

	/**
	 *档案名称
	 **/
	@Column(name="record_name",nullable=false,length=64)
	private String recordName;

	/**
	 *精选状态：0未精选、1已精选
	 **/
	@Column(name="status",nullable=false)
	private Integer status;

	/**
	 *微评
	 **/
	@Column(name="sort_desc",length=255)
	private String desc;

	/**
	 *创建日期
	 **/
	@Column(name="create_time",nullable=false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 *修改日期
	 **/
	@Column(name="modify_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date modifyTime;

	/**
	 *档案包Id
	 **/
	@Column(name="bag_id",nullable=false)
	private Integer bagId;

	/**
	 *资源类型：0 教学设计，1 自制课件，2 教学反思，3 教研活动，4 教学文章，5 计划总结，6 听课记录
	 **/
	@Column(name="res_type",nullable=false)
	private Integer resType;

	/**
	 *册别：0上册、1下册、2全一册
	 **/
	@Column(name="volume",nullable=false)
	private Integer volume;

	/**
	 *文件所在的路径
	 **/
	@Column(name="path",length=128)
	private String path;

	/**
	 *资源原id
	 **/
	@Column(name="res_id")
	private Integer resId;

	/**
	 * 学年
	 */
	@Column(name="school_year")
	private Integer schoolYear;
	
	@Column(name="share")
	private Integer share;
	
	/** 
	 * Getter method for property <tt>share</tt>. 
	 * @return property value of share 
	 */
	public Integer getShare() {
		return share;
	}

	/**
	 * Setter method for property <tt>share</tt>.
	 * @param share value to be assigned to property share
	 */
	public void setShare(Integer share) {
		this.share = share;
	}

	private String time;
	
	private Map<String,String> ext;
	
	private Integer childType;
	

	public Integer getChildType() {
		return childType;
	}

	public void setChildType(Integer childType) {
		this.childType = childType;
	}

	public void setRecordId(Integer recordId){
		this.recordId = recordId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRecordId(){
		return this.recordId;
	}

	public void setRecordName(String recordName){
		this.recordName = recordName;
	}

	public String getRecordName(){
		return this.recordName;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setDesc(String sortDesc){
		this.desc = sortDesc;
	}

	public String getDesc(){
		return this.desc;
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

	public void setBagId(Integer bagId){
		this.bagId = bagId;
	}

	public Integer getBagId(){
		return this.bagId;
	}

	public void setResType(Integer resType){
		this.resType = resType;
	}

	public Integer getResType(){
		return this.resType;
	}

	public void setVolume(Integer volume){
		this.volume = volume;
	}

	public Integer getVolume(){
		return this.volume;
	}

	public void setPath(String path){
		this.path = path;
	}

	public String getPath(){
		return this.path;
	}
	
	public Integer getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Integer schoolYear) {
		this.schoolYear = schoolYear;
	}

	public void setResId(Integer resId){
		this.resId = resId;
	}

	public Integer getResId(){
		return this.resId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public Map<String, String> getExt() {
		return ext;
	}

	public void setExt(Map<String, String> ext) {
		this.ext = ext;
	}

	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof Record))
				return false;
			Record castOther = (Record) other;
			return new EqualsBuilder().append(recordId, castOther.recordId).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(recordId).toHashCode();
	}
}


