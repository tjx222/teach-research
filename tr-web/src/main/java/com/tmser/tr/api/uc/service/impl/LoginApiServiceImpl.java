/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.api.uc.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tmser.tr.api.uc.service.LoginApiService;
import com.tmser.tr.common.bo.BaseObject;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.uc.bo.Login;
import com.tmser.tr.uc.dao.LoginDao;
import com.tmser.tr.uc.exception.UcException;
import com.tmser.tr.uc.log.LoginLogUtils;
import com.tmser.tr.uc.service.PasswordService;
import com.tmser.tr.uc.utils.HmacSHA256Utils;

/**
 * <pre>
 * 登录服务
 * </pre>
 *
 * @author tmser
 * @version $Id: LoginServiceImpl.java, v 1.0 2015年1月31日 下午1:56:04 tmser Exp $
 */
@Service
@Transactional
public class LoginApiServiceImpl extends AbstractService<Login, Integer> implements LoginApiService {
	private static final Logger logger = LoggerFactory.getLogger(LoginApiServiceImpl.class);
	@Resource
	private LoginDao loginDao;
	@Resource
	private PasswordService passwordService;

	/**
	 * 按用户名查找
	 * 
	 * @param username
	 * @return
	 * @see com.tmser.tr.uc.service.LoginService#findByUsername(java.lang.String)
	 */
	@Override
	public Login findByUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return null;
		}
		Login model = new Login();
		model.setLoginname(username);
		model.setIsAdmin(false);
		return loginDao.getOne(model);
	}

	/**
	 * 按邮箱查找用户
	 * 
	 * @param email
	 * @return
	 * @see com.tmser.tr.uc.service.LoginService#findByEmail(java.lang.String)
	 */
	@Override
	public Login findByEmail(String email) {
		if (StringUtils.isEmpty(email)) {
			return null;
		}
		Login model = new Login();
		model.setMail(email);
		model.setIsAdmin(false);
		return loginDao.getOne(model);
	}

	/**
	 * 按手机号查找
	 * 
	 * @param mobilePhoneNumber
	 * @return
	 * @see com.tmser.tr.uc.service.LoginService#findByMobilePhoneNumber(java.lang.String)
	 */
	@Override
	public Login findByCellPhone(String cellphone) {
		if (StringUtils.isEmpty(cellphone)) {
			return null;
		}
		Login model = new Login();
		model.setCellphone(cellphone);
		model.setIsAdmin(false);
		return loginDao.getOne(model);
	}

	/**
	 * @param principal
	 * @param password
	 * @return
	 * @throws UcException
	 * @see com.tmser.tr.uc.service.LoginService#login(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public Result login(String principal, String password) {
		if (StringUtils.isEmpty(principal) || StringUtils.isEmpty(password)) {
			LoginLogUtils.log(principal, "loginError", "principal is empty");
			logger.debug("用户名或者密码不能为空");
			return new Result(0, "用户名或者密码不能为空", new Date(), null);
		}
		// 密码如果不在指定范围内 肯定错误
		if (password.length() < Login.PASSWORD_MIN_LENGTH || password.length() > Login.PASSWORD_MAX_LENGTH) {
			LoginLogUtils.log(principal, "loginError", "password length error! password is between {} and {}", Login.PASSWORD_MIN_LENGTH, Login.PASSWORD_MAX_LENGTH);
			logger.debug("密码不符合规定");
			return new Result(0, "密码不符合规定", new Date(), null);
		}

		Login login = null;

		if (maybeUsername(principal)) {
			login = findByUsername(principal);
		}

		if (login == null && maybeEmail(principal)) {
			login = findByEmail(principal);
		}

		if (login == null && maybeMobilePhoneNumber(principal)) {
			login = findByCellPhone(principal);
		}

		if (login == null || Boolean.TRUE.equals(login.getDeleted())) {
			LoginLogUtils.log(principal, "loginError", "user is not exists!");
			logger.debug("用户不存在，或者用户已删除");
			return new Result(0, "用户不存在，或者用户已删除", new Date(), null);
		}
		passwordService.validate(login, password);

		if (BaseObject.ENABLE != login.getEnable()) {
			LoginLogUtils.log(principal, "loginError", "user is blocked!");
			logger.debug("用户已禁用");
			return new Result(0, "用户已禁用", new Date(), null);
		}

		LoginLogUtils.log(principal, "loginSuccess", "");

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("userid", login.getId());
		data.put("username", login.getLoginname());
		data.put("token", HmacSHA256Utils.digest(login.getPassword(), login.getLoginname()));
		return new Result(1, "用户登录成功", new Date(), data);
	}

	private boolean maybeUsername(String principal) {
		if (!principal.matches(Login.USERNAME_PATTERN)) {
			return false;
		}
		// 如果用户名不在指定范围内也是错误的
		if (principal.length() < Login.USERNAME_MIN_LENGTH || principal.length() > Login.USERNAME_MAX_LENGTH) {
			return false;
		}

		return true;
	}

	private boolean maybeEmail(String principal) {
		if (!principal.matches(Login.EMAIL_PATTERN)) {
			return false;
		}
		return true;
	}

	private boolean maybeMobilePhoneNumber(String principal) {
		if (!principal.matches(Login.MOBILE_PHONE_NUMBER_PATTERN)) {
			return false;
		}
		return true;
	}

	@Override
	public BaseDAO<Login, Integer> getDAO() {
		return loginDao;
	}

}
