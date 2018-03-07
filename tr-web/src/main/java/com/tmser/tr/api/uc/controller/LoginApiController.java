/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.api.uc.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.api.uc.service.LoginApiService;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.rethink.controller.RethinkController;

/**
 * <pre>
 * 用户登录，找回密码
 * </pre>
 *
 * @author tmser
 * @version $Id: LoginController.java, v 1.0 2015年2月1日 下午10:55:37 tmser Exp $
 */
@Controller
@RequestMapping(value = "/jy/api/uc", produces = "application/vnd.jypt.v1+json")
public class LoginApiController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(RethinkController.class);
	@Resource
	private LoginApiService loginApiService;

	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public Result loginForm(String username, String password) {
		Result result = null;
		try {
			result = loginApiService.login(username, password);
		} catch (Exception e) {
			logger.error("[--离线用户同步用户信息错误--]", e);
			result = new Result(0, "密码错误！", new Date(), null);
		}
		return result;
	}
}
