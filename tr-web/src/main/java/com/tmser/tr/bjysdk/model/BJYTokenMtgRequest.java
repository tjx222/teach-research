package com.tmser.tr.bjysdk.model;

import com.tmser.tr.bjysdk.Constants;

/**
 * Created by yfwang on 2017/11/10.
 */
public class BJYTokenMtgRequest extends GenericRequest {

  public BJYTokenMtgRequest(String partnerKey, String partnerId, String roomId, long timestamp) {
    super(partnerKey, partnerId, Constants.player_token, timestamp);
    this.roomId = roomId;
  }

  /**
   * 必填项
   * 房间ID，14位
   */
  private String roomId;
  /**
   * 非必填
   * 序列号 针对长期房间才会用到
   */
  private String sessionId;

  /**
   * 必填项
   * 过期时间，以秒为单位。如果传0则表示不过期
   */
  private long expiresIn;

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

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public long getExpiresIn() {
    return expiresIn;
  }

  public void setExpiresIn(long expiresIn) {
    this.expiresIn = expiresIn;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }
}
