/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.lessonplantemplate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.lessonplantemplate.bo.LessonPlanTemplate;

/**
 * <pre>
 *教案模板管理类
 * </pre>
 *
 * @author wangdawei
 * @version $Id: TemplateController.java, v 1.0 2015年2月1日 下午3:07:35 wangdawei Exp $
 */
@Controller
@RequestMapping("/jy/")
public class TemplateController extends AbstractController{

	/**
	 * 跳转到模板管理页
	 * @return
	 */
	@RequestMapping("templateManage")
	public String toManage(){
		
		return "/lessonPlanTemplate/templateManage";
	}
	
	@RequestMapping("lessonTreeDemo")
	public String toDemo(){
		return "/writeLessonPlan/lessonMeta";
	}
	
	/**
	 * 添加模板
	 * @param lessonPlanTemplate
	 * @param tp_file
	 * @param model
	 * @return
	 */
	@RequestMapping("addTemplate")
	public String addTemplate(LessonPlanTemplate lessonPlanTemplate,MultipartFile tp_file,Model model){
		lessonPlanTemplate.setTpName(tp_file.getOriginalFilename());
		lessonPlanTemplate.setTpIsdefault(1);
		model.addAttribute("template", lessonPlanTemplate);
		model.addAttribute("result","添加成功！");
		return"/lessonPlanTemplate/templateList";
	}
}
