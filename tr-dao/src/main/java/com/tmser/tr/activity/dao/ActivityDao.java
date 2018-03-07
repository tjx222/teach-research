/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.dao;

import com.tmser.tr.activity.bo.Activity;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.uc.bo.UserSpace;

 /**
 * 集体备课活动实体 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Activity.java, v 1.0 2015-03-06 Generate Tools Exp $
 */
public interface ActivityDao extends BaseDAO<Activity, Integer>{
	
	/**
	 * 参加集体备课活动数
	 * @param userSpace
	 * @param term
	 * @param schoolYear
	 */
   Integer countActivity(UserSpace userSpace,Integer term,Integer schoolYear);
   
   /**
    *  参加集体备课活动数
	 * @param userSpace
	 * @param schoolYear
	 */
   Integer countJoinActivity(UserSpace userSpace,Integer schoolYear);
}