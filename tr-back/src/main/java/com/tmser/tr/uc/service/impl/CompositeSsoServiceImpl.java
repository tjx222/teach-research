/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.uc.bo.App;
import com.tmser.tr.uc.bo.Login;
import com.tmser.tr.uc.service.AppService;
import com.tmser.tr.uc.service.SsoService;

/**
 * <pre>
 *  组合的单点登录服务
 * </pre>
 *
 * @author tmser
 * @version $Id: CompositeSsoServiceImpl.java, v 1.0 2016年1月13日 上午11:12:43 tmser Exp $
 */
@Transactional
public class CompositeSsoServiceImpl implements SsoService{
	
    private Map<String,SsoService> ssoServiceMap = new HashMap<String,SsoService>();
    
    @Resource
	private AppService appService;
	/**
	 * @param params
	 * @return
	 * @see com.tmser.tr.uc.service.SsoService#validate(java.util.Map)
	 */
	@Override
	public boolean validate(Map<String, String> params) {
		App app = getApp(params);
		if(app != null){
			SsoService sv = ssoServiceMap.get(app.getType());
			if(sv != null){
				return sv.validate(params);
			}
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
		App app = getApp(params);
		return app != null ? app.getAppid():null;
	}
	
	protected App getApp(Map<String, String> params) {
		for(String key : ssoServiceMap.keySet()){
			String appId = ssoServiceMap.get(key).getAppId(params);//用户传来的appid
			if(appId == null) continue;
			App model = new App();
			model.setType(key);
			model.setAppid(appId);
			App app = appService.findOne(model);
			if(app != null){
				return app;
			}
		}
		return null;
	}

	/**
	 * @param params
	 * @return
	 * @see com.tmser.tr.uc.service.SsoService#getAppkey(java.util.Map)
	 */
	@Override
	public String getAppkey(Map<String, String> params) {
		App app = getApp(params);
		return app != null ? app.getAppkey():null;
	}

	/**
	 * @param params
	 * @return
	 * @see com.tmser.tr.uc.service.SsoService#logincode(java.util.Map)
	 */
	@Override
	public String logincode(Map<String, String> params) {
		App app = getApp(params);
		if(app != null){
			SsoService sv = ssoServiceMap.get(app.getType());
			if(sv != null){
				return sv.logincode(params);
			}
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
		App app = getApp(params);
		if(app != null){
			SsoService sv = ssoServiceMap.get(app.getType());
			if(sv != null){
				return sv.loginFailedCallback(appLocalId,params);
			}
		}
		return null;
	}
	
	public  Map<String, SsoService> getSsoServiceMap() {
		return ssoServiceMap;
	}

	public  void setSsoServiceMap(Map<String, SsoService> ssoServiceMap) {
		this.ssoServiceMap = ssoServiceMap;
	}

}
