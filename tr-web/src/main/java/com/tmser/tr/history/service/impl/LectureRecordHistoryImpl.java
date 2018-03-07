/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.history.service.LectureRecordHistory;
import com.tmser.tr.lecturerecords.bo.LectureRecords;
import com.tmser.tr.lecturerecords.dao.LectureRecordsDao;

/**
 * <pre>
 *  历史资源-听课记录impl
 * </pre>
 *
 * @author dell
 * @version $Id: LectureRecordHistoryImpl.java, v 1.0 2016年5月31日 下午5:17:44 dell Exp $
 */
@Service
@Transactional
public class LectureRecordHistoryImpl implements LectureRecordHistory {
	
	@Autowired
	private LectureRecordsDao lectureRecordsDao;

	/**
	 * @param userId
	 * @param code
	 * @param currentYear
	 * @return
	 * @see com.tmser.tr.history.service.LectureRecordHistory#getThesisHistory(java.lang.Integer, java.lang.String, java.lang.Integer)
	 */
	@Override
	public Integer getLectureRecordHistory(Integer userId, String code,
			Integer currentYear) {
		// TODO Auto-generated method stub
		LectureRecords model=new LectureRecords();
		model.setLecturepeopleId(userId);// 听课人ID
		model.setIsDelete(false);// 不删除
		model.setIsEpub(1);// 发布
		model.setSchoolYear(currentYear);
		
		return lectureRecordsDao.count(model);
	}

}
