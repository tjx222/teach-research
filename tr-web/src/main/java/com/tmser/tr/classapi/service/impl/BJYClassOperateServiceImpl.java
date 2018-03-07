package com.tmser.tr.classapi.service.impl;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.bjysdk.BJYProperties;
import com.tmser.tr.bjysdk.BJYVideo;
import com.tmser.tr.bjysdk.BJYVideoClient;
import com.tmser.tr.bjysdk.BJYVideoUtil;
import com.tmser.tr.bjysdk.Constants;
import com.tmser.tr.bjysdk.exception.VideoException;
import com.tmser.tr.bjysdk.model.BJYBindDocMtgRequest;
import com.tmser.tr.bjysdk.model.BJYBindDocMtgResult;
import com.tmser.tr.bjysdk.model.BJYChatRecordMtgRequest;
import com.tmser.tr.bjysdk.model.BJYChatRecordResult;
import com.tmser.tr.bjysdk.model.BJYClassCallbackUrlMtgRequest;
import com.tmser.tr.bjysdk.model.BJYClassCallbackUrlResult;
import com.tmser.tr.bjysdk.model.BJYClassStatusMtgRequest;
import com.tmser.tr.bjysdk.model.BJYClassStatusResult;
import com.tmser.tr.bjysdk.model.BJYCreateMtgRequest;
import com.tmser.tr.bjysdk.model.BJYCreateMtgResult;
import com.tmser.tr.bjysdk.model.BJYDeleteMtgRequest;
import com.tmser.tr.bjysdk.model.BJYMtgResult;
import com.tmser.tr.bjysdk.model.BJYRemoveDocMtgRequest;
import com.tmser.tr.bjysdk.model.BJYTokenMtgRequest;
import com.tmser.tr.bjysdk.model.BJYTokenMtgResult;
import com.tmser.tr.bjysdk.model.BJYUpdateMtgRequest;
import com.tmser.tr.bjysdk.model.BJYUploadDocMtgRequest;
import com.tmser.tr.bjysdk.model.BJYUploadDocMtgResult;
import com.tmser.tr.bjysdk.model.ParamKV;
import com.tmser.tr.classapi.ChatInfo;
import com.tmser.tr.classapi.ClassUserType;
import com.tmser.tr.classapi.bo.ClassChartRecord;
import com.tmser.tr.classapi.bo.ClassInfo;
import com.tmser.tr.classapi.bo.ClassUser;
import com.tmser.tr.classapi.dao.ClassChartRecordDao;
import com.tmser.tr.classapi.dao.ClassInfoDao;
import com.tmser.tr.classapi.dao.ClassUserDao;
import com.tmser.tr.classapi.service.ClassOperateService;
import com.tmser.tr.classapi.vo.ClassCallBackParam;
import com.tmser.tr.common.listener.Listenable;
import com.tmser.tr.common.listener.ListenableSupport;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.dao.ResourcesDao;
import com.tmser.tr.utils.StringUtils;

/**
 * Created by yfwang on 2017/11/7.
 */
@Transactional
public class BJYClassOperateServiceImpl extends ListenableSupport implements ClassOperateService, Listenable {

  private static final Logger logger = LoggerFactory.getLogger(ClassOperateService.class);

  @Autowired
  private ClassUserDao classUserDao;

  @Autowired
  private ResourcesDao resourcesDao;

  @Autowired
  private ClassInfoDao classInfoDao;

  @Autowired
  private ClassChartRecordDao classChartRecordDao;

  private BJYVideo v;

  private String siteId;

  private String key;

  @Autowired
  private BJYProperties bJYProperties;

  @Override
  public void init() {
    v = new BJYVideoClient();
  }

  @Override
  public void destroy() {
    v.shutdown();
  }

  @Override
  public ClassInfo createClass(ClassInfo classInfo) {
    BJYCreateMtgRequest createMtgRequest = new BJYCreateMtgRequest(key, siteId, v.timestamp());
    createMtgRequest.setTitle(classInfo.getTitle());
    createMtgRequest.setType(2);

    try {
      long start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(classInfo.getStartTimeStr()).getTime() / 1000;
      long end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(classInfo.getEndTimeStr()).getTime() / 1000;
      createMtgRequest.setStartTime(start);
      createMtgRequest.setEndTime(end);
    } catch (ParseException e) {
      throw new IllegalStateException("startTime or endTime convert failed, startTime = ["
          + classInfo.getStartTimeStr() + "],endTIme=[" + classInfo.getEndTimeStr() + "]");
    }
    try {
      BJYCreateMtgResult createMtg = v.joinMtg(createMtgRequest);
      if (createMtg == null || createMtg.getCode() != 0) {
        logger.warn("create class failed ! message: {}", createMtg == null ? " null result." : createMtg.getMsg());
        return null;
      }
      logger.info("createMtgRequest: code=[{}],message=[{}]", createMtg.getCode(), createMtg.getMsg());
      String roomId = createMtg.getRoom_id();
      if (StringUtils.isNotEmpty(roomId)) {
        classInfo.setId(roomId);
        classInfo.setTeacherCode(createMtg.getTeacher_code());
        classInfo.setAdminCode(createMtg.getAdmin_code());
        classInfo.setStudentCode(createMtg.getStudent_code());
        classInfo.setState(ClassInfo.INIT);
        // 绑定文件
        bindDocMtg(classInfo);

        // 存储相关信息
        classInfoDao.insert(classInfo);
        String jyhost = bJYProperties.getJyptHost();
        if (StringUtils.isEmpty(jyhost)) {
          jyhost = getHost();
        }
        if (StringUtils.isEmpty(getClassBackUrl(Constants.videoType))) {
          setClassBackUrl(Constants.videoType, jyhost);
        }
        if (StringUtils.isEmpty(getClassBackUrl(Constants.classEndType))) {
          setClassBackUrl(Constants.classEndType, jyhost);
        }
      }
      return classInfo;
    } catch (VideoException e) {
      logger.error("invoke interface JoinMtg error: code=[{}],message=[{}]", e.getErrorCode(), e.getErrorMessage());
    }
    return null;
  }

  private String getHost() {
    return new StringBuilder().append(WebThreadLocalUtils.getRequest().getScheme()).append("://")
        .append(WebThreadLocalUtils.getRequest().getServerName()).append(":")
        .append(WebThreadLocalUtils.getRequest().getServerPort())
        .append(WebThreadLocalUtils.getRequest().getContextPath()).toString();
  }

  private void bindDocMtg(ClassInfo classInfo) {
    if (classInfo.getDocIds() != null && classInfo.getDocIds().size() > 0) {
      BJYBindDocMtgRequest bindDocMtg = new BJYBindDocMtgRequest(key, siteId, v.timestamp());
      bindDocMtg.setRoomId(classInfo.getId());
      bindDocMtg.setFidList(classInfo.getDocIds());
      List<BJYBindDocMtgResult> bindResList = v.bindDocMtg(bindDocMtg);
      for (BJYBindDocMtgResult bindDoc : bindResList) {
        Resources res = new Resources();
        res.setId(bindDoc.getRedId());
        res.setDeviceId(bindDoc.getFid());
        resourcesDao.update(res);
        logger.info("bindDocMtgRequest: code=[{}],message=[{}],room_id=[{}],fid=[{}]", bindDoc.getCode(),
            bindDoc.getMsg(), bindDoc.getMsg());
      }
    }
  }

  private void unBindDocMtg(ClassInfo classInfo) {
    if (classInfo.getUnBindDocIds() != null && classInfo.getUnBindDocIds().size() > 0) {
      for (String fid : classInfo.getUnBindDocIds()) {
        try {
          BJYRemoveDocMtgRequest unbindDocMtg = new BJYRemoveDocMtgRequest(key, siteId, classInfo.getId(),
              v.timestamp());
          unbindDocMtg.setFid(Integer.valueOf(fid));
          BJYMtgResult result = v.unbindDocMtg(unbindDocMtg);
          logger.info("unBindDocMtg: code=[{}],message=[{}],room_id=[{}],rmsResId=[{}]", result.getCode(),
              result.getMsg(), classInfo.getId(), fid);
        } catch (VideoException e) {
          logger.error("invoke interface unBindDocMtg error: code=[{}],message=[{}]", e.getErrorCode(),
              e.getErrorMessage());
        }
      }

    }
  }

  @Override
  public ClassInfo updateClass(ClassInfo classInfo) {
    String roomId = classInfo.getId();
    BJYUpdateMtgRequest updateMtgRequest = new BJYUpdateMtgRequest(key, siteId, roomId, v.timestamp());
    ClassInfo old = classInfoDao.get(roomId);
    if (old == null) {
      throw new IllegalStateException("class doesn't exist, id = [" + roomId + "]");
    }
    updateMtgRequest.setTitle(classInfo.getTitle());
    try {
      if (!classInfo.getStartTimeStr().equals(old.getStartTimeStr())) {
        long start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(classInfo.getStartTimeStr()).getTime() / 1000;
        updateMtgRequest.setStartTime(start);
      }
      if (!classInfo.getEndTimeStr().equals(old.getEndTimeStr())) {
        long end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(classInfo.getEndTimeStr()).getTime() / 1000;
        updateMtgRequest.setEndTime(end);
      }
    } catch (ParseException e) {
      throw new IllegalStateException("startTime or endTime convert failed, startTime = ["
          + classInfo.getStartTimeStr() + "],endTIme=[" + classInfo.getEndTimeStr() + "]");
    }

    try {
      BJYMtgResult updateResult = v.getUpdateMtg(updateMtgRequest);
      if (updateResult == null) {
        return null;
      }
      logger.info("updateClass: code=[{}],message=[{}]", updateResult.getCode(), updateResult.getMsg());

      bindDocMtg(classInfo);
      unBindDocMtg(classInfo);
      classInfo.setDocIds(null);
      classInfo.setUnBindDocIds(null);
      // 存储相关信息
      classInfoDao.update(classInfo);
      return classInfo;
    } catch (VideoException e) {
      logger.error("invoke interface updateClass error: code=[{}],message=[{}]", e.getErrorCode(), e.getErrorMessage());
    }
    return null;
  }

  @Override
  public ClassInfo generateClassRecordUrl(String classid) {
    ClassInfo classInfo;
    if (classid != null && (classInfo = classInfoDao.get(classid)) != null) {
      // 视频课堂录制地址如已存在，直接返回
      if (StringUtils.isNotEmpty(classInfo.getRecordUrl())) {
        return classInfo;
      }
      BJYTokenMtgRequest tokenRequest = new BJYTokenMtgRequest(key, siteId, classInfo.getId(), v.timestamp());
      tokenRequest.setExpiresIn(0);
      try {
        BJYTokenMtgResult tokenResult = v.playRecord(tokenRequest);
        if (tokenResult == null) {
          return null;
        }
        logger.info("generateClassRecordUrl: code=[{}],message=[{}]", tokenResult.getCode(), tokenResult.getMsg());
        if (tokenResult.getCode() != 0) {
          logger.error("generateClassRecordUrl: code=[{}],message=[{}]", tokenResult.getCode(), tokenResult.getMsg());
        }
        if (StringUtils.isNotEmpty(tokenResult.getToken())) {
          String recordUrl = Constants.play_record + "?classid=" + classInfo.getId() + "&token="
              + tokenResult.getToken();
          classInfo.setRecordUrl(recordUrl);
          ClassInfo model = new ClassInfo();
          model.setId(classid);
          model.setRecordUrl(recordUrl);
          classInfoDao.update(model);
          return classInfo;
        }
        return null;
      } catch (VideoException e) {
        logger.error("invoke interface generateClassRecordUrl error: code=[{}],message=[{}]", e.getErrorCode(),
            e.getErrorMessage());
      }
    }
    return null;
  }

  @Override
  public void deleteClass(String classId) {
    if (StringUtils.isBlank(classId)) {
      return;
    }
    BJYDeleteMtgRequest delRequest = new BJYDeleteMtgRequest(key, siteId, classId, v.timestamp());
    try {
      BJYMtgResult delResult = v.deleteMtg(delRequest);
      if (delResult == null) {
        return;
      }
      logger.info("deleteClass: code=[{}],message=[{}]", delResult.getCode(), delResult.getMsg());
      if (delResult.getCode() != 0) {
        logger.error("deleteClass: code=[{}],message=[{}]", delResult.getCode(), delResult.getMsg());
      }
    } catch (VideoException e) {
      logger.error("invoke interface deleteClass error: code=[{}],message=[{}]", e.getErrorCode(), e.getErrorMessage());
    }
    classInfoDao.delete(classId);
    classUserDao.deleteByClassId(classId);
  }

  @Override
  public ClassUser joinClass(String classid, Integer userid, String username, ClassUserType userType) {
    ClassUser classUser = null;
    if (classid != null && (classInfoDao.get(classid)) != null) {
      ClassUser model = new ClassUser();
      model.setClassId(classid);
      model.setUserId(userid);
      classUser = classUserDao.getOne(model);
      if (classUser == null) {
        model.setUserType(userType == null ? ClassUserType.NORMAL.getValue() : userType.getValue());
        List<ParamKV> parames = new ArrayList<ParamKV>();
        parames.add(new ParamKV("room_id", classid));// 房间ID
        parames.add(new ParamKV("user_number", userid + ""));// 合作方账号体系下的用户ID号
        int roleId = 0;
        if (model.getUserType() == 1) {
          roleId = 1;// 0:学生 1:老师 2:管理员
        }
        parames.add(new ParamKV("user_role", roleId + ""));// 0:学生 1:老师 2:管理员
        parames.add(new ParamKV("user_name", username));// 显示的用户昵称
        parames.add(new ParamKV("user_avatar", ""));// 用户头像
        parames.add(new ParamKV("timestamp", v.timestamp() + ""));

        StringBuilder temp = new StringBuilder();
        Map<String, String> paramMap = BJYVideoUtil.hander(parames, key);
        try {
          for (String key : paramMap.keySet()) {
            temp.append((temp.toString().equals("") ? "" : "&") + key + "="
                + URLEncoder.encode(paramMap.get(key), "UTF-8"));
          }
        } catch (UnsupportedEncodingException e) {
          logger.warn("", e);
        }
        model.setClassUrl(Constants.room_enter + "?" + temp.toString());
        classUser = classUserDao.insert(model);
      }
    }
    if (classUser != null) {
      // 触发时间
      fireListenableEvent(ClassOperateService.JOIN_CLASS_EVENT, classUser);
    }
    return classUser;
  }

  @Override
  public ClassInfo getClassInfo(String classid) {
    if (StringUtils.isNotBlank(classid)) {
      return classInfoDao.get(classid);
    }
    return null;
  }

  @Override
  public List<String> uploadDoc(List<File> files, Integer userId, String userName) {
    if (files == null || files.size() == 0) {
      return null;
    }
    Map<String, File> fileMap = new HashMap<String, File>();
    for (File file : files) {
      fileMap.put(file.getName(), file);
    }
    return uploadDocMap(fileMap, userId, userName);
  }

  @Override
  public String uploadDoc(File file, Integer userId, String userName) {
    return null;
  }

  @Override
  public List<String> uploadDocMap(Map<String, File> fileMap, Integer userId, String userName) {
    BJYUploadDocMtgRequest uploadDocRequest = new BJYUploadDocMtgRequest(key, siteId, v.timestamp());
    uploadDocRequest.setAttachmentMap(fileMap);
    List<BJYUploadDocMtgResult> resList = v.fileUploadForeign(uploadDocRequest);
    List<String> docIds = new ArrayList<String>();
    for (BJYUploadDocMtgResult resMtg : resList) {
      if (resMtg.getCode() == 0) {
        docIds.add(resMtg.getFid() + ";" + resMtg.getResId());
      }
      logger.info("uploadDocRequest: code=[{}],message=[{}]", resMtg.getCode(), resMtg.getMsg());
    }
    return docIds;
  }

  @Override
  public List<String> uploadDocStreamMap(Map<String, InputStream> fileMap, Integer userId, String userName) {
    return null;
  }

  @Override
  public List<ChatInfo> listAllChatInfo(String roomId) {
    BJYChatRecordMtgRequest chatRecordMtgRequest = new BJYChatRecordMtgRequest(key, siteId, v.timestamp());
    chatRecordMtgRequest.setRoomId(roomId);
    try {
      BJYChatRecordResult result = v.getChatRecord(chatRecordMtgRequest);

      if (result == null || result.getCode() != 0) {
        logger.error("chatRecordMtgRequest: code=[{}],message=[{}]", result.getCode(), result.getMsg());
        return null;
      }
      logger.info("chatRecordMtgRequest: code=[{}],message=[{}]", result.getCode(), result.getMsg());

      List<BJYChatRecordResult> list = result.getList();
      List<ChatInfo> chats = new ArrayList<ChatInfo>();
      if (!CollectionUtils.isEmpty(list)) {
        for (BJYChatRecordResult bjyResult : list) {
          ChatInfo model = new ChatInfo();
          model.setRoomId(roomId);
          model.setTime(bjyResult.getTime());
          model.setContent(bjyResult.getContent());
          model.setFrom(bjyResult.getUser_number());
          model.setUserName(bjyResult.getUser_name());
          model.setUserRole(bjyResult.getUser_role());
          chats.add(model);
        }
      }
      return chats;
    } catch (VideoException e) {
      logger.error("invoke interface JoinMtg error: code=[{}],message=[{}]", e.getErrorCode(), e.getErrorMessage());
    }
    return null;
  }

  @Override
  public List<String> listClassResources(String classid) {
    return null;
  }

  @Override
  public List<ClassChartRecord> saveChartRecord(String roomId) {
    List<ClassChartRecord> insertList = new ArrayList<ClassChartRecord>();
    try {
      List<ChatInfo> chatInfos = this.listAllChatInfo(roomId);
      if (!CollectionUtils.isEmpty(chatInfos)) {
        for (ChatInfo chatInfo : chatInfos) {
          ClassChartRecord model = new ClassChartRecord();
          model.setContext(chatInfo.getContent());
          model.setRecordTime(chatInfo.getTime());
          model.setFromUserId(chatInfo.getFrom());
          model.setFromUserName(chatInfo.getUserName());
          model.setIsNormal(chatInfo.getUserRole());
          model.setOwnerId(chatInfo.getFrom());
          model.setMtgKey(chatInfo.getRoomId());
          insertList.add(model);
        }
      }
      if (insertList.size() > 0) {
        classChartRecordDao.batchInsert(insertList);
      }
    } catch (Exception e) {
      logger.error("", e);
    }
    return insertList;
  }

  @Override
  public List<ChatInfo> listAllChatInfoByClassId(String classid) {
    List<ClassChartRecord> listAll = saveChartRecord(classid);
    List<ChatInfo> chatInfoList = new ArrayList<ChatInfo>();
    for (ClassChartRecord chatInfo : listAll) {
      ChatInfo info = new ChatInfo();
      info.setContent(chatInfo.getContext());
      info.setFrom(chatInfo.getFromUserId());
      info.setTime(chatInfo.getRecordTime());
      chatInfoList.add(info);
    }
    return chatInfoList;
  }

  @Override
  public Object[] supportTypes() {
    return new Object[] { ClassOperateService.class };
  }

  /**
   * @param roomId
   * @return
   * @see com.tmser.tr.classapi.service.ClassOperateService#getRoomStatus(java.lang.String)
   */
  @Override
  public Integer getRoomStatus(String roomId) {
    BJYClassStatusMtgRequest clsassStatus = new BJYClassStatusMtgRequest(key, siteId, v.timestamp());
    clsassStatus.setRoomId(roomId);
    BJYClassStatusResult result = null;
    try {
      result = v.confStatus(clsassStatus);
    } catch (Exception e) {
      logger.error("", e);
      return null;
    }
    return result.getStatus();
  }

  /**
   * @param param
   * @see com.tmser.tr.classapi.service.ClassOperateService#classCallback(com.tmser.tr.classapi.vo.ClassCallBackParam)
   */
  @Override
  public void classCallback(ClassCallBackParam param) {
    ClassInfo classInfo = classInfoDao.get(param.getClassId());
    if (classInfo == null) {
      logger.warn("class not found ,id = {}", param.getClassId());
      return;
    }
    if (END_CLASS_EVENT.equals(param.getEventType())) {
      if (classInfo.getState() != ClassInfo.END) {
        classInfo.setState(ClassInfo.END);
        classInfoDao.update(classInfo);
        saveChartRecord(classInfo.getId());
        fireListenableEvent(END_CLASS_EVENT, param.getClassId());
      }
    }

  }

  /**
   * <p>
   * </p>
   * Getter method for property <tt>siteId</tt>.
   *
   * @return siteId String
   */
  public String getSiteId() {
    return siteId;
  }

  /**
   * <p>
   * </p>
   * Setter method for property <tt>siteId</tt>.
   *
   * @param siteId
   *          String value to be assigned to property siteId
   */
  public void setSiteId(String siteId) {
    this.siteId = siteId;
  }

  /**
   * <p>
   * </p>
   * Getter method for property <tt>key</tt>.
   *
   * @return key String
   */
  public String getKey() {
    return key;
  }

  /**
   * <p>
   * </p>
   * Setter method for property <tt>key</tt>.
   *
   * @param key
   *          String value to be assigned to property key
   */
  public void setKey(String key) {
    this.key = key;
  }

  /**
   * 
   * @param type
   *          {@link #Constants}
   * @return
   * @see com.tmser.tr.classapi.service.ClassOperateService#getClassBackUrl(int)
   */
  @Override
  public String getClassBackUrl(int type) {
    BJYClassCallbackUrlMtgRequest request = new BJYClassCallbackUrlMtgRequest(key,
        Constants.classEndType == type ? Constants.class_callback_get : Constants.transcode_call_get, siteId,
        v.timestamp());
    try {
      BJYClassCallbackUrlResult result = v.queryClassCallBackUrl(request);
      if (result == null) {
        return null;
      } else {
        if (result.getCode() != 0) {
          logger.error("getClassBackUrl: code=[{}],message=[{}]", result.getCode(), result.getMsg());
        } else {
          return result.getUrl();
        }
      }
    } catch (VideoException e) {
      logger.error("invoke interface getClassBackUrl error: code=[{}],message=[{}]", e.getErrorCode(),
          e.getErrorMessage());
    }
    return null;
  }

  /**
   * 
   * @param type
   * @return
   * @see com.tmser.tr.classapi.service.ClassOperateService#setClassBackUrl(int)
   */
  @Override
  public String setClassBackUrl(int type, String host) {
    BJYClassCallbackUrlMtgRequest request = new BJYClassCallbackUrlMtgRequest(key,
        Constants.classEndType == type ? Constants.class_callback_set : Constants.transcode_call_set, siteId,
        v.timestamp());
    String callbackUrl = Constants.classEndType == type ? host + "/jy/classapi/bjycallback" : host
        + "/jy/classapi/callbackPlayback";
    request.setUrl(callbackUrl);
    try {
      BJYClassCallbackUrlResult result = v.createClassCallBackUrl(request);
      if (result == null) {
        return null;
      } else {
        if (result.getCode() != 0) {
          logger.error("setClassBackUrl: code=[{}],message=[{}]", result.getCode(), result.getMsg());
        } else {
          return result.getUrl();
        }
      }
    } catch (VideoException e) {
      logger.error("invoke interface setClassBackUrl error: code=[{}],message=[{}]", e.getErrorCode(),
          e.getErrorMessage());
    }
    return null;
  }
}
