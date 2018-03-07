/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.service.MailService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.Email;
import com.tmser.tr.uc.bo.Login;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.VerificationCode;
import com.tmser.tr.uc.dao.LoginDao;
import com.tmser.tr.uc.dao.UserDao;
import com.tmser.tr.uc.dao.VerificationCodeDao;
import com.tmser.tr.uc.service.VerificationCodeService;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.DateUtils;
import com.tmser.tr.utils.SecurityCode;
import com.tmser.tr.utils.SecurityCode.SecurityCodeLevel;
/**
 * 保存邮箱验证码 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: VerificationCode.java, v 1.0 2015-08-05 Generate Tools Exp $
 */
@Service
@Transactional
public class VerificationCodeServiceImpl extends AbstractService<VerificationCode, Integer> implements VerificationCodeService {

	private static final Logger logger = LoggerFactory.getLogger(VerificationCodeServiceImpl.class);
	
	@Autowired
	private VerificationCodeDao verificationCodeDao;
	
	@Autowired
	private UserDao userdao;
	
	@Autowired
	private LoginDao logindao;
	
	@Autowired
	private MailService mailService;
	
	@Value("#{config.getProperty('mail_admin','jiaoyan@mainbo.com')}")
	private String from;
	
	@Value("#{config.getProperty('mail_livetime','600')}")
	private Integer liveTime;
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<VerificationCode, Integer> getDAO() {
		return verificationCodeDao;
	}

	/**
	 * @param uid
	 * @param mail
	 * @see com.tmser.tr.uc.service.VerificationCodeService#saveMail(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean valiadteAndsaveMail(String mail,String code) {
		
		    try {
				verificationCodeDao.deleteValidCode(liveTime);
			} catch (Exception e) {
				logger.error("clear valid code failed", e);
			}
		
			User oldUser = (User) WebThreadLocalUtils
					.getSessionAttrbitue(SessionKey.CURRENT_USER);
			VerificationCode vcCode=new VerificationCode();
			vcCode.setUserId(oldUser.getId());
			vcCode.setVerificationCode(mail+"-"+code);
			vcCode.addOrder(" creatTime desc");
			List<VerificationCode> vList= find(vcCode,1);
			for(VerificationCode vc:vList) {
			    if(((new Date()).getTime()-vc.getCreatTime().getTime())/1000<=600) {
			    		oldUser.setMail(mail);
			 			Login login=new Login();
			 			login.setId(oldUser.getId());
			 			login.setMail(mail);
			 			logindao.update(login);//更新该用户的邮箱
			 			
			 			User user=new User();
			 			user.setId(oldUser.getId());
			 			user.setMail(mail);
			 			user.setMailValid(true);
			 			user.setMailView(true);
			 			userdao.update(user);
			 			
			 			delete(vc.getId());
			 			return true;
				}
			}
			
			return false;
	}
	
	protected boolean sendMail(String toemail,String code) throws Exception{
		Email email = new Email();
		email.setToAddress(toemail);
		email.setFromAddress(from);
		email.setSubject("账号绑定验证码");
		StringBuilder content = new StringBuilder("<table cellpadding=\"0\" cellspacing=\"0\" width=\"96%\" style=\"background:#ffffff;border:1px solid rgb(204,204,204);margin:2%;\">")
		.append("<tbody><tr><td width=\"30px;\">&nbsp;</td><td align=\"\">")
		.append("<div style=\"line-height:40px;height:40px;\">&nbsp;</div><p style=\"margin:0px;padding:0px;\">")
		.append("<strong style=\"font-size:14px;line-height:30px;color:#333333;font-family:arial,sans-serif;\">亲爱的用户：")
		.append("</strong></p><div style=\"line-height:20px;height:20px;\">&nbsp;</div><p style=\"margin:0px;padding:0px;line-height:30px;font-size:14px;color:#333333;font-family:'宋体',arial,sans-serif;\">")
		.append("您好！感谢您使用教研服务，您正在进行邮箱登录绑定验证，本次请求的验证码为：</p><p style=\"margin:0px;padding:0px;line-height:30px;font-size:14px;color:#333333;font-family:'宋体',arial,sans-serif;\">")
		.append("<b style=\"font-size:18px;color:#ff9900\">")
		.append(code).append("</b><span style=\"margin:0px;padding:0px;margin-left:10px;line-height:30px;font-size:14px;color:#979797;font-family:'宋体',arial,sans-serif;\">")
		.append("(为了保障您帐号的安全性，请在1小时内完成验证。)</span></p><div style=\"line-height:80px;height:80px;\">&nbsp;</div><p style=\"margin:0px;padding:0px;line-height:30px;font-size:14px;color:#333333;font-family:'宋体',arial,sans-serif;\">")
		.append("教研平台服务中心</p><p style=\"margin:0px;padding:0px;line-height:30px;font-size:14px;color:#333333;font-family:'宋体',arial,sans-serif;\">")
		.append(DateUtils.getDate())
		.append("</p><div style=\"line-height:20px;height:20px;\">&nbsp;</div></td><td width=\"30px;\">&nbsp;</td></tr></tbody></table>");
		
		email.setContent(content.toString());
		try {
			mailService.sendMail(email);
		} catch (Exception e) {
				logger.error("邮件发送失败!");
				logger.error("",e);
			    throw e;
		}
		return true;
	}

	/**
	 * @param uid
	 * @param mail
	 * @return
	 * @throws Exception 
	 * @see com.tmser.tr.uc.service.VerificationCodeService#sendVarification(java.lang.Integer, java.lang.String)
	 */
	@Override
	public boolean sendVarification(Model m,String mail) throws Exception {
		User user=(User) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_USER);
		String code = SecurityCode.getSecurityCode(6,SecurityCodeLevel.Simple,false);
		VerificationCode vcCode=new VerificationCode();
		vcCode.setUserId(user.getId());
		vcCode.setVerificationCode(mail+"-"+code);
		vcCode.setCreatTime(new Date());
		
		sendMail(mail, code);
		verificationCodeDao.insert(vcCode);
		return true;
	}

}
