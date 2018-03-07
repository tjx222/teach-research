package com.tmser.tr.api.myplanbook.controller;

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

import com.tmser.tr.api.myplanbook.service.MyPlanBookApiService;
import com.tmser.tr.api.utils.ExceptionMessage;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.myplanbook.service.MyPlanBookService;

/**
 * <pre>
 * 离线端基础数据接口控制类
 * </pre>
 *
 * @author hyf
 * @version $Id: MyPlanBook.java, v 1.0 2016年4月14日 下午10:35:37 hyf Exp $
 */
@Controller
@RequestMapping(value = "/jy/api/myplanbook", produces = "application/vnd.jypt.v1+json")
public class MyPlanBookApiController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(MyPlanBookApiController.class);
	@Resource
	private MyPlanBookApiService myPlanBookApiService;

	@Resource
	private MyPlanBookService myPlanBookService;

	/**
	 * 离线端-我的备课本：获取备课信息接口
	 */

	@RequestMapping(value = "/getLessonPlan", method = RequestMethod.GET)
	@ResponseBody
	public Result getPlanBookInfo(Integer userid) {
		Result result = null;
		try {
			if (userid != null) {
				List<Map<String, Object>> data = myPlanBookApiService.getLessonPlan(userid);
				result = new Result(1, "数据读取成功", new Date(), data);
			} else {
				result = new Result(0, "用户ID不能为空！", new Date(), null);
			}
		} catch (Exception e) {
			logger.error("[--离线用户同步备课信息错误--]", e);
			result = new Result(0, "请求抛出异常！》》》" + ExceptionMessage.getExceptionMassage(e), new Date(), null);
		}
		return result;
	}

	/**
	 * 离线端-我的备课本：获取备课意见接口
	 */

	@RequestMapping(value = "/getLessonInfo", method = RequestMethod.GET)
	@ResponseBody
	public Result getLessonInfo(Integer userid) {
		Result result = null;
		try {
			if (userid != null) {
				List<Map<String, Object>> data = myPlanBookApiService.getLessonInfo(userid);
				result = new Result(1, "数据读取成功", new Date(), data);
			} else {
				result = new Result(0, "用户ID不能为空！", new Date(), null);
			}
		} catch (Exception e) {
			logger.error("[--离线用户同步备课意见错误--]", e);
			result = new Result(0, "请求抛出异常！》》》" + ExceptionMessage.getExceptionMassage(e), new Date(), null);
		}
		return result;
	}

	/**
	 * 离线端-我的备课本：提交(保存)备课信息接口
	 */
	@RequestMapping(value = "/saveMyPlanBook", method = RequestMethod.PUT)
	@ResponseBody
	public Result submitLessonPlans(@RequestParam(value = "lessonplan", required = true) String lessonplan) {
		Result result = null;
		try {
			result = myPlanBookApiService.saveMyPlanBook(lessonplan);

		} catch (Exception e) {
			logger.error("[--离线用户提交备课信息错误--]", e);
			result = new Result(0, "请求抛出异常！》》》" + ExceptionMessage.getExceptionMassage(e), new Date(), null);
		}
		return result;
	}

	/**
	 * 离线端-我的备课本：分享备课信息接口
	 */
	@RequestMapping(value = "/shareMyPlanBook", method = RequestMethod.POST)
	@ResponseBody
	public Result shareLessonPlans(@RequestParam(value = "planid", required = true) Integer planid) {
		Result result = null;
		try {
			result = myPlanBookApiService.shareMyPlanBook(planid);
		} catch (Exception e) {
			logger.error("[--离线用户提交备课信息错误--]", e);
			result = new Result(0, "请求抛出异常！》》》" + ExceptionMessage.getExceptionMassage(e), new Date(), null);
		}
		return result;
	}

	/**
	 * 离线端-我的备课本：删除备课信息接口
	 */
	@RequestMapping(value = "/deleteMyPlanBook", method = RequestMethod.DELETE)
	@ResponseBody
	public Result deleteLessonPlans(@RequestParam(value = "planid", required = true) Integer planid, @RequestParam(value = "optime", required = false) String optime) {
		Result result = null;
		try {
			result = myPlanBookApiService.deleteMyPlanBook(planid);

		} catch (Exception e) {
			logger.error("[--离线用户提交备课信息错误--]", e);
			result = new Result(0, "请求抛出异常！》》》" + ExceptionMessage.getExceptionMassage(e), new Date(), null);
		}
		return result;
	}

	/**
	 * 离线端-我的备课本：提交给上级
	 */
	@RequestMapping(value = "/submitMyPlanBook", method = RequestMethod.POST)
	@ResponseBody
	public Result submit(@RequestParam(value = "lessonplan", required = true) String lessonplan, @RequestParam(value = "optime", required = false) String optime) {
		Result result = null;
		try {
			result = myPlanBookApiService.submitLessonPlansByIdStr(lessonplan);

		} catch (Exception e) {
			logger.error("[--离线用户提交备课信息错误--]", e);
			result = new Result(0, "请求抛出异常！》》》" + ExceptionMessage.getExceptionMassage(e), new Date(), null);
		}
		return result;
	}

	/**
	 * 离线端-我的备课本：取消提交给上级
	 */
	@RequestMapping(value = "/unSubmitMyPlanBook", method = RequestMethod.POST)
	@ResponseBody
	public Result unSubmit(@RequestParam(value = "lessonplan", required = true) String lessonplan, @RequestParam(value = "optime", required = false) String optime) {
		Result result = null;
		try {
			result = myPlanBookApiService.unSubmitLessonPlansByIdStr(lessonplan);

		} catch (Exception e) {
			logger.error("[--离线用户提交备课信息错误--]", e);
			result = new Result(0, "请求抛出异常！》》》" + ExceptionMessage.getExceptionMassage(e), new Date(), null);
		}
		return result;
	}

	/**
	 * 返回资源的最后更新的时间
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/resLastuptime", method = RequestMethod.GET)
	public Result resLastuptime(@RequestParam(value = "planid", required = true) Integer planid) {
		Result result = null;
		try {
			Map<String, Object> lastTime = myPlanBookApiService.resLastuptime(planid);
			result = new Result(1, "最后更新时间读取成功！", new Date(), lastTime);
		} catch (Exception e) {
			logger.error("[--获取最后更新时间错误--]", e);
			result = new Result(0, "请求抛出异常！》》》" + ExceptionMessage.getExceptionMassage(e), new Date(), null);
		}
		return result;
	}
	
	/**
	 * 返回我的备课本查阅意见、听课意见是否有更新
	 * 
	 * @param userid
	 * @param lessonid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getupdatestate", method = RequestMethod.GET)
	public Result getupdatestate(@RequestParam(value = "userid", required = true) Integer userid,@RequestParam(value = "lessonid", required = true) String lessonid) {
		Result result = null;
		try {
			Map<String, Object> data = myPlanBookApiService.getupdatestate(userid,lessonid);
			result = new Result(1, "获取更新状态成功！", new Date(), data);
		} catch (Exception e) {
			logger.error("[--获取更新状态错误--]", e);
			result = new Result(0, "请求抛出异常！》》》" + ExceptionMessage.getExceptionMassage(e), new Date(), null);
		}
		return result;
	}
	
	/**
	 * 修改我的备课本查阅意见、听课意见是否有更新
	 * 
	 * @param userid
	 * @param lessonid
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updatestate", method = RequestMethod.POST)
	public Result updatestate(@RequestParam(value = "userid", required = true) Integer userid,@RequestParam(value = "lessonid", required = true) String lessonid,@RequestParam(value = "type", required = true) String type) {
		Result result = null;
		try {
			result = myPlanBookApiService.updatestate(userid,lessonid,type);
		} catch (Exception e) {
			logger.error("[--更新状态错误--]", e);
			result = new Result(0, "请求抛出异常！》》》" + ExceptionMessage.getExceptionMassage(e), new Date(), null);
		}
		return result;
	}

}
