package com.tmser.tr.bjysdk;

/**
 * Created by yfwang on 2017/11/7.
 */

import java.util.List;

import com.tmser.tr.bjysdk.exception.ClientException;
import com.tmser.tr.bjysdk.exception.VideoException;
import com.tmser.tr.bjysdk.model.BJYBindDocMtgRequest;
import com.tmser.tr.bjysdk.model.BJYBindDocMtgResult;
import com.tmser.tr.bjysdk.model.BJYClassCallbackUrlMtgRequest;
import com.tmser.tr.bjysdk.model.BJYChatRecordMtgRequest;
import com.tmser.tr.bjysdk.model.BJYChatRecordResult;
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

/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 * 
 * /**
 * 
 * <pre>
 * 视频相关访问接口
 * </pre>
 *
 * @author tmser
 * @version $Id: Video.java, v 1.0 2016年9月9日 下午2:08:46 tmser Exp $
 */
public interface BJYVideo {

  /**
   * 获取PC端课堂地址
   * 
   * @param joinMtgRequest
   * @throws VideoException
   * @throws ClientException
   * @return
   */

  public BJYCreateMtgResult joinMtg(BJYCreateMtgRequest joinMtgRequest) throws VideoException, ClientException;

  /**
   * 更新房间信息
   * 
   * @param joinMtgRequest
   * @return
   * @throws VideoException
   * @throws ClientException
   */
  public BJYMtgResult getUpdateMtg(BJYUpdateMtgRequest updateMtgRequest) throws VideoException, ClientException;

  /**
   * 请求服务器时间
   */
  public Long timestamp() throws VideoException, ClientException;

  /**
   * 获取课堂录制播放地址
   * 
   * @param playRecordRequest
   * @return
   */
  public BJYTokenMtgResult playRecord(BJYTokenMtgRequest tokenMtgRequest) throws VideoException, ClientException;

  /**
   * 文档上传
   * 
   * @param fileUploadForeignRequest
   * @return
   */
  public List<BJYUploadDocMtgResult> fileUploadForeign(BJYUploadDocMtgRequest uploadDocRequest);

  /**
   *
   * @param deleteMtg
   * @throws VideoException
   * @throws ClientException
   * @return
   */

  public BJYMtgResult deleteMtg(BJYDeleteMtgRequest delMtgRequest) throws VideoException, ClientException;

  /**
   *
   * @param bindDocMtg
   * @return
   * @throws VideoException
   * @throws ClientException
   */
  public List<BJYBindDocMtgResult> bindDocMtg(BJYBindDocMtgRequest bindDocMtg) throws VideoException, ClientException;

  public BJYMtgResult unbindDocMtg(BJYRemoveDocMtgRequest removeDocMtg) throws VideoException, ClientException;

  public BJYChatRecordResult getChatRecord(BJYChatRecordMtgRequest chatRecordRequest) throws VideoException,
      ClientException;

  /**
   * 关闭Client实例，并释放所有正在使用的资源。
   * 一旦关闭，将不再处理任何发往OSS的请求。
   */
  void shutdown();

  //
  //
  //
  // /**
  // * 查询单个课堂中用户进出课堂的历史记录
  // * @param ConfLogRequest
  // * @return
  // * @throws VideoException
  // * @throws ClientException
  // */
  // public ConfLogResult confLog(ConfLogRequest ConfLogRequest) throws
  // VideoException, ClientException;
  //
  //
  // /**
  // * 获取文档服务器地址
  // * @param getDocumentUrlRequest
  // * @return
  // */
  // public DocumentUrlResult getDocumentUrlRequest(DocumentUrlRequest
  // documentUrlRequest);
  //
  //
  //
  // /**
  // * 文档下载
  // * @param downLoadDocumentRequest
  // * @return
  // */
  // public DownLoadDocumentResult downLoadDocument(DownLoadDocumentRequest
  // downLoadDocumentRequest);
  //
  // /**
  // * 文档删除
  // * @param delDocumentRequest
  // * @return
  // */
  // public DelDocumentResult delDocument(DelDocumentRequest
  // delDocumentRequest);
  //
  // /**
  // * 获取文档列表
  // * @param documentListRequest
  // * @return
  // */
  // public DocumentListResult getDocumentList(DocumentListRequest
  // documentListRequest);
  //
  /**
   * 查询课堂状态
   * 
   * @param confStatusRequest
   * @return
   */
  public BJYClassStatusResult confStatus(BJYClassStatusMtgRequest classStatus);

  //
  // /**
  // * 获取教师视频录制列表
  // * @param techertRecordListRequest
  // * @return
  // */
  // public TechertRecordListResult
  // getTechertRecordListRequest(TechertRecordListRequest
  // techertRecordListRequest);
  //
  // /**
  // * 获取单个课堂的录制列表
  // * @param recordListRequest
  // * @return
  // */
  // public RecordListResult getRecordListRequest(RecordListRequest
  // recordListRequest);
  // /**
  // * 获取课堂聊天记录
  // * @param chartRecordRequeset
  // * @return
  // */
  // public ChartRecordResult getChartRecord(ChartRecordRequeset
  // chartRecordRequeset);
  //
  // /**
  // * 查询课堂的文档列表
  // * @param meettingDocumentRequeset
  // * @return
  // */
  // public MeettingDocumentResult
  // getMeettingDocumentList(MeettingDocumentRequeset meettingDocumentRequeset);
  //
  // /**
  // * 文档详情
  // * @param documentDetailRequest
  // * @return
  // */
  // public DocumentDetailResult getDocumentDetail(DocumentDetailRequest
  // documentDetailRequest);
  //
  // /**
  // * 查询当前时刻可以进入课堂的总人数
  // * @param licenseRequest
  // * @return
  // */
  // public LicenseResult getLicense(LicenseRequest licenseRequest);
  //
  // /**
  // * 查询站点当前在线总人数接口
  // * @param countOnlineTotalRequest
  // * @return
  // */
  // public CountOnlineTotalResult getCountOnlineTotal(CountOnlineTotalRequest
  // countOnlineTotalRequest);
  //
  // /**
  // * 按月分页查询课堂记录接口
  // * @param confListRequest
  // * @return
  // */
  // public ConfListResult getConfList(ConfListRequest confListRequest);
  //
  // /**
  // * 查询单个正在进行中课堂
  // * @param countOnlineRequest
  // * @return
  // */
  // public CountOnlineResult getCountOnline(CountOnlineRequest
  // countOnlineRequest);
  //
  // /**
  // * 导出站点用户 进出课堂的 记录的Excel
  // * @param exportJoinlogRequest
  // * @return
  // */
  // public ExportJoinlogResult getCountOnlineTotal(ExportJoinlogRequest
  // exportJoinlogRequest);
  //
  // /**
  // * 查询单个 课堂 中用户进出总记录接口
  // * @param confLogTotalRequest
  // * @return
  // */
  // public ConfLogTotalResult confLogTotal(ConfLogTotalRequest
  // confLogTotalRequest);
  // /**
  // * 消息记录url回调（创建课程后调用）
  // * @param chartRecordUrlRequest
  // * @return
  // */
  // public ChartRecordUrlResult addChatCallback(ChartRecordUrlRequest
  // chartRecordUrlRequest);

  /**
   * 注册课堂下课回调地址
   * 
   * @param classStatus
   * @return
   */
  BJYClassCallbackUrlResult createClassCallBackUrl(BJYClassCallbackUrlMtgRequest classStatus);

  /**
   * 查询课堂下课回调地址
   * 
   * @param classStatus
   * @return
   */
  BJYClassCallbackUrlResult queryClassCallBackUrl(BJYClassCallbackUrlMtgRequest classStatus);

}
