/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.xxsy.schoolbanner.service;

import com.tmser.tr.xxsy.bannermanner.bo.SchoolBanner;
import com.tmser.tr.common.service.BaseService;

/**
 * 学校横幅广告 服务类
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: SchoolBanner.java, v 1.0 2015-10-28 tmser Exp $
 */

public interface SchoolBannerService extends BaseService<SchoolBanner, Integer>{

	/**
	 * 保存或更新横幅广告
	 * @param schoolBanner
	 */
	void saveSchoolBanner(SchoolBanner schoolBanner);
	/**
	 * 
	 * @param schoolBanner
	 * 功能：显示横幅广告，操作其他数据
	 */
	void updateSchoolBanner(SchoolBanner schoolBanner);

}
