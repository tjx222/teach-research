/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.schoolres.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.activity.bo.Activity;
import com.tmser.tr.activity.bo.SchoolActivity;
import com.tmser.tr.activity.service.ActivityService;
import com.tmser.tr.activity.service.SchoolActivityService;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.lecturerecords.bo.LectureRecords;
import com.tmser.tr.lecturerecords.dao.LectureRecordsDao;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.lessonplan.dao.LessonInfoDao;
import com.tmser.tr.lessonplan.dao.LessonPlanDao;
import com.tmser.tr.plainsummary.bo.PlainSummary;
import com.tmser.tr.plainsummary.dao.PlainSummaryDao;
import com.tmser.tr.recordbag.bo.Recordbag;
import com.tmser.tr.recordbag.dao.RecordbagDao;
import com.tmser.tr.schoolres.dao.SchoolResDao;
import com.tmser.tr.schoolres.service.SchoolResService;
import com.tmser.tr.thesis.bo.Thesis;
import com.tmser.tr.thesis.dao.ThesisDao;
/**
 * 学校资源展示服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author huangjunhua
 * @version $Id: LessonInfo.java, v 1.0 2015-03-12 tmser Exp $
 */
@Service
@Transactional
public class SchoolResServiceImpl implements SchoolResService {

	@Autowired
	private LessonInfoDao lessonInfoDao;

	@Autowired
	private LessonPlanDao lessonPlanDao;

	@Autowired
	private ActivityService activityService;

	@Autowired
	private SchoolActivityService schoolActivityService;

	@Autowired
	private PlainSummaryDao plainSummaryDao;

	@Autowired
	private ThesisDao thesisDao;

	@Autowired
	private LectureRecordsDao lectureRecordsDao;

	@Autowired
	private SchoolResDao schoolResDao;

	@Autowired
	private RecordbagDao recordbagDao;

	/**
	 * @param info
	 * @return
	 * @see com.tmser.tr.schoolres.service.SchoolResService#findAllLessonInfo(com.tmser.tr.lessonplan.bo.LessonInfo)
	 */
	@Override
	public List<LessonInfo> findAllLessonInfo(LessonInfo model) {
		// TODO Auto-generated method stub
		return lessonInfoDao.listAll(model);
	}

	/**
	 * @param model
	 * @return
	 * @see com.tmser.tr.schoolres.service.SchoolResService#findAllLessonPlan(com.tmser.tr.lessonplan.bo.LessonPlan)
	 */
	@Override
	public List<LessonPlan> findAllLessonPlan(LessonPlan model) {
		// TODO Auto-generated method stub
		return lessonPlanDao.listAll(model);
	}

	/**
	 * @param model
	 * @return
	 * @see com.tmser.tr.schoolres.service.SchoolResService#findPageLessonInfo(com.tmser.tr.lessonplan.bo.LessonPlan)
	 */
	@Override
	public PageList<LessonInfo> findPageLessonInfo(LessonInfo model) {
		// TODO Auto-generated method stub
		return lessonInfoDao.listPage(model);
	}

	/**
	 * @param model
	 * @return
	 * @see com.tmser.tr.schoolres.service.SchoolResService#findPageLessonPlan(com.tmser.tr.lessonplan.bo.LessonPlan)
	 */
	@Override
	public PageList<LessonPlan> findPageLessonPlan(LessonPlan model) {
		// TODO Auto-generated method stub
		return lessonPlanDao.listPage(model);
	}

	/**
	 * @param model
	 * @param count
	 * @return
	 * @see com.tmser.tr.schoolres.service.SchoolResService#findLimitActivity
	 */
	@Override
	public List<Activity> findLimitActivity(Activity model,Integer limit) {
		return activityService.find(model, limit);
	}

	/**
	 * @param model
	 * @return
	 * @see com.tmser.tr.schoolres.service.SchoolResService#findLimitSchoolActivity
	 */
	@Override
	public List<SchoolActivity> findLimitSchoolActivity(SchoolActivity model,Integer limit) {
		return schoolActivityService.find(model, limit);
	}

	/**
	 * @param model
	 * @return
	 * @see com.tmser.tr.schoolres.service.SchoolResService#findPageActivity(com.tmser.tr.activity.bo.Activity)
	 */
	@Override
	public PageList<Activity> findPageActivity(Activity model) {
		return activityService.findByPage(model);
	}

	/**
	 * @param model
	 * @return
	 * @see com.tmser.tr.schoolres.service.SchoolResService#findPageSchoolActivity(com.tmser.tr.activity.bo.SchoolActivity)
	 */
	@Override
	public PageList<SchoolActivity> findPageSchoolActivity(SchoolActivity model) {
		return schoolActivityService.findByPage(model);
	}

	/**
	 * @param model
	 * @return
	 * @see com.tmser.tr.schoolres.service.SchoolResService#findAllPlainSummary(com.tmser.tr.plainsummary.bo.PlainSummary)
	 */
	@Override
	public List<PlainSummary> findAllPlainSummary(PlainSummary model) {
		// TODO Auto-generated method stub
		return plainSummaryDao.listAll(model);
	}

	/**
	 * @param model
	 * @return
	 * @see com.tmser.tr.schoolres.service.SchoolResService#findPagePlainSummary(com.tmser.tr.plainsummary.bo.PlainSummary)
	 */
	@Override
	public PageList<PlainSummary> findPagePlainSummary(PlainSummary model) {
		// TODO Auto-generated method stub
		return plainSummaryDao.listPage(model);
	}

	/**
	 * @param model
	 * @return
	 * @see com.tmser.tr.schoolres.service.SchoolResService#findAllThesis(com.tmser.tr.thesis.bo.Thesis)
	 */
	@Override
	public List<Thesis> findAllThesis(Thesis model) {
		// TODO Auto-generated method stub
		return thesisDao.listAll(model);
	}

	/**
	 * @param model
	 * @return
	 * @see com.tmser.tr.schoolres.service.SchoolResService#findPageThesis(com.tmser.tr.thesis.bo.Thesis)
	 */
	@Override
	public PageList<Thesis> findPageThesis(Thesis model) {
		// TODO Auto-generated method stub
		return thesisDao.listPage(model);
	}

	/**
	 * @param model
	 * @return
	 * @see com.tmser.tr.schoolres.service.SchoolResService#findAllLectureRecords(com.tmser.tr.lecturerecords.bo.LectureRecords)
	 */
	@Override
	public List<LectureRecords> findAllLectureRecords(LectureRecords model) {
		// TODO Auto-generated method stub
		return lectureRecordsDao.listAll(model);
	}

	/**
	 * @param model
	 * @return
	 * @see com.tmser.tr.schoolres.service.SchoolResService#findPageLectureRecords(com.tmser.tr.lecturerecords.bo.LectureRecords)
	 */
	@Override
	public PageList<LectureRecords> findPageLectureRecords(LectureRecords model) {
		// TODO Auto-generated method stub
		return lectureRecordsDao.listPage(model);
	}

	/**
	 * @param recordbag
	 * @return
	 * @see com.tmser.tr.schoolres.service.SchoolResService#findAllRecordbags(com.tmser.tr.recordbag.bo.Recordbag)
	 */
	@Override
	public List<Map<String, Object>> findAllRecordbags(Integer orgID) {
		// TODO Auto-generated method stub
		return schoolResDao.findAllRecordbags(orgID);
	}

	/**
	 * @param model
	 * @return
	 * @see com.tmser.tr.schoolres.service.SchoolResService#findAllRecordbag(com.tmser.tr.recordbag.bo.Recordbag)
	 */
	@Override
	public List<Recordbag> findAllRecordbag(Recordbag model) {
		// TODO Auto-generated method stub
		return recordbagDao.listAll(model);
	}

	/**
	 * @param model
	 * @return
	 * @see com.tmser.tr.schoolres.service.SchoolResService#findPageRecordbag(com.tmser.tr.recordbag.bo.Recordbag)
	 */
	@Override
	public PageList<Recordbag> findPageRecordbag(Recordbag model) {
		// TODO Auto-generated method stub
		return recordbagDao.listPage(model);
	}

	/**
	 * @param page
	 * @return
	 * @see com.tmser.tr.schoolres.service.SchoolResService#findPageRecordbags(com.tmser.tr.common.page.Page)
	 */
	@Override
	public PageList<Map<String, Object>> findPageRecordbags(Page page,Integer orgID) {
		// TODO Auto-generated method stub
		return schoolResDao.findPageRecordbags(page,orgID);
	}

	/**
	 * @param id
	 * @return
	 * @see com.tmser.tr.schoolres.service.SchoolResService#getOneLessonInfo(java.lang.Integer)
	 */
	@Override
	public LessonInfo getOneLessonInfo(Integer id) {
		// TODO Auto-generated method stub
		return lessonInfoDao.get(id);
	}

	/**
	 * @param id
	 * @return
	 * @see com.tmser.tr.schoolres.service.SchoolResService#getLessonPlan(java.lang.Integer)
	 */
	@Override
	public LessonPlan getOneLessonPlan(Integer id) {
		// TODO Auto-generated method stub
		return lessonPlanDao.get(id);
	}

	/**
	 * @param activityObj
	 * @return
	 * @see com.tmser.tr.schoolres.service.SchoolResService#findActivityCount(java.lang.Object)
	 */
	@Override
	public int findActivityCount(Object activityObj) {
		int count = 0;
		if(activityObj instanceof Activity){
			Activity model = (Activity)activityObj;
			count = activityService.count(model);
		}else if(activityObj instanceof SchoolActivity){
			SchoolActivity model = (SchoolActivity)activityObj;
			count = schoolActivityService.count(model);
		}
		return count;
	}
}
