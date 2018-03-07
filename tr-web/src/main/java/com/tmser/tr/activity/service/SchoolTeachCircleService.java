/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.service;

import java.util.List;

import com.tmser.tr.activity.bo.SchoolTeachCircle;
import com.tmser.tr.activity.vo.SchoolTeachCircleVo;
import com.tmser.tr.common.service.BaseService;

/**
 * 校际教研圈 服务类
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: SchoolTeachCircle.java, v 1.0 2015-05-12 Generate Tools Exp $
 */

public interface SchoolTeachCircleService extends BaseService<SchoolTeachCircle, Integer> {

	/**
	 * 保存校际检验圈
	 * 
	 * @param stc
	 * @param circleOrgs
	 */
	String saveCircle(SchoolTeachCircle stc, String circleOrgs);

	/**
	 * 查询校际教研圈
	 * 
	 * @return
	 */
	List<SchoolTeachCircle> findAllCircleByOrg();

	/**
	 * 查看校际教研圈
	 * 
	 * @return
	 */
	List<SchoolTeachCircle> findCircleByOrg();

	/**
	 * 删除校际教研圈
	 * 
	 * @param stc
	 */
	void deleteCircle(SchoolTeachCircle stc);

	/**
	 * 退出或者恢复教研圈
	 * 
	 * @param stc
	 * @param i
	 */
	void setCircleOrgState(SchoolTeachCircle stc, int i);

	/**
	 * 校际教研圈邀请回应操作
	 * 
	 * @param state
	 * @param stcId
	 * @return
	 */
	Boolean saveYaoQing(Integer state, Integer stcId, Long noticeId, String content);

	/**
	 * 查看校际教研圈
	 * 
	 * @return
	 */
	List<SchoolTeachCircle> lookTeachCircle();

	/**
	 * 获取当前学校参加的教研圈
	 * 
	 * @return
	 */
	List<SchoolTeachCircleVo> getCurrentSchoolJoinCicles();

	/**
	 * 判断教研圈名称是否重复
	 * 
	 * @param stc
	 * @return
	 */
	String checkCircleName(SchoolTeachCircle stc);

}
