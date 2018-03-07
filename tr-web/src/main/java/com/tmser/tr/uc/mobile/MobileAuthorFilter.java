/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.tmser.tr.uc.mobile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.Encodes;
import com.tmser.tr.utils.StringUtils;

/**
 * 验证用户过滤器 1、用户是否删除 2、用户是否锁定
 * <p>
 * User: tmser
 * <p>
 * Date: 14-3-19 下午3:09
 * <p>
 * Version: 1.0
 */
public class MobileAuthorFilter extends AccessControlFilter {

	private final static Logger logger = LoggerFactory.getLogger(MobileAuthorFilter.class);

	private static final Pattern AUTHOR_HEADER_PATTERN = Pattern.compile("\\s*ThirdParty\\s*([a-zA-Z0-9=]+)\\s*");

	@Resource
	private UserSpaceService userSpaceService;

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		boolean rs = true;
		MobileUserToken autoken = authorCode(request);
		if (autoken != null) {
			if (!isLoginRequest(request, response)) {
				Subject subject = getSubject(request, response);
				if (subject.getPrincipal() != null) {// 已经登录，切换身份
					HttpSession session = ((HttpServletRequest)request).getSession(false);
					if (session != null) {
						UserSpace cspace = (UserSpace) session.getAttribute(SessionKey.CURRENT_SPACE);
						if (cspace != null) {
							if(!cspace.getId().equals(autoken.getSpaceid())){
								UserSpace sp = userSpaceService.findOne(autoken.getSpaceid());
								if (sp != null) {
									if (autoken.getPhaseid() != null) {
										sp.setPhaseId(autoken.getPhaseid());
									}
									session.setAttribute(SessionKey.CURRENT_SPACE, sp);
								}
							}else if(autoken.getPhaseid()!=null && !cspace.getPhaseId().equals(autoken.getPhaseid())){
								cspace.setPhaseId(autoken.getPhaseid());
								session.setAttribute(SessionKey.CURRENT_SPACE, cspace);
							}
						}
					}
				} else {
					rs = false;
				}
			}

			if (!rs) {
				try {
					Subject subject = getSubject(request, response);
					subject.login(autoken);
					UserSpace sp = userSpaceService.findOne(autoken.getSpaceid());
					if (sp != null && sp.getUserId().equals(subject.getPrincipal())) {
						if (autoken.getPhaseid() != null) {
							sp.setPhaseId(autoken.getPhaseid());
						}
						WebThreadLocalUtils.getRequest().getSession().setAttribute(SessionKey.CURRENT_SPACE, sp);
					}
				} catch (Exception e) {
					logger.warn("mobile login failed[username={},token={}]", autoken.getUsername(), autoken.getToken());
					logger.warn("", e);
				}
			}
		}
		return true;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		saveRequestAndRedirectToLogin(request, response);
		return false;
	}

	protected MobileUserToken authorCode(ServletRequest request) {
		// Authorization: ThirdParty
		// base64({"username":"","token":"1234ffff",spaceid:11,phaseid:1})
		String authVale = null;
		HttpServletRequest req = (HttpServletRequest) request;
		String authHeader = req.getHeader("Authorization");
		if (StringUtils.isBlank(authHeader)) {// 支持cookie 传参
			Cookie[] cookies = req.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if ("Authorization".equals(cookie.getName())) {
						authHeader = cookie.getValue();
						break;
					}
				}
			}
		}

		if (StringUtils.isNotBlank(authHeader)) {
			Matcher m = AUTHOR_HEADER_PATTERN.matcher(authHeader);
			while (m.find()) {
				authVale = m.group(1);
			}
		}

		MobileUserToken token = null;
		try {
			if (authVale != null)
				token = JSON.parseObject(Encodes.decodeBase64(authVale), MobileUserToken.class);
		} catch (Exception e) {
			logger.error("parse token failed ", e);
		}
		return token;
	}

}
