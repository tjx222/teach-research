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

import com.tmser.tr.back.jxtx.service.JXTXSubjectManageService;
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
 * @version $Id: JXTXSubjectManageController.java, v 1.0 2015年8月28日 下午5:20:16 wy
 *          Exp $
 */
@Controller
@RequestMapping("/jy/back/jxtx")
public class JXTXSubjectManageController extends AbstractController {

	@Autowired
	private JXTXSubjectManageService jXTXSubjectManageService;

	/**
	 * 基础数据关系维护的学科查询
	 * 
	 * @param m
	 * @param type
	 * @return
	 */
	@RequestMapping("/subjectIndex")
	public String index(Model m, Integer type) {
		List<MetaRelationship> XDlist = MetaUtils.getPhaseMetaProvider().listAll();
		m.addAttribute("XDlist", XDlist);
		return viewName("subject/subjectIndex");
	}

	@RequestMapping("/querySubject")
	public String querySubject(Model m, Integer eid) {
		m.addAttribute("eid", eid);
		List<Meta> subjectList = jXTXSubjectManageService.querySubjectByEid(eid);
		m.addAttribute("subjectList", subjectList);
		return viewName("subject/subjectRIndex");
	}

	@RequestMapping("/addSubject")
	public String addSubject(Model m, Integer eid) {
		List<Meta> iMetadataList = jXTXSubjectManageService.queryMeta(eid);
		m.addAttribute("iMetadataList", iMetadataList);
		m.addAttribute("eid", eid);
		return viewName("subject/subjectAdd");
	}

	@RequestMapping("/saveSubject")
	@ResponseBody
	public JuiResult saveSubject(Integer[] ids, Integer eid) {
		JuiResult rs = new JuiResult();
		try {
			jXTXSubjectManageService.saveSubjectMate(ids, eid);
			rs.setMessage("保存成功！");
			rs.setCallbackType(JuiResult.CB_CLOSE);
		} catch (Exception e) {
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("保存失败！");
			logger.error("学科保存失败", e);
		}
		return rs;
	}

	@RequestMapping("/sortSubject")
	@ResponseBody
	public JuiResult sortGrade(@RequestParam("subjectIds[]") List<Integer> subjectIds, @RequestParam("subjectNames") String subjectNames, @RequestParam("eid") Integer eid) {
		JuiResult rs = new JuiResult();
		rs.setStatusCode(JuiResult.FAILED);
		rs.setMessage("排序保存失败！");
		try {
			String descs = URLDecoder.decode(subjectNames, "UTF-8");
			if (jXTXSubjectManageService.sortSubjectMate(subjectIds, descs, eid)) {
				rs.setMessage("排序成功！");
				rs.setCallbackType(JuiResult.CB_CLOSE);
			}
		} catch (Exception e) {
			logger.error("年级排序失败", e);
		}
		return rs;
	}

	@RequestMapping("/deleteSubject")
	@ResponseBody
	public JuiResult deleteSubject(Integer eid, Integer xkid) {
		JuiResult rs = new JuiResult();
		try {
			jXTXSubjectManageService.deleteById(eid, xkid);
			rs.setMessage("删除成功！");
			rs.setCallbackType("");
		} catch (Exception e) {
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("删除失败！");
			logger.error("学科删除失败", e);
		}
		return rs;
	}
}
