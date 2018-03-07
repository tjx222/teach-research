/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.lecturerecords.bo.LectureRecords;
import com.tmser.tr.lecturerecords.service.LectureRecordsService;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 *  历史学年记录模块-听课记录
 * </pre>
 *
 * @author dell
 * @version $Id: LectureRecordHistoryController.java, v 1.0 2016年5月20日 下午3:01:09 dell Exp $
 */

@Controller
@RequestMapping("/jy/history")
public class LectureRecordHistoryController extends AbstractController {

	@Resource
	private UserSpaceService userSpaceService;
	
	@Resource
	private LectureRecordsService lectureRecordsService;
	
    /**
	 * 首页
	 * @param m
	 * @return
	 */ 
	@RequestMapping("/{year}/tkjl/index")
	public String index(@PathVariable("year")Integer schoolYear,
			LectureRecords info,Model m){
		User user = CurrentUserContext.getCurrentUser();
		LectureRecords model = new LectureRecords();
		model.pageSize(10);// 设置每页的展示数
		model.setSchoolYear(schoolYear);// 当前学年
		model.setLecturepeopleId(user.getId());// 听课人ID
		model.setIsDelete(false);// 不删除
		model.setIsEpub(1);// 发布
		model.addOrder("epubTime desc");// 按照发布时间降序
		if (info != null) {// 从页面传过来的当前页数
			model.currentPage(info.getPage().getCurrentPage());
			model.setTerm(info.getTerm());
			model.setType(info.getType());
			if (StringUtils.isNotEmpty(info.getTopic())) {
				model.setTopic(SqlMapping.LIKE_PRFIX+info.getTopic()+SqlMapping.LIKE_PRFIX);
			}
		}
		int cp = model.getPage().getCurrentPage();

		PageList<LectureRecords> plList = lectureRecordsService.findByPage(model);// 查询当前页的评论
		if (plList.getDatalist().size() == 0 && cp > 1) {
			model.currentPage(cp - 1);
			plList = lectureRecordsService.findByPage(model);// 查询当前页的评论
		}

		m.addAttribute("data", plList);// 按照分页进行查询
		m.addAttribute("year",schoolYear);
		m.addAttribute("lr", info);
		return "history/tkjl/index";
	}
	
}
