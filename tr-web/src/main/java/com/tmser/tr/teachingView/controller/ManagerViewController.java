package com.tmser.tr.teachingView.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.activity.bo.SchoolActivity;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.plainsummary.bo.PlainSummary;
import com.tmser.tr.teachingView.service.TeachingViewService;
import com.tmser.tr.teachingview.vo.SearchVo;
import com.tmser.tr.thesis.bo.Thesis;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * 教研一览管理者详情页
 * <pre>
 *
 * </pre>
 *
 * @author dell
 * @version $Id: ManagerViewController.java, v 1.0 2016年5月9日 上午10:22:37 dell Exp $
 */
@Controller
@RequestMapping("/jy/teachingView")
public class ManagerViewController extends AbstractController{

	@Autowired
	private TeachingViewService teachingViewService;
	/**
	 * 教学管理者情况一览
	 * @author wangyao
	 * @param searchVo
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/m_user_list")
	public String teachingView_manager(SearchVo searchVo, Model model){
		UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); //用户空间
		Integer schoolYear = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR); //学年
		searchVo.setOrgId(userSpace.getOrgId());
		if(searchVo.getTermId() == null){
			searchVo.setTermId((Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM));
		}
		searchVo.setPhaseId(userSpace.getPhaseId());
		searchVo.setUserId(userSpace.getUserId());
		searchVo.setSchoolYear(schoolYear);
		try {
			List<Map<String,Object>> dataList = teachingViewService.getManagerDataList(searchVo,userSpace);
			model.addAttribute("dataList", dataList);
			model.addAttribute("dataSize", dataList.size());
		} catch (Exception e) {
			logger.error("查阅管理者用户一览列表失败", e);
		}
		return "/teachingview/manager/m_user_list";
	}
	@ResponseBody
	@RequestMapping("/manager/m_user_list_sort")
	public Object teachingView_managers(SearchVo searchVo, Model model){
		UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); //用户空间
		try {
			List<Map<String,Object>> dataList = teachingViewService.getManagerDataList(searchVo,userSpace);
			return dataList;
		} catch (Exception e) {
			logger.error("管理者一览列表排序失败", e);
		}
		return null;
	}
	/**
	 * 教学管理者详情页
	 * @author wangyao
	 * @param searchVo
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/m_details")
	public String teachingView_manager_detail(SearchVo searchVo, Model model){
		Integer schoolYear = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR); //学年
		searchVo.setSchoolYear(schoolYear);
		UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); //用户空间
		searchVo.setPhaseId(userSpace.getPhaseId());
		searchVo.setOrgId(userSpace.getOrgId());
		try {
			List<Map<String,Object>> dataList = teachingViewService.getManagerDetailDataList(searchVo);
			model.addAttribute("dataMap", dataList.size()>0?dataList.get(0):new HashMap<String,Object>());
		} catch (Exception e) {
			logger.error("管理者详情页查询失败", e);
		}
		return "/teachingview/manager/m_details";
	}
	/**
	 * 教学管理者详情页(教案课件)
	 * @author wangyao
	 * @param searchVo
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/m_lesson")
	public String m_lesson(SearchVo searchVo, Model model){
		try {
			Map<String,Object> dataList = teachingViewService.getManagerLessonDetailDataByType(searchVo);
			model.addAttribute("chekInfoData", dataList.get("chekInfoData"));
			model.addAttribute("lessonInfoData", dataList.get("lessonInfoData"));
		} catch (Exception e) {
			logger.error("查询管理者教案、课件、反思资源详情失败", e);
		}
		return "/teachingview/manager/m_lesson";
	}
	/**
	 * 教学管理者详情页(教案课件反思)
	 * @author wangyao
	 * @param searchVo
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/m_lesson_fansi")
	public String m_lesson_fansi(SearchVo searchVo, Model model){
		try {
			searchVo.setFlago(String.valueOf(LessonPlan.KE_HOU_FAN_SI));
			Map<String,Object> fansiMap = teachingViewService.getManagerLessonDetailDataByType(searchVo);
			model.addAttribute("fansiChekInfoData", fansiMap.get("chekInfoData"));
			model.addAttribute("fansiData", fansiMap.get("lessonInfoData"));
			Map<String, Object> qiTaFanSiMap = teachingViewService.getManagerLessonFanSi(searchVo,String.valueOf(LessonPlan.QI_TA_FAN_SI));
			model.addAttribute("qiTaFanSiChekInfoData", qiTaFanSiMap.get("fansiCheckData"));
			model.addAttribute("qiTaFanSiData", qiTaFanSiMap.get("fansiData"));
			
		} catch (Exception e) {
			logger.error("查询管理者反思资源详情失败", e);
		}
		return "/teachingview/manager/m_lesson_fansi";
	}
	/**
	 * 管理者可查阅集体备课
	 * @author wangyao
	 * @param model
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("/manager/m_check_jitibeike")
	public String manager_check_jitibeike(Model model,SearchVo searchVo){
		try {
			Map<String, Object> data = teachingViewService.getManagerCheckActivityDetailData(searchVo);
			model.addAttribute("listPage", data.get("listPage"));
			model.addAttribute("activityData", data.get("activityData"));
		} catch (Exception e) {
			logger.error("查询管理者集体备课资源详情失败", e);
		}
		return "/teachingview/manager/m_check_jitibeike";
	}
	/**
	 * 管理者发起、参与集体备课
	 * @author wangyao
	 * @param model
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("/manager/m_partLaunchActivity")
	public String m_partLaunchActivity(Model model,SearchVo searchVo){
		try {
			Map<String, Object> dataMap = teachingViewService.getManagerActivityDetailData(searchVo);//发起
			model.addAttribute("launchData", dataMap.get("launchData"));
			model.addAttribute("partInData", dataMap.get("partInData"));
			boolean isLeader = (boolean)dataMap.get("isLeader");
			if(!isLeader){
				return "/teachingview/manager/m_partActivity";
			}
		} catch (Exception e) {
			logger.error("查询管理者发起参与集体备课资源详情失败", e);
		}
		return "/teachingview/manager/m_partLaunchActivity";
	}
	/**
	 * 管理者查阅计划总结数量
	 * @author wangyao
	 * @param model
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("/manager/m_checkPlanSummary")
	public String m_checkPlanSummary(Model model,SearchVo searchVo){
		try {
			Map<String, Object> planData = teachingViewService.getManagerCheckPlanSummaryDetailData(searchVo);//计划
			model.addAttribute("planData", planData.get("planPage"));
			model.addAttribute("summaryPage", planData.get("summaryPage"));
			model.addAttribute("chekInfoPlanData", planData.get("chekInfoPlanData"));
			model.addAttribute("chekInfoSummaryData", planData.get("chekInfoSummaryData"));
		} catch (Exception e) {
			logger.error("查询管理者计划总结查阅详情失败", e);
		}
		return "/teachingview/manager/m_checkPlanSummary";
	}
	/**
	 * 管理者计划总结撰写、分享数
	 * @author wangyao
	 * @param model
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("/manager/m_planSummaryWrite")
	public String m_planSummaryWrite(Model model,SearchVo searchVo){
		try {
			PlainSummary planWrite = new PlainSummary();
			searchVo.setFlags(String.valueOf(ResTypeConstants.TPPLAIN_SUMMARY_PLIAN));
			Map<String, Object> planWriteData = teachingViewService.getManagerPersonPlanWriteDetailData(planWrite, searchVo);//计划
			model.addAttribute("planWriteData", planWriteData.get("listPage"));
			PlainSummary summaryWrite = new PlainSummary();
			searchVo.setFlags(String.valueOf(ResTypeConstants.TPPLAIN_SUMMARY_SUMMARY));
			Map<String, Object> summaryWriteData = teachingViewService.getManagerPersonPlanWriteDetailData(summaryWrite, searchVo);//计划
			model.addAttribute("summaryWriteData", summaryWriteData.get("listPage"));
			PlainSummary planShare = new PlainSummary();
			searchVo.setFlags(String.valueOf(ResTypeConstants.TPPLAIN_SUMMARY_PLIAN));
			planShare.setIsShare(1);
			Map<String, Object> planShareData = teachingViewService.getManagerPersonPlanWriteDetailData(planShare, searchVo);//计划
			model.addAttribute("planShareData", planShareData.get("listPage"));
			PlainSummary summaryShare = new PlainSummary();
			searchVo.setFlags(String.valueOf(ResTypeConstants.TPPLAIN_SUMMARY_SUMMARY));
			summaryShare.setIsShare(1);
			Map<String, Object> summaryShareData = teachingViewService.getManagerPersonPlanWriteDetailData(summaryShare, searchVo);//计划
			model.addAttribute("summaryShareData", summaryShareData.get("listPage"));
			
			model.addAttribute("WriteCount", (Integer)planWriteData.get("count")+(Integer)summaryWriteData.get("count"));
			model.addAttribute("shareCount", (Integer)planShareData.get("count")+(Integer)summaryShareData.get("count"));
		} catch (Exception e) {
			logger.error("查询管理者计划总结撰写、分享详情失败", e);
		}
		return "/teachingview/manager/m_planSummaryWrite";
	}
	/**
	 * 管理者听课记录
	 * @author wangyao
	 * @param listType
	 * @param model
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("/manager/m_lecture_record")
	public String m_lecture_record(Model model,SearchVo searchVo){
		try {
			Map<String, Object> lectureData = teachingViewService.getManagerLectureDetailData(searchVo);
			model.addAttribute("lectureData", lectureData.get("listPage"));
		} catch (Exception e) {
			logger.error("查询管理者听课记录详情失败", e);
		}
		return "/teachingview/manager/m_lecture_record";
	}
	/**
	 * 管理者查阅听课记录
	 * @author wangyao
	 * @param listType
	 * @param model
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("/manager/m_check_lecture")
	public String m_check_lecture(Model model,SearchVo searchVo){
		try {
			Map<String, Object> lectureData = teachingViewService.findAllSubmitLecture(searchVo);
			model.addAttribute("chekInfoLectureData", lectureData.get("chekInfoLectureData"));
			model.addAttribute("lectureList", lectureData.get("lectureList"));
		} catch (Exception e) {
			logger.error("查询管理者听课记录详情失败", e);
		}
		return "/teachingview/manager/m_check_lecture";
	}
	/**
	 * 管理者教学文章
	 * @author wangyao
	 * @param model
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("/manager/m_thesis")
	public String m_thesis(Model model,SearchVo searchVo){
		try {
			Thesis thesis = new Thesis();
			Thesis shareThesis = new Thesis();
			if("0".equals(searchVo.getFlago())){
				thesis.addPage(searchVo.getPage());
			}else if("1".equals(searchVo.getFlago())){
				shareThesis.addPage(searchVo.getPage());
			}
			Map<String, Object> thesisWriteData = teachingViewService.getManagerThesisDetailData(thesis, searchVo);//计划
			model.addAttribute("thesisWriteData", thesisWriteData.get("listPage"));
			shareThesis.setIsShare(1);
			Map<String, Object> shareThesisData = teachingViewService.getManagerThesisDetailData(shareThesis, searchVo);//计划
			model.addAttribute("shareThesisData", shareThesisData.get("listPage"));
		} catch (Exception e) {
			logger.error("查询管理者教学文章详情失败", e);
		}
		return "/teachingview/manager/m_thesis";
	}

	/**
	 * 管理者同伴互助资源交流数
	 * @author wangyao
	 * @param model
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("/manager/m_companion")
	public String m_companion(Model model,SearchVo searchVo){
		try {
			Map<String, Object> messageData = teachingViewService.getManagerCompanionDetailData(searchVo);
			model.addAttribute("messageData", messageData.get("listPage"));
		} catch (Exception e) {
			logger.error("查询管理者同伴互助资源交流数详情失败", e);
		}
		return "/teachingview/manager/m_companion";
	}
	/**
	 * 管理者发起校际教研数量
	 * @author wangyao
	 * @param listType
	 * @param model
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("/manager/m_partLaunchSchoolActivity")
	public String m_partLaunchSchoolActivity(Model model,SearchVo searchVo){
		try {
			Map<String, Object> launchData = teachingViewService.getManagerSchActivityDetailData(searchVo);
			model.addAttribute("launchData", launchData.get("data"));
			boolean isPart = (boolean)launchData.get("isPart");
			PageList<SchoolActivity> partInData = teachingViewService.getManagerPartSchActivityDetailData(searchVo);
			model.addAttribute("partInData", partInData);
			if(!isPart){
				return "/teachingview/manager/m_partSchoolActivity";
			}
		} catch (Exception e) {
			logger.error("查询管理者发起校际教研详情失败", e);
		}
		return "/teachingview/manager/m_partLaunchSchoolActivity";
	}
	/**
	 * 管理者查阅计划总结数量
	 * @author wangyao
	 * @param model
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("/manager/m_checkThesis")
	public String m_checkThesis(Model model,SearchVo searchVo){
		try {
			Map<String, Object> thesisData = teachingViewService.findAllSubmitThesis(searchVo);//计划
			model.addAttribute("thesisData", thesisData.get("thesisList"));
			model.addAttribute("chekInfoThesisData", thesisData.get("chekInfoThesisData"));
		} catch (Exception e) {
			logger.error("查询管理者计划总结查阅详情失败", e);
		}
		return "/teachingview/manager/m_checkThesis";
	}
}
