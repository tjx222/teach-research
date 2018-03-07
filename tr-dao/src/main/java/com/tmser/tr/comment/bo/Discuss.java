/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.comment.bo;

import java.util.List;

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
 * 区域教研中各活动的讨论表 Entity
 * <pre>
 *
 * </pre>
 *
 * @author zpp
 * @version $Id: AreaActivityDiscuss.java, v 1.0 2016-09-29 zpp Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = Discuss.TABLE_NAME)
public class Discuss extends BaseObject {
	public static final String TABLE_NAME="jy_discuss";
	
	/**
	 *讨论表ID
	 **/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="type_id")
	private Integer typeId;

	/**
	 *关联校际或者区域活动的ID
	 **/
	@Column(name="activity_id")
	private Integer activityId;

	/**
	 *讨论人当时的身份空间ID
	 **/
	@Column(name="space_id")
	private Integer spaceId;

	/**
	 *讨论的内容
	 **/
	@Column(name="content",length=512)
	private String content;

	/**
	 *讨论层级 1：一层（直接对应活动的讨论） 2：二层（对一层的讨论进行的评论）
	 **/
	@Column(name="discuss_level")
	private Integer discussLevel;

	/**
	 *讨论的父层ID
	 **/
	@Column(name="parent_id")
	private Integer parentId;
	
	/**
	 * 数据子类集合
	 **/
	private List<Discuss> childList;
	
	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public void setActivityId(Integer activityId){
		this.activityId = activityId;
	}

	public Integer getActivityId(){
		return this.activityId;
	}

	public void setSpaceId(Integer spaceId){
		this.spaceId = spaceId;
	}

	public Integer getSpaceId(){
		return this.spaceId;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return this.content;
	}

	public Integer getDiscussLevel() {
		return discussLevel;
	}

	public void setDiscussLevel(Integer discussLevel) {
		this.discussLevel = discussLevel;
	}

	public void setParentId(Integer parentId){
		this.parentId = parentId;
	}

	public Integer getParentId(){
		return this.parentId;
	}
	

	public List<Discuss> getChildList() {
		return childList;
	}

	public void setChildList(List<Discuss> childList) {
		this.childList = childList;
	}

	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof Discuss))
				return false;
			Discuss castOther = (Discuss) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


