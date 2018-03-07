/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.api.base.controller;

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

import com.tmser.tr.api.base.service.MobileBasicService;
import com.tmser.tr.api.utils.ExceptionMessage;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.rethink.controller.RethinkController;

/**
 * <pre>
 * 离线端基础数据接口控制类
 * </pre>
 *
 * @author zpp
 * @version $Id: BasicController.java, v 1.0 2016年4月11日 下午14:05:37 zpp Exp $
 */
@Controller
@RequestMapping(value = "/jy/api/base", produces = "application/vnd.jypt.v1+json")
public class MobileBasicController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(RethinkController.class);

	@Resource
	private MobileBasicService mobileBasicService;

	/**
	 * 移动教研平台离线端请求在线登录成功后，同步用户基础信息控制。
	 * 
	 * @param userid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	public Result userInfo(@RequestParam(value = "userid", required = true) Integer userid) {
		Result result = null;
		try {
			if (userid != null) {
				Map<String, Object> data = mobileBasicService.getBaseUserInfo(userid);
				result = new Result(1, "数据读取成功！", new Date(), data);
			} else {
				result = new Result(0, "用户ID不能为空！", new Date(), null);
			}
		} catch (Exception e) {
			logger.error("[--离线用户同步用户信息错误--]", e);
			result = new Result(0, "请求抛出异常！》》》" + ExceptionMessage.getExceptionMassage(e), new Date(), null);
		}
		return result;
	}

	/**
	 * 移动教研平台离线端请求在线登录成功后，同步机构-学科\年级\用户\的信息。
	 * 
	 * @param orgId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/orgInfo", method = RequestMethod.GET)
	public Result orgInfo(@RequestParam(value = "orgid", required = true) Integer orgid) {
		Result result = null;
		try {
			Map<String, Object> data = mobileBasicService.getOrgInfo(orgid);
			if (data != null) {
				result = new Result(1, "数据读取成功！", new Date(), data);
			} else {
				result = new Result(0, "此机构数据已不存在！", new Date(), null);
			}
		} catch (Exception e) {
			logger.error("[--离线用户同步机构-年级学科用户信息错误--]", e);
			result = new Result(0, "请求抛出异常！》》》orgid=" + orgid + "   日志信息：" + ExceptionMessage.getExceptionMassage(e), new Date(), null);
		}
		return result;
	}

	/**
	 * 移动教研平台离线端请求在线登录成功后，同步用户目录章节信息。
	 * 
	 * @param userid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/bookInfo", method = RequestMethod.GET)
	public Result bookInfo(@RequestParam(value = "userid", required = true) Integer userid) {
		Result result = null;
		try {
			List<Map<String, Object>> data = mobileBasicService.getBookInfo(userid);
			result = new Result(1, "数据读取成功！", new Date(), data);
		} catch (Exception e) {
			logger.error("[--离线用户同步用户书籍信息错误--]", e);
			result = new Result(0, "请求抛出异常！》》》" + ExceptionMessage.getExceptionMassage(e), new Date(), null);
		}
		return result;
	}

	/**
	 * 获得当前机构下所用到的书籍数据集合
	 * 
	 * @param orgid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/orgBooks", method = RequestMethod.GET)
	public Result orgBooks(@RequestParam(value = "orgid", required = true) Integer orgid) {
		Result result = null;
		try {
			Map<String, Object> data = mobileBasicService.getOrgBooksInfo(orgid);
			result = new Result(1, "机构书籍数据读取成功！", new Date(), data);
		} catch (Exception e) {
			logger.error("[--离线同步机构书籍信息错误--]", e);
			result = new Result(0, "请求抛出异常！》》》 机构ID=" + orgid + ExceptionMessage.getExceptionMassage(e), new Date(), null);
		}
		return result;
	}

}
