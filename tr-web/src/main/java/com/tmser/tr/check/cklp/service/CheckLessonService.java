/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.check.cklp.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.tmser.tr.check.bo.CheckInfo;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.bo.LessonPlan;

/**
 * <pre>
 *	按课题查阅的备课资源服务类
 * </pre>
 *
 * @author tmser
 * @version $Id: CheckLessonService.java, v 1.0 2015年3月14日 下午4:10:08 tmser Exp $
 */
public interface CheckLessonService {
	/**
	 * 查阅统计年级学科内所有可查阅教师资源信息统计
	 * @param grade
	 * @param subject
	 * @param type
	 * @return
	 */
	Map<Integer,List<Object>> listTchLessonStatics(Integer grade,Integer subject,Integer type);
	
	/**
	 * 按书及用户查询已提交的课题
	 * @param userid
	 * @param fasciculeId
	 * @return
	 */
	List<LessonInfo> listTchLessons(Integer type,Integer userid,Integer gradeId,Integer subjectId,Integer searchType,Integer termId,Integer fasciculeId);
	
	/**
	 * 统计用户撰写的课题数
	 * @param userid
	 * @param fasciculeId
	 * @return
	 */
	Integer countLessonInfo(Integer type,Integer userid,Integer gradeId,Integer subjectId,Integer searchType,Integer termId,Integer fasciculeId);
	
	/**
	 * 统计学期个人被查阅数
	 * @param type
	 * @param userid
	 * @param gradeId
	 * @param subjectId
	 * @param fasciculeId
	 * @return
	 */
	Integer countCheckLesson(Integer type,Integer userid,Integer gradeId,Integer subjectId,Integer searchType,Integer termId,Integer fasciculeId);

	/**
	 * 统计学期个人提交数
	 * @param type
	 * @param userid
	 * @param gradeId
	 * @param subjectId
	 * @param fasciculeId
	 * @return
	 */
	Integer countSubmitLesson(Integer type,Integer userid,Integer gradeId,Integer subjectId,Integer searchType,Integer termId,Integer fasciculeId);
	/**
	 * 获取当前用户教材册别
	 * @param type
	 * @param userId
	 * @return
	 */
	Integer findFasciculeId(Integer type,Integer gradeId,Integer subjectId, Integer userId);
	
	
	/**
	 * 查阅课题，获取课题信息及课题下包含的资源。
	 * @param type
	 * @param lesInfoId
	 * @param m
	 */
	void viewLesson(Integer type,Integer lesInfoId,Model m);
	
	/**
	 * 根据资源作者id 和资源类型查找查阅信息
	 * @param authorId
	 * @param type
	 * @return 资源id -- 查阅信息
	 */
	Map<Integer,CheckInfo> checkedLessonMap(Integer authorId,Integer gradeId,Integer subjectId,Integer type);

	/**
	 * @param type
	 * @param userId
	 * @param gradeId
	 * @param subjectId
	 * @param fasciculeId
	 * @return
	 */
	List<LessonPlan> listTchLessonsOther(Integer type, Integer userId,
			Integer gradeId, Integer subjectId, Integer searchType,Integer termId);

	/**
	 * @param type
	 * @param userId
	 * @param gradeId
	 * @param subjectId
	 * @param fasciculeId
	 * @return
	 */
	Integer countLessonOther(Integer type, Integer userId, Integer gradeId,
			Integer subjectId, Integer fasciculeId);

	/**
	 * @param type
	 * @param planId
	 * @param m
	 */
	void viewOtherLesson(Integer type, Integer planId, Model m);
	
	/**
	 * 查看课题
	 * @param type
	 * @param lesInfoId
	 * @param m
	 */
	void viewCheckedLesson(Integer type,Integer lesInfoId,Model m);

	/**
	 * 撰写篇数
	 * @param type
	 * @param userId
	 * @param grade
	 * @param subject
	 * @param searchType
	 * @param termId
	 * @param fasciculeId
	 * @return
	 */
	Object countLessonPlan(Integer type, Integer userId, Integer grade,
			Integer subject, Integer searchType, Integer termId,
			Integer fasciculeId,Boolean isSubmit,Boolean isScan);
	
	/**
	 * 已查阅篇数
	 * @param type
	 * @param userid
	 * @param gradeId
	 * @param subjectId
	 * @param searchType
	 * @param termId
	 * @param fasciculeId
	 * @return
	 */
	Integer countCheckLessonPlan(Integer type, Integer userid,
			Integer gradeId, Integer subjectId,Integer searchType,Integer termId, Integer fasciculeId);
}
