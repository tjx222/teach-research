/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.operationmanage.dao;

import com.tmser.tr.back.operationmanage.vo.SearchVo;

/**
 * <pre>
 * 后台运营管理dao
 * </pre>
 *
 * @author daweiwbs
 * @version $Id: OperationManageDao.java, v 1.0 2015年11月5日 上午10:17:22 daweiwbs
 *          Exp $
 */
public interface OperationManageDao {

  /**
   * 获取用户数
   * 
   * @param orgId
   * @param search
   * @return
   */
  Integer getUserCount(SearchVo search);

  /**
   * 获取撰写教案总数
   * 
   * @param orgId
   * @param search
   * @return
   */
  Integer getLessonPlanCount(SearchVo search);

  /**
   * 获取查阅数
   * 
   * @param orgId
   * @param search
   * @return
   */
  Integer getViewCount(SearchVo search);

  /**
   * 获取分享发表数
   * 
   * @param orgId
   * @param search
   * @return
   */
  Integer getShareCount(SearchVo search);

  /**
   * 获取集体备课发起数
   * 
   * @param orgId
   * @param search
   * @return
   */
  Integer getActivityPushCount(SearchVo search);

  /**
   * 获取集体备课的参与次数
   * 
   * @param orgId
   * @param search
   * @return
   */
  Integer getActivityJoinCount(SearchVo search);

  /**
   * 获取成长档案精品资源数
   * 
   * @param orgId
   * @param search
   * @return
   */
  Integer getProgressResCount(SearchVo search);

  /**
   * 同伴互助留言数
   * 
   * @param orgId
   * @param search
   * @return
   */
  Integer getPeerMessageCount(SearchVo search);

  /**
   * 获取资源总数
   * 
   * @param orgId
   * @param search
   * @return
   */
  Integer getResTotalCount(SearchVo search);

  /**
   * 教案总数（老师）
   * 
   * @param userId
   * @param search
   * @return
   */
  Integer getLessonPlanCount_teacher(SearchVo search);

  /**
   * 课件总数（老师）
   * 
   * @param userId
   * @param search
   * @return
   */
  Integer getKejianCount_teacher(SearchVo search);

  /**
   * 反思总数（老师）
   * 
   * @param userId
   * @param search
   * @return
   */
  Integer getFansiCount_teacher(SearchVo search);

  /**
   * 听课记录节数（老师）
   * 
   * @param userId
   * @param search
   * @return
   */
  Integer getListenCount_teacher(SearchVo search);

  /**
   * 教学文章篇数（老师）
   * 
   * @param userId
   * @param search
   * @return
   */
  Integer getTeachTextCount_teacher(SearchVo search);

  /**
   * 计划总结总数（老师）
   * 
   * @param userId
   * @param search
   * @return
   */
  Integer getPlanSummaryCount_teacher(SearchVo search);

  /**
   * 集体备课参与次数（老师）
   * 
   * @param userId
   * @param search
   * @return
   */
  Integer getActivityJoinCount_teacher(SearchVo search);

  /**
   * 集体备课讨论数（老师）
   * 
   * @param userId
   * @param search
   * @return
   */
  Integer getActivityDiscussCount_teacher(SearchVo search);

  /**
   * 集体备课任主备人次数（老师）
   * 
   * @param userId
   * @param search
   * @return
   */
  Integer getActivityMainCount_teacher(SearchVo search);

  /**
   * 校际教研参与次数（老师）
   * 
   * @param userId
   * @param search
   * @return
   */
  Integer getSchoolActivityJoinCount_teacher(SearchVo search);

  /**
   * 校际教研讨论数（老师）
   * 
   * @param userId
   * @param search
   * @return
   */
  Integer getSchoolActivityDiscussCount_teacher(SearchVo search);

  /**
   * 校际教研任主备人次数（老师）
   * 
   * @param userId
   * @param search
   * @return
   */
  Integer getSchoolActivityMainCount_teacher(SearchVo search);

  /**
   * 同伴互助留言数（老师）
   * 
   * @param userId
   * @param search
   * @return
   */
  Integer getPeerMessageCount_teacher(SearchVo search);

  /**
   * 成长档案袋资源数（老师）
   * 
   * @param userId
   * @param search
   * @return
   */
  Integer getProgressResCount_teacher(SearchVo search);

  /**
   * 分享发表数（老师）
   * 
   * @param userId
   * @param search
   * @return
   */
  Integer getShareCount_teacher(SearchVo search);

  /**
   * 查阅教案数（管理者）
   * 
   * @param userId
   * @param search
   * @return
   */
  Integer getViewLessonPlanCount_leader(SearchVo search);

  /**
   * 查阅课件数（管理者）
   * 
   * @param userId
   * @param search
   * @return
   */
  Integer getViewKejianCount_leader(SearchVo search);

  /**
   * 查阅反思数（管理者）
   * 
   * @param userId
   * @param search
   * @return
   */
  Integer getViewFansiCount_leader(SearchVo search);

  /**
   * 查阅计划总结数（管理者）
   * 
   * @param userId
   * @param search
   * @return
   */
  Integer getViewPlanSummaryCount_leader(SearchVo search);

  /**
   * 听课记录数（管理者）
   * 
   * @param userId
   * @param search
   * @return
   */
  Integer getListenCount_leader(SearchVo search);

  /**
   * 教学文章数（管理者）
   * 
   * @param userId
   * @param search
   * @return
   */
  Integer getTeachTextCount_leader(SearchVo search);

  /**
   * 计划总结数（管理者）
   * 
   * @param userId
   * @param search
   * @return
   */
  Integer getPlanSummaryCount_leader(SearchVo search);

  /**
   * 集体备课发布数（管理者）
   * 
   * @param userId
   * @param search
   * @return
   */
  Integer getActivityPushCount_leader(SearchVo search);

  /**
   * 集体备课参与数（管理者）
   * 
   * @param userId
   * @param search
   * @return
   */
  Integer getActivityJoinCount_leader(SearchVo search);

  /**
   * 集体备课讨论数（管理者）
   * 
   * @param userId
   * @param search
   * @return
   */
  Integer getActivityDiscussCount_leader(SearchVo search);

  /**
   * 集体备课查阅数（管理者）
   * 
   * @param userId
   * @param search
   * @return
   */
  Integer getActivityViewCount_leader(SearchVo search);

  /**
   * 校际教研发布数（管理者）
   * 
   * @param userId
   * @param search
   * @return
   */
  Integer getSchoolActivityPushCount_leader(SearchVo search);

  /**
   * 校际教研参与数（管理者）
   * 
   * @param userId
   * @param search
   * @return
   */
  Integer getSchoolActivityJoinCount_leader(SearchVo search);

  /**
   * 校际教研讨论数（管理者）
   * 
   * @param userId
   * @param search
   * @return
   */
  Integer getSchoolActivityDiscussCount_leader(SearchVo search);

  /**
   * 同伴互助留言数（管理者）
   * 
   * @param userId
   * @param search
   * @return
   */
  Integer getPeerMessageCount_leader(SearchVo search);

  /**
   * 分享发布篇数（管理者）
   * 
   * @param userId
   * @param search
   * @return
   */
  Integer getShareCount_leader(SearchVo search);

  /**
   * 区域下用户总数
   * 
   * @param search
   * @return
   */
  Integer getUserTotalCountOfArea(SearchVo search);

  /**
   * 区域下撰写教案的总数
   * 
   * @param search
   * @return
   */
  Integer getLessonPlanTotalCountOfArea(SearchVo search);

  /**
   * 区域下的查阅总数
   * 
   * @param search
   * @return
   */
  Integer getViewTotalCountOfArea(SearchVo search);

  /**
   * 区域下分享发表总数
   * 
   * @param search
   * @return
   */
  Integer getShareTotalCountOfArea(SearchVo search);

  /**
   * 区域下集体备课的发表总数
   * 
   * @param search
   * @return
   */
  Integer getActivityPushTotalCountOfArea(SearchVo search);

  /**
   * 区域下集体备课的参与总数
   * 
   * @param search
   * @return
   */
  Integer getActivityJoinTotalCountOfArea(SearchVo search);

  /**
   * 区域下成长档案资源总数
   * 
   * @param search
   * @return
   */
  Integer getProgressResTotalCountOfArea(SearchVo search);

  /**
   * 区域下同伴互助留言总数
   * 
   * @param search
   * @return
   */
  Integer getPeerMessageTotalCountOfArea(SearchVo search);

  /**
   * 区域下资源总数
   * 
   * @param search
   * @return
   */
  Integer getResTotalCountOfArea(SearchVo search);

  /**
   * 学校下老师撰写教案总数
   * 
   * @param search
   * @return
   */
  Integer getLessonPlanTotalCount_teacher(SearchVo search);

  /**
   * 学校下老师上传课件总数
   * 
   * @param search
   * @return
   */
  Integer getKejianTotalCount_teacher(SearchVo search);

  /**
   * 学校下老师的反思总数
   * 
   * @param search
   * @return
   */
  Integer getFansiTotalCount_teacher(SearchVo search);

  /**
   * 学校下老师的听课记录总数
   * 
   * @param search
   * @return
   */
  Integer getListenTotalCount_teacher(SearchVo search);

  /**
   * 学校下老师的教学文章总数
   * 
   * @param search
   * @return
   */
  Integer getTeachTextTotalCount_teacher(SearchVo search);

  /**
   * 学校下老师的计划总结总数
   * 
   * @param search
   * @return
   */
  Integer getPlanSummaryTotalCount_teacher(SearchVo search);

  /**
   * 学校下老师参与集体备课总数
   * 
   * @param search
   * @return
   */
  Integer getActivityJoinTotalCount_teacher(SearchVo search);

  Integer getActivityDiscussTotalCount_teacher(SearchVo search);

  Integer getActivityMainTotalCount_teacher(SearchVo search);

  Integer getSchoolActivityJoinTotalCount_teacher(SearchVo search);

  Integer getSchoolActivityDiscussTotalCount_teacher(SearchVo search);

  Integer getSchoolActivityMainTotalCount_teacher(SearchVo search);

  Integer getPeerMessageTotalCount_teacher(SearchVo search);

  Integer getProgressResTotalCount_teacher(SearchVo search);

  Integer getShareTotalCount_teacher(SearchVo search);

  Integer getViewLessonPlanTotalCount_leader(SearchVo search);

  Integer getViewKejianTotalCount_leader(SearchVo search);

  Integer getViewFansiTotalCount_leader(SearchVo search);

  Integer getViewPlanSummaryTotalCount_leader(SearchVo search);

  Integer getListenTotalCount_leader(SearchVo search);

  Integer getTeachTextTotalCount_leader(SearchVo search);

  Integer getPlanSummaryTotalCount_leader(SearchVo search);

  Integer getActivityPushTotalCount_leader(SearchVo search);

  Integer getActivityJoinTotalCount_leader(SearchVo search);

  Integer getActivityDiscussTotalCount_leader(SearchVo search);

  Integer getActivityViewTotalCount_leader(SearchVo search);

  Integer getSchoolActivityPushTotalCount_leader(SearchVo search);

  Integer getSchoolActivityJoinTotalCount_leader(SearchVo search);

  Integer getSchoolActivityDiscussTotalCount_leader(SearchVo search);

  Integer getPeerMessageTotalCount_leader(SearchVo search);

  Integer getShareTotalCount_leader(SearchVo search);

  Integer getLessonPlanCountLesson(SearchVo search);

  Integer getLessonCount_teacher(SearchVo search);

  Integer getKejianLesson_teacher(SearchVo search);

  Integer getFansiLesson_teacher(SearchVo search);

  Integer getLessonTotalCount_teacher(SearchVo search);

  Integer getKejianTotalLesson_teacher(SearchVo search);

  Integer getFansiTotalLesson_teacher(SearchVo search);

  Integer getLessonPlanTotalCountLessonOfArea(SearchVo search);

}
