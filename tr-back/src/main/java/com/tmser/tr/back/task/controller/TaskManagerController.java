/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.task.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.back.task.Task;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.utils.SpringContextHolder;
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 *	任务监控管理器
 * </pre>
 *
 * @author tmser
 * @version $Id: TaskManagerController.java, v 1.0 2016年9月7日 下午4:07:52 tmser Exp $
 */
@Controller
@RequestMapping("/jy/back/task")
public class TaskManagerController extends AbstractController{

	/**
	 * 定时器管理
	 * @param m
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model m){
		List<Task> tasks = SpringContextHolder.getBeanNamesForType(Task.class);
		m.addAttribute("tasks",tasks);
		return viewName("index");
	}
	
	
	@RequestMapping("/excute/{code}/")
	@ResponseBody
	public JuiResult excute(@PathVariable("code")String code){
		JuiResult jrs = new JuiResult();
		jrs.setCallbackType("");
		jrs.setMessage("执行成功");
		List<Task> tasks = SpringContextHolder.getBeanNamesForType(Task.class);
		try {
			if (StringUtils.isNotBlank(code)) {
				for (Task task : tasks) {
					if (code.equals(task.code())) {
						logger.debug("execute task code:{}",code);
						task.execute();
					}
				}
			} 
		} catch (Exception e) {
			logger.error("execute task failed",e);
			jrs.setStatusCode(JuiResult.FAILED);
			jrs.setMessage("执行失败");
		}
		return jrs;
	}
}
