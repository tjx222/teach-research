/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.dao.impl;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.tmser.tr.common.dao.AbstractDAO;
import com.tmser.tr.uc.bo.VerificationCode;
import com.tmser.tr.uc.dao.VerificationCodeDao;

/**
 * 保存邮箱验证码 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: VerificationCode.java, v 1.0 2015-08-05 Generate Tools Exp $
 */
@Repository
public class VerificationCodeDaoImpl extends AbstractDAO<VerificationCode,Integer> implements VerificationCodeDao {

	/**
	 * @param livetime
	 * @see com.tmser.tr.uc.dao.VerificationCodeDao#deleteValidCode(int)
	 */
	@Override
	public void deleteValidCode(int livetime) {
		Date now = new Date();
		long time = now.getTime() - livetime * 1000; 
		super.update("delete from VerificationCode where creatTime < ?", new Object[]{time});
	}

}
