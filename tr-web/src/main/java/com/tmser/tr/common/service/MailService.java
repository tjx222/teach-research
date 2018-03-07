/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.common.service;

import java.io.IOException;

import javax.mail.MessagingException;

import com.tmser.tr.common.vo.Email;


/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: MailService.java, v 1.0 2015年8月7日 上午11:21:50 tmser Exp $
 */
public interface MailService {

	/**
	 * 发送邮件，以同步的方式发送
	 * @param email
	 * @throws MessagingException
	 * @throws IOException
	 */
	 void sendMail(Email email) throws MessagingException, IOException;
	 
	 /**
	  * 发送邮件以异步方式发送
	  * @param email
	  */
	 void sendMailByAsynchronousMode(final Email email);
	 
	 /**
	  * 更新邮件发送信息
	  * 适应动态配置
	  * @param host
	  * @param port
	  * @param username
	  * @param password
	  * @return
	  */
	 boolean updateDefaultConfig(String host,Integer port ,String username,String password);
}
