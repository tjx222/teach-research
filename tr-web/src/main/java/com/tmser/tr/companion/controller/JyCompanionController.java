/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.companion.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.MobileUtils;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.companion.constants.JyCompanionConstants;
import com.tmser.tr.companion.service.JyCompanionMessageService;
import com.tmser.tr.companion.service.JyCompanionService;
import com.tmser.tr.companion.vo.JyCompanionSearchVo;
import com.tmser.tr.companion.vo.JyCompanionVo;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.utils.JyCollectionUtils;

/**
 * 同伴互助控制器接口
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author Generate Tools
 * @version $Id: JyCompanion.java, v 1.0 2015-05-20 Generate Tools Exp $
 */
@Controller
@RequestMapping("/jy/companion")
public class JyCompanionController extends AbstractController {

	@Autowired
	private JyCompanionService jyCompanionService;
	@Autowired
	private JyCompanionMessageService jyCompanionMessageService;

	/**
	 * 获取同伴列表
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping("/companions/index")
	public String findCompanions(@ModelAttribute JyCompanionSearchVo vo, Model m,@RequestParam(value="isSearchPage",required=false,defaultValue="0") Integer isSearchPage) {
		UserSpace us = CurrentUserContext.getCurrentSpace();
		vo.setSchoolId(us.getOrgId());
		vo.setCurrentGradeId(us.getGradeId());
		vo.setCurrentRoleId(us.getSysRoleId());
		vo.setCurrentSubjectId(us.getSubjectId());
		vo.setCurrentUserId(us.getUserId());
		vo.setIsSameSchool(vo.getIsSameSchool() == null ? true : vo.getIsSameSchool());
		if(MobileUtils.isNormal()){
			vo.setPageSize(14);
		}else{
			vo.setPageSize(12);
		}
		vo.setPhaseId(us.getPhaseId());
		vo.setSchoolYear(us.getSchoolYear());
		PageList<JyCompanionVo> page = jyCompanionService.findCompanions(vo);
		m.addAttribute("page", page);
		m.addAttribute("vo", vo);
		m.addAttribute("isSearchPage", isSearchPage);
		return "companion/companion_index";
	}

	/**
	 * 跳转到浏览所有伙伴的留言页面
	 * 
	 * @return
	 */
	@RequestMapping("/companions/messages/index")
	public String getAllMessagesIndex(Model m) {
		List<JyCompanionVo> list = jyCompanionService
				.findLatestConmunicateCompanions();
		List<JyCompanionVo> friends=jyCompanionService.findAllFriends();
		m.addAttribute("companions", list);
		m.addAttribute("friends", friends);
		List<JyCompanionVo> friendsInner=getInnerFriends(friends);
		List<JyCompanionVo> friendsOuter=getOuterFriends(friends);
		m.addAttribute("friendsInner", friendsInner);
		m.addAttribute("friendsOuter", friendsOuter);
		m.addAttribute("friendsInnerNum", friendsInner.size());
		m.addAttribute("friendsOuterNum", friendsOuter.size());
		m.addAttribute("allCompanions", getAllCompanions(friends,list));
		return "companion/companion_messages";
	}

	/**
	 * 查看所有最近联系同伴、朋友侧边栏内容
	 * 
	 * @return
	 */
	@RequestMapping("/companions/latestComunicate-friends/side")
	public String getAllMessagesContent(Model m) {
		List<JyCompanionVo> list = jyCompanionService
				.findLatestConmunicateCompanions();
		List<JyCompanionVo> friends=jyCompanionService.findAllFriends();
		m.addAttribute("companions", list);
		m.addAttribute("friends", friends);
		List<JyCompanionVo> friendsInner=getInnerFriends(friends);
		List<JyCompanionVo> friendsOuter=getOuterFriends(friends);
		m.addAttribute("friendsInner", friendsInner);
		m.addAttribute("friendsOuter", friendsOuter);
		m.addAttribute("friendsInnerNum", friendsInner.size());
		m.addAttribute("friendsOuterNum", friendsOuter.size());
		m.addAttribute("allCompanions", getAllCompanions(friends,list));
		return "companion/companion_side_content";
	}

	/**
	 * 获取所有同伴
	 * @param friends
	 * @param list
	 * @return
	 */
	private List<JyCompanionVo> getAllCompanions(List<JyCompanionVo> friends,
			List<JyCompanionVo> list) {
		Map<Integer,JyCompanionVo> companionMap = new HashMap<>();
		for(JyCompanionVo vo:friends){
			companionMap.put(vo.getUserIdCompanion(), vo);
		}
		for(JyCompanionVo vo:list){
			companionMap.put(vo.getUserIdCompanion(), vo);
		}
		return JyCollectionUtils.getMapValue(companionMap);
	}

	/**
	 * 获取校外朋友
	 * @param friends
	 * @return
	 */
	private List<JyCompanionVo> getOuterFriends(List<JyCompanionVo> friends) {
		List<JyCompanionVo> result = new ArrayList<JyCompanionVo>();
		for(JyCompanionVo vo:friends){
			if(JyCompanionConstants.IS_SAME_ORG_FALSE.equals(vo.getIsSameOrg())){
				result.add(vo);
			}
		}
		return result;
	}

	/**
	 * 获取校内同伴
	 * @param friends
	 * @return
	 */
	private List<JyCompanionVo> getInnerFriends(List<JyCompanionVo> friends) {
		List<JyCompanionVo> result = new ArrayList<JyCompanionVo>();
		for(JyCompanionVo vo:friends){
			if(JyCompanionConstants.IS_SAME_ORG_TRUE.equals(vo.getIsSameOrg())){
				result.add(vo);
			}
		}
		return result;
	}


	/**
	 * 获取好友列表
	 * 
	 * @return
	 */
	@RequestMapping("/friends")
	public Result findFriends() {
		List<JyCompanionVo> list = jyCompanionService.findAllFriends();
		return new Result(list);
	}

	/**
	 * 新增好友关系
	 * 
	 * @param userIdCompanion
	 * @return
	 */
	@RequestMapping(value = "/friends/{userIdCompanion}", method = { RequestMethod.POST })
	public Result addFriend(
			@PathVariable("userIdCompanion") Integer userIdCompanion) {
		try {
			jyCompanionService.addFriend(userIdCompanion);
			return new Result("success", false);
		} catch (Exception e) {
			logger.error("新增好友错误", e);
			return new Result(e.getMessage());
		}
	}

	/**
	 * 移除好友关系
	 * 
	 * @param userIdCompanion
	 * @return
	 */
	@RequestMapping(value = "/friends/{userIdCompanion}", method = { RequestMethod.DELETE })
	public Result removeFriend(
			@PathVariable("userIdCompanion") Integer userIdCompanion) {
		try {
			jyCompanionService.deleteFriend(userIdCompanion);
			return new Result("success", false);
		} catch (Exception e) {
			logger.error("移除好友错误", e);
			return new Result(e.getMessage());
		}

	}

	/**
	 * 同伴详情
	 * 
	 * @return
	 */
	@RequestMapping("/companions/{userIdCompanion}")
	public String getCompaniondetail(
			@PathVariable("userIdCompanion") Integer userIdCompanion, Model m) {
		JyCompanionVo companionVo = jyCompanionService
				.findCompanionDetail(userIdCompanion);
		m.addAttribute("vo", companionVo);
		return "companion/companion_detail";
	}

	/**
	 * 查找用户简介信息
	 * @param userIds
	 * @return
	 */
	@RequestMapping("/userSimpleInfos")
	public Result getuserSimpleInfos(@RequestParam("userIds") List<Integer> userIds){
		List<JyCompanionVo> vos=jyCompanionService.findUserSimpleInfos(userIds);
		return new Result(vos);
	}

	public void setJyCompanionService(JyCompanionService jyCompanionService) {
		this.jyCompanionService = jyCompanionService;
	}

	/**
	 * 我的关注（移动端）
	 */
	@RequestMapping("/companions/mycare")
	public String mycare(Model m) {
		return "companion/companion_side_content";
	}

	/**
	 * 我关注的同伴（移动端）
	 */
	@RequestMapping("/companions/mycomps")
	public String mycomps(Model m,@RequestParam(value="username",required=false)String username,
			@RequestParam(value="iscare",required=true)Boolean iscare) {
		Map<String,Object> feirndMap = jyCompanionService.getMyCareFriend(username,iscare);
		m.addAttribute("data", feirndMap);
		m.addAttribute("username", username);
		if(iscare){
			return "companion/careCompanions";
		}else{
			return "companion/companionsmsg";
		}
	}

	/**
	 * 我的消息
	 */
	@RequestMapping("/companions/mymsg")
	public String mymsg(Model m) {
		//最后一个给我发送消息的人
		Integer companionId = jyCompanionMessageService.getLastSenderUserID();
		m.addAttribute("companionId", companionId);
		return "companion/companion_msg";
	}

	/**
	 * 同伴的信息和同伴的分享
	 */
	@RequestMapping("/companions/compshare")
	public String mymsg(Model m,Integer companionId,Boolean selfinfo) {
		//最后一个给我发送消息的人
		Map<String,Object> resultMap = jyCompanionService.getCompanionShares(selfinfo,companionId);
		m.addAttribute("datas", resultMap);
		m.addAttribute("companionId", companionId);
		if(selfinfo!=null && selfinfo){
			return "companion/companion_detail";
		}else{
			return "companion/companionshare";
		}
	}

	/**
	 * 主页进入同伴的信息和同伴的分享
	 */
	@RequestMapping("/companions/compshareindex")
	public String compshareindex(Model m,Integer companionId) {
		//最后一个给我发送消息的人
		Map<String,Object> resultMap = jyCompanionService.getCompanionShares(true,companionId);
		m.addAttribute("datas", resultMap);
		m.addAttribute("companionId", companionId);
		return "companion/companion_detail_index";
	}
}