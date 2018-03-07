/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.plainsummary.bo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.QueryObject;

 /**
 * 计划总结发布查看记录 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: PlainSummaryPunishView.java, v 1.0 2015-06-30 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = PlainSummaryPunishView.TABLE_NAME)
public class PlainSummaryPunishView extends QueryObject {
	public static final String TABLE_NAME="jy_plain_punish_view";
	
		@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	/**
	 *用户id
	 **/
	@Column(name="user_id")
	private Integer userId;

	/**
	 *计划总结id
	 **/
	@Column(name="plain_summary_id")
	private Integer plainSummaryId;

	/**
	 *查看日期
	 **/
	@Column(name="view_time")
	private Date viewTime;


	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setUserId(Integer userId){
		this.userId = userId;
	}

	public Integer getUserId(){
		return this.userId;
	}

	public void setPlainSummaryId(Integer plainSummaryId){
		this.plainSummaryId = plainSummaryId;
	}

	public Integer getPlainSummaryId(){
		return this.plainSummaryId;
	}

	public void setViewTime(Date viewTime){
		this.viewTime = viewTime;
	}

	public Date getViewTime(){
		return this.viewTime;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof PlainSummaryPunishView))
				return false;
			PlainSummaryPunishView castOther = (PlainSummaryPunishView) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


