/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.api.myplanbook.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.lessonplan.bo.LessonPlan;

/**
 * <pre>
 *
 * </pre>
 *
 * @author huyanfang
 * @version $Id: LectureRecordsMapping.java, v 1.0 2016年4月22日 下午10:30:13
 *          huyanfang Exp $
 */
public class LessonPlanMapping extends LessonPlan {

	private static final long serialVersionUID = 6296053251070600275L;
	private String info_id;

	@Override
	@JSONField(name = "plan_id")
	public Integer getPlanId() {
		return super.getPlanId();
	}

	@Override
	@JSONField(name = "plan_name")
	public String getPlanName() {
		return super.getPlanName();
	}

	public String getInfo_id() {
		return this.info_id;
	}

	@Override
	@JSONField(name = "res_id")
	public String getResId() {
		return super.getResId();
	}

	@Override
	@JSONField(name = "plan_type")
	public Integer getPlanType() {
		return super.getPlanType();
	}

	@Override
	@JSONField(name = "digest")
	public String getDigest() {
		return super.getDigest();
	}

	@Override
	@JSONField(name = "user_id")
	public Integer getUserId() {
		return super.getUserId();
	}

	@Override
	@JSONField(name = "grade_id")
	public Integer getGradeId() {
		return super.getGradeId();
	}

	@Override
	@JSONField(name = "subject_id")
	public Integer getSubjectId() {
		return super.getSubjectId();
	}

	@Override
	@JSONField(name = "book_id")
	public String getBookId() {
		return super.getBookId();
	}

	@Override
	@JSONField(name = "book_shortname")
	public String getBookShortname() {
		return super.getBookShortname();
	}

	@Override
	@JSONField(name = "lesson_id")
	public String getLessonId() {
		return super.getLessonId();
	}

	@Override
	@JSONField(name = "hours_id")
	public String getHoursId() {
		return super.getHoursId();
	}

	@Override
	@JSONField(name = "fascicule_id")
	public Integer getFasciculeId() {
		return super.getFasciculeId();
	}

	@Override
	@JSONField(name = "tp_id")
	public Integer getTpId() {
		return super.getTpId();
	}

	@Override
	@JSONField(name = "school_year")
	public Integer getSchoolYear() {
		return super.getSchoolYear();
	}

	@Override
	@JSONField(name = "org_id")
	public Integer getOrgId() {
		return super.getOrgId();
	}

	@Override
	@JSONField(name = "term_id")
	public Integer getTermId() {
		return super.getTermId();
	}

	@Override
	@JSONField(name = "phase_id")
	public Integer getPhaseId() {
		return super.getPhaseId();
	}

	@Override
	@JSONField(name = "is_submit")
	public Boolean getIsSubmit() {
		return super.getIsSubmit();
	}

	@Override
	@JSONField(name = "submit_time")
	public Date getSubmitTime() {
		return super.getSubmitTime();
	}

	@Override
	@JSONField(name = "is_share")
	public Boolean getIsShare() {
		return super.getIsShare();
	}

	@Override
	@JSONField(name = "share_time")
	public Date getShareTime() {
		return super.getShareTime();
	}

	@Override
	@JSONField(name = "is_scan")
	public Boolean getIsScan() {
		return super.getIsScan();
	}

	@Override
	@JSONField(name = "scan_up")
	public Boolean getScanUp() {
		return super.getScanUp();
	}

	@Override
	@JSONField(name = "is_comment")
	public Boolean getIsComment() {
		return super.getIsComment();
	}

	@Override
	@JSONField(name = "comment_up")
	public Boolean getCommentUp() {
		return super.getCommentUp();
	}

	@Override
	@JSONField(name = "down_num")
	public Integer getDownNum() {
		return super.getDownNum();
	}

	@Override
	@JSONField(name = "order_value")
	public Integer getOrderValue() {
		return super.getOrderValue();
	}

	@Override
	@JSONField(name = "crt_id")
	public Integer getCrtId() {
		return super.getCrtId();
	}

	@Override
	@JSONField(name = "crt_dttm")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCrtDttm() {
		return super.getCrtDttm();
	}

	@Override
	@JSONField(name = "lastup_id")
	public Integer getLastupId() {
		return super.getLastupId();
	}

	@Override
	@JSONField(name = "lastup_dttm")
	public Date getLastupDttm() {
		return super.getLastupDttm();
	}

	@Override
	@JSONField(name = "enable")
	public Integer getEnable() {
		return super.getEnable();
	}

	@Override
	@JSONField(name = "client_id")
	public String getClientId() {
		return super.getClientId();
	}

	/**********
	 * 
	 * @return
	 * @see com.tmser.tr.common.bo.QueryObject#getPage()
	 */

	public void setInfo_id(String infoId) {
		this.info_id = infoId;
	}

	public void setSubmit_time(Date submitTime) {
		super.setSubmitTime(submitTime);
	}

	public void setShare_time(Date shareTime) {
		super.setShareTime(shareTime);
	}

	public void setDigest(String digest) {
		super.setDigest(digest);
	}

	public void setPlan_id(Integer planId) {
		super.setPlanId(planId);
	}

	public void setPlan_name(String planName) {
		super.setPlanName(planName);
	}

	public void setRes_id(String resId) {
		super.setResId(resId);
	}

	public void setPlan_type(Integer planType) {
		super.setPlanType(planType);
	}

	public void setUser_id(Integer userId) {
		super.setUserId(userId);
	}

	public void setSubject_id(Integer subjectId) {
		super.setSubjectId(subjectId);
	}

	public void setGrade_id(Integer gradeId) {
		super.setGradeId(gradeId);
	}

	public void setLesson_id(String lessonId) {
		super.setLessonId(lessonId);
	}

	public void setHours_id(String hoursId) {
		super.setHoursId(hoursId);
	}

	public void setTp_id(Integer tpId) {
		super.setTpId(tpId);
	}

	public void setOrg_id(Integer orgId) {
		super.setOrgId(orgId);
	}

	public void setSchool_year(Integer schoolYear) {
		super.setSchoolYear(schoolYear);
	}

	public void setDown_num(Integer downNum) {
		super.setDownNum(downNum);
	}

	public void setOrder_value(Integer orderValue) {
		super.setOrderValue(orderValue);
	}

	public void setBook_id(String bookId) {
		super.setBookId(bookId);
	}

	public void setBook_shortname(String bookShortname) {
		super.setBookShortname(bookShortname);
	}

	public void setComment_up(Boolean commentUp) {
		super.setCommentUp(commentUp);
	}

	public void setScan_up(Boolean scanUp) {
		super.setScanUp(scanUp);
	}

	public void setTerm_id(Integer termId) {
		super.setTermId(termId);
	}

	public void setPhase_id(Integer phaseId) {
		super.setPhaseId(phaseId);
	}

	public void setClient_id(String clientId) {
		super.setClientId(clientId);
	}

	public void setFascicule_id(Integer fasciculeId) {
		super.setFasciculeId(fasciculeId);
	}

	public void setIs_submit(Boolean isSubmit) {
		super.setIsSubmit(isSubmit);
	}

	public void setIs_share(Boolean isShare) {
		super.setIsShare(isShare);
	}

	public void setIs_comment(Boolean isComment) {
		super.setIsComment(isComment);
	}

	public void setIs_scan(Boolean isScan) {
		super.setIsScan(isScan);
	}

	public void setCrt_dttm(Date crtDttm) {
		super.setCrtDttm(crtDttm);
	}

	public void setLastup_dttm(Date lastupDttm) {
		super.setLastupDttm(lastupDttm);
	}

	public void setCrt_id(Integer crtId) {
		super.setCrtId(crtId);
	}

	public void setLastup_id(Integer lastupId) {
		super.setLastupId(lastupId);
	}

	public void setEnable(Integer enable) {
		super.setEnable(enable);
	}

	@Override
	@JSONField(serialize = false)
	public Page getPage() {
		return super.getPage();
	}
}
