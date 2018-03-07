/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.tmser.tr.back.service.BackIndexService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: IndexController.java, v 1.0 2015年5月29日 上午11:37:35 tmser Exp $
 */
@Controller
@RequestMapping("/jy/back")
public class IndexController extends AbstractController{
	
	@Autowired
	private BackIndexService indexService;
	
	
	@RequestMapping(value={"/","/index"})
	public String index(Model m){
		List<Organization> orgList = indexService.listOrgList();
		m.addAttribute("orgList", orgList);
		if(orgList != null && orgList.size() > 0 
				&& WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_ORG) == null){
			WebThreadLocalUtils.setSessionAttrbitue(SessionKey.CURRENT_ORG, orgList.get(0));
		}
		return viewName("index");
	}
	
	@RequestMapping("/unauthorized")
	public String unauthorized(Model m){
		return "/unauthorized";
	}
	
	@RequestMapping("/select")
	public String select(@RequestParam("orgId") Integer orgId){
		List<Organization> orgList = indexService.listOrgList();
		if(orgList != null && orgList.size() > 0 ){
			for(Organization o : orgList){
				if(o.getId().equals(orgId)){
					WebThreadLocalUtils.setSessionAttrbitue(SessionKey.CURRENT_ORG, o);
					break;
				}
			}
		}
		return null;
	}
	
}
