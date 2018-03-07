/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.schconfig.clss.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tmser.tr.back.schconfig.clss.service.SchClassBatchService;
import com.tmser.tr.back.schconfig.clss.service.SchClassService;
import com.tmser.tr.back.schconfig.clss.service.SchClassUserService;
import com.tmser.tr.back.schconfig.clss.vo.ClassVo;
import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.schconfig.clss.bo.SchClass;
import com.tmser.tr.schconfig.clss.bo.SchClassUser;
import com.tmser.tr.schconfig.clss.vo.SchClassUserVo;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;

/**
 * 班级管理
 * 
 * <pre>
 * 学校班级管理
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: RedHeadManage.java, v 1.0 2015-08-26 Generate Tools Exp $
 */
@Controller
@RequestMapping("/jy/back/schconfig/clss")
public class SchClassController extends AbstractController {

	@Autowired
	private SchClassService schClassService;

	@Autowired
	private SchClassUserService schClassUserService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private UserSpaceService userSpaceService;
	@Autowired
	private SchClassBatchService schClassBatchService;

	/*
	 * 显示红头列表
	 */
	@RequestMapping("/index")
	public String index(Model m, Integer orgId, @RequestParam(value = "grade", required = false) Integer phase) {
		Organization org = null;
		if (orgId == null) {
			org = (Organization) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_ORG);
			if (org == null) {
				// 模块列表url
				m.addAttribute("orgurl", "/jy/back/schconfig/clss/index?orgId=");
				// 模块加载divId
				m.addAttribute("divId", "sch_clss");
				return "/back/xxsy/selectOrg";
			}
			orgId = org.getId();
		}
		m.addAttribute("orgId", orgId);
		m.addAttribute("cp", phase);
		return viewName("index");
	}

	/**
	 * 列出机构学段，年级的所有班级
	 */
	@RequestMapping("/list")
	public String listClss(Integer orgId, @RequestParam(value = "phase", required = false) Integer phase,
			@RequestParam(value = "gradeId", required = false) Integer gradeId, Model m) {
		Organization org = organizationService.findOne(orgId);
		Integer schoolings = org.getSchoolings();
		Map<Integer, List<Meta>> phaseGradeMap = MetaUtils.getOrgTypeMetaProvider().listPhaseGradeMap(schoolings);
		SchClass model = new SchClass();
		if (phase != null && gradeId == null) {
			List<Meta> clsIds = phaseGradeMap.get(phase);
			List<Integer> gids = new ArrayList<Integer>();
			for (Meta meta : clsIds) {
				gids.add(meta.getId());
			}
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("gids", gids);
			model.addCustomCondition(" and gradeId in (:gids) ", paramMap);
		}
		if (gradeId != null) {
			model.setGradeId(gradeId);
		}
		model.setOrgId(orgId);
		model.addOrder("sort desc");
		List<SchClass> clsList = schClassService.findAll(model);
		m.addAttribute("clsList", clsList);
		m.addAttribute("gradeId", gradeId);
		m.addAttribute("phase", phase);
		m.addAttribute("orgId", orgId);
		return viewName("list");
	}

	/*
	 * 新建子模块或修改班级
	 */
	@RequestMapping("/add")
	public String addOrEdit(@RequestParam(value = "phase", required = true) Integer phase, @RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "grade", required = false) Integer gradeId, @RequestParam(value = "orgId", required = false) Integer orgId, Model m) {
		SchClass cls = null;
		if (id != null) {
			cls = schClassService.findOne(id);
		} else {
			cls = new SchClass();
			cls.setOrgId(orgId);
			cls.setGradeId(gradeId);
		}
		m.addAttribute("phase", phase);
		m.addAttribute("cls", cls);

		return viewName("addoredit");
	}

	@RequestMapping("lookSelect")
	public String lookSelect(@RequestParam(value = "phase", required = true) Integer phase, @RequestParam(value = "orgId", required = false) Integer orgId,
			@RequestParam(value = "grade", required = false) Integer gradeId, Model m) {
		m.addAttribute("phase", phase);
		m.addAttribute("orgId", orgId);
		m.addAttribute("grade", gradeId);
		return viewName("dwzGradeLookup");
	}

	/*
	 * 新建子模块或修改班级
	 */
	@RequestMapping("/batchadd")
	public String batchAdd(@RequestParam(value = "phase", required = true) Integer phase, @RequestParam(value = "grade", required = false) Integer gradeId,
			@RequestParam(value = "orgId", required = false) Integer orgId, Model m) {

		m.addAttribute("phase", phase);
		m.addAttribute("orgId", orgId);
		m.addAttribute("grade", gradeId);
		return viewName("batchadd");
	}

	/**
	 * 保存班级信息
	 */
	@RequestMapping("/batchsave")
	@ResponseBody
	public JuiResult batchSave(ClassVo classes) {
		JuiResult juiResult = new JuiResult();
		Date now = new Date();
		User u = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		try {
			for (SchClass cls : classes.getClasses()) {
				cls.setCrtDttm(now);
				cls.setEnable(SchClass.ENABLE);
				cls.setLastupDttm(now);
				cls.setLastupId(u.getId());
				cls.setCrtId(u.getId());
				schClassService.save(cls);
				juiResult.setMessage("保存成功");
			}

		} catch (Exception e) {
			juiResult.setStatusCode(JuiResult.FAILED);
			juiResult.setMessage("批量保存失败");
			logger.error("班级批量保存失败", e);
		}
		return juiResult;
	}

	/**
	 * 保存班级信息
	 */
	@RequestMapping("/save")
	@ResponseBody
	public JuiResult save(SchClass cls) {
		JuiResult juiResult = new JuiResult();
		Date now = new Date();
		User u = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		try {
			if (cls.getId() != null) {
				SchClass model = new SchClass();
				model.setId(cls.getId());
				model.setCode(cls.getCode());
				model.setName(cls.getName());
				model.setSort(cls.getSort());
				model.setGradeId(cls.getGradeId());
				model.setLastupDttm(now);
				cls.setLastupId(u.getId());
				schClassService.update(model);
				juiResult.setMessage("修改成功");
			} else {

				cls.setCrtDttm(now);
				cls.setEnable(SchClass.ENABLE);
				cls.setLastupDttm(now);
				cls.setLastupId(u.getId());
				cls.setCrtId(u.getId());
				schClassService.save(cls);
				juiResult.setMessage("保存成功");
			}

		} catch (Exception e) {
			juiResult.setStatusCode(JuiResult.FAILED);
			if (cls.getId() != null) {
				juiResult.setMessage("修改失败");
			} else {
				juiResult.setMessage("保存失败");
			}
			logger.error("班级保存失败", e);
		}
		return juiResult;
	}

	/**
	 * 真实删除班级
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public JuiResult delete(Integer id, Model m) {
		JuiResult juiResult = new JuiResult();
		try {
			schClassService.realDelete(id);
			LoggerUtils.deleteLogger("删除id为{}的班级", id);
			juiResult.setMessage("删除成功");
			juiResult.setCallbackType("");
		} catch (Exception e) {
			juiResult.setStatusCode(JuiResult.FAILED);
			juiResult.setMessage("删除失败");
			logger.error("班级删除失败", e);
		}
		return juiResult;
	}

	/**
	 * 删除班级
	 */
	@RequestMapping("/state")
	@ResponseBody
	public JuiResult changeState(Integer id, Model m) {
		JuiResult juiResult = new JuiResult();
		try {
			SchClass cls = schClassService.findOne(id);
			if (cls != null) {
				SchClass model = new SchClass();
				model.setEnable(SchClass.ENABLE == cls.getEnable() ? SchClass.DISABLE : SchClass.ENABLE);
				model.setId(cls.getId());
				schClassService.update(model);
			}
			LoggerUtils.updateLogger("更新id为{}的班级状态", id);
			juiResult.setMessage("更新成功");
			juiResult.setCallbackType("");
		} catch (Exception e) {
			juiResult.setStatusCode(JuiResult.FAILED);
			juiResult.setMessage("更新失败");
			logger.error("更新失败", e);
		}
		return juiResult;
	}

	@RequestMapping("/editclsuser")
	public String editclsuser(Integer id, @RequestParam(value = "phase", required = true) Integer phase, Model m) {
		if (id != null) {
			SchClass clss = schClassService.findOne(id);
			Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
			SchClassUser model = new SchClassUser();
			model.setClassId(id);
			model.setSchoolYear(schoolYear);
			model.setEnable(SchClassUser.ENABLE);
			model.addCustomCulomn("id,classId,username,tchId,subjectId,type");
			model.buildCondition(" and (type = :tch or type = :master)").put("tch", SchClassUser.T_TEACHER).put("master", SchClassUser.T_MASTER);

			Map<Integer, SchClassUser> subjectUserMap = new HashMap<Integer, SchClassUser>();
			List<SchClassUser> culist = schClassUserService.findAll(model);
			for (SchClassUser cu : culist) {
				if (SchClassUser.T_TEACHER.equals(cu.getType())) {
					subjectUserMap.put(cu.getSubjectId(), cu);
				} else if (SchClassUser.T_MASTER.equals(cu.getType())) {
					m.addAttribute("master", cu);
				}
			}
			m.addAttribute("schUserMap", subjectUserMap);
			m.addAttribute("phase", phase);
			m.addAttribute("clss", clss);
		}

		return viewName("editclsuser");
	}

	/**
	 * 编辑班级用户
	 */
	@RequestMapping("/saveuser")
	@ResponseBody
	public JuiResult saveSchClassUser(SchClassUserVo scVo, Model m) {
		JuiResult juiResult = new JuiResult();
		try {
			SchClass cls = schClassService.findOne(scVo.getClsid());
			if (cls != null) {
				schClassUserService.editSchClassUser(scVo);
			}
			LoggerUtils.updateLogger("更新id为{}的班级用户", scVo.getClsid());
			juiResult.setMessage("用户管理保存成功");
			juiResult.setCallbackType("");
		} catch (Exception e) {
			juiResult.setStatusCode(JuiResult.FAILED);
			juiResult.setMessage("用户管理保存失败");
			logger.error("用户管理保存失败", e);
		}
		return juiResult;
	}

	@RequestMapping("/lookupuser")
	@ResponseBody
	public List<Map<String, Object>> lookUpUser(@RequestParam(value = "orgId", required = true) Integer orgId,
			@RequestParam(value = "subject", required = false) Integer subject, @RequestParam(value = "grade", required = false) Integer grade) {
		Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		List<Map<String, Object>> userList = new ArrayList<>();
		UserSpace model = new UserSpace();
		model.setGradeId(grade);
		model.setSubjectId(subject);
		model.setOrgId(orgId);
		model.addCustomCulomn("userId,username");
		model.addGroup("userId");
		model.setSysRoleId(SysRole.TEACHER.getId());
		model.setEnable(UserSpace.ENABLE);
		model.setSchoolYear(schoolYear);
		List<UserSpace> uslist = userSpaceService.findAll(model);
		for (UserSpace us : uslist) {
			Map<String, Object> u = new HashMap<>(2);
			u.put("tchId", us.getUserId());
			u.put("username", us.getUsername());
			userList.add(u);
		}
		return userList;
	}
	@RequestMapping("/batchTeacher")
	public String batchTeacher(@RequestParam(value = "phaseId", required = true) Integer phaseId,SchClass sch, Model m) {
		m.addAttribute("phaseId", phaseId);
		m.addAttribute("orgId", sch.getOrgId());
		return viewName("batchTeacher");
	}
	@RequestMapping("/downLoadRegisterTemplate")
	public void downLoadRegisterTemplate(@RequestParam(value = "phaseId", required = true) Integer phaseId,
			SchClass sch,HttpServletResponse response) {
		try {
			schClassBatchService.getRegisterTemplateFileStream(null, phaseId, sch.getOrgId(), response);
		} catch (Exception e) {
			logger.error("下载批量注册模板出错", e);
		}
	}
	/**
	 * 批量导入教师
	 * 
	 * @param orgId
	 * @param phaseId
	 * @param file
	 */
	@RequestMapping("/batchImportTeacher")
	public String batchImportTeacher(Integer orgId, Integer phaseId, MultipartFile registerFile, Model m) {
			try {
				StringBuilder resultBuffer = null;
				resultBuffer = schClassBatchService.batchImportTeacher(orgId, phaseId, registerFile);
			if (StringUtils.isBlank(resultBuffer)) {
				m.addAttribute("resultStr", "恭喜您，批量导入全部完成");
			} else {
				m.addAttribute("resultStr", resultBuffer.toString());
			}
		} catch (Exception e) {
			logger.error("批量导入教师出错", e);
			m.addAttribute("resultStr", "系统错误");
		}
		return "/back/yhgl/batch/result";
	}

}