/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.check.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import com.tmser.tr.check.bo.CheckInfo;
import com.tmser.tr.common.service.BaseService;

/**
 * 查阅基础信息 服务类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: CheckInfo.java, v 1.0 2015-03-14 Generate Tools Exp $
 */

public interface CheckInfoService extends BaseService<CheckInfo, Integer>{
	/**
	 * 回调列表
	 */
	List<CheckedCallback> callbackList = new CopyOnWriteArrayList<CheckedCallback>();
	

	/**
	 * 删除教案的相关查阅信息
	 * @param planId
	 * @return Integer 删除的查阅意见条数
	 */
	Integer deleteCheckOptionOfLessonPlan(Integer planId);
	
	
	/**
	 * 更新相关资源查阅更新状态
	 * @param res
	 * @return boolean 是否更新成功
	 */
	boolean updateCheckInfoUpdateState(Integer resid,Integer restype);


	/**
	 * 查询某一学年某一类型的所有资源
	 * @param schoolYear
	 * @param resType
	 * @return
	 */
	Map<Integer, Object> getCheckData(Integer schoolYear, Integer resType, Integer userId);
}
