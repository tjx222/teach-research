/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.xxsy.redhm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.back.xxsy.redhm.service.RedHeadManageService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.redheadmanage.bo.RedHeadManage;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * 红头管理控制器接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: RedHeadManage.java, v 1.0 2015-08-26 Generate Tools Exp $
 */
@Controller
@RequestMapping("/jy/back/xxsy/redhm")
public class RedHeadManageController extends AbstractController{
     
	@Autowired
	private RedHeadManageService redHeadManageService;
	
	@Autowired
	private OrganizationService organizationService;
	
	/*
	 * 显示红头列表
	*/
	@RequestMapping("/index")
	public String index(Model m,Integer orgId){
		RedHeadManage rhm=new RedHeadManage();
		if (orgId==null) {
			Organization o = (Organization) WebThreadLocalUtils
					.getSessionAttrbitue(SessionKey.CURRENT_ORG);
			if(o==null){
				//模块列表url
				m.addAttribute("orgurl", "/jy/back/xxsy/redhm/index?orgId=");
				//模块加载divId
				m.addAttribute("divId", "redHead");
				logger.error("红头管理获取学校ID信息失败");
				return "/back/xxsy/selectOrg";
			}else{
				rhm.setOrgId(o.getId());
				m.addAttribute("orgName", o.getShortName());
			}
		}else {
			rhm.setOrgId(orgId);
			m.addAttribute("orgName", organizationService.findOne(orgId).getShortName());
			m.addAttribute("orgId", orgId);
		}
		List<RedHeadManage> rhmList=redHeadManageService.getredhmlist(m,rhm);
		m.addAttribute("rhmList", rhmList);
		//m.addAttribute("currentIndex", 0);
		return viewName("redheadindex");
	}
	
	/*
	 * 增加或者编辑红头
	*/
	@RequestMapping("/addOrEditRedHead")
	public String addOrEditRedHead(Integer id,Model m,Integer orgId){
		if (id!=null) {
			RedHeadManage rhm= redHeadManageService.updateRedHead(id);
			m.addAttribute("title",  rhm.getTitle());
		}
		m.addAttribute("id", id);
		m.addAttribute("orgId", orgId);
		return viewName("addOrEditRedHead");
	}
	
	/*
	 * 保存红头标题
	*/
	@RequestMapping("/saveRedHead")
	@ResponseBody
	public JuiResult saveRedHead(RedHeadManage rhm){
		JuiResult juiResult=new JuiResult();
		try {
			if (rhm.getId()!=null) {
				juiResult.setMessage("修改成功");
			}else {
				juiResult.setMessage("保存成功");
			}
			juiResult.setNavTabId("redhead");
			redHeadManageService.updateOrSaveRedHead(rhm);
		} catch (Exception e) {
			// TODO: handle exception
			juiResult.setStatusCode(JuiResult.FAILED);
			if (rhm.getId()!=null) {
				juiResult.setMessage("修改失败");
			}else {
				juiResult.setMessage("保存失败");
			}
			logger.error("红头标题保存失败");
		}
		return juiResult ;
	}
	
	/*
	 * 删除红头标题
	*/
	@RequestMapping("/deleteRedHead")
	@ResponseBody
	public JuiResult deleteRedHead(Integer id,Model m){
		JuiResult juiResult=new JuiResult();
		try {
			redHeadManageService.deleteRedHead(id);
		    juiResult.setMessage("删除成功");
		    juiResult.setCallbackType("");
		    juiResult.setNavTabId("redhead");
		} catch (Exception e) {
			// TODO: handle exception
			juiResult.setStatusCode(JuiResult.FAILED);
			juiResult.setMessage("删除失败");
			logger.error("红头标题删除失败");
		}
		return juiResult ;
	}
	
}