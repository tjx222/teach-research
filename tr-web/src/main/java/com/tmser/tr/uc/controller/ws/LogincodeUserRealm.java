/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.controller.ws;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.beans.factory.annotation.Autowired;

import com.tmser.tr.common.bo.QueryObject.JOINTYPE;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.uc.Constants;
import com.tmser.tr.uc.bo.Login;
import com.tmser.tr.uc.exception.UcException;
import com.tmser.tr.uc.exception.UserBlockedException;
import com.tmser.tr.uc.exception.UserNotExistsException;
import com.tmser.tr.uc.exception.UserPasswordNotMatchException;
import com.tmser.tr.uc.exception.UserPasswordRetryLimitExceedException;
import com.tmser.tr.uc.service.LoginService;
import com.tmser.tr.uc.service.SsoService;
import com.tmser.tr.uc.shiro.AbstractUserRealm;

/**
 * <pre>
 *	自定义认证及权限收集器
 * </pre>
 *
 * @author tmser
 * @version $Id: UserRealm.java, v 1.0 2015年1月23日 下午6:23:54 tmser Exp $
 */
public class LogincodeUserRealm extends AbstractUserRealm {
    private static final Logger log = LoggerFactory.getLogger(LogincodeUserRealm.class);
    
    public static final String LOGINCODE_LOGIN_KEY = "_LOGIN_CODE_LOGIN_KEY";
    
    @Resource
    private LoginService loginService;
    
    @Autowired
	private SsoService ssoService;
    

	/**
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
							throws AuthenticationException {
		
			LogincodeToken upToken = (LogincodeToken) token;
	        String logincode =  new String(upToken.getPassword());
	        Login login = null;
	        try {
	        	Login umodel = new Login();
	        	umodel.setLogincode(logincode);
	        	umodel.addAlias("l");
	        	umodel.addJoin(JOINTYPE.INNER, "User u").on("l.id = u.id");
	        	Map<String,Object> paramMap = new HashMap<String,Object>();
	        	paramMap.put("appid", upToken.getApp().getId());
	        	umodel.addCustomCondition("and u.appId = :appid", paramMap);
	        	List<Login> ul = loginService.find(umodel, 1);
	        	if(ul != null && ul.size() > 0){
	        		login = ul.get(0);
	        	}
	        	
	        	if(login == null){
	        		login = ssoService.loginFailedCallback(upToken.getApp().getId(),upToken.getParams());
		        }
	        	
	        	if(login == null){
	        		throw new UserNotExistsException(); 
	        	}
	        	
	        	if(login.getEnable() != 1 || login.getIsAdmin() || login.getDeleted() ){
        			throw new UserBlockedException("用户被禁用！");
        		}
	        } catch (UserNotExistsException e) {
	        	WebThreadLocalUtils.setAttrbitue(Constants.ERROR,e.getMessage());
	            throw new UnknownAccountException(e.getMessage(), e);
	        } catch (UserPasswordNotMatchException e) {
	        	WebThreadLocalUtils.setAttrbitue(Constants.ERROR,e.getMessage());
	            throw new AuthenticationException(e.getMessage(), e);
	        } catch (UserPasswordRetryLimitExceedException e) {
	        	WebThreadLocalUtils.setAttrbitue(Constants.ERROR,e.getMessage());
	            throw new ExcessiveAttemptsException(e.getMessage(), e);
	        } catch (UserBlockedException e) {
	        	WebThreadLocalUtils.setAttrbitue(Constants.ERROR,e.getMessage());
	            throw new LockedAccountException(e.getMessage(), e);
	        } catch (Exception e) {
	            WebThreadLocalUtils.setAttrbitue(Constants.ERROR,e.getMessage());
	            log.error("login error", e);
	            throw new AuthenticationException(new UcException("user.unknown.error", null));
	        }

	        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(login.getId(), logincode.toCharArray(), getName());
	        return info;
	}
	
	@Override
	public boolean supports(AuthenticationToken token){
		return token != null && token instanceof LogincodeToken;
	}
	
}
