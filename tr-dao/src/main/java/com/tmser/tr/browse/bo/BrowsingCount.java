/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.browse.bo;



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
 * 资源浏览总数记录 Entity
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: BrowsingCount.java, v 1.0 2015-11-11 tmser Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = BrowsingCount.TABLE_NAME)
public class BrowsingCount extends BaseObject {
	public static final String TABLE_NAME="jy_browsing_count";

	/**
	 *主键
	 **/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	/**
	 *资源类型
	 **/
	@Column(name="type")
	private Integer type;

	/**
	 *子类型
	 **/
	@Column(name="child_type")
	private Integer childType;

	/**
	 *资源所属的机构ID
	 **/
	@Column(name="org_id")
	private Integer orgId;

	/**
	 *资源ID
	 **/
	@Column(name="res_id")
	private Integer resId;

	/**
	 *资源是否分享
	 **/
	@Column(name="res_share")
	private Boolean resShare;

	/**
	 *浏览的次数
	 **/
	@Column(name="count")
	private Integer count;


	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setType(Integer type){
		this.type = type;
	}

	public Integer getType(){
		return this.type;
	}

	public void setChildType(Integer childType){
		this.childType = childType;
	}

	public Integer getChildType(){
		return this.childType;
	}

	public void setResId(Integer resId){
		this.resId = resId;
	}

	public Integer getResId(){
		return this.resId;
	}

	public void setResShare(Boolean resShare){
		this.resShare = resShare;
	}

	public Boolean getResShare(){
		return this.resShare;
	}

	public void setCount(Integer count){
		this.count = count;
	}

	public Integer getCount(){
		return this.count;
	}

	/**
	 * Getter method for property <tt>orgId</tt>.
	 * @return property value of orgId
	 */
	public Integer getOrgId() {
		return orgId;
	}

	/**
	 * Setter method for property <tt>orgId</tt>.
	 * @param orgId value to be assigned to property orgId
	 */
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof BrowsingCount))
			return false;
		BrowsingCount castOther = (BrowsingCount) other;
		return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}
}


