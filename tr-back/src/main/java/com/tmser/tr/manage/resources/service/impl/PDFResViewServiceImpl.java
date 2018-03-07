/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.resources.service.impl;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.zhuozhengsoft.pageoffice.PDFCtrl;

/**
 * <pre>
 *
 * </pre>
 *
 * @author daweiwbs
 * @version $Id: CompositeResViewServiceImpl.java, v 1.0 2015年12月8日 上午9:30:08 tmser Exp $
 */
public class PDFResViewServiceImpl extends AbstractResViewServiceImpl{
	static final Set<String> officeExts = new HashSet<String>();
	
	static{
		officeExts.add("pdf");
		
	}
	
	
	@Autowired
	private ResourcesService resourcesService; 
	
	
	public PDFResViewServiceImpl(){
		super.setSupportExts(officeExts);
		super.setViewName("/resview/pdf_res_view");
	}
	
	/**
	 * @param res
	 * @return
	 * @see com.tmser.tr.manage.resources.service.ResViewService#choseView(com.tmser.tr.manage.resources.bo.Resources)
	 */
	@Override
	public void doView(Resources res) {
		HttpServletRequest request = WebThreadLocalUtils.getRequest();
		PDFCtrl poCtrl1 = new PDFCtrl(request);
		poCtrl1.setServerPage(request.getContextPath()+"/poserver.zz"); //此行必须
		poCtrl1.setMenubar(false);
		poCtrl1.setTitlebar(false);
		poCtrl1.setCustomToolbar(false);
		poCtrl1.setJsFunction_AfterDocumentOpened("AfterDocumentOpened()");
		//调用接口获取下载到本地的临时文件路径
		String localResPath = resourcesService.viewResources(res.getId());
		File f = new File(localResPath);
		if(f.isAbsolute() && localResPath.startsWith("/")){
			localResPath = "file://"+localResPath;
		}
		
		String ext = res.getExt().toLowerCase();
		if("pdf".equals(ext)){
			poCtrl1.webOpen(localResPath);
		}
		poCtrl1.setTagId("PDFCtrl1"); //此行必须
		
	}

}
