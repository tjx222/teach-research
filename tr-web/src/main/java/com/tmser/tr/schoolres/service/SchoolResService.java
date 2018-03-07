/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.schoolres.service;

import java.util.List;
import java.util.Map;

import com.tmser.tr.activity.bo.Activity;
import com.tmser.tr.activity.bo.SchoolActivity;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.lecturerecords.bo.LectureRecords;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.plainsummary.bo.PlainSummary;
import com.tmser.tr.recordbag.bo.Recordbag;
import com.tmser.tr.thesis.bo.Thesis;

/**
 * 学校资源展示服务
 * <pre>
 *
 * </pre>
 *
 * @author huangjunhua
 * @version $Id: LessonInfo.java, v 1.0 2015-03-12 tmser Exp $
 */

public interface SchoolResService{

	/**
	 * 按照条件查找所有课题资源
	 * @param info
	 * @return
	 */
	public List<LessonInfo> findAllLessonInfo(LessonInfo model);

	/**
	 * 通过主键得到一个lessonInfo对象
	 * @param model
	 * @return
	 */
	public LessonInfo getOneLessonInfo(Integer id);

	/**
	 * 分页查询备课总体资源
	 * @param model
	 * @return
	 */
	public PageList<LessonInfo> findPageLessonInfo(LessonInfo model);

	/**
	 * 按照要求查找所有的教案、课件、反思
	 * @param model
	 * @return
	 */
	public List<LessonPlan> findAllLessonPlan(LessonPlan model);

	/**
	 * 得到一个具体资源计划
	 * @param id
	 * @return
	 */
	public LessonPlan getOneLessonPlan(Integer id);

	/**
	 * 分页查找所有的教案、课件、反思
	 * @param model
	 * @return
	 */
	public PageList<LessonPlan> findPageLessonPlan(LessonPlan model);

	/**
	 * 按照条件查找所有的集体备课
	 * @param model
	 * @param limit
	 * @return
	 */
	public List<Activity> findLimitActivity(Activity model,Integer limit);

	/**
	 * 分页查找集体备课
	 * @param model
	 * @return
	 */
	public PageList<Activity> findPageActivity(Activity model);

	/**
	 * 按照条件查找所有的校际教研
	 * @param model
	 * @return
	 */
	public List<SchoolActivity> findLimitSchoolActivity(SchoolActivity model,Integer limit);

	/**
	 * 分页查找校际教研
	 * @param model
	 * @return
	 */
	public PageList<SchoolActivity> findPageSchoolActivity(SchoolActivity model);

	/**
	 * 按条件查找所有的计划
	 * @param model
	 * @return
	 */
	public List<PlainSummary> findAllPlainSummary(PlainSummary model);

	/**
	 * 分页查找教学计划
	 * @param model
	 * @return
	 */
	public PageList<PlainSummary> findPagePlainSummary(PlainSummary model);

	/**
	 * 按条件查找所有的教学文章
	 * @param model
	 * @return
	 */
	public List<Thesis> findAllThesis(Thesis model);

	/**
	 * 分页查找教学文章
	 * @param model
	 * @return
	 */
	public PageList<Thesis> findPageThesis(Thesis model);

	/**
	 * 按条件查找所有的听课记录
	 * @param model
	 * @return
	 */
	public List<LectureRecords> findAllLectureRecords(LectureRecords model);

	/**
	 * 分页查找听课记录
	 * @param model
	 * @return
	 */
	public PageList<LectureRecords> findPageLectureRecords(LectureRecords model);

	/**
	 * 查找所有的档案袋
	 * @param recordbag
	 * @return
	 */
	public List<Map<String, Object>> findAllRecordbags(Integer orgID);

	/**
	 * 查找所有的档案袋
	 * @param model
	 * @return
	 */
	public List<Recordbag> findAllRecordbag(Recordbag model);

	/**
	 * 分页查询所有档案袋
	 * @param model
	 * @return
	 */
	public PageList<Recordbag> findPageRecordbag(Recordbag model);

	/**
	 * sql查询带分页
	 * @param page
	 * @return
	 */
	public PageList<Map<String, Object>> findPageRecordbags(Page page,Integer orgID);

	/**
	 * 获得教研活动的总数
	 * @param activity
	 * @return
	 */
	public int findActivityCount(Object activityObj);

}
