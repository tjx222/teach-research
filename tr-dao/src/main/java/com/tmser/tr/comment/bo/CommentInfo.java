/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.comment.bo;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import com.tmser.tr.common.bo.QueryObject;

 /**
 * 评论信息 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: CommentInfo.java, v 1.0 2015-03-20 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = CommentInfo.TABLE_NAME)
public class CommentInfo extends QueryObject {
	public static final String TABLE_NAME="comment_info";
	
	public static final int PTPL=0;//普通评论
	public static final int HF=1;//回复
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	/**
	 *评论内容
	 **/
	@Column(name="content",length=2048)
	private String content;


	/**
	 *作者id
	 **/
	@Column(name="author_id")
	private Integer authorId;

	/**
	 *资源类型
	 **/
	@Column(name="res_type")
	private Integer resType;

	/**
	 *被评论资源id
	 **/
	@Column(name="res_id")
	private Integer resId;
	
	/**
	 *被评论资源标题
	 **/
	@Column(name="title",length=128)
	private String title;

	/**
	 *评论类型： 0  普通评论 ， 1 回复
	 **/
	@Column(name="type")
	private Integer type;

	/**
	 *回复父级id
	 **/
	@Column(name="parent_id")
	private Integer parentId;

	/**
	 *当前评论者id
	 **/
	@Column(name="user_id")
	private Integer userId;

	/**
	 *当前评论者
	 **/
	@Column(name="username")
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
	 * 创建时间
	 */
	@Column(name="crt_dttm")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date crtDttm;
	
	/**
	 * 原始意见id
	 */
	@Column(name="opinion_id")
	private Integer opinionId;
	
	/**
	 * 评论列表名称是否显示
	 */
	private Boolean titleShow;
	

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

	public void setResType(Integer resType){
		this.resType = resType;
	}

	public Integer getResType(){
		return this.resType;
	}

	public void setResId(Integer resId){
		this.resId = resId;
	}

	public Integer getResId(){
		return this.resId;
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
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getOpinionId() {
		return opinionId;
	}

	public void setOpinionId(Integer opinionId) {
		this.opinionId = opinionId;
	}
	

	public Boolean getTitleShow() {
		return titleShow;
	}

	public void setTitleShow(Boolean titleShow) {
		this.titleShow = titleShow;
	}

	/**
	 * 创建时间
	 */
	public Date getCrtDttm() {
		return crtDttm;
	}

	/**
	 * 创建时间
	 */
	public void setCrtDttm(Date crtDttm) {
		this.crtDttm = crtDttm;
	}
	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof CommentInfo))
				return false;
			CommentInfo castOther = (CommentInfo) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


