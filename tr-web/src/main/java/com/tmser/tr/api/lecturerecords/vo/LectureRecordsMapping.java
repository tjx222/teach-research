/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.api.lecturerecords.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.lecturerecords.bo.LectureRecords;

/**
 * <pre>
 *
 * </pre>
 *
 * @author zpp
 * @version $Id: LectureRecordsMapping.java, v 1.0 2016年4月18日 下午4:30:13 zpp Exp
 *          $
 */
public class LectureRecordsMapping extends LectureRecords {

	private static final long serialVersionUID = 6296053251070600275L;

	@Override
	@JSONField(name = "lecture_id")
	public Integer getId() {
		return super.getId();
	}

	@Override
	@JSONField(name = "crt_dttm")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCrtDttm() {
		return super.getCrtDttm();
	}

	@Override
	@JSONField(name = "lastup_dttm")
	public Date getLastupDttm() {
		return super.getLastupDttm();
	}

	@Override
	@JSONField(name = "res_type")
	public Integer getResType() {
		return super.getResType();
	}

	@Override
	@JSONField(name = "topic_id")
	public Integer getTopicId() {
		return super.getTopicId();
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
	@JSONField(name = "teachingpeople_id")
	public Integer getTeachingpeopleId() {
		return super.getTeachingpeopleId();
	}

	@Override
	@JSONField(name = "teaching_people")
	public String getTeachingPeople() {
		return super.getTeachingPeople();
	}

	@Override
	@JSONField(name = "number_lectures")
	public Integer getNumberLectures() {
		return super.getNumberLectures();
	}

	@Override
	@JSONField(name = "lecturepeople_id")
	public Integer getLecturepeopleId() {
		return super.getLecturepeopleId();
	}

	@Override
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(name = "lecture_people")
	public String getLecturePeople() {
		return super.getLecturePeople();
	}

	@Override
	@JSONField(name = "lecture_time")
	public Date getLectureTime() {
		return super.getLectureTime();
	}

	@Override
	@JSONField(name = "evaluation_rank")
	public String getEvaluationRank() {
		return super.getEvaluationRank();
	}

	@Override
	@JSONField(name = "lecture_content")
	public String getLectureContent() {
		return super.getLectureContent();
	}

	@Override
	@JSONField(name = "lecture_address")
	public String getLectureAddress() {
		return super.getLectureAddress();
	}

	@Override
	@JSONField(name = "lecture_company")
	public String getLectureCompany() {
		return super.getLectureCompany();
	}

	@Override
	@JSONField(name = "is_share")
	public Integer getIsShare() {
		return super.getIsShare();
	}

	@Override
	@JSONField(name = "share_time")
	public Date getShareTime() {
		return super.getShareTime();
	}

	@Override
	@JSONField(name = "is_delete")
	public Boolean getIsDelete() {
		return super.getIsDelete();
	}

	@Override
	@JSONField(name = "is_epub")
	public Integer getIsEpub() {
		return super.getIsEpub();
	}

	@Override
	@JSONField(name = "epub_time")
	public Date getEpubTime() {
		return super.getEpubTime();
	}

	@Override
	@JSONField(name = "submit_time")
	public Integer getIsSubmit() {
		return super.getIsSubmit();
	}

	@Override
	@JSONField(name = "is_reply")
	public Integer getIsReply() {
		return super.getIsReply();
	}

	@Override
	@JSONField(name = "reply_up")
	public Integer getReplyUp() {
		return super.getReplyUp();
	}

	@Override
	@JSONField(name = "is_comment")
	public Integer getIsComment() {
		return super.getIsComment();
	}

	@Override
	@JSONField(name = "comment_up")
	public Integer getCommentUp() {
		return super.getCommentUp();
	}

	@Override
	@JSONField(name = "phase_id")
	public Integer getPhaseId() {
		return super.getPhaseId();
	}

	@Override
	@JSONField(name = "grade_subject")
	public String getGradeSubject() {
		return super.getGradeSubject();
	}

	@Override
	@JSONField(name = "client_id")
	public String getClientId() {
		return super.getClientId();
	}
	
	@Override
	@JSONField(name = "lesson_id")
	public String getLessonId() {
		return super.getLessonId();
	}
	/**********
	 * 
	 * @return
	 * @see com.tmser.tr.common.bo.QueryObject#getPage()
	 */

	public void setSchool_year(Integer schoolYear) {
		super.setSchoolYear(schoolYear);
	}

	public void setLecture_id(Integer id) {
		super.setId(id);
	}

	public void setGrade_id(Integer gradeId) {
		super.setGradeId(gradeId);
	}

	public void setSubject_id(Integer subjectId) {
		super.setSubjectId(subjectId);
	}

	public void setTeachingpeople_id(Integer teachingpeopleId) {
		super.setTeachingpeopleId(teachingpeopleId);
	}

	public void setTeaching_people(String teachingPeople) {
		super.setTeachingPeople(teachingPeople);
	}

	public void setNumber_lectures(Integer numberLectures) {
		super.setNumberLectures(numberLectures);
	}

	public void setLecturepeople_id(Integer lecturepeopleId) {
		super.setLecturepeopleId(lecturepeopleId);
	}

	public void setLecture_people(String lecturePeople) {
		super.setLecturePeople(lecturePeople);
	}

	public void setLecture_time(Date lectureTime) {
		super.setLectureTime(lectureTime);
	}

	public void setEvaluation_rank(String evaluationRank) {
		super.setEvaluationRank(evaluationRank);
	}

	public void setLecture_content(String lectureContent) {
		super.setLectureContent(lectureContent);
	}

	public void setLecture_address(String lectureAddress) {
		super.setLectureAddress(lectureAddress);
	}

	public void setLecture_company(String lectureCompany) {
		super.setLectureCompany(lectureCompany);
	}

	public void setIs_share(Integer isShare) {
		super.setIsShare(isShare);
	}

	public void setShare_time(Date shareTime) {
		super.setShareTime(shareTime);
	}

	public void setIs_delete(Boolean isDelete) {
		super.setIsDelete(isDelete);
	}

	public void setIs_epub(Integer isEpub) {
		super.setIsEpub(isEpub);
	}

	public void setEpub_time(Date epubTime) {
		super.setEpubTime(epubTime);
	}

	public void setIs_ubmit(Integer isSubmit) {
		super.setIsSubmit(isSubmit);
	}

	public void setSubmit_time(Date submitTime) {
		super.setSubmitTime(submitTime);
	}

	public void setTopic_id(Integer topicId) {
		super.setTopicId(topicId);
	}

	public void setIs_reply(Integer isReply) {
		super.setIsReply(isReply);
	}

	public void setReply_up(Integer replyUp) {
		super.setReplyUp(replyUp);
	}

	public void setIs_comment(Integer isComment) {
		super.setIsComment(isComment);
	}

	public void setComment_up(Integer commentUp) {
		super.setCommentUp(commentUp);
	}

	public void setRes_type(Integer resType) {
		super.setResType(resType);
	}

	public void setOrg_id(Integer orgId) {
		super.setOrgId(orgId);
	}

	public void setPhase_id(Integer phaseId) {
		super.setPhaseId(phaseId);
	}

	public void setGrade_subject(String gradeSubject) {
		super.setGradeSubject(gradeSubject);
	}

	public void setClient_id(String clientId) {
		super.setClientId(clientId);
	}
	
	public void setLesson_id(String lessonId) {
		super.setLessonId(lessonId);
	}


	@Override
	@JSONField(serialize = false)
	public Page getPage() {
		return super.getPage();
	}

}
