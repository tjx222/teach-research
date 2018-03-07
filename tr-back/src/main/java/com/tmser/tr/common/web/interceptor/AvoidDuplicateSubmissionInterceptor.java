/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.common.web.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tmser.tr.common.annotation.UseToken;
import com.tmser.tr.utils.Identities;

/**
 * <pre>
 *  防止重复提交过滤器
 * </pre>
 *
 * @author tmser
 * @version $Id: AvoidDuplicateSubmissionInterceptor.java, v 1.0 2015年4月6日 下午9:25:50 tmser Exp $
 */
public class AvoidDuplicateSubmissionInterceptor extends HandlerInterceptorAdapter{
	private static final Logger LOG = LoggerFactory.getLogger(AvoidDuplicateSubmissionInterceptor.class);
	
	public static final String TOKEN_KEY = "_TOKEN_";
	 
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
    	if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
 
            UseToken annotation = method.getAnnotation(UseToken.class);
            if (annotation != null) {
                if (isRepeatSubmit(request,annotation.value())) {
                        LOG.warn("please don't repeat submit,url:[{}]", request.getServletPath());
                        return false;
                 }
           }
            return true;
    	} else {
    		return super.preHandle(request, response, handler);
    	}
    }
 
    private boolean isRepeatSubmit(HttpServletRequest request,boolean check) {
    	 String clinetToken = request.getParameter(TOKEN_KEY);
         if (clinetToken == null && check == false) {
        	 request.getSession(false).setAttribute(TOKEN_KEY,Identities.uuid());
             return false;
         }
         
        String serverToken = (String) request.getSession(false).getAttribute(TOKEN_KEY);
        if (serverToken == null) {
            return true;
        }
       
        if (!serverToken.equals(clinetToken)) {
            return true;
        }
        request.getSession(false).removeAttribute(TOKEN_KEY);
        return false;
    }
 
}
