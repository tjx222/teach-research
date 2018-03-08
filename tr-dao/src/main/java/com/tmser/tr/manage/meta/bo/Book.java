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
  public static final String TABLE_NAME = "sys_book";

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
   * 0：不显示；1：显示
   **/
  @Column(name = "is_display", nullable = false)
  private Integer isDisplay;

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
