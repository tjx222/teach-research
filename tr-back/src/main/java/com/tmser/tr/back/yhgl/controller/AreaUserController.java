/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.yhgl.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.back.logger.LoggerModule;
import com.tmser.tr.back.yhgl.service.BackUserManageService;
import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.uc.bo.Login;
import com.tmser.tr.uc.bo.Role;
import com.tmser.tr.uc.bo.RoleType;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.LoginService;
import com.tmser.tr.uc.service.RoleService;
import com.tmser.tr.uc.service.RoleTypeService;
import com.tmser.tr.uc.service.UserService;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 * 区域用户管理Controller
 * </pre>
 * 
 * @author wy
 * @version $Id: AreaUserController.java, v 1.0 2015年9月29日 上午11:37:35 wy Exp $
 */
@Controller
@RequestMapping("/jy/back/yhgl")
public class AreaUserController extends AbstractController {

	@Autowired
	private BackUserManageService backUserManageService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private UserService userService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private UserSpaceService userSpaceService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private RoleTypeService roleTypeService;

	/**
	 * 区域用户管理入口地址
	 * 
	 * @param m
	 * @return
	 */
	@RequestMapping("/areaUserIndex")
	public String schUserIndex(Model m) {
		Organization org = (Organization) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_ORG);
		if (org != null) {
			User user = new User();
			user.setOrgId(org.getId());
			user.setUserType(User.AREA_USER);
			List<Role> roleList = roleService.findRoleListByUseOrgId(user.getOrgId(), 1);
			if (user.order() == null || "".equals(user.order())) {
				user.addOrder("u.crtDttm desc");
			}
			Map<String, Object> areaUsersMap = backUserManageService.getAreaUsers(null, user, null);
			m.addAttribute("unitUserlist", areaUsersMap.get("unitUserlist"));
			m.addAttribute("unitId", user.getOrgId());
			m.addAttribute("roleList", roleList);
			m.addAttribute("model", user);
			m.addAttribute("org", org);
			return viewName("area/unitUserload");
		} else {
			return viewName("area/areaManageIndex");
		}
	}

	/**
	 * 所有的行政区域，显示区域树所用
	 * 
	 * @param m
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findUnit")
	public Object findSchool(Integer areaId, Integer type) {
		List<Organization> orglist = organizationService.getOrgByAreaId(areaId, type);
		return orglist;
	}

	/**
	 * 通过名称搜索所有部门
	 * 
	 * @param m
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/searAllUnit")
	public Object findSchool(String name, Integer type) {
		Map<String, Object> orglist = organizationService.getAllOrgByName(name, type);
		return orglist;
	}

	/**
	 * 通过区域ID搜索所有用户信息
	 * 
	 * @param m
	 * @param orgId
	 * @return
	 */
	@RequestMapping("/unitUserIndex")
	public String unitUserIndex(Model m, User user, UserSpace us, Integer areaId,Login login) {
		List<Role> roleList = new ArrayList<Role>();
		roleList = roleService.findRoleListByUseOrgId(null, 1);
		if (StringUtils.isBlank(user.order())) {
			user.addOrder("u.crtDttm desc");
		}
		Organization org = (Organization) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_ORG);
		Map<String, Object> areaUsersMap = backUserManageService.getAreaUsers(areaId, user, us.getRoleId());
		m.addAttribute("searchStr", areaUsersMap.get("searchStr"));
		m.addAttribute("selRoleId", us.getRoleId());
		m.addAttribute("unitUserlist", areaUsersMap.get("unitUserlist"));
		m.addAttribute("roleList", roleList);
		m.addAttribute("model", user);
		m.addAttribute("login", login);
		m.addAttribute("orglist", areaUsersMap.get("orglist"));
		m.addAttribute("areaId", areaId);
		m.addAttribute("org", org);
		return viewName("area/unitUserload");
	}

	/**
	 * 新增用户管理
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/addOrEditUnitUser")
	public String addUnitUser(Model model, Integer areaId, Integer id) {
		Organization org = (Organization) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_ORG);
		List<Organization> orglist = organizationService.getOrgByAreaId(areaId, Organization.UNIT);
		model.addAttribute("orglist", orglist);
		model.addAttribute("org", org);
		if (id != null) {
			Login loginUser = loginService.findOne(id);
			User user = userService.findOne(id);
			model.addAttribute("loginUser", loginUser);
			model.addAttribute("user", user);
			return viewName("area/unitUserEdit");
		}
		return viewName("area/unitUserAdd");
	}

	/**
	 * 查看用户信息
	 * 
	 * @param model
	 * @param unitId
	 * @param id
	 * @return
	 */
	@RequestMapping("/lookUnitUser")
	public String lookUnitUser(Model model, Integer unitId, Integer id) {
		Organization org = organizationService.findOne(unitId);
		model.addAttribute("org", org);
		Login loginUser = loginService.findOne(id);
		User user = userService.findOne(id);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("user", user);
		return viewName("area/unitUserLook");
	}

	/**
	 * 新增用户
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/addUnitUser")
	public String addUnitUser(Model model, Integer unitId) {
		Organization org = organizationService.findOne(unitId);
		model.addAttribute("org", org);
		return viewName("area/unitUserAdd");
	}

	/**
	 * 校验用户名称是否重复
	 * 
	 * @param model
	 * @param user
	 * @param loginname
	 * @return
	 */
	@RequestMapping("/valUnitName")
	@ResponseBody
	public Object valUnitName(Model model, Login login) {
		List<Login> findAllList = loginService.findAll(login);
		return findAllList == null || findAllList.isEmpty();
	}

	/**
	 * 保存区域用户
	 * 
	 * @return
	 */
	@RequestMapping("/saveUnitUser")
	@ResponseBody
	public JuiResult saveUnitUser(User user, Login login) {
		JuiResult rs = new JuiResult();
		try {
			if (user.getId() == null) {// 新增
				Organization org = (Organization) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_ORG);
				if (org != null) {
					user.setOrgName(org.getName());
					user.setOrgId(org.getId());
				}
				Organization findOne = organizationService.findOne(user.getOrgId());
				if (findOne != null) {
					user.setOrgName(findOne.getName());
				}
				User saveUser = backUserManageService.saveUserAccount(User.AREA_USER, login, user);
				LoggerUtils.insertLogger(LoggerModule.YHGL, "区域用户管理——增加用户，用户id：" + saveUser.getId());
			} else {
				backUserManageService.updateUserAccount(login, user);
				LoggerUtils.updateLogger(LoggerModule.YHGL, "区域用户管理——修改用户，用户id：" + user.getId());
			}
			rs.setStatusCode(JuiResult.SUCCESS);
			rs.setMessage("操作成功");
			rs.setCallbackType(JuiResult.CB_CLOSE);
		} catch (Exception e) {
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("操作失败");
			logger.error("用户操作出错", e);
		}
		return rs;
	}

	/**
	 * 查询给用户所赋所有身份信息
	 * 
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping("/unitUserRole")
	public String unitUserRole(Model model, UserSpace us) {
		us.addCustomCulomn("id,orgId,departmentId,roleId,userId,schoolYear,phaseId,subjectId,spaceName");
		List<UserSpace> userSpaceList = userSpaceService.findAll(us);
		model.addAttribute("uslist", userSpaceList);
		model.addAttribute("userId", us.getUserId());
		return viewName("area/userRoleInfo");
	}

	/**
	 * 增加用户身份
	 * 
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping("/addOrEditUnitRole")
	public String addOrEditUnitRole(Model model, Integer userId) {
		User userOne = userService.findOne(userId);
		Organization org = organizationService.findOne(userOne.getOrgId());
		Organization deptOrg = new Organization();
		deptOrg.setParentId(org.getId());
		List<Organization> allDeptOrg = organizationService.findAll(deptOrg);
		List<Role> roleList = roleService.findRoleListByUseOrgId(userOne.getOrgId(), 1);
		List<MetaRelationship> metaList = MetaUtils.getPhaseMetaProvider().listAll();
		model.addAttribute("allDeptOrg", allDeptOrg);
		model.addAttribute("roleList", roleList);
		model.addAttribute("metaList", metaList);
		model.addAttribute("userId", userId);
		model.addAttribute("org", org);
		return viewName("area/userRoleAdd");
	}

	/**
	 * 通过学段查找对应学科
	 * 
	 * @param model
	 * @param phaseId
	 * @return
	 */
	@RequestMapping("/sujectInfoByPhase/{phaseId}")
	@ResponseBody
	public List<List<String>> sujectInfoByPhase(Model model, @PathVariable Integer phaseId) {
		List<List<String>> subjList;
		if (-1 != phaseId.intValue()) {
			List<Meta> subjects = MetaUtils.getPhaseSubjectMetaProvider().listAllSubjectByPhaseId(phaseId);
			subjList = parseMetaListToJuiList(subjects);
		} else {
			subjList = new ArrayList<List<String>>();
			List<String> singleList = new ArrayList<String>();
			singleList.add("");
			singleList.add("请选择");
			subjList.add(singleList);
		}
		
		return subjList;
	}
	
	private List<List<String>> parseMetaListToJuiList(List<Meta> metas){
		List<List<String>> subjList = new ArrayList<List<String>>();
		for(Meta meta : metas){
			List<String> value = new ArrayList<String>();
			value.add(String.valueOf(meta.getId()));
			value.add(meta.getName());
			subjList.add(value);
		}
		return subjList;
	}

	/**
	 * 保存用户角色
	 * 
	 * @param model
	 * @param userSpace
	 * @param dwgxbm
	 * @return
	 */
	@RequestMapping("/saveyhRole")
	@ResponseBody
	public Object saveyhRole(Model model, UserSpace userSpace, String[] dwgxbm, Integer editId) {
		JuiResult rs = new JuiResult();
		try {
			if (userSpace.getPhaseId().intValue() == -1) {
				userSpace.setPhaseId(null);
			}
			UserSpace findOne = userSpaceService.findOne(editId);
			userSpace.setConDepIds(StringUtils.join(dwgxbm, ","));
			UserSpace space = new UserSpace();
			space.setRoleId(userSpace.getRoleId());
			space.setUserId(userSpace.getUserId());
			space.setPhaseId(userSpace.getPhaseId());
			space.setSubjectId(userSpace.getSubjectId());
//			space.setSchoolYear((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
			List<UserSpace> findList = userSpaceService.find(space, 100);
			if (findList.size() > 0) {
				if (findList.contains(findOne)) {
					findList.remove(findOne);
				}
				if (findList.size() > 0) {
					rs.setStatusCode(JuiResult.FAILED);
					rs.setMessage("该角色已经存在！");
					return rs;
				}
			}
			backUserManageService.saveAreaUserRole(userSpace, editId);
			LoggerUtils.insertLogger(LoggerModule.YHGL, "区域用户管理——增加角色，用户id：" + userSpace.getUserId());
			rs.setStatusCode(JuiResult.SUCCESS);
			rs.setMessage("保存成功！");
		} catch (Exception e) {
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("保存失败！");
			logger.error("部门用户角色保存失败", e);
		}

		return rs;
	}

	/**
	 * 删除给用户所赋角色
	 * 
	 * @param model
	 * @param user
	 * @return
	 */
	@RequestMapping("/delUserSpace")
	@ResponseBody
	public Object delUserSpace(Model model, UserSpace us) {
		JuiResult rs = new JuiResult();
		try {
			backUserManageService.delUserSpace(us);
			LoggerUtils.deleteLogger(LoggerModule.YHGL, "区域用户管理——删除角色，用户id：" + us.getUserId());
			rs.setMessage("操作成功");
			rs.setStatusCode(JuiResult.SUCCESS);
		} catch (Exception e) {
			logger.error("部门用户角色删除失败", e);
			rs.setStatusCode(JuiResult.FAILED);
		}

		return rs;
	}

	/**
	 * 查看用户角色信息
	 * 
	 * @param model
	 * @param userSpa
	 * @return
	 */
	@RequestMapping("/lookUnitUserRole")
	public String lookUnitUserRole(Model model, UserSpace userSpa) {
		Map<String, Object> metaMapInfo = backUserManageService.findAreaRoleInfo(userSpa);
		model.addAttribute("findOneSpace", metaMapInfo.get("findOneSpa"));
		model.addAttribute("findOnePhase", metaMapInfo.get("findOnePhase"));
		model.addAttribute("findOrgName", metaMapInfo.get("orgFind"));
		model.addAttribute("findOneRole", metaMapInfo.get("findOneRole"));
		model.addAttribute("gxbmName", metaMapInfo.get("gxbmList"));
		model.addAttribute("subject", metaMapInfo.get("subjectMeta"));
		return viewName("area/userRoleLook");
	}

	/**
	 * 根据角色类型决定学科、学段是否显示
	 * 
	 * @param model
	 * @param sysRoleId
	 * @return
	 */
	@RequestMapping("/showInfoBySysRoleId")
	@ResponseBody
	public Object showInfoBySysRoleId(Model model, Integer sysRoleId) {
		Map<String, Boolean> dataMap = new HashMap<String, Boolean>();
		if (sysRoleId != null) {
			RoleType roleType = new RoleType();
			roleType.setCode(sysRoleId);
			roleType.setUsePosition(1);
			RoleType type = roleTypeService.findOne(roleType);
			if (type != null) {
				dataMap.put("isNobm", type.getIsNoBmManager());
				dataMap.put("isNoXk", type.getIsNoXk());
				dataMap.put("isNoXd", type.getIsNoXz());
			}
		}
		return dataMap;
	}

	@RequestMapping("/exportAreaUser")
	public String exportAreaUser(Model model, Integer areaId) {
		List<Organization> orglist = organizationService.getOrgByAreaId(areaId, Organization.UNIT);
		model.addAttribute("orglist", orglist);
		return viewName("area/exportArea");
	}

}
