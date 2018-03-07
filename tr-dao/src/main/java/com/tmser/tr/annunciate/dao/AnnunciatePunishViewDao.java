/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.annunciate.dao;

import java.util.List;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.annunciate.bo.AnnunciatePunishView;

 /**
 * 通告发布查看 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: AnnunciatePunishView.java, v 1.0 2015-07-01 Generate Tools Exp $
 */
public interface AnnunciatePunishViewDao extends BaseDAO<AnnunciatePunishView, Integer>{
	
	/**
	 * 查询用户查看通告情况
	 * @param userId
	 * @param annunciateIds
	 * @return
	 */
	List<AnnunciatePunishView> findUserView(Integer userId,List<Integer> annunciateIds);
	
	/**
	 * 删除通告的查看记录
	 * @param annunciateId
	 */
	void deleteView(Integer annunciateId);

}