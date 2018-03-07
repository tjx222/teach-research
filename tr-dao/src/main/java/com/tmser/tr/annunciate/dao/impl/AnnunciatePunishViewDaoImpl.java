/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.annunciate.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tmser.tr.common.dao.AbstractDAO;
import com.tmser.tr.annunciate.bo.AnnunciatePunishView;
import com.tmser.tr.annunciate.dao.AnnunciatePunishViewDao;

/**
 * 通告发布查看 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: AnnunciatePunishView.java, v 1.0 2015-07-01 Generate Tools Exp $
 */
@Repository
public class AnnunciatePunishViewDaoImpl extends AbstractDAO<AnnunciatePunishView,Integer> implements AnnunciatePunishViewDao {

	@Override
	public List<AnnunciatePunishView> findUserView(Integer userId,
			List<Integer> annunciateIds) {
		String sql = "select * from AnnunciatePunishView v where v.userId = :userId and v.annunciateId in (:annunciateIds)";
		Map<String , Object> argMap = new HashMap<String , Object>();
		argMap.put("userId", userId);
		argMap.put("annunciateIds", annunciateIds);
		return this.queryByNamedSql(sql, argMap,this.mapper);
	}

	@Override
	public void deleteView(Integer annunciateId) {
		String sql = "delete from AnnunciatePunishView v where v.annunciateId=:annunciateId";
		Map<String , Object> argMap = new HashMap<String , Object>();
		argMap.put("annunciateId", annunciateId);
		this.updateWithNamedSql(sql, argMap);
	}

}
