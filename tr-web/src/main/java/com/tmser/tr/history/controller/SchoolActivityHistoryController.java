/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmser.tr.activity.bo.SchoolTeachCircle;
import com.tmser.tr.activity.bo.TeachSchedule;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.history.service.SchoolActivityHistoryService;
import com.tmser.tr.history.vo.SearchVo;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.CurrentUserContext;

/**
 * <pre>
 *
 * </pre>
 *
 * @author zpp
 * @version $Id: SchoolActivityHistoryController.java, v 1.0 2016年5月20日 下午1:37:20 zpp Exp $
 */
@Controller
@RequestMapping("/jy/history")
public class SchoolActivityHistoryController extends AbstractController{

	@Resource
	private UserSpaceService userSpaceService;
	@Resource
	private SchoolActivityHistoryService schoolActivityHistoryService;
	
	/**
	 * 首页
	 * @param m
	 * @return
	 */
	@RequestMapping(value = {"/{year}/xjjy/index","/{year}/qy_xjjy/index"})
	public String index(@PathVariable("year")Integer schoolYear,Model m,SearchVo sv){
		User user = CurrentUserContext.getCurrentUser();
		List<UserSpace> userSpaces = userSpaceService.listUserSpaceBySchoolYear(user.getId(), schoolYear);
		if(CollectionUtils.isEmpty(userSpaces)){
			return viewName("/xjjy/index");
		}
		m.addAttribute("userSpaces",userSpaces);
		sv.setSchoolYear(schoolYear);
		if(sv.getSpaceId()==null){
			sv.setSpaceId(userSpaces.get(0).getId());
		}
		Map<String, Object> result = schoolActivityHistoryService.listSchoolActivity(sv);
		m.addAttribute("result",result);
		return viewName("/xjjy/index");
	}
	
	/**
	 * 历年资源-查看教研进度表
	 * @param m
	 * @return
	 */
	@RequestMapping(value = {"/{year}/xjjy/teachschedule","/{year}/qy_xjjy/teachschedule"})
	public String teachschedule(@PathVariable("year")Integer schoolYear,Model m,SearchVo sv){
		sv.setSchoolYear(schoolYear);
		PageList<TeachSchedule> tlist=schoolActivityHistoryService.findTeachlList(sv);
		m.addAttribute("tlist",tlist);
		m.addAttribute("sv",sv);
		return viewName("/xjjy/teachschedule");
	}
	
	/**
	 * 历年资源-查看校级教研圈
	 * @param m
	 * @return
	 */
	@RequestMapping(value = {"/{year}/xjjy/teachcircle","/{year}/qy_xjjy/teachcircle"})
	public String teachcircle(@PathVariable("year")Integer schoolYear,Model m,SearchVo sv){
		sv.setSchoolYear(schoolYear);
		List<SchoolTeachCircle> stcList=schoolActivityHistoryService.findTeachcircleList(sv);
		m.addAttribute("stcList",stcList);
		m.addAttribute("sv",sv);
		return viewName("/xjjy/teachcircle");
	}
}
