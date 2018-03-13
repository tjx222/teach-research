/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.lessonplan.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.zhuozhengsoft.pageoffice.FileSaver;

/**
 * 教案相关功能操作Service接口类
 * 
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: LessonPlanService.java, v 1.0 2015年2月10日 下午2:52:00 tmser Exp $
 */
public interface LessonPlanService extends BaseService<LessonPlan, Integer> {

  PageList<LessonPlan> findValidPlanList(LessonPlan lp);

  /**
   * 获得当前登陆用户、当前学年的所有有效的教案
   * 
   * @return
   */
  List<LessonPlan> findAllPlanList();

  /**
   * 删除教案
   * 
   * @param lp
   * @return
   */
  Boolean deleteLessonPlan(LessonPlan lp);

  /**
   * 分享教案
   * 
   * @param lp
   * @return
   */
  Boolean sharingLessonPlan(LessonPlan lp);

  /**
   * 提交教案之前的数据查询展示
   * 
   * @param isSubmit
   * @return
   */
  Map<String, Object> getSubmitData(Integer isSubmit);

  /**
   * 提交或者取消提交教案
   * 
   * @param isSubmit
   * @param planIds
   * @return
   */
  Boolean submitLessonPlan(String isSubmit, String planIds);

  /**
   * 保存教案
   * 
   * @param fs
   * @return
   */
  Integer saveLessonPlan(FileSaver fs);

  public Map<String, Object> toEditLessonPlanRemote(String lessonid, String username);

  /**
   * 平台对接保存教案
   * 
   * @param lessonid
   * @param username
   * @param content
   * @return
   * @throws IOException
   */
  Result saveLessonPlanRemote(String lessonid, String username, String content) throws IOException;

  /**
   * 根据课题id获取已写过教案的课时id连成的字符串
   * 
   * @param lessonPlan
   * @return
   */
  String getHoursStrOfWritedLessonById(LessonPlan lessonPlan);

  /**
   * 获取同伴资源
   * 
   * @param lessonPlan
   * @return
   */
  PageList<LessonPlan> getPeerResource(LessonPlan lessonPlan);

  /**
   * 获取最新的备课资源
   * 
   * @param book1
   *          当前所教的书
   * @return
   */
  LessonPlan getLatestLessonPlan(Integer planType);

  /**
   * 通过id获取备课资源
   * 
   * @param planId
   * @return
   */
  LessonPlan getLessonPlanById(Integer planId);

  /**
   * 获取书下备课资源统计数据
   * 
   * @param bookId
   * @return
   */
  Map<String, Integer> getCountDataOfPlanForBook(String bookId);

  /**
   * 获取课题下的备课资源统计数据
   * 
   * @param lessonId
   * @return
   */
  Map<String, Integer> getCountDataOfPlanForLesson(String lessonId);

  /**
   * 获取课题下的教案资源集合
   * 
   * @param lessonId
   * @return
   */
  List<LessonPlan> getLessonPlanListForLesson(String lessonId);

  /**
   * 获取课题下的课件资源集合
   * 
   * @param lessonId
   * @return
   */
  List<LessonPlan> getKeJianListForLesson(String lessonId);

  /**
   * 获取课题下的其他反思资源集合
   * 
   * @param lessonId
   * @return
   */
  List<LessonPlan> getFanSiListForLesson(String lessonId);

  /**
   * 修改教案
   * 
   * @param fs
   * @return
   */
  Boolean editLessonPlan(FileSaver fs);

  /**
   * 获取课题下的教案集合
   * 
   * @param infoId
   * @return
   */
  List<LessonPlan> getJiaoanByInfoId(Integer infoId);

  /**
   * 根据教案类型将查阅意见的状态更新为已查看（查阅意见已更新置为0）
   * 
   * @param lessonPlan
   */
  void setScanListAlreadyShowByType(LessonPlan lessonPlan);

  /**
   * 平台对接课件
   * 
   * @param loginname
   * @param lessonid
   * @param path
   * @return
   */
  Integer abutmentTeachingRemote(String loginName, String lessonId, String path, String bookId, String chapterName);

  /**
   * 对接删除课件
   * 
   * @param url
   * @param loginName
   * @return
   */
  Result deleabutmentTeachingPlan(String url, String loginName);

  /**
   * 平台对接课件时，验证课本，章节
   * 
   * @param bookId
   * @param lessonId
   * @param loginName
   *          第三方登陆码
   * @return
   */
  Result validateLessonIdBookId(String bookId, String lessonId, String loginName);

  /**
   * @return
   */
  Integer saveLessonPlanWithFile(LessonPlan params, String resId);

  /**
   * 
   * @param resId
   * @param planId
   * @return
   */
  Result updateLessonPlanWithResId(String resId, Integer planId);

  /**
   * @param lp
   * @param spaceId
   * @return
   */
  String filterCurrentBook(LessonPlan lp, Integer spaceId);

}
