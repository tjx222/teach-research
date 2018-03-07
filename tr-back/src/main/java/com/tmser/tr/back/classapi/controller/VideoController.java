/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.classapi.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.back.classapi.service.ClassinfoService;
import com.tmser.tr.back.classapi.service.ClassvisitService;
import com.tmser.tr.classapi.bo.ClassInfo;
import com.tmser.tr.classapi.bo.ClassVisit;
import com.tmser.tr.common.bo.QueryObject.JOINTYPE;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.service.UserService;
import com.tmser.tr.utils.DateUtils;

/**
 * <pre>
 * 直播课堂
 * </pre>
 */
@Controller
@RequestMapping("/jy/back/zbkt")
public class VideoController extends AbstractController {
	
	@Autowired
	private ClassinfoService classinfoService;
	@Autowired
	private ClassvisitService classvisitService;
	@Autowired
	private UserService userService;
	/**
	 * 课堂统计
	 * @return
	 */
	@RequestMapping("kttj")
	public String yhjrsj(ClassInfo model,Model m,@RequestParam(value="orgName",required=false)String orgName
			,@RequestParam(value="searchName",required=false)String searchName){
		String searchValue = null;
		Map<String,Object> paramMap = new HashMap<String,Object>();
		if(!StringUtils.isBlank(orgName)){
			searchValue = orgName;
			model.addJoin(JOINTYPE.INNER, "User u").on("u.id=c.userId and u.orgName like :orgName");
			paramMap.put("orgName", "%" + orgName + "%");
		}
		model.addAlias("c");
		model.addCustomCulomn("c.*");
		if(!StringUtils.isBlank(model.getUserName())){
			searchValue = model.getUserName();
			m.addAttribute("userName", model.getUserName());
			model.setUserName(SqlMapping.LIKE_PRFIX + model.getUserName() + SqlMapping.LIKE_PRFIX);
		}
		model.pageSize(20);
		model.addCustomCondition("", paramMap);
		if (StringUtils.isBlank(model.order())) {
			model.addOrder("c.startTime desc");
		}
		PageList<ClassInfo> findByPage = classinfoService.findByPage(model);
		for (ClassInfo info : findByPage.getDatalist()) {
			User findOne = userService.findOne(info.getUserId());
			if(findOne != null){
				info.setFlago(findOne.getOrgName());
			}
		}
		m.addAttribute("classInfoList", findByPage);
		m.addAttribute("model", model);
		m.addAttribute("searchValue", searchValue==null?model.getId():searchValue);
		m.addAttribute("searchName", searchName);
		return viewName("kttj");
	}
	/**
	 * 课堂统计详情
	 * @return
	 */
	@RequestMapping("kttjDetail")
	public String kttjDetail(ClassVisit model,Model m){
		model.pageSize(20);
		PageList<ClassVisit> findByPage = classvisitService.findByPage(model);
		m.addAttribute("classDetail", findByPage);
		m.addAttribute("model", model);
		return viewName("kttjDetail");
	}
	/**
	 * 课堂并发统计跳转
	 * @return
	 */
	@RequestMapping("ktbftj")
	public String ktbftj(ClassInfo model,Model m){
		model.setStartTime(DateUtils.nextDay(-20));
		model.setEndtime(new Date());
		m.addAttribute("model", model);
		return viewName("ktbftj");
	}
	/**
	 * 用户进入时间统计
	 * 课堂并发统计数据
	 * @param model
	 * @param m
	 * @return
	 * Map:optionsX,result.linesX,result.linesY
	 */
	@RequestMapping("ktbftjResult")
	@ResponseBody
	public Map<String,Object> ktbftjResult(ClassInfo model,Model m){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<String> optionsX = new ArrayList<String>();//x轴数据
		List<Integer> linesX = new ArrayList<Integer>();//x占位符
		List<Integer> linesY = new ArrayList<Integer>();//y轴数据
		if(model.getFlago().equals("1")){//用户进入课堂时间按天统计
			List<ClassInfo> classInfoList  = classinfoService.ktbftjResultByDay(model);
			for(int i = 0;i<classInfoList.size();i++){
				optionsX.add(DateUtils.formatDate(classInfoList.get(i).getStartTime(), "yyyy/MM/dd"));
				linesX.add(i);
				linesY.add(classInfoList.get(i).getCount());
			}
		}else{//用户进入课堂时间实时统计
			List<ClassInfo> classInfoList  = classinfoService.ktbftjResult(model);
			for(int i = 0;i<classInfoList.size();i++){
				optionsX.add(DateUtils.formatDate(classInfoList.get(i).getStartTime(), "HH:mm:ss"));
				linesX.add(i);
				linesY.add(classInfoList.get(i).getCount());
			}
		}
		resultMap.put("optionsX", optionsX);
		resultMap.put("linesX", linesX);
		resultMap.put("linesY", linesY);
		return resultMap;
	}
	
	/**
	 * 用户进入时间统计 跳转
	 * @return
	 */
	@RequestMapping("yhjrsj")
	public String yhjrsj(ClassVisit model,Model m){
		model.setStartTime(DateUtils.nextDay(-20));
		model.setEndTime(new Date());
		m.addAttribute("model", model);
		return viewName("yhjrsj");
	}
	/**
	 * 用户进入时间统计统计数据
	 * @param model
	 * @param m
	 * @return
	 * Map:optionsX,result.linesX,result.linesY
	 */
	@RequestMapping("yhjrsjResult")
	@ResponseBody
	public Map<String,Object> yhjrsjResult(ClassVisit model,Model m){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<String> optionsX = new ArrayList<String>();//x轴数据
		List<Integer> linesX = new ArrayList<Integer>();//x占位符
		List<Integer> linesY = new ArrayList<Integer>();//y轴数据
		if(model.getFlago().equals("1")){//用户进入课堂时间按天统计
			List<ClassVisit> classInfoList  = classvisitService.yhjrsjResultByDay(model);
			for(int i = 0;i<classInfoList.size();i++){
				optionsX.add(DateUtils.formatDate(classInfoList.get(i).getStartTime(), "yyyy/MM/dd"));
				linesX.add(i);
				linesY.add(classInfoList.get(i).getCount());
			}
		}else{//用户进入课堂时间实时统计
			List<ClassVisit> classInfoList  = classvisitService.yhjrsjResult(model);
			for(int i = 0;i<classInfoList.size();i++){
				optionsX.add(DateUtils.formatDate(classInfoList.get(i).getStartTime(), "HH:mm"));
				linesX.add(i);
				linesY.add(classInfoList.get(i).getCount());
			}
		}
		resultMap.put("optionsX", optionsX);
		resultMap.put("linesX", linesX);
		resultMap.put("linesY", linesY);
		return resultMap;
	}
}
