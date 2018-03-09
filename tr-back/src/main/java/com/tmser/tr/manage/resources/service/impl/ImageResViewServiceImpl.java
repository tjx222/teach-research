/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.resources.service.impl;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.manage.resources.bo.Resources;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: CompositeResViewServiceImpl.java, v 1.0 2015年12月8日 上午9:30:08 tmser Exp $
 */
public class ImageResViewServiceImpl extends AbstractResViewServiceImpl{
	static final Set<String> officeExts = new HashSet<String>();
	
	static{
		officeExts.add("jpg");
		officeExts.add("jpeg");
		officeExts.add("png");
		officeExts.add("gif");
	}
	
	
	public ImageResViewServiceImpl(){
		super.setSupportExts(officeExts);
		super.setViewName("/resview/img_res_view");
	}
	
	/**
	 * @param res
	 * @return
	 * @see com.tmser.tr.manage.resources.service.ResViewService#choseView(com.tmser.tr.manage.resources.bo.Resources)
	 */
	@Override
	public void doView(Resources res) {
		HttpServletRequest request = WebThreadLocalUtils.getRequest();
		request.setAttribute("resid", res.getId());
	}

}