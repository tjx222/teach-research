/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.dao;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.activity.bo.SchoolActivityTracks;

 /**
 * 校际教研教案修改留痕 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author wangdawei
 * @version $Id: SchoolActivityTracks.java, v 1.0 2015-05-28 wangdawei Exp $
 */
public interface SchoolActivityTracksDao extends BaseDAO<SchoolActivityTracks, Integer>{

	/**
	 * 删除
	 * @param id
	 * @param editType
	 */
	void deleteActivityTracks(Integer activityId, Integer editType);

}