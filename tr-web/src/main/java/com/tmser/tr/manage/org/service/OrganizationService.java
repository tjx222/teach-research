/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.org.service;

import java.util.List;

import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.common.service.BaseService;

/**
 * 机构 服务类
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: Organization.java, v 1.0 2015-03-19 tmser Exp $
 */

public interface OrganizationService extends BaseService<Organization, Integer>{

	/**
	 * 通过id连城的字符串（如：,1,22,333,）获取机构集合
	 * @param orgsJoinIds
	 * @return
	 */
	List<Organization> getOrgListByIdsStr(String orgsJoinIds);
	
	/**
	 * 
	 * @return
	 */
	void createOrganization(Organization org);

	/**
	 * 通过学校名称和区域ids检索学校
	 * @param schoolName
	 * @param areaIds
	 * @return
	 */
	public List<Organization> findOrgByNameAndAreaIds(String schoolName,String areaIds);
}
