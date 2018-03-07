/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.plainsummary.service;

import java.util.List;

import com.tmser.tr.plainsummary.bo.PlainSummary;
import com.tmser.tr.plainsummary.bo.PlainSummaryPunishView;
import com.tmser.tr.plainsummary.vo.PlainSummaryVo;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.BaseService;

/**
 * 计划总结发布查看记录 服务类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: PlainSummaryPunishView.java, v 1.0 2015-06-30 Generate Tools Exp $
 */

public interface PlainSummaryPunishViewService extends BaseService<PlainSummaryPunishView, Integer>{
	
	/**
	 * 删除计划总结的查阅记录
	 * @param psId
	 * @return
	 */
	Integer delteView(Integer psId);
	
	/**
	 * 查询发布的计划总结
	 * @param ps
	 * @return
	 */
	PageList<PlainSummaryVo> findPunishsByPage(PlainSummary ps);
	
	/**
	 * 查询未查看的已发布计划总结
	 * @return
	 */
	Integer getUnViewNum();
	
	/**
	 * 查询已提交未查看的计划总结
	 * @param num
	 * @return
	 */
	List<PlainSummaryVo> findUnviews(Integer num);
	
	
}
