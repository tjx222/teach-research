/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.activity.bo.SchoolActivity;
import com.tmser.tr.activity.dao.SchoolActivityDao;
import com.tmser.tr.comment.bo.Discuss;
import com.tmser.tr.comment.service.AbstractCommentCallback;
import com.tmser.tr.common.ResTypeConstants;

/**
 * <pre>
 * 教研活动评论回调接口，更新评论状态
 * 集体备课、区域教研、校际教研
 * </pre>
 * 
 * @author zpp
 * @version $Id: SchoolActivityCommentCallback.java, v 1.0 2016年1月27日 下午4:49:31
 *          zpp Exp $
 */
@Service
@Transactional
public class SchoolActivityCommentCallback extends AbstractCommentCallback {
	@Autowired
	private SchoolActivityDao schoolActivityDao;

	/**
	 * @param resid
	 * @param restype
	 * @see com.tmser.tr.comment.service.CommentCallback#commentSuccessCallback(java.lang.Integer,
	 *      java.lang.Integer)
	 */
	@Override
	public void commentSuccessCallback(Integer resid, String content) {
		SchoolActivity sa = schoolActivityDao.get(resid);
		if (sa != null && !sa.getIsComment()) {
			sa.setIsComment(true);
			schoolActivityDao.update(sa);
		}
	}

	/**
	 * @param restype
	 * @return
	 * @see com.tmser.tr.comment.service.CommentCallback#support(java.lang.Integer)
	 */
	@Override
	public boolean support(Integer restype) {
		return restype == ResTypeConstants.SCHOOLTEACH;
	}

	@Override
	public boolean canDiscuss(Integer activeId) {
		SchoolActivity sa = schoolActivityDao.get(activeId);
		return !sa.getIsOver();
	}

	@Override
	public void discussSuccessCallback(Discuss discuss) {
		SchoolActivity sa = schoolActivityDao.get(discuss.getActivityId());
		if (sa.getCommentsNum() == null) {
			sa.setCommentsNum(1);
		} else {
			sa.setCommentsNum(sa.getCommentsNum() + 1);
		}
		schoolActivityDao.update(sa);
	}
}
