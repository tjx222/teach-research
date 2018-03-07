/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.api.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 3020mt
 * @version $Id: ExceptionMessage.java, v 1.0 2016年5月6日 下午5:05:49 3020mt Exp $
 */
public class ExceptionMessage {
	/**
	 * 获取异常日志
	 * 
	 * @param e
	 * @return
	 */
	public static String getExceptionMassage(Exception e) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		e.printStackTrace(new PrintStream(baos));
		String exception = baos.toString();
		return exception;
	}
}
