/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.courseware.service;

import java.util.List;
import java.util.Map;

import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.lessonplan.bo.LessonPlan;

/**
 * 课件相关功能操作Service接口类
 * 
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: CoursewareService.java, v 1.0 2015年2月10日 下午2:52:00 tmser Exp $
 */
public interface CoursewareService {

	/**
	 * 通过条件查询课件的列表信息
	 * 
	 * @param lp
	 * @param page
	 * @return
	 */
	PageList<LessonPlan> findCourseList(LessonPlan lp, Page page);

	/**
	 * 课件的保存操作
	 * 
	 * @param lp
	 */
	void saveCourseware(LessonPlan lp);

	/**
	 * 获得当前登陆用户、当前学年的所有有效的课件
	 * 
	 * @return
	 */
	List<LessonPlan> findAllCourseList();

	/**
	 * 删除课件
	 * 
	 * @param lp
	 * @return
	 */
	Boolean deleteCourseware(LessonPlan lp);

	/**
	 * 分享课件
	 * 
	 * @param lp
	 * @return
	 */
	Boolean sharingCourseware(LessonPlan lp);

	/**
	 * 提交课件之前的数据查询展示
	 * 
	 * @param isSubmit
	 * @return
	 */
	Map<String,Object> getSubmitData(Integer isSubmit);

	/**
	 * 提交或者取消提交课件
	 * 
	 * @param isSubmit
	 * @param planIds
	 * @return
	 */
	Boolean submitCourseware(String isSubmit, String planIds);

	String setOnlyPlanName(LessonPlan lp, int i, Integer j);

}
