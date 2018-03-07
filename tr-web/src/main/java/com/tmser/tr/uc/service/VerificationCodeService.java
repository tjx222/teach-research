/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.service;

import org.springframework.ui.Model;

import com.tmser.tr.uc.bo.VerificationCode;
import com.tmser.tr.common.service.BaseService;

/**
 * 保存邮箱验证码 服务类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: VerificationCode.java, v 1.0 2015-08-05 Generate Tools Exp $
 */

public interface VerificationCodeService extends BaseService<VerificationCode, Integer>{
   
	/**
	 *  验证并保存邮箱地址 
      * @param uid 用户id
      * @param mail 邮箱地址
      * @return true 验证成功，false 验证失败
	*/
	public boolean valiadteAndsaveMail(String mail,String code);
	
	/*发送验证码到邮箱
     * code 用户验证码
     * tomail 邮箱地址
	*/
	public boolean sendVarification(Model m,String mail) throws Exception;
}
