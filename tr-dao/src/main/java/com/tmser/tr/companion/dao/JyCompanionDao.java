/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.companion.dao;

import java.util.Date;
import java.util.List;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.companion.bo.JyCompanion;
import com.tmser.tr.companion.vo.JyCompanionSearchVo;
import com.tmser.tr.companion.vo.JyCompanionVo;

/**
 * 同伴互助 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: JyCompanion.java, v 1.0 2015-05-20 Generate Tools Exp $
 */
public interface JyCompanionDao extends BaseDAO<JyCompanion, Integer>{

	/**
	 * 验证并返回是朋友关系的id
	 * @param currentUserId
	 * @param copanionIds
	 * @return
	 */
	List<Integer> validFriendRelation(Integer currentUserId,
			List<Integer> copanionIds);

	/**
	 * 查询本学期联系过的人员
	 * @param currentUserId
	 * @param startDate
	 * @return
	 */
	List<JyCompanionVo> findLatestConmunicateCompanions(Integer currentUserId,
			Date startDate);

	/**
	 * 同伴列表查询
	 * @param searchVo
	 * @return
	 */
	PageList<Integer> findUserIds(JyCompanionSearchVo searchVo);

	/**
	 * 新增同伴关系
	 * @param companion
	 */
	void addCompanion(JyCompanion companion);

	/**
	 * 将同伴设置为好友
	 * @param currentUserId
	 * @param userIdCompanion
	 * @return
	 */
	Integer updateCompanionToFriend(Integer currentUserId,
			Integer userIdCompanion);

	/**
	 * 将同伴从好友关系中去除
	 * @param currentUserId
	 * @param userIdCompanion
	 */
	void deleteCompanion(Integer currentUserId, Integer userIdCompanion);

	/**
	 * 更新最近联系时间
	 * @param currentUserId
	 * @param userIdCompanion
	 * @return
	 */
	Integer updateLastCommunicateTime(Integer currentUserId,
			Integer userIdCompanion);


}