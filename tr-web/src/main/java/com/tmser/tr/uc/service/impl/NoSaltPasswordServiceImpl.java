/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.service.impl;

import com.tmser.tr.utils.Encodes;

/**
 * <pre>
 * 无盐值加密方式。
 * </pre>
 *
 * @author tmser
 * @version $Id: Snippet.java, v 1.0 2015年12月2日 上午10:17:00 tmser Exp $
 */
public class NoSaltPasswordServiceImpl extends PasswordServiceImpl {
	
	/**
	 * 生成密码,无盐值
	 * @param username
	 * @param password 只对密码部分加密
	 * @param salt
	 * @return
	 */
    @Override
	public String encryptPassword(String username, String password, String salt) {
        return Encodes.encodeBase64(Encodes.md5Byte(password));
    }
    
}

