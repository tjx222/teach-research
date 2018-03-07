package com.tmser.tr.bjysdk;

import java.util.List;

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

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: VideoClient.java, v 1.0 2016年9月9日 下午3:45:11 tmser Exp $
 */
public class BJYVideoClient implements BJYVideo {
  private BJYVideoOperation videoOperation;

  public BJYVideoClient() {
    initOperations();
  }

  private void initOperations() {
    this.videoOperation = new BJYVideoOperation();
  }

  /**
   * 获取 PC端课堂地址接口
   * 
   * @return 返回url,param
   * @throws VideoException
   * @throws ClientException
   * @see com.mainbo.vsdk.Video#joinMtg()
   */
  @Override
  public BJYCreateMtgResult joinMtg(BJYCreateMtgRequest joinMtgRequest) throws VideoException {
    BJYCreateMtgResult joinMtg = this.videoOperation.getJoinMtg(joinMtgRequest);
    return joinMtg;
  }

  @Override
  public BJYMtgResult getUpdateMtg(BJYUpdateMtgRequest updateMtgRequest) throws VideoException {
    BJYMtgResult updateMtg = this.videoOperation.getUpdateMtg(updateMtgRequest);
    return updateMtg;
  }

  @Override
  public Long timestamp() throws VideoException {
    return System.currentTimeMillis() / 1000;
  }

  @Override
  public BJYTokenMtgResult playRecord(BJYTokenMtgRequest tokenMtgRequest) throws VideoException {
    BJYTokenMtgResult tokenMtg = this.videoOperation.playRecord(tokenMtgRequest);
    return tokenMtg;
  }

  @Override
  public List<BJYUploadDocMtgResult> fileUploadForeign(BJYUploadDocMtgRequest uploadDocRequest) throws VideoException {
    return this.videoOperation.putObject(uploadDocRequest);
  }

  @Override
  public BJYMtgResult deleteMtg(BJYDeleteMtgRequest delMtgRequest) throws VideoException {
    return this.videoOperation.deleteMtg(delMtgRequest);
  }

  @Override
  public List<BJYBindDocMtgResult> bindDocMtg(BJYBindDocMtgRequest bindDocMtg) throws VideoException {
    return this.videoOperation.bindDocMtg(bindDocMtg);
  }

  @Override
  public BJYMtgResult unbindDocMtg(BJYRemoveDocMtgRequest removeDocMtg) throws VideoException, ClientException {
    return this.videoOperation.unbindDocMtg(removeDocMtg);
  }

  /**
   * @param chatRecordRequest
   * @return
   * @throws VideoException
   * @throws ClientException
   * @see com.tmser.tr.bjysdk.BJYVideo#getChatRecord(com.tmser.tr.bjysdk.model.BJYChatRecordMtgRequest)
   */
  @Override
  public BJYChatRecordResult getChatRecord(BJYChatRecordMtgRequest chatRecordRequest) throws VideoException,
      ClientException {
    return this.videoOperation.getChatRecord(chatRecordRequest);
  }

  /**
   * @param confStatusRequest
   * @return
   * @see com.tmser.tr.bjysdk.BJYVideo#confStatus(com.mainbo.vsdk.model.ConfStatusRequest)
   */
  @Override
  public BJYClassStatusResult confStatus(BJYClassStatusMtgRequest classStatus) {
    return this.videoOperation.confStatus(classStatus);
  }

  /**
   * 
   * @param classStatus
   * @return
   * @see com.tmser.tr.bjysdk.BJYVideo#createClassCallBackUrl(com.tmser.tr.bjysdk.model.BJYClassCallbackUrlMtgRequest)
   */
  @Override
  public BJYClassCallbackUrlResult createClassCallBackUrl(BJYClassCallbackUrlMtgRequest classStatus) {
    return this.videoOperation.createClassCallBackUrl(classStatus);
  }

  /**
   * 
   * @param classStatus
   * @return
   * @see com.tmser.tr.bjysdk.BJYVideo#queryClassCallBackUrl(com.tmser.tr.bjysdk.model.BJYClassCallbackUrlMtgRequest)
   */
  @Override
  public BJYClassCallbackUrlResult queryClassCallBackUrl(BJYClassCallbackUrlMtgRequest classStatus) {
    return this.videoOperation.queryClassCallBackUrl(classStatus);
  }

  /**
   * 
   * @see com.tmser.tr.bjysdk.BJYVideo#shutdown()
   */
  @Override
  public void shutdown() {
    // TODO Auto-generated method stub

  }
}
