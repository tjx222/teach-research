package com.tmser.tr.bjysdk.model;

import com.tmser.tr.bjysdk.Constants;

/**
 * 
 * <pre>
 * 聊天记录
 * </pre>
 *
 * @author ljh
 * @version $Id: BJYChatRecordMtgRequest.java, v 1.0 2017年12月22日 下午3:19:00 ljh
 *          Exp $
 */
public class BJYChatRecordMtgRequest extends GenericRequest {

  public BJYChatRecordMtgRequest(String partnerKey, String partnerId, long timestamp) {
    super(partnerKey, partnerId, Constants.chat_record, timestamp);
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
