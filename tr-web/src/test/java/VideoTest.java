import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.tmser.tr.bjysdk.BJYVideoUtil;
import com.tmser.tr.bjysdk.SimpleHttpClient;
import com.tmser.tr.bjysdk.model.ParamKV;
import com.tmser.tr.utils.StringUtils;

/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */

/**
 * <pre>
 * video回调测试
 * </pre>
 *
 * @author ljh
 * @version $Id: VideoTest.java, v 1.0 2018年1月3日 上午11:40:27 ljh Exp $
 */
public class VideoTest {

  private String partenerKey = "X2wCd1FAjQQWBGqpp3TxTHsTc4SE0092YrE45U3QdeaDSp5fcKW4arZYGvXsAMS8UelN3nUEYbepeWf09WsjJw==";
  private int partenerId = 53928224;
  private String roomId = "";
  private String relativePath = "";

  private void processParams(String key, String value, List<ParamKV> parames) {
    if (StringUtils.isEmpty(value)) {
      return;
    }
    parames.add(new ParamKV(key, value));
  }

  /**
   * 手动触发回调:获取回调时间戳和签名
   * 
   * localhost:8081/jy-web/jy/classapi/bjycallback?room_id=roomId&op=end&
   * timestamp=1514445217&op_time=2017-12-28&qid=1234&sign=sign
   */
  @Test
  public void precessCallBackSign() {
    Long time = System.currentTimeMillis() / 1000;
    System.out.println(time);
    List<ParamKV> parames = new ArrayList<ParamKV>();
    processParams("timestamp", String.valueOf(time), parames);
    processParams("op", "end", parames);
    processParams("op_time", "2017-12-27", parames);
    processParams("qid", "4234", parames);
    processParams("room_id", "171227730906", parames);
    Map<String, String> paramMap = BJYVideoUtil.hander(parames, "partyId");
    System.out.println(paramMap.get("sign"));
  }

  /**
   * 聊天记录
   */
  @Test
  public void getChat() {
    relativePath = "https://api.baijiayun.com/openapi/room_data/exportChatMsg";
    roomId = "18010581136961";
    Long timestamp = System.currentTimeMillis() / 1000;
    List<ParamKV> parames = new ArrayList<ParamKV>();
    parames.add(new ParamKV("partner_id", partenerId + ""));
    parames.add(new ParamKV("room_id", roomId + ""));
    parames.add(new ParamKV("timestamp", timestamp + ""));
    Map<String, String> paramMap = BJYVideoUtil.hander(parames, partenerKey);
    String content = SimpleHttpClient.sendMessage(relativePath, paramMap);
    System.out.println(content);
  }

  /**
   * 回调地址{视频和课堂}
   * 
   */
  @Test
  public void getCallbackUrl() {
    relativePath = "https://api.baijiayun.com/openapi/live_account/getClassCallbackUrl";
    String relationPath2 = "https://api.baijiayun.com/openapi/video_account/getTranscodeCallbackUrl";
    Long timestamp = System.currentTimeMillis() / 1000;
    List<ParamKV> parames = new ArrayList<ParamKV>();
    parames.add(new ParamKV("partner_id", partenerId + ""));
    parames.add(new ParamKV("timestamp", timestamp + ""));
    Map<String, String> paramMap = BJYVideoUtil.hander(parames, partenerKey);
    String content = SimpleHttpClient.sendMessage(relativePath, paramMap);
    String content2 = SimpleHttpClient.sendMessage(relationPath2, paramMap);
    System.out.println(content);
    System.out.println(content2);
  }

  /**
   * 测试回放状态
   */
  @Test
  public void getBasicInfo() {
    relativePath = "http://www.baijiacloud.com/openapi/playback/getBasicInfo";
    roomId = "17122889597272";
    Long timestamp = System.currentTimeMillis() / 1000;
    List<ParamKV> parames = new ArrayList<ParamKV>();
    parames.add(new ParamKV("partner_id", partenerId + ""));
    parames.add(new ParamKV("room_id", roomId + ""));
    parames.add(new ParamKV("timestamp", timestamp + ""));
    Map<String, String> paramMap = BJYVideoUtil.hander(parames, partenerKey);
    String content = SimpleHttpClient.sendMessage(relativePath, paramMap);
    System.out.println(content);
    System.err.println("回放状态码[0:回放未生成] [10:回放生成中] [20:转码中] [30:回放生成失败] [100:回放生成成功]。回放未生成的没有video_id等其它信息");
  }

  /**
   * 视频回放列表
   */
  @Test
  public void testGetList() {
    relativePath = "http://www.baijiacloud.com/openapi/playback/getList";
    Long timestamp = System.currentTimeMillis() / 1000;
    List<ParamKV> parames = new ArrayList<ParamKV>();
    parames.add(new ParamKV("partner_id", partenerId + ""));
    parames.add(new ParamKV("page", 1 + ""));
    parames.add(new ParamKV("page_size", 100 + ""));
    parames.add(new ParamKV("timestamp", timestamp + ""));
    Map<String, String> paramMap = BJYVideoUtil.hander(parames, partenerKey);
    String content = SimpleHttpClient.sendMessage(relativePath, paramMap);
    System.out.println(content);
  }

  /**
   * 查询子账号列表
   */
  @Test
  public void testAccountList() {
    relativePath = "https://api.baijiayun.com/openapi/sub_account/getSubAccountList";
    Long timestamp = System.currentTimeMillis() / 1000;
    List<ParamKV> parames = new ArrayList<ParamKV>();
    parames.add(new ParamKV("partner_id", partenerId + ""));
    parames.add(new ParamKV("timestamp", timestamp + ""));
    Map<String, String> paramMap = BJYVideoUtil.hander(parames, partenerKey);
    String content = SimpleHttpClient.sendMessage(relativePath, paramMap);
    System.out.println(content);
  }
}
