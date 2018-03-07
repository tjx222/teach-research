package com.tmser.tr.bjysdk.model;

import java.io.File;
import java.util.Map;

import com.tmser.tr.bjysdk.Constants;

/**
 * Created by yfwang on 2017/11/10.
 */
public class BJYUploadDocMtgRequest extends GenericRequest {

  public BJYUploadDocMtgRequest(String partnerKey, String partnerId, long timestamp) {
    super(partnerKey, partnerId, Constants.upload_doc, timestamp);
  }

  /**
   * 非必填
   * 教室号，如果传了教室号则文档自动绑定到该教室，不传则不绑定
   */
  private String roomId;

  /**
   * 非必填
   * 是否使用动效PPT，只针对PPT有效
   */
  private int pptAnimation = -1;

  /**
   * 必填项
   * 要上传的文件（文件不参数签名的计算）
   */
  private Map<String, File> attachmentMap;

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

  public int getPptAnimation() {
    return pptAnimation;
  }

  public void setPptAnimation(int pptAnimation) {
    this.pptAnimation = pptAnimation;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public Map<String, File> getAttachmentMap() {
    return attachmentMap;
  }

  public void setAttachmentMap(Map<String, File> attachmentMap) {
    this.attachmentMap = attachmentMap;
  }
}
