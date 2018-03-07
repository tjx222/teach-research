/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmser.tr.common.page.PageList;
import com.tmser.tr.history.service.LessonPlanHistory;
import com.tmser.tr.history.vo.SearchVo;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.manage.meta.service.BookService;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.CurrentUserContext;

/**
 * <pre>
 * 教学资源历史查询controller
 * </pre>
 *
 * @author wangdawei
 * @version $Id: LessonPlanHistoryController.java, v 1.0 2016年6月12日 上午10:15:15 wangdawei Exp $
 */
@Controller
@RequestMapping("/jy/history")
public class LessonPlanHistoryController {

	@Autowired
	private UserSpaceService userSpaceService;
	@Autowired
    private BookService bookService;
    @Autowired
    private LessonPlanHistory lessonPlanHistory;
   
	
	/**
	 * 我的教案历史查询页
	 * @return
	 */
	@RequestMapping(value={"/{schoolYear}/zxja/index"})
	public String toHistoryJiaoan(SearchVo searchVo,Model m){
		User user = CurrentUserContext.getCurrentUser();
		//获取当年的空间集合
		List<UserSpace> allUserSpaceList = userSpaceService.listUserSpaceBySchoolYear(user.getId(), searchVo.getSchoolYear());
		//过滤空间集合
		List<UserSpace> userSpaceList = filtrateUserSpace(allUserSpaceList,SysRole.TEACHER.getId().toString());
		if(CollectionUtils.isEmpty(userSpaceList)){
			return "/history/jiaoanList";
		}
		if(searchVo.getSpaceId()==null){
			searchVo.setSpaceId(userSpaceList.get(0).getId());
		}
		//获取当年的教案集合
		PageList<LessonPlan> jiaoanList = lessonPlanHistory.getHistoryJiaoanList(searchVo);
		m.addAttribute("formatName",bookService.findOne(userSpaceList.get(0).getBookId()).getFormatName());
		m.addAttribute("userSpaceList", userSpaceList);
		m.addAttribute("jiaoanList", jiaoanList);
		m.addAttribute("searchVo", searchVo);
		return "/history/jiaoanList";
	}

	/**
	 * 我的课件历史查询页
	 * @return
	 */
	@RequestMapping(value={"/{schoolYear}/sckj/index"})
	public String toHistoryKejian(SearchVo searchVo,Model m){
		User user = CurrentUserContext.getCurrentUser();
		//获取当年的空间集合
		List<UserSpace> allUserSpaceList = userSpaceService.listUserSpaceBySchoolYear(user.getId(), searchVo.getSchoolYear());
		//过滤空间集合
		List<UserSpace> userSpaceList = filtrateUserSpace(allUserSpaceList,SysRole.TEACHER.getId().toString());
		if(CollectionUtils.isEmpty(userSpaceList)){
			return "/history/kejianList";
		}
		if(searchVo.getSpaceId()==null){
			searchVo.setSpaceId(userSpaceList.get(0).getId());
		}
		//获取当年的教案集合
		PageList<LessonPlan> kejianList = lessonPlanHistory.getHistoryKejianList(searchVo);
		m.addAttribute("formatName",bookService.findOne(userSpaceList.get(0).getBookId()).getFormatName());
		m.addAttribute("userSpaceList", userSpaceList);
		m.addAttribute("kejianList", kejianList);
		m.addAttribute("searchVo", searchVo);
		return "/history/kejianList";
	}
	
	/**
	 * 教学反思历史查询页
	 * @return
	 */
	@RequestMapping(value={"/{schoolYear}/jxfs/index"})
	public String toHistoryFansi(SearchVo searchVo,Model m){
		User user = CurrentUserContext.getCurrentUser();
		//获取当年的空间集合
		List<UserSpace> allUserSpaceList = userSpaceService.listUserSpaceBySchoolYear(user.getId(), searchVo.getSchoolYear());
		//过滤空间集合
		List<UserSpace> userSpaceList = filtrateUserSpace(allUserSpaceList,SysRole.TEACHER.getId().toString());
		if(CollectionUtils.isEmpty(userSpaceList)){
			return "/history/fansiList";
		}
		if(searchVo.getSpaceId()==null){
			searchVo.setSpaceId(userSpaceList.get(0).getId());
		}
		//获取当年的教案集合
		PageList<LessonPlan> fansiList = lessonPlanHistory.getHistoryFansiList(searchVo);
		m.addAttribute("formatName",bookService.findOne(userSpaceList.get(0).getBookId()).getFormatName());
		m.addAttribute("userSpaceList", userSpaceList);
		m.addAttribute("fansiList", fansiList);
		m.addAttribute("searchVo", searchVo);
		return "/history/fansiList";
	}
	
	/**
	 * 过滤userspace
	 * @param allUserSpaceList
	 * @param integers
	 * @return
	 */
	private List<UserSpace> filtrateUserSpace(List<UserSpace> allUserSpaceList,String roleIdStr) {
		roleIdStr = ","+roleIdStr+",";
		List<UserSpace> usList = new ArrayList<UserSpace>();
		for(UserSpace us : allUserSpaceList){
			if(roleIdStr.contains(","+String.valueOf(us.getSysRoleId())+",")){
				usList.add(us);
			}
		}
		return usList;
	}
	
	/**
	 * 获取教学资源的详细信息
	 * @param planId
	 * @param m
	 */
	@RequestMapping(value={"/getDetailInfo"})
	public void getLessonPlanDetailInfo(Integer planId, Model m){
		Map<String,Object> infoMap = lessonPlanHistory.getLessonPlanDetailInfo(planId);
		m.addAttribute("infoMap", infoMap);
	}
	
	
}
