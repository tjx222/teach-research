package com.tmser.tr.lessonplan.dao;

import java.util.List;
import java.util.Map;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.manage.meta.bo.Book;

/**
 * 备课资源表DAO接口
 * @author wangdawei
 * @version 1.0
 * 2015-02-03
 */
public interface LessonPlanDao extends BaseDAO<LessonPlan, Integer>{

	/**
	 * 获取同伴资源
	 * @param lessonPlan
	 * @return
	 */
	PageList<LessonPlan> getPeerResource(LessonPlan lessonPlan);

	/**
	 * 获取最新的备课资源
	 * @param book 
	 * @param userId
	 * @param string 
	 * @param subjectId
	 * @param planType 资源类型
	 * @return
	 */
	LessonPlan getLatestLessonPlan(Book book, Integer userId, Integer subjectId, Integer schoolYear,Integer planType);

	/**
	 * 逻辑删除某课题下的教案
	 * @param id
	 */
	void enableLessonPlan(Integer infoId);
	
	/**
	 * 按模型查询统计
	 * @param model
	 * @return map 包含 userid -- cnt (统计数)
	 */
	List<Map<String,Object>> countLessonPlanGroupByUser(LessonPlan model);

}