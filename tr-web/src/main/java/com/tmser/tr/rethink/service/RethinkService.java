/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.rethink.service;

import java.util.List;
import java.util.Map;

import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.lessonplan.bo.LessonPlan;

/**
 * <pre>
 * 教学反思相关功能操作Service接口类
 * </pre>
 *
 * @author tmser
 * @version $Id: RethinkService.java, v 1.0 2015年2月10日 下午2:55:05 tmser Exp $
 */
public interface RethinkService extends BaseService<LessonPlan, Integer> {

	/**
	 * 分页查询教学反思
	 * 
	 * @param lp
	 * @return
	 */
	PageList<LessonPlan> findCourseList(LessonPlan lp);

	/**
	 * 保存教学反思
	 * 
	 * @param lp
	 * @param myFile
	 */
	Boolean saveRethink(LessonPlan lp);

	/**
	 * 删除教学反思
	 * 
	 * @param lp
	 *            return
	 */
	Boolean deleteRethink(LessonPlan lp);

	/**
	 * 分享教学反思
	 * 
	 * @param lp
	 */
	Boolean sharingRethink(LessonPlan lp);

	/**
	 * 提交之前的数据查询
	 * 
	 * @param isSubmit
	 * @return
	 */
	Map<String, Object> getSubmitData(Integer isSubmit);

	/**
	 * 获得其他反思数据
	 * 
	 * @param isSubmit
	 * @return
	 */
	List<LessonPlan> getQTrethink(Integer isSubmit);

	/**
	 * 提交或者取消提交反思
	 * 
	 * @param isSubmit
	 * @param planIds
	 * @param qtFanSiIds
	 */
	Boolean submitRethink(String isSubmit, String planIds, String qtFanSiIds);

}
