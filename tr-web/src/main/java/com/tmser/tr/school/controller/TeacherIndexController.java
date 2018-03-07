/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.school.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.tmser.tr.common.web.controller.AbstractController;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: TeachIndexController.java, v 1.0 2015年2月11日 上午10:49:36 tmser Exp $
 */

@Controller
@RequestMapping("/jy/school/tch")
public class TeacherIndexController extends AbstractController{
	
	@RequestMapping("/index")
	public String index(Model m){
		return viewName("index");
	}

}
