/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.lessonplan.service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.vo.BookLessonVo;
import com.tmser.tr.uc.bo.UserSpace;

/**
 * <pre>
 * 我的备课本service
 * </pre>
 *
 * @author wangdawei
 * @version $Id: MyPlanBookService.java, v 1.0 2015年3月5日 下午3:27:34 wangdawei Exp
 *          $
 */
public interface MyPlanBookService extends BaseService<LessonInfo, Integer> {

  /**
   * 新增课题信息
   * 
   * @param lessonId
   * @param book
   * @return
   */
  LessonInfo saveLessonInfo(String lessonId, Integer gradeId, Integer subjectId, String lessonName, Integer planType);

  /**
   * 获取有备课资源的课题集合
   * 
   * @param bookId
   * @return
   */
  List<BookLessonVo> getLessonListForMyPlanBook(String bookId);

  /**
   * 根据课题id获取课题的扩展信息
   * 
   * @param lessonId
   * @return
   */
  LessonInfo getLessonInfoByLessonId(String lessonId);

  /**
   * 分享备课资源
   * 
   * @param planId
   * @return
   * @throws Exception
   */
  void sharePlanOfLessonById(Integer planId);

  /**
   * 取消分享备课资源
   * 
   * @param planId
   * @return
   * @throws Exception
   */
  Boolean unSharePlanOfLessonById(Integer planId);

  /**
   * 删除一条备课资源 （已提交和已分享的禁止删除）
   * 
   * @param planId
   * @throws Exception
   */
  void deleteLessonPlanById(Integer planId);

  /**
   * 获取已提交或未提交的备课资源
   * 
   * @param bookId
   * @param isSubmit
   * @return
   */
  Map<String, Object> getIsOrNotSubmitLessonPlanByBookId(String bookId, Boolean isSubmit, Integer type);

  /**
   * 批量提交备课资源
   * 
   * @param lessonPlanIdsStr
   * @return
   * @throws Exception
   */
  void submitLessonPlansByIdStr(String lessonPlanIdsStr);

  /**
   * 取消提交备课资源
   * 
   * @param lessonPlanIdsStr
   * @return
   * @throws Exception
   */
  void unSubmitLessonPlansByIdStr(String lessonPlanIdsStr);

  /**
   * 单条取消提交备课资源
   * 
   * @param planId
   * @return
   */
  Integer unSubmitLessonPlansById(Integer planId);

  /**
   * 接收集体备课的整理教案
   * 
   * @param activityId
   * @param m
   * @throws FileNotFoundException
   */
  Model receiveLessonPlanOfActivity(Integer activityId, Model m) throws FileNotFoundException;

  LessonInfo saveLessonInfo(UserSpace userSpace, String lessonid, String lessonName, Integer planType, Book book,
      Integer schoolYear, Integer termId);

  /**
   * 获取提交本课题资源除了当前人的其他人列表
   * 
   * @param lessonInfo
   * @return
   */
  List<LessonPlan> lessonSubmitOthers(LessonInfo lessonInfo);

  /**
   * 获取资源目录树
   * 
   * @param bookId
   * @return
   */
  List<BookLessonVo> getLessonTreeMyPlanBook(String bookId);
}
