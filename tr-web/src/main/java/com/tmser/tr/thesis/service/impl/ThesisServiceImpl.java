/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.thesis.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.thesis.bo.Thesis;
import com.tmser.tr.thesis.dao.ThesisDao;
import com.tmser.tr.thesis.service.ThesisService;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.SessionKey;
/**
 * 上传教学论文 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Thesis.java, v 1.0 2015-03-12 Generate Tools Exp $
 */
@Service
@Transactional
public class ThesisServiceImpl extends AbstractService<Thesis, Integer> implements ThesisService {

	@Autowired
	private ThesisDao thesisDao;
	
	@Autowired
	private ResourcesService resourcesService;
	
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<Thesis, Integer> getDAO() {
		
		return thesisDao;
	}

	/**
	 * @param thesis
	 * @param page
	 * @return
	 * @see com.tmser.tr.thesis.service.ThesisService#findCourseList(com.tmser.tr.thesis.bo.Thesis, com.tmser.tr.common.page.Page)
	 */
	@Override
	public PageList<Thesis> findCourseList(Thesis thesis) {
		UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); //用户空间
		thesis.setSchoolYear(userSpace.getSchoolYear());
		thesis.setUserId(userSpace.getUserId());//用户Id
		thesis.setPhaseId(userSpace.getPhaseId());
		thesis.setEnable(1);//有效
		//thesis.setSubjectId(userSpace.getSubjectId());//学科Id
		thesis.addOrder("lastupDttm desc");
		
		PageList<Thesis> listPage = thesisDao.listPage(thesis);
		return listPage;
		
	}

	/**
	 * @param thesis
	 * @param m
	 * @param originFileName
	 * @see com.tmser.tr.thesis.service.ThesisService#saveThesis(com.tmser.tr.thesis.bo.Thesis, org.springframework.ui.Model, java.lang.String)
	 */
	@Override
	public void saveThesis(Thesis thesis, Model m, String originFileName) {
		// TODO Auto-generated method stub
		if (thesis!=null) {
			UserSpace us = (UserSpace) WebThreadLocalUtils
					.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
			thesis.setFileName(originFileName);
			String fileSuffix=originFileName.substring(originFileName.lastIndexOf(".")+1);
			thesis.setFileSuffix(fileSuffix);
			if (thesis.getId()!=null) {
				//修改教学论文
				Thesis thesis2=thesisDao.get(thesis.getId());
				if (thesis2!=null) {
					resourcesService.deleteResources(thesis2.getResId());
				}
				thesis.setUserId(us.getUserId());
				thesis.setLastupDttm(new Date());
				thesisDao.update(thesis);
				resourcesService.updateTmptResources(thesis.getResId());
				
				
			}else {
				thesis.setUserId(us.getUserId());
				thesis.setPhaseId(us.getPhaseId());
				thesis.setIsShare(0);
				thesis.setIsComment(0);
				thesis.setCrtId(us.getUserId());
				thesis.setCrtDttm(new Date());
				thesis.setLastupId(us.getUserId());
				thesis.setLastupDttm(new Date());
				thesis.setEnable(1);
				thesis.setIsSubmit(Thesis.NOT_SUBMIT);
				thesis.setScanCount(0);
				thesis.setIsScan(Thesis.NOT_SCAN);
				thesis.setSchoolTerm((Integer)WebThreadLocalUtils
						.getSessionAttrbitue(SessionKey.CURRENT_TERM));
				thesis.setPhaseId(us.getPhaseId());
				thesis.setOrgId(us.getOrgId());
				thesis.setSchoolYear((Integer)WebThreadLocalUtils
						.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
				thesisDao.insert(thesis);
				m.addAttribute("success", true);
				resourcesService.updateTmptResources(thesis.getResId());
			}
		}
	}

	/**
	 * @param isSubmit
	 * @return
	 * @see com.tmser.tr.thesis.service.ThesisService#getSubmitData(java.lang.String)
	 */
	@Override
	public List<Thesis> getSubmitData(Thesis thesis,User user) {
		List<Thesis> listAll = new ArrayList<Thesis>();
		if(thesis != null){
			thesis.setUserId(user.getId());
			listAll = thesisDao.listAll(thesis);
		}
		return listAll;
	}
	/**
	 * 更新教学文章的状态
	 * @param resIds
	 * @param state
	 * @see com.tmser.tr.thesis.service.ThesisService#updateSubmitState(java.lang.Integer[], java.lang.Integer)
	 */
	@Override
	public boolean updateSubmitState(Integer[] resIds,Integer state) {
		Thesis thesis = new Thesis();
		boolean updates = true;
		for (Integer integer : resIds) {
			if(integer != null){
				thesis.setId(integer);
				thesis.setIsSubmit(state);
				thesis.setSubmitTime(new Date());
				thesis.setLastupDttm(new Date());
				updates = thesisDao.update(thesis)>0;
			}
		}
		return updates;
	}

}
