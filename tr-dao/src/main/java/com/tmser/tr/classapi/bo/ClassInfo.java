/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.classapi.bo;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import com.tmser.tr.common.bo.QueryObject;
import com.tmser.tr.utils.DateUtils;

/**
 * 课堂附件信息 Entity
 * 
 * <pre>
 *
 * </pre>
 *
 * @author yc
 * @version $Id: ClassInfo.java, v 1.0 2016-09-20 yc Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = ClassInfo.TABLE_NAME)
public class ClassInfo extends QueryObject {

  public static final String TABLE_NAME = "area_video_classinfo";

  public ClassInfo() {
  }

  public static final int INIT = 0;
  public static final int BEGINING = 1;
  public static final int END = 2;

  /**
   * 默认密码的课堂创建
   * 
   * @param title
   * @param startTime
   * @param endTime
   */
  public ClassInfo(String title, Integer userId, String userName, Date startTime, Date endTime) {
    this.userId = userId;
    this.userName = userName;
    this.startTime = startTime;
    this.endTime = endTime;
    this.title = title;
  }

  /**
   * 课堂Id
   **/
  @Id
  @Column(name = "id", length = 32)
  private String id;

  /**
   * 用户Id
   **/
  @Column(name = "userId")
  private Integer userId;

  /**
   * 创建者名称
   */
  @Column(name = "user_name", length = 32)
  private String userName;

  /**
   * 视频类型
   * 1、互动 2、直播；默认为1
   */
  @Column(name = "meeting_type")
  private Integer meetingType;

  /**
   * 视频质量：低 0，中 1，高2，较高 3，最高 4
   */
  @Column(name = "video_quality")
  private Integer videoQuality;

  /**
   * 语言：1 英文，2 中文
   */
  @Column(name = "language")
  private Integer language;

  /**
   * 课堂名称
   **/
  @Column(name = "title", length = 100)
  private String title;

  /**
   * 开始时间
   **/
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Column(name = "starttime")
  private Date startTime;

  /**
   * 结束时间
   **/
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Column(name = "endtime")
  private Date endTime;

  /**
   * 课堂密码
   **/
  @Column(name = "pwd", length = 100)
  private String pwd;

  /**
   * 教师参加码
   **/
  @Column(name = "teacher_code", length = 100)
  private String teacherCode;
  /**
   * 管理员参加码
   **/
  @Column(name = "admin_code", length = 100)
  private String adminCode;
  /**
   * 学生参加码
   **/
  @Column(name = "student_code", length = 100)
  private String studentCode;

  @Column(name = "state")
  private Integer state;

  private List<String> docIds;

  private List<String> unBindDocIds;

  /**
   * 视频录播地址
   */
  @Column(name = "record_url", length = 2048)
  private String recordUrl;

  @Transient
  private Integer count;

  public List<String> getUnBindDocIds() {
    return unBindDocIds;
  }

  public void setUnBindDocIds(List<String> unBindDocIds) {
    this.unBindDocIds = unBindDocIds;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  /**
   * Getter method for property <tt>docIds</tt>.
   * 
   * @return property value of docIds
   */
  public List<String> getDocIds() {
    return docIds;
  }

  /**
   * Setter method for property <tt>docIds</tt>.
   * 
   * @param docIds
   *          value to be assigned to property docIds
   */
  public void setDocIds(List<String> docIds) {
    this.docIds = docIds;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return this.id;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Integer getUserId() {
    return this.userId;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return this.title;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getStartTime() {
    return this.startTime;
  }

  public void setEndtime(Date endTime) {
    this.endTime = endTime;
  }

  public Date getEndTime() {
    return this.endTime;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public String getPwd() {
    return this.pwd;
  }

  /**
   * Getter method for property <tt>meetingType</tt>.
   * 
   * @return property value of meetingType
   */
  public Integer getMeetingType() {
    return meetingType;
  }

  /**
   * Setter method for property <tt>meetingType</tt>.
   * 
   * @param meetingType
   *          value to be assigned to property meetingType
   */
  public void setMeetingType(Integer meetingType) {
    this.meetingType = meetingType;
  }

  /**
   * Getter method for property <tt>videoQuality</tt>.
   * 
   * @return property value of videoQuality
   */
  public Integer getVideoQuality() {
    return videoQuality;
  }

  /**
   * Setter method for property <tt>videoQuality</tt>.
   * 
   * @param videoQuality
   *          value to be assigned to property videoQuality
   */
  public void setVideoQuality(Integer videoQuality) {
    this.videoQuality = videoQuality;
  }

  /**
   * Getter method for property <tt>recordUrl</tt>.
   * 
   * @return property value of recordUrl
   */
  public String getRecordUrl() {
    return recordUrl;
  }

  /**
   * @return
   */
  public String getStartTimeStr() {
    return DateUtils.formatDate(startTime, "yyyy-MM-dd HH:mm:ss");
  }

  /**
   * @return
   */
  public String getEndTimeStr() {
    return DateUtils.formatDate(endTime, "yyyy-MM-dd HH:mm:ss");
  }

  /**
   * Getter method for property <tt>language</tt>.
   * 
   * @return property value of language
   */
  public Integer getLanguage() {
    return language;
  }

  /**
   * Setter method for property <tt>language</tt>.
   * 
   * @param language
   *          value to be assigned to property language
   */
  public void setLanguage(Integer language) {
    this.language = language;
  }

  /**
   * Setter method for property <tt>recordUrl</tt>.
   * 
   * @param recordUrl
   *          value to be assigned to property recordUrl
   */
  public void setRecordUrl(String recordUrl) {
    this.recordUrl = recordUrl;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  @Override
  public boolean equals(final Object other) {
    if (!(other instanceof ClassInfo))
      return false;
    ClassInfo castOther = (ClassInfo) other;
    return new EqualsBuilder().append(id, castOther.id).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(id).toHashCode();
  }

  public String getTeacherCode() {
    return teacherCode;
  }

  public void setTeacherCode(String teacherCode) {
    this.teacherCode = teacherCode;
  }

  public String getAdminCode() {
    return adminCode;
  }

  public void setAdminCode(String adminCode) {
    this.adminCode = adminCode;
  }

  public String getStudentCode() {
    return studentCode;
  }

  public void setStudentCode(String studentCode) {
    this.studentCode = studentCode;
  }

  /**
   * <p>
   * </p>
   * Getter method for property <tt>state</tt>.
   *
   * @return state Integer
   */
  public Integer getState() {
    return state;
  }

  /**
   * <p>
   * </p>
   * Setter method for property <tt>state</tt>.
   *
   * @param state
   *          Integer value to be assigned to property state
   */
  public void setState(Integer state) {
    this.state = state;
  }
}
