package com.tmser.tr.lessonplantemplate.dao;

import java.util.List;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.lessonplantemplate.bo.LessonPlanTemplate;

/**
 * 教案模板DAO接口
 * @author wangdawei
 * @version 1.0
 * 2015-01-28
 */
public interface LessonPlanTemplateDao extends BaseDAO<LessonPlanTemplate, Integer>{

	/**
	 * 获取模板集合，包含系统自带的
	 * @param orgId
	 * @return
	 */
	List<LessonPlanTemplate> getTmplateListByOrg(Integer orgId);

	/**
	 * 获取机构下的默认模板
	 * @param orgId
	 * @return
	 */
	LessonPlanTemplate getDefaultTemplateByOrgId(Integer orgId);

	/**
	 * 通过id获取模板
	 * @param tpId
	 * @return
	 */
	LessonPlanTemplate getTemplateById(Integer tpId);

}