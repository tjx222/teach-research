/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.school.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.meta.bo.Menu;
import com.tmser.tr.manage.meta.service.MenuService;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.utils.CurrentUserContext;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: TeachIndexController.java, v 1.0 2018年2月11日 上午10:49:36 tmser
 *          Exp $
 */

@Controller
@RequestMapping("/jy/school/mng")
public class ManagerIndexController extends AbstractController {

	@Resource
	private MenuService menuService;

	@Resource
	private OrganizationService organizationService;

	@RequestMapping("/index")
	public String index(Model m) {
		User cuser = CurrentUserContext.getCurrentUser();
		Organization org = organizationService.findOne(cuser.getOrgId());
		Menu model = new Menu();
		model.setParentid(0);
		if(Organization.SCHOOL == org.getType()){
			model.setSysRoleId(Menu.TP_SCHOOL);
		}else{
			model.setSysRoleId(Menu.TP_UNIT);
		}
		List<Menu> allmenus = menuService.findAll(model);
		List<Menu> menus = new ArrayList<>();
		for (Menu menu : allmenus) {
			try {
				SecurityUtils.getSubject().checkPermission(menu.getCode());
				menus.add(menu);
			} catch (AuthorizationException e) {
				//do nothing
			}
		}
		
		m.addAttribute("menus",menus);
		return viewName("index");
	}
}
