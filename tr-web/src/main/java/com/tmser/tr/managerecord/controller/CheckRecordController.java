/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.managerecord.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tmser.tr.activity.bo.Activity;
import com.tmser.tr.activity.bo.ActivityTracks;
import com.tmser.tr.activity.service.ActivityService;
import com.tmser.tr.activity.service.ActivityTracksService;
import com.tmser.tr.check.bo.CheckInfo;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.CookieUtils;
import com.tmser.tr.common.utils.MobileUtils;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.lecturerecords.bo.LectureRecords;
import com.tmser.tr.lecturerecords.service.LectureRecordsService;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.manage.resources.bo.Attach;
import com.tmser.tr.manage.resources.service.AttachService;
import com.tmser.tr.managerecord.service.FanSiCheckService;
import com.tmser.tr.managerecord.service.JiaoAnCheckService;
import com.tmser.tr.managerecord.service.JtbkCheckService;
import com.tmser.tr.managerecord.service.KeJianCheckService;
import com.tmser.tr.managerecord.service.LectureCheckService;
import com.tmser.tr.managerecord.service.ThesisCheckService;
import com.tmser.tr.plainsummary.bo.PlainSummary;
import com.tmser.tr.plainsummary.service.PlainSummaryService;
import com.tmser.tr.thesis.bo.Thesis;
import com.tmser.tr.thesis.service.ThesisService;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.writelessonplan.service.LessonPlanService;

/**
 * <pre>
 *  管理记录-查阅记录-查阅课件
 * </pre>
 *
 * @author zpp
 * @version $Id: CheckRecordController.java, v 1.0 2015年6月4日 上午11:08:01 zpp Exp $
 */
@Controller
@RequestMapping("/jy/managerecord/check")
public class CheckRecordController extends AbstractController{
	@Autowired
	private ActivityService activityService;
	@Autowired
	private LectureRecordsService lectureRecordsService;
	
	@Resource
	private JiaoAnCheckService jiaoancheckService;
	@Resource
	private KeJianCheckService kejianCheckService;
	@Resource
	private FanSiCheckService fansiCheckService;
	@Resource
	private JtbkCheckService jtbkCheckService;
	@Resource
	private ThesisCheckService thesisCheckService;
	@Resource
	private LectureCheckService lectureCheckService;
	@Resource
	private LessonPlanService lessonPlanService;
	@Resource
	private AttachService activityAttachService;
	@Resource
	private ThesisService thesisService;
	@Resource
	private PlainSummaryService plainSummaryService;
	@Resource
	private ActivityTracksService activityTracksService;
	
	/**
	 * 管理记录查阅课件首页
	 * @param type
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/1")
	public String index(Model m ,Integer term , Page page,Integer listType) {
		if(term==null){
			term = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);
		}
		//获取当前用户空间
		UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		Map<String,Integer> countNumbers = kejianCheckService.getCheckNumbers(ResTypeConstants.KEJIAN, userSpace.getUserId(),term);
		m.addAttribute("checkCount", countNumbers.get("checkCount"));
		m.addAttribute("yijianCount", countNumbers.get("yijianCount"));
		//获取查阅数量和意见数量
		if(listType == null || listType==0){//查阅课题
			if(MobileUtils.isNormal()){
				page.setPageSize(18);
			}else{
				page.setPageSize(1000);
			}
			PageList<CheckInfo> checkInfoList = kejianCheckService.getCheckInfoListByType(ResTypeConstants.KEJIAN, userSpace.getUserId(),term,page,false);
			m.addAttribute("checkInfoList", checkInfoList);
			listType = 0;
		}else{//查阅意见
			if(MobileUtils.isNormal()){
				page.setPageSize(5);
			}else{
				page.setPageSize(1000);
			}
			PageList<CheckInfo> checkInfoList = kejianCheckService.getCheckInfoListByType(ResTypeConstants.KEJIAN, userSpace.getUserId(),term,page,true);
			List<Map<String,Object>> checkMapList = kejianCheckService.getCheckOptionMapList(checkInfoList);
			m.addAttribute("checkMapList", checkMapList);
			m.addAttribute("checkInfoList", checkInfoList);
		}
		m.addAttribute("listType", listType);
		m.addAttribute("term", term);
		
		return "managerecord/check/checkKeJianPage";	
	}
	
	/**
   * 查看课件详情及查阅信息
   * @param m
   * @return
   */
  @RequestMapping("/1/kejianView")
  public String kejianView(Integer type,Integer lesInfoId,Model m){
    kejianCheckService.kejianView(type,lesInfoId, m);
    return "managerecord/check/view_kejian";
  }
	
	/**
	 * 管理记录查阅反思详情
	 * @param type
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/2-3")
	public String fansiindex(Model m ,Integer term , Page page,Integer listType,Integer resType) {
		if(term==null){
			term = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);
		}
		//获取当前用户空间
		UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		
		Map<String,Integer> khCount = fansiCheckService.getCheckNumbers(ResTypeConstants.FANSI, userSpace.getUserId(),term);
		Integer khCheck = khCount.get("checkCount");
		Integer khYijian = khCount.get("yijianCount");
		Map<String,Integer> qtCount = fansiCheckService.getCheckNumbers(ResTypeConstants.FANSI_OTHER, userSpace.getUserId(),term);
		Integer qtCheck = qtCount.get("checkCount");
		Integer qtYijian = qtCount.get("yijianCount");
		m.addAttribute("checkCount", khCheck+qtCheck);
		m.addAttribute("yijianCount", khYijian+qtYijian);
		//获取查阅数量和意见数量
		if(resType==null){
			resType = ResTypeConstants.FANSI;
		}
		if(listType == null || listType==0){//查阅课题
			m.addAttribute("khCount", khCheck);
			m.addAttribute("qtCount", qtCheck);
			if(MobileUtils.isNormal()){
				page.setPageSize(18);
			}else{
				page.setPageSize(1000);
			}
			PageList<CheckInfo> checkInfoList = fansiCheckService.getCheckInfoListByType(resType, userSpace.getUserId(),term,page,false);
			m.addAttribute("checkInfoList", checkInfoList);
			listType = 0;
		}else{//查阅意见
			m.addAttribute("khCount", khYijian);
			m.addAttribute("qtCount", qtYijian);
			if(MobileUtils.isNormal()){
				page.setPageSize(5);
			}else{
				page.setPageSize(1000);
			}
			PageList<CheckInfo> checkInfoList = fansiCheckService.getCheckInfoListByType(resType, userSpace.getUserId(),term,page,true);
			List<Map<String,Object>> checkMapList = fansiCheckService.getCheckOptionMapList(checkInfoList);
			m.addAttribute("checkMapList", checkMapList);
			m.addAttribute("checkInfoList", checkInfoList);
		}
		m.addAttribute("resType", resType);
		m.addAttribute("listType", listType);
		m.addAttribute("term", term);
		
		return "managerecord/check/checkFanSiPage";	
	}
	
	/**
   * 查看反思详情及查阅信息
   * @param m
   * @return
   */
  @RequestMapping("/2-3/fansiView")
  public String fansiView(Integer type,Integer lesInfoId,Model m){
    fansiCheckService.fansiView(type,lesInfoId, m);
    return "managerecord/check/view_fansi";
  }
	
	/**
	 * 管理记录查阅集体备课首页
	 * @param type
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/5")
	public String activityindex(Model m ,Integer term , Page page,Integer listType) {
		if(term==null){
			term = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);
		}
		//获取当前用户空间
		UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		Map<String,Integer> countNumbers = jtbkCheckService.getCheckNumbers(ResTypeConstants.ACTIVITY, userSpace.getUserId(),term);
		m.addAttribute("checkCount", countNumbers.get("checkCount"));
		m.addAttribute("yijianCount", countNumbers.get("yijianCount"));
		//获取查阅数量和意见数量
		if(listType == null || listType==0){//查阅课题
			if(MobileUtils.isNormal()){
				page.setPageSize(15);
			}else{
				page.setPageSize(1000);
			}
			PageList<CheckInfo> checkInfoList = jtbkCheckService.getCheckInfoListByType(ResTypeConstants.ACTIVITY, userSpace.getUserId(),term,page,false);
			//处理列表数据
			PageList<Activity> activityList = getActivityData(checkInfoList); 
			m.addAttribute("activityList", activityList);
			listType = 0;
		}else{//查阅意见
			if(MobileUtils.isNormal()){
				page.setPageSize(5);
			}else{
				page.setPageSize(1000);
			}
			PageList<CheckInfo> checkInfoList = jtbkCheckService.getCheckInfoListByType(ResTypeConstants.ACTIVITY, userSpace.getUserId(),term,page,true);
			List<Map<String,Object>> checkMapList = jtbkCheckService.getCheckOptionMapList(checkInfoList);
			m.addAttribute("checkMapList", checkMapList);
			m.addAttribute("checkInfoList", checkInfoList);
		}
		m.addAttribute("listType", listType);
		m.addAttribute("term", term);
		
		return "managerecord/check/checkActivityPage";	
	}
	
	/**
	 * 通过查阅数据封装成活动列表数据
	 * @param checkInfoList
	 * @return
	 */
	private PageList<Activity> getActivityData(PageList<CheckInfo> checkInfoList) {
		Page page = checkInfoList.getPage();
		List<Integer> idslist = new ArrayList<Integer>();
		for(CheckInfo ci : checkInfoList.getDatalist()){
			idslist.add(ci.getResId());
		}
		if(!CollectionUtils.isEmpty(idslist)){
			Map<String,Object> params = new HashMap<>();
			params.put("idslist", idslist);
			Activity model = new Activity();
			model.addCustomCulomn("id,typeId,activityName,typeName,subjectName,gradeName,organizeUserName,startTime,endTime,commentsNum,isShare");
			model.addCustomCondition(" and id in (:idslist) ", params);
			List<Activity> datalist = activityService.findAll(model);
			return new PageList<Activity>(datalist,page);
		}
		return new PageList<Activity>(null,page);
		
	}
	
	/**
	 * 查阅活动列表
	 * @return
	 */
	@RequestMapping(value="/5/chayueActivity")
	public String chayueActivity(Integer activityId,Model m){
    	
    	Activity activity = activityService.findOne(activityId);
    	Integer typeId = activity.getTypeId();
    	m.addAttribute("activity", activity);
    	//有权限的参与人列表查询
    	List<UserSpace> usList = activityService.findUserBySubjectAndGrade(activity);
    	m.addAttribute("usList", usList);
    	m.addAttribute("type",ResTypeConstants.ACTIVITY);
    	
    	if("1".equals(String.valueOf(typeId))){//同备教案
    	  m.addAttribute("url","/jy/managerecord/check/5/chayueActivity1?activityId="+activityId);
        return "/resview/pageofficeOpenWindow";
    	}else{//主题研讨
    		Attach temp = new Attach();
        	temp.setActivityId(activityId);
        	List<Attach> attachList = activityAttachService.findAll(temp);
        	m.addAttribute("activity", activity);
        	m.addAttribute("attachList", attachList);
        	return "/managerecord/check/chayueZhuYan";
    	}
		
	}
	
	/**
   * 查阅活动列表
   * @return
   */
  @RequestMapping(value="/5/chayueActivity1")
  public String chayueActivity1(Integer activityId,Model m){
      
      Activity activity = activityService.findOne(activityId);
      Integer typeId = activity.getTypeId();
      m.addAttribute("activity", activity);
      //有权限的参与人列表查询
      List<UserSpace> usList = activityService.findUserBySubjectAndGrade(activity);
      m.addAttribute("usList", usList);
      m.addAttribute("type",ResTypeConstants.ACTIVITY);
    
      // 获取主备人的教案
      List<ActivityTracks> zhubeiList = activityTracksService.getActivityTracks_zhubei(activityId);
      m.addAttribute("zhubeiList", zhubeiList);
      //获取主备人的教案
      List<LessonPlan> lessonPlanList = lessonPlanService.getJiaoanByInfoId(activity.getInfoId());
      m.addAttribute("lessonPlanList", lessonPlanList);
      // 整理教案集合
      List<ActivityTracks> zhengliList = activityTracksService.getActivityTracks_zhengli(activity.getId());
      m.addAttribute("zhengliList", zhengliList);
      return "/managerecord/check/chayueJiBei";
    
  }
	
	/**
	 * 查阅听课记录详情页
	 * @param id
	 * @param m
	 * @return
	 */
	@RequestMapping(value="/6/checkLectureRecordInfo")
	public String toCheckLectureRecordInfo(Integer id,Model m){
		LectureRecords lr = lectureRecordsService.findOne(id);
		m.addAttribute("lr", lr);
		return "/managerecord/check/lectureRecordsCheckInfo";
	}

	/**
	 * 转到教案查阅详情页
	 * @param term
	 * @param m
	 * @return
	 */
	@RequestMapping("/0")
	public String toCheckLessonPlanInfo(Integer term,Page page,Integer listType, Model m){
		//获取当前用户空间
		UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		Map<String,Integer> countMap = jiaoancheckService.getCheckNumbers(ResTypeConstants.JIAOAN, userSpace.getUserId(),term);
		m.addAttribute("checkInfoCount", countMap.get("checkCount"));
		m.addAttribute("optionCount", countMap.get("yijianCount"));
		if(listType==null || listType==0){
			//已查阅的课题
			if(MobileUtils.isNormal()){
				page.setPageSize(16);
			}else{
				page.setPageSize(1000);
			}
			PageList<CheckInfo> checkInfoPageList = jiaoancheckService.getCheckInfoListByType(ResTypeConstants.JIAOAN, userSpace.getUserId(),term,page,false);
			m.addAttribute("checkInfoPageList", checkInfoPageList);
			m.addAttribute("page", checkInfoPageList.getPage());
		}else if(listType==1){
			//查阅意见
			if(MobileUtils.isNormal()){
				page.setPageSize(5);
			}else{
				page.setPageSize(1000);
			}
			PageList<CheckInfo> checkInfoPageList = jiaoancheckService.getCheckInfoListByType(ResTypeConstants.JIAOAN, userSpace.getUserId(),term,page,true);
			List<Map<String,Object>> checkMapList = jiaoancheckService.getCheckOptionMapList(checkInfoPageList);
			m.addAttribute("checkMapList", checkMapList);
			m.addAttribute("page", checkInfoPageList.getPage());
		}
		m.addAttribute("listType", listType);
		if(term==null){
			term = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM); //学期
		}
		m.addAttribute("term", term);
		return "/managerecord/check/lessonPlanCheckPage";
	}
	
	/**
   * 跳到教案课题的详细查阅页面
   * @param planInfoId
   * @param m
   * @return
   */
  @RequestMapping("/0/viewLessonPlanCheckInfo")
  public String viewLessonPlanCheckInfo(Integer planInfoId,Model m){
    jiaoancheckService.viewLessonPlanCheckInfo(planInfoId,m);
    return "/managerecord/check/lessonPlanCheckInfo";
  }
	
	/**
	 * 转到听课记录详情页
	 * @param term
	 * @param m
	 * @return
	 */
	@RequestMapping("/t")
	public String toTingkejilu(Integer term,Page page,Integer listType, Model m){
		//获取当前用户空间
		UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		Map<String,Integer> countMap = jiaoancheckService.getCheckNumbers(ResTypeConstants.JIAOAN, userSpace.getUserId(),term);
		m.addAttribute("checkInfoCount", countMap.get("checkCount"));
		m.addAttribute("optionCount", countMap.get("yijianCount"));
		if(listType==null || listType==0){
			//已查阅的课题
			page.setPageSize(16);
			PageList<CheckInfo> checkInfoPageList = jiaoancheckService.getCheckInfoListByType(ResTypeConstants.JIAOAN, userSpace.getUserId(),term,page,false);
			m.addAttribute("checkInfoPageList", checkInfoPageList);
			m.addAttribute("page", checkInfoPageList.getPage());
		}else if(listType==1){
			//查阅意见
			page.setPageSize(5);
			PageList<CheckInfo> checkInfoPageList = jiaoancheckService.getCheckInfoListByType(ResTypeConstants.JIAOAN, userSpace.getUserId(),term,page,true);
			List<Map<String,Object>> checkMapList = jiaoancheckService.getCheckOptionMapList(checkInfoPageList);
			m.addAttribute("checkMapList", checkMapList);
			m.addAttribute("page", checkInfoPageList.getPage());
		}
		m.addAttribute("listType", listType);
		if(term==null){
			term = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM); //学期
		}
		m.addAttribute("term", term);
		return "/managerecord/check/lessonPlanCheckPage";
	}
	
	/**
	 * 管理记录查阅计划总结详情
	 * @param type
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/8-9")
	public ModelAndView getPlainSummary(Integer term , Page page,Integer listType,Integer resType) {
		ModelAndView m = new ModelAndView("/managerecord/check/planSummary");
		if(term==null){
			term = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);
		}
		//获取当前用户空间
		UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		
		Map<String,Integer> khCount = jiaoancheckService.getCheckNumbers(ResTypeConstants.TPPLAIN_SUMMARY_PLIAN, userSpace.getUserId(),term);
		Integer khCheck = khCount.get("checkCount");
		Integer khYijian = khCount.get("yijianCount");
		Map<String,Integer> qtCount = jiaoancheckService.getCheckNumbers(ResTypeConstants.TPPLAIN_SUMMARY_SUMMARY, userSpace.getUserId(),term);
		Integer qtCheck = qtCount.get("checkCount");
		Integer qtYijian = qtCount.get("yijianCount");
		m.addObject("checkCount", khCheck+qtCheck);
		m.addObject("yijianCount", khYijian+qtYijian);
		//获取查阅数量和意见数量
		if(resType==null){
			resType = ResTypeConstants.TPPLAIN_SUMMARY_PLIAN;
		}
		if(listType == null || listType==0){//查阅课题
			m.addObject("khCount", khCheck);
			m.addObject("qtCount", qtCheck);
			if(MobileUtils.isNormal()){
				page.setPageSize(18);
			}else{
				page.setPageSize(1000);
			}
			PageList<CheckInfo> checkInfoList = jiaoancheckService.getCheckInfoListByType(resType, userSpace.getUserId(),term,page,false);
			m.addObject("checkInfoList", checkInfoList);
			listType = 0;
		}else{//查阅意见
			m.addObject("khCount", khYijian);
			m.addObject("qtCount", qtYijian);
			if(MobileUtils.isNormal()){
				page.setPageSize(5);
			}else{
				page.setPageSize(1000);
			}
			PageList<CheckInfo> checkInfoList = jiaoancheckService.getCheckInfoListByType(resType, userSpace.getUserId(),term,page,true);
			List<Map<String,Object>> checkMapList = jiaoancheckService.getCheckOptionMapList(checkInfoList);
			m.addObject("checkMapList", checkMapList);
			m.addObject("checkInfoList", checkInfoList);
		}
		m.addObject("resType", resType);
		m.addObject("listType", listType);
		m.addObject("term", term);
		return m;
	}
	
	/**
	 * 管理记录查阅听课记录详情
	 * @param type
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/6")
	public String lectureindex(Model m ,Integer term , Page page,Integer listType) {
		if(term==null){
			term = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);
		}
		//获取当前用户空间
		UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		Map<String,Integer> countNumbers = lectureCheckService.getCheckNumbers(ResTypeConstants.LECTURE, userSpace.getUserId(),term);
		m.addAttribute("checkCount", countNumbers.get("checkCount"));
		m.addAttribute("yijianCount", countNumbers.get("yijianCount"));
		//获取查阅数量和意见数量
		if(listType == null || listType==0){//查阅课题
			if(MobileUtils.isNormal()){
				page.setPageSize(15);
			}else{
				page.setPageSize(1000);
			}
			PageList<CheckInfo> checkInfoList = lectureCheckService.getCheckInfoListByType(ResTypeConstants.LECTURE, userSpace.getUserId(),term,page,false);
			//处理列表数据
			PageList<LectureRecords> lectureList = getLectureData(checkInfoList); 
			m.addAttribute("lectureList", lectureList);
			listType = 0;
		}else{//查阅意见
			if(MobileUtils.isNormal()){
				page.setPageSize(5);
			}else{
				page.setPageSize(1000);
			}
			PageList<CheckInfo> checkInfoList = lectureCheckService.getCheckInfoListByType(ResTypeConstants.LECTURE, userSpace.getUserId(),term,page,true);
			List<Map<String,Object>> checkMapList = lectureCheckService.getCheckOptionMapList(checkInfoList);
			m.addAttribute("checkMapList", checkMapList);
			m.addAttribute("checkInfoList", checkInfoList);
		}
		m.addAttribute("listType", listType);
		m.addAttribute("term", term);
		return "managerecord/check/checkLecturePage";
	}
	
	/**
	 * 封装听课记录的数据
	 * @param checkInfoList
	 * @return
	 */
	private PageList<LectureRecords> getLectureData(PageList<CheckInfo> checkInfoList) {
		Page page = checkInfoList.getPage();
		List<Integer> idslist = new ArrayList<Integer>();
		for(CheckInfo ci : checkInfoList.getDatalist()){
			idslist.add(ci.getResId());
		}
		if(!CollectionUtils.isEmpty(idslist)){
			Map<String,Object> params = new HashMap<>();
			params.put("idslist", idslist);
			LectureRecords model = new LectureRecords();
			model.addCustomCulomn("id,topic,type,grade,subject,teachingPeople,numberLectures,lectureTime,isShare");
			model.addCustomCondition(" and id in (:idslist) ", params);
			List<LectureRecords> datalist = lectureRecordsService.findAll(model);
			return new PageList<LectureRecords>(datalist,page);
		}
		return new PageList<LectureRecords>(null,page);
	}
	/**
	 * 管理记录查阅教学文章详情
	 * @param type
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/10")
	public String thesisindex(Model m ,Integer term , Page page,Integer listType) {
		if(term==null){
			term = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);
		}
		//获取当前用户空间
		UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		Map<String,Integer> countNumbers = thesisCheckService.getCheckNumbers(ResTypeConstants.JIAOXUELUNWEN, userSpace.getUserId(),term);
		m.addAttribute("checkCount", countNumbers.get("checkCount"));
		m.addAttribute("yijianCount", countNumbers.get("yijianCount"));
		//获取查阅数量和意见数量
		if(listType == null || listType==0){//查阅课题
			if(MobileUtils.isNormal()){
				page.setPageSize(18);
			}else{
				page.setPageSize(1000);
			}
			PageList<CheckInfo> checkInfoList = thesisCheckService.getCheckInfoListByType(ResTypeConstants.JIAOXUELUNWEN, userSpace.getUserId(),term,page,false);
			m.addAttribute("checkInfoList", checkInfoList);
			listType = 0;
		}else{//查阅意见
			if(MobileUtils.isNormal()){
				page.setPageSize(5);
			}else{
				page.setPageSize(1000);
			}
			PageList<CheckInfo> checkInfoList = thesisCheckService.getCheckInfoListByType(ResTypeConstants.JIAOXUELUNWEN, userSpace.getUserId(),term,page,true);
			List<Map<String,Object>> checkMapList = thesisCheckService.getCheckOptionMapList(checkInfoList);
			m.addAttribute("checkMapList", checkMapList);
			m.addAttribute("checkInfoList", checkInfoList);
		}
		m.addAttribute("listType", listType);
		m.addAttribute("term", term);
		return "managerecord/check/checkThesisPage";
	}
	
	/**
   * 查看教学文章
   * @param 
   * @param m
   * @return
   */
  @RequestMapping("/10/thesisView")
  public String viewThesis(Thesis thesis,Model m){
    m.addAttribute("findOne",thesisService.findOne(thesis.getId()));
    m.addAttribute("thesis",thesis);
    return viewName("view_thesis");
  }
  
	/**
   * 查看计划总结
   * @param 
   * @param m
   * @return
   */
  @RequestMapping("/planSummaryView")
  public String planSummaryView(PlainSummary plan,Model m,Integer resType){
    m.addAttribute("model",plainSummaryService.findOne(plan.getId()));
    m.addAttribute("resType",resType);
    return viewName("planSummaryView");
  }
	
}
