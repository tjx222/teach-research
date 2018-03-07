/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.managerecord.service;

import java.util.List;
import java.util.Map;

import com.tmser.tr.check.bo.CheckInfo;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;

/**
 * <pre>
 *  管理记录--查阅服务类
 * </pre>
 *
 * @author wangdawei
 * @version $Id: JiaoAnCheckRecordService.java, v 1.0 2015年5月26日 上午10:51:23 tmser Exp $
 */
public interface CheckRecordInfoService{

	/**
	 * 获取已查阅的资源
	 * @param type 资源类型 ResTypeConstants中获取
	 * @param userId
	 * @param termId
	 * @param page 
	 * @return
	 */
	PageList<CheckInfo> getCheckInfoListByType(Integer type,Integer userId,Integer termId, Page page,Boolean onlyHasOptions);

	/**
	 * 获取资源的查阅意见
	 * @param jiaoan
	 * @param userId
	 * @param term
	 * @param page
	 * @return Map,包含资源和其查阅意见集合
	 */
	List<Map<String, Object>> getCheckOptionMapList(Integer type, Integer userId,
			Integer term, Page page);
	/**
	 * 获取资源的查阅意见
	 * @param checkInfoPageList 有查阅意见的checkInfo集合
	 * @return
	 */
	List<Map<String, Object>> getCheckOptionMapList(PageList<CheckInfo> checkInfoPageList);
	/**
	 * 获取资源的查阅数量和意见数量，以课题来统计
	 * @param type
	 * @param userId
	 * @param term
	 * @return Map,包含资源和其查阅意见集合
	 */
	Map<String, Integer> getCheckNumbers(int type, Integer userId,Integer term);
	
}
