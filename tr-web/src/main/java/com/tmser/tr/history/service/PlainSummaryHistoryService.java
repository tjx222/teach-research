/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.service;

import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.plainsummary.bo.PlainSummary;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 3020mt
 * @version $Id: PlainSummaryHistoryService.java, v 1.0 2016年5月19日 下午1:39:43
 *          3020mt Exp $
 */
public interface PlainSummaryHistoryService {
	Result findAll(Integer year, Integer userSpaceId, Integer term,
			Integer category, String title);
	/**
	 * 分页查询计划总结
	 * @param year
	 * @param userSpaceId
	 * @param term
	 * @param category
	 * @param title
	 * @return
	 */
	PageList<PlainSummary> findList(Integer year,PlainSummary ps);
}
