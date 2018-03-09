package com.tmser.tr.feedback.bo;

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

import com.tmser.tr.common.bo.BaseObject;
/**
* 用户反馈信息 Entity
* <pre>
 *@author tmser
* </pre>
*
* @author Generate Tools
* @version $Id: Reply.java, v 1.0 2015-09-15 Generate Tools Exp $
*/
@SuppressWarnings("serial")
@Entity
@Table(name = Reply.TABLE_NAME)
public class Reply extends BaseObject {
	public static final String TABLE_NAME="feedback_reply";
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	/**
	 *回复者id
	 **/
	@Column(name="user_id_receiver")
	private Integer Receiver;

	/**
	 *机构id
	 **/
	@Column(name="org_id")
	private Integer orgId;
	/**
	 *回复者姓名
	 **/
	@Column(name="user_name_receiver",length=32)
	private String userNameReceiver;
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	/**
	 *回复内容
	 **/
	@Column(name="content",length=500)
	private String content;

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
	 *回复时间
	 **/
	@Column(name="sender_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date senderTime;

	/**
	 * 关联反馈表外键pid
	 * @param pid
	 */
	@Column(name="pid")
	private Integer pid ;

	public void setId(Integer id){
		this.id = id;
	}


	public Integer getReceiver() {
		return Receiver;
	}


	public void setReceiver(Integer receiver) {
		Receiver = receiver;
	}


	public String getUserNameReceiver() {
		return userNameReceiver;
	}


	public void setUserNameReceiver(String userNameReceiver) {
		this.userNameReceiver = userNameReceiver;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getAttachment1() {
		return attachment1;
	}


	public void setAttachment1(String attachment1) {
		this.attachment1 = attachment1;
	}


	public String getAttachment2() {
		return attachment2;
	}


	public void setAttachment2(String attachment2) {
		this.attachment2 = attachment2;
	}


	public String getAttachment3() {
		return attachment3;
	}


	public void setAttachment3(String attachment3) {
		this.attachment3 = attachment3;
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


	public String getAttachment3Name() {
		return attachment3Name;
	}


	public void setAttachment3Name(String attachment3Name) {
		this.attachment3Name = attachment3Name;
	}


	public Date getSenderTime() {
		return senderTime;
	}


	public void setSenderTime(Date senderTime) {
		this.senderTime = senderTime;
	}


	public Integer getPid() {
		return pid;
	}


	public void setPid(Integer pid) {
		this.pid = pid;
	}


	public Integer getId() {
		return id;
	}


	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof Reply))
				return false;
			Reply castOther = (Reply) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}
