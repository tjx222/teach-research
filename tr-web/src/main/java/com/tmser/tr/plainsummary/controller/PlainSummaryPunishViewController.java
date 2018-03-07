/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.plainsummary.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tmser.tr.annunciate.service.AnnunciatePunishViewService;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.plainsummary.bo.PlainSummary;
import com.tmser.tr.plainsummary.bo.PlainSummaryPunishView;
import com.tmser.tr.plainsummary.service.PlainSummaryPunishViewService;
import com.tmser.tr.plainsummary.vo.PlainSummaryVo;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * 计划总结发布查看记录控制器接口
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: PlainSummaryPunishView.java, v 1.0 2015-06-30 Generate Tools
 *          Exp $
 */
@Controller
@RequestMapping("/jy/planSummary/")
public class PlainSummaryPunishViewController extends AbstractController {

	@Autowired
	private PlainSummaryPunishViewService plainSummaryPunishViewService;

	@Autowired
	private AnnunciatePunishViewService annunciatePunishViewService;

	/**
	 * 计划总结查看
	 * 
	 * @param psId
	 * @return
	 */
	@RequestMapping(value = "punishViews/{psId}", method = RequestMethod.PUT)
	public ModelAndView addView(@PathVariable("psId") Integer psId) {
		ModelAndView mv = new ModelAndView("/plainSummary/planSummaryView");
		PlainSummaryPunishView view = new PlainSummaryPunishView();
		view.setUserId(CurrentUserContext.getCurrentUserId());
		view.setPlainSummaryId(psId);
		view.setViewTime(new Date());
		Integer count = plainSummaryPunishViewService.count(view);
		// 没有记录即新增查看记录
		if (count == 0) {
			try {
				plainSummaryPunishViewService.save(view);
			} catch (Exception e) {
				logger.error("新增计划总结发布查阅失败", e);
			}
		}

		mv.addObject("code", 1);
		return mv;
	}

	/**
	 * 获取发布的计划总结
	 * 
	 * @param ps
	 * @return
	 */
	@RequestMapping(value = "punishs")
	public ModelAndView getPunishs(PlainSummary ps) {
		if (ps == null) {
			ps = new PlainSummary();
		}
		try {
			ModelAndView mv = new ModelAndView("/plainSummary/planSummary_punish");
			ps.pageSize(10);// 支持客户端设置每页显示数量
			ps.setOrgId(CurrentUserContext.getCurrentUser().getOrgId());
			UserSpace us = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
			ps.setPhaseId(us.getPhaseId());
			PageList<PlainSummaryVo> page = plainSummaryPunishViewService.findPunishsByPage(ps);
			mv.addObject("data", page);
			return mv;
		} catch (Exception e) {
			logger.error("get punish jydt failed.", e);
			throw e;
		}

	}

	/**
	 * 获取未查看已发布的计划总结
	 * 
	 * @param ps
	 * @return
	 */
	@RequestMapping(value = "punishs/unViewNum", method = RequestMethod.GET)
	@ResponseBody
	public Result getUnViewNum() {
		Integer unViewNum = plainSummaryPunishViewService.getUnViewNum();
		Integer noticeNum = annunciatePunishViewService.getAnnunciateNum();
		Map<String, Integer> mv = new HashMap<>();
		mv.put("unViewNum", unViewNum);
		mv.put("noticeNum", noticeNum);
		return new Result(mv);
	}

	/**
	 * 获取已发布未查看的计划总结
	 * 
	 * @return
	 */
	@RequestMapping(value = "punishs/unViews/{num}", method = RequestMethod.GET)
	public Result getLatestUnView(@PathVariable("num") Integer num) {
		// 查询已提交未查看的计划总结
		List<PlainSummaryVo> list = plainSummaryPunishViewService.findUnviews(num);
		return new Result(list);
	}
}