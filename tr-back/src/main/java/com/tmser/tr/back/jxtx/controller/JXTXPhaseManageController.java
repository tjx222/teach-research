/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.jxtx.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.back.jxtx.service.impl.JXTXPhaseManageServiceImpl;
import com.tmser.tr.back.logger.LoggerModule;
import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.common.vo.Datas;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.MetaRelationship;

/**
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author wy
 * @version $Id: JXTXPhaseManageController.java, v 1.0 2015年8月26日 上午10:43:52 wy
 *          Exp $
 */
@Controller
@RequestMapping("/jy/back/jxtx")
public class JXTXPhaseManageController extends AbstractController {

	@Autowired
	private JXTXPhaseManageServiceImpl jXTXPhaseManageService;

	@RequestMapping("/phaseIndex")
	public String index(Model m, Integer type) {
		m.addAttribute("phaselist", MetaUtils.getPhaseMetaProvider().listAll());
		return viewName("phase/phaseIndex");
	}

	@RequestMapping("/delete")
	@ResponseBody
	public JuiResult delete(MetaRelationship metaship) {
		JuiResult rs = new JuiResult();
		try {
			jXTXPhaseManageService.delete(metaship.getId());
			LoggerUtils.deleteLogger(LoggerModule.JXTX, "组织机构管理——删除学段，元数据id：" + metaship.getId());
			rs.setMessage("删除成功！");
			rs.setCallbackType("");
		} catch (Exception e) {
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("删除失败！");
			logger.error("学段删除失败", e);
		}
		return rs;
	}

	@RequestMapping("/add")
	public String add(Model m, MetaRelationship metaship) {
		m.addAttribute("iMetadataList", getPhaseMeta());
		return viewName("phase/phaseAdd");
	}
	
	private List<Meta> getPhaseMeta(){
		List<Meta> metalist = new ArrayList<Meta>();
		// 查询元数据
		List<Meta> listAllPhaseMeta = MetaUtils.getPhaseMetaProvider().listAllPhaseMeta();
		// 找出已添加的
		List<MetaRelationship> metas = MetaUtils.getPhaseMetaProvider().listAll();
		Set<Integer> oldIds = new HashSet<Integer>();
		for (MetaRelationship mr : metas) {
			oldIds.add(mr.getEid());
		}
		for (Meta m : listAllPhaseMeta){
			if(!oldIds.contains(m.getId())){
				metalist.add(m);
			}
		}
		return metalist;
	}

	@RequestMapping("/save")
	@ResponseBody
	public JuiResult savePhase(Datas metas) {
		JuiResult rs = new JuiResult();
		try {
			jXTXPhaseManageService.savePhaseMate(metas);
			rs.setMessage("保存成功！");
			rs.setCallbackType(JuiResult.CB_CLOSE);
		} catch (Exception e) {
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("保存失败！");
			logger.error("学段保存失败", e);
		}
		return rs;
	}

	@RequestMapping("/sortPhase")
	@ResponseBody
	public JuiResult sortGrade(@RequestParam("paramIds[]") List<Integer> phaseIds) {
		JuiResult rs = new JuiResult();
		rs.setStatusCode(JuiResult.FAILED);
		rs.setMessage("排序保存失败！");
		try {
			if (jXTXPhaseManageService.sortPhaseMate(phaseIds)) {
				rs.setMessage("排序成功！");
				rs.setCallbackType(JuiResult.CB_CLOSE);
			}
		} catch (Exception e) {
			logger.error("学段排序失败", e);
		}
		return rs;
	}
}
