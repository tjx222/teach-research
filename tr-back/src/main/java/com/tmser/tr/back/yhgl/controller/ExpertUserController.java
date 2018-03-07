/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.yhgl.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.back.logger.LoggerModule;
import com.tmser.tr.back.yhgl.service.BackUserManageService;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.uc.bo.Login;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.service.LoginService;
import com.tmser.tr.uc.service.UserService;

/**
 * <pre>
 * 专家用户管理Controller
 * </pre>
 * 
 * @author wy
 * @version $Id: expertUserController.java, v 1.0 2015年9月29日 上午11:37:35 wy Exp $
 */
@Controller
@RequestMapping("/jy/back/yhgl")
public class ExpertUserController extends AbstractController {

	@Autowired
	private BackUserManageService backUserManageService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private UserService userService;
	@Autowired
	private LoginService loginService;

	/**
	 * 查询所有专家用户
	 * 
	 * @param m
	 * @param orgId
	 * @return
	 */
	@RequestMapping("/expertUserIndex")
	public String expertUserIndex(Model m, User user) {
		// if(StringUtils.isNotEmpty(user.getName())){
		// String name = Encodes.urlDecode(user.getName());
		// }
		m.addAttribute("searchStr", user.getName());
		if (!StringUtils.isBlank(user.getName())) {
			user.setName(SqlMapping.LIKE_PRFIX + user.getName() + SqlMapping.LIKE_PRFIX);
		}
		if (user.order() == null) {
			user.addOrder(" crtDttm desc");
		}
		PageList<User> expertUserlist = userService.findByPage(user);
		for (User u : expertUserlist.getDatalist()) {
			Login logUser = loginService.findOne(u.getId());
			u.setEnable(logUser.getEnable());
			u.setNickname(logUser.getLoginname());
		}
		m.addAttribute("expertUserlist", expertUserlist);
		m.addAttribute("model", user);
		return viewName("expert/expertUserload");
	}

	@RequestMapping("/addOrEditExpertUser")
	public String addOrEditExpertUser(Model model, Integer id, Integer orgId) {
		Organization org = organizationService.findOne(orgId);
		model.addAttribute("org", org);
		Login loginUser = loginService.findOne(id);
		User user = userService.findOne(id);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("user", user);
		return viewName("expert/expertUserAddOrEdit");
	}

	/**
	 * 保存专家用户代码
	 * 
	 * @return
	 */
	@RequestMapping("/saveExpertUser")
	@ResponseBody
	public JuiResult saveExpertUser(User user, Login login) {
		JuiResult rs = new JuiResult();
		try {
			if (user.getId() == null) {// 新增
				User saveUserAccount = backUserManageService.saveUserAccount(User.EXPERT_USER, login, user);
				backUserManageService.saveExpertUserSpace(saveUserAccount, user);
				LoggerUtils.insertLogger(LoggerModule.YHGL, "专家用户管理——增加用户，用户id：" + saveUserAccount.getId());
			} else {
				backUserManageService.updateUserAccount(login, user);
				backUserManageService.saveExpertUserSpace(null, user);
				LoggerUtils.insertLogger(LoggerModule.YHGL, "专家用户管理——修改用户，用户id：" + user.getId());
			}
			rs.setStatusCode(JuiResult.SUCCESS);
			rs.setMessage("操作成功");
			rs.setCallbackType(JuiResult.FORWARD_CONFIRM);
		} catch (Exception e) {
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("操作失败");
			logger.error("用户操作出错", e);
		}
		return rs;
	}

	/**
	 * 查看用户信息
	 * 
	 * @param model
	 * @param unitId
	 * @param id
	 * @return
	 */
	@RequestMapping("/lookExpertUser")
	public String lookExpertUser(Model model, Integer unitId, Integer id) {
		Organization org = organizationService.findOne(unitId);
		model.addAttribute("org", org);
		Login loginUser = loginService.findOne(id);
		User user = userService.findOne(id);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("user", user);
		return viewName("expert/expertUserLook");
	}
}
