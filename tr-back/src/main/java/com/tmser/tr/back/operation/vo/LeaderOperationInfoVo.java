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
public class LeaderOperationInfoVo {

	/**
	 * 用户id
	 */
	public Integer userId;

	/**
	 * 用户姓名
	 */
	public String userName;

	/**
	 * 是否被冻结
	 */
	public String status;

	/**
	 * 职务名称
	 */
	public String roleName;

	/**
	 * 查阅教案总数
	 */
	public Integer viewLessonPlanCount;

	/**
	 * 查阅课件总数
	 */
	public Integer viewKejianCount;

	/**
	 * 查阅反思总数
	 */
	public Integer viewFansiCount;

	/**
	 * 查阅计划总结数
	 */
	public Integer viewPlanSummaryCount;

	/**
	 * 听课记录节数
	 */
	public Integer listenCount;

	/**
	 * 教学文章数
	 */
	public Integer teachTextCount;

	/**
	 * 撰写计划总结数
	 */
	public Integer planSummaryCount;

	/**
	 * 集体备课发起次数
	 */
	public Integer activityPushCount;

	/**
	 * 集体备课参与次数
	 */
	public Integer activityJoinCount;

	/**
	 * 集体备课讨论数
	 */
	public Integer activityDiscussCount;

	/**
	 * 集体备课查阅数
	 */
	public Integer activityViewCount;

	/**
	 * 校际教研发起数
	 */
	public Integer schoolActivityPushCount;

	/**
	 * 校际教研参与次数
	 */
	public Integer schoolActivityJoinCount;

	/**
	 * 校际教研讨论数
	 */
	public Integer schoolActivityDiscussCount;

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

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getViewLessonPlanCount() {
		return viewLessonPlanCount;
	}

	public void setViewLessonPlanCount(Integer viewLessonPlanCount) {
		this.viewLessonPlanCount = viewLessonPlanCount;
	}

	public Integer getViewKejianCount() {
		return viewKejianCount;
	}

	public void setViewKejianCount(Integer viewKejianCount) {
		this.viewKejianCount = viewKejianCount;
	}

	public Integer getViewFansiCount() {
		return viewFansiCount;
	}

	public void setViewFansiCount(Integer viewFansiCount) {
		this.viewFansiCount = viewFansiCount;
	}

	public Integer getViewPlanSummaryCount() {
		return viewPlanSummaryCount;
	}

	public void setViewPlanSummaryCount(Integer viewPlanSummaryCount) {
		this.viewPlanSummaryCount = viewPlanSummaryCount;
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

	public Integer getActivityDiscussCount() {
		return activityDiscussCount;
	}

	public void setActivityDiscussCount(Integer activityDiscussCount) {
		this.activityDiscussCount = activityDiscussCount;
	}

	public Integer getActivityViewCount() {
		return activityViewCount;
	}

	public void setActivityViewCount(Integer activityViewCount) {
		this.activityViewCount = activityViewCount;
	}

	public Integer getSchoolActivityPushCount() {
		return schoolActivityPushCount;
	}

	public void setSchoolActivityPushCount(Integer schoolActivityPushCount) {
		this.schoolActivityPushCount = schoolActivityPushCount;
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
