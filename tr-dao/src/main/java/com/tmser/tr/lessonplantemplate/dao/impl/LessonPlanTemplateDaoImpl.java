package com.tmser.tr.lessonplantemplate.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.tmser.tr.common.dao.AbstractDAO;
import com.tmser.tr.lessonplantemplate.bo.LessonPlanTemplate;
import com.tmser.tr.lessonplantemplate.dao.LessonPlanTemplateDao;

/**
 * 教案模板 Dao 实现类
 * @author wangdawei
 * @version 1.0
 * 2015-01-28
 */
@Repository
public class LessonPlanTemplateDaoImpl extends AbstractDAO<LessonPlanTemplate,Integer> implements LessonPlanTemplateDao {

	/**
	 * 获取模板集合，包含系统自带的
	 * @param orgId
	 * @return
	 * @see com.tmser.tr.lessonplantemplate.dao.LessonPlanTemplateDao#getTmplateListByOrg(java.lang.Integer)
	 */
	@Override
	public List<LessonPlanTemplate> getTmplateListByOrg(Integer orgId) {
		String sql = "select * from LessonPlanTemplate where enable = 1 and tpType = 0 or (tpType = 1 and orgId = ?) order by tpType desc, tpIsdefault desc, sort asc ";
		RowMapper<LessonPlanTemplate> rowMapper = getMapper();
		List<LessonPlanTemplate> lptList = query(sql, new Object[]{orgId}, rowMapper);
		return lptList;
	}

	/**
	 * 获取机构下的默认模板
	 * @param orgId
	 * @return
	 * @see com.tmser.tr.lessonplantemplate.dao.LessonPlanTemplateDao#getDefaultTemplateByOrgId(java.lang.Integer)
	 */
	@Override
	public LessonPlanTemplate getDefaultTemplateByOrgId(Integer orgId) {
		LessonPlanTemplate lessonPlanTemplate = null;
		String sql = "select a.tpId,a.tpName,a.orgId,a.resId,a.tpIsdefault,a.tpType from LessonPlanTemplate a where a.tpType = 0 or (a.tpType = 1 and a.orgId = ?) order by a.tpType desc, a.tpIsdefault desc,a.sort asc ";
		RowMapper<LessonPlanTemplate> rowMapper = new BeanPropertyRowMapper<LessonPlanTemplate>(LessonPlanTemplate.class);
		List<LessonPlanTemplate> list = queryWithLimit(sql,new Object[]{orgId}, rowMapper,1);
		if(list!=null && list.size()>0){
			lessonPlanTemplate =  list.get(0);
		}
		
		return lessonPlanTemplate;
	}

	/**
	 * 通过id获取模板
	 * @param tpId
	 * @return
	 * @see com.tmser.tr.lessonplantemplate.dao.LessonPlanTemplateDao#getTemplateById(java.lang.Integer)
	 */
	@Override
	public LessonPlanTemplate getTemplateById(Integer tpId) {
		String sql = "select a.tpId,a.tpName,a.orgId,a.resId,a.tpIsdefault,a.tpType from LessonPlanTemplate a where a.tpId = ?";
		RowMapper<LessonPlanTemplate> rowMapper = new BeanPropertyRowMapper<LessonPlanTemplate>(LessonPlanTemplate.class);
		LessonPlanTemplate lessonPlanTemplate = queryForSingle(sql,new Object[]{tpId}, rowMapper);
		return lessonPlanTemplate;
	}

}
