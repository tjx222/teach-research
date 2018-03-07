/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.schedule.bo;

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
 * 日程表 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Schedule.java, v 1.0 2015-04-10 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = Schedule.TABLE_NAME)
public class Schedule extends BaseObject {
	public static final String TABLE_NAME="teacher_schedule";
	
	/**
	 *日程表id
	 **/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	/**
	 *用户id
	 **/
	@Column(name="userid",nullable=false)
	private Integer userid;

	/**
	 *日程主题
	 **/
	@Column(name="summary")
	private String summary;

	/**
	 *日程起始时间
	 **/
	@Column(name="start")
	private Long start;

	/**
	 *日程结束时间
	 **/
	@Column(name="end")
	private Long end;

	/**
	 *是否全天
	 **/
	@Column(name="isallday")
	private Boolean isallday;

	/**
	 *地点
	 **/
	@Column(name="location")
	private String location;

	/**
	 *描述
	 **/
	@Column(name="description",length=500)
	private String description;

	/**
	 *颜色
	 **/
	@Column(name="color")
	private String color;


	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setUserid(Integer userid){
		this.userid = userid;
	}

	public Integer getUserid(){
		return this.userid;
	}

	public void setSummary(String summary){
		this.summary = summary;
	}

	public String getSummary(){
		return this.summary;
	}

	public void setStart(Long start){
		this.start = start;
	}

	public Long getStart(){
		return this.start;
	}

	public void setEnd(Long end){
		this.end = end;
	}

	public Long getEnd(){
		return this.end;
	}

	public void setIsallday(Boolean isallday){
		this.isallday = isallday;
	}

	public Boolean getIsallday(){
		return this.isallday;
	}

	public void setLocation(String location){
		this.location = location;
	}

	public String getLocation(){
		return this.location;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return this.description;
	}

	public void setColor(String color){
		this.color = color;
	}

	public String getColor(){
		return this.color;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof Schedule))
				return false;
			Schedule castOther = (Schedule) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


