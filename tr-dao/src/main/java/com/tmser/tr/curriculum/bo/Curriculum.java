/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.curriculum.bo;



import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.BaseObject;

 /**
 * 课程表 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Curriculum.java, v 1.0 2016-01-14 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = Curriculum.TABLE_NAME)
public class Curriculum extends BaseObject {
	public static final String TABLE_NAME="teacher_curriculum";
	
		@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="userid",nullable=false)
	private Integer userid;

	@Column(name="week")
	private Integer week;

	@Column(name="lesson")
	private Integer lesson;

	@Column(name="content",length=50)
	private String content;


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

	public void setWeek(Integer week){
		this.week = week;
	}

	public Integer getWeek(){
		return this.week;
	}

	public void setLesson(Integer lesson){
		this.lesson = lesson;
	}

	public Integer getLesson(){
		return this.lesson;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return this.content;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof Curriculum))
				return false;
			Curriculum castOther = (Curriculum) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


