/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.lessonplan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.comment.service.AbstractCommentCallback;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.lessonplan.dao.LessonInfoDao;
import com.tmser.tr.lessonplan.dao.LessonPlanDao;

/**
 * <pre>
 *	备课资源评论回调业务类
 * </pre>
 *
 * @author wangdawei
 * @version $Id: MyPlanBookCommentCallback.java, v 1.0 2015年4月1日 下午2:31:53 wangdawei Exp $
 */
@Service
@Lazy(false)
@Transactional
public class MyPlanBookCommentCallback extends AbstractCommentCallback{

	@Autowired
	LessonInfoDao lessonInfoDao;
	@Autowired
	LessonPlanDao lessonPlanDao;
	
	/**
	 * @param resid
	 * @param restype
	 * @see com.tmser.tr.comment.service.CommentCallback#commentSuccessCallback(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void commentSuccessCallback(Integer resid, String content) {
		LessonPlan lessonPlan = lessonPlanDao.get(resid);
		if(lessonPlan!=null){
			if(!lessonPlan.getCommentUp()){
				lessonPlan.setCommentUp(true);
				lessonPlan.setIsComment(true);
				lessonPlanDao.update(lessonPlan);
			}
			LessonInfo lessonInfo = new LessonInfo();
			lessonInfo.setId(lessonPlan.getInfoId());
			lessonInfo.addCustomCulomn(" commentCount = commentCount+1");
			lessonInfo.setCommentUp(true);
			lessonInfoDao.update(lessonInfo);
		}
	}

	/**
	 * @param restype
	 * @return
	 * @see com.tmser.tr.comment.service.CommentCallback#support(java.lang.Integer)
	 */
	@Override
	public boolean support(Integer restype) {
		//支持教案、课件、反思 （不包含其他反思）
		return restype == ResTypeConstants.JIAOAN || restype == ResTypeConstants.KEJIAN || restype == ResTypeConstants.FANSI;
	}
	
}
