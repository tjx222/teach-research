/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.publishrelationship.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.meta.bo.BookSync;
import com.tmser.tr.manage.publishrelationship.service.PublishRelationshipService;

/**
 * <pre>
 *
 * </pre>
 *
 * @author ghw
 * @version $Id: PublishRelationship.java, v 1.0 2016年10月11日 上午11:56:25 ghw Exp $
 */
@Controller
@RequestMapping("/jy/manage/publishRelationship")
public class PublishRelationshipController extends AbstractController{
	@Autowired
	private PublishRelationshipService publishRelationshipService;
	
	@RequestMapping("/list")
	@ResponseBody
	public Result findListByPhaseAndSubject(Integer phaseId, Integer subjectId,Integer grade){
		Result re = Result.newInstance();
		List<BookSync> pList = publishRelationshipService.findListByXDXKNj(phaseId, subjectId,grade);
		re.setData(pList);
		return re;
	}
}
