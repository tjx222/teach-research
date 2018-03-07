/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.meta.bo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.QueryObject;

/**
 * 元数据关系表 Entity
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author tmser
 * @version $Id: MetaRelationship.java, v 1.0 2015-03-11 tmser Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = MetaRelationship.TABLE_NAME)
public class MetaRelationship extends QueryObject {
	
	public static final String TABLE_NAME = "sys_meta_relationship";
	
	public static final String SPLIT_CHAR = ",";

	/**
	 * 学段-年级
	 */
	public static final int T_XD_NJ = 0;
	
	public static final int T_ORG_TYPE = 1;

	/**
	 * 学段 -- 学科
	 */
	public static final int T_XD_XK = 2;

	/**
	 * 学段类型
	 */
	public static final int T_XD = 3;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 学制名称
	 **/
	@Column(name = "name",length=255)
	private String name;

	/**
	 * 关联表字段id列表，使用‘，’ 分割
	 **/
	@Column(name = "ids",length=1024)
	private String ids;

	/**
	 * 类型， 0 ：学段(eid)-->年级(ids) 1：学校类型(id)-->学段(ids) 2：学段(eid)-->学科(ids)
	 * 3：学段类型(id)-->学段(eid) 4：学制类型(id) 5：学段、学制-年级关联(eid、sort)-->年级(ids)
	 * 
	 **/
	@Column(name = "type")
	private Integer type;
	
	@Column(name = "descs",length=1024)
	private String descs;
	
	@Column(name = "sort")
	private Integer sort;
	
	@Column(name = "org_id")
	private Integer orgId;
	
	@Column(name = "area_id")
	private Integer areaId;

	public String getDescs() {
		return descs;
	}

	public void setDescs(String descs) {
		this.descs = descs;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	/**
	 * 基础元素id
	 **/
	@Column(name = "eid")
	private Integer eid;
	
	@Column(name = "enable")
	private Boolean enable;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
	public <T> void setIds(List<T> idSet) {
		this.ids = StringUtils.join(idSet, SPLIT_CHAR);
	}

	public String getIds() {
		return this.ids;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return this.type;
	}

	public void setEid(Integer eid) {
		this.eid = eid;
	}

	public Integer getEid() {
		return this.eid;
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
  
  

  /** 
   *
   * Getter method for property <tt>enable</tt>. 
   *
   * @return enable Boolean
   */
  public Boolean getEnable() {
    return enable;
  }

  /**
   *
   * Setter method for property <tt>enable</tt>.
   *
   * @param enable Boolean value to be assigned to property enable
   */
  public void setEnable(Boolean enable) {
    this.enable = enable;
  }

  @Override
	public boolean equals(final Object other) {
		if (!(other instanceof MetaRelationship))
			return false;
		MetaRelationship castOther = (MetaRelationship) other;
		return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}
}
