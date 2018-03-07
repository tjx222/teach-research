package com.tmser.tr.lessonplan.bo;

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

import com.tmser.tr.common.bo.BaseObject;

/**
 * 备课资源表 Entity
 * 
 * @author wangdawei
 * @version 1.0
 *          2015-02-03
 */
@SuppressWarnings("serial")
@Entity
@Table(name = LessonPlan.TABLE_NAME)
public class LessonPlan extends BaseObject {
  public static final String TABLE_NAME = "lesson_plan";
  public static final int JIAO_AN = 0; // 教案
  public static final int KE_JIAN = 1; // 课件
  public static final int KE_HOU_FAN_SI = 2; // 课后反思
  public static final int QI_TA_FAN_SI = 3; // 其他反思

  /**
   * id
   **/
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "plan_id")
  private Integer planId;

  /**
   * lesson_info表id
   */
  @Column(name = "info_id")
  private Integer infoId;

  /**
   * 备课资源名称
   **/
  @Column(name = "plan_name", nullable = false)
  private String planName;

  /**
   * 审核状态，0：未审核 ，1：分享审核
   */
  @Column(name = "state")
  private Integer state;

  /**
   * 资源id
   **/
  @Column(name = "res_id", nullable = false)
  private String resId;

  /**
   * 资源类型（0：教案，1：课件，2：课后反思，3：其他反思）
   **/
  @Column(name = "plan_type")
  private Integer planType;

  /**
   * 摘要
   */
  @Column(name = "digest")
  private String digest;

  /**
   * 用户id
   **/
  @Column(name = "user_id", nullable = false)
  private Integer userId;

  /**
   * 科目id
   **/
  @Column(name = "subject_id")
  private Integer subjectId;

  /**
   * 年级id
   **/
  @Column(name = "grade_id")
  private Integer gradeId;

  /**
   * 书id
   */
  @Column(name = "book_id")
  private String bookId;

  /**
   * 书的简称
   */
  @Column(name = "book_shortname")
  private String bookShortname;
  /**
   * 课题id
   **/
  @Column(name = "lesson_id")
  private String lessonId;

  /**
   * 课时id
   **/
  @Column(name = "hours_id")
  private String hoursId;

  /**
   * 模板id
   **/
  @Column(name = "tp_id")
  private Integer tpId;

  /**
   * 用户所在机构id
   **/
  @Column(name = "org_id")
  private Integer orgId;

  /**
   * 册别id
   */
  @Column(name = "fascicule_id")
  private Integer fasciculeId;
  /**
   * 学年
   **/
  @Column(name = "school_year")
  private Integer schoolYear;

  /**
   * 学期id
   */
  @Column(name = "term_id")
  private Integer termId;

  /**
   * 学段id
   */
  @Column(name = "phase_id")
  private Integer phaseId;

  /**
   * 是否提交，0：未提交 1：已提交
   */
  @Column(name = "is_submit")
  private Boolean isSubmit;

  /**
   * 是否分享，0：未分享 1：已分享
   */
  @Column(name = "is_share")
  private Boolean isShare;

  /**
   * 是否评论，0：未评论 1：已评论
   */
  @Column(name = "is_comment")
  private Boolean isComment;

  /**
   * 评论意见已更新
   */
  @Column(name = "comment_up")
  private Boolean commentUp;
  /**
   * 是否查阅，0：未查阅 1：已查阅
   */
  @Column(name = "is_scan")
  private Boolean isScan;

  /**
   * 查阅意见是否已更新
   */
  @Column(name = "scan_up")
  private Boolean scanUp;
  /**
   * 提交时间
   */
  @Column(name = "submit_time")
  private Date submitTime;

  /**
   * 分享时间
   */
  @Column(name = "share_time")
  private Date shareTime;

  /**
   * 下载量
   **/
  @Column(name = "down_num")
  private Integer downNum;

  /**
   * 排序值
   **/
  @Column(name = "order_value")
  private Integer orderValue;

  /**
   * 客户端id 判断是否为离线端过来的数据
   */
  @Column(name = "client_id")
  private String clientId;

  /**
   * lessonplan来源，0-本地上传，1-平台对接
   */
  @Column(name = "origin")
  private Integer origin;

  @Transient
  private String lessonName;
  @Transient
  private String LessonHours;

  /**
   * Getter method for property <tt>lessonHours</tt>.
   * 
   * @return property value of LessonHours
   */
  public String getLessonHours() {
    return LessonHours;
  }

  /**
   * Setter method for property <tt>lessonHours</tt>.
   * 
   * @param LessonHours
   *          value to be assigned to property lessonHours
   */
  public void setLessonHours(String lessonHours) {
    LessonHours = lessonHours;
  }

  /**
   * Getter method for property <tt>lessonName</tt>.
   * 
   * @return property value of lessonName
   */
  public String getLessonName() {
    return lessonName;
  }

  /**
   * Setter method for property <tt>lessonName</tt>.
   * 
   * @param lessonName
   *          value to be assigned to property lessonName
   */
  public void setLessonName(String lessonName) {
    this.lessonName = lessonName;
  }

  public Integer getOrigin() {
    return origin;
  }

  public void setOrigin(Integer origin) {
    this.origin = origin;
  }

  private String userName; // 用户名

  private String orgName;// 机构名

  public Integer getInfoId() {
    return infoId;
  }

  public void setInfoId(Integer infoId) {
    this.infoId = infoId;
  }

  public Date getSubmitTime() {
    return submitTime;
  }

  public void setSubmitTime(Date submitTime) {
    this.submitTime = submitTime;
  }

  public Date getShareTime() {
    return shareTime;
  }

  public void setShareTime(Date shareTime) {
    this.shareTime = shareTime;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getOrgName() {
    return orgName;
  }

  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }

  public String getDigest() {
    return digest;
  }

  public void setDigest(String digest) {
    this.digest = digest;
  }

  public void setPlanId(Integer planId) {
    this.planId = planId;
  }

  public Integer getPlanId() {
    return this.planId;
  }

  public void setPlanName(String planName) {
    this.planName = planName;
  }

  public String getPlanName() {
    return this.planName;
  }

  public void setResId(String resId) {
    this.resId = resId;
  }

  public String getResId() {
    return this.resId;
  }

  public void setPlanType(Integer planType) {
    this.planType = planType;
  }

  public Integer getPlanType() {
    return this.planType;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Integer getUserId() {
    return this.userId;
  }

  public void setSubjectId(Integer subjectId) {
    this.subjectId = subjectId;
  }

  public Integer getSubjectId() {
    return this.subjectId;
  }

  public void setGradeId(Integer gradeId) {
    this.gradeId = gradeId;
  }

  public Integer getGradeId() {
    return this.gradeId;
  }

  public void setLessonId(String lessonId) {
    this.lessonId = lessonId;
  }

  public String getLessonId() {
    return this.lessonId;
  }

  public void setHoursId(String hoursId) {
    this.hoursId = hoursId;
  }

  public String getHoursId() {
    return this.hoursId;
  }

  public void setTpId(Integer tpId) {
    this.tpId = tpId;
  }

  public Integer getTpId() {
    return this.tpId;
  }

  public void setOrgId(Integer orgId) {
    this.orgId = orgId;
  }

  public Integer getOrgId() {
    return this.orgId;
  }

  public void setSchoolYear(Integer schoolYear) {
    this.schoolYear = schoolYear;
  }

  public Integer getSchoolYear() {
    return this.schoolYear;
  }

  public void setDownNum(Integer downNum) {
    this.downNum = downNum;
  }

  public Integer getDownNum() {
    return this.downNum;
  }

  public void setOrderValue(Integer orderValue) {
    this.orderValue = orderValue;
  }

  public Integer getOrderValue() {
    return this.orderValue;
  }

  public String getBookId() {
    return bookId;
  }

  public void setBookId(String bookId) {
    this.bookId = bookId;
  }

  public String getBookShortname() {
    return bookShortname;
  }

  public void setBookShortname(String bookShortname) {
    this.bookShortname = bookShortname;
  }

  public Boolean getCommentUp() {
    return commentUp;
  }

  public void setCommentUp(Boolean commentUp) {
    this.commentUp = commentUp;
  }

  public Boolean getScanUp() {
    return scanUp;
  }

  public void setScanUp(Boolean scanUp) {
    this.scanUp = scanUp;
  }

  public Integer getTermId() {
    return termId;
  }

  public void setTermId(Integer termId) {
    this.termId = termId;
  }

  public Integer getPhaseId() {
    return phaseId;
  }

  public void setPhaseId(Integer phaseId) {
    this.phaseId = phaseId;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public LessonPlan() {
  }

  /**
   * 新增时构造
   * 
   * @param other
   * @return
   * @see com.tmser.tr.common.bo.QueryObject#equals(java.lang.Object)
   */
  public LessonPlan(Integer infoId, String planName, String resId,
      Integer planType, Integer userId, Integer subjectId, Integer gradeId,
      String bookId, String bookShortname, String lessonId, String hoursId,
      Integer tpId, Integer orgId, Integer fasciculeId, Integer schoolYear,
      Integer termId, Integer phaseId, Integer orderValue, Date crtDttm,
      Integer enable) {
    this.infoId = infoId;
    this.planName = planName;
    this.resId = resId;
    this.planType = planType;
    this.userId = userId;
    this.subjectId = subjectId;
    this.gradeId = gradeId;
    this.bookId = bookId;
    this.bookShortname = bookShortname;
    this.lessonId = lessonId;
    this.hoursId = hoursId;
    this.tpId = tpId;
    this.orgId = orgId;
    this.fasciculeId = fasciculeId;
    this.schoolYear = schoolYear;
    this.termId = termId;
    this.phaseId = phaseId;
    this.downNum = 0;
    this.orderValue = orderValue;
    this.isComment = false;
    this.isScan = false;
    this.isShare = false;
    this.isSubmit = false;
    this.scanUp = false;
    this.commentUp = false;
    super.setCrtId(userId);
    super.setCrtDttm(crtDttm);
    super.setLastupId(userId);
    super.setLastupDttm(crtDttm);
    super.setEnable(enable);
  }

  public Integer getFasciculeId() {
    return fasciculeId;
  }

  public void setFasciculeId(Integer fasciculeId) {
    this.fasciculeId = fasciculeId;
  }

  public Boolean getIsSubmit() {
    return isSubmit;
  }

  public void setIsSubmit(Boolean isSubmit) {
    this.isSubmit = isSubmit;
  }

  public Boolean getIsShare() {
    return isShare;
  }

  public void setIsShare(Boolean isShare) {
    this.isShare = isShare;
  }

  public Boolean getIsComment() {
    return isComment;
  }

  public void setIsComment(Boolean isComment) {
    this.isComment = isComment;
  }

  public Boolean getIsScan() {
    return isScan;
  }

  public void setIsScan(Boolean isScan) {
    this.isScan = isScan;
  }

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  @Override
  public boolean equals(final Object other) {
    if (!(other instanceof LessonPlan))
      return false;
    LessonPlan castOther = (LessonPlan) other;
    return new EqualsBuilder().append(planId, castOther.planId).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(planId).toHashCode();
  }
}
