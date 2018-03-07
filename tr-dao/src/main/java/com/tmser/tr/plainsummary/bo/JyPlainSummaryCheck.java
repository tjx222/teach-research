/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.plainsummary.bo;



import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.BaseObject;

 /**
 * plainSummary Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: JyPlainSummaryCheck.java, v 1.0 2015-04-09 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = JyPlainSummaryCheck.TABLE_NAME)
public class JyPlainSummaryCheck extends BaseObject {
	public static final String TABLE_NAME="jy_plain_summary_check";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	/**
	 *审阅人用户空间
	 **/
	@Column(name="check_space_id",nullable=false)
	private Integer checkSpaceId;

	/**
	 *被审阅人用户空间
	 **/
	@Column(name="checked_space_id",nullable=false)
	private Integer checkedSpaceId;

	/**
	 *被审阅计划总结id
	 **/
	@Column(name="plain_summary_id",nullable=false)
	private Integer plainSummaryId;

	/**
	 *被审阅计划总结类型
	 **/
	@Column(name="plain_summary_category",nullable=false)
	private Integer plainSummaryCategory;

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setCheckSpaceId(Integer checkSpaceId){
		this.checkSpaceId = checkSpaceId;
	}

	public Integer getCheckSpaceId(){
		return this.checkSpaceId;
	}

	public void setCheckedSpaceId(Integer checkedSpaceId){
		this.checkedSpaceId = checkedSpaceId;
	}

	public Integer getCheckedSpaceId(){
		return this.checkedSpaceId;
	}

	public void setPlainSummaryId(Integer plainSummaryId){
		this.plainSummaryId = plainSummaryId;
	}

	public Integer getPlainSummaryId(){
		return this.plainSummaryId;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof JyPlainSummaryCheck))
				return false;
			JyPlainSummaryCheck castOther = (JyPlainSummaryCheck) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}

	public Integer getPlainSummaryCategory() {
		return plainSummaryCategory;
	}

	public void setPlainSummaryCategory(Integer plainSummaryCategory) {
		this.plainSummaryCategory = plainSummaryCategory;
	}
}


