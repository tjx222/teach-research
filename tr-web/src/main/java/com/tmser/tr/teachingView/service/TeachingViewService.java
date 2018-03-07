package com.tmser.tr.teachingView.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.tmser.tr.activity.bo.SchoolActivity;
import com.tmser.tr.comment.bo.CommentInfo;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.plainsummary.bo.PlainSummary;
import com.tmser.tr.recordbag.bo.Record;
import com.tmser.tr.teachingview.vo.SearchVo;
import com.tmser.tr.thesis.bo.Thesis;
import com.tmser.tr.uc.bo.UserSpace;

/**
 * 
 * <pre>
 *	教研一览service接口
 * </pre>
 *
 * @author wangdawei
 * @version $Id: TeachingViewService.java, v 1.0 2016年4月8日 上午10:55:13 wangdawei Exp $
 */
public interface TeachingViewService {

	public List<Map<String, String>> getGradeList();
	public List<Map<String, String>> getSubjectList();
	public List<Map<String, Object>> getTeacherDataList(SearchVo searchVo) throws Exception;
	
	public Map<String, Object> getTeacherDataDetail(SearchVo searchVo) throws Exception;
	public List<Map<String, Object>> getGradeDataList(SearchVo searchVo) throws Exception;
	public List<Map<String, Object>> getSubjectDataList(SearchVo searchVo) throws Exception;
	
	public SearchVo setDateRange(SearchVo searchVo);
	public void getActivityDataList(SearchVo searchVo, Model m);
	public void getFansiDataList(SearchVo searchVo, Model m);
	public void getSummaryDataList(SearchVo searchVo, Model m);
	public void getThesisDataList(SearchVo searchVo, Model m);
	public void getCompanionDataList(SearchVo searchVo, Model m);
	public void getRecordBagDataList(SearchVo searchVo, Model m);
	public void getSchoolActivityDataList(SearchVo searchVo, Model m);
	public Integer getRecordBagDetail(Record model, Integer termId,Model m);
	/**
	 * 获得管理者查询列表信息
	 * @author wangyao
	 * @param searchVo
	 * @param userSpace
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getManagerDataList(SearchVo searchVo,UserSpace userSpace) throws Exception;
	/**
	 * 获得管理者详情页
	 * @author wangyao
	 * @param searchVo
	 * @param userSpace
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getManagerDetailDataList(SearchVo searchVo) throws Exception;
	/**
	 * 查询教案课件可查阅数量
	 * @author wangyao
	 * @param searchVo
	 * @return
	 */
	public Map<String, Object> getManagerLessonDetailDataByType(SearchVo searchVo);
	/**
	 * 查询管理者查阅集体备课列表
	 * @author wangyao
	 * @param searchVo
	 * @param page
	 * @return
	 */
	public Map<String, Object> getManagerCheckActivityDetailData(SearchVo searchVo);
	/**
	 * 查询管理者发起、参与集体备课列表
	 * @author wangyao
	 * @param searchVo
	 * @param partPage 
	 * @param listType
	 * @return
	 */
	public Map<String, Object> getManagerActivityDetailData(SearchVo searchVo);
	/**
	 * 计划总结查阅数
	 * @author wangyao
	 * @param searchVo
	 * @param page
	 * @param listType
	 * @return
	 */
	Map<String, Object> getManagerCheckPlanSummaryDetailData(SearchVo searchVo);
	/**
	 * 获得教案课件撰写数
	 * @author wangyao
	 * @param plan
	 * @param searchVo
	 * @return
	 */
	Map<String, Object> getManagerPersonPlanWriteDetailData(PlainSummary plan,
			SearchVo searchVo);
	/**
	 * 获得听课记录数
	 * @author wangyao
	 * @param searchVo
	 * @param page
	 * @return
	 */
	Map<String, Object> getManagerLectureDetailData(SearchVo searchVo);
	/**
	 * 教学文章数（管理者）
	 * @author wangyao
	 * @param thesis
	 * @param searchVo
	 * @param page
	 * @return
	 */
	Map<String, Object> getManagerThesisDetailData(Thesis thesis,
			SearchVo searchVo);
	/**
	 * 可参与区域教研（管理者）
	 * @author wangyao
	 * @param searchVo
	 * @param page
	 * @return
	 */
//	Map<String, Object> getManagerRegoinActivityDetailData(SearchVo searchVo);
	/**
	 * 获得发起校际教研（管理者）
	 * @author wangyao
	 * @param searchVo
	 * @param page
	 * @return
	 */
	Map<String, Object> getManagerSchActivityDetailData(SearchVo searchVo);
	/**
	 * 获得参与校际教研详情（管理者）
	 * @author wangyao
	 * @param searchVo
	 * @param page
	 * @return
	 */
	PageList<SchoolActivity> getManagerPartSchActivityDetailData(
			SearchVo searchVo);
	/**
	 * 同伴互助留言（管理者）
	 * @author wangyao
	 * @param searchVo
	 * @param page
	 * @return
	 */
	Map<String, Object> getManagerCompanionDetailData(SearchVo searchVo);
	/**
	 * 获得反思可查阅数
	 * @author wangyao
	 * @param searchVo
	 * @param listType
	 * @return
	 */
	Map<String, Object> getManagerLessonFanSi(SearchVo searchVo, String listType);
	/**
	 * 获得同伴互助的信息列表
	 * @author wangyao
	 * @param searchVo
	 * @param userIdReceiver
	 * @return
	 */
	Map<String, Object> getViewCompanionMessage(SearchVo searchVo,Integer userIdReceiver);
	/**
	 * 查阅评论详情
	 * @author wangyao
	 * @param info
	 * @param m
	 */
	public void findAllCommentReply(CommentInfo info,Model m);
	/**
	 * 查询区域教研参加数
	 * @author wangyao
	 * @param searchVo
	 * @return
	 */
//	Map<String, Object> getManagerRegoinActivityPartDetailData(SearchVo searchVo);
	public void getJiaoanDataList(SearchVo searchVo, Model m);
	public void getKejianDataList(SearchVo searchVo, Model m);
	public void getListenDataList(SearchVo searchVo, Model m);
	
	Map<String, Object> findAllSubmitThesis(SearchVo searchVo);
	/**
	 * 查找可以被查阅的听课记录
	 * @param searchVo
	 * @return
	 */
	public Map<String, Object> findAllSubmitLecture(SearchVo searchVo);
}
