/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;

import com.tmser.tr.uc.Constants;
import com.tmser.tr.uc.bo.App;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.service.AppService;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.SpringContextHolder;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: JyLogoutFilter.java, v 1.0 2016年1月15日 上午9:41:45 tmser Exp $
 */
public class JyLogoutFilter extends LogoutFilter{

    /**
     * 
     * 退出
     * 首先，判定是否在教研登录页登录，是直接用退出到默认地址；
     * 否则，获取用户所属app,跳转到app 设置的登录页
     * 
     * @param request the incoming Servlet request
     * @param response the outgoing ServletResponse
     * @param subject the not-yet-logged-out currently executing Subject
     * @return the redirect URL to send the user after logout.
     */
    @Override
	protected String getRedirectUrl(ServletRequest request, ServletResponse response, Subject subject) {
    	String redirectUrl = getRedirectUrl();
    	Object from = null;
		try {
			from = subject.getSession(false).getAttribute(Constants.LOGIN_FROM_WHERE);
		} catch (Exception e) {
			//do noting
			return redirectUrl;
		}
    	if(!Constants.LOGIN_FROM_LOCAL.equals(from)){
    		User user = (User) subject.getSession(false).getAttribute(SessionKey.CURRENT_USER);
    		if(user != null && user.getAppId() != null 
    				&& !Integer.valueOf(0).equals(user.getAppId())){
    			AppService appservice = SpringContextHolder.getBean(AppService.class);
    			App app = appservice.findOne(user.getAppId());
    			if(app != null && StringUtils.isNotEmpty(app.getLoginUrl())){
    				redirectUrl = app.getLoginUrl();
    			}
    		}
    	}
    	return redirectUrl;
    }
}
