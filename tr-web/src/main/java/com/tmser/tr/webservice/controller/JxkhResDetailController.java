/**
 * Tmser.com Inc.
 * Copyright (c) 2016-2018 All Rights Reserved.
 */
package com.tmser.tr.webservice.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmser.tr.activity.bo.Activity;
import com.tmser.tr.activity.bo.ActivityTracks;
import com.tmser.tr.activity.bo.SchoolActivity;
import com.tmser.tr.activity.bo.SchoolActivityTracks;
import com.tmser.tr.activity.service.ActivityService;
import com.tmser.tr.activity.service.ActivityTracksService;
import com.tmser.tr.activity.service.SchoolActivityService;
import com.tmser.tr.activity.service.SchoolActivityTracksService;
import com.tmser.tr.activity.service.SchoolTeachCircleOrgService;
import com.tmser.tr.classapi.bo.ClassInfo;
import com.tmser.tr.classapi.service.ClassOperateService;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.lecturerecords.bo.LectureRecords;
import com.tmser.tr.lecturerecords.service.LectureRecordsService;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.service.LessonInfoService;
import com.tmser.tr.manage.resources.bo.Attach;
import com.tmser.tr.manage.resources.service.AttachService;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.UserService;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 * 绩效考核系统资源查看控制类
 * </pre>
 *
 * @author zpp
 * @version $Id: JxkhResDetailController.java, v 1.0 2016年3月28日 上午10:27:31 zpp
 *          Exp $
 */
@Controller
@RequestMapping("/jy/jxck")
public class JxkhResDetailController extends AbstractController {
	@Resource
	private ActivityService activityService;
	@Autowired
	private ActivityTracksService activityTracksService;
	@Resource
	private AttachService activityAttachService;
	@Resource
	private SchoolActivityService schoolActivityService;
	@Resource
	private SchoolActivityTracksService schoolActivityTracksService;
	@Resource
	private SchoolTeachCircleOrgService schoolTeachCircleOrgService;
	@Resource
	private LectureRecordsService lectureRecordsService;
	@Resource
	private ClassOperateService classOperateService;
	@Resource
	private UserService userService;
	@Autowired
	private UserSpaceService userSpaceService;
	@Autowired
	private LessonInfoService lessonInfoService;

	/**
	 * 集体备课查看控制类（绩效考核调用）
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/viewActivity")
	public String viewActivity(Integer id, Model m) {
		Activity activity = activityService.findOne(id);
		if (Activity.TBJA.intValue() == activity.getTypeId().intValue()) {
			// 获取主备人的教案
			List<ActivityTracks> zhubeiList = activityTracksService.getActivityTracks_zhubei(id);
			m.addAttribute("zhubeiList", zhubeiList);
			m.addAttribute("activity", activity);
			// 整理教案集合
			List<ActivityTracks> zhengliList = activityTracksService.getActivityTracks_zhengli(id);
			m.addAttribute("zhengliList", zhengliList);
			// 有权限的参与人列表查询
			List<UserSpace> usList = activityService.findUserBySubjectAndGrade(activity);
			m.addAttribute("usList", usList);
			return "/jx_view/activity/chaKanJiBei";
		} else if (Activity.ZTYT.intValue() == activity.getTypeId().intValue() || Activity.SPJY.equals(activity.getTypeId())) {
			Attach temp = new Attach();
			temp.setActivityId(id);
			List<Attach> attachList = activityAttachService.findAll(temp);
			m.addAttribute("activity", activity);
			m.addAttribute("attachList", attachList);
			// 有权限的参与人列表查询
			List<UserSpace> usList = activityService.findUserBySubjectAndGrade(activity);
			m.addAttribute("usList", usList);
			return "/jx_view/activity/chaKanZhuYan";
		} else {
			return "";
		}

	}

	/**
	 * 校级教研查看控制类（绩效考核调用）
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/viewSchActivity")
	public String viewSchActivity(Integer id, Model m) {
		SchoolActivity schoolActivity = schoolActivityService.findOne(id);
		UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
		if (SchoolActivity.TBJA.equals(schoolActivity.getTypeId())) {
			if (schoolActivity.getInfoId() != null) {
				LessonInfo lessonInfo = lessonInfoService.findOne(schoolActivity.getInfoId());
				m.addAttribute("lessonName", lessonInfo.getLessonName());
				m.addAttribute("lessonInfo", lessonInfo);
			}
			// 获取主备人的教案
			List<SchoolActivityTracks> zhubeiList = schoolActivityTracksService.getActivityTracks_zhubei(id);
			m.addAttribute("zhubeiList", zhubeiList);
			// 获取所有参与的学校的名称
			String joinOrgNames = schoolTeachCircleOrgService
					.getJoinOrgNamesByCircleId(schoolActivity.getSchoolTeachCircleId());
			if (StringUtils.isNotEmpty(joinOrgNames)) {
				String[] joinOrgs = joinOrgNames.split("、");
				m.addAttribute("joinOrgNames", joinOrgs);
				m.addAttribute("joinOrgLength", joinOrgs.length);
			} else {
				m.addAttribute("joinOrgLength", 0);
			}
			m.addAttribute("activity", schoolActivity);
			// 整理教案集合
			List<SchoolActivityTracks> zhengliList = schoolActivityTracksService.getActivityTracks_zhengli(id);
			m.addAttribute("zhengliList", zhengliList);
			m.addAttribute("type", ResTypeConstants.SCHOOLTEACH);
			m.addAttribute("user1", userService.findOne(schoolActivity.getMainUserId()));
			m.addAttribute("user2", userService.findOne(schoolActivity.getOrganizeUserId()));
			m.addAttribute("us", userSpaceService.findOne(schoolActivity.getSpaceId()));
			m.addAttribute("operateType", 0);
			return "/jx_view/schactivity/chaKanJiBei";
		} else if (SchoolActivity.ZTYT.equals(schoolActivity.getTypeId()) || SchoolActivity.SPYT.equals(schoolActivity.getTypeId())) {
			if (schoolActivity.getInfoId() != null) {
				LessonInfo lessonInfo = lessonInfoService.findOne(schoolActivity.getInfoId());
				m.addAttribute("lessonName", lessonInfo.getLessonName());
			}
			Attach temp = new Attach();
			temp.setActivityId(id);
			List<Attach> attachList = activityAttachService.findAll(temp);
			// 有权限的参与人列表查询
			List<UserSpace> usList = schoolActivityService.findUserBySubjectAndGrade(schoolActivity);
			m.addAttribute("usList", usList);
			m.addAttribute("activity", schoolActivity);
			m.addAttribute("attachList", attachList);
			m.addAttribute("userSpace", userSpace);
			// 获取所有参与的学校的名称
			String joinOrgNames = schoolTeachCircleOrgService.getJoinOrgNamesByCircleId(schoolActivity.getSchoolTeachCircleId());
			if (StringUtils.isNotEmpty(joinOrgNames)) {
				String[] joinOrgs = joinOrgNames.split("、");
				m.addAttribute("joinOrgNames", joinOrgs);
				m.addAttribute("joinOrgLength", joinOrgs.length);
			} else {
				m.addAttribute("joinOrgLength", 0);
			}
			m.addAttribute("user2", userService.findOne(schoolActivity.getOrganizeUserId()));
			m.addAttribute("us", userSpaceService.findOne(schoolActivity.getSpaceId()));
			m.addAttribute("operateType", 0);
			m.addAttribute("activityType", ResTypeConstants.SCHOOLTEACH);
			return "/jx_view/schactivity/chaKanJiBei";
		} else if (SchoolActivity.ZBKT.equals(schoolActivity.getTypeId())) {
			SchoolActivity sa = schoolActivityService.findOne(id);
			// 有权限的参与人列表查询
			List<UserSpace> usList = schoolActivityService.findUserBySubjectAndGrade(sa);
			m.addAttribute("usList", usList);
			m.addAttribute("activity", sa);
			m.addAttribute("activityType", ResTypeConstants.SCHOOLTEACH);
			m.addAttribute("operateType", 0);
			ClassInfo classInfo = classOperateService.getClassInfo(sa.getClassId());
			m.addAttribute("recordUrl", classInfo.getRecordUrl());
			m.addAttribute("user2", userService.findOne(sa.getOrganizeUserId()));
			m.addAttribute("us", userSpaceService.findOne(sa.getSpaceId()));
			// 获取所有参与的学校的名称
			String joinOrgNames = schoolTeachCircleOrgService.getJoinOrgNamesByCircleId(sa.getSchoolTeachCircleId());
			if (StringUtils.isNotEmpty(joinOrgNames)) {
				String[] joinOrgs = joinOrgNames.split("、");
				m.addAttribute("joinOrgNames", joinOrgs);
				m.addAttribute("joinOrgLength", joinOrgs.length);
			} else {
				m.addAttribute("joinOrgLength", 0);
			}
			if (StringUtils.isEmpty(sa.getClassId())) {
				return "/jx_view/schactivity/chaKanJiBei";
			}
			// 获取参考附件集合
			Attach attach = new Attach();
			attach.setActivityId(id);
			attach.setActivityType(Attach.XJJY);
			List<Attach> attachList = activityAttachService.findAll(attach);
			m.addAttribute("attachList", attachList);
			return "/jx_view/schactivity/chaKanJiBei";
		}else{
			return "";
		}

	}

	/**
	 * 查看单个听课记录
	 * 
	 * @param info
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "/viewTopic")
	public String seeTopic(Model m, Integer id) {
		LectureRecords lr = lectureRecordsService.findOne(id);
		m.addAttribute("lr", lr);// 按照主键查询单个
		return "/jx_view/lecture/lecturerecords";
	}

}
