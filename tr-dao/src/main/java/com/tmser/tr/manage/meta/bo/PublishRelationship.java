/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.meta.bo;



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
 * 出版社关系表 Entity
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: PublishRelationship.java, v 1.0 2015-08-24 tmser Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = PublishRelationship.TABLE_NAME)
public class PublishRelationship extends BaseObject {
	public static final String TABLE_NAME="sys_publish_relationship";
	
		/**
	 *关系表主键
	 **/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	/**
	 *学段ID
	 **/
	@Column(name="phase_id")
	private Integer phaseId;

	/**
	 *学科ID
	 **/
	@Column(name="subject_id")
	private Integer subjectId;

	/**
	 *出版社全称
	 **/
	@Column(name="name",length=32)
	private String name;

	/**
	 *出版社简称
	 **/
	@Column(name="short_name",length=16)
	private String shortName;

	/**
	 *出版社元数据ID
	 **/
	@Column(name="eid")
	private Integer eid;

	/**
	 *排序
	 **/
	@Column(name="sort")
	private Integer sort;

	@Column(name = "scope")
	private String scope;
	
	@Column(name="org_id")
	private Integer orgId;
	
	@Column(name = "area_id")
	private Integer areaId;
	
	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setPhaseId(Integer phaseId){
		this.phaseId = phaseId;
	}

	public Integer getPhaseId(){
		return this.phaseId;
	}

	public void setSubjectId(Integer subjectId){
		this.subjectId = subjectId;
	}

	public Integer getSubjectId(){
		return this.subjectId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setShortName(String shortName){
		this.shortName = shortName;
	}

	public String getShortName(){
		return this.shortName;
	}

	public void setEid(Integer eid){
		this.eid = eid;
	}

	public Integer getEid(){
		return this.eid;
	}

	public void setSort(Integer sort){
		this.sort = sort;
	}

	public Integer getSort(){
		return this.sort;
	}

	/** 
   *
   * Getter method for property <tt>scope</tt>. 
   *
   * @return scope String
   */
  public String getScope() {
    return scope;
  }

  /**
   *
   * Setter method for property <tt>scope</tt>.
   *
   * @param scope String value to be assigned to property scope
   */
  public void setScope(String scope) {
    this.scope = scope;
  }

  /** 
   *
   * Getter method for property <tt>orgId</tt>. 
   *
   * @return orgId Integer
   */
  public Integer getOrgId() {
    return orgId;
  }

  /**
   *
   * Setter method for property <tt>orgId</tt>.
   *
   * @param orgId Integer value to be assigned to property orgId
   */
  public void setOrgId(Integer orgId) {
    this.orgId = orgId;
  }

  /** 
   *
   * Getter method for property <tt>areaId</tt>. 
   *
   * @return areaId Integer
   */
  public Integer getAreaId() {
    return areaId;
  }

  /**
   *
   * Setter method for property <tt>areaId</tt>.
   *
   * @param areaId Integer value to be assigned to property areaId
   */
  public void setAreaId(Integer areaId) {
    this.areaId = areaId;
  }
  

  @Override
	public boolean equals(final Object other) {
			if (!(other instanceof PublishRelationship))
				return false;
			PublishRelationship castOther = (PublishRelationship) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


