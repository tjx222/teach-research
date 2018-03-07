/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.companion.bo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.QueryObject;

/**
 * 同伴互助留言 Entity
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author Generate Tools
 * @version $Id: JyCompanionMessage.java, v 1.0 2015-05-20 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = JyCompanionMessage.TABLE_NAME)
public class JyCompanionMessage extends QueryObject {
	public static final String TABLE_NAME = "jy_companion_message";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 发送者id
	 **/
	@Column(name = "user_id_sender")
	private Integer userIdSender;

	/**
	 * 发送者姓名
	 **/
	@Column(name = "user_name_sender", length = 32)
	private String userNameSender;

	/**
	 * 接受者id
	 **/
	@Column(name = "user_id_receiver")
	private Integer userIdReceiver;

	/**
	 * 接受者姓名
	 **/
	@Column(name = "user_name_receiver", length = 32)
	private String userNameReceiver;

	/**
	 * 留言内容
	 **/
	@Column(name = "message", length = 1024)
	private String message;

	/**
	 * 附件1
	 **/
	@Column(name = "attachment_1", length = 32)
	private String attachment1;

	/**
	 * 附件2
	 **/
	@Column(name = "attachment_2", length = 32)
	private String attachment2;

	/**
	 * 附件3
	 **/
	@Column(name = "attachment_3", length = 32)
	private String attachment3;

	/**
	 * 附件1
	 **/
	@Column(name = "attachment_1_name", length = 256)
	private String attachment1Name;

	/**
	 * 附件2
	 **/
	@Column(name = "attachment_2_name", length = 256)
	private String attachment2Name;

	/**
	 * 附件3
	 **/
	@Column(name = "attachment_3_name", length = 256)
	private String attachment3Name;

	/**
	 * 留言发送时间
	 **/
	@Column(name = "sender_time", nullable = false)
	private Date senderTime;

	private String senderTimeStr;

	/**
	 * 设置查询时间
	 * 
	 * @param timeStr
	 */
	public void setSearchTime(String timeStr) {
		if (StringUtils.isNotBlank(timeStr)) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				senderTime = df.parse(timeStr);
			} catch (ParseException e) {

			}
		}
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setUserIdSender(Integer userIdSender) {
		this.userIdSender = userIdSender;
	}

	public Integer getUserIdSender() {
		return this.userIdSender;
	}

	public void setUserNameSender(String userNameSender) {
		this.userNameSender = userNameSender;
	}

	public String getUserNameSender() {
		return this.userNameSender;
	}

	public void setUserIdReceiver(Integer userIdReceiver) {
		this.userIdReceiver = userIdReceiver;
	}

	public Integer getUserIdReceiver() {
		return this.userIdReceiver;
	}

	public void setUserNameReceiver(String userNameReceiver) {
		this.userNameReceiver = userNameReceiver;
	}

	public String getUserNameReceiver() {
		return this.userNameReceiver;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	public void setAttachment1(String attachment1) {
		this.attachment1 = attachment1;
	}

	public String getAttachment1() {
		return this.attachment1;
	}

	public void setAttachment2(String attachment2) {
		this.attachment2 = attachment2;
	}

	public String getAttachment2() {
		return this.attachment2;
	}

	public void setAttachment3(String attachment3) {
		this.attachment3 = attachment3;
	}

	public String getAttachment3() {
		return this.attachment3;
	}

	public void setSenderTime(Date senderTime) {
		this.senderTime = senderTime;
	}

	public Date getSenderTime() {
		return this.senderTime;
	}

	public String getSenderTimeStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.senderTimeStr = sdf.format(this.senderTime);
		return this.senderTimeStr;
	}

	public void setSenderTimeStr(String senderTimeStr) {
		this.senderTimeStr = senderTimeStr;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof JyCompanionMessage))
			return false;
		JyCompanionMessage castOther = (JyCompanionMessage) other;
		return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}

	public String getAttachment3Name() {
		return attachment3Name;
	}

	public void setAttachment3Name(String attachment3Name) {
		this.attachment3Name = attachment3Name;
	}

	public String getAttachment1Name() {
		return attachment1Name;
	}

	public void setAttachment1Name(String attachment1Name) {
		this.attachment1Name = attachment1Name;
	}

	public String getAttachment2Name() {
		return attachment2Name;
	}

	public void setAttachment2Name(String attachment2Name) {
		this.attachment2Name = attachment2Name;
	}
}
