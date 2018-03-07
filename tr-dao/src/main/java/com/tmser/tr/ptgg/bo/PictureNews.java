/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.ptgg.bo;



import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.BaseObject;

 /**
 * 图片新闻 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: PictureNews.java, v 1.0 2015-10-12 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = PictureNews.TABLE_NAME)
public class PictureNews extends BaseObject {
	public static final String TABLE_NAME="jy_flatform_picturenews";
	
		/**
	 *主键id
	 **/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	/**
	 *新闻标题
	 **/
	@Column(name="title",length=32)
	private String title;

	/**
	 *新闻内容
	 **/
	@Column(name="content",length=21845)
	private String content;

	@Column(name="attachs",length=500)
	private String attachs;

	/**
	 *是否置顶 0：非置顶 1：置顶
	 **/
	@Column(name="istop")
	private Integer istop;

	/**
	 *是否删除 0：未删除 1：已删除
	 **/
	@Column(name="isdelete")
	private Integer isdelete;

	/**
	 *是否是草稿0：草稿 1：已发布
	 **/
	@Column(name="status")
	private Integer status;

	/**
	 *是否展示 0：不展示 1：展示
	 **/
	@Column(name="is_display")
	private Integer isDisplay;
	
	/**
	 *创建人姓名
	 **/
	@Column(name="crtname")
	private String crtname;

	/**
	 * 组织机构id
	 */
	@Column(name="org_id")
	private Integer orgId;

	/**
	 *父id
	 **/
	@Column(name="parentid")
	private Integer parentid;

	/**
	 *前台显示顺序
	 **/
	@Column(name="sort")
	private Integer sort;
	
	@Transient
	private String path;//图片显示路径
	
	@Transient
	private String resId;//图片显示路径
	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getParentid() {
		return parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getCrtname() {
		return crtname;
	}

	public void setCrtname(String crtname) {
		this.crtname = crtname;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return this.title;
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

	public void setIstop(Integer istop){
		this.istop = istop;
	}

	public Integer getIstop(){
		return this.istop;
	}

	public void setIsdelete(Integer isdelete){
		this.isdelete = isdelete;
	}

	public Integer getIsdelete(){
		return this.isdelete;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setIsDisplay(Integer isDisplay){
		this.isDisplay = isDisplay;
	}

	public Integer getIsDisplay(){
		return this.isDisplay;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof PictureNews))
				return false;
			PictureNews castOther = (PictureNews) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


