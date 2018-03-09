/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.controller.ws;

import java.util.Map;

import org.apache.shiro.authc.UsernamePasswordToken;

import com.tmser.tr.uc.bo.App;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tjx
 * @version $Id: UsernamePasswordCenterToken.java, v 1.0 2016年1月06日 下午5:22:34 tmser Exp $
 */
@SuppressWarnings("serial")
public class LogincodeToken extends UsernamePasswordToken {

	private App app;
	
	private Map<String,String> params;

	public LogincodeToken(){}
	
	public LogincodeToken(App app,String username,String password,Map<String,String> params){
		super(username,password);
		this.setApp(app);
		this.params = params;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
}
