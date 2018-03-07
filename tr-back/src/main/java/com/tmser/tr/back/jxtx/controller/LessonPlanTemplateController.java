/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.jxtx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.back.jxtx.service.LessonPlanTemplateService;
import com.tmser.tr.back.logger.LoggerModule;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.lessonplantemplate.bo.LessonPlanTemplate;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 *	教案模板管理controller
 * </pre>
 *
 * @author daweiwbs
 * @version $Id: LessonPlanTemplateController.java, v 1.0 2015年9月6日 下午3:29:26 daweiwbs Exp $
 */
@Controller
@RequestMapping("/jy/back/jxtx/jamb")
public class LessonPlanTemplateController extends AbstractController{
 
	
	@Autowired
	public LessonPlanTemplateService lessonPlanTemplateService;
	
	@Autowired
	public ResourcesService resourcesService;
	
	/**
	 * 模板管理首页
	 * @return
	 */
	@RequestMapping("/index")
	public String toIndex(){
		 if(WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_ORG)==null){ //无管理学校，则跳系统管理员首页
			 return "forward:/jy/back/jxtx/jamb/index_sys";
		 }else{
			 return "forward:/jy/back/jxtx/jamb/index_org";
		 }
	}
	
	/**
	 * 系统管理员首页
	 * @return
	 */
	@RequestMapping("/index_sys")
	public String toSysIndex(LessonPlanTemplate lpt,Model m){
		lpt.setTpType(0);
		if(StringUtils.isEmpty(lpt.order())){
			lpt.addOrder("sort asc");
		}
		
		PageList<LessonPlanTemplate> templatePageList = lessonPlanTemplateService.findByPage(lpt);
		m.addAttribute("templateList", templatePageList);
		m.addAttribute("model", lpt);
		return "/back/jxtx/jamb/sys_index";
	}
	
	/**
	 * 学校管理员首页（也是列表页）
	 * @param pageNum
	 * @param numPerPage
	 * @param m
	 * @return
	 */
	@RequestMapping("/index_org")
	public String toOrgIndex(LessonPlanTemplate lpt,Model m){
		lpt.setTpType(1);
		if(StringUtils.isEmpty(lpt.order())){
			lpt.addOrder("sort asc");
		}
		lpt.setOrgId(((Organization)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_ORG)).getId());
		PageList<LessonPlanTemplate> templatePageList = lessonPlanTemplateService.findByPage(lpt);
		m.addAttribute("templateList", templatePageList);
		m.addAttribute("model", lpt);
		return "/back/jxtx/jamb/org_index";
	}
	
	/**
	 * 获取学校模板
	 * @param pageNum
	 * @param numPerPage
	 * @param m
	 * @return
	 */
	@RequestMapping("/getOrgTemplateList")
	public String getOrgTemplateList(@RequestParam(value="searchStr",required=false)String searchStr,LessonPlanTemplate lpt,Model m){
		lpt.setTpType(1);
		if(!StringUtils.isBlank(searchStr)){
			lpt.setOrgName(SqlMapping.LIKE_PRFIX +searchStr+ SqlMapping.LIKE_PRFIX);
		}
		if(StringUtils.isEmpty(lpt.order())){
			lpt.addOrder(" crtDttm desc");
		}
		
		PageList<LessonPlanTemplate> templatePageList = lessonPlanTemplateService.findByPage(lpt);
		
		m.addAttribute("templateList", templatePageList);
		m.addAttribute("model", lpt);
		m.addAttribute("searchStr", searchStr);
		return "/back/jxtx/jamb/list_org";
	}
	
	/**
	 * 模板信息表单编辑页
	 * @param m
	 * @return
	 */
	@RequestMapping("/edit")
	public String toEdit(Integer tpId,Integer tpType,Model m){
		if(tpId!=null){
			LessonPlanTemplate lpt = lessonPlanTemplateService.findOne(tpId);
			Resources fileRes = resourcesService.findOne(lpt.getResId());
			m.addAttribute("fileRes", fileRes);
			m.addAttribute("template", lpt);
			m.addAttribute("tpType", lpt.getTpType());
		}else{
			m.addAttribute("tpType",tpType);
		}
		List<MetaRelationship> phaseList = null;
		if((Organization)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_ORG)==null){
			 phaseList = MetaUtils.getPhaseMetaProvider().listAll();
		}else{
			phaseList = MetaUtils.getOrgTypeMetaProvider().listAllPhase(((Organization)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_ORG)).getSchoolings());
		}
		m.addAttribute("phaseList", phaseList);
		return "/back/jxtx/jamb/edit";
	}
	
	/**
	 * 保存模板
	 * @param template
	 * @return
	 */
	@RequestMapping("/saveTemplate")
	@ResponseBody
	public JuiResult saveTemplate(LessonPlanTemplate template,String oldFileRes,String oldPicRes){
		JuiResult rs = new JuiResult();
		try {
			if(template.getTpId()==null){ //新增
				LessonPlanTemplate lpt = lessonPlanTemplateService.saveLessonPlanTemplate(template);
				rs.setMessage("保存成功");
				if(template.getTpType().intValue()==1){
					LoggerUtils.insertLogger(LoggerModule.JXTX, "教案模板(学校)——增加模板，模板id："+lpt.getTpId());
				}else if(template.getTpType().intValue()==0){
					LoggerUtils.insertLogger(LoggerModule.JXTX, "教案模板(系统)——增加模板，模板id："+lpt.getTpId());
				}
			}else{ //修改
				lessonPlanTemplateService.updateLessonPlanTemplate(template);
				rs.setMessage("修改成功");
				LoggerUtils.insertLogger(LoggerModule.JXTX, "教案模板——更新模板，模板id："+template.getTpId());
			}
			rs.setStatusCode(JuiResult.SUCCESS);
			rs.setCallbackType(JuiResult.CB_CLOSE);
		} catch (Exception e) {
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("保存失败");
			logger.error("保存教案模板失败", e);
		}
		
		return rs;
	}
	
	/**
	 * 删除
	 * @param tpId
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public JuiResult delete(Integer tpId){
		JuiResult rs = JuiResult.forwardInstance();
		LessonPlanTemplate lpt = lessonPlanTemplateService.findOne(tpId);
		try {
			lessonPlanTemplateService.delete(tpId);
			//删掉模板文件
			resourcesService.deleteResources(lpt.getResId());
			//删掉模板图片
			Resources res = resourcesService.findOne(lpt.getIco());
			resourcesService.deleteWebResources(res.getPath());
			rs.setMessage("删除成功");
			rs.setCallbackType("");
			LoggerUtils.insertLogger(LoggerModule.JXTX, "教案模板——删除模板，模板id："+tpId);
		} catch (Exception e) {
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("删除失败");
			logger.error("删除教案模板失败", e);
		}
		return rs;
	}
		
	/**
	 * 禁用
	 * @param tpId
	 * @return
	 */
	@RequestMapping("/forbid")
	@ResponseBody
	public JuiResult forbid(Integer tpId){
		JuiResult rs = new JuiResult();
		try {
			LessonPlanTemplate lpt = new LessonPlanTemplate();
			lpt.setTpId(tpId);
			lpt.setEnable(0);
			lessonPlanTemplateService.update(lpt);
			rs.setStatusCode(JuiResult.SUCCESS);
			rs.setMessage("操作成功");
			rs.setNavTabId("tmpcontent");
			rs.setCallbackType("");
			LoggerUtils.insertLogger(LoggerModule.JXTX, "教案模板——冻结模板，模板id："+tpId);
		} catch (Exception e) {
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("操作失败");
			logger.error("禁用教案模板失败", e);
		}
		return rs;
	}
	
	/**
	 * 取消禁用
	 * @param tpId
	 * @return
	 */
	@RequestMapping("/unforbid")
	@ResponseBody
	public JuiResult unforbid(Integer tpId){
		JuiResult rs = new JuiResult();
		try {
			LessonPlanTemplate lpt = new LessonPlanTemplate();
			lpt.setTpId(tpId);
			lpt.setEnable(1);
			lessonPlanTemplateService.update(lpt);
			rs.setStatusCode(JuiResult.SUCCESS);
			rs.setMessage("操作成功");
			rs.setCallbackType("");
			rs.setNavTabId("tmpcontent");
			LoggerUtils.insertLogger(LoggerModule.JXTX, "教案模板——解冻模板，模板id："+tpId);
		} catch (Exception e) {
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("操作失败");
			logger.error("取消禁用教案模板失败", e);
		}
		return rs;
	}
	
	/**
	 * 浏览模板文件
	 * @param tpId
	 * @return
	 */
	@RequestMapping("/scanTemplate")
	public String scanTemplate(Integer tpId){
		LessonPlanTemplate lpt = lessonPlanTemplateService.findOne(tpId);
		return "forward:/jy/scanResFile?resId="+lpt.getResId();
	}
	
	/**
	 * 置为默认(学校模板)
	 * @param tpId
	 * @return
	 */
	@RequestMapping("/beDefault")
	@ResponseBody
	public JuiResult beDefault(Integer tpId){
		JuiResult rs = new JuiResult();
		try {
			LessonPlanTemplate lpt = new LessonPlanTemplate();
			lpt.setTpType(1);
			lpt.setTpIsdefault(1);
			lpt = lessonPlanTemplateService.findOne(lpt);
			if(lpt!=null){
				lpt.setTpIsdefault(0);
				lessonPlanTemplateService.update(lpt);
			}
			lpt = new LessonPlanTemplate();
			lpt.setTpIsdefault(1);
			lpt.setTpId(tpId);
			lessonPlanTemplateService.update(lpt);
			rs.setStatusCode(JuiResult.SUCCESS);
			rs.setMessage("操作成功");
			rs.setNavTabId("tmpcontent");
			rs.setCallbackType("");
			LoggerUtils.insertLogger(LoggerModule.JXTX, "教案模板——置为默认，模板id："+tpId);
		} catch (Exception e) {
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("操作失败");
			logger.error("置为默认的教案模板操作失败", e);
		}
		return rs;
	}
	
}
