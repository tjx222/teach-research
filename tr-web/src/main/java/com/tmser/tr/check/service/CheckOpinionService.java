/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.check.service;

import com.tmser.tr.check.bo.CheckInfo;
import com.tmser.tr.check.bo.CheckOpinion;
import com.tmser.tr.common.service.BaseService;

/**
 * 查阅意见 服务类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: CheckOpinion.java, v 1.0 2015-03-14 Generate Tools Exp $
 */

public interface CheckOpinionService extends BaseService<CheckOpinion, Integer>{

	/**
	 * 保存查阅意见
	 * @param co
	 * @return
	 */
	boolean saveCheckbo(CheckInfo co);
	
	/**
	 * 保存查阅回复
	 * @param model
	 * @return
	 */
	CheckOpinion saveCheckReply(CheckOpinion model);
	

}
