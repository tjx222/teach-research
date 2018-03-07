/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.dao.impl;

import org.springframework.stereotype.Repository;

import com.tmser.tr.common.dao.AbstractDAO;
import com.tmser.tr.activity.bo.SchoolActivityTracks;
import com.tmser.tr.activity.dao.SchoolActivityTracksDao;

/**
 * 校际教研教案修改留痕 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author wangdawei
 * @version $Id: SchoolActivityTracks.java, v 1.0 2015-05-28 wangdawei Exp $
 */
@Repository
public class SchoolActivityTracksDaoImpl extends AbstractDAO<SchoolActivityTracks,Integer> implements SchoolActivityTracksDao {

	/**
	 * 删除
	 * @param activityId
	 * @param editType
	 * @see com.tmser.tr.activity.dao.SchoolActivityTracksDao#deleteActivityTracks(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void deleteActivityTracks(Integer activityId, Integer editType) {
		String sql = "delete from SchoolActivityTracks where activityId = ? and editType = ?";
		Object[] args = new Object[]{activityId,editType};
		update(sql, args);
	}

}
