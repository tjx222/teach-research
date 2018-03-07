/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.resources.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tmser.tr.common.utils.CookieUtils;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResViewService;
import com.tmser.tr.manage.resources.service.ResourcesService;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: ResViewController.java, v 1.0 2015年12月7日 下午2:32:50 tmser Exp $
 */
@Controller
@RequestMapping("/jy/")
public class ResViewController extends AbstractController {
	
	@Autowired
	private ResourcesService resourcesService; //资源service
	
	@Autowired
	private ResViewService resViewService;
	
  
	@RequestMapping("scanResFile")
    public String scanRes(@RequestParam(value="resId",required=true)String resId,Model m){
	 
	  Resources resources = resourcesService.findOne(resId);
      if(resources!=null){
        return resViewService.choseView(resources);
      }
      return null;
  }
	
}
