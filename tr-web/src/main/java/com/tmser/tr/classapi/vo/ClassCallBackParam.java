/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */

package com.tmser.tr.classapi.vo;

import java.io.Serializable;

/**
 * <pre>
 *
 * </pre>
 *
 * @author ljh
 * @version $Id: ClassCallBackParam.java, v 1.0 2017年12月26日 上午10:19:32 ljh Exp $
 */
public class ClassCallBackParam implements Serializable {

  /**
   * <pre>
   *
   * </pre>
   */
  private static final long serialVersionUID = 1L;

  private String classId;

  private String eventType;

  /**
   * <p>
   * </p>
   * Getter method for property <tt>classId</tt>.
   *
   * @return classId String
   */
  public String getClassId() {
    return classId;
  }

  /**
   * <p>
   * </p>
   * Setter method for property <tt>classId</tt>.
   *
   * @param classId
   *          String value to be assigned to property classId
   */
  public void setClassId(String classId) {
    this.classId = classId;
  }

  /**
   * <p>
   * </p>
   * Getter method for property <tt>eventType</tt>.
   *
   * @return eventType Integer
   */
  public String getEventType() {
    return eventType;
  }

  /**
   * <p>
   * </p>
   * Setter method for property <tt>eventType</tt>.
   *
   * @param eventType
   *          Integer value to be assigned to property eventType
   */
  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

}
