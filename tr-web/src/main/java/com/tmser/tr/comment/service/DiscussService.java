/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.comment.service;

import java.util.List;
import java.util.Map;

import com.tmser.tr.comment.bo.Discuss;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.BaseService;

/**
 * 区域教研中各活动的讨论 服务类
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: AreaActivityDiscussService.java, v 1.0 2015-05-29 tmser Exp $
 */

public interface DiscussService extends BaseService<Discuss, Integer>{

	/**
	 * 保存区域校际教研活动的发布的讨论信息
	 * @param aad
	 * @return
	 */
	String saveDiscuss(Discuss aad);

	/**
	 * 查询活动下面的讨论列表
	 * @param aad
	 * @return
	 */
	PageList<Discuss> discussIndex(Discuss aad,String searchName);
	
	
	
	/**
	 * 按类型统计活动讨论数
	 * @param activeType
	 * @param activeIds
	 * @return
	 */
	long countDiscussByType(Integer activeType, List<Integer> activeIds);
	
	
	/**
	 * 按类型统计活动参与人数
	 * @param activeType
	 * @param activeIds
	 * @return
	 */
	long countDiscussByUser(Integer activeType, List<Integer> activeIds);
	
	/**
	 * 批量保存讨论内容
	 */
	void batchSave(List<Discuss> discussList);
	
	/**
	 * 按活动获取讨论列表
	 * @param discuss
	 * @return
	 */
	PageList<Discuss> getDiscussByActiveId(Discuss discuss);

	/**
	 * 通过用户、类型和资源id集合查询出以资源id为key的一个map
	 * @param userId
	 * @param resType
	 * @param ids
	 * @return
	 */
	Map<Integer, List<Discuss>> findDiscussMap(Integer userId, Integer resType,List<Integer> ids);
	
}
