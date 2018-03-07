/**
 * Tmser.com Inc.
 * Copyright (c) 2016-2018 All Rights Reserved.
 */
package com.tmser.tr.webservice.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.lecturerecords.bo.LectureRecords;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.plainsummary.bo.PlainSummary;
import com.tmser.tr.recordbag.bo.Recordbag;
import com.tmser.tr.webservice.service.JxkhService;
import com.tmser.tr.webservice.vo.DataResult;
import com.tmser.tr.webservice.vo.JxParams;

/**
 * <pre>
 *  绩效考核系统数据采集通道
 * </pre>
 *
 * @author tmser
 * @version $Id: JxkhResController.java, v 1.0 2016年3月1日 上午10:27:31 zpp Exp $
 */
@Controller
@RequestMapping("/jy/jx")
public class JxkhResController extends AbstractController{
	
	
	@Resource
	private JxkhService jxkhService;
	
	/**
	 * 针对于“教案”模块收集的数据为各身份各模块的数据之和
	 * @param jp
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getJiaoAnData")
	public DataResult getJiaoAnData(JxParams jp) {
		DataResult result = jxkhService.getBeiKeRes(jp,ResTypeConstants.JIAOAN,new LessonPlan());
		return result;
	}
	
	/**
	 * 针对于“课件”模块收集的数据为各身份各模块的数据之和
	 * @param jp
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getKenJianData")
	public DataResult getKenJianData(JxParams jp) {
		DataResult result = jxkhService.getBeiKeRes(jp,ResTypeConstants.KEJIAN,new LessonPlan());
		return result;
	}
	
	/**
	 * 针对于“反思”模块收集的数据为各身份各模块的数据之和
	 * @param jp
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getFanSiData")
	public DataResult getFanSiData(JxParams jp) {
		DataResult result = jxkhService.getBeiKeRes(jp,ResTypeConstants.FANSI,new LessonPlan());
		return result;
	}
	/**
	 * 针对于听课记录模块收集的数据为各身份各模块的数据之和
	 * @param jp
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getLectureData")
	public DataResult getLectureData(JxParams jp) {
		DataResult result = jxkhService.getLectureRes(jp,ResTypeConstants.LECTURE,new LectureRecords());
		return result;
	}
	/**
	 * 针对于个人总结（只收集个人总结的数据）
	 * @param jp
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getPlainSummarys")
	public DataResult getPlainSummarys(JxParams jp) {
		DataResult result = jxkhService.getPlainSummarys(jp,ResTypeConstants.TPPLAIN_SUMMARY_SUMMARY,new PlainSummary());
		return result;
	}
	/**
	 * 针对于个人计划（只收集个人计划的数据）
	 * @param jp
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getPersonPlanData")
	public DataResult getPersonPlanData(JxParams jp) {
		DataResult result = jxkhService.getPlainSummarys(jp,ResTypeConstants.TPPLAIN_SUMMARY_PLIAN,new PlainSummary());
		return result;
	}
	/**
	 * 针对于“成长档案袋”模块收集的数据为该用户已精选的全部资源内容
	 * @param jp
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getRecordBagData")
	public DataResult getRecordBagData(JxParams jp) {
		DataResult result = jxkhService.getRecords(jp,new Recordbag());
		return result;
	}
	/**
	 * 针对于集体备课
	 * @param jp
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getActivityData")
	public DataResult getActivityData(JxParams jp) {
		DataResult result = jxkhService.getActivityData(jp,null);
		return result;
	}
	/**
	 * 针对于“集体担任主备人”收集的数据为该用户各身份参与的集体备课中担任主备人的数量之和
	 * @param jp
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getJiTiBeiKeMainData")
	public DataResult getJiTiBeiKeMainData(JxParams jp) {
		DataResult result = jxkhService.getActivityMainUser(jp,null);
		return result;
	}
	/**
	 * 针对于校际教研模块收集的数据为该用户可参加的活动数量
	 * @param jp
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getSchoolActivity")
	public DataResult getSchoolActivity(JxParams jp) {
		DataResult result = jxkhService.getSchoolActivity(jp,null);
		return result;
	}
	/**
	 * 针对于校际教研模块收集的数据为该用户可参加的活动数量
	 * @param jp
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getWebShare")
	public DataResult getWebShare(JxParams jp) {
		DataResult result = jxkhService.getWebShareDatas(jp);
		return result;
	}
	
	
	
}
