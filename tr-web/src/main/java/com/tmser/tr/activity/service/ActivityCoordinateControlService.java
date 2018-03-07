/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.service;

import java.util.List;

/**
 * <pre>
 * 活动的整理教案协同控制Service
 * </pre>
 *
 * @author wangdawei
 * @version $Id: ActivityCoordinateControlService.java, v 1.0 2015年7月15日 下午5:22:46 wangdawei Exp $
 */
public interface ActivityCoordinateControlService {

	/**
	 * 获取主备教案资源的整理的控制权
	 * @param activityId
	 * @param userId
	 * @return
	 */
	boolean getPowerOfZhengli(String resId);

	/**
	 * 判断活动的主备教案或整理教案是否正在被整理中
	 * @param zhubeiList
	 * @param zhengliList
	 * @return
	 */
	<T> boolean mainUserIsZhengliing(List<T> zhubeiList,
			List<T> zhengliList);

	
}
