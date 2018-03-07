/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.annunciate.bo;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.BaseObject;

 /**
 * 通知公告 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: JyAnnunciate.java, v 1.0 2016-01-14 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = JyAnnunciate.TABLE_NAME)
public class JyAnnunciate extends BaseObject {
	public static final String TABLE_NAME="jy_annunciate";
	
		@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
		
	/**
	 *通知公告类型
	 **/
	@Column(name="annunciate_type",nullable=false)
	private Integer annunciateType;

	/**
	 *组织id 
	 **/
	@Column(name="org_id",nullable=false)
	private Integer orgId;
	
	/**
	 *用户id
	 **/
	@Column(name="user_id",nullable=false)
	private Integer userId;
	
	/**
	 *参与机构ids
	 **/
	@Column(name="orgs_join_ids")
	private String orgsJoinIds;
	
	/**
	 *参与机构数
	 **/
	@Column(name="orgs_join_count")
	private Integer orgsJoinCount;

	/**
	 *工作空间id
	 **/
	@Column(name="space_id",nullable=false)
	private Integer spaceId;

	/**
	 *红头文件标题id
	 **/
	@Column(name="red_title_id",nullable=false)
	private Integer redTitleId;

	@Column(name="from_where",length=20)
	private String fromWhere;

	/**
	 *通告标题
	 **/
	@Column(name="title",nullable=false,length=200)
	private String title;

	/**
	 *通告类型：0、普通文件，1、红头文件
	 **/
	@Column(name="type",nullable=false,length=2)
	private String type;

	/**
	 *通告内容
	 **/
	@Column(name="content",nullable=false,length=21845)
	private String content;

	/**
	 *附件列表，用#分割
	 **/
	@Column(name="attachs",length=500)
	private String attachs;

	/**
	 *通告状态：0、草稿箱，1、已发布
	 **/
	@Column(name="status",nullable=false)
	private Integer status;

	/**
	 *通告范围：0、学校，1、区域
	 **/
	@Column(name="annunciate_range",nullable=false)
	private Integer annunciateRange;

	/**
	 *是否可用：1、可用，0、不可用
	 **/
	@Column(name="is_enable",nullable=false)
	private Integer isEnable;

	/**
	 *是否已删除：1、已删除，0未删除
	 **/
	@Column(name="is_delete",nullable=false)
	private Integer isDelete;

	/**
	 *是否在学校首页显示：0 不显示，1 显示
	 **/
	@Column(name="is_display")
	private Integer isDisplay;

	/**
	 *是否在学校首页显示：0 不显示，1 显示
	 **/
	@Column(name="is_top")
	private Boolean isTop;
	
	/**
	 * 是否转发：0 没有转发，1 转发
	 **/
	@Column(name="is_forward")
	private Integer isForward;
	
	/**
	 * 转发说明
	 **/
	@Column(name="forward_description")
	private String forwardDescription;
	
	/**
	 *转发说明
	 **/
	@Transient
	private String sendDescription;
	
    @Transient
    private String crtName;
    
	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setOrgId(Integer orgId){
		this.orgId = orgId;
	}

	public Integer getOrgId(){
		return this.orgId;
	}

	public void setUserId(Integer userId){
		this.userId = userId;
	}

	public Integer getUserId(){
		return this.userId;
	}

	public void setSpaceId(Integer spaceId){
		this.spaceId = spaceId;
	}

	public Integer getSpaceId(){
		return this.spaceId;
	}

	public void setRedTitleId(Integer redTitleId){
		this.redTitleId = redTitleId;
	}

	public Integer getRedTitleId(){
		return this.redTitleId;
	}

	public void setFromWhere(String fromWhere){
		this.fromWhere = fromWhere;
	}

	public String getFromWhere(){
		return this.fromWhere;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return this.title;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return this.type;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return this.content;
	}

	public void setAttachs(String attachs){
		this.attachs = attachs;
	}

	public String getAttachs(){
		return this.attachs;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setAnnunciateRange(Integer annunciateRange){
		this.annunciateRange = annunciateRange;
	}

	public Integer getAnnunciateRange(){
		return this.annunciateRange;
	}

	public void setIsEnable(Integer isEnable){
		this.isEnable = isEnable;
	}

	public Integer getIsEnable(){
		return this.isEnable;
	}

	public void setIsDelete(Integer isDelete){
		this.isDelete = isDelete;
	}

	public Integer getIsDelete(){
		return this.isDelete;
	}

	public void setIsDisplay(Integer isDisplay){
		this.isDisplay = isDisplay;
	}

	public Integer getIsDisplay(){
		return this.isDisplay;
	}
	
	public String getCrtName() {
		return crtName;
	}

	public void setCrtName(String crtName) {
		this.crtName = crtName;
	}


	public Boolean getIsTop() {
		return isTop;
	}

	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}

	public Integer getAnnunciateType() {
		return annunciateType;
	}

	public void setAnnunciateType(Integer annunciateType) {
		this.annunciateType = annunciateType;
	}

	public String getOrgsJoinIds() {
		return orgsJoinIds;
	}

	public void setOrgsJoinIds(String orgsJoinIds) {
		this.orgsJoinIds = orgsJoinIds;
	}

	public Integer getOrgsJoinCount() {
		return orgsJoinCount;
	}

	public void setOrgsJoinCount(Integer orgsJoinCount) {
		this.orgsJoinCount = orgsJoinCount;
	}

	public Integer getIsForward() {
		return isForward;
	}

	public void setIsForward(Integer isForward) {
		this.isForward = isForward;
	}

	public String getForwardDescription() {
		return forwardDescription;
	}

	public void setForwardDescription(String forwardDescription) {
		this.forwardDescription = forwardDescription;
	}

	public String getSendDescription() {
		return sendDescription;
	}

	public void setSendDescription(String sendDescription) {
		this.sendDescription = sendDescription;
	}

	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof JyAnnunciate))
				return false;
			JyAnnunciate castOther = (JyAnnunciate) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


