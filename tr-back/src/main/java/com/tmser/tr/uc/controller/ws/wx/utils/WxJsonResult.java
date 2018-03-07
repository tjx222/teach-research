/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.controller.ws.wx.utils;

import com.tmser.tr.uc.controller.ws.wx.WxUserVo;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: WxJsonResult.java, v 1.0 2016年1月6日 下午4:45:43 tmser Exp $
 */
public class WxJsonResult {

	private Integer type;
	
	private WxUserVo user;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public WxUserVo getUser() {
		return user;
	}

	public void setUser(WxUserVo user) {
		this.user = user;
	}
	
	
}
