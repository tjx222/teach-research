/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.zzjg.service;

/**
 * <pre>
 *
 * </pre>
 *
 * @author ghw
 * @version $Id: SchoolSynchronousController.java, v 1.0 2016年11月21日 下午3:13:01 ghw Exp $
 */
public interface SynchronizeOrgService {
	/**
	 * 
	 * @param appid
	 * @param appkey
	 * @param logId
	 */
	void synchronousSchool(String appid, String appkey, Long logId);
}
