/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.api.writelessonplan.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tmser.tr.api.utils.ExceptionMessage;
import com.tmser.tr.api.writelessonplan.service.LessonPlanApiService;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.manage.resources.bo.ResRecommend;
import com.tmser.tr.rethink.controller.RethinkController;

/**
 * <pre>
 * 离线端撰写教案接口控制类
 * </pre>
 *
 * @author hyf
 * @version $Id: lessonplan.java, v 1.0 2016年4月15日 下午14:35:37 hyf Exp $
 */
@Controller
@RequestMapping(value = "/jy/api/writelessonplan", produces = "application/vnd.jypt.v1+json")
public class WriteLessonPlanApiController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(RethinkController.class);

	@Resource
	private LessonPlanApiService lessonPlanApiService;

	/**
	 * 保存教案
	 * 
	 * @param saveLessonPlan
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Result saveLessonPlan(@RequestParam(value = "lessonplan", required = true) String lessonplan, @RequestParam(value = "file", required = true) MultipartFile file) {
		Result result = null;
		try {
			result = lessonPlanApiService.saveLessonPlan(lessonplan, file);
		} catch (Exception e) {
			logger.error("[--离线用户保存教案错误--]", e);
			result = new Result(0, "请求抛出异常!》》》 " + ExceptionMessage.getExceptionMassage(e), new Date(), null);
		}
		return result;
	}

	/**
	 * 修改教案
	 * 
	 * @param saveLessonPlan
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Result updateLessonPlan(@RequestParam(value = "lessonplan", required = true) String lessonplan, @RequestParam(value = "file", required = true) MultipartFile file) {
		Result result = null;
		try {
			result = lessonPlanApiService.updateLessonPlan(lessonplan, file);
		} catch (Exception e) {
			logger.error("[--离线用户保存教案错误--]", e);
			result = new Result(0, "请求抛出异常！》》》" + ExceptionMessage.getExceptionMassage(e), new Date(), null);
		}
		return result;
	}

	/**
	 * 移动离线端-获取可用的教案模板
	 */
	@RequestMapping(value = "/getLessonPlanTemplate", method = RequestMethod.GET)
	@ResponseBody
	public Result getLessonPlanTemplate(Integer orgid) {
		Result result = null;
		try {
			if (orgid != null) {
				List<Map<String, Object>> data = lessonPlanApiService.getTemplateListByOrg(orgid);
				result = new Result(1, "数据读取成功", new Date(), data);
			} else {
				result = new Result(0, "用户机构orgid不能为空", new Date(), null);
			}
		} catch (Exception e) {
			logger.error("[--离线用户同步教案模板错误--]", e);
			result = new Result(0, "请求抛出异常！》》》" + ExceptionMessage.getExceptionMassage(e), new Date(), null);
		}
		return result;
	}

	/**
	 * 获取所有的推送资源
	 * 
	 * @param lessonId(课题id，必填)
	 * @param resType(资源类型，非必填)
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "/getCommendResource", method = RequestMethod.GET)
	public String getCommendResource(@RequestParam(value = "lessonId", required = true) String lessonId, @RequestParam(value = "resType", required = false) Integer resType, Map<String, Object> m) {
		try {
			ResRecommend rr = new ResRecommend();
			rr.setLessonId(lessonId);
			if (resType != null) {
				rr.setResType(resType);
			} else {
				rr.setResType(0);
			}
			List<ResRecommend> data = lessonPlanApiService.findCommendResource(rr);
			m.put("datalist", data);
			m.put("res", rr);
		} catch (Exception e) {
			logger.error("[--撰写教案，获取推送资源错误--]", e);
		}
		return "/writelessonplan/push_res_list";
	}

	/**
	 * 获取同伴资源
	 * 
	 * @param spaceId(用户空间id，必填)
	 * @param lessonId(课题id，必填)
	 * @param planType(资源类型，非必填)
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "getPeerResource", method = RequestMethod.GET)
	public String getPeerResource(@RequestParam(value = "spaceId", required = true) Integer spaceId, @RequestParam(value = "lessonId", required = true) String lessonId,
			@RequestParam(value = "planType", required = false) Integer planType, Map<String, Object> m) {
		try {
			if (planType == null) {
				planType = 0;
			}
			List<LessonPlan> lplist = lessonPlanApiService.getPeerResource(spaceId, lessonId, planType);
			m.put("datalist", lplist);
			m.put("planType", planType);
			m.put("lessonId", lessonId);
			m.put("spaceId", spaceId);
		} catch (Exception e) {
			logger.error("[--撰写教案，获取同伴资源错误--]", e);
		}
		return "/writelessonplan/peer_res_list";
	}

}
