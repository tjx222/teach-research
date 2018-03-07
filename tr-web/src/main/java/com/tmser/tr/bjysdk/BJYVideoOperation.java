package com.tmser.tr.bjysdk;

import static com.tmser.tr.bjysdk.common.CodingUtils.assertParameterNotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tmser.tr.bjysdk.common.ResponseParseException;
import com.tmser.tr.bjysdk.exception.ClientException;
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
import com.tmser.tr.bjysdk.model.GenericRequest;
import com.tmser.tr.bjysdk.model.ParamKV;
import com.tmser.tr.utils.FileUtils;
import com.tmser.tr.utils.StringUtils;

/**
 * Video Operation
 */
public class BJYVideoOperation {

  protected <T extends GenericRequest> void assertGenericRequestNotNull(T gr) {
    assertParameterNotNull(gr.getRelativePath(), "relativePath");
  }

  /**
   * Complete multipart upload.
   */
  public BJYCreateMtgResult getJoinMtg(BJYCreateMtgRequest createMtgRequest) throws VideoException {
    assertGenericRequestNotNull(createMtgRequest);
    assertParameterNotNull(createMtgRequest.getPartnerId(), "partner_id");
    assertParameterNotNull(createMtgRequest.getTitle(), "title");
    assertParameterNotNull(createMtgRequest.getStartTime(), "start_time");
    assertParameterNotNull(createMtgRequest.getEndTime(), "end_time");
    assertParameterNotNull(createMtgRequest.getTimestamp(), "timestamp");
    assertParameterNotNull(createMtgRequest.getType(), "type");

    List<ParamKV> parames = new ArrayList<ParamKV>();
    parames.add(new ParamKV("title", createMtgRequest.getTitle()));
    parames.add(new ParamKV("start_time", createMtgRequest.getStartTime() + ""));
    parames.add(new ParamKV("end_time", createMtgRequest.getEndTime() + ""));
    parames.add(new ParamKV("type", createMtgRequest.getType() + ""));
    parames.add(new ParamKV("partner_id", createMtgRequest.getPartnerId() + ""));
    parames.add(new ParamKV("timestamp", createMtgRequest.getTimestamp() + ""));
    Map<String, String> paramMap = BJYVideoUtil.hander(parames, createMtgRequest.getPartnerKey());

    String content = SimpleHttpClient.sendMessage(createMtgRequest.getRelativePath(), paramMap);
    if (StringUtils.isEmpty(content)) {
      return null;
    }
    try {
      return (new JSONResponseParser<BJYCreateMtgResult>(BJYCreateMtgResult.class)).parse(content);
    } catch (ResponseParseException e) {
      throw new VideoException("content parse to " + BJYCreateMtgResult.class + " Error:content=" + content);
    }
  }

  /**
   * Complete multipart upload.
   */
  public BJYMtgResult getUpdateMtg(BJYUpdateMtgRequest updateMtgRequest) throws VideoException {

    assertGenericRequestNotNull(updateMtgRequest);
    assertParameterNotNull(updateMtgRequest.getPartnerId(), "partner_id");
    assertParameterNotNull(updateMtgRequest.getRoomId(), "roomId");
    assertParameterNotNull(updateMtgRequest.getTimestamp(), "timestamp");

    List<ParamKV> parames = new ArrayList<ParamKV>();
    if (StringUtils.isNotEmpty(updateMtgRequest.getTitle())) {
      parames.add(new ParamKV("title", updateMtgRequest.getTitle()));
    }
    parames.add(new ParamKV("room_id", updateMtgRequest.getRoomId()));
    if (updateMtgRequest.getStartTime() != 0) {
      parames.add(new ParamKV("start_time", updateMtgRequest.getStartTime() + ""));
    }
    if (updateMtgRequest.getEndTime() != 0) {
      parames.add(new ParamKV("end_time", updateMtgRequest.getEndTime() + ""));
    }
    parames.add(new ParamKV("partner_id", updateMtgRequest.getPartnerId() + ""));
    parames.add(new ParamKV("timestamp", updateMtgRequest.getTimestamp() + ""));

    Map<String, String> paramMap = BJYVideoUtil.hander(parames, updateMtgRequest.getPartnerKey());

    String content = SimpleHttpClient.sendMessage(updateMtgRequest.getRelativePath(), paramMap);
    if (StringUtils.isEmpty(content)) {
      return null;
    }
    try {
      return (new JSONResponseParser<BJYMtgResult>(BJYMtgResult.class)).parse(content);
    } catch (ResponseParseException e) {
      throw new VideoException("content parse to " + BJYMtgResult.class + " Error:content=" + content);
    }

  }

  public BJYTokenMtgResult playRecord(BJYTokenMtgRequest tokenMtgRequest) throws VideoException {
    assertGenericRequestNotNull(tokenMtgRequest);
    assertParameterNotNull(tokenMtgRequest.getPartnerId(), "partner_id");
    assertParameterNotNull(tokenMtgRequest.getRoomId(), "room_id");
    assertParameterNotNull(tokenMtgRequest.getExpiresIn(), "expires_in");
    assertParameterNotNull(tokenMtgRequest.getTimestamp(), "timestamp");

    // tokenMtgRequest.setRoomId("17110847602218");
    List<ParamKV> parames = new ArrayList<ParamKV>();
    parames.add(new ParamKV("partner_id", tokenMtgRequest.getPartnerId() + ""));
    parames.add(new ParamKV("room_id", tokenMtgRequest.getRoomId() + ""));
    parames.add(new ParamKV("expires_in", tokenMtgRequest.getExpiresIn() + ""));
    parames.add(new ParamKV("timestamp", tokenMtgRequest.getTimestamp() + ""));

    Map<String, String> paramMap = BJYVideoUtil.hander(parames, tokenMtgRequest.getPartnerKey());
    String content = SimpleHttpClient.sendMessage(tokenMtgRequest.getRelativePath(), paramMap);
    if (StringUtils.isEmpty(content)) {
      return null;
    }
    try {
      return (new JSONResponseParser<BJYTokenMtgResult>(BJYTokenMtgResult.class)).parse(content);
    } catch (ResponseParseException e) {
      throw new VideoException("content parse to " + BJYTokenMtgResult.class + " Error:content=" + content);
    }
  }

  /**
   * Upload input stream or file to server.
   */
  public List<BJYUploadDocMtgResult> putObject(BJYUploadDocMtgRequest uploadDocRequest) throws VideoException,
      ClientException {
    assertGenericRequestNotNull(uploadDocRequest);
    assertParameterNotNull(uploadDocRequest.getPartnerId(), "partner_id");

    Map<String, File> fileMap = uploadDocRequest.getAttachmentMap();
    List<BJYUploadDocMtgResult> msgResult = new ArrayList<BJYUploadDocMtgResult>();
    for (String fileName : fileMap.keySet()) {

      List<ParamKV> parames = new ArrayList<ParamKV>();
      parames.add(new ParamKV("partner_id", uploadDocRequest.getPartnerId() + ""));
      if (StringUtils.isNotEmpty(uploadDocRequest.getRoomId())) {
        parames.add(new ParamKV("room_id", uploadDocRequest.getRoomId() + ""));
      }
      parames.add(new ParamKV("timestamp", uploadDocRequest.getTimestamp() + ""));

      File stream = fileMap.get(fileName);

      String suffix = stream.getName().substring(stream.getName().lastIndexOf(".") + 1);
      if ("ppt".equals(suffix) || "pptx".equals(suffix)) {
        parames.add(new ParamKV("ppt_animation", "1"));
      }
      Map<String, String> paramMap = BJYVideoUtil.hander(parames, uploadDocRequest.getPartnerKey());

      String content = SimpleHttpClient.sendFileMessage(uploadDocRequest.getRelativePath(), paramMap, stream, fileName);
      if (StringUtils.isEmpty(content)) {
        BJYUploadDocMtgResult res = new BJYUploadDocMtgResult();
        res.setMsg("putObject 请求数据返回为空，fileName" + fileName + ",room_id=" + uploadDocRequest.getRoomId());
        res.setCode(1);
        msgResult.add(res);
        continue;
      }
      try {
        BJYUploadDocMtgResult docMtgResult = (new JSONResponseParser<BJYUploadDocMtgResult>(BJYUploadDocMtgResult.class))
            .parse(content);
        docMtgResult.setResId(FileUtils.getFileNameNoExtension(fileName));
        msgResult.add(docMtgResult);
      } catch (ResponseParseException e) {
        e.printStackTrace();
        BJYUploadDocMtgResult res = new BJYUploadDocMtgResult();
        res.setMsg(" putObject 数据解析出错,cotent=" + content + "，fileName" + fileName + ",room_id="
            + uploadDocRequest.getRoomId());
        res.setCode(1);
        msgResult.add(res);
      }
    }
    return msgResult;
  }

  public BJYMtgResult deleteMtg(BJYDeleteMtgRequest delMtgRequest) {
    assertGenericRequestNotNull(delMtgRequest);
    assertParameterNotNull(delMtgRequest.getPartnerId(), "partner_id");
    assertParameterNotNull(delMtgRequest.getRoomId(), "roomId");
    assertParameterNotNull(delMtgRequest.getTimestamp(), "timestamp");

    List<ParamKV> parames = new ArrayList<ParamKV>();
    parames.add(new ParamKV("room_id", delMtgRequest.getRoomId()));
    parames.add(new ParamKV("partner_id", delMtgRequest.getPartnerId() + ""));
    parames.add(new ParamKV("timestamp", delMtgRequest.getTimestamp() + ""));

    Map<String, String> paramMap = BJYVideoUtil.hander(parames, delMtgRequest.getPartnerKey());

    String content = SimpleHttpClient.sendMessage(delMtgRequest.getRelativePath(), paramMap);
    if (StringUtils.isEmpty(content)) {
      return null;
    }
    try {
      return (new JSONResponseParser<BJYMtgResult>(BJYMtgResult.class)).parse(content);
    } catch (ResponseParseException e) {
      throw new VideoException("content parse to " + BJYMtgResult.class + " Error:content=" + content);
    }
  }

  public List<BJYBindDocMtgResult> bindDocMtg(BJYBindDocMtgRequest bindDocMtg) {
    assertGenericRequestNotNull(bindDocMtg);
    assertParameterNotNull(bindDocMtg.getRoomId(), "room_id");
    assertParameterNotNull(bindDocMtg.getPartnerId(), "partner_id");

    List<BJYBindDocMtgResult> msgResult = new ArrayList<BJYBindDocMtgResult>();

    for (String fid : bindDocMtg.getFidList()) {
      List<ParamKV> parames = new ArrayList<ParamKV>();
      parames.add(new ParamKV("partner_id", bindDocMtg.getPartnerId() + ""));
      parames.add(new ParamKV("room_id", bindDocMtg.getRoomId() + ""));

      String[] data = fid.split(";");

      parames.add(new ParamKV("fid", data[0]));
      parames.add(new ParamKV("timestamp", bindDocMtg.getTimestamp() + ""));
      Map<String, String> paramMap = BJYVideoUtil.hander(parames, bindDocMtg.getPartnerKey());
      String content = SimpleHttpClient.sendMessage(bindDocMtg.getRelativePath(), paramMap);

      if (StringUtils.isEmpty(content)) {
        BJYBindDocMtgResult res = new BJYBindDocMtgResult();
        res.setMsg("bindDocMtg 请求数据返回为空，room_id=" + bindDocMtg.getRoomId() + ",fid=" + fid);
        res.setCode(1);
        msgResult.add(res);
        continue;
      }
      try {
        BJYBindDocMtgResult docMtgResult = (new JSONResponseParser<BJYBindDocMtgResult>(BJYBindDocMtgResult.class))
            .parse(content);
        docMtgResult.setRedId(data[1]);
        msgResult.add(docMtgResult);
      } catch (ResponseParseException e) {
        e.printStackTrace();
        BJYBindDocMtgResult res = new BJYBindDocMtgResult();
        res.setMsg("bindDocMtg 数据解析出错，room_id=" + bindDocMtg.getRoomId() + ",fid=" + fid);
        res.setCode(1);
        msgResult.add(res);
      }
    }
    return msgResult;
  }

  public BJYMtgResult unbindDocMtg(BJYRemoveDocMtgRequest removeDocMtg) {
    List<ParamKV> parames = new ArrayList<ParamKV>();
    parames.add(new ParamKV("partner_id", removeDocMtg.getPartnerId() + ""));
    parames.add(new ParamKV("room_id", removeDocMtg.getRoomId() + ""));
    parames.add(new ParamKV("fid", removeDocMtg.getFid() + ""));
    parames.add(new ParamKV("timestamp", removeDocMtg.getTimestamp() + ""));
    Map<String, String> paramMap = BJYVideoUtil.hander(parames, removeDocMtg.getPartnerKey());
    String content = SimpleHttpClient.sendMessage(removeDocMtg.getRelativePath(), paramMap);

    if (StringUtils.isEmpty(content)) {
      BJYMtgResult res = new BJYMtgResult();
      res.setMsg("unbindDocMtg 请求数据返回为空，room_id=" + removeDocMtg.getRoomId() + ",fid=" + removeDocMtg.getFid());
      res.setCode(1);
      return res;
    }
    try {
      return (new JSONResponseParser<BJYMtgResult>(BJYMtgResult.class)).parse(content);
    } catch (ResponseParseException e) {
      e.printStackTrace();
      BJYMtgResult res = new BJYMtgResult();
      res.setMsg("unbindDocMtg 请求数据返回为空，room_id=" + removeDocMtg.getRoomId() + ",fid=" + removeDocMtg.getFid());
      res.setCode(1);
      return res;
    }
  }

  /**
   * 获取聊天记录
   * 
   * @param chatRecordRequest
   * @return
   */
  public BJYChatRecordResult getChatRecord(BJYChatRecordMtgRequest chatRecordRequest) {
    assertGenericRequestNotNull(chatRecordRequest);
    assertParameterNotNull(chatRecordRequest.getPartnerId(), "partner_id");
    assertParameterNotNull(chatRecordRequest.getRoomId(), "roomId");
    assertParameterNotNull(chatRecordRequest.getTimestamp(), "timestamp");
    List<ParamKV> parames = new ArrayList<ParamKV>();
    parames.add(new ParamKV("room_id", chatRecordRequest.getRoomId()));
    parames.add(new ParamKV("partner_id", chatRecordRequest.getPartnerId() + ""));
    parames.add(new ParamKV("timestamp", chatRecordRequest.getTimestamp() + ""));
    Map<String, String> paramMap = BJYVideoUtil.hander(parames, chatRecordRequest.getPartnerKey());
    String content = SimpleHttpClient.sendMessage(chatRecordRequest.getRelativePath(), paramMap);
    if (StringUtils.isEmpty(content)) {
      return null;
    }
    try {
      return (new JSONResponseParser<BJYChatRecordResult>(BJYChatRecordResult.class)).parse(content);
    } catch (ResponseParseException e) {
      throw new VideoException("content parse to " + BJYMtgResult.class + " Error:content=" + content);
    }
  }

  /**
   * @param classStatus
   * @return
   */
  public BJYClassStatusResult confStatus(BJYClassStatusMtgRequest classStatus) {
    assertGenericRequestNotNull(classStatus);
    assertParameterNotNull(classStatus.getPartnerId(), "partner_id");
    assertParameterNotNull(classStatus.getRoomId(), "roomId");
    assertParameterNotNull(classStatus.getTimestamp(), "timestamp");
    List<ParamKV> parames = new ArrayList<ParamKV>();
    parames.add(new ParamKV("room_id", classStatus.getRoomId()));
    parames.add(new ParamKV("partner_id", classStatus.getPartnerId() + ""));
    parames.add(new ParamKV("timestamp", classStatus.getTimestamp() + ""));
    Map<String, String> paramMap = BJYVideoUtil.hander(parames, classStatus.getPartnerKey());
    String content = SimpleHttpClient.sendMessage(classStatus.getRelativePath(), paramMap);
    if (StringUtils.isEmpty(content)) {
      return null;
    }
    try {
      return (new JSONResponseParser<BJYClassStatusResult>(BJYClassStatusResult.class)).parse(content);
    } catch (ResponseParseException e) {
      throw new VideoException("content parse to " + BJYMtgResult.class + " Error:content=" + content);
    }
  }

  /**
   * @param classStatus
   * @return
   */
  public BJYClassCallbackUrlResult createClassCallBackUrl(BJYClassCallbackUrlMtgRequest classStatus) {
    assertGenericRequestNotNull(classStatus);
    assertParameterNotNull(classStatus.getUrl(), "url");
    assertParameterNotNull(classStatus.getPartnerId(), "partner_id");
    assertParameterNotNull(classStatus.getTimestamp(), "timestamp");
    List<ParamKV> parames = new ArrayList<ParamKV>();
    parames.add(new ParamKV("partner_id", classStatus.getPartnerId() + ""));
    parames.add(new ParamKV("timestamp", classStatus.getTimestamp() + ""));
    parames.add(new ParamKV("url", classStatus.getUrl() + ""));
    Map<String, String> paramMap = BJYVideoUtil.hander(parames, classStatus.getPartnerKey());
    String content = SimpleHttpClient.sendMessage(classStatus.getRelativePath(), paramMap);
    if (StringUtils.isEmpty(content)) {
      return null;
    }
    try {
      return (new JSONResponseParser<BJYClassCallbackUrlResult>(BJYClassCallbackUrlResult.class)).parse(content);
    } catch (ResponseParseException e) {
      throw new VideoException("content parse to " + BJYMtgResult.class + " Error:content=" + content);
    }
  }

  /**
   * @param classStatus
   * @return
   */
  public BJYClassCallbackUrlResult queryClassCallBackUrl(BJYClassCallbackUrlMtgRequest classStatus) {
    assertGenericRequestNotNull(classStatus);
    assertParameterNotNull(classStatus.getPartnerId(), "partner_id");
    assertParameterNotNull(classStatus.getTimestamp(), "timestamp");
    List<ParamKV> parames = new ArrayList<ParamKV>();
    parames.add(new ParamKV("partner_id", classStatus.getPartnerId() + ""));
    parames.add(new ParamKV("timestamp", classStatus.getTimestamp() + ""));
    Map<String, String> paramMap = BJYVideoUtil.hander(parames, classStatus.getPartnerKey());
    String content = SimpleHttpClient.sendMessage(classStatus.getRelativePath(), paramMap);
    if (StringUtils.isEmpty(content)) {
      return null;
    }
    try {
      return (new JSONResponseParser<BJYClassCallbackUrlResult>(BJYClassCallbackUrlResult.class)).parse(content);
    } catch (ResponseParseException e) {
      throw new VideoException("content parse to " + BJYMtgResult.class + " Error:content=" + content);
    }
  }
}
