/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.api.lecturerecords.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.api.lecturerecords.service.MobileLecturerecordsService;
import com.tmser.tr.api.lecturerecords.vo.LectureRecordsMapping;
import com.tmser.tr.api.utils.ExceptionMessage;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.rethink.controller.RethinkController;

/**
 * <pre>
 * 离线端听课记录数据接口控制类
 * </pre>
 *
 * @author tmser
 * @version $Id: BasicController.java, v 1.0 2016年4月11日 下午14:05:37 tmser Exp $
 */
@Controller
@RequestMapping(value = "/jy/api/lecture", produces = "application/vnd.jypt.v1+json")
public class MobileLecturerecordsController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(RethinkController.class);

	@Resource
	private MobileLecturerecordsService mobileLecturerecordsService;

	/**
	 * 听课记录信息获取信息
	 * 
	 * @param userid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/lecturelist", method = RequestMethod.GET)
	public Result lecturelist(@RequestParam(value = "userid", required = true) Integer userid, @RequestParam(value = "optime", required = false) String optime) {
		Result result = null;
		try {
			List<LectureRecordsMapping> lrlist = mobileLecturerecordsService.findInfoList(userid, optime);
			result = new Result(1, "读取听课记录信息成功！", new Date(), lrlist);
		} catch (Exception e) {
			logger.error("[--离线用户同步用户信息错误--]", e);
			result = new Result(0, "请求抛出异常！》》》" + ExceptionMessage.getExceptionMassage(e), new Date(), null);
		}
		return result;
	}

	/**
	 * 离线接口调用，听课记录信息保存
	 * 
	 * @param userid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Result save(@RequestParam(value = "lectureinfo", required = true) String lectureinfo) {
		Result result = null;
		try {
			result = mobileLecturerecordsService.saveLecture(lectureinfo);
		} catch (Exception e) {
			logger.error("[--离线听课记录保存出错--]", e);
			result = new Result(0, "请求抛出异常！》》》" + ExceptionMessage.getExceptionMassage(e), new Date(), null);
		}
		return result;
	}

	/**
	 * 离线接口调用，听课记录信息修改
	 * 
	 * @param userid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Result update(@RequestParam(value = "lectureinfo", required = true) String lectureinfo) {
		Result result = null;
		try {
			result = mobileLecturerecordsService.updateLecture(lectureinfo);

		} catch (Exception e) {
			logger.error("[--离线听课记录更新出错--]", e);
			result = new Result(0, "请求抛出异常！》》》" + ExceptionMessage.getExceptionMassage(e), new Date(), null);
		}
		return result;
	}

	/**
	 * 离线接口调用，听课记录信息删除
	 * 
	 * @param userid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public Result delete(@RequestParam(value = "lectureid", required = true) Integer lectureid) {
		Result result = null;
		try {
			result = mobileLecturerecordsService.deleteLecture(lectureid);
		} catch (Exception e) {
			logger.error("[--离线听课记录删除出错--]", e);
			result = new Result(0, "请求抛出异常！》》》" + ExceptionMessage.getExceptionMassage(e), new Date(), null);
		}
		return result;
	}

	/**
	 * 离线接口调用，听课记录信息分享
	 * 
	 * @param userid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/share", method = RequestMethod.POST)
	public Result share(@RequestParam(value = "lectureid", required = true) Integer lectureid) {
		Result result = null;
		try {
			result = mobileLecturerecordsService.shareLecture(lectureid);
		} catch (Exception e) {
			logger.error("[--离线听课记录分享出错--]", e);
			result = new Result(0, "请求抛出异常！》》》" + ExceptionMessage.getExceptionMassage(e), new Date(), null);
		}
		return result;
	}
	
	/**
	 * 修改听课记录回复和评论的状态
	 * 
	 * @param lectureid
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/uplecupsta", method = RequestMethod.POST)
	public Result uplecupsta(@RequestParam(value = "lectureid", required = true) Integer lectureid,@RequestParam(value = "type", required = true) String type) {
		Result result = null;
		try {
			result = mobileLecturerecordsService.uplecupsta(lectureid,type);
		} catch (Exception e) {
			logger.error("[--离线听课记录回复和评论状态修改出错--]", e);
			result = new Result(0, "请求抛出异常！》》》" + ExceptionMessage.getExceptionMassage(e), new Date(), null);
		}
		return result;
	}

}
