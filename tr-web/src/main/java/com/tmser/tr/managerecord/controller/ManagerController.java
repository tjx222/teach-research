/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.managerecord.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.activity.bo.Activity;
import com.tmser.tr.activity.bo.SchoolActivity;
import com.tmser.tr.activity.bo.SchoolTeachCircleOrg;
import com.tmser.tr.activity.dao.SchoolTeachCircleOrgDao;
import com.tmser.tr.activity.service.ActivityService;
import com.tmser.tr.activity.service.SchoolActivityService;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.MobileUtils;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.lecturerecords.bo.LectureRecords;
import com.tmser.tr.lecturerecords.service.LectureRecordsService;
import com.tmser.tr.managerecord.service.ManagerService;
import com.tmser.tr.managerecord.service.ManagerVO;
import com.tmser.tr.plainsummary.bo.PlainSummary;
import com.tmser.tr.plainsummary.service.PlainSummaryService;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * <pre>
 * 管理记录
 * </pre>
 *
 * @author sysc
 * @version $Id: ManagerController.java, v 1.0 2015年5月12日 上午11:08:01 sysc Exp $
 */
@Controller
@RequestMapping("/jy/managerecord")
public class ManagerController extends AbstractController {
	@Autowired
	private ManagerService managerService;

	@Autowired
	LectureRecordsService lectureRecordsService;

	@Autowired
	private PlainSummaryService plainSummaryService;

	@Autowired
	private ActivityService activityService;

	@Autowired
	private SchoolActivityService schoolActivityService;

	@Autowired
	private SchoolTeachCircleOrgDao schoolTeachCircleOrgDao;

	/**
	 * 查阅记录
	 * 
	 * @param id
	 * @param m
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkdata", method = RequestMethod.GET)
	public List<ManagerVO> checkdata(@RequestParam(value = "grade", required = false) Integer grade, @RequestParam(value = "term", required = false) Integer term,
			@RequestParam(value = "subject", required = false) Integer subject) {
		return managerService.findRecordList(grade, subject, term);
	}

	/**
	 * 教研活动记录json
	 * 
	 * @param id
	 * @param m
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/jyhdata")
	public List<ManagerVO> jyhdata(@RequestParam(value = "term", required = false) int term) {
		return managerService.findActivityList(term);
	}

	/**
	 * 教学管理记录json
	 * 
	 * @param id
	 * @param m
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/tchdata")
	public List<ManagerVO> tchdata(@RequestParam(value = "term", required = false) int term, Model m) {
		return managerService.findMangerList(term);
	}

	/**
	 * 管理记录统计首页
	 * 
	 * @param type
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index(Map<String, Integer> m) {
		managerService.index(m);
		return "managerecord/index";
	}

	/**
	 * 管理记录数据统计页面
	 * 
	 * @param type
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "/indexdata")
	public String indexData(@RequestParam(value = "grade", required = false) Integer grade, @RequestParam(value = "term", required = false) Integer term,
			@RequestParam(value = "subject", required = false) Integer subject, Model m) {
		m.addAttribute("checklist", managerService.findRecordList(grade, subject, term));
		m.addAttribute("activitylist", managerService.findActivityList(term));
		m.addAttribute("managelist", managerService.findMangerList(term));
		m.addAttribute("term", term);
		return "managerecord/indexdata";
	}

	/**
	 * 跳转查阅记录
	 * 
	 * @param grade
	 * @param term
	 * @param subject
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "/check")
	public String list(@RequestParam(value = "grade", required = false) Integer grade, @RequestParam(value = "term", required = false) Integer term,
			@RequestParam(value = "subject", required = false) Integer subject, Model m) {

		if (term == null)
			term = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);// 学期
		m.addAttribute("term", term);
		m.addAttribute("grade", grade);
		m.addAttribute("subject", subject);
		return "managerecord/check";
	}

	/**
	 * 跳转教学管理记录
	 * 
	 * @param term
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "/tch")
	public String tch(@RequestParam(value = "term", required = false) Integer term, Model m) {
		if (term == null)
			term = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);// 学期
		m.addAttribute("term", term);
		return "managerecord/tch";
	}

	/**
	 * 跳转教研活动记录
	 * 
	 * @param term
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "/jyhd")
	public String jyhd(@RequestParam(value = "term", required = false) Integer term, Model m) {
		term = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);// 学期
		m.addAttribute("term", term);
		return "managerecord/jyhd";
	}

	/**
	 * 听课记录查看详细
	 * 
	 * @param m
	 * @param request
	 * @return
	 */
	@RequestMapping("lecturedetailed")
	public String lectureDetailed(Model m, HttpServletRequest request) {
		return "forward:/jy/managerecord/lecDetail";// 调到听课资源详细页
	}

	/**
	 * 获取用户的所有计划总结消息(管理记录中计划总结详情)
	 * 
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "planDetail")
	public String plainSummaryList(@RequestParam(value = "listType", required = false) Integer listType, @RequestParam(value = "term", required = false) Integer term, Model m) {
		// 获取用户消息
		User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		// 学期
		Integer schoolTerm = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);
		PlainSummary model = new PlainSummary();
		model.setUserId(user.getId());
		model.setOrgId(userSpace.getOrgId());
		model.setPhaseId(userSpace.getPhaseId());
		model.setSchoolYear(userSpace.getSchoolYear());
		model.addOrder("lastupDttm desc");
		if (term != null)
			model.setTerm(term);
		else
			model.setTerm(schoolTerm);
		// 获取用户的所有撰写计划总结
		List<PlainSummary> list = plainSummaryService.findAll(model); // 撰写
		model.setIsShare(1);
		List<PlainSummary> sharelist = plainSummaryService.findAll(model); // 分享
		PlainSummary model1 = new PlainSummary();
		model1.setUserId(user.getId());
		model1.setOrgId(userSpace.getOrgId());
		model1.setPhaseId(userSpace.getPhaseId());
		model1.setSchoolYear(userSpace.getSchoolYear());
		model1.addOrder("lastupDttm desc");
		if (term != null)
			model1.setTerm(term);
		else
			model1.setTerm(schoolTerm);
		// 获取用户的所有分享计划总结
		model1.addCustomCondition("  and  category in (1,3)", null);
		if (listType == 1)
			model1.setIsShare(listType);
		List<PlainSummary> planlist = plainSummaryService.findAll(model1); // 计划
		model1.addCustomCondition("  and  category in (2,4)", null);
		List<PlainSummary> zlist = plainSummaryService.findAll(model1); // 总结
		m.addAttribute("count", list.size());
		m.addAttribute("shareCount", sharelist.size());
		m.addAttribute("list", planlist);
		m.addAttribute("listCount", planlist.size());
		m.addAttribute("zlist", zlist);
		m.addAttribute("zCount", zlist.size());

		m.addAttribute("listType", listType);

		// 学年
		Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);

		m.addAttribute("schoolYear", schoolYear);
		if (term != null)
			m.addAttribute("term", term);
		else
			m.addAttribute("term", schoolTerm);
		return "/managerecord/planDetail";
	}

	/**
	 * 按条件查询所有的听课记录页面(管理记录中听课记录详情)
	 * 
	 * @return
	 */
	@RequestMapping("lecDetail")
	public String lecDetail(LectureRecords model, @RequestParam(value = "term1", required = false) Integer term1, @RequestParam(value = "subject1", required = false) Integer subject1,
			@RequestParam(value = "grade1", required = false) Integer grade1, Integer phaseId1, Model m) {
		Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		UserSpace us = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		if (term1 == null)
			term1 = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);
		if (phaseId1 == null)
			phaseId1 = us.getPhaseId();
		if (grade1 != null)
			model.setGradeId(grade1);
		if (subject1 != null)
			model.setSubjectId(subject1);
		model.setTerm(term1);
		model.setPhaseId(phaseId1);
		if (MobileUtils.isNormal()) {
			model.pageSize(15);
		} else {
			model.pageSize(1000);
		}
		model.setSchoolYear(schoolYear);// 当前学年
		model.setLecturepeopleId(user.getId());// 听课人ID
		model.setIsDelete(false);// 不删除
		model.setIsEpub(1);// 发布
		model.addOrder("epubTime desc");// 按照发布时间降序
		PageList<LectureRecords> plList = lectureRecordsService.findByPage(model);// 查询当前页的评论
		m.addAttribute("data", plList);// 按照分页进行查询

		model.setIsEpub(0);// 查找草稿的集合
		model.setType(null);// 草稿箱不区分校内校外
		List<LectureRecords> caogaoList = lectureRecordsService.findAll(model);// 查找所有的草稿
		m.addAttribute("caogaoSize", caogaoList.size());

		model.setIsEpub(null);// 查找所有的听课记录,无论是否发布
		List<LectureRecords> allList = lectureRecordsService.findAll(model);
		System.out.println(allList.size());

		// 加载学科List
		m.addAttribute("subjectList", activityService.findSubjectList());
		// 加载年级List
		m.addAttribute("gradeList", activityService.findGradeList());
		m.addAttribute("term", term1);
		m.addAttribute("grade", grade1);
		m.addAttribute("subject", subject1);
		m.addAttribute("phaseId", phaseId1);
		// if(allList.size()==0){
		// return "/lecturerecords/nolecturerecordslist";
		// }else{
		return "/managerecord/lecDetail";
		// }
	}

	/**
	 * 查看单个听课记录
	 * 
	 * @param info
	 * @param m
	 * @return
	 */
	@RequestMapping("lecView")
	public String seeTopic(Model m, Integer id) {
		LectureRecords lr = lectureRecordsService.findOne(id);
		m.addAttribute("lr", lr);// 按照主键查询单个
		return "/managerecord/lecview";
	}

	/**
	 * 管理者 教研管理记录进入集体活动(管理者 集体备课)详细页面
	 * 
	 * @return
	 */
	@RequestMapping("/activity")
	public String activity(@RequestParam(value = "listType", required = false)String listType, Page page, 
			@RequestParam(required=false)Integer term, Model model ) {
		UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
		Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);// 学年

		if (term == null)
			term = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);// 学期

		Activity activity1 = new Activity();
		activity1.setTerm(term);
		activity1.setOrgId(userSpace.getOrgId());
		activity1.setStatus(1);
		activity1.setSchoolYear(schoolYear);
		activity1.setOrganizeUserId(userSpace.getUserId());// 用户Id
		activity1.setPhaseId(userSpace.getPhaseId());

		Activity activity2 = new Activity();
		activity2.setTerm(term);
		activity2.addAlias("a");
		activity2.addCustomCulomn("a.id");
		activity2.setPhaseId(userSpace.getPhaseId());
		activity2.setOrgId(userSpace.getOrgId());
		activity2.setSchoolYear(schoolYear);
		activity2.setStatus(1);
		
		activity2.buildCondition(" and EXISTS( select b.id from Discuss b where b.activityId = a.id and b.typeId=:typeId and b.crtId=:crtId LIMIT 0,1)")
			.put("typeId", ResTypeConstants.ACTIVITY)
			.put("crtId", userSpace.getUserId());

		listType = listType == null ? "0" : listType;
		// 列表选型
		model.addAttribute("listType", listType);
		model.addAttribute("term", term);
		
		// 页码
		activity1.addPage(page);
		activity2.addPage(page);
		
		activity1.pageSize(10);
		activity2.pageSize(10);
		
		int myCount = 0;
		int joinCount = 0;
		PageList<Activity> activityList;
		if ("0".equals(listType)) {
			joinCount = activityService.count(activity2);// 集体备课参加数
			// 加载发起活动列表
			activityList = activityService.findByPage(activity1);
			myCount = activityList.getTotalCount();
		} else {
			myCount = activityService.count(activity1);// 发起集体备课活动数
			
			activity2.addCustomCulomn(null);
			activityList = activityService.findByPage(activity2);
			joinCount = activityList.getTotalCount();
		}
		
		model.addAttribute("activityList", activityList);
		model.addAttribute("count1", myCount);
		model.addAttribute("count2", joinCount);
		return "/managerecord/activity/activityDetail";
	}

	/**
	 * 管理者 校际教研
	 * @param listType
	 * @param model
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("/school")
	public String indexSchoolTeach(@RequestParam(value = "listType", required = false)String listType, Page page, 
			@RequestParam(required=false)Integer term, Model model) {
		UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
		Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);// 学年

		if (term == null)
			term = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);// 学期

		SchoolActivity activity1 = new SchoolActivity();
		activity1.setTerm(term);
		activity1.setOrgId(userSpace.getOrgId());
		activity1.setStatus(1);
		activity1.setSchoolYear(schoolYear);
		activity1.setOrganizeUserId(userSpace.getUserId());// 用户Id
		activity1.setPhaseId(userSpace.getPhaseId());
		
		SchoolActivity activity2 = new SchoolActivity();
		activity2.addAlias("a");
		activity2.addCustomCulomn("a.id");
		activity2.setTerm(term);
		activity2.setPhaseId(userSpace.getPhaseId());
		activity2.setOrgId(userSpace.getOrgId());
		activity2.setSchoolYear(schoolYear);
		activity2.setStatus(1);
		activity2.buildCondition(" and EXISTS( select b.id from Discuss b where b.activityId = a.id and b.typeId=:typeId and b.crtId=:crtId LIMIT 0,1)")
			.put("typeId", ResTypeConstants.SCHOOLTEACH)
			.put("crtId", userSpace.getUserId());
		
		listType = listType == null ? "0" : listType;
		// 列表选型
		model.addAttribute("listType", listType);
		model.addAttribute("term", term);
		
		PageList<SchoolActivity> activityList;
		// 页码
		activity1.addPage(page);
		activity2.addPage(page);
				
		activity1.pageSize(10);
		activity2.pageSize(10);
		
		
		int myCount = 0;
		int joinCount = 0;
		if ("0".equals(listType)) {
			joinCount = schoolActivityService.count(activity2);// 集体备课参加数
			// 加载发起活动列表
			activityList = schoolActivityService.findByPage(activity1);
			myCount = activityList.getTotalCount();
		} else {
			myCount = schoolActivityService.count(activity1);// 发起集体备课活动数

			activity2.addCustomCulomn(null);
			activityList = schoolActivityService.findByPage(activity2);
			joinCount = activityList.getTotalCount();
		}

		boolean isTuiChu = false;
		if (activityList.getDatalist() != null && activityList.getDatalist().size() > 0) {
			for (SchoolActivity saTemp : activityList.getDatalist()) {
				saTemp.setIsTuiChu(isTuiChu);
				SchoolTeachCircleOrg stco = new SchoolTeachCircleOrg();
				stco.setStcId(saTemp.getSchoolTeachCircleId());
				stco.addOrder("sort asc");
				saTemp.setStcoList(schoolTeachCircleOrgDao.listAll(stco));
			}
		}
		
		model.addAttribute("activityList", activityList);
		model.addAttribute("count1", myCount);
		model.addAttribute("count2", joinCount);
		return "/managerecord/schoolDetail";
	}

}
