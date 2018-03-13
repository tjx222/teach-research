/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.lessonplan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.check.service.AbstractCheckedCallback;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.lessonplan.dao.LessonInfoDao;
import com.tmser.tr.lessonplan.dao.LessonPlanDao;

/**
 * <pre>
 *	备课资源查阅回调业务类
 * </pre>
 *
 * @author wangdawei
 * @version $Id: MyPlanBookCallback.java, v 1.0 2015年4月1日 上午10:27:08 wangdawei Exp $
 */
@Service
@Lazy(false)
@Transactional
public class MyPlanBookScanCallback extends AbstractCheckedCallback{

	@Autowired
	LessonInfoDao lessonInfoDao;
	@Autowired
	LessonPlanDao lessonPlanDao;
	/**
	 * @param resid
	 * @param restype
	 * @see com.tmser.tr.check.service.CheckedCallback#checkSuccessCallback(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void checkSuccessCallback(Integer resid, Integer restype,String content) {
		LessonInfo lessonInfo = new LessonInfo();
		lessonInfo.setId(resid);
		lessonInfo.addCustomCulomn(" scanCount = scanCount+1");
		lessonInfo.setScanUp(true);
		int i = lessonInfoDao.update(lessonInfo);
		if(i>0){
			LessonPlan lessonPlan = new LessonPlan();
			lessonPlan.setInfoId(resid);
			lessonPlan.setIsSubmit(true);
			lessonPlan.setScanUp(false);
			lessonPlan.setPlanType(restype);
			List<LessonPlan> lessonPlanList = lessonPlanDao.listAll(lessonPlan);
			for(LessonPlan lp:lessonPlanList){
				lp.setIsScan(true);
				lp.setScanUp(true);
				lessonPlanDao.update(lp);
			}
		}
		
	}

	@Override
	public boolean support(Integer restype) {
		//支持教案、课件、反思 （不包含其他反思）
		return restype == ResTypeConstants.JIAOAN || restype == ResTypeConstants.KEJIAN || restype == ResTypeConstants.FANSI;
	}

	/**
	 * @return
	 * @see com.tmser.tr.check.service.AbstractCheckedCallback#getType()
	 */
	@Override
	protected Integer getType() {
		return null;
	}

}
