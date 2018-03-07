/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.ptgg.service;

import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.ptgg.bo.FlatformAnnouncement;

/**
 * 平台公告---->首页广告 服务类
 * <pre>
 * @author lijianghu
 * @date:2015-9-28
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Flatform_announcement.java, v 1.0 2015-09-28 Generate Tools Exp $
 */

public interface FlatformAnnouncementService extends BaseService<FlatformAnnouncement, Integer>{

	/**
	 * 保存首页广告
	 * @param flatformAnnouncement
	 */
	void saveHomeAds(FlatformAnnouncement flatformAnnouncement);

	/**
	 * 更新首页广告
	 * @param flatformAnnouncement
	 */
	void updatePic(FlatformAnnouncement flatformAnnouncement);

}
