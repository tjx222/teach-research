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
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.history.service.ThesisHistory;
import com.tmser.tr.thesis.bo.Thesis;
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 *  历史学年记录模块-教学文章
 * </pre>
 *
 * @author dell
 * @version $Id: ThesisHistoryController.java, v 1.0 2016年5月23日 上午11:40:46 dell Exp $
 */

@Controller
@RequestMapping("/jy/history")
public class ThesisHistoryController extends AbstractController {
	
	@Resource
	private ThesisHistory thesisHistory;
	
	/**
	 * 首页
	 * param m
	*/
	@RequestMapping("/{year}/jxwz/index")
	public String getIndex(@PathVariable("year")Integer schoolYear,
			Thesis thesis,Page page,Model m){
    	String title = thesis.getThesisTitle();
    	if (!StringUtils.isEmpty(title)) {
    		thesis.setThesisTitle(SqlMapping.LIKE_PRFIX+title+SqlMapping.LIKE_PRFIX);
		}
    	page.setPageSize(10);
    	thesis.addPage(page);
    	thesis.setSchoolYear(schoolYear);
		PageList<Thesis> tList=thesisHistory.findCourseList(thesis);
		m.addAttribute("tList", tList);
		thesis.setThesisTitle(title);
		m.addAttribute("year",schoolYear);
		m.addAttribute("thesis", thesis);
		return "history/jxwz/index";
	}
}
