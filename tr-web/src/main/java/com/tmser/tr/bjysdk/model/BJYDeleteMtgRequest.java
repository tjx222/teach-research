package com.tmser.tr.bjysdk.model;

import com.tmser.tr.bjysdk.Constants;

/**
 * Created by yfwang on 2017/11/10.
 */
public class BJYDeleteMtgRequest extends GenericRequest {

  public BJYDeleteMtgRequest(String partnerKey, String partnerId, String roomId, long timestamp) {
    super(partnerKey, partnerId, Constants.room_delete, timestamp);
    this.roomId = roomId;
  }

  /**
   * 必填项
   * 房间ID，14位
   */
  private String roomId;

  /**
   * 必填项
   * 请求接口参数签名
   */
  private String sign;

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
}
