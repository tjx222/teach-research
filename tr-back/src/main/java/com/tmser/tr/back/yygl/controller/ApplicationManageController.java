package com.tmser.tr.back.yygl.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.meta.bo.Icon;
import com.tmser.tr.manage.meta.bo.Menu;
import com.tmser.tr.manage.meta.service.IconService;
import com.tmser.tr.manage.meta.service.MenuService;

/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
 
/**
 * 应用管理控制器接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: ApplicationManage.java, v 1.0 2015-09-02 Generate Tools Exp $
 */
@Controller
@RequestMapping("/jy/back/yygl")
public class ApplicationManageController extends AbstractController{
	@Autowired 
	private MenuService menuService;
	
	@Autowired 
	private IconService icoService;
	
	@Value("#{config.getProperty('front_web_url','')}")
	private String front_web_url;
	
	@RequestMapping("/index")
	public String getIndex(Menu model, Model m){
		model.setParentid(0);
		String searchName = null;
		if(!StringUtils.isEmpty(model.getName())){
			searchName = model.getName();
			model.setName(SqlMapping.LIKE_PRFIX+searchName+SqlMapping.LIKE_PRFIX);
		}
		List<Menu> amlist = menuService.findAll(model);
		m.addAttribute("amlist", amlist);
		model.setName(searchName);
		m.addAttribute("model", model);
		return viewName("index");
	}
	
	@RequestMapping("/listico")
	public String listico(Model m){
		Icon ico = new Icon();
		ico.setWidth(70);
		ico.setHeight(69);
		ico.setUserId(0);
		ico.addCustomCulomn("id,imgSrc,description");
		m.addAttribute("icolist", icoService.findAll(ico));
		m.addAttribute("basepath",front_web_url);
		return viewName("listico");
	}
	
	/*
	 * 查看子模块
	*/
	@RequestMapping("/viewSon/{id}")
	public String appManageSonView(@PathVariable(value="id")Integer id,Model m){
		Menu model = new Menu();
		model.setParentid(id);
		List<Menu> amlist = menuService.findAll(model);
		m.addAttribute("amlist", amlist);
		return "/back/yygl/son/sonIndex";
	}
	
	/*
	 * 新建模块或修改模块
	*/
	@RequestMapping("/addOrEdit/{id}")
	public String addOrEditAppManage(@PathVariable(value="id")Integer id,Model m){
		if (id != -1) {
			Menu aManage=menuService.findOne(id);
			m.addAttribute("aManage", aManage);
		}
		return viewName("addOrEditApplicationManage");
	}
	
	/*
	 * 新建子模块或修改子模块
	*/
	@RequestMapping("/addSon/{pid}")
	public String addOrEditAppManageSon(@PathVariable(value="pid")Integer pid,Model m){
			
		if(pid != -1){
			Menu ap = menuService.findOne(pid);
			m.addAttribute("ap", ap);
		}
	
		return "/back/yygl/son/addOrEditAppManageSon";
	}
	
	/*
	 * 新建子模块或修改子模块
	*/
	@RequestMapping("/editSon/{id}")
	public String editAppManageSon(@PathVariable(value="id")Integer id,Model m){
			
		if(id != -1){
			Menu aManage= menuService.findOne(id);
			m.addAttribute("aManage", aManage);
			Menu ap = menuService.findOne(aManage.getParentid());
			m.addAttribute("ap", ap);
		}
	
		return "/back/yygl/son/addOrEditAppManageSon";
	}
	
	/*
	 * 保存或更新模块
	*/
	@RequestMapping("/save")
	@ResponseBody
	public JuiResult saveAppManage(Menu am,Model m){
		JuiResult juiResult=new JuiResult();
		try {
			if (am.getId()!=null) {
				juiResult.setMessage("修改成功");
			}else {
				juiResult.setMessage("保存成功");
			}
			menuService.saveOrUpdateMenu(am);
			juiResult.setNavTabId("applicationManage");
		} catch (Exception e) {
			juiResult.setStatusCode(JuiResult.FAILED);
			if (am.getId()!=null) {
				juiResult.setMessage("修改失败");
			}else {
				juiResult.setMessage("保存失败");
			}
			logger.error("模块保存失败",e);
		}
		return juiResult;
	}
	
	/*
	 * 删除 模块
	*/
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public JuiResult deleteAppManage(@PathVariable(value="id")Integer id){
		JuiResult juiResult=new JuiResult();
		try {
			menuService.deleteMenuWithChild(id);
			juiResult.setMessage("删除成功");
			juiResult.setCallbackType("");
			juiResult.setNavTabId("applicationManage");
		} catch (Exception e) {
			juiResult.setStatusCode(JuiResult.FAILED);
			logger.error("模块删除失败");
		}
		return juiResult;
	}
	
	
	/*
	 * 查看模块
	*/
	@RequestMapping("/view/{id}")
	public String appManageView(@PathVariable(value="id")Integer id,Model m){
		if (id!=null) {
			Menu aManage= menuService.findOne(id);
			m.addAttribute("aManage", aManage);
			if (aManage.getParentid()!=0) {
				m.addAttribute("pname", (menuService.findOne(aManage.getParentid())).getName());
			}
		}
		return viewName("applicationManageView");
	}
}