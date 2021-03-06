/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.common.vo;

/**
 * <pre>
 *  邮件实体
 * </pre>
 *
 * @author tmser
 * @version $Id: Mail.java, v 1.0 2015年8月7日 上午11:06:05 tmser Exp $
 */
public class Email {

	/** 发件人 **/
	private String fromAddress;

	/** 收件人 **/
	private String toAddress;

	/** 邮件主题 **/
	private String subject;

	/** 邮件内容 **/
	private String content;

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
