/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.history.service.PlainSummaryHistoryService;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.plainsummary.bo.PlainSummary;
import com.tmser.tr.plainsummary.service.PlainSummaryService;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * <pre>
 *	计划总结历史资源控制器
 * </pre>
 *
 * @author 3020mt
 * @version $Id: PlainSummaryHistoryController.java, v 1.0 2016年5月19日 下午1:34:57
 *          3020mt Exp $
 */
@Controller
@RequestMapping("/jy/history")
public class PlainSummaryHistoryController extends AbstractController {
	@Resource
	private PlainSummaryHistoryService plainSummaryHistoryService;
	@Autowired
	private PlainSummaryService plainSummaryService;
	@Autowired
	private ResourcesService resourcesService;
	@Autowired
	private UserSpaceService userSpaceService;
	
	@RequestMapping("/{year}/jhzj/index")
	public String list(@PathVariable(value = "year") Integer year,
			PlainSummary modle,Model m) {
		User user = (User) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_USER);
		List<UserSpace> usList = userSpaceService.listUserSpaceBySchoolYear(user.getId(), year);
		PageList<PlainSummary> psList = plainSummaryHistoryService.findList(year,modle);
		m.addAttribute("data", psList);
		m.addAttribute("schoolYear", year);
		m.addAttribute("ps", modle);
		m.addAttribute("usList", usList);
		return viewName("jhzj/index");
	}
	
	@RequestMapping("/{year}/jhzj/showInfo")
	@ResponseBody
	public Result showInfo(Integer id){
		PlainSummary ps = plainSummaryService.findOne(id);
		Resources resources = resourcesService.findOne(ps.getContentFileKey());
		Result re = new Result();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("rs", resources);
		map.put("ps", ps);
		re.setCode(1);
		re.setData(map);
		re.setMsg("ok");
		return re;
	}
}
