/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.history.service.PlainSummaryHistoryService;
import com.tmser.tr.plainsummary.bo.PlainSummary;
import com.tmser.tr.plainsummary.service.PlainSummaryService;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 3020mt
 * @version $Id: PlainSummaryHistoryServiceImpl.java, v 1.0 2016年5月19日 下午1:40:24
 *          3020mt Exp $
 */
@Service("plainSummaryHistoryService")
public class PlainSummaryHistoryServiceImpl implements
		PlainSummaryHistoryService {

	@Autowired
	private PlainSummaryService plainSummaryService;
	@Autowired
	private UserSpaceService userSpaceService;

	/**
	 * @return
	 * @see com.tmser.tr.history.service.PlainSummaryHistoryService#findAll()
	 */
	@Override
	public Result findAll(Integer year, Integer userSpaceId, Integer term,
			Integer category, String title) {
		User user = (User) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_USER);
		PlainSummary model = new PlainSummary();
		model.setUserId(user.getId());
		model.setSchoolYear(year);
		UserSpace us = userSpaceService.findOne(userSpaceId);
		if(us != null){
			model.setUserRoleId(us.getRoleId());
		}
		model.setTerm(term);
		model.setCategory(category);
		if (title != null) {
			model.setTitle(SqlMapping.LIKE_PRFIX + title
					+ SqlMapping.LIKE_PRFIX);
		}
		model.addOrder(" lastupDttm desc ");
		List<PlainSummary> list = plainSummaryService.findAll(model);
		Result re = new Result();
		re.setCode(1);
		re.setData(list);
		re.setMsg("ok");
		return re;
	}

	/**
	 * @param year
	 * @param userSpaceId
	 * @param term
	 * @param category
	 * @param title
	 * @return
	 * @see com.tmser.tr.history.service.PlainSummaryHistoryService#findList(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@Override
	public PageList<PlainSummary> findList(Integer year,PlainSummary ps) {
		User user = (User) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_USER);
		PlainSummary model = new PlainSummary();
		model.pageSize(10);
		model.currentPage(ps.getPage().getCurrentPage());
		model.setUserId(user.getId());
		model.setSchoolYear(year);
		UserSpace us = userSpaceService.findOne(ps.getUserSpaceId());
		if(us != null){
			model.setUserRoleId(us.getRoleId());
			model.setGradeId(us.getGradeId());
			model.setSubjectId(us.getSubjectId());
		}
		model.setTerm(ps.getTerm());
		model.setCategory(ps.getCategory());
		if (StringUtils.isNotBlank(ps.getTitle())) {
			model.setTitle(SqlMapping.LIKE_PRFIX + ps.getTitle()
					+ SqlMapping.LIKE_PRFIX);
		}
		model.addOrder(" lastupDttm desc ");
		int cp = model.getPage().getCurrentPage();

		PageList<PlainSummary> plList = plainSummaryService.findByPage(model);
		if (plList.getDatalist().size() == 0 && cp > 1) {
			model.currentPage(cp - 1);
			plList = plainSummaryService.findByPage(model);
		}
		return plList;
	}

}
