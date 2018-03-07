/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.service;

import com.tmser.tr.activity.bo.Activity;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.history.vo.SearchVo;

/**
 * <pre>
 *  集体备课历史查询
 * </pre>
 *
 * @author wangdawei
 * @version $Id: ActivityHistory.java, v 1.0 2016年5月26日 下午3:42:50 wangdawei Exp $
 */
public interface ActivityHistory{

	Integer getActivityHistoryCount_issue(Integer userId,Integer currentYear);

	Integer getActivityHistoryCount_join(Integer userId,Integer currentYear);

	PageList<Activity> getHistoryActivityListBySpaceId_faqi(SearchVo searchVo);

	PageList<Activity> getHistoryActivityListBySpaceId_canyu(SearchVo searchVo);

	Integer getHistoryActivityCountBySpaceId_faqi(SearchVo searchVo);

	Integer getHistoryActivityCountBySpaceId_canyu(SearchVo searchVo);

}
