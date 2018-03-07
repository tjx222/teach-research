/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.service;

import java.util.Map;

import com.tmser.tr.uc.bo.Login;

/**
 * <pre>
 *   单点登录服务接口。
 *   教研平台作为应用接入第三方平台时，用户对接步骤：
 *   1. 后台注册app，提供给对方；或对方提供key 和  secret
 *   2. 实现本接口中方法
 *   3. 实现类注入到spring-shiro 的CompositeSsoServiceImpl#ssoServiceMap 属性中
 *   如：
 *      <entry key="a342bed3579-1e73-1323-96ff-6827e54fa" >
 *	                <bean class="com.tmser.tr.uc.controller.ws.ex.ErxiaoSsoService"></bean>
 *      </entry> 
 * </pre>
 *
 * @author tmser
 * @version $Id: SsoService.java, v 1.0 2016年1月13日 上午9:08:04 tmser Exp $
 */
public interface SsoService {
	/**
	 * 接入应用数据有效性验证
	 * 此处无需验证 appid 和 appkey
	 * @param params 接入应用传递的参数map
	 * @return 成功 true, 失败 false
	 */
	boolean validate(Map<String,String> params);
	
	/**
	 * 解析appid
	 * 一般是在参数中解析appid,以应对部分应用参数加密
	 * @param params 接入应用传递的参数map
	 * @return appid 
	 */
	String getAppId(Map<String,String> params);
	
	/**
	 * 解析appid
	 * 一般是在参数中解析appid,以应对部分应用参数加密
	 * @param params 接入应用传递的参数map
	 * @return appid 
	 */
	String getAppkey(Map<String,String> params);
	
	/**
	 * 解析登录代码
	 * @param params 接入应用传递的参数map
	 * @return
	 */
	String logincode(Map<String,String> params);
	
	/**
	 * 本地不存在该用户时的回调
	 * 一般根据对方提供的用户数据，创建新用户
	 * @param appLocalId  应用在本地注册的id
	 * @param params 接入应用传递的参数map
	 * @return 成功换回 
	 */
	Login loginFailedCallback(Integer appLocalId,Map<String,String> params);
}
