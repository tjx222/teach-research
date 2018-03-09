/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.rethink.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.comment.service.AbstractCommentCallback;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.lessonplan.dao.LessonPlanDao;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: ReThinkCommentCallback.java, v 1.0 2015年4月1日 下午4:49:31 tmser Exp $
 */
@Service
@Lazy(false)
@Transactional
public class ReThinkCommentCallback extends AbstractCommentCallback{
	
	@Autowired
	private LessonPlanDao lessonPlanDao;
	
	/**
	 * @param resid
	 * @param restype
	 * @see com.tmser.tr.comment.service.CommentCallback#commentSuccessCallback(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void commentSuccessCallback(Integer resid, String content) {
			LessonPlan lessonPlan = lessonPlanDao.get(resid);
			lessonPlan.setCommentUp(true);
			lessonPlan.setIsComment(true);
			lessonPlanDao.update(lessonPlan);
	}

	/**
	 * @param restype
	 * @return
	 * @see com.tmser.tr.comment.service.CommentCallback#support(java.lang.Integer)
	 */
	@Override
	public boolean support(Integer restype) {
		return restype==ResTypeConstants.FANSI_OTHER;
	}

}
