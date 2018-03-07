/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.lecturerecords.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.lecturerecords.bo.LectureRecords;
import com.tmser.tr.lecturerecords.dao.LectureRecordsDao;
import com.tmser.tr.lecturerecords.service.AbstractLecReplyActionCallback;

/**
 * <pre>
 *新增回复回调接口实现类
 * </pre>
 *
 * @author huangjunhua
 * @version $Id: ReplyFunctionBackImpl.java, v 1.0 2015年4月28日 下午3:27:24 huangjunhua Exp $
 */
@Service
@Lazy(false)
@Transactional
public class ReplyFunctionBackImpl extends AbstractLecReplyActionCallback{
	@Autowired
	LectureRecordsDao lectureRecordsDao;
	
	/**
	 * @param resid
	 * @param restype
	 * @see com.tmser.tr.lecturerecords.service.ActionCallback#actionSuccessCallback(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void actionSuccessCallback(Integer resid, Integer restype) {
		// TODO Auto-generated method stub
		LectureRecords records=lectureRecordsDao.get(resid);
		records.setIsReply(1);
		records.setReplyUp(1);
		lectureRecordsDao.update(records);
	}

	/**
	 * @param restype
	 * @return
	 * @see com.tmser.tr.lecturerecords.service.ActionCallback#support(java.lang.Integer)
	 */
	@Override
	public boolean support(Integer restype) {
		// TODO Auto-generated method stub
		return true;
	}

}
