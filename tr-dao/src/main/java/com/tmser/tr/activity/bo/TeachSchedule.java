/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.bo;



import java.util.Date;

import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import com.tmser.tr.common.bo.BaseObject;

 /**
 * 教研进度表 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: TeachSchedule.java, v 1.0 2015-05-18 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = TeachSchedule.TABLE_NAME)
public class TeachSchedule extends BaseObject {
	public static final String TABLE_NAME="school_teach_schedule";
	
		/**
	 *教研进度ID
	 **/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	/**
	 *进度表名称
	 **/
	@Column(name="name",length=30)
	private String name;

	/**
	 *机构ID
	 **/
	@Column(name="org_id")
	private Integer orgId;

	/**
	 *学科ID
	 **/
	@Column(name="subject_id")
	private Integer subjectId;

	/**
	 *年级ID
	 **/
	@Column(name="grade_id")
	private Integer gradeId;

	/**
	 *文件资源ID
	 **/
	@Column(name="res_id",length=32)
	private String resId;

	/**
	 *校际教研圈ID
	 **/
	@Column(name="school_teach_circle_id")
	private Integer schoolTeachCircleId;

	/**
	 *是否发布
	 **/
	@Column(name="is_release")
	private Boolean isRelease;

	/**
	 *发布时间
	 **/
	@Column(name="release_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date releaseTime;
	
	/**
	 *学年
	 **/
	@Column(name="school_year")
	private Integer schoolYear;

	/**
	 *文件后缀
	 **/
	@Column(name="file_suffix")
	private String fileSuffix;
	

	/**
	 * 所属地区id节点集合
	 **/
	@Column(name="area_ids")
	private String areaIds;
	
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

	public void setOrgId(Integer orgId){
		this.orgId = orgId;
	}

	public Integer getOrgId(){
		return this.orgId;
	}

	public void setSubjectId(Integer subjectId){
		this.subjectId = subjectId;
	}

	public Integer getSubjectId(){
		return this.subjectId;
	}

	public void setGradeId(Integer gradeId){
		this.gradeId = gradeId;
	}

	public Integer getGradeId(){
		return this.gradeId;
	}

	public void setResId(String resId){
		this.resId = resId;
	}

	public String getResId(){
		return this.resId;
	}

	public void setSchoolTeachCircleId(Integer schoolTeachCircleId){
		this.schoolTeachCircleId = schoolTeachCircleId;
	}

	public Integer getSchoolTeachCircleId(){
		return this.schoolTeachCircleId;
	}

	public void setIsRelease(Boolean isRelease){
		this.isRelease = isRelease;
	}

	public Boolean getIsRelease(){
		return this.isRelease;
	}
	
	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public Integer getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Integer schoolYear) {
		this.schoolYear = schoolYear;
	}
	

	public String getFileSuffix() {
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	public String getAreaIds() {
		return areaIds;
	}

	public void setAreaIds(String areaIds) {
		this.areaIds = areaIds;
	}

	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof TeachSchedule))
				return false;
			TeachSchedule castOther = (TeachSchedule) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


