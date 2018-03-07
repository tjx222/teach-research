/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.controller;

import java.util.List;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmser.tr.activity.bo.Activity;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.history.service.ActivityHistory;
import com.tmser.tr.history.vo.SearchVo;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 * 集体备课历史查询controller
 * </pre>
 *
 * @author wangdawei
 * @version $Id: LessonPlanHistoryController.java, v 1.0 2016年6月12日 上午10:15:15 wangdawei Exp $
 */
@Controller
@RequestMapping("/jy/history")
public class ActivityHistoryController {

	@Autowired
	private UserSpaceService userSpaceService;
    @Autowired
	private ActivityHistory activityHistoryService;
	
	/**
	 * 我的集体备课历史查询页
	 * @return
	 */
	@RequestMapping(value={"/{schoolYear}/jtbk/index"})
	public String toHistoryJiaoan(SearchVo searchVo,Model m){
		User user = CurrentUserContext.getCurrentUser();
		//获取当年的空间集合
		List<UserSpace> allUserSpaceList = userSpaceService.listUserSpaceBySchoolYear(user.getId(), searchVo.getSchoolYear());
		if(CollectionUtils.isEmpty(allUserSpaceList)){
			return "/history/activityList";
		}
		if(searchVo.getSpaceId()==null){
			searchVo.setSpaceId(allUserSpaceList.get(0).getId());
		}
		UserSpace userSpace = userSpaceService.findOne(searchVo.getSpaceId());
		if(userSpace.getSysRoleId().intValue()!=SysRole.BKZZ.getId() && userSpace.getSysRoleId().intValue()!=SysRole.XKZZ.getId()){
			searchVo.setFlags("1");
		}
		if(StringUtils.isBlank(searchVo.getFlags()) || "0".equals(searchVo.getFlags()) ){
			//获取当年发起的集体备课集合
			PageList<Activity> activityList_faqi = activityHistoryService.getHistoryActivityListBySpaceId_faqi(searchVo);
			m.addAttribute("activityList_faqi", activityList_faqi);
		}else if("1".equals(searchVo.getFlags())){
			//获取当年已参与的集体备课集合
			PageList<Activity> activityList_canyu = activityHistoryService.getHistoryActivityListBySpaceId_canyu(searchVo);
			m.addAttribute("activityList_canyu", activityList_canyu);
		}
		Integer activityCount_faqi = activityHistoryService.getHistoryActivityCountBySpaceId_faqi(searchVo);
		Integer activityCount_canyu = activityHistoryService.getHistoryActivityCountBySpaceId_canyu(searchVo);
		m.addAttribute("userSpaceList", allUserSpaceList);
		m.addAttribute("activityCount_faqi", activityCount_faqi);
		m.addAttribute("activityCount_canyu", activityCount_canyu);
		m.addAttribute("searchVo", searchVo);
		m.addAttribute("userSpace",userSpace );
		return "/history/activityList";
	}

	
}
