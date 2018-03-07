/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.dao;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.uc.bo.VerificationCode;

 /**
 * 保存邮箱验证码 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: VerificationCode.java, v 1.0 2015-08-05 Generate Tools Exp $
 */
public interface VerificationCodeDao extends BaseDAO<VerificationCode, Integer>{
	/**
	 * 删除过期的验证码
	 * @param livetime 存活时间，单位是秒
	 */
	void deleteValidCode(int livetime);
}