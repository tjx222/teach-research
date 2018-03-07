package com.tmser.tr.writelessonplan.controller;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.writelessonplan.service.LessonPlanService;

/**
 * 
 * <pre>
 * 对接简案、课件
 * </pre>
 *
 * @author guohuawei
 * @version $Id: LessonPlanRemoteController.java, v 1.0 2016年5月20日 下午1:41:52
 *          guohuawei Exp $
 */
@Controller
@RequestMapping("/jy/ws")
public class LessonPlanRemoteController extends AbstractController {
	@Autowired
	private LessonPlanService lessonPlanService;

	private static final Logger logger = LoggerFactory
			.getLogger(LessonPlanRemoteController.class);

	@RequestMapping("/toEditPlanRemote")
	public String toEditLessonPlanRemote(String lessonid, String username,
			String content, Model m) {
		Map<String, Object> map = lessonPlanService.toEditLessonPlanRemote(
				lessonid, username);
		if (map != null) {
			m.addAttribute("userId", map.get("userId"));
			m.addAttribute("chapterName", map.get("chapterName"));
			JSONObject jsonObject = (JSONObject) JSONObject.toJSON(map
					.get("chapterTree"));
			m.addAttribute("chapterTree", jsonObject);
			JSONArray arr = null;
			try {
				arr = JSON.parseArray(content);
				
			} catch (Exception e) {
				m.addAttribute("contents", content);
			}
			if (arr != null) {
				StringBuilder sb = new StringBuilder();
				for (Object o : arr) {
					sb.append(o).append("<br/>");
				}
				m.addAttribute("contents", sb.toString());
			}
		}
		return "writelessonplan/generateLessonPlan";
	}

	@RequestMapping("/saveLessonPlanRemote")
	@ResponseBody
	public Result saveLessonPlanRemote(String lessonid, String userid,
			String content) {
		Result result = new Result();
		try {
			result = lessonPlanService.saveLessonPlanRemote(lessonid, userid,
					content);
		} catch (IOException e) {
			result.setCode(0);
			result.setMsg("保存失败，请重试");
			logger.error("平台对接保存简案失败", e);
		}
		return result;
	}

	@RequestMapping("/abutmentTeachingplan")
	@ResponseBody
	public Result abutmentTeachingplan(String lessonId, String loginName,
			String url, String bookId, String lessonName) {
		Integer planId = lessonPlanService.abutmentTeachingRemote(loginName,
				lessonId, url, bookId, lessonName);
		Result result = new Result();
		if (planId != null) {
			result.setCode(1);
			result.setData(planId);
			result.setMsg("保存成功");
		} else {
			result.setCode(0);
			result.setMsg("保存失败");
		}
		return result;
	}

	@RequestMapping("/abutmentTeachingplan/delAbutmentTeachingplan")
	@ResponseBody
	public Result delAbutmentTeachingplan(String url, String loginName) {
		Result rs = lessonPlanService.deleabutmentTeachingPlan(url, loginName);
		return rs;
	}

	@RequestMapping("/abutmentTeachingplan/validateLessonIdBookId")
	@ResponseBody
	public Result validateLessonIdBookId(String bookId, String lessonId,
			String loginName) {
		Result re = lessonPlanService.validateLessonIdBookId(bookId, lessonId,
				loginName);
		return re;
	}

}
