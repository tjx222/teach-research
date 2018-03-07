package com.tmser.tr.api.writelessonplan.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.tmser.tr.common.vo.Result;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.resources.bo.ResRecommend;

/**
 * 移动离线端-备课资源表 服务实现类
 * 
 * @author
 * @version 1.0 2016-04-15
 */

public interface LessonPlanApiService {

	/**
	 * 保存教案
	 * 
	 * @param orgId
	 * @return
	 */
	Result saveLessonPlan(String lessonPlan, MultipartFile file);

	/**
	 * 修改教案
	 * 
	 * @param orgId
	 * @return
	 */
	Result updateLessonPlan(String lessonPlan, MultipartFile file);

	/**
	 * 新增课题信息
	 * 
	 * @param lessonId
	 * @param book
	 * @return
	 */
	LessonInfo saveLessonInfo(LessonPlan lp, Book book);

	/**
	 * 根据课题id获取已写过教案的课时id连成的字符串
	 */
	String getHoursStrOfWritedLessonById(LessonPlan lessonPlan);

	/**
	 * 获取模板集合，包含系统自带的
	 * 
	 * @param orgId
	 * @return
	 */
	List<Map<String, Object>> getTemplateListByOrg(Integer orgId);

	/**
	 * 获取推送资源
	 * 
	 * @param rr
	 * @return
	 */
	List<ResRecommend> findCommendResource(ResRecommend rr);

	/**
	 * 获取同伴资源
	 * 
	 * @param spaceId
	 * @param lessonId
	 * @param planType
	 * @return
	 */
	List<LessonPlan> getPeerResource(Integer spaceId, String lessonId, Integer planType);
}
