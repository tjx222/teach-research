/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.classapi;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.QueryObject;

/**
 * <pre>
 * 课堂讨论信息
 * </pre>
 *
 * @author tmser
 * @version $Id: ChatInfo.java, v 1.0 2016年10月31日 上午10:44:17 tmser Exp $
 */
public class ChatInfo extends QueryObject {

  private static final long serialVersionUID = -6881872376078451912L;

  /**
   * 发送者id
   */
  private Integer from;
  /**
   * 接收者id
   */
  private Integer to;
  /**
   * 内容
   */
  private String content;
  /**
   * 发送时间
   */
  private Date time;

  private Integer userRole;

  private String userName;

  private String roomId;

  /**
   * Getter method for property <tt>userRole</tt>.
   * 
   * @return property value of userRole
   */
  public Integer getUserRole() {
    return userRole;
  }

  /**
   * Setter method for property <tt>userRole</tt>.
   * 
   * @param userRole
   *          value to be assigned to property userRole
   */
  public void setUserRole(Integer userRole) {
    this.userRole = userRole;
  }

  /**
   * Getter method for property <tt>userName</tt>.
   * 
   * @return property value of userName
   */
  public String getUserName() {
    return userName;
  }

  /**
   * Setter method for property <tt>userName</tt>.
   * 
   * @param userName
   *          value to be assigned to property userName
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Integer getFrom() {
    return from;
  }

  public void setFrom(Integer from) {
    this.from = from;
  }

  public Integer getTo() {
    return to;
  }

  public void setTo(Integer to) {
    this.to = to;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  /**
   * Getter method for property <tt>roomId</tt>.
   * 
   * @return property value of roomId
   */
  public String getRoomId() {
    return roomId;
  }

  /**
   * Setter method for property <tt>roomId</tt>.
   * 
   * @param roomId
   *          value to be assigned to property roomId
   */
  public void setRoomId(String roomId) {
    this.roomId = roomId;
  }

  @Override
  public boolean equals(final Object other) {
    if (!(other instanceof ChatInfo))
      return false;
    ChatInfo castOther = (ChatInfo) other;
    return new EqualsBuilder().append(from, castOther.from).append(to, castOther.to).append(time, castOther.time)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(time).toHashCode();
  }
}
