/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.api.base.service;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 *	移动离线端基础接口服务。
 * </pre>
 *
 * @author tmser
 * @version $Id: MobileBasicService.java, v 1.0 2016年4月11日 下午1:52:23 tmser Exp $
 */
public interface MobileBasicService {
	/**
	 * 移动教研平台离线端请求在线登录成功后，同步用户基础信息。
	 * 
	 * @param userid
	 * @return
	 */
	Map<String, Object> getBaseUserInfo(Integer userid);

	/**
	 * 移动教研平台离线端请求在线登录成功后，同步机构-学科\年级\用户\的信息。
	 * 
	 * @param orgid
	 * @return
	 */
	Map<String, Object> getOrgInfo(Integer orgid);

	/**
	 * 移动教研平台离线端请求在线登录成功后，同步用户目录章节信息。
	 * 
	 * @param userid
	 * @return
	 */
	List<Map<String, Object>> getBookInfo(Integer userid);

	/**
	 * 获得当前机构下所用到的书籍数据集合
	 * 
	 * @param orgid
	 * @return
	 * 
	 */
	Map<String, Object> getOrgBooksInfo(Integer orgid);

}
