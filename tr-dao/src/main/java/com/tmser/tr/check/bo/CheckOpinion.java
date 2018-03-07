/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.check.bo;

import java.util.Date;



import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import com.tmser.tr.common.bo.QueryObject;

 /**
 * 查阅意见 Entity
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: CheckOpinion.java, v 1.0 2016-01-14 tmser Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = CheckOpinion.TABLE_NAME)
public class CheckOpinion extends QueryObject {
	public static final String TABLE_NAME="check_opinion";
	
	/**
	 * 查阅意见
	 */
	public static final int TYPE_CHECK = 0;
	
	/**
	 * 查阅回复
	 */
	public static final int TYPE_REPLY = 1;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	/**
	 *意见内容
	 **/
	@Column(name="content",length=2048)
	private String content;

	/**
	 *资源作者id
	 **/
	@Column(name="author_id")
	private Integer authorId;

	/**
	 *查阅信息id
	 **/
	@Column(name="check_info_id")
	private Integer checkInfoId;

	/**
	 *查阅意见类型： 0  普通意见， 1 回复
	 **/
	@Column(name="type")
	private Integer type;

	/**
	 *回复父级id
	 **/
	@Column(name="parent_id")
	private Integer parentId;

	/**
	 *查阅者id
	 **/
	@Column(name="user_id")
	private Integer userId;

	/**
	 *查阅者
	 **/
	@Column(name="username",length=32)
	private String username;

	/**
	 *是否删除
	 **/
	@Column(name="is_delete")
	private Boolean isDelete;

	/**
	 *是否隐藏
	 **/
	@Column(name="is_hidden")
	private Boolean isHidden;

	/**
	 *创建时间
	 **/
	@Column(name="crt_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date crtTime;

	/**
	 *资源id
	 **/
	@Column(name="res_id")
	private Integer resId;

	/**
	 *资源类型
	 **/
	@Column(name="res_type")
	private Integer resType;

	/**
	 *原始意见id
	 **/
	@Column(name="opinion_id")
	private Integer opinionId;

	/**
	 *用户空间id
	 **/
	@Column(name="space_id")
	private Integer spaceId;


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

	public void setAuthorId(Integer authorId){
		this.authorId = authorId;
	}

	public Integer getAuthorId(){
		return this.authorId;
	}

	public void setCheckInfoId(Integer checkInfoId){
		this.checkInfoId = checkInfoId;
	}

	public Integer getCheckInfoId(){
		return this.checkInfoId;
	}

	public void setType(Integer type){
		this.type = type;
	}

	public Integer getType(){
		return this.type;
	}

	public void setParentId(Integer parentId){
		this.parentId = parentId;
	}

	public Integer getParentId(){
		return this.parentId;
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

	public void setIsDelete(Boolean isDelete){
		this.isDelete = isDelete;
	}

	public Boolean getIsDelete(){
		return this.isDelete;
	}

	public void setIsHidden(Boolean isHidden){
		this.isHidden = isHidden;
	}

	public Boolean getIsHidden(){
		return this.isHidden;
	}

	public void setCrtTime(Date crtTime){
		this.crtTime = crtTime;
	}

	public Date getCrtTime(){
		return this.crtTime;
	}

	public void setResId(Integer resId){
		this.resId = resId;
	}

	public Integer getResId(){
		return this.resId;
	}

	public void setResType(Integer resType){
		this.resType = resType;
	}

	public Integer getResType(){
		return this.resType;
	}

	public void setOpinionId(Integer opinionId){
		this.opinionId = opinionId;
	}

	public Integer getOpinionId(){
		return this.opinionId;
	}

	public void setSpaceId(Integer spaceId){
		this.spaceId = spaceId;
	}

	public Integer getSpaceId(){
		return this.spaceId;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof CheckOpinion))
				return false;
			CheckOpinion castOther = (CheckOpinion) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


