/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.check.lecturerecords.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmser.tr.check.bo.CheckInfo;
import com.tmser.tr.check.lecturerecords.service.CheckLectureRecordsService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.lecturerecords.bo.LectureRecords;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.UserService;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * <pre>
 *  查阅听课记录 controller
 * </pre>
 *
 * @author wangdawei
 * @version $Id: CheckLectureRecordsContorller.java, v 1.0 2016年8月17日 下午3:40:14 wangdawei Exp $
 */
@Controller
@RequestMapping("/jy/check/lectureRecords")
public class CheckLectureRecordsContorller extends AbstractController{

	@Autowired
	private CheckLectureRecordsService checkLectureRecordsService;
	@Autowired
	private UserService userService;
	
	/**
	 * 首页
	 * @return
	 */
	@RequestMapping("/toIndex")
	public String checkIndex(){
		if(ifGroupLeader()){
			return "redirect:/jy/check/lectureRecords/toCheckTeacherIndex";
		}else{
			return "/check/lecturerecords/index";
		}
	}

	/**
	 * 查阅教师页
	 * @return
	 */
	@RequestMapping("/toCheckTeacherIndex")
	public String toCheckTeacherIndex(LectureRecords lr,Model m){
		Integer termId = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);
		List<Map<String,String>> gradeList = checkLectureRecordsService.getGradeList();
		List<Map<String,String>> subjectList = checkLectureRecordsService.getSubjectList();
		if(lr.getSubjectId()==null){
			lr.setSubjectId(Integer.valueOf(subjectList.get(0).get("id")));
		}
		if(lr.getGradeId()==null){
			lr.setGradeId(Integer.valueOf(gradeList.get(0).get("id")));
		}
		if(lr.getTerm()==null){
			lr.setTerm(termId);
		}
		List<Map<String,Object>> teacherMapList = checkLectureRecordsService.getTeacherMapList(lr,m);
		m.addAttribute("teacherMapList", teacherMapList);
		m.addAttribute("gradeList", gradeList);
		m.addAttribute("subjectList", subjectList);
		m.addAttribute("lr", lr);
		return "/check/lecturerecords/checkTeacherIndex";
	}
	
	/**
	 * 查阅管理者页
	 * @param sysRoleId
	 * @param lr
	 * @param m
	 * @return
	 */
	@RequestMapping("/toCheckLeaderIndex")
	public String toCheckLeaderIndex(Integer sysRoleId,LectureRecords lr,Model m){
		Integer termId = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);
		if(lr.getTerm()==null){
			lr.setTerm(termId);
		}
		List<Map<String,Object>> roleMapList = getRoleList();
		if(sysRoleId==null){
			sysRoleId=(Integer)roleMapList.get(0).get("roleId");
		}
		if(sysRoleId.intValue()==SysRole.BKZZ.getId().intValue()){ //备课组长
			List<Map<String,String>> gradeList = checkLectureRecordsService.getGradeList();
			m.addAttribute("gradeList", gradeList);
			if(lr.getGradeId()==null){
				lr.setGradeId(Integer.valueOf(gradeList.get(0).get("id")));
			}
		}
		List<Map<String,Object>> leaderMapList = checkLectureRecordsService.getLeaderMapList(sysRoleId,lr,m);
		m.addAttribute("leaderMapList", leaderMapList);
		m.addAttribute("roleMapList", roleMapList);
		m.addAttribute("sysRoleId", sysRoleId);
		m.addAttribute("lr", lr);
		return "/check/lecturerecords/checkLeaderIndex";
	}
	
	/**
	 * 获取可查阅的角色
	 * @return
	 */
	private List<Map<String, Object>> getRoleList() {
		UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		List<Map<String,Object>> roleMapList = new ArrayList<Map<String,Object>>();
		SysRole[] roleArray = {SysRole.ZR,SysRole.XKZZ,SysRole.NJZZ,SysRole.BKZZ};
		for(SysRole role:roleArray){
			if(userSpace.getSysRoleId().intValue()!=role.getId().intValue()){
				Map<String,Object> roleMap = new HashMap<String,Object>();
				roleMap.put("roleId", role.getId());
				roleMap.put("roleName",role.getCname());
				roleMapList.add(roleMap);
			}
		}
		return roleMapList;
	}

	/**
	 * 进入教师的听课记录列表页面
	 * @param lr
	 * @param m
	 * @return
	 */
	@RequestMapping("/toLectureRecordsList")
	public String toLectureRecordsList(Integer sysRoleId,LectureRecords lr,Model m){
		Map<String,Object> dataMap = checkLectureRecordsService.getLectureRecordsDataMap(lr);
		User user = userService.findOne(lr.getLecturepeopleId());
		m.addAttribute("dataMap", dataMap);
		m.addAttribute("lectureRecords", lr);
		m.addAttribute("user", user);
		m.addAttribute("sysRoleId", sysRoleId);
		return "/check/lecturerecords/lectureRecordsList";
	}
	
	/**
	 * 进入听课记录的查阅页面
	 * @param lr
	 * @param m
	 * @return
	 */
	@RequestMapping("/toCheckLectureRecords")
	public String toCheckLectureRecords(Integer sysRoleId,LectureRecords lr,Model m){
		LectureRecords lectureRecords = checkLectureRecordsService.getLectureRecordsByNum(lr,m);
		if(lectureRecords.getType().intValue()==0){ //校内听课记录,则获取本课题的其他人的听课记录
			List<LectureRecords> otherRecords = checkLectureRecordsService.getOtherLectureRecordsByLessonId(lectureRecords);
			m.addAttribute("otherRecords", otherRecords);
		}
		m.addAttribute("lr", lectureRecords);
//		if(lectureRecords.getIsScan().intValue()==1){//被查阅，则获取查阅信息和查阅意见
//			CheckInfo checkInfo = new CheckInfo();
//			checkInfo.setResId(lectureRecords.getId());
//			checkInfo.setResType(ResTypeConstants.LECTURE);
//			checkInfo.setSchoolYear(schoolYear);
//			checkInfo = checkInfoService.findOne(checkInfo);
//			m.addAttribute("checkInfo", checkInfo);
//		}
		m.addAttribute("lectureRecords", lr);
		m.addAttribute("sysRoleId", sysRoleId);
		return "/check/lecturerecords/checkLectureRecords";
	}
	
	/**
	 * 查阅听课记录
	 * @param checkInfo
	 * @param content
	 * @param m
	 */
	@RequestMapping("/checkLectureRecords")
	public void checkLectureRecords(CheckInfo checkInfo,String content,Model m){
		try {
			checkLectureRecordsService.checkLectureRecords(checkInfo,content);
			m.addAttribute("result", 0);
		} catch (Exception e) {
			logger.error("查阅听课记录出错", e);
		}
	}
	
	/**
	 * 判断是否为组长
	 * @return
	 */
	private boolean ifGroupLeader() {
		UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		Integer roleId = userSpace.getSysRoleId();
		if(roleId.intValue()==SysRole.NJZZ.getId().intValue()||roleId.intValue()==SysRole.XKZZ.getId().intValue()||roleId.intValue()==SysRole.BKZZ.getId().intValue()){
			return true;
		}
		return false;
	}
	
	
	
}
