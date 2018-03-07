/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.operation.vo;

/**
 * <pre>
 * 老师运营情况VO
 * </pre>
 * 
 * @author daweiwbs
 * @version $Id: OrgOperationInfoVo.java, v 1.0 2015年10月30日 上午11:12:40 daweiwbs
 *          Exp $
 */
public class TeacherOperationInfoVo {

  /**
   * 教师id
   */
  public Integer userId;

  /**
   * 教师姓名
   */
  public String userName;

  /**
   * 是否被冻结
   */
  public String status;

  /**
   * 撰写教案总数
   */
  public Integer lessonPlanCount;

  private Integer lessonCount;

  /**
   * 上传课件总数
   */
  public Integer kejianCount;

  private Integer kejianLesson;

  /**
   * 上传反思总数
   */
  public Integer fansiCount;

  private Integer fansiLesson;

  /**
   * 听课记录节数
   */
  public Integer listenCount;

  /**
   * 教学文章数
   */
  public Integer teachTextCount;

  /**
   * 计划总结数
   */
  public Integer planSummaryCount;

  /**
   * 集体备课参与次数
   */
  public Integer activityJoinCount;

  /**
   * 集体备课讨论数
   */
  public Integer activityDiscussCount;

  /**
   * 集体备课任主备人次数
   */
  public Integer activityMainCount;

  /**
   * 校际教研参与次数
   */
  public Integer schoolActivityJoinCount;

  /**
   * 校际教研讨论数
   */
  public Integer schoolActivityDiscussCount;

  /**
   * 校际教研任主备人次数
   */
  public Integer schoolActivityMainCount;

  /**
   * 区域教研参与次数
   */
  public Integer regionActivityJoinCount;

  /**
   * 区域教研讨论数
   */
  public Integer regionActivityDiscussCount;

  /**
   * 同伴互助留言数
   */
  public Integer peerMessageCount;

  /**
   * 成长档案袋精选资源数
   */
  public Integer progressResCount;

  /**
   * 分享发表总数
   */
  public Integer shareCount;

  /**
   * 资源总数
   */
  public Integer resTotalCount;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Integer getLessonPlanCount() {
    return lessonPlanCount;
  }

  public void setLessonPlanCount(Integer lessonPlanCount) {
    this.lessonPlanCount = lessonPlanCount;
  }

  public Integer getLessonCount() {
    return lessonCount;
  }

  public void setLessonCount(Integer lessonCount) {
    this.lessonCount = lessonCount;
  }

  public Integer getKejianCount() {
    return kejianCount;
  }

  public void setKejianCount(Integer kejianCount) {
    this.kejianCount = kejianCount;
  }

  public Integer getKejianLesson() {
    return kejianLesson;
  }

  public void setKejianLesson(Integer kejianLesson) {
    this.kejianLesson = kejianLesson;
  }

  public Integer getFansiLesson() {
    return fansiLesson;
  }

  public void setFansiLesson(Integer fansiLesson) {
    this.fansiLesson = fansiLesson;
  }

  public Integer getFansiCount() {
    return fansiCount;
  }

  public void setFansiCount(Integer fansiCount) {
    this.fansiCount = fansiCount;
  }

  public Integer getListenCount() {
    return listenCount;
  }

  public void setListenCount(Integer listenCount) {
    this.listenCount = listenCount;
  }

  public Integer getTeachTextCount() {
    return teachTextCount;
  }

  public void setTeachTextCount(Integer teachTextCount) {
    this.teachTextCount = teachTextCount;
  }

  public Integer getPlanSummaryCount() {
    return planSummaryCount;
  }

  public void setPlanSummaryCount(Integer planSummaryCount) {
    this.planSummaryCount = planSummaryCount;
  }

  public Integer getActivityJoinCount() {
    return activityJoinCount;
  }

  public void setActivityJoinCount(Integer activityJoinCount) {
    this.activityJoinCount = activityJoinCount;
  }

  public Integer getActivityDiscussCount() {
    return activityDiscussCount;
  }

  public void setActivityDiscussCount(Integer activityDiscussCount) {
    this.activityDiscussCount = activityDiscussCount;
  }

  public Integer getActivityMainCount() {
    return activityMainCount;
  }

  public void setActivityMainCount(Integer activityMainCount) {
    this.activityMainCount = activityMainCount;
  }

  public Integer getSchoolActivityJoinCount() {
    return schoolActivityJoinCount;
  }

  public void setSchoolActivityJoinCount(Integer schoolActivityJoinCount) {
    this.schoolActivityJoinCount = schoolActivityJoinCount;
  }

  public Integer getSchoolActivityDiscussCount() {
    return schoolActivityDiscussCount;
  }

  public void setSchoolActivityDiscussCount(Integer schoolActivityDiscussCount) {
    this.schoolActivityDiscussCount = schoolActivityDiscussCount;
  }

  public Integer getSchoolActivityMainCount() {
    return schoolActivityMainCount;
  }

  public void setSchoolActivityMainCount(Integer schoolActivityMainCount) {
    this.schoolActivityMainCount = schoolActivityMainCount;
  }

  public Integer getRegionActivityJoinCount() {
    return regionActivityJoinCount;
  }

  public void setRegionActivityJoinCount(Integer regionActivityJoinCount) {
    this.regionActivityJoinCount = regionActivityJoinCount;
  }

  public Integer getRegionActivityDiscussCount() {
    return regionActivityDiscussCount;
  }

  public void setRegionActivityDiscussCount(Integer regionActivityDiscussCount) {
    this.regionActivityDiscussCount = regionActivityDiscussCount;
  }

  public Integer getPeerMessageCount() {
    return peerMessageCount;
  }

  public void setPeerMessageCount(Integer peerMessageCount) {
    this.peerMessageCount = peerMessageCount;
  }

  public Integer getProgressResCount() {
    return progressResCount;
  }

  public void setProgressResCount(Integer progressResCount) {
    this.progressResCount = progressResCount;
  }

  public Integer getShareCount() {
    return shareCount;
  }

  public void setShareCount(Integer shareCount) {
    this.shareCount = shareCount;
  }

  public Integer getResTotalCount() {
    return resTotalCount;
  }

  public void setResTotalCount(Integer resTotalCount) {
    this.resTotalCount = resTotalCount;
  }

}
