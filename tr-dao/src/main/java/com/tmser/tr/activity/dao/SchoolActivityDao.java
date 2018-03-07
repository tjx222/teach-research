/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.dao;

import java.util.List;
import java.util.Map;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.activity.bo.SchoolActivity;

 /**
 * 校际教研活动表 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author zpp
 * @version $Id: SchoolActivity.java, v 1.0 2015-05-20 zpp Exp $
 */
public interface SchoolActivityDao extends BaseDAO<SchoolActivity, Integer>{

	/**
	 * 查询用户作为临时的专家
	 * @param userName
	 * @return
	 */
	List<Map<String, Object>> findUserObjectByName(String userName);

	/**
	 * 查询专家用户，通过ids
	 * @param expertIds
	 * @return
	 */
	List<Map<String, Object>> findUserObjectByIds(String expertIds);

	/**
	 * 参加集体备课活动数
	 * @param userSpace
	 * @param term
	 * @param schoolYear 
	 */
	Integer countSchoolActivity(UserSpace userSpace,Integer schoolYear,Integer term);
	
	/**
	 * 参加集体备课活动数
	 * @param userSpace
	 * @param schoolYear 
	 */
	Integer countJoinSchoolActivity(UserSpace userSpace,Integer schoolYear);
}