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

import com.tmser.tr.common.bo.QueryObject;
import com.tmser.tr.manage.meta.Meta;

/**
 * 基础元数据 Entity
 * 
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: SysDic.java, v 1.0 2016-01-14 tmser Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = SysDic.TABLE_NAME)
public class SysDic extends QueryObject implements Meta {
  public static final String TABLE_NAME = "sys_dic";
  
  public static final String DICSTATUS_ACTIVE = "active";
  public static final String DICSTATUS_INACTIVE = "inactive";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "dic_id")
  private Integer id;

  @Column(name = "dic_name", nullable = false, length = 64)
  private String name;

  @Column(name = "parent_id", nullable = false)
  private Integer parentId;

  @Column(name = "dic_level", nullable = false)
  private Integer dicLevel;

  @Column(name = "dic_orderby", nullable = false)
  private Integer dicOrderby;

  @Column(name = "dic_status", nullable = false, length = 32)
  private String dicStatus;

  @Column(name = "operator", nullable = false, length = 32)
  private String operator;

  @Column(name = "cascade_dic_ids", nullable = false, length = 64)
  private String cascadeDicIds;

  @Column(name = "child_count", nullable = false)
  private Integer childCount;

  @Column(name = "standard_code", length = 8)
  private String standardCode;

  @Column(name = "scope")
  private String scope;

  @Column(name = "org_id")
  private Integer orgId;

  @Column(name = "area_id")
  private Integer areaId;

  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public Integer getId() {
    return this.id;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return this.name;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  @Override
  public Integer getParentId() {
    return this.parentId;
  }

  public void setDicLevel(Integer dicLevel) {
    this.dicLevel = dicLevel;
  }

  public Integer getDicLevel() {
    return this.dicLevel;
  }

  public void setDicOrderby(Integer dicOrderby) {
    this.dicOrderby = dicOrderby;
  }

  @Override
  public Integer getDicOrderby() {
    return this.dicOrderby;
  }

  public void setDicStatus(String dicStatus) {
    this.dicStatus = dicStatus;
  }

  public String getDicStatus() {
    return this.dicStatus;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public String getOperator() {
    return this.operator;
  }

  public void setCascadeDicIds(String cascadeDicIds) {
    this.cascadeDicIds = cascadeDicIds;
  }

  public String getCascadeDicIds() {
    return this.cascadeDicIds;
  }

  public void setChildCount(Integer childCount) {
    this.childCount = childCount;
  }

  @Override
  public Integer getChildCount() {
    return this.childCount;
  }

  public void setStandardCode(String standardCode) {
    this.standardCode = standardCode;
  }

  public String getStandardCode() {
    return this.standardCode;
  }

  /**
   *
   * Getter method for property <tt>scope</tt>.
   *
   * @return scope String
   */
  public String getScope() {
    return this.scope;
  }

  /**
   *
   * Setter method for property <tt>scope</tt>.
   *
   * @param scope
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
   * @param orgId
   *          Integer value to be assigned to property orgId
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
   * @param areaId
   *          Integer value to be assigned to property areaId
   */
  public void setAreaId(Integer areaId) {
    this.areaId = areaId;
  }

  @Override
  public boolean equals(final Object other) {
    if (!(other instanceof SysDic))
      return false;
    SysDic castOther = (SysDic) other;
    return new EqualsBuilder().append(id, castOther.id).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(id).toHashCode();
  }
}
