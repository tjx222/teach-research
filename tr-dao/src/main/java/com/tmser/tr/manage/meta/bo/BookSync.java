/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.meta.bo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.BaseObject;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 3020mt
 * @version $Id: BookSync.java, v 1.0 2016年7月15日 下午3:44:08 3020mt Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = BookSync.TABLE_NAME)
public class BookSync extends BaseObject {
	public static final String TABLE_NAME = "commidity_sync";

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	
	/** 
	 * Getter method for property <tt>id</tt>. 
	 * @return property value of id 
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Setter method for property <tt>id</tt>.
	 * @param id value to be assigned to property id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "com_id")
	private String comId;

	/**
	 * 商品名称
	 **/
	@Column(name = "com_name", nullable = false)
	private String comName;

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
	 * 书籍的上下册关系，即相互关联com_id
	 */
	@Column(name = "relation_com_id")
	private String relationComId;

	/**
	 * 本多媒体书排序
	 **/
	@Column(name = "com_order")
	private Integer comOrder;
	
	@Column(name="org_id")
	private Integer orgId;
	
	@Column(name = "area_id")
	private Integer areaId;
	
	/**
	 * Getter method for property <tt>comId</tt>.
	 * 
	 * @return property value of comId
	 */
	public String getComId() {
		return comId;
	}

	/**
	 * Setter method for property <tt>comId</tt>.
	 * 
	 * @param comId
	 *            value to be assigned to property comId
	 */
	public void setComId(String comId) {
		this.comId = comId;
	}

	/**
	 * Getter method for property <tt>comName</tt>.
	 * 
	 * @return property value of comName
	 */
	public String getComName() {
		return comName;
	}

	/**
	 * Setter method for property <tt>comName</tt>.
	 * 
	 * @param comName
	 *            value to be assigned to property comName
	 */
	public void setComName(String comName) {
		this.comName = comName;
	}

	/**
	 * Getter method for property <tt>phase</tt>.
	 * 
	 * @return property value of phase
	 */
	public String getPhase() {
		return phase;
	}

	/**
	 * Setter method for property <tt>phase</tt>.
	 * 
	 * @param phase
	 *            value to be assigned to property phase
	 */
	public void setPhase(String phase) {
		this.phase = phase;
	}

	/**
	 * Getter method for property <tt>phaseId</tt>.
	 * 
	 * @return property value of phaseId
	 */
	public Integer getPhaseId() {
		return phaseId;
	}

	/**
	 * Setter method for property <tt>phaseId</tt>.
	 * 
	 * @param phaseId
	 *            value to be assigned to property phaseId
	 */
	public void setPhaseId(Integer phaseId) {
		this.phaseId = phaseId;
	}

	/**
	 * Getter method for property <tt>gradeLevel</tt>.
	 * 
	 * @return property value of gradeLevel
	 */
	public String getGradeLevel() {
		return gradeLevel;
	}

	/**
	 * Setter method for property <tt>gradeLevel</tt>.
	 * 
	 * @param gradeLevel
	 *            value to be assigned to property gradeLevel
	 */
	public void setGradeLevel(String gradeLevel) {
		this.gradeLevel = gradeLevel;
	}

	/**
	 * Getter method for property <tt>gradeLevelId</tt>.
	 * 
	 * @return property value of gradeLevelId
	 */
	public Integer getGradeLevelId() {
		return gradeLevelId;
	}

	/**
	 * Setter method for property <tt>gradeLevelId</tt>.
	 * 
	 * @param gradeLevelId
	 *            value to be assigned to property gradeLevelId
	 */
	public void setGradeLevelId(Integer gradeLevelId) {
		this.gradeLevelId = gradeLevelId;
	}

	/**
	 * Getter method for property <tt>subject</tt>.
	 * 
	 * @return property value of subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Setter method for property <tt>subject</tt>.
	 * 
	 * @param subject
	 *            value to be assigned to property subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Getter method for property <tt>subjectId</tt>.
	 * 
	 * @return property value of subjectId
	 */
	public Integer getSubjectId() {
		return subjectId;
	}

	/**
	 * Setter method for property <tt>subjectId</tt>.
	 * 
	 * @param subjectId
	 *            value to be assigned to property subjectId
	 */
	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	/**
	 * Getter method for property <tt>publisher</tt>.
	 * 
	 * @return property value of publisher
	 */
	public String getPublisher() {
		return publisher;
	}

	/**
	 * Setter method for property <tt>publisher</tt>.
	 * 
	 * @param publisher
	 *            value to be assigned to property publisher
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	/**
	 * Getter method for property <tt>publisherId</tt>.
	 * 
	 * @return property value of publisherId
	 */
	public Integer getPublisherId() {
		return publisherId;
	}

	/**
	 * Setter method for property <tt>publisherId</tt>.
	 * 
	 * @param publisherId
	 *            value to be assigned to property publisherId
	 */
	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}

	/**
	 * Getter method for property <tt>fascicule</tt>.
	 * 
	 * @return property value of fascicule
	 */
	public String getFascicule() {
		return fascicule;
	}

	/**
	 * Setter method for property <tt>fascicule</tt>.
	 * 
	 * @param fascicule
	 *            value to be assigned to property fascicule
	 */
	public void setFascicule(String fascicule) {
		this.fascicule = fascicule;
	}

	/**
	 * Getter method for property <tt>fasciculeId</tt>.
	 * 
	 * @return property value of fasciculeId
	 */
	public Integer getFasciculeId() {
		return fasciculeId;
	}

	/**
	 * Setter method for property <tt>fasciculeId</tt>.
	 * 
	 * @param fasciculeId
	 *            value to be assigned to property fasciculeId
	 */
	public void setFasciculeId(Integer fasciculeId) {
		this.fasciculeId = fasciculeId;
	}

	/**
	 * Getter method for property <tt>bookEdtion</tt>.
	 * 
	 * @return property value of bookEdtion
	 */
	public String getBookEdtion() {
		return bookEdtion;
	}

	/**
	 * Setter method for property <tt>bookEdtion</tt>.
	 * 
	 * @param bookEdtion
	 *            value to be assigned to property bookEdtion
	 */
	public void setBookEdtion(String bookEdtion) {
		this.bookEdtion = bookEdtion;
	}

	/**
	 * Getter method for property <tt>bookEdtionId</tt>.
	 * 
	 * @return property value of bookEdtionId
	 */
	public Integer getBookEdtionId() {
		return bookEdtionId;
	}

	/**
	 * Setter method for property <tt>bookEdtionId</tt>.
	 * 
	 * @param bookEdtionId
	 *            value to be assigned to property bookEdtionId
	 */
	public void setBookEdtionId(Integer bookEdtionId) {
		this.bookEdtionId = bookEdtionId;
	}

	/**
	 * Getter method for property <tt>bookInTime</tt>.
	 * 
	 * @return property value of bookInTime
	 */
	public Date getBookInTime() {
		return bookInTime;
	}

	/**
	 * Setter method for property <tt>bookInTime</tt>.
	 * 
	 * @param bookInTime
	 *            value to be assigned to property bookInTime
	 */
	public void setBookInTime(Date bookInTime) {
		this.bookInTime = bookInTime;
	}

	/**
	 * Getter method for property <tt>formatName</tt>.
	 * 
	 * @return property value of formatName
	 */
	public String getFormatName() {
		return formatName;
	}

	/**
	 * Setter method for property <tt>formatName</tt>.
	 * 
	 * @param formatName
	 *            value to be assigned to property formatName
	 */
	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}

	/**
	 * Getter method for property <tt>relationComId</tt>.
	 * 
	 * @return property value of relationComId
	 */
	public String getRelationComId() {
		return relationComId;
	}

	/**
	 * Setter method for property <tt>relationComId</tt>.
	 * 
	 * @param relationComId
	 *            value to be assigned to property relationComId
	 */
	public void setRelationComId(String relationComId) {
		this.relationComId = relationComId;
	}

	/**
	 * Getter method for property <tt>comOrder</tt>.
	 * 
	 * @return property value of comOrder
	 */
	public Integer getComOrder() {
		return comOrder;
	}

	/**
	 * Setter method for property <tt>comOrder</tt>.
	 * 
	 * @param comOrder
	 *            value to be assigned to property comOrder
	 */
	public void setComOrder(Integer comOrder) {
		this.comOrder = comOrder;
	}
	
	

	/** 
   *
   * Getter method for property <tt>orgId</tt>. 
   *
   * @return orgId Integer
   */
  public Integer getOrgId() {
    return orgId;
  }

  /**
   *
   * Setter method for property <tt>orgId</tt>.
   *
   * @param orgId Integer value to be assigned to property orgId
   */
  public void setOrgId(Integer orgId) {
    this.orgId = orgId;
  }

  /** 
   *
   * Getter method for property <tt>areaId</tt>. 
   *
   * @return areaId Integer
   */
  public Integer getAreaId() {
    return areaId;
  }

  /**
   *
   * Setter method for property <tt>areaId</tt>.
   *
   * @param areaId Integer value to be assigned to property areaId
   */
  public void setAreaId(Integer areaId) {
    this.areaId = areaId;
  }
  

  @Override
	public boolean equals(final Object other) {
		if (!(other instanceof BookSync))
			return false;
		BookSync castOther = (BookSync) other;
		return new EqualsBuilder().append(comId, castOther.comId).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(comId).toHashCode();
	}

}
