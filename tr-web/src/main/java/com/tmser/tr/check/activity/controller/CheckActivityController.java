/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.check.activity.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tmser.tr.activity.bo.Activity;
import com.tmser.tr.activity.bo.ActivityTracks;
import com.tmser.tr.activity.service.ActivityService;
import com.tmser.tr.activity.service.ActivityTracksService;
import com.tmser.tr.check.activity.service.CheckActivityService;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.resources.bo.Attach;
import com.tmser.tr.manage.resources.service.AttachService;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.SchoolYearService;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.writelessonplan.service.LessonPlanService;

/**
 * <pre>
 * 查阅活动基础类
 * </pre>
 *
 * @author
 * @version $Id: CheckActivityController.java, v 1.0 2015-4-16 10:09:35
 */
@Controller
@RequestMapping("/jy/check/activity")
public class CheckActivityController extends AbstractController {

	@Autowired
	private CheckActivityService checkActivityService;
	@Resource
	private ActivityService activityService;
	@Resource
	private LessonPlanService lessonPlanService;
	@Resource
	private AttachService activityAttachService;
	@Resource
	private ActivityTracksService activityTracksService;
	@Resource
	private SchoolYearService schoolYearService;

	/**
	 * 查阅集体备课（活动）入口页
	 * 
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(@RequestParam(value = "grade", required = false) Integer grade,
			@RequestParam(value = "subject", required = false) Integer subject,
			@RequestParam(value = "term", required = false) Integer term, Model m) {
		m.addAttribute("term", term);
		m.addAttribute("currentterm", schoolYearService.getCurrentTerm());
		m.addAttribute("subject", subject);
		m.addAttribute("grade", grade);
		return "check/activity/index";
	}

	/**
	 * 已提交可查阅的活动列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/activitylist")
	public String activitylist(Integer grade, Integer subject, Integer term, Model m, Page page) {
		m.addAttribute("grade", grade);
		m.addAttribute("subject", subject);
		if (term == null) {
			term = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);// 学期
		}
		m.addAttribute("term", term);
		Map<String, Object> dataMap = checkActivityService.findCheckActivity(grade, subject, term, page);
		m.addAttribute("countAll", dataMap.get("countAll"));
		m.addAttribute("countAudit", dataMap.get("countAudit"));
		m.addAttribute("listPage", dataMap.get("listPage"));
		m.addAttribute("checkedResIdMap", dataMap.get("checkedResIdMap"));
		return "check/activity/activity_list";
	}

	/**
	 * 查阅活动列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/chayueActivity")
	public String chayueActivity(Integer activityId, Integer typeId, Integer grade, Integer subject, Integer term,
			Model m) {
		if (typeId != null && Activity.TBJA.equals(typeId.intValue())) {// 同备教案
		  StringBuilder url = new StringBuilder("/jy/check/activity/chayueActivity1?activityId=" + activityId+"&typeId="+typeId);
		  if (grade != null) {
		    url.append("&grade="+grade);
		  }
		  if (subject != null) {
        url.append("&subject="+subject);
      }
		  if (term != null) {
        url.append("&term="+term);
      }
		  m.addAttribute("url", url.toString());
	    return "/resview/pageofficeOpenWindow";
		} else if (typeId != null && (Activity.ZTYT.intValue()==typeId.intValue()|| Activity.SPJY.intValue()==typeId.intValue())) {// 主题研讨、视频教研
		  Activity activity = activityService.findOne(activityId);
	    // 判断是否有权限
	    Assert.isTrue(ifHavePower(activity), "没有权限");
	    m.addAttribute("activity", activity);
	    // 有权限的参与人列表查询
	    List<UserSpace> usList = activityService.findUserBySubjectAndGrade(activity);
	    m.addAttribute("usList", usList);
	    m.addAttribute("type", ResTypeConstants.ACTIVITY);
	    m.addAttribute("grade", grade);
	    m.addAttribute("subject", subject);
	    m.addAttribute("term", term);
	    
		  Attach temp = new Attach();
			temp.setActivityId(activityId);
			List<Attach> attachList = activityAttachService.findAll(temp);
			m.addAttribute("activity", activity);
			m.addAttribute("attachList", attachList);
			if(Activity.ZTYT.equals(typeId.intValue())){
				return "/check/activity/chayueZhuYan";
			}
			return "/check/activity/chayueShipin";
		}else {
			return "";
		}

	}
	
	/**
   * 查阅活动列表
   * 
   * @return
   */
  @RequestMapping(value = "/chayueActivity1")
  public String chayueActivity1(Integer activityId, Integer typeId, Integer grade, Integer subject, Integer term,
      Model m) {
    Activity activity = activityService.findOne(activityId);
    // 判断是否有权限
    Assert.isTrue(ifHavePower(activity), "没有权限");
    m.addAttribute("activity", activity);
    // 有权限的参与人列表查询
    List<UserSpace> usList = activityService.findUserBySubjectAndGrade(activity);
    m.addAttribute("usList", usList);
    m.addAttribute("type", ResTypeConstants.ACTIVITY);
    m.addAttribute("grade", grade);
    m.addAttribute("subject", subject);
    m.addAttribute("term", term);
    // 获取主备人的教案
    List<ActivityTracks> zhubeiList = activityTracksService.getActivityTracks_zhubei(activityId);
    m.addAttribute("zhubeiList", zhubeiList);
    // 整理教案集合
    List<ActivityTracks> zhengliList = activityTracksService.getActivityTracks_zhengli(activity.getId());
    m.addAttribute("zhengliList", zhengliList);
    return "/check/activity/chayueJiBei";

  }

	/**
	 * 判断是否有权限
	 * 
	 * @param activity
	 * @return
	 */
	private boolean ifHavePower(Activity activity) {
		boolean flag = false;
		UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
		Integer roleId = userSpace.getSysRoleId();// 角色id
		if (roleId.intValue() == SysRole.XZ.getId().intValue() || roleId.intValue() == SysRole.FXZ.getId().intValue()) {// 校长
			flag = true;
		} else if (roleId.intValue() == SysRole.ZR.getId().intValue()) {// 主任
			flag = true;
		} else if (roleId.intValue() == SysRole.XKZZ.getId().intValue()) {// 学科组长
			if (activity.getOrgId().intValue() == userSpace.getOrgId().intValue()
					&& activity.getSubjectIds().contains(String.valueOf(userSpace.getSubjectId()))) {
				flag = true;
			}
		} else if (roleId.intValue() == SysRole.NJZZ.getId().intValue()) {// 年级组长
			if (activity.getOrgId().intValue() == userSpace.getOrgId().intValue()
					&& activity.getGradeIds().contains(String.valueOf(userSpace.getGradeId()))) {
				flag = true;
			}
		} else if (roleId.intValue() == SysRole.BKZZ.getId().intValue()
				|| roleId.intValue() == SysRole.TEACHER.getId().intValue()) {// 备课组长或老师
			if (activity.getOrgId().intValue() == userSpace.getOrgId().intValue()
					&& activity.getSubjectIds().contains(String.valueOf(userSpace.getSubjectId()))
					&& activity.getGradeIds().contains(String.valueOf(userSpace.getGradeId()))) {
				flag = true;
			}
		}
		return flag;
	}
}
