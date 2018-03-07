/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.plainsummary.service;

import java.util.List;

import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.plainsummary.bo.PlainSummary;

/**
 * 计划总结 服务类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: PlainSummary.java, v 1.0 2015-03-19 Generate Tools Exp $
 */

public interface PlainSummaryService extends BaseService<PlainSummary, Integer>{
	/**
	 * 分享
	 * @param psId
	 */
	void share(Integer psId);
	
	/**
	 * 取消分享
	 * @param psId
	 */
	void cancelShare(Integer psId);
	
	/**
	 * 发布
	 * @param psId
	 */
	void punish(Integer psId,Integer punishRange,Integer schoolCircleId);
	
	/**
	 * 取消发布
	 * @param psId
	 */
	void cancelPunsih(Integer psId);
	
	/**
	 * 提交
	 * @param psId
	 */
	void submit(Integer psId);
	
	/**
	 * 取消提交
	 * @param psId
	 */
	void cancelSubmit(Integer psId);
	
	/**
	 * 编辑
	 * @param ps
	 */
	void edit(PlainSummary ps);
	
	/**
	 * 获取上一篇发布的计划总结
	 * @param psId
	 * @return
	 */
	PlainSummary getPrePunishPlanSummary(Integer psId);
	
	/**
	 * 下一篇发布的计划总结
	 * @param psId
	 * @return
	 */
	PlainSummary getNextPunishPlanSummary(Integer psId);
	
	/**
	 * 获取提交计划总结资源除了当前人的其他人列表
	 * @param ps
	 * @return
	 */
	List<PlainSummary> resourceSubmitOthers(PlainSummary ps);
	
}
