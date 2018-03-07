/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.schedule.controller;

import java.beans.PropertyEditorSupport;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.schedule.bo.Schedule;
import com.tmser.tr.schedule.service.ScheduleService;
import com.tmser.tr.schedule.vo.Event;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * 日程表控制器接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Schedule.java, v 1.0 2015-04-08 Generate Tools Exp $
 */
@Controller
@RequestMapping("/jy/schedule")
public class ScheduleController extends AbstractController{
	
    @Autowired 
    private ScheduleService scheduleService;
    
	/**
	 * 显示日程信息
    */
	@RequestMapping("/index")
	public String index(){
		return viewName("index");
	}
	
	/**
	 * iframe显示日程信息
    */
	@RequestMapping("/small")
	public String small(){
		return viewName("small");
	}
	
	/**
	 * 显示日程信息
    */
	@RequestMapping(value="/list",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Result list(Long fromdt,Long todt){
		UserSpace us = (UserSpace) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		Result rs = new Result();
		Map<String, Object> parm = new HashMap<String, Object>();
		parm.put("fromdt", fromdt);
		parm.put("todt", todt);
		Schedule schedule=new Schedule();
		schedule.setUserid(us.getUserId());
		schedule.addCustomCondition("and `start` >= :fromdt and `start` < :todt",parm);
		
		List<Schedule> sList = scheduleService.findAll(schedule);
		rs.setCode(1);
		rs.setMsg("query success");
		rs.setData((Serializable) sList);
		return rs;
	}
	
	/**
	 * 增加日程信息
	*/
	@RequestMapping("/add")
	@ResponseBody
	public Result add(Event event,Model m){
		Result rs = new Result();
		UserSpace us = (UserSpace) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		if (us != null) {
			rs.setCode(1);
			Schedule schedule=new Schedule();
			schedule.setUserid(us.getUserId());
			schedule.setStart(Long.valueOf(event.getEvent().get("start")));
			schedule.setEnd(Long.valueOf(event.getEvent().get("end")));
			schedule.setSummary(event.getEvent().get("summary"));
			schedule.setIsallday(Boolean.valueOf(event.getEvent().get("isallday")));
			schedule.setLocation(event.getEvent().get("location"));
			schedule.setDescription(event.getEvent().get("description"));
			Date now = new Date();
			schedule.setCrtDttm(now);
			schedule.setLastupDttm(now);
			schedule.setColor(event.getEvent().get("color"));
			scheduleService.save(schedule);
			rs.setData(schedule);
		}
		return rs;
	}
	
	/**
	 * 增加日程信息
	*/
	@RequestMapping("/update")
	@ResponseBody
	public Result update(Schedule event,
			Model m){
		Result rs = new Result();
		UserSpace us = (UserSpace) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		if (us != null) {
			rs.setCode(1);
			Schedule schedule=new Schedule();
			schedule.setId(event.getId());
			schedule.setStart(event.getStart());
			schedule.setEnd(event.getEnd());
			schedule.setSummary(event.getSummary());
			schedule.setIsallday(event.getIsallday());
			schedule.setLocation(event.getLocation());
			schedule.setDescription(event.getDescription());
			Date now = new Date();
			schedule.setLastupDttm(now);
			schedule.setColor(event.getColor());
			if(us.getUserId().equals(event.getUserid()))
				scheduleService.update(schedule);
			rs.setData(schedule);
		}
		return rs;
	}
	
	/**
	 * 删除日程信息
	*/
	@RequestMapping("/del")
	@ResponseBody
	public Result delete(@RequestParam(value="id")Integer id,Model m){
		Result rs = new Result();
		UserSpace us = (UserSpace) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		if (us != null && id != null) {
			Schedule sc = scheduleService.findOne(id);
			if(sc != null && us.getUserId().equals(sc.getUserid())){
				scheduleService.delete(id);
				rs.setCode(1);
			}
		}
		return rs;
  }
	
	/**
	 * 删除日程信息
	*/
	@RequestMapping("/get")
	@ResponseBody
	public Result get(@RequestParam(value="id")Integer id,Model m){
		Result rs = new Result();
		UserSpace us = (UserSpace) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		if (us != null && id != null) {
			Schedule sc = scheduleService.findOne(id);
			if(sc != null && us.getUserId().equals(sc.getUserid())){
				rs.setData(sc);
				rs.setCode(1);
			}
		}
		return rs;
  }
	
	//@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception { 
		PropertyEditorSupport dateEditor = new PropertyEditorSupport(){
			
			public String getAsText() {
				Date value = (Date) getValue();
				return (value != null ? String.valueOf(value.getTime()) : "");
		    }
			
			/**
		     *
		     * @param text  The string to be parsed.
		     */
		    public void setAsText(String text) throws java.lang.IllegalArgumentException {
		    	if (!StringUtils.hasText(text)) {
					// Treat empty String as null value.
					setValue(null);
				}else {
					try {
						Long datetime = Long.valueOf(text);
						setValue(new Date(datetime));
					}
					catch (NumberFormatException ex) {
						throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
					}
				}
		    }
		};
		//表示如果命令对象有Date类型的属性，将使用该属性编辑器进行类型转换
		binder.registerCustomEditor(Date.class, "crtDttm", dateEditor);
		binder.registerCustomEditor(Date.class, "lastupDttm", dateEditor);
 } 

}