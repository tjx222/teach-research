/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.bo;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import com.tmser.tr.common.bo.BaseObject;

/**
 * 校际教研活动表 Entity
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author zpp
 * @version $Id: SchoolActivity.java, v 1.0 2015-05-20 zpp Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = SchoolActivity.TABLE_NAME)
public class SchoolActivity extends BaseObject {
	public static final String TABLE_NAME = "school_activity";
	public static final Integer TBJA = 1;// 同备教案
	public static final Integer ZTYT = 2;// 主题研讨
	public static final Integer SPYT = 3;// 视频研讨
	public static final Integer ZBKT = 4;// 直播课堂
	/**
	 * 校际教研活动ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 校际教研活动类型 1：同备教案 2：主题研讨 3：视频研讨 4：直播课堂
	 */
	@Column(name = "type_id")
	private Integer typeId;

	/**
	 * 校际教研活动课题ID
	 */
	@Column(name = "info_id")
	private Integer infoId;

	/**
	 * 校际教研活动类型名称
	 */
	@Column(name = "type_name", length = 15)
	private String typeName;

	/**
	 * 校际教研活动名称
	 */
	@Column(name = "activity_name", length = 128)
	private String activityName;

	/**
	 * 校际教研活动要求 备注等信息
	 */
	@Column(name = "remark", length = 300)
	private String remark;

	/**
	 * 视频地址
	 */
	@Column(name = "video_url", length = 100)
	private String videoUrl;

	/**
	 * 直播课堂id
	 */
	@Column(name = "class_id", length = 128)
	private String classId;

	/**
	 * 校际教研活动是否发文状态 0：草稿 1：正式发文
	 */
	@Column(name = "status")
	private Integer status;

	/**
	 * 校际教研活动学科Id字符串 示例 ： ,1,12,
	 */
	@Column(name = "subject_ids", length = 128)
	private String subjectIds;

	/**
	 * 校际教研活动学科名称字符串 示例： 语文，数学
	 */
	@Column(name = "subject_name", length = 128)
	private String subjectName;

	/**
	 * 校际教研活动年级字符串 示例: ,101,102,103,
	 */
	@Column(name = "grade_ids", length = 128)
	private String gradeIds;

	/**
	 * 校际教研活动年级名称字符串 示例： 一年级,二年级
	 */
	@Column(name = "grade_name", length = 128)
	private String gradeName;

	/**
	 * 校际教研活动组织者ID
	 */
	@Column(name = "organize_user_id")
	private Integer organizeUserId;

	/**
	 * 校际教研活动组织者名称
	 */
	@Column(name = "organize_user_name", length = 10)
	private String organizeUserName;

	/**
	 * 校际教研活动组织者学科id
	 */
	@Column(name = "organize_subject_id")
	private Integer organizeSubjectId;

	/**
	 * 校际教研组织者年级id
	 */
	@Column(name = "organize_grade_id")
	private Integer organizeGradeId;

	/**
	 * 校际教研活动组织者机构ID
	 */
	@Column(name = "org_id")
	private Integer orgId;

	/**
	 * 学段ID
	 */
	@Column(name = "phase_id")
	private Integer phaseId;

	/**
	 * 校际教研活动组织者空间ID
	 */
	@Column(name = "space_id")
	private Integer spaceId;

	/**
	 * 校际教研活动学期
	 */
	@Column(name = "term")
	private Integer term;

	/**
	 * 校际教研活动学年
	 */
	@Column(name = "school_year")
	private Integer schoolYear;

	/**
	 * 校际教研活动开始时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "start_time")
	private Date startTime;

	/**
	 * 校际教研活动结束时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "end_time")
	private Date endTime;

	/**
	 * 校际教研活动评论数量
	 */
	@Column(name = "comments_num")
	private Integer commentsNum;

	/**
	 * 校际教研活动创建时间
	 */
	@Column(name = "create_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 校际教研活动发布时间
	 */
	@Column(name = "release_time")
	private Date releaseTime;

	/**
	 * 校际教研活动是否已结束
	 */
	@Column(name = "is_over")
	private Boolean isOver;

	/**
	 * 校际教研活动是否已提交
	 */
	@Column(name = "is_submit")
	private Boolean isSubmit;

	/**
	 * 校际教研活动是否已查阅
	 */
	@Column(name = "is_audit")
	private Boolean isAudit;

	/**
	 * 校际教研活动是否已分享
	 */
	@Column(name = "is_share")
	private Boolean isShare;

	/**
	 * 分享时间
	 */
	@Column(name = "share_time")
	private Date shareTime;

	/**
	 * 校际教研活动是否已评论
	 */
	@Column(name = "is_comment")
	private Boolean isComment;

	/**
	 * 校际教研活动整理教案是否已发送
	 */
	@Column(name = "is_send")
	private Boolean isSend;

	/**
	 * 校际教研活动主备人ID
	 */
	@Column(name = "main_user_id")
	private Integer mainUserId;

	/**
	 * 校际教研活动主备人名称
	 */
	@Column(name = "main_user_name", length = 10)
	private String mainUserName;

	/**
	 * 校际教研活动主备人学校机构ID
	 */
	@Column(name = "main_user_org_id")
	private Integer mainUserOrgId;

	/**
	 * 校际教研活动主备人学科
	 */
	@Column(name = "main_user_subject_id")
	private Integer mainUserSubjectId;

	/**
	 * 校际教研活动主备人年级
	 */
	@Column(name = "main_user_grade_id")
	private Integer mainUserGradeId;

	/**
	 * 校际教研活动所属的校际教研圈
	 */
	@Column(name = "school_teach_circle_id")
	private Integer schoolTeachCircleId;

	/**
	 * 校际教研活动所属的校际教研圈名称
	 */
	@Column(name = "school_teach_circle_name", length = 30)
	private String schoolTeachCircleName;

	/**
	 * 校际教研活动的区域机构IDS（层级路径节点字符串）
	 */
	@Column(name = "area_ids", length = 30)
	private String areaIds;

	/**
	 * 专家字符串 用户ids
	 */
	@Column(name = "expert_ids", length = 100)
	private String expertIds;

	/**
	 * 用户所在的机构是否已经退出该教研圈
	 */
	private Boolean isTuiChu;

	/**
	 * 活动是否应经有了讨论信息
	 */
	private Boolean isDiscuss;

	/**
	 * 发起活动时可参与的机构ids
	 */
	@Column(name = "orgids", length = 64)
	private String orgids;

	/**
	 * 教研圈下的机构列表
	 */
	private List<SchoolTeachCircleOrg> stcoList;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	/**
	 * Getter method for property <tt>orgids</tt>.
	 * 
	 * @return property value of orgids
	 */
	public String getOrgids() {
		return orgids;
	}

	/**
	 * Setter method for property <tt>orgids</tt>.
	 * 
	 * @param orgids
	 *            value to be assigned to property orgids
	 */
	public void setOrgids(String orgids) {
		this.orgids = orgids;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getTypeId() {
		return this.typeId;
	}

	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	public Integer getInfoId() {
		return this.infoId;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityName() {
		return this.activityName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setSubjectIds(String subjectIds) {
		this.subjectIds = subjectIds;
	}

	public String getSubjectIds() {
		return this.subjectIds;
	}

	public void setGradeIds(String gradeIds) {
		this.gradeIds = gradeIds;
	}

	public String getGradeIds() {
		return this.gradeIds;
	}

	public void setOrganizeUserId(Integer organizeUserId) {
		this.organizeUserId = organizeUserId;
	}

	public Integer getOrganizeUserId() {
		return this.organizeUserId;
	}

	public void setOrganizeUserName(String organizeUserName) {
		this.organizeUserName = organizeUserName;
	}

	public String getOrganizeUserName() {
		return this.organizeUserName;
	}

	public Integer getOrganizeSubjectId() {
		return organizeSubjectId;
	}

	public void setOrganizeSubjectId(Integer organzieSubjectId) {
		this.organizeSubjectId = organzieSubjectId;
	}

	public Integer getOrganizeGradeId() {
		return organizeGradeId;
	}

	public void setOrganizeGradeId(Integer organzieGradeId) {
		this.organizeGradeId = organzieGradeId;
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

	public void setSpaceId(Integer spaceId) {
		this.spaceId = spaceId;
	}

	public Integer getSpaceId() {
		return this.spaceId;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public void setSchoolYear(Integer schoolYear) {
		this.schoolYear = schoolYear;
	}

	public Integer getSchoolYear() {
		return this.schoolYear;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setCommentsNum(Integer commentsNum) {
		this.commentsNum = commentsNum;
	}

	public Integer getCommentsNum() {
		return this.commentsNum;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public Date getReleaseTime() {
		return this.releaseTime;
	}

	public void setIsOver(Boolean isOver) {
		this.isOver = isOver;
	}

	public Boolean getIsOver() {
		return this.isOver;
	}

	public void setIsSubmit(Boolean isSubmit) {
		this.isSubmit = isSubmit;
	}

	public Boolean getIsSubmit() {
		return this.isSubmit;
	}

	public void setIsAudit(Boolean isAudit) {
		this.isAudit = isAudit;
	}

	public Boolean getIsAudit() {
		return this.isAudit;
	}

	public void setIsShare(Boolean isShare) {
		this.isShare = isShare;
	}

	public Boolean getIsShare() {
		return this.isShare;
	}

	public void setIsComment(Boolean isComment) {
		this.isComment = isComment;
	}

	public Boolean getIsComment() {
		return this.isComment;
	}

	public void setIsSend(Boolean isSend) {
		this.isSend = isSend;
	}

	public Boolean getIsSend() {
		return this.isSend;
	}

	public void setMainUserId(Integer mainUserId) {
		this.mainUserId = mainUserId;
	}

	public Integer getMainUserId() {
		return this.mainUserId;
	}

	public void setMainUserName(String mainUserName) {
		this.mainUserName = mainUserName;
	}

	public String getMainUserName() {
		return this.mainUserName;
	}

	public Integer getMainUserOrgId() {
		return mainUserOrgId;
	}

	public void setMainUserOrgId(Integer mainUserOrgId) {
		this.mainUserOrgId = mainUserOrgId;
	}

	public void setMainUserSubjectId(Integer mainUserSubjectId) {
		this.mainUserSubjectId = mainUserSubjectId;
	}

	public Integer getMainUserSubjectId() {
		return this.mainUserSubjectId;
	}

	public void setMainUserGradeId(Integer mainUserGradeId) {
		this.mainUserGradeId = mainUserGradeId;
	}

	public Integer getMainUserGradeId() {
		return this.mainUserGradeId;
	}

	public void setSchoolTeachCircleId(Integer schoolTeachCircleId) {
		this.schoolTeachCircleId = schoolTeachCircleId;
	}

	public Integer getSchoolTeachCircleId() {
		return this.schoolTeachCircleId;
	}

	public void setSchoolTeachCircleName(String schoolTeachCircleName) {
		this.schoolTeachCircleName = schoolTeachCircleName;
	}

	public String getSchoolTeachCircleName() {
		return this.schoolTeachCircleName;
	}

	public Boolean getIsTuiChu() {
		return isTuiChu;
	}

	public void setIsTuiChu(Boolean isTuiChu) {
		this.isTuiChu = isTuiChu;
	}

	public String getAreaIds() {
		return areaIds;
	}

	public void setAreaIds(String areaIds) {
		this.areaIds = areaIds;
	}

	public String getExpertIds() {
		return expertIds;
	}

	public void setExpertIds(String expertIds) {
		this.expertIds = expertIds;
	}

	public List<SchoolTeachCircleOrg> getStcoList() {
		return stcoList;
	}

	public void setStcoList(List<SchoolTeachCircleOrg> stcoList) {
		this.stcoList = stcoList;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public Boolean getIsDiscuss() {
		return isDiscuss;
	}

	public void setIsDiscuss(Boolean isDiscuss) {
		this.isDiscuss = isDiscuss;
	}

	public Date getShareTime() {
		return shareTime;
	}

	public void setShareTime(Date shareTime) {
		this.shareTime = shareTime;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof SchoolActivity))
			return false;
		SchoolActivity castOther = (SchoolActivity) other;
		return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}
}
