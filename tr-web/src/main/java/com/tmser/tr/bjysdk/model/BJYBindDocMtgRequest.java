package com.tmser.tr.bjysdk.model;

import java.util.List;

import com.tmser.tr.bjysdk.Constants;

/**
 * Created by yfwang on 2017/11/10.
 */
public class BJYBindDocMtgRequest extends GenericRequest {

  public BJYBindDocMtgRequest(String partnerKey, String partnerId, long timestamp) {
    super(partnerKey, partnerId, Constants.bind_doc, timestamp);
  }

  /**
   * 必填项
   * 房间ID，14位
   */
  private String roomId;
  /**
   * 必填项
   * 文档资源号
   */
  private String fid;
  /**
   * 必填项
   * 请求接口参数签名
   */
  private String sign;

  private List<String> fidList;

  public String getRoomId() {
    return roomId;
  }

  public void setRoomId(String roomId) {
    this.roomId = roomId;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public String getFid() {
    return fid;
  }

  public void setFid(String fid) {
    this.fid = fid;
  }

  public List<String> getFidList() {
    return fidList;
  }

  public void setFidList(List<String> fidList) {
    this.fidList = fidList;
  }
}
