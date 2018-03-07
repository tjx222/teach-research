/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.controller.ws.ex;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.tmser.tr.uc.bo.Login;
import com.tmser.tr.uc.service.SsoService;
import com.tmser.tr.utils.DateUtils;
import com.tmser.tr.utils.Encodes;

/**
 * <pre>
 *  实验二小用户对接服务
 * </pre>
 *
 * @author tmser
 * @version $Id: ErxiaoSsoService.java, v 1.0 2016年1月13日 下午5:24:40 tmser Exp $
 */
public class ErxiaoSsoService implements SsoService{

	/**
	 * @param params
	 * @return
	 * @see com.tmser.tr.uc.service.SsoService#validate(java.util.Map)
	 */
	@Override
	public boolean validate(Map<String, String> params) {
		String pkey = params.get("pkey");
		String puid = params.get("puid");
		String nowDate = DateUtils.getDate("yyyy-MM-dd");
		String scre = "1231q2w3eQWE\\qw";
		
		return StringUtils.isNotEmpty(pkey) 
				&& pkey.equalsIgnoreCase(Encodes.md5(puid+logincode(params)+nowDate+scre));
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
		return params.get("pusername");
	}

	/**
	 * @param appLocalId
	 * @param params
	 * @return
	 * @see com.tmser.tr.uc.service.SsoService#loginFailedCallback(java.lang.Integer, java.util.Map)
	 */
	@Override
	public Login loginFailedCallback(Integer appLocalId,
			Map<String, String> params) {
		return null;
	}

}
