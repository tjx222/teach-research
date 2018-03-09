/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.managerecord.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.lecturerecords.bo.LectureRecords;
import com.tmser.tr.lecturerecords.dao.LectureRecordsDao;
import com.tmser.tr.managerecord.service.AbstractCheckRecordService;
import com.tmser.tr.managerecord.service.LectureCheckService;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * <pre>
 *	 听课记录管理记录服务类
 * </pre>
 *
 * @author tmser
 * @version $Id: LectureCheckRecordServiceImpl.java, v 1.0 2016年8月17日 下午2:06:11 tmser Exp $
 */
@Service
@Transactional
public class LectureCheckRecordServiceImpl extends AbstractCheckRecordService implements LectureCheckService{

	@Autowired
	private LectureRecordsDao lectureRecordsDao; 
	
	/**
	 * @return 
	 * @see com.tmser.tr.managerecord.service.ManagerInterFaceService#setType()
	 */
	@Override
	public Integer[] getType() {
		return new Integer[]{ResTypeConstants.LECTURE};
	}

	/**
	 * @return
	 * @see com.tmser.tr.managerecord.service.AbstractCheckRecordService#getName()
	 */
	@Override
	protected String getName() {
		return "查阅听课记录";
	}

	/**
	 * @param grade
	 * @param term
	 * @param subject
	 * @return
	 * @see com.tmser.tr.managerecord.service.AbstractCheckRecordService#getSubmitSum(int, int, int)
	 */
	@Override
	public Integer getSubmitSum(Set<Integer> grades, Set<Integer> subjects,Integer term) {
		UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		Integer schoolYear = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		LectureRecords lr = new LectureRecords();
		lr.setSchoolYear(schoolYear);
		lr.setTerm(term);
		lr.setOrgId(userSpace.getOrgId());
		lr.setPhaseId(userSpace.getPhaseId());
		lr.setIsSubmit(1);//提交
		int count = lectureRecordsDao.count(lr);
		return count;
	}

	/**
	 * @return
	 * @see com.tmser.tr.managerecord.service.CheckRecordService#getOrder()
	 */
	@Override
	public Integer getOrder() {
		return 5;
	}


}
