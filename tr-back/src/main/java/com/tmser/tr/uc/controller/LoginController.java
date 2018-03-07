/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.uc.Constants;
import com.tmser.tr.uc.bo.Login;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.exception.UserPasswordRetryLimitExceedException;
import com.tmser.tr.uc.service.LoginService;
import com.tmser.tr.uc.service.PasswordService;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * <pre>
 * 用户登录，找回密码
 * </pre>
 *
 * @author tmser
 * @version $Id: LoginController.java, v 1.0 2015年2月1日 下午10:55:37 tmser Exp $
 */
@Controller
@RequestMapping("/jy/uc")
public class LoginController extends AbstractController {
	
	@Resource
	private LoginService loginService;

	@Resource
	private PasswordService passwordService;

	@RequestMapping(value = { "/{login:login;?.*}" })
	public String loginForm(ModelMap model) {
		// 表示退出
		if (!StringUtils.isEmpty(WebThreadLocalUtils.getParameter("logout"))) {
			model.addAttribute(Constants.MESSAGE,
					messageSource.getMessage("user.logout.success", null, null));
		}

		// 表示用户删除了 @see org.apache.shiro.web.filter.user.SysUserFilter
		if (!StringUtils.isEmpty(WebThreadLocalUtils.getParameter("notfound"))) {
			model.addAttribute(Constants.ERROR,
					messageSource.getMessage("user.notfound", null, null));
		}

		// 表示用户被管理员强制退出
		if (!StringUtils.isEmpty(WebThreadLocalUtils.getParameter("forcelogout"))) {
			model.addAttribute(Constants.ERROR,
					messageSource.getMessage("user.forcelogout", null, null));
		}

		// 表示用户输入的验证码错误
		if (!StringUtils.isEmpty(WebThreadLocalUtils.getParameter("jcaptchaError"))) {
			model.addAttribute(Constants.ERROR, messageSource.getMessage(
					"jcaptcha.validate.error", null, null));
		}
		// 表示用户身份错误错误
		if (!StringUtils.isEmpty(WebThreadLocalUtils.getParameter("userspcaeError"))) {
			model.addAttribute(Constants.ERROR, messageSource.getMessage(
					"user.space.not.exists", null, null));
		}

		// 表示用户锁定了 @see org.apache.shiro.web.filter.user.SysUserFilter
		if (!StringUtils.isEmpty(WebThreadLocalUtils.getParameter("blocked"))) {
			// TODO 锁定原因
			model.addAttribute(Constants.ERROR, messageSource.getMessage(
					"user.blocked", new Object[] { "" }, null));
		}

		if (!StringUtils.isEmpty(WebThreadLocalUtils.getParameter("unknown"))) {
			model.addAttribute(Constants.ERROR,
					messageSource.getMessage("user.unknown.error", null, null));
		}

		// 登录失败了 提取错误消息
		Exception shiroLoginFailureEx = (Exception) WebThreadLocalUtils.getAttrbitue(
				FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		if (shiroLoginFailureEx != null) {
			if(shiroLoginFailureEx instanceof UnknownAccountException ||
			   shiroLoginFailureEx instanceof ExcessiveAttemptsException ||
			   shiroLoginFailureEx instanceof UserPasswordRetryLimitExceedException||
			   shiroLoginFailureEx instanceof LockedAccountException){
			   model.addAttribute(Constants.ERROR,
						shiroLoginFailureEx.getMessage());
			}else if(shiroLoginFailureEx instanceof AuthenticationException){
				model.addAttribute(Constants.ERROR,
						messageSource.getMessage("user.not.exists", null, null));
				logger.error("login failed",shiroLoginFailureEx);
			}else{
				model.addAttribute(Constants.ERROR,
						messageSource.getMessage("user.unknown.error", null, null));
				logger.error("login failed",shiroLoginFailureEx);
			}
		}

		// 如果用户直接到登录页面 先退出一下
		// 原因：isAccessAllowed实现是subject.isAuthenticated()---->即如果用户验证通过 就允许访问
		// 这样会导致登录一直死循环
		Subject subject = SecurityUtils.getSubject();
		if (subject != null && subject.isAuthenticated()) {
			subject.logout();
		}

		// 如果同时存在错误消息 和 普通消息 只保留错误消息
		if (model.containsAttribute(Constants.ERROR)) {
			model.remove(Constants.MESSAGE);
		}
		return "forward:/index";
	}
	
	@RequestMapping(value={"/switch","/workspace"})
	public String switchWorkSpace(Integer spaceid) {
		return "forward:" + loginService.toWorkSpace(spaceid);
	}
	
	@RequestMapping(value="/modifypsd",method=RequestMethod.GET)
	public String modifyPsd(Model m) {
		User us = (User) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_USER);
		m.addAttribute("user", us);
		m.addAttribute("login", loginService.findOne(us.getId()));
		return viewName("modifypsd");
	}
	
	
	@RequestMapping(value="/ajaxlogin",method=RequestMethod.GET)
	public String ajaxlogin() {
		return viewName("ajaxlogin");
	}
	
	
	@RequestMapping(value="/modifypsd",method=RequestMethod.POST)
	@ResponseBody
	public JuiResult modifyPsd(Login login,Model m) {
		User us = (User) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_USER);
		JuiResult rs = new JuiResult();
		String newpsd = login.getPassword();
		rs.setMessage("修改成功！");
		if(newpsd != null && newpsd.equals(login.getFlags())){
			Login oldLogin = loginService.findOne(us.getId());
			login.setPassword( passwordService.encryptPassword(oldLogin.getLoginname(),newpsd, oldLogin.getSalt()));
			login.setId(us.getId());
			Map<String,Object> paramMap = new HashMap<String,Object>(1);
			paramMap.put("psd", passwordService.encryptPassword(oldLogin.getLoginname(),login.getFlago(), oldLogin.getSalt()));
			login.addCustomCondition(" and password = :psd ", paramMap);
			if(loginService.update(login) == 0){
				rs.setStatusCode(JuiResult.FAILED);
				rs.setMessage("原密码输入错误！");
			}else{
				LoggerUtils.updateLogger("修改个人密码");
			}
		}else{
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("重复密码输入不一致！");
		}
		
		return rs;
	}
}
