/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.mobile;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.uc.Constants;
import com.tmser.tr.uc.bo.Login;
import com.tmser.tr.uc.exception.UcException;
import com.tmser.tr.uc.exception.UserBlockedException;
import com.tmser.tr.uc.exception.UserNotExistsException;
import com.tmser.tr.uc.exception.UserPasswordNotMatchException;
import com.tmser.tr.uc.exception.UserPasswordRetryLimitExceedException;
import com.tmser.tr.uc.service.LoginService;
import com.tmser.tr.uc.shiro.AbstractUserRealm;
import com.tmser.tr.uc.utils.HmacSHA256Utils;

/**
 * <pre>
 * 移动端自定义认证及权限收集器
 * </pre>
 *
 * @author tmser
 * @version $Id: UserRealm.java, v 1.0 2015年1月23日 下午6:23:54 tmser Exp $
 */
public class MobileUserRealm extends AbstractUserRealm {
	private static final Logger log = LoggerFactory.getLogger(MobileUserRealm.class);

	@Resource
	private LoginService loginService;

	/**
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		MobileUserToken upToken = (MobileUserToken) token;
		String username = upToken.getUsername().trim();
		String password = upToken.getToken();

		Login user = null;
		try {
			user = loginService.findLogin(username);
			if (user != null) {
				if (!password.equals(HmacSHA256Utils.digest(user.getPassword(), username))) {
					throw new UserPasswordNotMatchException();
				}
			} else {
				throw new UserNotExistsException();
			}
			WebThreadLocalUtils.setSessionAttrbitue(Constants.LOGIN_FROM_WHERE, Constants.LOGIN_FROM_LOCAL);
		} catch (UserNotExistsException e) {
			WebThreadLocalUtils.setAttrbitue(Constants.ERROR, e.getMessage());
			throw new UnknownAccountException(e.getMessage(), e);
		} catch (UserPasswordNotMatchException e) {
			WebThreadLocalUtils.setAttrbitue(Constants.ERROR, e.getMessage());
			throw new AuthenticationException(e.getMessage(), e);
		} catch (UserPasswordRetryLimitExceedException e) {
			WebThreadLocalUtils.setAttrbitue(Constants.ERROR, e.getMessage());
			throw new ExcessiveAttemptsException(e.getMessage(), e);
		} catch (UserBlockedException e) {
			WebThreadLocalUtils.setAttrbitue(Constants.ERROR, e.getMessage());
			throw new LockedAccountException(e.getMessage(), e);
		} catch (Exception e) {
			WebThreadLocalUtils.setAttrbitue(Constants.ERROR, e.getMessage());
			log.warn("login error", e);
			throw new AuthenticationException(new UcException("user.unknown.error", null));
		}

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user.getId(), password.toCharArray(), getName());
		return info;
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof MobileUserToken;
	}

	public static void main(String[] args) {
		String s = "39b4c9e9c6e8cea0ec4bf524f7924f2e177e2c98a11d4a9103cc4fda4fe3901d";
		System.out.println(s);
		System.out.println(HmacSHA256Utils.digest("4QrcOUm6Wau+VuBX8g+IPg==", "zhangpanpan"));
	}
}
