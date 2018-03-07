/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.plainsummary.dao;

import java.util.List;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.plainsummary.bo.PlainSummaryPunishView;

 /**
 * 计划总结发布查看记录 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: PlainSummaryPunishView.java, v 1.0 2015-06-30 Generate Tools Exp $
 */
public interface PlainSummaryPunishViewDao extends BaseDAO<PlainSummaryPunishView, Integer>{
	
	/**
	 * 删除计划总结查看记录
	 * @param psId
	 * @return
	 */
	Integer delteView(Integer psId);
	
	/**
	 * 查询当前用户下计划总结的查看情况
	 * @param currentUserId
	 * @param psIds
	 * @return
	 */
	List<PlainSummaryPunishView> findView(Integer currentUserId,
			List<Integer> psIds);

}