package com.tmser.tr.bjysdk.model;

/**
 * Created by yfwang on 2017/11/15.
 */
public class GenericRequest {

  /**
   * 必填项
   * 合作方id
   */
  private String partnerId;
  /**
   * 必填项
   * 合作Key
   */
  private String partnerKey;
  /**
   * 必填项
   * 请求路径
   */
  private String relativePath;
  /**
   * 必填项
   * 当前时间，unix时间戳(秒)
   */
  private long timestamp;

  public GenericRequest(String partnerKey, String partnerId, String relativePath, long timestamp) {
    this.partnerId = partnerId;
    this.partnerKey = partnerKey;
    this.relativePath = relativePath;
    this.timestamp = timestamp;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public String getPartnerId() {
    return partnerId;
  }

  public void setPartnerId(String partnerId) {
    this.partnerId = partnerId;
  }

  public String getPartnerKey() {
    return partnerKey;
  }

  public void setPartnerKey(String partnerKey) {
    this.partnerKey = partnerKey;
  }

  public String getRelativePath() {
    return relativePath;
  }

  public void setRelativePath(String relativePath) {
    this.relativePath = relativePath;
  }
}
