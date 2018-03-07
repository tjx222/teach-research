/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.thesis.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tmser.tr.common.dao.AbstractDAO;
import com.tmser.tr.thesis.bo.Thesis;
import com.tmser.tr.thesis.dao.ThesisDao;
import com.tmser.tr.uc.bo.UserSpace;

/**
 * 上传教学论文 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Thesis.java, v 1.0 2015-03-12 Generate Tools Exp $
 */
@Repository
public class ThesisDaoImpl extends AbstractDAO<Thesis,Integer> implements ThesisDao {

	/**
	 * 
	 * @param model
	 * @param subjectId
	 * @param gradeId
	 * @param sysRoleId
	 * @return
	 * @see com.tmser.tr.thesis.dao.ThesisDao#countThesisGroupByAuth(com.tmser.tr.thesis.bo.Thesis, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Map<String,Object>> countThesisGroupByAuth(Thesis model,Integer subjectId,Integer gradeId,Integer sysRoleId) {
		StringBuilder sqlJoin = new StringBuilder(" join UserSpace u on u.userId = t.userId");
		StringBuilder sql = new StringBuilder("select count(DISTINCT t.id) totalNum ,t.userId userId from Thesis t");
		Map<String,Object> argMap = new HashMap<String, Object>();
		if(model != null){
			if(gradeId != null){
				sqlJoin.append(" and u.gradeId = :gradeId");
				argMap.put("gradeId", gradeId);
			}
			if(subjectId != null){
				sqlJoin.append(" and u.subjectId = :subjectId");
				argMap.put("subjectId", subjectId);
			}
			if(sysRoleId != null){
				sqlJoin.append(" and u.sysRoleId = :sysRoleId");
				argMap.put("sysRoleId", sysRoleId);
			}
			if(model.getSchoolYear() != null){
				sqlJoin.append(" and u.schoolYear = :schoolYear");
				argMap.put("schoolYear", model.getSchoolYear());
			}
			if(model.getOrgId() != null){
				sqlJoin.append(" and u.orgId = :orgId");
			}
			sqlJoin.append(" and u.enable = :enable");
			argMap.put("enable", UserSpace.ENABLE);
			sql.append(sqlJoin.toString());
			sql.append(" where 1=1");
			if(model.getUserId() != null){
				sql.append(" and t.userId = :userId");
				argMap.put("userId", model.getUserId());
			}
			if(model.getIsSubmit() != null){
				sql.append(" and t.isSubmit = :isSubmit");
				argMap.put("isSubmit", model.getIsSubmit());
			}
			if(model.getSchoolYear() != null){
				sql.append(" and t.schoolYear = :schoolYear");
				argMap.put("schoolYear", model.getSchoolYear());
			}
			if(model.getOrgId() != null){
				sql.append(" and t.orgId = :orgId");
				argMap.put("orgId", model.getOrgId());
			}
			if(model.getPhaseId() != null){
				sql.append(" and t.phaseId = :phaseId");
				argMap.put("phaseId", model.getPhaseId());
			}
			if(model.customCondition() != null){
				sql.append(model.customCondition().getConditon());
				Map<String, Object> paramMap = model.customCondition().getParamMap();
				if(paramMap != null){
					for (String param : paramMap.keySet()) {
						argMap.put(param, paramMap.get(param));
					}
				}
			}
			sql.append(" group by t.userId");
		}
		return this.queryByNamedSql(sql.toString(), argMap);
		
	}

}
