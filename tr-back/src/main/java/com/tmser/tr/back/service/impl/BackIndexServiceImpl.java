/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.back.service.BackIndexService;
import com.tmser.tr.common.bo.QueryObject;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: BackIndexServiceImpl.java, v 1.0 2015年6月13日 上午9:56:56 tmser Exp $
 */
@Service
@Transactional
public class BackIndexServiceImpl implements BackIndexService{
	
	@Autowired
	private OrganizationService organizationService;
	
	/**
	 * @return
	 * @see com.tmser.tr.back.service.BackIndexService#listMenu()
	 */
	@Override
	public List<Organization> listOrgList(){
		UserSpace cus = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		if(cus != null && !cus.getSysRoleId().equals(SysRole.ADMIN.getId())){
				Organization org = new Organization();
				org.addAlias("o");
				org.addCustomCulomn("o.id,o.name,o.shortName,o.areaId,o.areaName");
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("userId", cus.getUserId());
				org.addJoin(QueryObject.JOINTYPE.INNER, "UserManagescope u").on("u.userId = :userId and o.id = u.orgId");
				org.addCustomCondition("", paramMap);
			return organizationService.findAll(org);
		}
		return null;
	}
	
}
