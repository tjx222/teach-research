/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.service;

import java.util.List;

import com.tmser.tr.activity.bo.SchoolTeachCircleOrg;
import com.tmser.tr.common.service.BaseService;

/**
 * 校际教研圈附属机构 服务类
 * <pre>
 *
 * </pre>
 *
 * @author zpp
 * @version $Id: SchoolTeachCircleOrg.java, v 1.0 2015-05-12 zpp Exp $
 */

public interface SchoolTeachCircleOrgService extends BaseService<SchoolTeachCircleOrg, Integer>{

	/**
	 * 根据指定的状态，判断机构是否满足指定的教研圈
	 * @param orgId
	 * @param schoolTeachCircleId
	 * @param stateArray
	 * @return
	 */
	boolean ifMatchTheCircleByStates(Integer orgId, Integer schoolTeachCircleId,Integer[] stateArray);

	/**
	 * 获取所有参与的学校的名称
	 * @param schoolTeachCircleId
	 * @return
	 */
	String getJoinOrgNamesByCircleId(Integer schoolTeachCircleId);


	/**
	 * 获取所有参与的学校的id集合
	 * @param schoolTeachCircleId
	 * @return
	 */
	List<Integer> getJoinOrgIdsByCircleId(Integer schoolTeachCircleId);
	
	/**
	 * 获取某机构（学校）所参与的教研圈
	 * @param orgId
	 * @return
	 */
	List<Integer> getCircleByOrgId(Integer orgId);
	
}
