/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.dao;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.activity.bo.ActivityTracks;

 /**
 * 集备教案修改留痕表 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author wangdawei
 * @version $Id: ActivityTracks.java, v 1.0 2015-03-27 wangdawei Exp $
 */
public interface ActivityTracksDao extends BaseDAO<ActivityTracks, Integer>{

	/**
	 * 删除
	 * @param id
	 * @param editType
	 */
	void deleteActivityTracks(Integer id, Integer editType);

}