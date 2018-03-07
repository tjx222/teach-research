/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.school.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.UserMenuService;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: TeachIndexController.java, v 1.0 2015年2月11日 上午10:49:36 tmser
 *          Exp $
 */

@Controller
@RequestMapping("/jy/school/mng")
public class ManagerIndexController extends AbstractController {

	private static final Logger logger = LoggerFactory
			.getLogger(ManagerIndexController.class);

	@Resource
	private UserMenuService userMenuService;

	@Resource
	private OrganizationService organizationService;

	@RequestMapping("/index")
	public String index(@RequestParam(required = false) Integer phaseId,
			Model m) {
		UserSpace us = (UserSpace) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		SysRole role = SysRole.getRoleById(us.getSysRoleId());
		if (role != null && Integer.valueOf(0).equals(us.getPhaseId())) {
			if (phaseId == null) {
				Organization org = organizationService.findOne(us.getOrgId());
				if (org != null) {
					String id = org.getPhaseTypes();
					String[] ids = id != null ? id.split(",") : new String[] {};
					List<MetaRelationship> mlist = new ArrayList<MetaRelationship>(
							ids.length);
					for (String pid : ids) {
						try {
							if (!StringUtils.isBlank(pid))
								mlist.add(MetaUtils.getMetaRelation(
										Integer.parseInt(pid)));
						} catch (NumberFormatException e) {
							logger.error("add phase failed ", e);
						}
					}
					if (mlist.size() > 1) {
						m.addAttribute("phaseList", mlist);
						return "/uc/select";
					}
				}
			} else {
				UserSpace newUs = new UserSpace();
				try {
					BeanUtils.copyProperties(newUs, us);
					newUs.setPhaseId(phaseId);
					newUs.setPhaseType(
							(MetaUtils.getMetaRelation(phaseId)).getEid());
					newUs.setFlags("true");
				} catch (IllegalAccessException e) {
					logger.error("", e);
				} catch (InvocationTargetException e) {
					logger.error("", e);
				}
				WebThreadLocalUtils
						.setSessionAttrbitue(SessionKey.CURRENT_SPACE, newUs);
			}
		}

		return viewName("index");
	}
}
