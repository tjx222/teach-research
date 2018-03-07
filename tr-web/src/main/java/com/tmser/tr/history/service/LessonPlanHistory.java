/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.service;

import java.util.Map;

import com.tmser.tr.common.page.PageList;
import com.tmser.tr.history.vo.SearchVo;
import com.tmser.tr.lessonplan.bo.LessonPlan;

/**
 * <pre>
 *	历史教学资源查询service
 * </pre>
 *
 * @author wangdawei
 * @version $Id: LessonPlanHistory.java, v 1.0 2016年5月25日 下午4:25:33 wangdawei Exp $
 */
public interface LessonPlanHistory{

	
	Integer getLessonPlanHistoryCount(Integer userId, String code,
			Integer currentYear);

	PageList<LessonPlan> getHistoryJiaoanList(SearchVo searchVo);

	Map<String, Object> getLessonPlanDetailInfo(Integer planId);

	PageList<LessonPlan> getHistoryKejianList(SearchVo searchVo);

	PageList<LessonPlan> getHistoryFansiList(SearchVo searchVo);

}
