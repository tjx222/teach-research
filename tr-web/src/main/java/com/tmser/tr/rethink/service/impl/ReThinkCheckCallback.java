/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.rethink.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.check.service.AbstractCheckedCallback;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.lessonplan.dao.LessonPlanDao;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: ReThinkCheckCallback.java, v 1.0 2015年3月31日 下午4:32:36 tmser Exp $
 */
@Service
@Lazy(false)
@Transactional
public class ReThinkCheckCallback extends AbstractCheckedCallback{

	@Autowired
	private LessonPlanDao lessonPlanDao;
	
	/**
	 * @param resid
	 * @param restype
	 * @see com.tmser.tr.check.service.CheckedCallback#checkSuccessCallback(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void checkSuccessCallback(Integer resid, Integer restype,String content) {
		try {
			LessonPlan lessonPlan = lessonPlanDao.get(resid);
			if(!lessonPlan.getIsScan() || !lessonPlan.getScanUp()){
				lessonPlan.setScanUp(true);
				lessonPlan.setIsScan(true);
				lessonPlanDao.update(lessonPlan);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return
	 * @see com.tmser.tr.check.service.AbstractCheckedCallback#getType()
	 */
	@Override
	protected Integer getType() {
		return ResTypeConstants.FANSI;
	}

	@Override
	public boolean support(Integer restype) {
		if(restype==ResTypeConstants.FANSI_OTHER){
			return true;
		}else{
			return false;
		}
	}
	

}
