/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.managerecord.service.impl;

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
import com.tmser.tr.managerecord.service.JiaoAnCheckService;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * <pre>
 *	 查阅教案管理记录服务类
 * </pre>
 *
 * @author sysc
 * @version $Id: managerServiceTwoImpl.java, v 1.0 2015年5月7日 下午2:06:11 sysc Exp $
 */
@Service
@Transactional
public class JiaoAnCheckRecordServiceImpl extends AbstractCheckRecordService implements JiaoAnCheckService{

	@Autowired
	private LessonInfoDao lessonInfoDao;
	@Autowired
	private LessonPlanDao lessonPlanDao;
	/**
	 * @return 
	 * @see com.tmser.tr.managerecord.service.ManagerInterFaceService#setType()
	 */
	@Override
	public Integer[] getType() {
		return new Integer[]{ResTypeConstants.JIAOAN};
	}

	/**
	 * @return
	 * @see com.tmser.tr.managerecord.service.AbstractCheckRecordService#getName()
	 */
	@Override
	protected String getName() {
		return "查阅教案";
	}

	/**
	 * @param grade
	 * @param term
	 * @param subject
	 * @return
	 * @see com.tmser.tr.managerecord.service.AbstractCheckRecordService#getSubmitSum(int, int, int)
	 */
	@Override
	public Integer getSubmitSum(Set<Integer> grades, Set<Integer> subjects,Integer term) {
		UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		LessonInfo lessonInfo = new LessonInfo();
		lessonInfo.setOrgId(userSpace.getOrgId());
		lessonInfo.setTermId(term);
		lessonInfo.setSchoolYear(schoolYear);
		lessonInfo.setPhaseId(userSpace.getPhaseId());
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("subjectsStr", subjects);
		paramMap.put("gradesStr", grades);
		//lessonInfo.addCustomCondition(" and jiaoanSubmitCount>0", null);
		lessonInfo.addCustomCondition(" and subjectId in(:subjectsStr) and gradeId in(:gradesStr) and jiaoanSubmitCount>0", paramMap);
		int count = lessonInfoDao.count(lessonInfo);
		return count;
	}

	/**
	 * @return
	 * @see com.tmser.tr.managerecord.service.CheckRecordService#getOrder()
	 */
	@Override
	public Integer getOrder() {
		return 0;
	}

	/**
	 * 查看教案课题的查阅详情页
	 * @param planInfoId
	 * @param m
	 * @see com.tmser.tr.managerecord.service.JiaoAnCheckService#viewLessonPlanCheckInfo(java.lang.Integer, org.springframework.ui.Model)
	 */
	@Override
	public void viewLessonPlanCheckInfo(Integer planInfoId, Model m) {
		Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		if(planInfoId != null){
		 LessonInfo lessonInfo = lessonInfoDao.get(planInfoId);
		 LessonPlan model = new LessonPlan();
		 model.setBookId(lessonInfo.getBookId());
		 model.setLessonId(lessonInfo.getLessonId());
		 model.setIsSubmit(true);
		 model.setUserId(lessonInfo.getUserId());
		 model.setSchoolYear(schoolYear);
		 model.addOrder("orderValue");
		 model.setPlanType(LessonPlan.JIAO_AN);
		 List<LessonPlan> lplist = lessonPlanDao.listAll(model);
		 m.addAttribute("lessonList",lplist);
		 m.addAttribute("data",lessonInfo);
	}
		
	}

	
}
