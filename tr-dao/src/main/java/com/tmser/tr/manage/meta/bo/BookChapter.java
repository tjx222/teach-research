/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.meta.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.QueryObject;

/**
 * 书籍的章节目录 Entity
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: CommdityBookChapter.java, v 1.0 2015-02-04 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = BookChapter.TABLE_NAME)
public class BookChapter extends QueryObject {
  public static final String TABLE_NAME = "sys_book_chapter";

  /**
   * 主键
   **/
  @Id
  @Column(name = "chapter_id")
  private String chapterId;

  /**
   * 名称
   **/
  @Column(name = "chapter_name", nullable = false)
  private String chapterName;

  /**
   * 商品书籍id
   **/
  @Column(name = "com_id", nullable = false)
  private String comId;

  /**
   * 父节点
   **/
  @Column(name = "parent_id", nullable = false)
  private String parentId;

  /**
   * 章节层级
   **/
  @Column(name = "chapter_level", nullable = false)
  private Integer chapterLevel;

  /**
   * 开始位置
   **/
  @Column(name = "start_index")
  private Integer startIndex;

  /**
   * 结束位置
   **/
  @Column(name = "end_index")
  private Integer endIndex;

  /**
   * 章节顺序
   **/
  @Column(name = "order_num", nullable = false)
  private Integer orderNum;

  /**
   * 页数
   **/
  @Column(name = "pagenum")
  private Integer pagenum;

  /**
   * 知识点
   **/
  @Column(name = "is_chapter")
  private Integer isChapter;

  public void setChapterId(String chapterId) {
    this.chapterId = chapterId;
  }

  public String getChapterId() {
    return this.chapterId;
  }

  public void setChapterName(String chapterName) {
    this.chapterName = chapterName;
  }

  public String getChapterName() {
    return this.chapterName;
  }

  public void setComId(String comId) {
    this.comId = comId;
  }

  public String getComId() {
    return this.comId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getParentId() {
    return this.parentId;
  }

  public void setChapterLevel(Integer chapterLevel) {
    this.chapterLevel = chapterLevel;
  }

  public Integer getChapterLevel() {
    return this.chapterLevel;
  }

  public void setStartIndex(Integer startIndex) {
    this.startIndex = startIndex;
  }

  public Integer getStartIndex() {
    return this.startIndex;
  }

  public void setEndIndex(Integer endIndex) {
    this.endIndex = endIndex;
  }

  public Integer getEndIndex() {
    return this.endIndex;
  }

  public void setOrderNum(Integer orderNum) {
    this.orderNum = orderNum;
  }

  public Integer getOrderNum() {
    return this.orderNum;
  }

  public void setPagenum(Integer pagenum) {
    this.pagenum = pagenum;
  }

  public Integer getPagenum() {
    return this.pagenum;
  }

  /**
   * Getter method for property <tt>isChapter</tt>.
   *
   * @return isChapter Integer
   */
  public Integer getIsChapter() {
    return isChapter;
  }

  /**
   * Setter method for property <tt>isChapter</tt>.
   *
   * @param isChapter
   *          Integer value to be assigned to property isChapter
   */
  public void setIsChapter(Integer isChapter) {
    this.isChapter = isChapter;
  }

  @Override
  public boolean equals(final Object other) {
    if (!(other instanceof BookChapter))
      return false;
    BookChapter castOther = (BookChapter) other;
    return new EqualsBuilder().append(chapterId, castOther.chapterId).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(chapterId).toHashCode();
  }
}
