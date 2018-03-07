/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.service;

import java.util.List;
import java.util.Map;

import com.tmser.tr.check.bo.CheckInfo;
import com.tmser.tr.history.vo.SearchVo;



/**
 * <pre>
 *  管理查询
 * </pre>
 *
 * @author wangyao
 * @version $Id: ManagerCheckHistoryCount.java, v 1.0 2016年5月30日 下午3:42:50 wangyao Exp $
 */
public interface ManagerCheckHistoryCount extends HistoryCount{

	/**
	 * @param types
	 * @param spaceId
	 * @param year
	 * @return
	 */
	Integer getManagerCheckCountByType(Integer[] types, Integer spaceId,
			Integer year);

	/**
	 * @param types
	 * @param spaceId
	 * @param year
	 * @return
	 */
	Integer getManagerCheckOptionCountByType(Integer[] types, Integer spaceId,
			Integer year);

	/**
	 * @param checkInfoPageList
	 * @return
	 */
	Map<String,Object> getCheckOptionMapList(List<CheckInfo> checkInfoList,SearchVo searchVo);


}
