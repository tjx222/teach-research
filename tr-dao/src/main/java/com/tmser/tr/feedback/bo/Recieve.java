/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.feedback.bo;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import com.tmser.tr.common.bo.BaseObject;
import com.tmser.tr.manage.resources.bo.Resources;

 /**
 * 用户反馈信息 Entity
 * <pre>
 *@author lijianghu
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Recieve.java, v 1.0 2015-09-15 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = Recieve.TABLE_NAME)
public class Recieve extends BaseObject {
	public static final String TABLE_NAME="feedback_recieve";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	/**
	 *反馈者id
	 **/
	@Column(name="user_id_sender")
	private Integer userIdSender;

	/**
	 *反馈者姓名
	 **/
	@Column(name="user_name_sender",length=32)
	private String userNameSender;

	/**
	 *反馈内容
	 **/
	@Column(name="message",length=500)
	private String message;

	/**
	 *附件1
	 **/
	@Column(name="attachment_1",length=256)
	private String attachment1;

	/**
	 *附件2
	 **/
	@Column(name="attachment_2",length=256)
	private String attachment2;

	/**
	 *附件3
	 **/
	@Column(name="attachment_3",length=256)
	private String attachment3;

	@Column(name="attachment_1_name",length=256)
	private String attachment1Name;

	@Column(name="attachment_2_name",length=256)
	private String attachment2Name;

	@Column(name="attachment_3_name",length=256)
	private String attachment3Name;
	/**
	 * org_id *数据库*
	 */
	/**
	 *机构id
	 **/
	@Column(name="org_id")
	private Integer orgId;
	
	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	@Transient
	private List<Resources> list;
	
	public List<Resources> getList() {
		return list;
	}

	public void setList(List<Resources> list) {
		this.list = list;
	}

	/**
	 *反馈时间
	 **/
	@Column(name="sender_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date senderTime;
	/**
	 * 是否已回复
	 * @param id
	 */
	private Integer ishavareply;

	public Integer getIshavareply() {
		return ishavareply;
	}

	public void setIshavareply(Integer ishavareply) {
		this.ishavareply = ishavareply;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setUserIdSender(Integer userIdSender){
		this.userIdSender = userIdSender;
	}

	public Integer getUserIdSender(){
		return this.userIdSender;
	}

	public void setUserNameSender(String userNameSender){
		this.userNameSender = userNameSender;
	}

	public String getUserNameSender(){
		return this.userNameSender;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return this.message;
	}

	public void setAttachment1(String attachment1){
		this.attachment1 = attachment1;
	}

	public String getAttachment1(){
		return this.attachment1;
	}

	public void setAttachment2(String attachment2){
		this.attachment2 = attachment2;
	}

	public String getAttachment2(){
		return this.attachment2;
	}

	public void setAttachment3(String attachment3){
		this.attachment3 = attachment3;
	}

	public String getAttachment3(){
		return this.attachment3;
	}

	public void setAttachment1Name(String attachment1Name){
		this.attachment1Name = attachment1Name;
	}

	public String getAttachment1Name(){
		return this.attachment1Name;
	}

	public void setAttachment2Name(String attachment2Name){
		this.attachment2Name = attachment2Name;
	}

	public String getAttachment2Name(){
		return this.attachment2Name;
	}

	public void setAttachment3Name(String attachment3Name){
		this.attachment3Name = attachment3Name;
	}

	public String getAttachment3Name(){
		return this.attachment3Name;
	}

	public void setSenderTime(Date senderTime){
		this.senderTime = senderTime;
	}

	public Date getSenderTime(){
		return this.senderTime;
	}

	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof Recieve))
				return false;
			Recieve castOther = (Recieve) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


