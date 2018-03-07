/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.common.page.PageList;
import com.tmser.tr.history.service.ThesisHistory;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.thesis.bo.Thesis;
import com.tmser.tr.thesis.dao.ThesisDao;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.CurrentUserContext;

/**
 * <pre>
 *  历史资源-教学文章impl
 * </pre>
 *
 * @author dell
 * @version $Id: ThesisHistoryServiceImpl.java, v 1.0 2016年5月31日 下午4:54:49 dell Exp $
 */
@Service
@Transactional
public class ThesisHistoryImpl implements ThesisHistory {

	@Autowired
	private ThesisDao thesisDao;
	@Autowired
	private ResourcesService resourcesService;
	@Resource
	private UserSpaceService userSpaceService;
	/**
	 * @param userId
	 * @param code
	 * @param currentYear
	 * @return
	 * @see com.tmser.tr.history.service.ThesisHistory#getThesisHistory(java.lang.Integer, java.lang.String, java.lang.Integer)
	 */
	@Override
	public Integer getThesisHistory(Integer userId, String code,
			Integer currentYear) {
		// TODO Auto-generated method stub
		Thesis model=new Thesis();
		model.setSchoolYear(currentYear);
		model.setUserId(userId);
		Integer count=thesisDao.count(model);
		return count;
	}
	
	/**
	 * 分页查询
	 * @param thesis
	 * @return
	 * @see com.tmser.tr.history.service.ThesisHistory#findCourseList(com.tmser.tr.thesis.bo.Thesis)
	 */
	@Override
	public PageList<Thesis> findCourseList(Thesis thesis) {
		thesis.setUserId(CurrentUserContext.getCurrentUserId());//用户Id
		thesis.setEnable(1);//有效
		thesis.addOrder("lastupDttm desc");
		PageList<Thesis> listPage = thesisDao.listPage(thesis);
		for (Thesis th:listPage.getDatalist()) {
			if (th.getResId()!=null) {
			 Resources r= resourcesService.findOne(th.getResId());
			 if (r!=null&&r.getSize()!=null) {
				th.setFlago(String.valueOf(r.getSize()));
			  }
			}
		}
		return listPage;
	}

}
