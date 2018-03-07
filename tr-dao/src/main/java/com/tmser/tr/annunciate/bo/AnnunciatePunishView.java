/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.annunciate.bo;

import java.util.Date;


import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.BaseObject;

 /**
 * 通告发布查看 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: AnnunciatePunishView.java, v 1.0 2015-07-01 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = AnnunciatePunishView.TABLE_NAME)
public class AnnunciatePunishView extends BaseObject {
	public static final String TABLE_NAME="annunciate_punish_view";
	
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
	 *通告id
	 **/
	@Column(name="annunciate_id")
	private Integer annunciateId;

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

	public void setAnnunciateId(Integer annunciateId){
		this.annunciateId = annunciateId;
	}

	public Integer getAnnunciateId(){
		return this.annunciateId;
	}

	public void setViewTime(Date viewTime){
		this.viewTime = viewTime;
	}

	public Date getViewTime(){
		return this.viewTime;
	}

	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof AnnunciatePunishView))
				return false;
			AnnunciatePunishView castOther = (AnnunciatePunishView) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


