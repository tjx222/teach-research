/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmser.tr.activity.bo.Activity;
import com.tmser.tr.activity.service.ActivityService;
import com.tmser.tr.check.bo.CheckInfo;
import com.tmser.tr.check.service.CheckInfoService;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.history.service.ManagerCheckHistoryCount;
import com.tmser.tr.history.vo.SearchVo;
import com.tmser.tr.lecturerecords.bo.LectureRecords;
import com.tmser.tr.lecturerecords.service.LectureRecordsService;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.managerecord.service.KeJianCheckService;
import com.tmser.tr.plainsummary.bo.PlainSummary;
import com.tmser.tr.plainsummary.service.PlainSummaryService;
import com.tmser.tr.thesis.bo.Thesis;
import com.tmser.tr.thesis.service.ThesisService;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.utils.StringUtils;
import com.tmser.tr.writelessonplan.service.LessonPlanService;

/**
 * 
 * <pre>
 *	管理查阅
 * </pre>
 *
 * @author wangyao
 * @version $Id: HistoryManagerCheckController.java, v 1.0 2016年5月19日 下午4:06:34 wangyao Exp $
 */
@Controller
@RequestMapping("/jy/history")
public class HistoryManagerCheckController extends AbstractController{
	
	@Autowired
	private UserSpaceService userSpaceService;
	@Autowired
	private CheckInfoService checkInfoService;
	@Autowired
	private ManagerCheckHistoryCount managerCheckHistoryCount;
	@Resource
	private KeJianCheckService kejianCheckService;
	@Resource
	private ActivityService activityService;
	@Resource
	private LessonPlanService lessonPlanService;
	@Resource
	private PlainSummaryService plainSummaryService;
	@Resource
	private ThesisService thesisService;
	@Resource
	private LectureRecordsService lectureRecordsService;
	/**
	 * 首页
	 * @param m
	 * @return
	 */
	@RequestMapping("/{year}/cygljl/index")
	public String index(Model model,SearchVo searchVo,UserSpace userSpace,@PathVariable("year")Integer year){
		User user = CurrentUserContext.getCurrentUser();
		Map<String,Integer> countMap = new HashMap<String,Integer>();
		Integer spaceId = null;
		List<UserSpace> spaceList = userSpaceService.listUserSpaceBySchoolYear(user.getId(), year);
		if(userSpace.getId() != null){
			spaceId = userSpace.getId();
		}else{
			if(!CollectionUtils.isEmpty(spaceList)){
				for (UserSpace userSpace2 : spaceList) {
					if(!SysRole.TEACHER.getId().equals(userSpace2.getSysRoleId())){
						spaceId = userSpace2.getId();
						break;
					}
				}
			}else{
				spaceId = -1;
			}
		}
		countMap.put("jiaoAnCount", managerCheckHistoryCount.getManagerCheckCountByType(new Integer[]{ResTypeConstants.JIAOAN},spaceId,year));
		countMap.put("keJianCount", managerCheckHistoryCount.getManagerCheckCountByType(new Integer[]{ResTypeConstants.KEJIAN},spaceId,year));
		countMap.put("fanSiCount", managerCheckHistoryCount.getManagerCheckCountByType(new Integer[]{ResTypeConstants.FANSI,ResTypeConstants.FANSI_OTHER},spaceId,year));
		countMap.put("activityCount", managerCheckHistoryCount.getManagerCheckCountByType(new Integer[]{ResTypeConstants.ACTIVITY},spaceId,year));
		countMap.put("lessonPlanCount", managerCheckHistoryCount.getManagerCheckCountByType(new Integer[]{ResTypeConstants.TPPLAIN_SUMMARY_PLIAN,ResTypeConstants.TPPLAIN_SUMMARY_SUMMARY},spaceId,year));
		countMap.put("thesisCount", managerCheckHistoryCount.getManagerCheckCountByType(new Integer[]{ResTypeConstants.JIAOXUELUNWEN},spaceId,year));
		countMap.put("lectureCount", managerCheckHistoryCount.getManagerCheckCountByType(new Integer[]{ResTypeConstants.LECTURE},spaceId,year));
		
		countMap.put("jiaoAnOptionCount", managerCheckHistoryCount.getManagerCheckOptionCountByType(new Integer[]{ResTypeConstants.JIAOAN},spaceId,year));
		countMap.put("keJianOptionCount", managerCheckHistoryCount.getManagerCheckOptionCountByType(new Integer[]{ResTypeConstants.KEJIAN},spaceId,year));
		countMap.put("fanSiOptionCount", managerCheckHistoryCount.getManagerCheckOptionCountByType(new Integer[]{ResTypeConstants.FANSI,ResTypeConstants.FANSI_OTHER},spaceId,year));
		countMap.put("activityOptionCount", managerCheckHistoryCount.getManagerCheckOptionCountByType(new Integer[]{ResTypeConstants.ACTIVITY},spaceId,year));
		countMap.put("lessonPlanOptionCount", managerCheckHistoryCount.getManagerCheckOptionCountByType(new Integer[]{ResTypeConstants.TPPLAIN_SUMMARY_PLIAN,ResTypeConstants.TPPLAIN_SUMMARY_SUMMARY},spaceId,year));
		countMap.put("thesisOptionCount", managerCheckHistoryCount.getManagerCheckOptionCountByType(new Integer[]{ResTypeConstants.JIAOXUELUNWEN},spaceId,year));
		countMap.put("lectureOptionCount", managerCheckHistoryCount.getManagerCheckOptionCountByType(new Integer[]{ResTypeConstants.LECTURE},spaceId,year));
		model.addAttribute("countMap", countMap);
		model.addAttribute("spaceList", spaceList);
		model.addAttribute("year", year);
		model.addAttribute("spaceId", spaceId);
		searchVo.setTypeId(-1);
		return viewName("cygljl/index");
	}
	@RequestMapping("/{year}/cygljl/check_lesson")
	public String jiaoAnDetail(Model model,SearchVo searchVo,@PathVariable("year")Integer year,CheckInfo checkInfo,Page page){
		this.getVo(searchVo.getTypeId(),checkInfo,searchVo,year);
		if("0".equals(checkInfo.getFlago()) || StringUtils.isEmpty(checkInfo.getFlago())){
			checkInfo.addPage(page);
		}
		checkInfo.getPage().setPageSize(15);
		PageList<CheckInfo> checkInfoList = checkInfoService.findByPage(checkInfo);
		model.addAttribute("resIdsMap", getLessonResList(checkInfoList));//资源id
		checkInfo.addPage(new Page());
		if("1".equals(checkInfo.getFlago())){
			checkInfo.addPage(page);
		}
		checkInfo.getPage().setPageSize(3);
		this.getDataByType(checkInfo,checkInfoList,model,year,searchVo);
		return viewName("cygljl/check_lesson");
	}
	@RequestMapping("/{year}/cygljl/check_thesis")
	public String check_thesis(Model model,SearchVo searchVo,@PathVariable("year")Integer year,CheckInfo checkInfo,Page page){
		this.getVo(ResTypeConstants.JIAOXUELUNWEN,checkInfo,searchVo,year);
		if("0".equals(checkInfo.getFlago()) ||StringUtils.isEmpty(checkInfo.getFlago())){
			checkInfo.addPage(page);
		}
		checkInfo.getPage().setPageSize(15);
		PageList<CheckInfo> checkInfoList = checkInfoService.findByPage(checkInfo);
		model.addAttribute("resIdsMap", getThesisResList(checkInfoList));//资源id
		checkInfo.addPage(new Page());
		if("1".equals(checkInfo.getFlago())){
			checkInfo.addPage(page);
		}
		checkInfo.getPage().setPageSize(3);
		this.getDataByType(checkInfo,checkInfoList,model,year,searchVo);
		searchVo.setTypeId(ResTypeConstants.JIAOXUELUNWEN);
		return viewName("cygljl/check_thesis");
	}
	private void getDataByType(CheckInfo checkInfo, PageList<CheckInfo> checkInfoList, Model model, Integer year, SearchVo searchVo){
		Map<String,Object> checkMap = managerCheckHistoryCount.getCheckOptionMapList(checkInfoService.findByPage(checkInfo).getDatalist(),searchVo);
		Map<String,Object> countMap = managerCheckHistoryCount.getCheckOptionMapList(checkInfoService.findAll(checkInfo),searchVo);
		model.addAttribute("checkMapList", checkMap.get("data"));//查阅意见数据
		model.addAttribute("checkOptionList", checkInfoService.findByPage(checkInfo));//查阅意见分页集合
		model.addAttribute("checkInfoList", checkInfoList);//查阅教学文章分页集合
		model.addAttribute("year", year);
		model.addAttribute("optionCount", countMap.get("count"));
		model.addAttribute("totalCount", checkInfoList.getPage().getTotalCount());
	}
	@RequestMapping("/{year}/cygljl/check_lecture")
	public String check_lecture(Model model,SearchVo searchVo,@PathVariable("year")Integer year,CheckInfo checkInfo,Page page){
		this.getVo(ResTypeConstants.LECTURE,checkInfo,searchVo,year);
		if("0".equals(checkInfo.getFlago()) ||StringUtils.isEmpty(checkInfo.getFlago())){
			checkInfo.addPage(page);
		}
		checkInfo.getPage().setPageSize(15);
		PageList<CheckInfo> checkInfoList = checkInfoService.findByPage(checkInfo);
		Page lecturePage = checkInfoList.getPage();
		List<LectureRecords> datalist = new ArrayList<LectureRecords>();
		for(CheckInfo ci : checkInfoList.getDatalist()){
			LectureRecords lr = lectureRecordsService.findOne(ci.getResId());
			datalist.add(lr);
		}
		PageList<LectureRecords> lectureList = new PageList<LectureRecords>(datalist,lecturePage);
		model.addAttribute("lectureList", lectureList);
		checkInfo.addPage(new Page());
		if("1".equals(checkInfo.getFlago())){
			checkInfo.addPage(page);
		}
		checkInfo.getPage().setPageSize(3);
		this.getDataByType(checkInfo,checkInfoList,model,year,searchVo);
		searchVo.setTypeId(ResTypeConstants.LECTURE);
		return viewName("cygljl/check_lecture");
	}
	/**
	 * 查询教案课件反思资源id
	 * @param checkInfoList
	 */
	private Map<Integer,String> getLessonResList(PageList<CheckInfo> checkInfoList) {
		Map<Integer,String> resIdMap = new HashMap<Integer,String>();
		for (CheckInfo info : checkInfoList.getDatalist()) {
			LessonPlan plan = new LessonPlan();
			plan.addCustomCulomn("planId,resId,infoId");
			plan.setInfoId(info.getResId());
			plan.setUserId(info.getAuthorId());
			plan.setGradeId(info.getGradeId());
			plan.setSubjectId(info.getSubjectId());
			plan.setPlanType(info.getResType());
			List<LessonPlan> findAll = lessonPlanService.findAll(plan);
			List<String> resIds = new ArrayList<String>();
			for (LessonPlan lessonPlan : findAll) {
				resIds.add(lessonPlan.getResId());
			}
			resIdMap.put(info.getId(), StringUtils.join(resIds, ","));
		}
		return resIdMap;
	}
	@RequestMapping("/{year}/cygljl/check_activity")
	public String activityDetail(Model model,SearchVo searchVo,@PathVariable("year")Integer year,CheckInfo checkInfo,Page page){
		this.getVo(ResTypeConstants.ACTIVITY,checkInfo,searchVo,year);
		if("0".equals(checkInfo.getFlago()) || StringUtils.isEmpty(checkInfo.getFlago())){
			checkInfo.addPage(page);
		}
		checkInfo.getPage().setPageSize(15);
		PageList<CheckInfo> checkInfoList = checkInfoService.findByPage(checkInfo);
		Page avtivityPage = checkInfoList.getPage();
		List<Activity> datalist = new ArrayList<Activity>();
		for(CheckInfo ci : checkInfoList.getDatalist()){
			Activity activity = activityService.findOne(ci.getResId());
			datalist.add(activity);
		}
		PageList<Activity> activityList = new PageList<Activity>(datalist,avtivityPage);
		model.addAttribute("activityList", activityList);
		checkInfo.addPage(new Page());
		if("1".equals(checkInfo.getFlago())){
			checkInfo.addPage(page);
		}
		checkInfo.getPage().setPageSize(3);
		Map<String,Object> checkMap = managerCheckHistoryCount.getCheckOptionMapList(checkInfoService.findByPage(checkInfo).getDatalist(),searchVo);
		Map<String,Object> countMap = managerCheckHistoryCount.getCheckOptionMapList(checkInfoService.findAll(checkInfo),searchVo);
		model.addAttribute("checkMapList", checkMap.get("data"));//查阅意见数据
		model.addAttribute("checkOptionList", checkInfoService.findByPage(checkInfo));//查阅意见分页集合
		model.addAttribute("checkInfoList", checkInfoList);//查阅课题分页集合
		model.addAttribute("year", year);
		model.addAttribute("activityOptionCount", countMap.get("count"));
		searchVo.setTypeId(ResTypeConstants.ACTIVITY);
		return viewName("cygljl/check_activity");
	}
	
	@RequestMapping("/{year}/cygljl/check_fansi")
	public String fanSiDetail(Model model,SearchVo searchVo,@PathVariable("year")Integer year,CheckInfo checkInfo,Page page){
		this.getVo(ResTypeConstants.FANSI,checkInfo,searchVo,year);
		if(("0".equals(checkInfo.getFlago()) && "0".equals(checkInfo.getFlags())) || StringUtils.isEmpty(checkInfo.getFlago())){
			checkInfo.addPage(page);
		}
		checkInfo.getPage().setPageSize(15);
		PageList<CheckInfo> fansiList = checkInfoService.findByPage(checkInfo);
		model.addAttribute("fansiList", fansiList);
		model.addAttribute("fansiResIdsMap", getLessonResList(fansiList));//资源id
		checkInfo.addPage(new Page());
		if("1".equals(checkInfo.getFlago()) && "0".equals(checkInfo.getFlags())){
			checkInfo.addPage(page);
		}
		checkInfo.getPage().setPageSize(3);
		model.addAttribute("fansiOptionList", checkInfoService.findByPage(checkInfo));//分页集合
		Map<String,Object> fansiMap = managerCheckHistoryCount.getCheckOptionMapList(checkInfoService.findByPage(checkInfo).getDatalist(),searchVo);
		model.addAttribute("fansiMap", fansiMap.get("data"));
		Map<String,Object> fansiCountMap = managerCheckHistoryCount.getCheckOptionMapList(checkInfoService.findAll(checkInfo),searchVo);
		model.addAttribute("fansiOptionCount", fansiCountMap.get("count"));
		
		this.getVo(ResTypeConstants.FANSI_OTHER,checkInfo,searchVo,year);
		checkInfo.addPage(new Page());
		if("0".equals(checkInfo.getFlago()) && "1".equals(checkInfo.getFlags())){
			checkInfo.addPage(page);
		}
		checkInfo.getPage().setPageSize(15);
		PageList<CheckInfo> otherList = checkInfoService.findByPage(checkInfo);
		model.addAttribute("otherList", otherList);
		model.addAttribute("otherResIdsMap", getLessonResList(otherList));//资源id
		checkInfo.addPage(new Page());
		if("1".equals(checkInfo.getFlago()) && "1".equals(checkInfo.getFlags())){
			checkInfo.addPage(page);
		}
		checkInfo.getPage().setPageSize(3);
		model.addAttribute("otherOptionList", checkInfoService.findByPage(checkInfo));
		Map<String,Object> otherMap = managerCheckHistoryCount.getCheckOptionMapList(checkInfoService.findByPage(checkInfo).getDatalist(),searchVo);
		model.addAttribute("otherMap", otherMap.get("data"));
		Map<String,Object> otherCountMap = managerCheckHistoryCount.getCheckOptionMapList(checkInfoService.findAll(checkInfo),searchVo);
		model.addAttribute("otherOptionCount", otherCountMap.get("count"));
		
		model.addAttribute("year", year);
		searchVo.setTypeId(ResTypeConstants.FANSI);
		return viewName("cygljl/check_fansi");
	}
	@RequestMapping("/{year}/cygljl/check_planSummary")
	public String planSummaryDetail(Model model,SearchVo searchVo,@PathVariable("year")Integer year,CheckInfo checkInfo,Page page){
		this.getVo(ResTypeConstants.TPPLAIN_SUMMARY_PLIAN,checkInfo,searchVo,year);
		if(("0".equals(checkInfo.getFlago()) && "0".equals(checkInfo.getFlags())) || StringUtils.isEmpty(checkInfo.getFlago())){
			checkInfo.addPage(page);
		}
		checkInfo.getPage().setPageSize(15);
		PageList<CheckInfo> planList = checkInfoService.findByPage(checkInfo);
		model.addAttribute("planList", planList);
		model.addAttribute("planResIdsMap", getPlanSummaryResList(planList));//资源id
		checkInfo.addPage(new Page());
		if("1".equals(checkInfo.getFlago()) && "0".equals(checkInfo.getFlags())){
			checkInfo.addPage(page);
		}
		checkInfo.getPage().setPageSize(3);
		model.addAttribute("planOptionList", checkInfoService.findByPage(checkInfo));//分页集合
		Map<String,Object> planMap = managerCheckHistoryCount.getCheckOptionMapList(checkInfoService.findByPage(checkInfo).getDatalist(),searchVo);
		model.addAttribute("planMap", planMap.get("data"));
		Map<String,Object> planCountMap = managerCheckHistoryCount.getCheckOptionMapList(checkInfoService.findAll(checkInfo),searchVo);
		model.addAttribute("planOptionCount", planCountMap.get("count"));
		checkInfo.addPage(new Page());
		this.getVo(ResTypeConstants.TPPLAIN_SUMMARY_SUMMARY,checkInfo,searchVo,year);
		if("0".equals(checkInfo.getFlago()) && "1".equals(checkInfo.getFlags())){
			checkInfo.addPage(page);
		}
		checkInfo.getPage().setPageSize(15);
		PageList<CheckInfo> summaryList = checkInfoService.findByPage(checkInfo);
		model.addAttribute("summaryList", summaryList);
		model.addAttribute("summaryResIdsMap", getPlanSummaryResList(summaryList));//资源id
		checkInfo.addPage(new Page());
		if("1".equals(checkInfo.getFlago()) && "1".equals(checkInfo.getFlags())){
			checkInfo.addPage(page);
		}
		checkInfo.getPage().setPageSize(3);
		model.addAttribute("summaryOptionList", checkInfoService.findByPage(checkInfo));
		Map<String,Object> summaryMap = managerCheckHistoryCount.getCheckOptionMapList(checkInfoService.findByPage(checkInfo).getDatalist(),searchVo);
		model.addAttribute("summaryMap", summaryMap.get("data"));
		Map<String,Object> summaryCountMap = managerCheckHistoryCount.getCheckOptionMapList(checkInfoService.findAll(checkInfo),searchVo);
		model.addAttribute("summaryOptionCount", summaryCountMap.get("count"));
		
		model.addAttribute("year", year);
		searchVo.setTypeId(ResTypeConstants.TPPLAIN_SUMMARY_PLIAN);
		return viewName("cygljl/check_planSummary");
	}
	/**
	 * @param planList
	 * @return
	 */
	private Map<Integer,Thesis> getThesisResList(PageList<CheckInfo> planList) {
		Map<Integer,Thesis> resIdMap = new HashMap<Integer,Thesis>();
		for (CheckInfo info : planList.getDatalist()) {
			Thesis thesis = thesisService.findOne(info.getResId());
			resIdMap.put(info.getId(), thesis);
		}
		return resIdMap;
	}
	private Map<Integer,PlainSummary> getPlanSummaryResList(PageList<CheckInfo> planList) {
		Map<Integer,PlainSummary> resIdMap = new HashMap<Integer,PlainSummary>();
		for (CheckInfo info : planList.getDatalist()) {
			PlainSummary plan = plainSummaryService.findOne(info.getResId());
			resIdMap.put(info.getId(), plan);
		}
		return resIdMap;
	}
	private void getVo(Integer type,CheckInfo checkInfo, SearchVo searchVo, Integer year){
		checkInfo.setResType(type);
		checkInfo.addOrder(" createtime desc");
		checkInfo.setSchoolYear(year);
		checkInfo.setTerm(searchVo.getTermId());
		searchVo.setSchoolYear(year);
		checkInfo.setSpaceId(searchVo.getSpaceId());
		if(!StringUtils.isEmpty(searchVo.getSearchStr())){
			checkInfo.setTitle(SqlMapping.LIKE_PRFIX + searchVo.getSearchStr() + SqlMapping.LIKE_PRFIX);
		}
		if(checkInfo.getFlago() != null && checkInfo.getFlago().length()==0){
			checkInfo.setFlago(null);
		}
		if(checkInfo.getFlags() != null && checkInfo.getFlags().length()==0){
			checkInfo.setFlags(null);
		}
		checkInfo.pageSize(1);
	}
}
