package com.tmser.tr.bjysdk.model;

import com.tmser.tr.bjysdk.Constants;

/**
 * Created by yfwang on 2017/11/7.
 */
public class BJYCreateMtgRequest extends GenericRequest {

  public BJYCreateMtgRequest(String partnerKey, String partnerId, long timestamp) {
    super(partnerKey, partnerId, Constants.room_create, timestamp);
  }

  /**
   * 必填项
   * 直播课标题，不超过20个字符或汉字，超过部分将进行截取
   */
  private String title;
  /**
   * 必填项
   * 开课时间, unix时间戳（秒）
   * 非长期房间，结束时间与开始时间间隔需大于15分钟并小于24小时，开始时间和结束时间范围必须在当前时间一年以内
   */
  private long startTime;
  /**
   * 必填项
   * 下课时间, unix时间戳（秒）
   * 非长期房间，结束时间与开始时间间隔需大于15分钟并小于24小时，开始时间和结束时间范围必须在当前时间一年以内
   */
  private long endTime;
  /**
   * 默认2
   * 1:一对一课 2:普通大班课 3:互动小班课 默认为普通大班课
   */
  private int type;
  /**
   * 默认0
   * 房间最大人数, 不传或传0表示不限制。注：一对一是1人，互动小班课人数不能超过10人，普通大班课可以不设置人数上限
   */
  private int maxUsers;
  /**
   * 默认1800
   * 学生可提前进入的时间，单位为秒，默认为30分钟
   */
  private int preEnterTime;
  /**
   * 默认0
   * 是否是长期房间，0:普通房间 1:长期房间 默认为普通房间（注：需要给账号开通长期房间权限才可以创建长期房间）
   */
  private int isLongTerm;
  /**
   *
   * 学生发言时是否自动开启摄像头 1:开启 2:不开启 默认会开启
   */
  private int speakCameraTurnon;
  /**
   * 老师是否启用设备检测 1:启用 2:不启用 默认不启用
   */
  private int teacherNeedDetectDevice;
  /**
   * 学生是否启用设备检测 1:启用 2:不启用 默认不启用
   */
  private int studentNeedDetectDevice;
  /**
   * 必填项
   * 签名
   */
  private String sign;

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

  public int getIsLongTerm() {
    return isLongTerm;
  }

  public void setIsLongTerm(int isLongTerm) {
    this.isLongTerm = isLongTerm;
  }

  public int getSpeakCameraTurnon() {
    return speakCameraTurnon;
  }

  public void setSpeakCameraTurnon(int speakCameraTurnon) {
    this.speakCameraTurnon = speakCameraTurnon;
  }

  public int getTeacherNeedDetectDevice() {
    return teacherNeedDetectDevice;
  }

  public void setTeacherNeedDetectDevice(int teacherNeedDetectDevice) {
    this.teacherNeedDetectDevice = teacherNeedDetectDevice;
  }

  public int getStudentNeedDetectDevice() {
    return studentNeedDetectDevice;
  }

  public void setStudentNeedDetectDevice(int studentNeedDetectDevice) {
    this.studentNeedDetectDevice = studentNeedDetectDevice;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }
}
