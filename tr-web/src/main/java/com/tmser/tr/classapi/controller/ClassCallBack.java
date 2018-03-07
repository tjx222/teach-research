package com.tmser.tr.classapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tmser.tr.bjysdk.BJYProperties;
import com.tmser.tr.bjysdk.BJYVideoUtil;
import com.tmser.tr.bjysdk.Constants;
import com.tmser.tr.bjysdk.model.BJYClassStatusBackRequest;
import com.tmser.tr.bjysdk.model.BJYMtgResult;
import com.tmser.tr.bjysdk.model.BJYPlaybackRequest;
import com.tmser.tr.bjysdk.model.ParamKV;
import com.tmser.tr.classapi.bo.ClassInfo;
import com.tmser.tr.classapi.service.ClassOperateService;
import com.tmser.tr.classapi.vo.ClassCallBackParam;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.utils.StringUtils;

/**
 * 聊天记录控制器
 * 
 * <pre>
 *
 * </pre>
 *
 * @author ljh
 * @version $Id: ChartRecordController.java, v 1.0 2017年2月13日 下午2:05:02 ljh Exp
 *          $
 */
@Controller
@RequestMapping("/jy/classapi")
public class ClassCallBack extends AbstractController {

  @Resource(name = "classApi")
  private ClassOperateService classOperateService;

  @Autowired
  private BJYProperties prop;

  @RequestMapping("/bjycallback")
  public @ResponseBody BJYMtgResult callbackClassStatus(BJYClassStatusBackRequest classStatus) {
    BJYMtgResult result = new BJYMtgResult();
    String classStatus_json = JSON.toJSONString(classStatus);
    logger.info("callbackClassStatus：classStatus= {}", JSON.toJSONString(classStatus_json));
    if (classStatus == null || StringUtils.isEmpty(classStatus.getRoom_id())
        || StringUtils.isEmpty(classStatus.getSign())) {
      result.setCode(1);
      result.setMsg("参数错误");
      return result;
    }
    List<ParamKV> parames = new ArrayList<ParamKV>();
    processParams("timestamp", classStatus.getTimestamp() + "", parames);
    processParams("op", classStatus.getOp() + "", parames);
    processParams("op_time", classStatus.getOp_time() + "", parames);
    processParams("qid", classStatus.getQid() + "", parames);
    processParams("room_id", classStatus.getRoom_id() + "", parames);
    Map<String, String> paramMap = BJYVideoUtil.hander(parames, prop.getKey());
    if (!paramMap.get("sign").equals(classStatus.getSign())) {
      result.setCode(1);
      result.setMsg("参数错误");
      return result;
    }
    if ("end".equals(classStatus.getOp())) {
      try {
        ClassCallBackParam classCallBackParam = new ClassCallBackParam();
        classCallBackParam.setClassId(classStatus.getRoom_id());
        classCallBackParam.setEventType(ClassOperateService.END_CLASS_EVENT);
        classOperateService.classCallback(classCallBackParam);
      } catch (Exception e) {
        logger.error("", e);
        result.setCode(1);
        result.setMsg("回调错误");
        return result;
      }
    }
    return result;
  }

  @RequestMapping("/callbackPlayback")
  public @ResponseBody BJYMtgResult callbackPlayback(BJYPlaybackRequest playback) {
    BJYMtgResult result = new BJYMtgResult();
    String playback_json = JSON.toJSONString(playback);
    logger.info("callbackPlayback：playback=" + playback_json);
    if (playback == null || StringUtils.isEmpty(playback.getRoom_id())) {
      result.setCode(1);
      result.setMsg("参数错误");
      return result;
    }
    List<ParamKV> parames = new ArrayList<ParamKV>();
    processParams("timestamp", playback.getTimestamp() + "", parames);
    processParams("file_md5", playback.getFile_md5() + "", parames);
    processParams("now_definition", playback.getNow_definition() + "", parames);
    processParams("origin_definition", playback.getOrigin_definition() + "", parames);
    processParams("preface_url", playback.getPreface_url() + "", parames);
    processParams("qid", playback.getQid() + "", parames);
    processParams("room_id", playback.getRoom_id() + "", parames);
    processParams("length", playback.getLength() + "", parames);
    processParams("status", playback.getStatus() + "", parames);
    processParams("total_size", playback.getTotal_size() + "", parames);
    processParams("total_transcode_size", playback.getTotal_transcode_size() + "", parames);
    processParams("video_id", playback.getVideo_id() + "", parames);
    // 暂且不做验证，防止回放地址保存失败
    // Map<String, String> paramMap = BJYVideoUtil.hander(parames,
    // prop.getPartnerKey());
    // if(!paramMap.get("sign").equals(playback.getSign())){
    // result.setCode(1);
    // result.setMsg("参数错误");
    // return result;
    // }
    ClassInfo classInfo = null;
    try {
      if (playback.getStatus() == 100) {
        classInfo = classOperateService.generateClassRecordUrl(playback.getRoom_id());
      }
    } catch (Exception e) {
      logger.error("callbackPlayback Error：playback=" + playback_json, e);
      result.setCode(1);
      result.setMsg("获取Token错误");
      return result;
    }
    if (classInfo == null) {
      result.setCode(1);
      result.setMsg("获取Token错误");
      return result;
    }
    result.setCode(0);
    result.setMsg("");
    return result;
  }

  private static void processParams(String key, String value, List<ParamKV> parames) {
    if (StringUtils.isEmpty(value)) {
      return;
    }
    parames.add(new ParamKV(key, value));
  }

  @RequestMapping("/registCallback")
  public @ResponseBody Result registCallback(@RequestParam(value = "host", required = false) String host) {
    Result rs = new Result();
    if (host == null || !host.startsWith("http")) {
      host = new StringBuilder().append(WebThreadLocalUtils.getRequest().getScheme()).append("://")
          .append(WebThreadLocalUtils.getRequest().getServerName()).append(":")
          .append(WebThreadLocalUtils.getRequest().getServerPort())
          .append(WebThreadLocalUtils.getRequest().getContextPath()).toString();
      rs.setMsg("无效的host,将使用默认host注册. " + host);
    }
    try {
      classOperateService.setClassBackUrl(Constants.classEndType, host);
      classOperateService.setClassBackUrl(Constants.videoType, host);
    } catch (Exception e) {
      logger.error("", e);
      rs.setCode(0);
      rs.setMsg("注册失败," + e.getMessage());
    }

    return rs;
  }
}
