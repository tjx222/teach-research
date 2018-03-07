/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.org.service;

import java.util.List;
import java.util.Map;

import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.common.service.BaseService;

/**
 * 机构 服务类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author tmser
 * @version $Id: Organization.java, v 1.0 2015-03-19 tmser Exp $
 */

public interface OrganizationService extends BaseService<Organization, Integer> {

	/**
	 * 通过学校名称检索学校
	 * 
	 * @param schoolName
	 * @return
	 */
	List<Organization> findOrgByName(String schoolName);

	/**
	 * 通过父级区域查找学校id集合
	 * 
	 * @param areaId
	 * @param type
	 * @return
	 */
	List<Integer> getOrgIdsByAreaId(Integer areaId);

	/**
	 * 通过id连城的字符串（如：,1,22,333,）获取机构集合
	 * 
	 * @param orgsJoinIds
	 * @return
	 */
	List<Organization> getOrgListByIdsStr(String orgsJoinIds);

	/**
	 * 通过区域ID查找学校机构集合
	 * 
	 * @param areaId
	 * @param type
	 * @return
	 */
	List<Organization> getOrgByAreaId(Integer areaId, Integer type);

	/**
	 * 通过名称检索出所有学校
	 * 
	 * @param name
	 * @param type
	 * @return
	 */
	Map<String, Object> getAllOrgByName(String name, Integer type);
	
	
	/**
	 * 获取学校包含的学段列表
	 * @param orgId
	 * @return
	 */
   List<MetaRelationship> listPhasebyOrgId(Integer orgId);

	/**
	 * @param org
	 */
	void createOrganization(Organization org);

}
