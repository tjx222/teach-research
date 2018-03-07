/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.service;

import java.util.List;

import com.tmser.tr.manage.org.bo.Organization;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: BackIndexService.java, v 1.0 2015年6月13日 上午9:56:28 tmser Exp $
 */
public interface BackIndexService {

	/**
	 * 展示当前用户菜单列表
	 * @return
	 */
	List<Organization> listOrgList();
}
