/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.notice.bo;

import java.util.Date;



import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import com.tmser.tr.common.bo.BaseObject;

 /**
 * 站内信 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: JyLetter.java, v 1.0 2015-05-12 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = JyLetter.TABLE_NAME)
public class JyLetter extends BaseObject {
	public static final String TABLE_NAME="jy_letter";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	/**
	 *通知发送者id
	 **/
	@Column(name="sender_id")
	private Integer senderId;

	/**
	 *通知接受者id
	 **/
	@Column(name="receiver_id")
	private Integer receiverId;

	/**
	 *发送时间
	 **/
	@Column(name="send_date",nullable=false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date sendDate;

	/**
	 *内容
	 **/
	@Column(name="content",length=21845)
	private String content;

	/**
	 *发送者状态,1：已删除，0：未删除
	 **/
	@Column(name="sender_state")
	private Integer senderState;

	/**
	 *发送者更改状态时间
	 **/
	@Column(name="sender_state_change_date",nullable=false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date senderStateChangeDate;

	/**
	 *接受者状态，2：已阅读，1：已删除，0：未阅读
	 **/
	@Column(name="receiver_state")
	private Integer receiverState;

	/**
	 *接受通知者更改状态时间
	 **/
	@Column(name="receiver_state_change_date",nullable=false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date receiverStateChangeDate;

	@Column(name="parent_id")
	private Long parentId;

	@Column(name="parent_ids",length=200)
	private String parentIds;


	public void setId(Long id){
		this.id = id;
	}

	public Long getId(){
		return this.id;
	}

	public void setSenderId(Integer senderId){
		this.senderId = senderId;
	}

	public Integer getSenderId(){
		return this.senderId;
	}

	public void setReceiverId(Integer receiverId){
		this.receiverId = receiverId;
	}

	public Integer getReceiverId(){
		return this.receiverId;
	}

	public void setSendDate(Date sendDate){
		this.sendDate = sendDate;
	}

	public Date getSendDate(){
		return this.sendDate;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return this.content;
	}

	public void setSenderState(Integer senderState){
		this.senderState = senderState;
	}

	public Integer getSenderState(){
		return this.senderState;
	}

	public void setSenderStateChangeDate(Date senderStateChangeDate){
		this.senderStateChangeDate = senderStateChangeDate;
	}

	public Date getSenderStateChangeDate(){
		return this.senderStateChangeDate;
	}

	public void setReceiverState(Integer receiverState){
		this.receiverState = receiverState;
	}

	public Integer getReceiverState(){
		return this.receiverState;
	}

	public void setReceiverStateChangeDate(Date receiverStateChangeDate){
		this.receiverStateChangeDate = receiverStateChangeDate;
	}

	public Date getReceiverStateChangeDate(){
		return this.receiverStateChangeDate;
	}

	public void setParentId(Long parentId){
		this.parentId = parentId;
	}

	public Long getParentId(){
		return this.parentId;
	}

	public void setParentIds(String parentIds){
		this.parentIds = parentIds;
	}

	public String getParentIds(){
		return this.parentIds;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof JyLetter))
				return false;
			JyLetter castOther = (JyLetter) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


