package com.tmser.tr.bjysdk.model;

/**
 * <pre>
 * 回调地址:<br> <strong>下课和视频回放</strong>
 * </pre>
 *
 * @author ljh
 * @version $Id: BJYClassCallbackUrlMtgRequest.java, v 1.0 2018年1月5日 下午4:19:06
 *          ljh Exp $
 */
public class BJYClassCallbackUrlMtgRequest extends GenericRequest {

  public BJYClassCallbackUrlMtgRequest(String partnerKey, String url, String partnerId, long timestamp) {
    super(partnerKey, partnerId, url, timestamp);
  }

  /**
   * 回调url
   */
  private String url;
  /**
   * 必填项
   * 请求接口参数签名
   */
  private String sign;

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

  /**
   * Getter method for property <tt>sign</tt>.
   * 
   * @return property value of sign
   */
  public String getSign() {
    return sign;
  }

  /**
   * Setter method for property <tt>sign</tt>.
   * 
   * @param sign
   *          value to be assigned to property sign
   */
  public void setSign(String sign) {
    this.sign = sign;
  }
}
