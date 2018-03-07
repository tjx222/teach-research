/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.monitor.jvm.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmser.tr.back.monitor.MoitorBaseController;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: JvmMonitorController.java, v 1.0 2015年9月30日 下午4:48:07 tmser Exp $
 */
@Controller
@RequestMapping("/jy/back/monitor/jvm")
public class JvmMonitorController extends MoitorBaseController{


    @RequestMapping()
    public String index() {
        return viewName("index");
    }
    
    @RequestMapping("/front")
    public String front(Model model) {
    	model.addAttribute("src", StringUtils.isEmpty(defaultFrontWebUrl) ? "" :
    		defaultFrontWebUrl+"/jy/ws/monitor/jvm");
        return front();
    }

}
