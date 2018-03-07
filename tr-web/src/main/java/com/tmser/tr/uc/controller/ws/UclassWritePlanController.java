/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.controller.ws;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.lessonplantemplate.bo.LessonPlanTemplate;
import com.tmser.tr.lessonplantemplate.service.LessonPlanTemplateService;
import com.tmser.tr.manage.meta.service.BookChapterService;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.zhuozhengsoft.pageoffice.DocumentVersion;
import com.zhuozhengsoft.pageoffice.OfficeVendorType;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;

/**
 * <pre>
 *  优课写教案对接
 * </pre>
 *
 * @author tmser
 * @version $Id: WenXuanLoginController.java, v 1.0 2015年7月16日 上午10:27:31 tmser Exp $
 */
@Controller
@RequestMapping("/jy/ws/uclass")
public class UclassWritePlanController extends AbstractController{
	@Autowired
	BookChapterService bcs;
	@Autowired
	private LessonPlanTemplateService lptService; //教案模板service
	@Autowired
	private ResourcesService resourcesService; //资源service
	
	@RequestMapping("/writeplan/{chapterId}")
	public String modify(@PathVariable("chapterId") String chapterId,String path, Model m) {
		m.addAttribute("lessonId", chapterId);
		m.addAttribute("path", path);
		return "/uclass/writelessonplan";
	}
	
	/**
	 * 获取可用的教案模板
	 */
	@RequestMapping("getLessonPlanTemplate")
	public void getLessonPlanTemplate(Model m){
		//获取模板集合(包含系统自带的)
		List<LessonPlanTemplate> lptList = lptService.getTemplateListByOrg(-1);
		m.addAttribute("templateList", lptList);
	}
	
	/**
	 * 跳转到写新教案页面
	 * @param tpId
	 * @param request
	 * @return
	 */
	@RequestMapping("toEditWordPage")
	public String toEditWordPage(@RequestParam(value="tpId",required=false) Integer tpId,String lessonId,HttpServletRequest request,Model m){
		PageOfficeCtrl poc = new PageOfficeCtrl(request);
		poc.setServerPage(request.getContextPath()+"/poserver.zz");
		//设置word文件的保存页面
		poc.setSaveFilePage("saveLessonPlan");
		//设置文档加载后执行的js方法
		poc.setJsFunction_AfterDocumentOpened("AfterDocumentOpened");
		//设置控件标题
		poc.setCaption("撰写教案");
		poc.setOfficeVendor(OfficeVendorType.AutoSelect);
		LessonPlanTemplate lessonPlanTemplate = null;
		//获取教案模板（以id获取，id不存在则获取默认模板，没有默认模板则打开新的word）
		if(tpId !=null){
			//根据id获取模板
			lessonPlanTemplate = lptService.getTemplateById(tpId);
		}else{
			//获取默认模板
			lessonPlanTemplate = lptService.getDefaultTemplateByOrgId(-1);
		}
		if(lessonPlanTemplate == null){
			//打开新文档
			poc.webCreateNew("uclass", DocumentVersion.Word2003);
		}else{
			Resources resources = resourcesService.findOne(lessonPlanTemplate.getResId());
			//调用下载接口获下载后的取本地路径
			String resPath = resources.getPath();
			//打开指定的Word文档
			poc.webOpen(request.getContextPath()+"/"+resPath,OpenModeType.docNormalEdit,"uclass");
			m.addAttribute("template", lessonPlanTemplate);
		}
		poc.setTagId("PageOfficeCtrl1");//此行必需
		m.addAttribute("lessonId", lessonId);
		m.addAttribute("lessonName", bcs.findOne(lessonId).getChapterName());
		return "/uclass/wordtemplate_edit";
	}
}
