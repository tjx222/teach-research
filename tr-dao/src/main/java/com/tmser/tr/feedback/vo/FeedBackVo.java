package com.tmser.tr.feedback.vo;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.tmser.tr.common.page.Page;

/**
 *用户反馈vo
 * <pre>
 *
 * </pre>
 *
 * @author admin
 * @version $Id: FeedBackVo.java, v 1.0 2015年9月16日 下午3:59:12 admin Exp $
 */
public class FeedBackVo extends Page implements Serializable {
	/**
	 * <pre>
	 *
	 * </pre>
	 */
	private static final long serialVersionUID = -2217349002372103327L;
	private Integer id;//反馈表主键ID
	private Integer userIdSender;//反馈者id
	private String userNameSender;//反馈者姓名
	private String message;//反馈内容
	private String attachmentRecieveId;//附件ID【多个，以逗号隔开】
	private String attachment1RecieveName;//附件名
	private Date senderTime;//反馈时间
	private Date replyDate;//回复时间
	private String replyContent;//回复内容
	private Integer pid;//回复表主键ID
	private List<?> list;//各附件详细信息
	private String userNameReceiver;//回复者姓名
	public String getUserNameReceiver() {
		return userNameReceiver;
	}
	public void setUserNameReceiver(String userNameReceiver) {
		this.userNameReceiver = userNameReceiver;
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserIdSender() {
		return userIdSender;
	}
	public void setUserIdSender(Integer userIdSender) {
		this.userIdSender = userIdSender;
	}
	public String getUserNameSender() {
		return userNameSender;
	}
	public void setUserNameSender(String userNameSender) {
		this.userNameSender = userNameSender;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getAttachmentRecieveId() {
		return attachmentRecieveId;
	}
	public void setAttachmentRecieveId(String attachmentRecieveId) {
		this.attachmentRecieveId = attachmentRecieveId;
	}
	public String getAttachment1RecieveName() {
		return attachment1RecieveName;
	}
	public void setAttachment1RecieveName(String attachment1RecieveName) {
		this.attachment1RecieveName = attachment1RecieveName;
	}
	public Date getSenderTime() {
		return senderTime;
	}
	public void setSenderTime(Date senderTime) {
		this.senderTime = senderTime;
	}
	public Date getReplyDate() {
		return replyDate;
	}
	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
}
