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
import com.tmser.tr.plainsummary.bo.PlainSummary;
import com.tmser.tr.plainsummary.dao.PlainSummaryDao;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * <pre>
 *	 查阅教案管理记录服务类
 * </pre>
 *
 * @author sysc
 * @version $Id: managerServiceTwoImpl.java, v 1.0 2015年5月7日 下午2:06:11 sysc Exp $
 */
@Service
@Transactional
public class JhzjCheckRecordServiceImpl extends AbstractCheckRecordService{

	@Autowired
	private PlainSummaryDao plainSummaryDao;
	/**
	 * @return 
	 * @see com.tmser.tr.managerecord.service.ManagerInterFaceService#setType()
	 */
	@Override
	public Integer[] getType() {
		return new Integer[]{ResTypeConstants.TPPLAIN_SUMMARY_PLIAN,ResTypeConstants.TPPLAIN_SUMMARY_SUMMARY};
	}

	/**
	 * @return
	 * @see com.tmser.tr.managerecord.service.AbstractCheckRecordService#getName()
	 */
	@Override
	protected String getName() {
		return "查阅计划总结";
	}

	/**
	 * 获取提交计划总结总数
	 * @param grade
	 * @param term
	 * @param subject
	 * @return
	 * @see com.tmser.tr.managerecord.service.AbstractCheckRecordService#getSubmitSum(int, int, int)
	 */
	@Override
	public Integer getSubmitSum(Set<Integer> grades, Set<Integer> subjects,Integer term) {
		UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); //用户空间
		Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		PlainSummary model = new PlainSummary();
		model.setOrgId(userSpace.getOrgId());
		model.setTerm(term);
		model.setSchoolYear(schoolYear);
		model.setIsSubmit(1);
		model.setPhaseId(userSpace.getPhaseId());
		return plainSummaryDao.count(model);
	}

	/**
	 * @return
	 * @see com.tmser.tr.managerecord.service.CheckRecordService#getOrder()
	 */
	@Override
	public Integer getOrder() {
		return 4;
	}

}
