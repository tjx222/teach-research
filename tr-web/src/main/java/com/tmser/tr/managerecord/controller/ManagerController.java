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
import com.tmser.tr.activity.service.ActivityService;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.managerecord.service.ManagerService;
import com.tmser.tr.managerecord.service.ManagerVO;
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
  private ActivityService activityService;

  /**
   * 查阅记录
   * 
   * @param id
   * @param m
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/checkdata", method = RequestMethod.GET)
  public List<ManagerVO> checkdata(@RequestParam(value = "grade", required = false) Integer grade,
      @RequestParam(value = "term", required = false) Integer term,
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
  public String indexData(@RequestParam(value = "grade", required = false) Integer grade,
      @RequestParam(value = "term", required = false) Integer term,
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
  public String list(@RequestParam(value = "grade", required = false) Integer grade,
      @RequestParam(value = "term", required = false) Integer term,
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
   * 管理者 教研管理记录进入集体活动(管理者 集体备课)详细页面
   * 
   * @return
   */
  @RequestMapping("/activity")
  public String activity(@RequestParam(value = "listType", required = false) String listType, Page page,
      @RequestParam(required = false) Integer term, Model model) {
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

    activity2
        .buildCondition(
            " and EXISTS( select b.id from Discuss b where b.activityId = a.id and b.typeId=:typeId and b.crtId=:crtId LIMIT 0,1)")
        .put("typeId", ResTypeConstants.ACTIVITY).put("crtId", userSpace.getUserId());

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

}
