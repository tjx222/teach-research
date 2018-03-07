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
import com.tmser.tr.managerecord.service.AbstractCheckRecordService;
import com.tmser.tr.managerecord.service.ThesisCheckService;
import com.tmser.tr.thesis.bo.Thesis;
import com.tmser.tr.thesis.dao.ThesisDao;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * <pre>
 *	 教学文章管理记录服务类
 * </pre>
 *
 * @author zpp
 * @version $Id: ThesisCheckRecordServiceImpl.java, v 1.0 2016年8月17日 下午2:06:11 zpp Exp $
 */
@Service
@Transactional
public class ThesisCheckRecordServiceImpl extends AbstractCheckRecordService implements ThesisCheckService{

	@Autowired
	private ThesisDao thesisDao; 
	
	/**
	 * @return 
	 * @see com.tmser.tr.managerecord.service.ManagerInterFaceService#setType()
	 */
	@Override
	public Integer[] getType() {
		return new Integer[]{ResTypeConstants.JIAOXUELUNWEN};
	}

	/**
	 * @return
	 * @see com.tmser.tr.managerecord.service.AbstractCheckRecordService#getName()
	 */
	@Override
	protected String getName() {
		return "查阅教学文章";
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
		Thesis t = new Thesis();
		t.setSchoolYear(schoolYear);
		t.setSchoolTerm(term);
		t.setOrgId(userSpace.getOrgId());
		t.setPhaseId(userSpace.getPhaseId());
		t.setIsSubmit(1);//提交
		int count = thesisDao.count(t);
		return count;
	}

	/**
	 * @return
	 * @see com.tmser.tr.managerecord.service.CheckRecordService#getOrder()
	 */
	@Override
	public Integer getOrder() {
		return 6;
	}


}
