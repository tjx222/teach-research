/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.meta.bo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.QueryObject;

/**
 * 书籍对象 Entity
 * 
 * <pre>
 *
 * </pre>
 *
 * @author zpp
 * @version $Id: Commidity.java, v 1.0 2015-02-06 zpp Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = Book.TABLE_NAME)
public class Book extends QueryObject {
  public static final String TABLE_NAME = "commidity";

  @Id
  @Column(name = "com_id")
  private String comId;

  /**
   * 商品名称
   **/
  @Column(name = "com_name", nullable = false)
  private String comName;

  /**
   * 商品大分类
   **/
  @Column(name = "com_type", nullable = false)
  private Integer comType;

  /**
   * 0：裸书；1：多媒体书；2：精品资源
   **/
  @Column(name = "com_type_name", nullable = false)
  private String comTypeName;

  /**
   * 内容类型
   **/
  @Column(name = "content_type")
  private Integer contentType;

  /**
   * 所有商品的内容类型
   **/
  @Column(name = "content_type_name")
  private String contentTypeName;

  /**
   * 适用人群
   **/
  @Column(name = "usergroup_type")
  private Integer usergroupType;

  /**
   * 0：老师；1：学生：2：通用
   **/
  @Column(name = "usergroup_name")
  private String usergroupName;

  /**
   * 内容组类型
   **/
  @Column(name = "contentgroup_type")
  private Integer contentgroupType;

  @Column(name = "contentgroup_type_name")
  private String contentgroupTypeName;

  /**
   * 学段
   **/
  @Column(name = "phase")
  private String phase;

  @Column(name = "phase_id")
  private Integer phaseId;

  /**
   * 年级
   **/
  @Column(name = "grade_level")
  private String gradeLevel;

  @Column(name = "grade_level_id")
  private Integer gradeLevelId;

  /**
   * 学科
   **/
  @Column(name = "subject")
  private String subject;

  @Column(name = "subject_id")
  private Integer subjectId;

  /**
   * 出版社
   **/
  @Column(name = "publisher")
  private String publisher;

  @Column(name = "publisher_id")
  private Integer publisherId;

  /**
   * 册别
   **/
  @Column(name = "fascicule")
  private String fascicule;

  @Column(name = "fascicule_id")
  private Integer fasciculeId;

  /**
   * 版次(元数据)
   **/
  @Column(name = "book_edtion")
  private String bookEdtion;

  @Column(name = "book_edtion_id")
  private Integer bookEdtionId;

  /**
   * 关键字
   **/
  @Column(name = "keywords")
  private String keywords;

  /**
   * 0：下架；1：上架
   **/
  @Column(name = "shelf_type", nullable = false)
  private Integer shelfType;

  /**
   * 价格
   **/
  @Column(name = "price", nullable = false)
  private Float price;

  /**
   * 入库时间
   **/
  @Column(name = "book_in_time", nullable = false)
  private Date bookInTime;

  /**
   * 教材简称
   **/
  @Column(name = "format_name")
  private String formatName;

  /**
   * 0:正常1：删除
   **/
  @Column(name = "sys_delete", nullable = false)
  private Integer sysDelete;

  /**
   * 数据修改时间
   **/
  @Column(name = "data_modified_time", nullable = false)
  private Date dataModifiedTime;

  /**
   * 上架时间
   **/
  @Column(name = "shelf_time")
  private Date shelfTime;

  /**
   * 上架人
   **/
  @Column(name = "shelfer")
  private String shelfer;

  /**
   * 下载次数
   **/
  @Column(name = "download_times", nullable = false)
  private Long downloadTimes;

  /**
   * 0:普通1：推荐
   **/
  @Column(name = "recommend", nullable = false)
  private Integer recommend;

  /**
   * 好评度1-5
   **/
  @Column(name = "praise_degree")
  private Integer praiseDegree;

  /**
   * 描述
   **/
  @Column(name = "remark")
  private String remark;

  /**
   * 书籍的上下册关系，即相互关联com_id
   */
  @Column(name = "relation_com_id")
  private String relationComId;

  /**
   * 本多媒体书排序
   **/
  @Column(name = "com_order")
  private Integer comOrder;

  /**
   * 试读（0：普通，1：试读）
   **/
  @Column(name = "sale_type", nullable = false)
  private Integer saleType;

  /**
   * 格式
   **/
  @Column(name = "format")
  private Integer format;

  /**
   * 商品状态：-5未加工 -4已加工 0未审核1已通过2正在同步3已同步4同步失败
   **/
  @Column(name = "s_state")
  private Integer SState;

  @Column(name = "s_state_operator")
  private Integer SStateOperator;

  @Column(name = "s_state_operator_name")
  private String SStateOperatorName;

  @Column(name = "s_state_time")
  private Date SStateTime;

  /**
   * 好评度（样例值：0.95）
   **/
  @Column(name = "praise_rate")
  private Float praiseRate;

  /**
   * 评论数
   **/
  @Column(name = "comment_count")
  private Integer commentCount;

  @Column(name = "entity_package_count")
  private Integer entityPackageCount;

  @Column(name = "platform_key")
  private String platformKey;

  /**
   * 0：不显示；1：显示
   **/
  @Column(name = "is_display", nullable = false)
  private Integer isDisplay;

  /**
   * 书籍的编码(便于检索书籍)
   **/
  @Column(name = "edu_code")
  private String eduCode;

  public void setComId(String comId) {
    this.comId = comId;
  }

  public String getComId() {
    return this.comId;
  }

  public void setComName(String comName) {
    this.comName = comName;
  }

  public String getComName() {
    return this.comName;
  }

  public void setComType(Integer comType) {
    this.comType = comType;
  }

  public Integer getComType() {
    return this.comType;
  }

  public Integer getIsDisplay() {
    return isDisplay;
  }

  public void setIsDisplay(Integer isDisplay) {
    this.isDisplay = isDisplay;
  }

  public void setComTypeName(String comTypeName) {
    this.comTypeName = comTypeName;
  }

  public String getComTypeName() {
    return this.comTypeName;
  }

  public void setContentType(Integer contentType) {
    this.contentType = contentType;
  }

  public Integer getContentType() {
    return this.contentType;
  }

  public void setContentTypeName(String contentTypeName) {
    this.contentTypeName = contentTypeName;
  }

  public String getContentTypeName() {
    return this.contentTypeName;
  }

  public void setUsergroupType(Integer usergroupType) {
    this.usergroupType = usergroupType;
  }

  public Integer getUsergroupType() {
    return this.usergroupType;
  }

  public void setUsergroupName(String usergroupName) {
    this.usergroupName = usergroupName;
  }

  public String getUsergroupName() {
    return this.usergroupName;
  }

  public void setContentgroupType(Integer contentgroupType) {
    this.contentgroupType = contentgroupType;
  }

  public Integer getContentgroupType() {
    return this.contentgroupType;
  }

  public void setContentgroupTypeName(String contentgroupTypeName) {
    this.contentgroupTypeName = contentgroupTypeName;
  }

  public String getContentgroupTypeName() {
    return this.contentgroupTypeName;
  }

  public void setPhase(String phase) {
    this.phase = phase;
  }

  public String getPhase() {
    return this.phase;
  }

  public void setPhaseId(Integer phaseId) {
    this.phaseId = phaseId;
  }

  public Integer getPhaseId() {
    return this.phaseId;
  }

  public void setGradeLevel(String gradeLevel) {
    this.gradeLevel = gradeLevel;
  }

  public String getGradeLevel() {
    return this.gradeLevel;
  }

  public void setGradeLevelId(Integer gradeLevelId) {
    this.gradeLevelId = gradeLevelId;
  }

  public Integer getGradeLevelId() {
    return this.gradeLevelId;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getSubject() {
    return this.subject;
  }

  public void setSubjectId(Integer subjectId) {
    this.subjectId = subjectId;
  }

  public Integer getSubjectId() {
    return this.subjectId;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public String getPublisher() {
    return this.publisher;
  }

  public void setPublisherId(Integer publisherId) {
    this.publisherId = publisherId;
  }

  public Integer getPublisherId() {
    return this.publisherId;
  }

  public void setFascicule(String fascicule) {
    this.fascicule = fascicule;
  }

  public String getFascicule() {
    return this.fascicule;
  }

  public void setFasciculeId(Integer fasciculeId) {
    this.fasciculeId = fasciculeId;
  }

  public Integer getFasciculeId() {
    return this.fasciculeId;
  }

  public void setBookEdtion(String bookEdtion) {
    this.bookEdtion = bookEdtion;
  }

  public String getBookEdtion() {
    return this.bookEdtion;
  }

  public void setBookEdtionId(Integer bookEdtionId) {
    this.bookEdtionId = bookEdtionId;
  }

  public Integer getBookEdtionId() {
    return this.bookEdtionId;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  public String getKeywords() {
    return this.keywords;
  }

  public void setShelfType(Integer shelfType) {
    this.shelfType = shelfType;
  }

  public Integer getShelfType() {
    return this.shelfType;
  }

  public void setPrice(Float price) {
    this.price = price;
  }

  public Float getPrice() {
    return this.price;
  }

  public void setBookInTime(Date bookInTime) {
    this.bookInTime = bookInTime;
  }

  public Date getBookInTime() {
    return this.bookInTime;
  }

  public void setFormatName(String formatName) {
    this.formatName = formatName;
  }

  public String getFormatName() {
    return this.formatName;
  }

  public void setSysDelete(Integer sysDelete) {
    this.sysDelete = sysDelete;
  }

  public Integer getSysDelete() {
    return this.sysDelete;
  }

  public void setDataModifiedTime(Date dataModifiedTime) {
    this.dataModifiedTime = dataModifiedTime;
  }

  public Date getDataModifiedTime() {
    return this.dataModifiedTime;
  }

  public void setShelfTime(Date shelfTime) {
    this.shelfTime = shelfTime;
  }

  public Date getShelfTime() {
    return this.shelfTime;
  }

  public void setShelfer(String shelfer) {
    this.shelfer = shelfer;
  }

  public String getShelfer() {
    return this.shelfer;
  }

  public void setDownloadTimes(Long downloadTimes) {
    this.downloadTimes = downloadTimes;
  }

  public Long getDownloadTimes() {
    return this.downloadTimes;
  }

  public void setRecommend(Integer recommend) {
    this.recommend = recommend;
  }

  public Integer getRecommend() {
    return this.recommend;
  }

  public void setPraiseDegree(Integer praiseDegree) {
    this.praiseDegree = praiseDegree;
  }

  public Integer getPraiseDegree() {
    return this.praiseDegree;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getRemark() {
    return this.remark;
  }

  public void setRelationComId(String relationComId) {
    this.relationComId = relationComId;
  }

  public String getRelationComId() {
    return this.relationComId;
  }

  public void setComOrder(Integer comOrder) {
    this.comOrder = comOrder;
  }

  public Integer getComOrder() {
    return this.comOrder;
  }

  public void setSaleType(Integer saleType) {
    this.saleType = saleType;
  }

  public Integer getSaleType() {
    return this.saleType;
  }

  public void setFormat(Integer format) {
    this.format = format;
  }

  public Integer getFormat() {
    return this.format;
  }

  public void setSState(Integer SState) {
    this.SState = SState;
  }

  public Integer getSState() {
    return this.SState;
  }

  public void setSStateOperator(Integer SStateOperator) {
    this.SStateOperator = SStateOperator;
  }

  public Integer getSStateOperator() {
    return this.SStateOperator;
  }

  public void setSStateOperatorName(String SStateOperatorName) {
    this.SStateOperatorName = SStateOperatorName;
  }

  public String getSStateOperatorName() {
    return this.SStateOperatorName;
  }

  public void setSStateTime(Date SStateTime) {
    this.SStateTime = SStateTime;
  }

  public Date getSStateTime() {
    return this.SStateTime;
  }

  public void setPraiseRate(Float praiseRate) {
    this.praiseRate = praiseRate;
  }

  public Float getPraiseRate() {
    return this.praiseRate;
  }

  public void setCommentCount(Integer commentCount) {
    this.commentCount = commentCount;
  }

  public Integer getCommentCount() {
    return this.commentCount;
  }

  public void setEntityPackageCount(Integer entityPackageCount) {
    this.entityPackageCount = entityPackageCount;
  }

  public Integer getEntityPackageCount() {
    return this.entityPackageCount;
  }

  public void setPlatformKey(String platformKey) {
    this.platformKey = platformKey;
  }

  public String getPlatformKey() {
    return this.platformKey;
  }

  /**
   * Getter method for property <tt>eduCode</tt>.
   * 
   * @return property value of eduCode
   */
  public String getEduCode() {
    return eduCode;
  }

  /**
   * Setter method for property <tt>eduCode</tt>.
   * 
   * @param eduCode
   *          value to be assigned to property eduCode
   */
  public void setEduCode(String eduCode) {
    this.eduCode = eduCode;
  }

  @Override
  public boolean equals(final Object other) {
    if (!(other instanceof Book))
      return false;
    Book castOther = (Book) other;
    return new EqualsBuilder().append(comId, castOther.comId).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(comId).toHashCode();
  }
}
