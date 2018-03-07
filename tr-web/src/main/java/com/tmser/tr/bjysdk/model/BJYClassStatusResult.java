package com.tmser.tr.bjysdk.model;

/**
 * <pre>
 * 课堂状态:1:上课中 0:不在上课中
 * </pre>
 *
 * @author ljh
 * @version $Id: BJYClassStatusResult.java, v 1.0 2017年12月25日 下午5:02:17 ljh Exp
 *          $
 */
public class BJYClassStatusResult extends BJYMtgResult {

  public static final int STATUS_LIVE = 1;
  public static final int STATUS_NOT_LIVE = 0;
  private Integer status;

  /**
   * <p>
   * </p>
   * Getter method for property <tt>status</tt>.
   *
   * @return status Integer
   */
  public Integer getStatus() {
    return status;
  }

  /**
   * <p>
   * </p>
   * Setter method for property <tt>status</tt>.
   *
   * @param status
   *          Integer value to be assigned to property status
   */
  public void setStatus(Integer status) {
    this.status = status;
  }
}
