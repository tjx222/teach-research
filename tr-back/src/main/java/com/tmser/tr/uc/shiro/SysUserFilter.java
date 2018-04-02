/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.tmser.tr.uc.shiro;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tmser.tr.uc.bo.LoginLog;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.exception.UserSpaceNotExistsException;
import com.tmser.tr.uc.service.LoginLogService;
import com.tmser.tr.uc.service.SchoolYearService;
import com.tmser.tr.uc.service.UserService;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * 验证用户过滤器
 * 1、用户是否删除
 * 2、用户是否锁定
 * <p>
 * User: tmser
 * <p>
 * Date: 14-3-19 下午3:09
 * <p>
 * Version: 1.0
 */
public class SysUserFilter extends UserFilter {

  @Autowired
  private UserService userService;

  @Resource
  private UserSpaceService userSpaceService;

  @Resource
  private SchoolYearService schoolYearService;

  @Autowired(required = false)
  private LoginLogService loginLogService;

  @Override
  protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
    Subject subject = getSubject(request, response);
    if (subject == null) {
      return true;
    }

    Integer uid = (Integer) subject.getPrincipal();
    try {
      setSession(uid, request);
    } catch (UserSpaceNotExistsException e) {
      WebUtils.issueRedirect(request, response, "/jy/uc/login?userspcaeError=1");
      return false;
    }

    return super.preHandle(request, response);
  }

  @Override
  protected void saveRequest(ServletRequest request) {
    // do nothing
    return;
  }

  @Override
  protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
    if (notAjaxRequest((HttpServletRequest) request)) {
      return super.onAccessDenied(request, response);
    }
    // 这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(
        "{\"statusCode\":\"301\", \"message\":\"\u4f1a\u8bdd\u8d85\u65f6\uff0c\u8bf7\u91cd\u65b0\u767b\u5f55\u3002\"}");
    return false;
  }

  protected boolean notAjaxRequest(HttpServletRequest request) {
    String requestedWith = request.getHeader("X-Requested-With");
    return requestedWith == null || !requestedWith.toLowerCase().contains("xmlhttprequest");
  }

  protected boolean notStaticRequest(HttpServletRequest request) {
    return !pathsMatch("/static/**", request);
  }

  protected void setSession(Integer userid, ServletRequest request) {
    HttpSession session = ((HttpServletRequest) request).getSession();
    if (userid != null && session.getAttribute(SessionKey.CURRENT_USER) == null) {
      User u = userService.findOne(userid);
      session.setAttribute(SessionKey.CURRENT_USER, u);
      UserSpace cus = null;
      UserSpace model = new UserSpace();
      model.addAlias("s");
      model.addCustomCulomn("s.*");
      model.setUserId(userid);
      List<UserSpace> lus = userSpaceService.find(model, 1);

      if (lus == null || lus.size() == 0) {
        throw new UserSpaceNotExistsException("用户空间不存在");
      }

      if (cus == null) {
        cus = lus.get(0);
      }

      if (loginLogService != null && cus != null)
        loginLogService.addHistroy(cus, LoginLog.T_LOGIN);

      session.setAttribute(SessionKey.CURRENT_SCHOOLYEAR, schoolYearService.getCurrentSchoolYear());
      session.setAttribute(SessionKey.CURRENT_TERM, schoolYearService.getCurrentTerm());
      session.setAttribute(SessionKey.CURRENT_SPACE, cus);
    }
  }
}
