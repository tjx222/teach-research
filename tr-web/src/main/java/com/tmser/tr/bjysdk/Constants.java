package com.tmser.tr.bjysdk;

/**
 * Created by yfwang on 2017/11/15.
 */
public final class Constants {
  public static final String DEFAULT_VIDEO_ENDPOINT = "https://baijiacloud.com";

  public static final String DEFAULT_VIDEO_SITEID = "api";

  public static final String DEFAULT_CHARSET_NAME = "utf-8";
  public static final String DEFAULT_XML_ENCODING = "utf-8";

  public static final String endpoint = "https://api.baijiayun.com";
  public static final String room_create = "https://api.baijiayun.com/openapi/room/create";
  public static final String room_enter = "http://www.baijiayun.com/web/room/enter";
  public static final String room_update = "https://api.baijiayun.com/openapi/room/update";
  public static final String room_delete = "https://api.baijiayun.com/openapi/room/delete";
  public static final String player_token = "https://api.baijiayun.com/openapi/video/getPlayerToken";
  public static final String play_record = "http://www.baijiacloud.com/web/playback/index";
  public static final String upload_doc = "https://api.baijiayun.com/openapi/doc/uploadDoc";
  public static final String bind_doc = "https://api.baijiayun.com/openapi/doc/bindDoc";
  public static final String live_status = "https://api.baijiayun.com/openapi/live/getLiveStatus";
  public static final String remove_doc = "https://api.baijiayun.com/openapi/doc/removeDoc";
  public static final String chat_record = "https://api.baijiayun.com/openapi/room_data/exportChatMsg";
  public static final String class_status = "https://api.baijiayun.com/openapi/live/getLiveStatus";
  // 设置下课回调
  public static final String class_callback_set = "https://api.baijiayun.com/openapi/live_account/setClassCallbackUrl";
  // 设置视频回调
  public static final String transcode_call_set = "https://api.baijiayun.com/openapi/video_account/setTranscodeCallbackUrl";

  // 查询下课回调
  public static final String class_callback_get = "https://api.baijiayun.com/openapi/live_account/getClassCallbackUrl";
  // 查询视频回调
  public static final String transcode_call_get = "https://api.baijiayun.com/openapi/video_account/getTranscodeCallbackUrl";

  public static final int classEndType = 1;

  public static final int videoType = 2;
}
