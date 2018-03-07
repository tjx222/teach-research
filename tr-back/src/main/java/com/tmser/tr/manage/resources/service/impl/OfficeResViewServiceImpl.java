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
import com.zhuozhengsoft.pageoffice.OfficeVendorType;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: CompositeResViewServiceImpl.java, v 1.0 2015年12月8日 上午9:30:08 tmser Exp $
 */
public class OfficeResViewServiceImpl extends AbstractResViewServiceImpl{
	static final Set<String> officeExts = new HashSet<String>();
	
	static{
		officeExts.add("doc");
		officeExts.add("docx");
		officeExts.add("xls");
		officeExts.add("xlsx");
		officeExts.add("ppt");
		officeExts.add("pptx");
		officeExts.add("wps");
		officeExts.add("dps");
		officeExts.add("et");
	}
	
	
	@Autowired
	private ResourcesService resourcesService; 
	
	
	public OfficeResViewServiceImpl(){
		super.setSupportExts(officeExts);
		super.setViewName("/resview/office_res_view");
	}
	
	/**
	 * @param res
	 * @return
	 * @see com.tmser.tr.manage.resources.service.ResViewService#choseView(com.tmser.tr.manage.resources.bo.Resources)
	 */
	@Override
	public void doView(Resources res) {
		HttpServletRequest request = WebThreadLocalUtils.getRequest();
		PageOfficeCtrl poc = new PageOfficeCtrl(request);
		poc.setServerPage(request.getContextPath()+"/poserver.zz");
		poc.setMenubar(false);//不显示菜单栏
		poc.setOfficeToolbars(false);//不显示office工具栏
		poc.setTitlebar(false);//不显示标题栏
		poc.setCustomToolbar(false);//不显示自定义的工具栏
		poc.setOfficeVendor(OfficeVendorType.AutoSelect);
		
		//调用接口获取下载到本地的临时文件路径
		String localResPath = resourcesService.viewResources(res.getId());
		File f = new File(localResPath);
		if(f.isAbsolute() && localResPath.startsWith("/")){
			localResPath = "file://"+localResPath;
		}
		
		String ext = res.getExt().toLowerCase();
		if("doc".equals(ext) || "docx".equals(ext) || "wps".equals(ext)){
			poc.webOpen(localResPath, OpenModeType.docReadOnly, "");
		}else if("ppt".equals(ext) || "pptx".equals(ext) || "dps".equals(ext)){
			poc.webOpen(localResPath, OpenModeType.pptNormalEdit, "");
		}else if("xls".equals(ext) || "xlsx".equals(ext) || "et".equals(ext)){
			poc.webOpen(localResPath, OpenModeType.xlsReadOnly, "");
		}
		poc.setTagId("PageOfficeCtrl1");//此行必需
		
	}

}
