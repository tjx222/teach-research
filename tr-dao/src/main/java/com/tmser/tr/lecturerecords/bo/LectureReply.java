/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.lecturerecords.bo;



import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.BaseObject;

 /**
 * 听课记录回复 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: LectureReply.java, v 1.0 2016-01-14 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = LectureReply.TABLE_NAME)
public class LectureReply extends BaseObject {
	public static final String TABLE_NAME="lecture_reply";
	
		/**
	 *主键id
	 **/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	/**
	 *回复内容
	 **/
	@Column(name="content",length=2048)
	private String content;

	/**
	 *回复类型  0回复   1回复的回复
	 **/
	@Column(name="type")
	private Integer type;

	/**
	 *作者id(听课人ID)
	 **/
	@Column(name="author_id")
	private Integer authorId;

	/**
	 *资源id(听课记录主键)
	 **/
	@Column(name="res_id")
	private Integer resId;

	/**
	 *授课人ID
	 **/
	@Column(name="teacher_id")
	private Integer teacherId;

	/**
	 *回复者id
	 **/
	@Column(name="user_id")
	private Integer userId;

	/**
	 *回复者的名字
	 **/
	@Column(name="username",length=11)
	private String username;

	/**
	 *回复父级
	 **/
	@Column(name="parent_id")
	private Integer parentId;

	/**
	 *是否删除 0未  1已
	 **/
	@Column(name="is_delete")
	private Integer isDelete;

	/**
	 *是否隐藏 0未 1已
	 **/
	@Column(name="is_hidden")
	private Integer isHidden;


	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return this.content;
	}

	public void setType(Integer type){
		this.type = type;
	}

	public Integer getType(){
		return this.type;
	}

	public void setAuthorId(Integer authorId){
		this.authorId = authorId;
	}

	public Integer getAuthorId(){
		return this.authorId;
	}

	public void setResId(Integer resId){
		this.resId = resId;
	}

	public Integer getResId(){
		return this.resId;
	}

	public void setTeacherId(Integer teacherId){
		this.teacherId = teacherId;
	}

	public Integer getTeacherId(){
		return this.teacherId;
	}

	public void setUserId(Integer userId){
		this.userId = userId;
	}

	public Integer getUserId(){
		return this.userId;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return this.username;
	}

	public void setParentId(Integer parentId){
		this.parentId = parentId;
	}

	public Integer getParentId(){
		return this.parentId;
	}

	public void setIsDelete(Integer isDelete){
		this.isDelete = isDelete;
	}

	public Integer getIsDelete(){
		return this.isDelete;
	}

	public void setIsHidden(Integer isHidden){
		this.isHidden = isHidden;
	}

	public Integer getIsHidden(){
		return this.isHidden;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof LectureReply))
				return false;
			LectureReply castOther = (LectureReply) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


