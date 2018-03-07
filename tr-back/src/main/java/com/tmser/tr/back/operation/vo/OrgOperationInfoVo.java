/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.operation.vo;

/**
 * <pre>
 * 学校运营情况VO
 * </pre>
 * 
 * @author daweiwbs
 * @version $Id: OrgOperationInfoVo.java, v 1.0 2015年10月30日 上午11:12:40 daweiwbs
 *          Exp $
 */
public class OrgOperationInfoVo {

  /**
   * 机构id
   */
  public Integer orgId;

  /**
   * 机构名称
   */
  public String orgName;

  /**
   * 用户数
   */
  public Integer userCount;

  /**
   * 撰写教案总数
   */
  public Integer lessonPlanCount;

  /**
   * 撰写教案总课数
   */
  private Integer lessonPlanCountLesson;

  /**
   * 查阅总数
   */
  public Integer viewCount;

  /**
   * 分享总数
   */
  public Integer shareCount;

  /**
   * 集体备课发起数
   */
  public Integer activityPushCount;

  /**
   * 集体备课参与次数
   */
  public Integer activityJoinCount;

  /**
   * 成长档案袋精选资源数
   */
  public Integer progressResCount;

  /**
   * 同伴互助留言数
   */
  public Integer peerMessageCount;

  /**
   * 资源总数
   */
  public Integer resTotalCount;

  /**
   * 人均资源数
   */
  public Double perResCount;

  public Integer getOrgId() {
    return orgId;
  }

  public void setOrgId(Integer orgId) {
    this.orgId = orgId;
  }

  public String getOrgName() {
    return orgName;
  }

  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }

  public Integer getUserCount() {
    return userCount;
  }

  public void setUserCount(Integer userCount) {
    this.userCount = userCount;
  }

  public Integer getLessonPlanCount() {
    return lessonPlanCount;
  }

  public void setLessonPlanCount(Integer lessonPlanCount) {
    this.lessonPlanCount = lessonPlanCount;
  }

  public Integer getLessonPlanCountLesson() {
    return lessonPlanCountLesson;
  }

  public void setLessonPlanCountLesson(Integer lessonPlanCountLesson) {
    this.lessonPlanCountLesson = lessonPlanCountLesson;
  }

  public Integer getViewCount() {
    return viewCount;
  }

  public void setViewCount(Integer viewCount) {
    this.viewCount = viewCount;
  }

  public Integer getShareCount() {
    return shareCount;
  }

  public void setShareCount(Integer shareCount) {
    this.shareCount = shareCount;
  }

  public Integer getActivityPushCount() {
    return activityPushCount;
  }

  public void setActivityPushCount(Integer activityPushCount) {
    this.activityPushCount = activityPushCount;
  }

  public Integer getActivityJoinCount() {
    return activityJoinCount;
  }

  public void setActivityJoinCount(Integer activityJoinCount) {
    this.activityJoinCount = activityJoinCount;
  }

  public Integer getProgressResCount() {
    return progressResCount;
  }

  public void setProgressResCount(Integer progressResCount) {
    this.progressResCount = progressResCount;
  }

  public Integer getPeerMessageCount() {
    return peerMessageCount;
  }

  public void setPeerMessageCount(Integer peerMessageCount) {
    this.peerMessageCount = peerMessageCount;
  }

  public Integer getResTotalCount() {
    return resTotalCount;
  }

  public void setResTotalCount(Integer resTotalCount) {
    this.resTotalCount = resTotalCount;
  }

  public Double getPerResCount() {
    return perResCount;
  }

  public void setPerResCount(Double perResCount) {
    this.perResCount = perResCount;
  }

}
