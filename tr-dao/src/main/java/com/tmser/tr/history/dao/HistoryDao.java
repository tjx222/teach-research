/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.dao;

import com.tmser.tr.history.vo.SearchVo;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangdawei
 * @version $Id: HistoryDao.java, v 1.0 2016年5月30日 下午3:38:12 wangdawei Exp $
 */
public interface HistoryDao {

	Integer getActivityHistoryCount_join(SearchVo searchVo);

	/**
	 * 获得某个学年某个人的校际教研参与数
	 * @param searchVo
	 * @return
	 */
	Integer getSchoolActivityHistoryCount_join(SearchVo searchVo);


}
