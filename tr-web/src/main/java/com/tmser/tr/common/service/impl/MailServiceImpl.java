/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.common.service.impl;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.tmser.tr.common.service.MailService;
import com.tmser.tr.common.vo.Email;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: MailServiceImpl.java, v 1.0 2015年8月7日 上午11:22:23 tmser Exp $
 */
public class MailServiceImpl implements MailService {
	
	private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

	private JavaMailSender mailSender;// 注入Spring封装的javamail，Spring的xml中已让框架装配
	
	private TaskExecutor taskExecutor;// 注入Spring封装的异步执行器

	@Override
	public void sendMail(Email email) throws MessagingException, IOException {
		sendMailBySynchronizationMode(email);
	}

	@Override
	public void sendMailByAsynchronousMode(final Email email) {
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					sendMailBySynchronizationMode(email);
				} catch (Exception e) {
					logger.error("send mail failed {}",email);
					logger.error("",e);
				}
			}
		});
	}

	public void sendMailBySynchronizationMode(Email email)
			throws MessagingException, IOException {
		MimeMessage mime = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mime, true, "utf-8");
		helper.setFrom(email.getFromAddress());// 发件人
		helper.setTo(email.getToAddress());// 收件人
		helper.setReplyTo(email.getFromAddress());// 回复到
		helper.setSubject(email.getSubject());// 邮件主题
		helper.setText(email.getContent(), true);// true表示设定html格式
		mailSender.send(mime);
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public TaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	
	@Override
	public boolean updateDefaultConfig(String host,Integer port ,String username,String password){
		if(this.mailSender != null && this.mailSender instanceof JavaMailSenderImpl){
			JavaMailSenderImpl ms = (JavaMailSenderImpl)this.mailSender;
			ms.setHost(host);
			ms.setPort(port);
			ms.setUsername(username);
			ms.setPassword(password);
			logger.info("update mail success!");
			return true;
		}
		
		return false;
	}

}
