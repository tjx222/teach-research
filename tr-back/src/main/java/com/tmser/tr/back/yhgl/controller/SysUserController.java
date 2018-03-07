/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.yhgl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.back.logger.LoggerModule;
import com.tmser.tr.back.yhgl.service.BackUserManageService;
import com.tmser.tr.back.yhgl.service.RegisterService;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.uc.bo.Login;
import com.tmser.tr.uc.bo.Role;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserManagescope;
import com.tmser.tr.uc.bo.UserRole;
import com.tmser.tr.uc.service.LoginService;
import com.tmser.tr.uc.service.RoleService;
import com.tmser.tr.uc.service.UserRoleService;
import com.tmser.tr.uc.service.UserService;

/**
 * <pre>
 * 系统用户管理
 * </pre>
 * 
 * @author daweiwbs
 * @version $Id: SysUserController.java, v 1.0 2015年10月8日 下午2:48:00 daweiwbs Exp
 *          $
 */
@Controller
@RequestMapping("/jy/back/yhgl/sys")
public class SysUserController extends AbstractController {

	@Autowired
	private BackUserManageService userManageService;
	@Autowired
	private UserService userService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private RegisterService registerService;

	@RequestMapping("/index")
	public String toIndex(User user, Model m) {
		if (user.order() == null || "".equals(user.order())) {
			user.addOrder("crtDttm desc");
		}
		m.addAttribute("search_name", user.getName());
		m.addAttribute("search_role", user.getAppId());
		PageList<User> userList = userManageService.getSysUsers(user);
		m.addAttribute("sysUserList", userList);
		// 系统角色集合
		List<Role> roleList = roleService.findRoleListByUseOrgId(null, 3);
		m.addAttribute("roleList", roleList);
		m.addAttribute("user", user);
		return "/back/yhgl/system/index";
	}

	/**
	 * 修改用户
	 * 
	 * @param userId
	 * @param m
	 * @return
	 */
	@RequestMapping("/editUser")
	public String toEdit(Integer userId, Model m) {
		// 系统角色集合
		List<Role> roleList = roleService.findRoleListByUseOrgId(null, 3);
		if (userId != null) {
			Login login = loginService.findOne(userId);
			User user = userService.findOne(userId);
			UserRole ur = new UserRole();
			ur.setUserId(userId);
			Role role = roleService.findOne(userRoleService.find(ur, 1).get(0).getRoleId());
			m.addAttribute("login", login);
			m.addAttribute("user", user);
			m.addAttribute("userId", userId);
			m.addAttribute("role", role);
			roleList.remove(role);
		} 
		m.addAttribute("roleList", roleList);
		return "/back/yhgl/system/edit";
	}

	/**
	 * 保存用户账号
	 * 
	 * @param user
	 * @param login
	 * @return
	 */
	@RequestMapping("/saveUser")
	@ResponseBody
	public JuiResult saveUser(Integer roleId, Integer userId, User user, Login login) {
		JuiResult rs = new JuiResult();
		try {
			registerService.saveUser(roleId, userId, user, login);
			rs.setStatusCode(JuiResult.SUCCESS);
			rs.setCallbackType(JuiResult.CB_CLOSE);
			rs.setMessage("保存成功");
		} catch (Exception e) {
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("保存失败");
			logger.error("保存用户账号出错", e);
		}
		return rs;
	}

	/**
	 * 判定用户是否已经存在
	 * 
	 * @param loginname
	 * @param m
	 */
	@RequestMapping("/userExistOrNot")
	public void userExistOrNot(String loginname, Model m) {
		if (loginService.getLoginByLoginname(loginname) != null) {
			m.addAttribute("isExist", true);
		} else {
			m.addAttribute("isExist", false);
		}
	}

	/**
	 * 到授权用户的管理范围页
	 * 
	 * @param userId
	 * @param m
	 * @return
	 */
	@RequestMapping("powerScopeEdit")
	public String powerScopeEdit(Integer userId, Model m) {
		// 得到用户角色
		UserRole ur = new UserRole();
		ur.setUserId(userId);
		Integer id=userRoleService.find(ur, 1).get(0).getRoleId();
		Role role = roleService.findOne(id);
		m.addAttribute("role", role);
		m.addAttribute("userId", userId);
		return "/back/yhgl/system/powerScopeEdit";
	}

	/**
	 * 授权用户的管理范围
	 * 
	 * @param userId
	 * @param roleId
	 * @param orgIdsStr
	 * @param m
	 * @return
	 */
	@RequestMapping("/savePowerScope")
	@ResponseBody
	public JuiResult savePowerScope(Integer userId, Integer roleId, String idsStr, Integer scope, Model m) {
		JuiResult rs = new JuiResult();
		try {
			if(scope.intValue() == 0){
				userManageService.savePowerScopeArea(userId, roleId, idsStr);
			} else if (scope.intValue() == 1) {
				userManageService.savePowerScope(userId, roleId, idsStr);
			}
			rs.setMessage("授权成功");
			rs.setStatusCode(JuiResult.SUCCESS);
			rs.setCallbackType(JuiResult.CB_CLOSE);
			LoggerUtils.insertLogger(LoggerModule.YHGL, "系统用户管理——授权用户管理范围，用户id：" + userId);
		} catch (Exception e) {
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("授权失败");
			logger.error("授权用户的管辖范围出错", e);
		}
		return rs;
	}

	/**
	 * 获取用户的管理权限
	 * 
	 * @param userId
	 * @param m
	 */
	@RequestMapping("getManagescope")
	public void getManagescope(Integer userId, Model m) {
		List<UserManagescope> scopeList = userManageService.getManagescopeByUserId(userId);
		m.addAttribute("scopeList", scopeList);
	}

	/**
	 * 查看用户
	 * 
	 * @param userId
	 * @param m
	 * @return
	 */
	@RequestMapping("/viewUser")
	public String viewUser(Integer userId, Model m) {
		if (userId != null) {
			Login login = loginService.findOne(userId);
			User user = userService.findOne(userId);
			UserRole ur = new UserRole();
			ur.setUserId(userId);
			Role role = roleService.findOne(userRoleService.find(ur, 1).get(0).getRoleId());
			m.addAttribute("login", login);
			m.addAttribute("user", user);
			m.addAttribute("userId", userId);
			m.addAttribute("role", role);
		}
		return "/back/yhgl/system/view";
	}

	/**
	 * 删除系统用户
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping("/delSysUser")
	@ResponseBody
	public JuiResult delSysUser(Integer userId) {
		JuiResult rs = new JuiResult();
		try {
			userManageService.deleteSysUser(userId);
			rs.setMessage("删除成功");
			rs.setStatusCode(JuiResult.SUCCESS);
			rs.setCallbackType(JuiResult.CB_CLOSE);
			LoggerUtils.deleteLogger(LoggerModule.YHGL, "系统用户管理——删除用户，用户id：" + userId);
		} catch (Exception e) {
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("删除失败");
			logger.error("删除系统用户出错", e);
		}
		return rs;
	}

}
