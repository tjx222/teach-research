/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.plainsummary.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tmser.tr.common.dao.AbstractDAO;
import com.tmser.tr.plainsummary.bo.PlainSummaryPunishView;
import com.tmser.tr.plainsummary.dao.PlainSummaryPunishViewDao;

/**
 * 计划总结发布查看记录 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: PlainSummaryPunishView.java, v 1.0 2015-06-30 Generate Tools Exp $
 */
@Repository
public class PlainSummaryPunishViewDaoImpl extends AbstractDAO<PlainSummaryPunishView,Integer> implements PlainSummaryPunishViewDao {

	@Override
	public Integer delteView(Integer psId) {
		String sql = "delete from PlainSummaryPunishView where plain_summary_id=:psId";
		Map<String, Object> argMap = new HashMap<String, Object>();
		argMap.put("psId", psId);
		return this.updateWithNamedSql(sql, argMap);
	}

	@Override
	public List<PlainSummaryPunishView> findView(Integer currentUserId,
			List<Integer> psIds) {
		String sql = "select * from PlainSummaryPunishView v where v.userId=:currentUserId and v.plainSummaryId in (:psIds)";
		Map<String, Object> argMap = new HashMap<String, Object>();
		argMap.put("currentUserId", currentUserId);
		argMap.put("psIds", psIds);
		return this.queryByNamedSql(sql, argMap,this.mapper);
	}

}
