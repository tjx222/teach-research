/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.yhgl.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.tmser.tr.back.logger.LoggerModule;
import com.tmser.tr.back.yhgl.service.RegisterService;
import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.org.bo.Area;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.AreaService;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * <pre>
 * 注册管理
 * </pre>
 * 
 * @author daweiwbs
 * @version $Id: RegisterController.java, v 1.0 2015年8月25日 下午3:26:02 daweiwbs
 *          Exp $
 */
@Controller
@RequestMapping("/jy/back/yhgl")
public class RegisterController extends AbstractController {

	@Autowired
	private RegisterService registerService;
	@Autowired
	private OrganizationService orgService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private OrganizationService organizationService;

	@RequestMapping("/index")
	public String index() {
		return "/back/register/index";
	}

	/**
	 * 下载批量注册模板
	 * 
	 * @param phaseId
	 * @param orgId
	 * @return
	 */
	@RequestMapping("/downLoadRegisterTemplate")
	public void downLoadRegisterTemplate(Integer phaseId, Integer orgId, String templateType, HttpServletResponse response) {
		try {
			registerService.getRegisterTemplateFileStream(templateType, phaseId, orgId, response);
		} catch (Exception e) {
			logger.error("下载批量注册模板出错", e);
		}
	}

	/**
	 * 到区域用户批量注册页
	 * 
	 * @param orgId
	 * @param m
	 * @return
	 */
	@RequestMapping("/toUnitUserBatch")
	public String toUnitUserBatch(Integer areaId, Model m, Integer orgId) {
		Organization orga = (Organization) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_ORG);
		if (areaId != null) {
			List<Organization> orglist = organizationService.getOrgByAreaId(areaId, Organization.UNIT);
			m.addAttribute("orglist", orglist);
		}
		if (orgId != null) {
			Organization org = orgService.findOne(orgId);
			String orgStr = org.getName();
			Area area1 = areaService.findOne(org.getAreaId());
			if (area1.getParentId().intValue() != 0) {
				Area area2 = areaService.findOne(area1.getParentId());
				orgStr = area2.getName() + area1.getName() + orgStr;
			} else {
				orgStr = area1.getName() + orgStr;
			}
			m.addAttribute("orgName", orgStr);
		}
		m.addAttribute("orga", orga);
		return "/back/yhgl/batch/unitUserbatch";
	}

	/**
	 * 到学校用户批量注册页
	 * 
	 * @param orgId
	 * @param m
	 * @return
	 */
	@RequestMapping("/toSchUserBatch")
	public String toSchUserBatch(Integer orgId, Model m) {
		List<MetaRelationship> phaseList = orgService.listPhasebyOrgId(orgId);
		m.addAttribute("phaseList", phaseList);
		Organization org = orgService.findOne(orgId);
		String orgStr = org.getName();
		Area area1 = areaService.findOne(org.getAreaId());
		if (area1.getParentId().intValue() != 0) {
			Area area2 = areaService.findOne(area1.getParentId());
			orgStr = area2.getName() + area1.getName() + orgStr;
		} else {
			orgStr = area1.getName() + orgStr;
		}
		m.addAttribute("orgName", orgStr);
		m.addAttribute("orgId", orgId);
		return "/back/yhgl/batch/schUserbatch";
	}

	/**
	 * 批量注册
	 * 
	 * @param orgId
	 * @param phaseId
	 * @param file
	 */
	@RequestMapping("/batchRegister")
	public String batchRegister(String registerType, Integer orgId, Integer phaseId, MultipartFile registerFile, Model m) {
		try {
			StringBuilder resultBuffer = null;
			if ("xxyh".equals(registerType)) { // 学校用户注册
				resultBuffer = registerService.batchRegiter_xxyh(orgId, phaseId, registerFile);
				LoggerUtils.insertLogger(LoggerModule.YHGL, "学校用户管理——批量注册用户，学校id：" + orgId);
			} else if ("qyyh".equals(registerType)) {// 区域用户注册
				resultBuffer = registerService.batchRegiter_qyyh(orgId, registerFile);
				LoggerUtils.insertLogger(LoggerModule.YHGL, "区域用户管理——批量注册用户，机构id：" + orgId);
			}
			if (StringUtils.isBlank(resultBuffer)) {
				m.addAttribute("resultStr", "恭喜您，批量注册全部完成");
			} else {
				m.addAttribute("resultStr", resultBuffer.toString());
			}
		} catch (Exception e) {
			logger.error("批量注册出错", e);
			m.addAttribute("resultStr", "系统错误");
		}
		m.addAttribute("flag", registerType);
		return "/back/yhgl/batch/result";
	}

	/**
	 * 导出机构用户
	 */
	@RequestMapping("/exportsDetails")
	public void exportOrgUser(String templateType, Integer phaseId, Integer orgId, HttpServletResponse response) {
		registerService.exportTemplateWithUser(templateType, phaseId, orgId, response);
	}

}
