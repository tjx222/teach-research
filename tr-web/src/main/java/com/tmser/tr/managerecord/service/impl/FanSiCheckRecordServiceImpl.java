/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.managerecord.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.lessonplan.dao.LessonInfoDao;
import com.tmser.tr.lessonplan.dao.LessonPlanDao;
import com.tmser.tr.managerecord.service.AbstractCheckRecordService;
import com.tmser.tr.managerecord.service.FanSiCheckService;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * <pre>
 *	 查阅教案管理记录服务类
 * </pre>
 *
 * @author sysc
 * @version $Id: FanSiCheckRecordServiceImpl.java, v 1.0 2015年5月7日 下午2:06:11 sysc Exp $
 */
@Service
@Transactional
public class FanSiCheckRecordServiceImpl extends AbstractCheckRecordService implements FanSiCheckService{
	
	@Autowired
	private LessonPlanDao lessonPlanDao;
	@Autowired
	private LessonInfoDao lessonInfoDao;

	/**
	 * @return 
	 * @see com.tmser.tr.managerecord.service.ManagerInterFaceService#setType()
	 */
	@Override
	public Integer[] getType() {
		return new Integer[]{ResTypeConstants.FANSI,ResTypeConstants.FANSI_OTHER};
	}

	/**
	 * @return
	 * @see com.tmser.tr.managerecord.service.AbstractCheckRecordService#getName()
	 */
	@Override
	protected String getName() {
		return "查阅反思";
	}

	/**
	 * @param grades
	 * @param term
	 * @param subjects
	 * @return
	 * @see com.tmser.tr.managerecord.service.AbstractCheckRecordService#getSubmitSum(int, int, int)
	 */
	@Override
	public Integer getSubmitSum(Set<Integer> grades, Set<Integer> subjects,Integer term) {
		UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		Integer schoolYear = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		//其他反思提交数
		LessonPlan lp = new LessonPlan();
		lp.setSchoolYear(schoolYear);
		lp.setTermId(term);
		lp.setOrgId(userSpace.getOrgId());
		lp.setPlanType(ResTypeConstants.FANSI_OTHER);
		lp.setIsSubmit(true);
		lp.setPhaseId(userSpace.getPhaseId());
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("subjectsStr", subjects);
		paramMap.put("gradesStr", grades);
		lp.addCustomCondition(" and subjectId in(:subjectsStr) and gradeId in(:gradesStr)", paramMap);
		int count = lessonPlanDao.count(lp);
		//反思按照课题提交数（课后反思）
		LessonInfo li = new LessonInfo();
		li.setSchoolYear(schoolYear);
		li.setTermId(term);
		li.setOrgId(userSpace.getOrgId());
		li.setPhaseId(userSpace.getPhaseId());
		li.addCustomCondition("  and subjectId in(:subjectsStr) and gradeId in(:gradesStr) and fansiSubmitCount > 0", paramMap);
		int count2 = lessonInfoDao.count(li);
		int total = count + count2 ;
		return total;
	}

	/**
	 * @return
	 * @see com.tmser.tr.managerecord.service.CheckRecordService#getOrder()
	 */
	@Override
	public Integer getOrder() {
		return 2;
	}

	/**
	 * 查看反思详情及查阅信息
	 * @param type
	 * @param lesInfoId
	 * @param m
	 * @see com.tmser.tr.managerecord.service.FanSiCheckService#fansiView(java.lang.Integer, java.lang.Integer, org.springframework.ui.Model)
	 */
	@Override
	public void fansiView(Integer type, Integer lesInfoId, Model m) {
		Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		if(type == ResTypeConstants.FANSI){
			if(lesInfoId != null){
				LessonInfo lessonInfo = lessonInfoDao.get(lesInfoId);
				LessonPlan model = new LessonPlan();
				model.setBookId(lessonInfo.getBookId());
				model.setLessonId(lessonInfo.getLessonId());
				model.setIsSubmit(true);
				model.setUserId(lessonInfo.getUserId());
				model.setSchoolYear(schoolYear);
				model.addOrder("orderValue");
				List<LessonPlan> lplist = lessonPlanDao.listAll(model);
				m.addAttribute("lessonList",lplist);
				m.addAttribute("data",lessonInfo);
			}
		}else if(type == ResTypeConstants.FANSI_OTHER){
			if(lesInfoId != null){
				LessonPlan lessonPlan = lessonPlanDao.get(lesInfoId);
				LessonInfo lessonInfo = new LessonInfo();
				lessonInfo.setId(lessonPlan.getPlanId());
				lessonInfo.setSubmitTime(lessonPlan.getSubmitTime());
				lessonInfo.setLessonName(lessonPlan.getPlanName());
				lessonInfo.setGradeId(lessonPlan.getGradeId());
				lessonInfo.setSubjectId(lessonPlan.getSubjectId());
				lessonInfo.setUserId(lessonPlan.getUserId());
				List<LessonPlan> lplist = new ArrayList<LessonPlan>();
				lplist.add(lessonPlan);
				m.addAttribute("lessonList",lplist);
				m.addAttribute("data",lessonInfo);
			}
		}
	}

}
