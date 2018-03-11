package com.tmser.tr.lessonplan.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.dao.AbstractDAO;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.lessonplan.dao.LessonPlanDao;

/**
 * 备课资源表 Dao 实现类
 * @author wangdawei
 * @version 1.0
 * 2015-02-03
 */
@Repository
public class LessonPlanDaoImpl extends AbstractDAO<LessonPlan,Integer> implements LessonPlanDao {

	/**
	 * 获取同伴资源
	 * @param lessonPlan
	 * @return
	 * @see com.tmser.tr.lessonplan.dao.LessonPlanDao#getPeerResource(com.tmser.tr.lessonplan.bo.LessonPlan)
	 */
	@Override
	public PageList<LessonPlan> getPeerResource(LessonPlan lessonPlan) {
		String sql = "select a.planId,a.planName,a.planType,a.lessonId,a.resId,a.orgId,a.downNum,b.name userName,b.orgName from LessonPlan a,User b,Organization o "+
					 "where a.userId = b.id and a.orgId = o.id and o.orgType = ? and o.enable = 1 and a.gradeId = ? and a.subjectId = ? and a.isShare = ? and a.userId != ? and a.planType = ? and a.lessonId = ? and a.enable = 1 order by a.downNum desc";
		Object[] args = new Object[]{lessonPlan.getOrgId(),lessonPlan.getGradeId(),lessonPlan.getSubjectId(),1,lessonPlan.getUserId(),lessonPlan.getPlanType(),lessonPlan.getLessonId()};
		RowMapper<LessonPlan> rowMapper = new BeanPropertyRowMapper<LessonPlan>(LessonPlan.class);
		return queryPage(sql, args, rowMapper, lessonPlan.getPage());
	}

	/**
	 * 获取最新的备课资源
	 * @param userId
	 * @param subjectId
	 * @param planType 资源类型
	 * @return
	 * @see com.tmser.tr.lessonplan.dao.LessonPlanDao#getLatestLessonPlan(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public LessonPlan getLatestLessonPlan(Integer userId, Integer schoolYear,Integer planType) {
		String sql ="";
		Object[] args = null;
		if(planType!=null){
			sql = "select * from LessonPlan where userId = ? and schoolYear = ? and planType = ? and enable = 1 order by crtDttm desc limit 0,1";
			args = new Object[]{userId,schoolYear,planType};
		}else{
			sql = "select * from LessonPlan where  userId = ? and schoolYear = ? and planType != ? and enable = 1 order by crtDttm desc limit 0,1";
			args = new Object[]{userId,schoolYear,ResTypeConstants.FANSI_OTHER};
		}
		RowMapper<LessonPlan> rowMapper = getMapper();
		LessonPlan lessonPlan = queryForSingle(sql, args, rowMapper);
		return lessonPlan;
	}

	/**
	 * 逻辑删除某课题下的教案
	 * @param id
	 * @see com.tmser.tr.lessonplan.dao.LessonPlanDao#enableLessonPlan(java.lang.Integer)
	 */
	@Override
	public void enableLessonPlan(Integer infoId) {
		String sql = "update LessonPlan set enable=0,isSubmit=0,isScan=0,scanUp=0 where infoId = ? and planType = ?";
		Object[] args = new Object[]{infoId,LessonPlan.JIAO_AN};
		update(sql, args);
	}

	/**
	 * @param model
	 * @return
	 * @see com.tmser.tr.lessonplan.dao.LessonPlanDao#countLessonPlanGroupByUser(com.tmser.tr.lessonplan.bo.LessonPlan)
	 */
	@Override
	public List<Map<String, Object>> countLessonPlanGroupByUser(LessonPlan model) {
		StringBuilder sql = new StringBuilder();
		List<Object> argList = new ArrayList<Object>();
		
		sql.append("select ").append(model.alias())
		   .append(".userId userId, count(").append(model.alias())
		   .append(".planId) cnt from LessonPlan ").append(model.alias())
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
		
		if(model.getIsSubmit() != null){
			sql.append("and ").append(model.alias())
			.append(".isSubmit = ? ");
			argList.add(model.getIsSubmit());
		}
		
		if(model.getIsScan() != null){
			sql.append("and ").append(model.alias())
			.append(".isScan = ? ");
			argList.add(model.getIsScan());
		}
		
		if(model.getPlanType() != null){
			sql.append("and ").append(model.alias())
		   .append(".planType = ? ");
			argList.add(model.getPlanType());
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
