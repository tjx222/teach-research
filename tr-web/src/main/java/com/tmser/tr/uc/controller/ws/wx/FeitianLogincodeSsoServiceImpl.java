/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.controller.ws.wx;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.uc.bo.Login;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.controller.ws.wx.utils.Platform;
import com.tmser.tr.uc.service.LoginService;
import com.tmser.tr.uc.service.SsoService;
import com.tmser.tr.uc.service.UserService;

/**
 * <pre>
 *  文轩单点登录服务类
 * </pre>
 *
 * @author tmser
 * @version $Id: WenxuanLogincodeSsoServiceImpl.java, v 1.0 2016年1月13日 下午2:32:43 tmser Exp $
 */
@Transactional
public class FeitianLogincodeSsoServiceImpl implements SsoService{
    
    
	@Resource
	private LoginService loginService;
	
	@Autowired
	private UserService userService;
    
	/**
	 * @param params
	 * @return
	 * @see com.tmser.tr.uc.service.SsoService#validate(java.util.Map)
	 */
	@Override
	public boolean validate(Map<String, String> params) {
		try {
			return StringUtils.isNotEmpty(logincode(params));
		} catch (Exception e) {
			//do nothing
		}
		return false;
	}

	/**
	 * @param params
	 * @return
	 * @see com.tmser.tr.uc.service.SsoService#getAppId(java.util.Map)
	 */
	@Override
	public String getAppId(Map<String, String> params) {
		return params.get("appid");
	}

	/**
	 * @param params
	 * @return
	 * @see com.tmser.tr.uc.service.SsoService#getAppkey(java.util.Map)
	 */
	@Override
	public String getAppkey(Map<String, String> params) {
		return params.get("appkey");
	}

	/**
	 * @param params
	 * @return
	 * @see com.tmser.tr.uc.service.SsoService#logincode(java.util.Map)
	 */
	@Override
	public String logincode(Map<String, String> params) {
		String arr = null;
		try {
			arr = Platform.decrypt(params.get("uid"));
			return arr.split("_")[1];
		} catch (Exception e) {
			//do nothing
		}
		return null;
	}

	/**
	 * @param params
	 * @return
	 * @see com.tmser.tr.uc.service.SsoService#loginFailedCallback(java.util.Map)
	 */
	@Override
	public Login loginFailedCallback(Integer appLocalId,Map<String, String> params) {
		 try {
			 String idcard = logincode(params);
			 if(idcard!=null && idcard.length() > 18){
				 idcard = idcard.substring(idcard.length() - 18);
			 }
	         User umodel = new User();
	         umodel.setIdcard(idcard);
	         List<User> ul = userService.find(umodel, 1);
	         if(ul != null && ul.size() > 0){
	        		return loginService.findOne(ul.get(0).getId());
	         }
		 }catch(Exception e){
			 //do nothing
		 }
		return null;
	}
	
}
