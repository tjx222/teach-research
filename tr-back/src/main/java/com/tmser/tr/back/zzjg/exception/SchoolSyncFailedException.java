/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.zzjg.exception;

import com.tmser.tr.utils.exception.BaseException;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 3020mt
 * @version $Id: SchoolDataNotCurrectException.java, v 1.0 2016年11月22日 上午9:29:33 3020mt Exp $
 */
public class SchoolSyncFailedException extends BaseException {
	
	private Long time;

	/**
	 * <pre>
	 *
	 * </pre>
	 */
	private static final long serialVersionUID = -2816699905536492905L;

	/**
	 * @param defaultMessage
	 */
	public SchoolSyncFailedException(Long time) {
		super("sync.school.info.not.currect");
		this.setTime(time);
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

}
