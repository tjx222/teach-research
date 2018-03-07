/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.jxtx.controller;

import java.net.URLDecoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.back.jxtx.service.JXTXGradeManageService;
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
 * @version $Id: JXTXGradeManageController.java, v 1.0 2015年8月27日 下午6:24:03 wy
 *          Exp $
 */
@Controller
@RequestMapping("/jy/back/jxtx")
public class JXTXGradeManageController extends AbstractController {

	@Autowired
	private JXTXGradeManageService jXTXGradeManageService;

	/**
	 * 基础数据关系维护的年级查询
	 * 
	 * @param m
	 * @param type
	 * @return
	 */
	@RequestMapping("/gradeIndex")
	public String index(Model m) {
		List<MetaRelationship> XDlist = MetaUtils.getPhaseMetaProvider().listAll();
		m.addAttribute("XDlist", XDlist);
		return viewName("grade/gradeIndex");
	}

	@RequestMapping("/queryGrade")
	public String queryGrade(Model m, Integer eid) {
		m.addAttribute("eid", eid);
		List<Meta> gradelist = jXTXGradeManageService.queryGradeByEid(eid);
		m.addAttribute("gradelist", gradelist);
		return viewName("grade/gradeRIndex");
	}

	@RequestMapping("/addGrade")
	public String addGrade(Model m, Integer eid) {
		List<Meta> iMetadataList = jXTXGradeManageService.queryMeta(eid);
		m.addAttribute("iMetadataList", iMetadataList);
		m.addAttribute("eid", eid);
		return viewName("grade/gradeAdd");
	}

	@RequestMapping("/saveGrade")
	@ResponseBody
	public JuiResult saveGrade(Integer[] ids, Integer eid) {
		JuiResult rs = new JuiResult();
		try {
			jXTXGradeManageService.saveGradeMate(ids, eid);
			rs.setMessage("保存成功！");
			rs.setCallbackType(JuiResult.CB_CLOSE);
		} catch (Exception e) {
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("保存失败！");
			logger.error("年级保存失败", e);
		}
		return rs;
	}

	@RequestMapping("/sortGrade")
	@ResponseBody
	public JuiResult sortGrade(@RequestParam("gradeIds[]") List<Integer> gradeIds, @RequestParam("gradeNames") String gradeNames, @RequestParam("eid") Integer eid) {
		JuiResult rs = new JuiResult();
		rs.setStatusCode(JuiResult.FAILED);
		rs.setMessage("排序保存失败！");
		try {
			String descs = URLDecoder.decode(gradeNames, "UTF-8");
			if (jXTXGradeManageService.sortGradeMate(gradeIds, descs, eid)) {
				rs.setMessage("排序成功！");
				rs.setCallbackType(JuiResult.CB_CLOSE);
			}
		} catch (Exception e) {
			logger.error("年级排序失败", e);
		}
		return rs;
	}

	@RequestMapping("/deleteGrade")
	@ResponseBody
	public JuiResult deleteGrade(Integer eid, Integer njid) {
		JuiResult rs = new JuiResult();
		try {
			jXTXGradeManageService.deleteById(eid, njid);
			rs.setMessage("删除成功！");
			rs.setCallbackType("");
			rs.setNavTabId("grade");
		} catch (Exception e) {
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("删除失败！");
			logger.error("年级删除失败", e);
		}
		return rs;
	}

}
