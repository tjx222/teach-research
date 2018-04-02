/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.shiro;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tmser.tr.common.bo.QueryObject.JOINTYPE;
import com.tmser.tr.uc.bo.LoginLog;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.LoginLogService;
import com.tmser.tr.uc.service.SchoolYearService;
import com.tmser.tr.uc.service.UserService;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tmser
 * @version $Id: CustomFormAutenticationFilter.java, v 1.0 2015年2月2日 下午2:38:05
 *          tmser Exp $
 */
public class CustomFormAutenticationFilter extends FormAuthenticationFilter {
  @Autowired
  private UserService userService;

  @Resource
  private UserSpaceService userSpaceService;

  @Resource
  private SchoolYearService schoolYearService;

  @Autowired(required = false)
  private LoginLogService loginLogService;

  @Override
  protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
    request.setAttribute(getFailureKeyAttribute(), ae);
  }

  @Override
  protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
      ServletResponse response) {
    if (notAjaxRequest((HttpServletRequest) request)) {
      return super.onLoginFailure(token, e, request, response);
    } else {
      try {
        response.setCharacterEncoding("UTF-8");
        response.getWriter()
            .write("{\"statusCode\":\"300\", \"message\":\"\u767b\u5f55\u5931\u8d25\uff0c\u8bf7\u91cd\u8bd5\"}");
      } catch (IOException e1) {
        // do nothing
      }
    }
    return false;
  }

  @Override
  protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
      ServletResponse response) throws Exception {
    if (notAjaxRequest((HttpServletRequest) request)) {
      super.onLoginSuccess(token, subject, request, response);
    } else {
      try {
        if (subject == null) {
          return true;
        }
        Integer userid = (Integer) subject.getPrincipal();
        HttpSession session = ((HttpServletRequest) request).getSession();
        if (userid != null && session.getAttribute(SessionKey.CURRENT_USER) == null) {
          User u = userService.findOne(userid);
          session.setAttribute(SessionKey.CURRENT_USER, u);
          UserSpace cus = null;
          UserSpace model = new UserSpace();
          model.addAlias("s");
          model.addCustomCulomn("s.*");
          model.addJoin(JOINTYPE.INNER, "RoleType rt").on("s.sysRoleId = rt.code and rt.usePosition = 3");
          model.setUserId(userid);
          List<UserSpace> lus = userSpaceService.find(model, 1);

          if (cus == null && lus != null && lus.size() > 0) {
            if (lus.size() == 1) {
              cus = lus.get(0);
            }
          }

          if (loginLogService != null)
            loginLogService.addHistroy(cus, LoginLog.T_LOGIN);

          session.setAttribute(SessionKey.CURRENT_SCHOOLYEAR, schoolYearService.getCurrentSchoolYear());
          session.setAttribute(SessionKey.CURRENT_TERM, schoolYearService.getCurrentTerm());
          session.setAttribute(SessionKey.CURRENT_SPACE, cus);
        }
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(
            "{\"statusCode\":\"200\", \"callbackType\":\"closeCurrent\",\"message\":\"\u767b\u5f55\u6210\u529f\"}");
      } catch (IOException e1) {
        // do nothing
      }
    }
    // we handled the success redirect directly, prevent the chain from
    // continuing:
    return false;
  }

  protected boolean notAjaxRequest(HttpServletRequest request) {
    String requestedWith = request.getHeader("X-Requested-With");
    return requestedWith == null || !requestedWith.toLowerCase().contains("xmlhttprequest");
  }

  /**
   * Convenience method merely delegates to
   * {@link WebUtils#saveRequest(javax.servlet.ServletRequest)
   * WebUtils.saveRequest(request)} to save the request
   * state for reuse later. This is mostly used to retain user request state
   * when a redirect is issued to
   * return the user to their originally requested url/resource.
   * <p/>
   * If you need to save and then immediately redirect the user to login,
   * consider using
   * {@link #saveRequestAndRedirectToLogin(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
   * saveRequestAndRedirectToLogin(request,response)} directly.
   *
   * @param request
   *          the incoming ServletRequest to save for re-use later (for example,
   *          after a redirect).
   */
  @Override
  protected void saveRequest(ServletRequest request) {
    if (request instanceof HttpServletRequest) {
      HttpServletRequest rq = (HttpServletRequest) request;
      String requestedWith = rq.getHeader("X-Requested-With");
      if (requestedWith == null || !requestedWith.toLowerCase().contains("xmlhttprequest")) {
        WebUtils.saveRequest(request);
      }
    } else {
      super.saveRequest(request);
    }

  }

}
