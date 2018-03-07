/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.shiro;


import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.Cache.ValueWrapper;

import com.tmser.tr.uc.bo.Login;
import com.tmser.tr.uc.exception.UcException;
import com.tmser.tr.uc.exception.UserBlockedException;
import com.tmser.tr.uc.exception.UserNotExistsException;
import com.tmser.tr.uc.exception.UserPasswordNotMatchException;
import com.tmser.tr.uc.exception.UserPasswordRetryLimitExceedException;
import com.tmser.tr.uc.service.LoginService;

/**
 * <pre>
 *	自定义认证及权限收集器
 * </pre>
 *
 * @author tmser
 * @version $Id: UserRealm.java, v 1.0 2015年1月23日 下午6:23:54 tmser Exp $
 */
public class UserRealm extends AbstractUserRealm {
    
    @Resource
    private LoginService loginService;
    
	@Resource(name="cacheManger")
    private CacheManager cacheManager;

    private Cache authorizationInfoCache;
    
    
	/**
	 * @param principals
	 * @return
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		if(authorizationInfoCache == null){
			authorizationInfoCache = cacheManager.getCache("authorizationInfoCache");
		}
		
		Integer uid = (Integer) principals.getPrimaryPrincipal();
		
        ValueWrapper cacheElement = authorizationInfoCache.get(uid);
        SimpleAuthorizationInfo authorizationInfo;
        
        if (cacheElement != null) {
        	authorizationInfo = (SimpleAuthorizationInfo) cacheElement.get();
            if(authorizationInfo != null){
            	return authorizationInfo;
            }
        }
		
        authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(loginService.findStringRoles(uid));
        authorizationInfo.setStringPermissions(loginService.findStringPermissions(uid));
        
        authorizationInfoCache.put(uid, authorizationInfo);
        

        return authorizationInfo;
	}

	/**
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
							throws AuthenticationException {
		 	UsernamePasswordToken upToken = (UsernamePasswordToken) token;
	        String username = upToken.getUsername().trim();
	        String password = "";
	        if (upToken.getPassword() != null) {
	            password = new String(upToken.getPassword());
	        }

	        Login user = null;
	        try {
	            user = loginService.login(username, password);
	        } catch (UserNotExistsException e) {
	            throw new UnknownAccountException(e.getMessage(), e);
	        } catch (UserPasswordNotMatchException e) {
	            throw new AuthenticationException(e.getMessage(), e);
	        } catch (UserPasswordRetryLimitExceedException e) {
	            throw new ExcessiveAttemptsException(e.getMessage(), e);
	        } catch (UserBlockedException e) {
	            throw new LockedAccountException(e.getMessage(), e);
	        } catch (Exception e) {
	            log.error("login error", e);
	            throw new AuthenticationException(new UcException("user.unknown.error", null));
	        }

	        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user.getId(), password.toCharArray(), getName());
	        return info;
	}
	
}
