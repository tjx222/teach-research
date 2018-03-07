package com.tmser.tr.bjysdk.model;

import com.tmser.tr.bjysdk.Constants;

/**
 * Created by yfwang on 2017/11/10.
 */
public class BJYUpdateMtgRequest extends GenericRequest {

  public BJYUpdateMtgRequest(String partnerKey, String partnerId, String roomId, long timestamp) {
    super(partnerKey, partnerId, Constants.room_update, timestamp);
    this.roomId = roomId;
  }

  /**
   * 必填项
   * 房间ID，14位
   */
  private String roomId;
  /**
   * 非必填
   * 房间标题
   */
  private String title;

  /**
   * 非必填
   * 开课时间, unix时间戳(秒)
   */
  private long startTime;
  /**
   * 非必填
   * 结束时间, unix时间戳(秒)
   */
  private long endTime;
  /**
   * 非必填
   * 1:一对一课 2:普通大班课 3:小班课
   */
  private int type;
  /**
   * 非必填
   * 房间最大人数
   */
  private int maxUsers;
  /**
   * 非必填
   * 学生可提前进入的时间，单位为秒
   */
  private int preEnterTime;

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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public long getStartTime() {
    return startTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public long getEndTime() {
    return endTime;
  }

  public void setEndTime(long endTime) {
    this.endTime = endTime;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getMaxUsers() {
    return maxUsers;
  }

  public void setMaxUsers(int maxUsers) {
    this.maxUsers = maxUsers;
  }

  public int getPreEnterTime() {
    return preEnterTime;
  }

  public void setPreEnterTime(int preEnterTime) {
    this.preEnterTime = preEnterTime;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }
}
