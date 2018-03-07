package com.tmser.tr.bjysdk.model;

import java.util.Date;
import java.util.List;

/**
 * 
 * <pre>
 * 聊天结果
 * </pre>
 *
 * @author ljh
 * @version $Id: BJYChatRecordResult.java, v 1.0 2017年12月22日 下午3:42:31 ljh Exp $
 */
public class BJYChatRecordResult extends BJYMtgResult {

  /**
   * 时间
   */
  private Date time;
  /**
   * 用户编号
   */
  private Integer user_number;
  /**
   * 用户类型:1 用户角色 0:学生 1:老师 2:助教
   */
  private Integer user_role;
  /**
   * 用户名
   */
  private String user_name;
  /**
   * 聊天内容
   */
  private String content;

  private List<BJYChatRecordResult> list;

  /**
   * Getter method for property <tt>list</tt>.
   * 
   * @return property value of list
   */
  public List<BJYChatRecordResult> getList() {
    return list;
  }

  /**
   * Setter method for property <tt>list</tt>.
   * 
   * @param list
   *          value to be assigned to property list
   */
  public void setList(List<BJYChatRecordResult> list) {
    this.list = list;
  }

  /**
   * Getter method for property <tt>time</tt>.
   * 
   * @return property value of time
   */
  public Date getTime() {
    return time;
  }

  /**
   * Setter method for property <tt>time</tt>.
   * 
   * @param time
   *          value to be assigned to property time
   */
  public void setTime(Date time) {
    this.time = time;
  }

  /**
   * Getter method for property <tt>user_number</tt>.
   * 
   * @return property value of user_number
   */
  public Integer getUser_number() {
    return user_number;
  }

  /**
   * Setter method for property <tt>user_number</tt>.
   * 
   * @param user_number
   *          value to be assigned to property user_number
   */
  public void setUser_number(Integer user_number) {
    this.user_number = user_number;
  }

  /**
   * Getter method for property <tt>user_role</tt>.
   * 
   * @return property value of user_role
   */
  public Integer getUser_role() {
    return user_role;
  }

  /**
   * Setter method for property <tt>user_role</tt>.
   * 
   * @param user_role
   *          value to be assigned to property user_role
   */
  public void setUser_role(Integer user_role) {
    this.user_role = user_role;
  }

  /**
   * Getter method for property <tt>user_name</tt>.
   * 
   * @return property value of user_name
   */
  public String getUser_name() {
    return user_name;
  }

  /**
   * Setter method for property <tt>user_name</tt>.
   * 
   * @param user_name
   *          value to be assigned to property user_name
   */
  public void setUser_name(String user_name) {
    this.user_name = user_name;
  }

  /**
   * Getter method for property <tt>content</tt>.
   * 
   * @return property value of content
   */
  public String getContent() {
    return content;
  }

  /**
   * Setter method for property <tt>content</tt>.
   * 
   * @param content
   *          value to be assigned to property content
   */
  public void setContent(String content) {
    this.content = content;
  }

}
