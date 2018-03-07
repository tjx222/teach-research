/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.dao.impl;

import org.springframework.stereotype.Repository;

import com.tmser.tr.activity.bo.ActivityTracks;
import com.tmser.tr.activity.dao.ActivityTracksDao;
import com.tmser.tr.common.dao.AbstractDAO;

/**
 * 集备教案修改留痕表 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author wangdawei
 * @version $Id: ActivityTracks.java, v 1.0 2015-03-27 wangdawei Exp $
 */
@Repository
public class ActivityTracksDaoImpl extends AbstractDAO<ActivityTracks,Integer> implements ActivityTracksDao {

	/**
	 * 删除
	 * @param id
	 * @param editType
	 * @see com.tmser.tr.activity.dao.ActivityTracksDao#deleteActivityTracks(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void deleteActivityTracks(Integer id, Integer editType) {
		String sql = "delete from ActivityTracks where activityId = ? and editType = ?";
		Object[] args = new Object[]{id,editType};
		update(sql, args);
	}

}
