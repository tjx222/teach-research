/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.bo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.BaseObject;

 /**
 * 校际教研圈 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: SchoolTeachCircle.java, v 1.0 2015-05-12 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = SchoolTeachCircle.TABLE_NAME)
public class SchoolTeachCircle extends BaseObject {
	public static final String TABLE_NAME="school_teach_circle";
	
		/**
	 *教研圈ID
	 **/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	/**
	 *教研圈名称
	 **/
	@Column(name="name",length=30)
	private String name;

	/**
	 *创建机构ID
	 **/
	@Column(name="org_id")
	private Integer orgId;
	
	/**
	 *创建学年
	 **/
	@Column(name="school_year")
	private Integer schoolYear;
	
	/**
	 *是否可以删除
	 **/
	@Column(name="is_delete")
	private Boolean isDelete;
	
	/**
	 *教研圈所属区域ids
	 **/
	@Column(name="area_ids")
	private String areaIds;

	/**
	 * 子集学校机构集合
	 */
	private List<SchoolTeachCircleOrg> stcoList;
	

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

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Integer schoolYear) {
		this.schoolYear = schoolYear;
	}
	
	public String getAreaIds() {
		return areaIds;
	}

	public void setAreaIds(String areaIds) {
		this.areaIds = areaIds;
	}

	public List<SchoolTeachCircleOrg> getStcoList() {
		return stcoList;
	}

	public void setStcoList(List<SchoolTeachCircleOrg> stcoList) {
		this.stcoList = stcoList;
	}

	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof SchoolTeachCircle))
				return false;
			SchoolTeachCircle castOther = (SchoolTeachCircle) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


