/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.jxtx.exception;

import com.tmser.tr.utils.exception.BaseException;

/**
 * <pre>
 *
 * </pre>
 *
 * @author ghw
 * @version $Id: SchoolTypeNameAlreadyExistException.java, v 1.0 2016年11月25日 下午4:53:57 ghw Exp $
 */
public class SchoolTypeNameAlreadyExistException extends BaseException{
	
	/**
	 * <pre>
	 *
	 * </pre>
	 */
	private static final long serialVersionUID = 1L;

	public SchoolTypeNameAlreadyExistException(){
		 super("schoolType's name must be unique");
	}

}
