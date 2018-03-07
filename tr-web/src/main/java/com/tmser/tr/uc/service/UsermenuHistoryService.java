/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.service;

import java.util.List;
import java.util.Set;

import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.uc.bo.UsermenuHistory;

/**
 * 用户功能历史记录 服务类
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: UsermenuHistory.java, v 1.0 2016-05-19 tmser Exp $
 */

public interface UsermenuHistoryService extends BaseService<UsermenuHistory, Integer>{

	/**
	 * 获取用户历史学年功能代码集合
	 * @param userId
	 * @param schoolYear
	 * @return
	 */
	Set<String> listUserHistory(Integer userId,Integer schoolYear);
	
	
	/**
	 * 生成历史学年功能集合
	 * @param userId
	 * @param schoolYear
	 * @return
	 */
	UsermenuHistory createUserHistory(Integer userId,Integer schoolYear);
	
	/**
	 * 批量插入用户历史学年记录
	 * @param uhs
	 */
	void batchInsert(List<UsermenuHistory> uhs);
}
