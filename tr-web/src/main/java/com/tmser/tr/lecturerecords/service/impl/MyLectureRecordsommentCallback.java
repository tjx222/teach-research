/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.lecturerecords.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.comment.service.AbstractCommentCallback;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.lecturerecords.bo.LectureRecords;
import com.tmser.tr.lecturerecords.dao.LectureRecordsDao;
/**
 * <pre>
 *听课记录评论回调函数
 * </pre>
 *
 * @author huangjunhua
 */
@Service
@Lazy(false)
@Transactional
public class MyLectureRecordsommentCallback extends AbstractCommentCallback{

	@Autowired
	LectureRecordsDao lectureRecordsDao;
	
	/**
	 * @param resid
	 * @param restype
	 * @see com.tmser.tr.comment.service.CommentCallback#commentSuccessCallback(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void commentSuccessCallback(Integer resid, String content) {
		LectureRecords lectureRecords = lectureRecordsDao.get(resid);
		if(lectureRecords!=null){
			lectureRecords.setIsComment(1);
			lectureRecords.setCommentUp(1);
			lectureRecordsDao.update(lectureRecords);
		}
	}

	/**
	 * @param restype
	 * @return
	 * @see com.tmser.tr.comment.service.CommentCallback#support(java.lang.Integer)
	 */
	@Override
	public boolean support(Integer restype) {
		return restype==ResTypeConstants.LECTURE;
	}
	
}
