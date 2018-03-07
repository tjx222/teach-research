/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.lessonplan.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tmser.tr.common.dao.AbstractDAO;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.dao.LessonInfoDao;

/**
 * 课题信息表 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author wangdawei
 * @version $Id: LessonInfo.java, v 1.0 2015-03-05 wangdawei Exp $
 */
@Repository
public class LessonInfoDaoImpl extends AbstractDAO<LessonInfo,Integer> implements LessonInfoDao {

	/**
	 * @param model
	 * @return
	 * @see com.tmser.tr.lessonplan.dao.LessonInfoDao#countUncommitLesson(com.tmser.tr.lessonplan.bo.LessonInfo)
	 */
	@Override
	public List<Map<String, Object>> countLessonGroupByUser(LessonInfo model) {
		StringBuilder sql = new StringBuilder();
		List<Object> argList = new ArrayList<Object>();
		
		sql.append("select ").append(model.alias())
		   .append(".userId userId, count(").append(model.alias())
		   .append(".id) cnt from LessonInfo ").append(model.alias())
		   .append(" where 1=1 ");
		if(model.getGradeId() != null){
			sql.append("and ").append(model.alias())
		   .append(".gradeId = ? ");
			argList.add(model.getGradeId());
		}
		if(model.getSubjectId() != null){
			sql.append("and ").append(model.alias())
		   .append(".subjectId = ? ");
			argList.add(model.getSubjectId());
		}
		
		if(model.getOrgId() != null){
			sql.append("and ").append(model.alias())
		   .append(".orgId = ? ");
			argList.add(model.getOrgId());
		}
		
		if(model.getSchoolYear() != null){
			sql.append("and ").append(model.alias())
		   .append(".schoolYear = ? ");
			argList.add(model.getSchoolYear());
		}
		
		if(model.customCondition() != null){
			sql.append(model.customCondition().getConditon());
		}
		
		sql.append(" group by ").append(model.alias())
		   .append(".userId ");
		
		return this.query(sql.toString(), argList.toArray());
	}
}
