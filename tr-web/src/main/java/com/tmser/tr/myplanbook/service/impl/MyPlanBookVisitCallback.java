/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.myplanbook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.lecturerecords.service.AbstractLecRecActionCallback;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.dao.LessonInfoDao;

/**
 * <pre>
 * 听课记录回调业务类
 * </pre>
 *
 * @author wangdawei
 * @version $Id: MyPlanBookVisitCallback.java, v 1.0 2015年5月8日 下午2:52:09 wangdawei Exp $
 */
@Service
@Lazy(false)
@Transactional
public class MyPlanBookVisitCallback extends AbstractLecRecActionCallback{

	@Autowired
	LessonInfoDao lessonInfoDao;
	
	/**
	 * @param resid
	 * @param restype
	 * @see com.tmser.tr.lecturerecords.service.ActionCallback#actionSuccessCallback(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void actionSuccessCallback(Integer resid, Integer restype) {
		LessonInfo lessonInfo = new LessonInfo();
		lessonInfo.setId(resid);
		lessonInfo.addCustomCulomn(" visitCount = visitCount+1");
		lessonInfo.setVisitUp(true);
		lessonInfoDao.update(lessonInfo);
	}

	/**
	 * @param restype
	 * @return
	 * @see com.tmser.tr.lecturerecords.service.ActionCallback#support(java.lang.Integer)
	 */
	@Override
	public boolean support(Integer restype) {
		return true;
	}

	/**
	 * 删除听课意见 回调
	 * @param resid
	 * @see com.tmser.tr.lecturerecords.service.ActionCallback#deleteSuccessCallback(java.lang.Integer)
	 */
	@Override
	public void deleteSuccessCallback(Integer resid) {
		LessonInfo lessonInfo = new LessonInfo();
		lessonInfo.setId(resid);
		lessonInfo.addCustomCulomn(" visitCount = visitCount-1");
		lessonInfoDao.update(lessonInfo);
	}
	
}
