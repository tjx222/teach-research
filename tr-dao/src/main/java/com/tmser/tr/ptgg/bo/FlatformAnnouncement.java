/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.ptgg.bo;

import java.util.Date;


import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.BaseObject;

 /**
 * 平台公告---->首页广告 Entity
 * <pre>
 *	@author tmser
 *	@date:2015-9-28
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Flatform_announcement.java, v 1.0 2015-09-28 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = FlatformAnnouncement.TABLE_NAME)
public class FlatformAnnouncement extends BaseObject {
	public static final String TABLE_NAME="jy_flatform_announcement";
	
		/**
	 *主键id
	 **/
	@Id
	@Column(name="id")
	private Integer id;

	/**
	 *发布者id
	 **/
	@Column(name="userId")
	private Integer userid;

	/**
	 *发布者姓名
	 **/
	@Column(name="userName",length=32)
	private String username;

	/**
	 *大图片id
	 **/
	@Column(name="pictureId",length=132)
	private String pictureid;
	/**
	 * 大图片名称
	 */
	@Column(name="pictureName",length=32)
	private String pictureName;
	/**
	 *小图片id
	 **/
	@Column(name="littlepictureId",length=32)
	private String littlepictureId;
	/**
	 * 小图片名称
	 */
	@Column(name="littlepictureName",length=32)
	private String littlepictureName;
	/**
	 *发布时间
	 **/
	@Column(name="cdate")
	private Date cdate;

	/**
	 *是否显示 0:不显示 1:显示
	 **/
	@Column(name="isview")
	private Integer isview;


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

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return this.username;
	}

	public void setPictureid(String pictureid){
		this.pictureid = pictureid;
	}

	public String getPictureid(){
		return this.pictureid;
	}

	public void setCdate(Date cdate){
		this.cdate = cdate;
	}

	public Date getCdate(){
		return this.cdate;
	}
	public String getPictureName() {
		return pictureName;
	}

	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}

	public Integer getIsview() {
		return isview;
	}

	public void setIsview(Integer isview) {
		this.isview = isview;
	}



	public String getLittlepictureId() {
		return littlepictureId;
	}

	public void setLittlepictureId(String littlepictureId) {
		this.littlepictureId = littlepictureId;
	}

	public String getLittlepictureName() {
		return littlepictureName;
	}

	public void setLittlepictureName(String littlepictureName) {
		this.littlepictureName = littlepictureName;
	}

	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof FlatformAnnouncement))
				return false;
			FlatformAnnouncement castOther = (FlatformAnnouncement) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


