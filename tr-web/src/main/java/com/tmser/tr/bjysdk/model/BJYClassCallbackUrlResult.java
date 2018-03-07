package com.tmser.tr.bjysdk.model;

/**
 * <pre>
 * 回调地址:<br> <strong>下课和视频回放</strong>
 * </pre>
 *
 * @author ljh
 * @version $Id: BJYClassCallbackUrlResult.java, v 1.0 2018年1月5日 下午4:18:47 ljh
 *          Exp $
 */
public class BJYClassCallbackUrlResult extends BJYMtgResult {

  /**
   * 回调地址
   */
  private String url;

  /**
   * Getter method for property <tt>url</tt>.
   * 
   * @return property value of url
   */
  public String getUrl() {
    return url;
  }

  /**
   * Setter method for property <tt>url</tt>.
   * 
   * @param url
   *          value to be assigned to property url
   */
  public void setUrl(String url) {
    this.url = url;
  }
}
