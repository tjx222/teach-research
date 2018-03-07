/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.classapi.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.tmser.tr.classapi.ChatInfo;
import com.tmser.tr.classapi.ClassUserType;
import com.tmser.tr.classapi.bo.ClassChartRecord;
import com.tmser.tr.classapi.bo.ClassInfo;
import com.tmser.tr.classapi.bo.ClassUser;
import com.tmser.tr.classapi.vo.ClassCallBackParam;

/**
 * <pre>
 * 课堂操作的服务接口
 * </pre>
 *
 * @author yangchao
 * @version $Id: ClassOperateService.java, v 1.0 2016年9月20日 上午10:32:09 yangchao
 *          Exp $
 */
public interface ClassOperateService {
  /**
   * 加入课堂事件名称
   */
  String JOIN_CLASS_EVENT = "_join_class_";

  String END_CLASS_EVENT = "_end_class_";

  /**
   * 初始化服务端
   */
  void init();

  /**
   * 结束客户端
   */
  void destroy();

  /**
   * 创建课堂
   * 
   * @param classInfo
   *          课堂基础信息，其中必填项为：
   * 
   * @return 成功返回课堂信息，失败返回null
   */
  ClassInfo createClass(ClassInfo classInfo);

  /**
   * 更新课堂
   * 
   * @param classInfo
   *          课堂基础信息，其中必填项为：
   *          id 不能为空
   * 
   * @return 成功返回课堂信息，失败返回null
   */
  ClassInfo updateClass(ClassInfo classInfo);

  /**
   * 删除课堂
   * 
   * @param classId
   *          课堂id
   *          id 不能为空
   *          将删除课堂信息，及课堂用户地址的所有信息
   *          失败将抛出异常
   */
  void deleteClass(String classId);

  /**
   * 加入课堂。
   * 
   * @param classid
   *          课堂id
   * @param userid
   *          用户id
   * @param username
   *          用户课堂显示名称
   * @param userType
   *          用户课堂类型
   * @return classUrl 返回加入后课堂信息，失败返回 null.
   */
  ClassUser joinClass(String classid, Integer userid, String username, ClassUserType userType);

  /**
   * 根据课堂id获取课堂信息
   * 
   * @param classid
   * @return
   */
  ClassInfo getClassInfo(String classid);

  /**
   * 上传文档
   * 
   * @param files
   *          文档列表
   * @param userId
   *          用户id
   * @param userName
   *          用户名
   * @return 成功返回文档id列表,空返回null
   */
  List<String> uploadDoc(List<File> files, Integer userId, String userName);

  /**
   * 上传文档
   * 
   * @param file
   *          文档列表
   * @param userId
   *          用户id
   * @param userName
   *          用户名
   * @return 成功返回文档id,空返回null
   */
  String uploadDoc(File file, Integer userId, String userName);

  /**
   * 上传文档
   * 
   * @param fileMap
   *          文档名称（包含后缀）<---->文档实体 map
   * @param userId
   *          用户id
   * @param userName
   *          用户名
   * @return 成功返回文档id列表,空返回null
   */
  List<String> uploadDocMap(Map<String, File> fileMap, Integer userId, String userName);

  /**
   * 上传文档
   * 
   * @param fileMap
   *          文档名称（包含后缀）<---->输入流 map
   * @param userId
   *          用户id
   * @param userName
   *          用户名
   * @return 成功返回文档id列表,空返回null
   */
  List<String> uploadDocStreamMap(Map<String, InputStream> fileMap, Integer userId, String userName);

  /**
   * 获取课堂录播地址。
   * 有多条获取最新的一条。
   * 并更新 classinfo 模型
   * 
   * @param classid
   * @return
   */
  ClassInfo generateClassRecordUrl(String classid);

  /**
   * 课堂结束后获取课堂用户交流记录
   * 只显示对所有人的记录
   * 
   * @param classid
   * @param page
   * @return
   */
  List<ChatInfo> listAllChatInfo(String classid);

  /**
   * 获取课堂结束状态的所有资源
   * 
   * @param classid
   * @return
   */
  List<String> listClassResources(String classid);

  /**
   */
  public List<ClassChartRecord> saveChartRecord(String chartJson);

  /**
   * 通过classId获取聊天记录，mainbo数据库中获取
   * 
   * @param classid
   * @return
   * @see com.tmser.tr.classapi.service.ClassOperateService#listAllChatInfo(java.lang.String)
   */
  List<ChatInfo> listAllChatInfoByClassId(String classid);

  /**
   * 查询房间状态
   * 
   * @param roomId
   * @return
   */
  Integer getRoomStatus(String roomId);

  /**
   * 课堂回调
   * 
   * @param param
   */
  void classCallback(ClassCallBackParam param);

  /**
   * 创建课堂回调地址
   * 
   * @param type
   *          {@link #Constants}
   * @return url
   */
  String setClassBackUrl(int type, String url);

  /**
   * 获取课堂回调地址
   * 
   * @param type
   *          {@link #Constants}
   * @return url
   */
  String getClassBackUrl(int type);

}
