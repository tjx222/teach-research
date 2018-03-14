/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.ui.Model;

import com.tmser.tr.activity.bo.Activity;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.uc.bo.UserSpace;

/**
 * 集体备课活动 服务类
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Activity.java, v 1.0 2015-03-06 Generate Tools Exp $
 */

public interface ActivityService extends BaseService<Activity, Integer> {

  PageList<Activity> findMyOrganizeActivityList(Activity activity);

  PageList<Activity> findOtherActivityList(Activity activity);

  boolean isLeader(Integer sysRoleId);

  List<Meta> findGradeList(Integer phaseId);

  List<Meta> findSubjectList(Integer phaseId);

  List<LessonInfo> findChapterList(Integer userId, Integer gradeId, Integer subjectId) throws ParseException;

  List<UserSpace> findMainUserList(UserSpace us);

  Activity saveActivityTbja(Activity activity) throws Exception;

  Activity saveActivityZtyt(Activity activity, String resIds);

  /**
   * 删除活动，级联删除活动相关的内容
   * 
   * @param activityId
   */
  void deleteActivity(Integer activityId);

  /**
   * 更新集体备课——同备教案
   * 
   * @param activity
   * @return
   */
  Activity updateActivityTbja(Activity activity, Model m) throws Exception;

  /**
   * 更新集体备课——主题研讨
   * 
   * @param activity
   * @return
   */
  Activity updateActivityZtyt(Activity activity, String resIds, Model m);

  /**
   * 讲整理教案发送给参与人
   * 
   * @param id
   */
  void sendToJoiner(Integer id);

  /**
   * 判断活动是否已结束，如果已结束则更改状态为“结束”
   * 
   * @param activity
   * @return
   */
  public boolean ifActivityIsOver(Activity activity);

  /**
   * 删除一条草稿
   * 
   * @param id
   */
  void deleteDraft(Integer id);

  /**
   * 通过学科年级查询用户
   * 
   * @param activityId
   * @return
   * @see com.tmser.tr.activity.service.ActivityService#findUserBySubjectAndGrade(java.lang.Integer)
   */
  List<UserSpace> findUserBySubjectAndGrade(Activity activity);
}
