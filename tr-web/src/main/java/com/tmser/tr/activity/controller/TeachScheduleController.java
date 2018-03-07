/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.controller;

 
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmser.tr.activity.bo.SchoolTeachCircle;
import com.tmser.tr.activity.bo.SchoolTeachCircleOrg;
import com.tmser.tr.activity.bo.TeachSchedule;
import com.tmser.tr.activity.service.SchoolTeachCircleOrgService;
import com.tmser.tr.activity.service.SchoolTeachCircleService;
import com.tmser.tr.activity.service.TeachScheduleService;
import com.tmser.tr.common.annotation.UseToken;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * 教研进度表控制器接口
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: TeachSchedule.java, v 1.0 2015-05-18 Generate Tools Exp $
 */
@Controller
@RequestMapping("/jy/teachschedule")
public class TeachScheduleController extends AbstractController {

	@Resource
	private TeachScheduleService teachScheduleService;

	@Resource
	private SchoolTeachCircleService schoolTeachCircleService;
	
	@Resource
	private SchoolTeachCircleOrgService schoolTeachCircleOrgService;

	@Resource
	private ResourcesService resourcesService;

	@RequestMapping("/index")
	@UseToken
	public String index(Model m) {
		UserSpace us = (UserSpace) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		if (us != null) {
			TeachSchedule teachSchedule = new TeachSchedule();
			teachSchedule.setCrtId(us.getUserId());
			teachSchedule.addOrder("lastupDttm desc");
			List<TeachSchedule> tSchedules = teachScheduleService
					.findAll(teachSchedule);
			m.addAttribute("tSchedules", tSchedules);
			List<SchoolTeachCircleOrg> sList = teachScheduleService.getindex();
			m.addAttribute("sList", sList);
			SchoolTeachCircleOrg stco=new SchoolTeachCircleOrg();
			stco.addOrder("stcId desc,sort asc");
			List<SchoolTeachCircle> stlist = new ArrayList<SchoolTeachCircle>();
			for (SchoolTeachCircleOrg s : sList) {
				SchoolTeachCircle findOne = schoolTeachCircleService.findOne(s.getStcId());
				stco.setStcId(s.getStcId());
				List<SchoolTeachCircleOrg> findAll = schoolTeachCircleOrgService.findAll(stco);
				findOne.setStcoList(findAll);
				stlist.add(findOne);
			}
			m.addAttribute("stlist", stlist);
		}
		return viewName("teachscheduleindex");

	}
	
	@RequestMapping("/read")
	public String readTeachSchedule(Model m,TeachSchedule teachSchedule,Page page){
		UserSpace us = (UserSpace) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		if (us!=null) {
			List<Integer> stcolist=schoolTeachCircleOrgService.getCircleByOrgId(us.getOrgId());
			if (stcolist.size()!=0) {
				Map<String,Object> paramMap = new HashMap<String,Object>();
				paramMap.put("stcidlist", stcolist);
				teachSchedule.addCustomCondition(" and schoolTeachCircleId in(:stcidlist)", paramMap);	
			}
			PageList<TeachSchedule> tlist=teachScheduleService.findTeachlList(teachSchedule, page);
			m.addAttribute("tlist", tlist);
		}
		return viewName("readteachschedule");
		
	}

	// 保存校际进度表信息
	@RequestMapping("/save")
	@UseToken
	public void saveTeachSchedule(TeachSchedule ts, String originFileName,Model m) {
		try {
			teachScheduleService.saveTeach(ts, originFileName);
			m.addAttribute("isOk", 1);
		} catch (Exception e) {
			logger.error("校际进度表保存失败...",e);
			e.printStackTrace();
		}
		//return "redirect:/jy/teachschedule/index";
	}
	
	/**
	 * 通过文件Id得到文件对象
	 */
	@RequestMapping("/getFileById")
	public void getFileById(String resId,Model m){
		Resources res = resourcesService.findOne(resId);
		m.addAttribute("res",res);
	}
	
	// 删除校际进度表信息
	@RequestMapping("/delete")
	public void deleteTeachSchedule(Integer id, String resId, Model m) {
		if (id != null) {
			teachScheduleService.delete(id);
			if (resId!=null) {
				resourcesService.deleteResources(resId); 
			}
			boolean isOk = true;
			m.addAttribute("isOk", isOk);
		}
	}

	// 发布校际进度表信息
	@RequestMapping("/release")
	public void releaseTeachSchedule(TeachSchedule teachSchedule, Boolean isRelease,
			Model m) {
		if (teachSchedule.getId() != null) {
			teachSchedule.setIsRelease(isRelease);
			m.addAttribute("isRelease", isRelease);
			  if (isRelease==true) {
				  teachSchedule.setReleaseTime(new Date()); 
				  }
			teachScheduleService.update(teachSchedule);
			//添加消息通知
			teachScheduleService.addXiaoXiTongZhi(teachSchedule);
			boolean isOk = true;
			m.addAttribute("isOk", isOk);
		}
	}

//查看校际进度表信息
 @RequestMapping("/view")
 public String teachScheduleView(Model m, Integer id,String listType) {
   if (id!=null) {
     TeachSchedule ts=teachScheduleService.findOne(id);
     SchoolTeachCircleOrg stco =new SchoolTeachCircleOrg();
     stco.setStcId(ts.getSchoolTeachCircleId());
     stco.addOrder("stcId desc,sort asc");
     m.addAttribute(
         "schoolTeachCircleName",
         schoolTeachCircleService.findOne(ts.getSchoolTeachCircleId()).getName());
     m.addAttribute("stcolist", schoolTeachCircleOrgService.findAll(stco));
     m.addAttribute("teachSchedule", ts);
     m.addAttribute("listType", listType);
   }
   return viewName("teachscheduleview");
 }
	
	// 查看校际进度表是否被删除
	@RequestMapping("/dataIsDelete")
	public void dataIsDelete(Model m, Integer id) {
		TeachSchedule ts=teachScheduleService.findOne(id);
		if(ts!=null){
			Boolean isRelease = ts.getIsRelease();
			if(!isRelease){
				m.addAttribute("result", "delete");
			}
		}else{
			m.addAttribute("result", "delete");
		}
	}

}