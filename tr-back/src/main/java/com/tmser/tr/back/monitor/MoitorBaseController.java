/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.monitor;

import org.springframework.beans.factory.annotation.Value;

import com.tmser.tr.common.web.controller.AbstractController;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: MoitorBaseController.java, v 1.0 2015年11月10日 上午10:47:57 tmser Exp $
 */
public abstract class MoitorBaseController extends AbstractController{

	@Value("#{config.getProperty('front_web_url','')}")
	protected String defaultFrontWebUrl;
	
	protected String front(){
		return "/back/monitor/front";
	}
}
