/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.managerecord.service;

import org.springframework.ui.Model;


/**
 * <pre>
 *	课件反思查阅service
 * </pre>
 *
 * @author tmser
 * @version $Id: KeJianCheckService.java, v 1.0 2015年6月9日 下午5:20:36 tmser Exp $
 */
public interface KeJianCheckService extends CheckRecordInfoService{

	/**
	 * 查看课件详情及查阅信息
	 * @param type
	 * @param lesInfoId
	 * @param m
	 */
	void kejianView(Integer type, Integer lesInfoId, Model m);
	

}