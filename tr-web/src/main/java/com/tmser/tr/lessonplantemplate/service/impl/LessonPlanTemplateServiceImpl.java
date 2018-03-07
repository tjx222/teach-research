package com.tmser.tr.lessonplantemplate.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.lessonplantemplate.service.LessonPlanTemplateService;
import com.tmser.tr.lessonplantemplate.bo.LessonPlanTemplate;
import com.tmser.tr.lessonplantemplate.dao.LessonPlanTemplateDao;
/**
 * 教案模板 服务实现类
 * @author wangdawei
 * @version 1.0
 * 2015-01-28
 */
@Service
@Transactional
public class LessonPlanTemplateServiceImpl extends AbstractService<LessonPlanTemplate, Integer> implements LessonPlanTemplateService {

	@Autowired
	private LessonPlanTemplateDao lessonPlanTemplateDao;

	/**
	 * 获取模板集合，包含系统自带的
	 * @param orgId
	 * @return
	 * @see com.tmser.tr.lessonplantemplate.service.LessonPlanTemplateService#getTemplateListByOrg(java.lang.Integer)
	 */
	@Override
	public List<LessonPlanTemplate> getTemplateListByOrg(Integer orgId) {
		List<LessonPlanTemplate> lptList = lessonPlanTemplateDao.getTmplateListByOrg(orgId);
		return lptList;
	}

	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<LessonPlanTemplate, Integer> getDAO() {
		return lessonPlanTemplateDao;
	}

	/**
	 * 获取机构下的默认模板
	 * @param orgId
	 * @return
	 * @see com.tmser.tr.lessonplantemplate.service.LessonPlanTemplateService#getDefaultTemplateByOrgId(java.lang.Integer)
	 */
	@Override
	public LessonPlanTemplate getDefaultTemplateByOrgId(Integer orgId) {
		LessonPlanTemplate lessonPlanTemplate = lessonPlanTemplateDao.getDefaultTemplateByOrgId(orgId);
		return lessonPlanTemplate;
	}

	/**
	 * 根据id获取模板
	 * @param tpId
	 * @return
	 * @see com.tmser.tr.lessonplantemplate.service.LessonPlanTemplateService#getTemplateById(java.lang.Integer)
	 */
	@Override
	public LessonPlanTemplate getTemplateById(Integer tpId) {
		LessonPlanTemplate lessonPlanTemplate = lessonPlanTemplateDao.getTemplateById(tpId);
		return lessonPlanTemplate;
	}

}
