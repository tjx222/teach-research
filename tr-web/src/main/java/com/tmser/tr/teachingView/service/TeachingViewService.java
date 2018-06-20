package com.tmser.tr.teachingView.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.tmser.tr.comment.bo.CommentInfo;
import com.tmser.tr.recordbag.bo.Record;
import com.tmser.tr.teachingview.vo.SearchVo;
import com.tmser.tr.uc.bo.UserSpace;

/**
 * 
 * <pre>
 * 教研一览service接口
 * </pre>
 *
 * @author wangdawei
 * @version $Id: TeachingViewService.java, v 1.0 2016年4月8日 上午10:55:13 wangdawei
 *          Exp $
 */
public interface TeachingViewService {

  public List<Map<String, String>> getGradeList(Integer phaseId);

  public List<Map<String, String>> getSubjectList(Integer phaseId);

  public List<Map<String, Object>> getTeacherDataList(SearchVo searchVo) throws Exception;

  public Map<String, Object> getTeacherDataDetail(SearchVo searchVo) throws Exception;

  public List<Map<String, Object>> getGradeDataList(SearchVo searchVo) throws Exception;

  public List<Map<String, Object>> getSubjectDataList(SearchVo searchVo) throws Exception;

  public SearchVo setDateRange(SearchVo searchVo);

  public void getActivityDataList(SearchVo searchVo, Model m);

  public void getFansiDataList(SearchVo searchVo, Model m);

  public void getCompanionDataList(SearchVo searchVo, Model m);

  public void getRecordBagDataList(SearchVo searchVo, Model m);

  public Integer getRecordBagDetail(Record model, Integer termId, Model m);

  /**
   * 获得管理者查询列表信息
   * 
   * @author wangyao
   * @param searchVo
   * @param userSpace
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> getManagerDataList(SearchVo searchVo, UserSpace userSpace) throws Exception;

  /**
   * 获得管理者详情页
   * 
   * @author wangyao
   * @param searchVo
   * @param userSpace
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> getManagerDetailDataList(SearchVo searchVo) throws Exception;

  /**
   * 查询教案课件可查阅数量
   * 
   * @author wangyao
   * @param searchVo
   * @return
   */
  public Map<String, Object> getManagerLessonDetailDataByType(SearchVo searchVo);

  /**
   * 查询管理者查阅集体备课列表
   * 
   * @author wangyao
   * @param searchVo
   * @param page
   * @return
   */
  public Map<String, Object> getManagerCheckActivityDetailData(SearchVo searchVo);

  /**
   * 查询管理者发起、参与集体备课列表
   * 
   * @author wangyao
   * @param searchVo
   * @param partPage
   * @param listType
   * @return
   */
  public Map<String, Object> getManagerActivityDetailData(SearchVo searchVo);

  /**
   * 同伴互助留言（管理者）
   * 
   * @author wangyao
   * @param searchVo
   * @param page
   * @return
   */
  Map<String, Object> getManagerCompanionDetailData(SearchVo searchVo);

  /**
   * 获得反思可查阅数
   * 
   * @author wangyao
   * @param searchVo
   * @param listType
   * @return
   */
  Map<String, Object> getManagerLessonFanSi(SearchVo searchVo, String listType);

  /**
   * 获得同伴互助的信息列表
   * 
   * @author wangyao
   * @param searchVo
   * @param userIdReceiver
   * @return
   */
  Map<String, Object> getViewCompanionMessage(SearchVo searchVo, Integer userIdReceiver);

  /**
   * 查阅评论详情
   * 
   * @author wangyao
   * @param info
   * @param m
   */
  public void findAllCommentReply(CommentInfo info, Model m);

  public void getJiaoanDataList(SearchVo searchVo, Model m);

  public void getKejianDataList(SearchVo searchVo, Model m);

}
