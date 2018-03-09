/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.activity.bo.Activity;
import com.tmser.tr.activity.dao.ActivityDao;
import com.tmser.tr.comment.service.AbstractCommentCallback;
import com.tmser.tr.common.ResTypeConstants;

/**
 * <pre>
 *	教研活动评论回调接口，更新评论状态
 *	集体备课、区域教研、校际教研
 * </pre>
 *
 * @author tmser
 * @version $Id: SchoolActivityCommentCallback.java, v 1.0 2016年1月27日 下午4:49:31 tmser Exp $
 */
@Service
@Transactional
public class ActivityCommentCallback extends AbstractCommentCallback{

	@Autowired
	private ActivityDao activityDao;

	/**
	 * @param resid
	 * @param restype
	 * @see com.tmser.tr.comment.service.CommentCallback#commentSuccessCallback(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void commentSuccessCallback(Integer resid, String content) {
		Activity a = activityDao.get(resid);
		if(a!=null && !a.getIsComment()){
				a.setIsComment(true);
				activityDao.update(a);
		}
	}

	/**
	 * @param restype
	 * @return
	 * @see com.tmser.tr.comment.service.CommentCallback#support(java.lang.Integer)
	 */
	@Override
	public boolean support(Integer restype) {
		return restype==ResTypeConstants.ACTIVITY;
	}

	/**
	 * @param activeId
	 * @return
	 * @see com.tmser.tr.comment.service.AbstractCommonentCallback#canDiscuss(java.lang.Integer)
	 */
	@Override
	public boolean canDiscuss(Integer activeId) {
		Activity a = activityDao.get(activeId);
		return !a.getIsOver();
	}
	

}
