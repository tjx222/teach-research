/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.companion.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.companion.bo.JyCompanion;
import com.tmser.tr.companion.vo.JyCompanionSearchVo;
import com.tmser.tr.companion.vo.JyCompanionVo;

/**
 * 同伴互助 服务类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: JyCompanion.java, v 1.0 2015-05-20 Generate Tools Exp $
 */

public interface JyCompanionService extends BaseService<JyCompanion, Integer>{

	/**
	 * f分页查询同伴列表
	 * @param searchVo
	 * @return
	 */
	public PageList<JyCompanionVo> findCompanions(JyCompanionSearchVo searchVo);

	/**
	 * 获取用户所有好友信息
	 * @return
	 */
	public List<JyCompanionVo> findAllFriends();

	/**
	 * 查询用户最近联系人
	 * @return
	 */
	public List<JyCompanionVo> findLatestConmunicateCompanions();

	/**
	 * 查询同伴详情
	 * @return
	 */
	public JyCompanionVo findCompanionDetail(Integer userIdCompanion);

	/**
	 * 将同伴加为好友
	 * @param userIdCompanion
	 * @return
	 */
	public void addFriend(Integer userIdCompanion);

	/**
	 * 将同伴从好友关系中去除
	 * @param userIdCompanion
	 * @return
	 */
	public void deleteFriend(Integer userIdCompanion);

	/**
	 * 新增同伴关系
	 * @param userIdCompanion
	 * @param isFriend
	 */
	public void addCompanion(Integer userIdCompanion,Boolean isFriend,Date latestComunicateTime);

	/**
	 * 查找用户简介信息
	 * @param userIds
	 * @return
	 */
	public List<JyCompanionVo> findUserSimpleInfos(List<Integer> userIds);

	/**
	 * 同伴互助我的关注用户信息
	 * @param username
	 * @param iscare
	 * @return
	 */
	public Map<String, Object> getMyCareFriend(String username,Boolean iscare);

	/**
	 * 同伴的信息和同伴的分享
	 * @param selfinfo
	 * @param companionId
	 * @return
	 */
	public Map<String, Object> getCompanionShares(Boolean selfinfo,
			Integer companionId);
}
