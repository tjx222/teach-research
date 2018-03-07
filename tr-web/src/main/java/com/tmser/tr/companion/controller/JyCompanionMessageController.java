/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.companion.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.companion.bo.JyCompanionMessage;
import com.tmser.tr.companion.service.JyCompanionMessageService;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.uc.dao.UserDao;
import com.tmser.tr.uc.utils.CurrentUserContext;

/**
 * 同伴互助留言控制器接口
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author Generate Tools
 * @version $Id: JyCompanionMessage.java, v 1.0 2015-05-20 Generate Tools Exp $
 */
@Controller
@RequestMapping("/jy/companion")
public class JyCompanionMessageController extends AbstractController {

	@Autowired
	private JyCompanionMessageService jyCompanionMessageService;

	@Autowired
	private UserDao uerDao;

	@Autowired
	private ResourcesService resourcesService;


	/**
	 * 给同伴发送消息
	 * 
	 * @param userIdCompanion
	 * @param message
	 */
	@RequestMapping(value = "/messages", method = RequestMethod.POST)
	public Result sendMessage(@ModelAttribute JyCompanionMessage message) {
		try {
			if(StringUtils.isNotBlank(message.getAttachment1())){
				//回调确认文件已保存
				resourcesService.updateTmptResources(message.getAttachment1());
			}else{
				message.setAttachment1Name(null);
			}
			if(StringUtils.isNotBlank(message.getAttachment2())){
				//回调确认文件已保存
				resourcesService.updateTmptResources(message.getAttachment2());
			}else{
				message.setAttachment2Name(null);
			}
			if(StringUtils.isNotBlank(message.getAttachment3())){
				//回调确认文件已保存
				resourcesService.updateTmptResources(message.getAttachment3());
			}else{
				message.setAttachment3Name(null);
			}
			jyCompanionMessageService.addMessage(message);
			return new Result("success", false);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new Result(e.getMessage());
		}
	}

	/**
	 * 查询通讯记录
	 * 
	 * @param userIdCompanion
	 * @param pageSize
	 * @param pageNum
	 * @param startDate
	 * @return
	 */
	@RequestMapping("/companions/{userIdCompanion}/messages")
	public Result findMessages(
			@PathVariable("userIdCompanion") Integer userIdCompanion,
			@ModelAttribute JyCompanionMessage message) {
		message.setUserIdReceiver(userIdCompanion);
		message.setUserIdSender(CurrentUserContext.getCurrentUserId());

		// 分页查询
		PageList<JyCompanionMessage> page = jyCompanionMessageService
				.findMessageWithPage(message);
		List<Date> comunicateDates=jyCompanionMessageService.findAllComunicateDates(message);
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("page", page);
		resultMap.put("dates", getComunicateDatesStr(comunicateDates));
		return new Result(resultMap);
	}

	/**
	 * 获取时间列表
	 * @param comunicateDates
	 * @return
	 */
	private List<String> getComunicateDatesStr(List<Date> comunicateDates) {
		Set<String> set = new HashSet<String>();
		if(!CollectionUtils.isEmpty(comunicateDates)){
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			for(Date date:comunicateDates){
				set.add(df.format(date));
			}
		}
		return new ArrayList<String>(set);
	}

	/**
	 * 查询通讯记录
	 * 
	 * @param userIdCompanion
	 * @param pageSize
	 * @param pageNum
	 * @param startDate
	 * @return
	 */
	@RequestMapping("/messages/{userIdCompanion}/page")
	public String findMessagesPage(
			@PathVariable("userIdCompanion") Integer userIdCompanion,
			JyCompanionMessage message, Model m) {
		message.setUserIdReceiver(userIdCompanion);
		message.setUserIdSender(CurrentUserContext.getCurrentUserId());
		message.pageSize(5);

		// 分页查询
		PageList<JyCompanionMessage> page = jyCompanionMessageService
				.findMessageWithPage(message);
		m.addAttribute("page", page);
		m.addAttribute("user", CurrentUserContext.getCurrentUser());
		m.addAttribute("companionUser", uerDao.get(userIdCompanion));
		return "companion/companion_detail_message";
	}

	@RequestMapping("/attachments/{attachmentId}")
	public ModelAndView getAttachment(@PathVariable("attachmentId") String attachmentId){
		Resources res = resourcesService.findOne(attachmentId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("res", res);
		mv.setViewName("companion/resource_view");
		return mv;
	}

	/**
	 * 获取指定时间之前的消息
	 * @param time
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/{userId}/preMessages/{time}/{pageSize}",method=RequestMethod.GET)
	public Result getPreMessages(@PathVariable("userId") Integer userId,
			@PathVariable("time") Long time,@PathVariable("pageSize") Integer pageSize){
		Date date = null;
		if(time==-1){
			date = new Date();
		}else{
			date = new Date(time);
		}
		//查询指定时间之前的消息
		List<JyCompanionMessage> messages = jyCompanionMessageService.getPreMessages( userId,date,pageSize);

		Map<String,Object> map = new HashMap<String, Object>();
		map.put("messages", messages);
		//消息记录开始时间
		map.put("startTime", getStartTime(messages));
		//消息记录结束时间
		map.put("endTime", getEndTime(messages));
		//是否还有上一页消息
		map.put("hasPre", hashPre( getStartTime(messages),userId));
		//是否还有下一页消息
		map.put("hasNext", hashNext( getEndTime(messages),userId));
		return new Result(map);
	}

	/**
	 * 获取指定时间之后的消息
	 * @param time
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/{userId}/nextMessages/{time}/{pageSize}",method=RequestMethod.GET)
	public Result getNextMessages(@PathVariable("userId") Integer userId,
			@PathVariable("time") Long time,@PathVariable("pageSize") Integer pageSize){
		//查询指定时间之前的消息
		List<JyCompanionMessage> messages = jyCompanionMessageService.getNextMessages( userId,new Date(time),pageSize);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("messages", messages);
		//消息记录开始时间
		map.put("startTime", getStartTime(messages));
		//消息记录结束时间
		map.put("endTime", getEndTime(messages));
		//是否还有上一页消息
		map.put("hasPre", hashPre( getStartTime(messages),userId));
		//是否还有下一页消息
		map.put("hasNext", hashNext(getEndTime(messages),userId));
		return new Result(map);
	}

	/**
	 * 获取所有的沟通时间
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/{userId}/comunicateDates",method=RequestMethod.GET)
	public Result getComnicateDates(@PathVariable("userId") Integer userId){
		JyCompanionMessage message = new JyCompanionMessage();
		message.setUserIdReceiver(userId);
		message.setUserIdSender(CurrentUserContext.getCurrentUserId());
		List<Date> comunicateDates=jyCompanionMessageService.findAllComunicateDates(message);
		return new Result(getComunicateDatesStr(comunicateDates));
	}

	/**
	 * 验证是否有前消息
	 * @param startTime
	 * @param userId
	 * @return
	 */
	private Integer hashPre(Long startTime, Integer userId) {
		if(startTime==null){
			return 0;
		}
		//首次沟通的时间
		Date date=jyCompanionMessageService.getFirstMessageDate(userId);
		if(date==null){
			return 0;
		}
		if(startTime>date.getTime()){
			return 1;
		}
		return 0;
	}

	/**
	 * 是否有下一条消息
	 * @param endTime
	 * @param userId
	 * @return
	 */
	private Integer hashNext(Long endTime, Integer userId) {
		if(endTime==null){
			return 0;
		}
		//最近沟通的时间
		Date date=jyCompanionMessageService.getLatestMessageDate(userId);
		if(date==null){
			return 0;
		}
		if(endTime<date.getTime()){
			return 1;
		}
		return 0;
	}

	/**
	 * 获取记录开始时间
	 * @param messages
	 * @return
	 */
	private Long getStartTime(List<JyCompanionMessage> messages) {
		if(CollectionUtils.isEmpty(messages)){
			return null;
		}
		return messages.get(0).getSenderTime().getTime();
	}

	/**
	 * 获取记录结束时间
	 * @param messages
	 * @return
	 */
	private Long getEndTime(List<JyCompanionMessage> messages) {
		if(CollectionUtils.isEmpty(messages)){
			return null;
		}
		return messages.get(messages.size()-1).getSenderTime().getTime();
	}

	/**
	 * 查询通讯记录
	 * 
	 * @param companionId
	 * @param page
	 * @param m
	 * @return
	 */
	@RequestMapping("/companions/compSendMsg/{companionId}")
	public String compSendMsg(@PathVariable("companionId") Integer companionId,
			Boolean msglist, Page page, Model m) {
		// 分页查询
		PageList<JyCompanionMessage> jcmList = jyCompanionMessageService.findMessageLastPage(companionId,page);
		m.addAttribute("data", jcmList);
		m.addAttribute("companionId", companionId);
		if(msglist!=null && msglist){
			return "companion/comp_send_msg_list";
		}else{
			return "companion/comp_send_msg";
		}
	}


}