/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.lecturerecords.bo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import com.tmser.tr.common.bo.BaseObject;

/**
 * 听课记录 Entity
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: LectureRecords.java, v 1.0 2015-03-31 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = LectureRecords.TABLE_NAME)
public class LectureRecords extends BaseObject {
	public static final String TABLE_NAME = "lecture_records";

	/**
	 * 主键ID，自动自增
	 **/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 资源类型 默认6
	 **/
	@Column(name = "res_type")
	private Integer resType;

	/**
	 * 听课类型 0是校内听课 1是校外听课
	 **/
	@Column(name = "type")
	private Integer type;

	/**
	 * 校内听课课题ID
	 */
	@Column(name = "topic_id")
	private Integer topicId;

	/**
	 * 课题
	 **/
	@Column(name = "topic")
	private String topic;

	/**
	 * 当前学年
	 **/
	@Column(name = "school_year")
	private Integer schoolYear;

	/**
	 * 当前学期
	 **/
	@Column(name = "term")
	private Integer term;

	/**
	 * 听课人组织机构ID
	 **/
	@Column(name = "org_id")
	private Integer orgId;

	/**
	 * 年级ID
	 **/
	@Column(name = "grade_id")
	private Integer gradeId;

	/**
	 * 年级
	 **/
	@Column(name = "grade")
	private String grade;

	/**
	 * 学科ID
	 **/
	@Column(name = "subject_id")
	private Integer subjectId;

	/**
	 * 学科
	 **/
	@Column(name = "subject")
	private String subject;

	/**
	 * 授课人ID
	 **/
	@Column(name = "teachingpeople_id")
	private Integer teachingpeopleId;

	/**
	 * 授课人
	 **/
	@Column(name = "teaching_people")
	private String teachingPeople;

	/**
	 * 听课节数
	 **/
	@Column(name = "number_lectures")
	private Integer numberLectures;

	/**
	 * 听课人ID
	 **/
	@Column(name = "lecturepeople_id")
	private Integer lecturepeopleId;

	/**
	 * 听课人
	 **/
	@Column(name = "lecture_people")
	private String lecturePeople;

	/**
	 * 听课时间
	 **/
	@Column(name = "lecture_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lectureTime;

	/**
	 * 评价等级
	 **/
	@Column(name = "evaluation_rank")
	private String evaluationRank;

	/**
	 * 听课意见
	 **/
	@Column(name = "lecture_content", length = 21800)
	private String lectureContent;

	/**
	 * 听课地点
	 **/
	@Column(name = "lecture_address")
	private String lectureAddress;

	/**
	 * 听课单位
	 **/
	@Column(name = "lecture_company")
	private String lectureCompany;

	/**
	 * 是否分享 0为不分享 1为分享
	 **/
	@Column(name = "is_share")
	private Integer isShare;

	/**
	 * 分享时间
	 **/
	@Column(name = "share_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date shareTime;

	/**
	 * 是否删除 0为不删除 1为删除
	 **/
	@Column(name = "is_delete")
	private Boolean isDelete;

	/**
	 * 是否发布 0为存草稿 1为发布
	 **/
	@Column(name = "is_epub")
	private Integer isEpub;

	/**
	 * 发布时间
	 **/
	@Column(name = "epub_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date epubTime;

	/**
	 * 是否提交 0为不提交 1为发提交
	 **/
	@Column(name = "is_submit")
	private Integer isSubmit;

	/**
	 * 提交时间
	 */
	@Column(name = "submit_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date submitTime;

	/**
	 * 是否回复 0为未回复 1为已回复
	 **/
	@Column(name = "is_reply")
	private Integer isReply;

	/**
	 * 回复是否更新 0为未更新 1为已更新
	 **/
	@Column(name = "reply_up")
	private Integer replyUp;

	/**
	 * 是否评论 0为未评论 1为已评论
	 **/
	@Column(name = "is_comment")
	private Integer isComment;

	/**
	 * 是否查阅  0为未查阅 1为已查阅
	 */
	@Column(name="is_scan")
	private Integer isScan;
	
	/**
	 * 查阅是否更新 0未更新，1已更新
	 */
	@Column(name="scan_up")
	private Integer scanUp;
	/**
	 * 评论是否更新 0为未更新 1为已更新
	 **/
	@Column(name = "comment_up")
	private Integer commentUp;

	/**
	 * 学段ID
	 */
	@Column(name = "phase_id")
	private Integer phaseId;

	/**
	 * 校外的年级学科
	 */
	@Column(name = "grade_subject")
	private String gradeSubject;
	/**
	 * 客户端ID
	 */
	@Column(name = "client_id", length = 64)
	private String clientId;

	@Transient
	private String lessonId;
	 
	public Integer getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Integer schoolYear) {
		this.schoolYear = schoolYear;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return this.type;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTopic() {
		return this.topic;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public Integer getGradeId() {
		return this.gradeId;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getGrade() {
		return this.grade;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getSubjectId() {
		return this.subjectId;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setTeachingpeopleId(Integer teachingpeopleId) {
		this.teachingpeopleId = teachingpeopleId;
	}

	public Integer getTeachingpeopleId() {
		return this.teachingpeopleId;
	}

	public void setTeachingPeople(String teachingPeople) {
		this.teachingPeople = teachingPeople;
	}

	public String getTeachingPeople() {
		return this.teachingPeople;
	}

	public void setNumberLectures(Integer numberLectures) {
		this.numberLectures = numberLectures;
	}

	public Integer getNumberLectures() {
		return this.numberLectures;
	}

	public void setLecturepeopleId(Integer lecturepeopleId) {
		this.lecturepeopleId = lecturepeopleId;
	}

	public Integer getLecturepeopleId() {
		return this.lecturepeopleId;
	}

	public void setLecturePeople(String lecturePeople) {
		this.lecturePeople = lecturePeople;
	}

	public String getLecturePeople() {
		return this.lecturePeople;
	}

	public void setLectureTime(Date lectureTime) {
		this.lectureTime = lectureTime;
	}

	public Date getLectureTime() {
		return this.lectureTime;
	}

	public void setEvaluationRank(String evaluationRank) {
		this.evaluationRank = evaluationRank;
	}

	public String getEvaluationRank() {
		return this.evaluationRank;
	}

	public void setLectureContent(String lectureContent) {
		this.lectureContent = lectureContent;
	}

	public String getLectureContent() {
		return this.lectureContent;
	}

	public void setLectureAddress(String lectureAddress) {
		this.lectureAddress = lectureAddress;
	}

	public String getLectureAddress() {
		return this.lectureAddress;
	}

	public void setLectureCompany(String lectureCompany) {
		this.lectureCompany = lectureCompany;
	}

	public String getLectureCompany() {
		return this.lectureCompany;
	}

	public void setIsShare(Integer isShare) {
		this.isShare = isShare;
	}

	public Integer getIsShare() {
		return this.isShare;
	}

	public void setShareTime(Date shareTime) {
		this.shareTime = shareTime;
	}

	public Date getShareTime() {
		return this.shareTime;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Boolean getIsDelete() {
		return this.isDelete;
	}

	public void setIsEpub(Integer isEpub) {
		this.isEpub = isEpub;
	}

	public Integer getIsEpub() {
		return this.isEpub;
	}

	public void setEpubTime(Date epubTime) {
		this.epubTime = epubTime;
	}

	public Date getEpubTime() {
		return this.epubTime;
	}

	public Integer getIsSubmit() {
		return isSubmit;
	}

	public void setIsSubmit(Integer isSubmit) {
		this.isSubmit = isSubmit;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public Integer getTopicId() {
		return topicId;
	}

	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Integer getIsReply() {
		return isReply;
	}

	public void setIsReply(Integer isReply) {
		this.isReply = isReply;
	}

	public Integer getReplyUp() {
		return replyUp;
	}

	public void setReplyUp(Integer replyUp) {
		this.replyUp = replyUp;
	}

	public Integer getIsComment() {
		return isComment;
	}

	public void setIsComment(Integer isComment) {
		this.isComment = isComment;
	}

	public Integer getCommentUp() {
		return commentUp;
	}

	public void setCommentUp(Integer commentUp) {
		this.commentUp = commentUp;
	}

	public Integer getResType() {
		return resType;
	}

	public void setResType(Integer resType) {
		this.resType = resType;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(Integer phaseId) {
		this.phaseId = phaseId;
	}

	public String getGradeSubject() {
		return gradeSubject;
	}

	public void setGradeSubject(String gradeSubject) {
		this.gradeSubject = gradeSubject;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getLessonId() {
		return lessonId;
	}

	public void setLessonId(String lessonId) {
		this.lessonId = lessonId;
	}

	/** 
	 * Getter method for property <tt>isScan</tt>. 
	 * @return property value of isScan 
	 */
	public Integer getIsScan() {
		return isScan;
	}

	/**
	 * Setter method for property <tt>isScan</tt>.
	 * @param isScan value to be assigned to property isScan
	 */
	public void setIsScan(Integer isScan) {
		this.isScan = isScan;
	}

	/** 
	 * Getter method for property <tt>scanUp</tt>. 
	 * @return property value of scanUp 
	 */
	public Integer getScanUp() {
		return scanUp;
	}

	/**
	 * Setter method for property <tt>scanUp</tt>.
	 * @param scanUp value to be assigned to property scanUp
	 */
	public void setScanUp(Integer scanUp) {
		this.scanUp = scanUp;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof LectureRecords))
			return false;
		LectureRecords castOther = (LectureRecords) other;
		return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}
}