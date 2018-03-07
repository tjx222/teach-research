/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.api.uc.service;

import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.uc.bo.Login;
import com.tmser.tr.uc.exception.UcException;

/**
 * <pre>
 *	用户登录服务。
 * </pre>
 *
 * @author tmser
 * @version $Id: LoginService.java, v 1.0 2015年1月31日 下午1:52:23 tmser Exp $
 */
public interface LoginApiService extends BaseService<Login, Integer> {

	/**
	 * 登录
	 * 
	 * @param principal
	 *            登录身份标识，可以是用户名、邮箱、手机
	 * @param password
	 *            密码
	 * @return 成功返回登录信息
	 */
	Result login(String principal, String password) throws UcException;

	/**
	 * 按用户名查找用户
	 * 
	 * @param username
	 * @return
	 */
	Login findByUsername(String username);

	/**
	 * 按邮箱查找用户
	 * 
	 * @param email
	 * @return
	 */
	Login findByEmail(String email);

	/**
	 * 按手机号查找用户
	 * 
	 * @param cellPhone
	 * @return
	 */
	Login findByCellPhone(String cellPhone);

}
