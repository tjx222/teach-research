/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.service;

import java.util.List;
import java.util.Map;

import com.tmser.tr.activity.bo.SchoolTeachCircle;
import com.tmser.tr.activity.bo.TeachSchedule;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.history.vo.SearchVo;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: SchoolActivityHistoryService.java, v 1.0 2016年5月24日 上午11:34:33 tmser Exp $
 */
public interface SchoolActivityHistoryService {

	/**
	 * 获得校际教研历史学年的数据
	 * @param sv
	 * @return
	 */
	Map<String, Object> listSchoolActivity(SearchVo sv);

	/**
	 * 历年资源-查看教研进度表
	 * @param sv
	 * @return
	 */
	PageList<TeachSchedule> findTeachlList(SearchVo sv);

	/**
	 * 历年资源-查看校级教研圈
	 * @param sv
	 * @return
	 */
	List<SchoolTeachCircle> findTeachcircleList(SearchVo sv);

}
