/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.xxsy.bannermanner.bo;



import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.BaseObject;

 /**
 * 学校横幅广告 Entity
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: SchoolBanner.java, v 1.0 2015-10-28 tmser Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = SchoolBanner.TABLE_NAME)
public class SchoolBanner extends BaseObject {
	public static final String TABLE_NAME="jy_school_banner";
	
		/**
	 *主键id
	 **/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	/**
	 *组织机构id
	 **/
	@Column(name="orgid")
	private Integer orgId;

	/**
	 *学校id(暂未用)
	 **/
	@Column(name="schid")
	private Integer schid;

	/**
	 *图片id
	 **/
	@Column(name="attachs",length=120)
	private String attachs;
	/**
	 *图片name
	 **/
	@Column(name="attachsname",length=120)
	private String attachsname;

	/**
	 *是否显示 0：不显示  1：显示
	 **/
	@Column(name="isview")
	private Integer isview;
	
	/**
	 *显示顺序
	 **/
	@Column(name="vieworder")
	private Integer vieworder;

	public String getAttachsname() {
		return attachsname;
	}

	public void setAttachsname(String attachsname) {
		this.attachsname = attachsname;
	}

	public Integer getVieworder() {
		return vieworder;
	}

	public void setVieworder(Integer vieworder) {
		this.vieworder = vieworder;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public void setSchid(Integer schid){
		this.schid = schid;
	}

	public Integer getSchid(){
		return this.schid;
	}

	public void setAttachs(String attachs){
		this.attachs = attachs;
	}

	public String getAttachs(){
		return this.attachs;
	}

	public void setIsview(Integer isview){
		this.isview = isview;
	}

	public Integer getIsview(){
		return this.isview;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof SchoolBanner))
				return false;
			SchoolBanner castOther = (SchoolBanner) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


