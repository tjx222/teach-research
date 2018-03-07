/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.rzgl.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmser.tr.back.logger.bo.OperateLog;
import com.tmser.tr.back.logger.service.OperateLogService;
import com.tmser.tr.common.bo.QueryObject;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.uc.bo.LoginLog;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.LoginLogService;
import com.tmser.tr.uc.service.UserService;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.uc.vo.LogSearchVo;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 延方
 * @version $Id: LoggerManageController.java, v 1.0 2015年10月28日 下午2:40:59 延方 Exp $
 */
@Controller
@RequestMapping("/jy/back/rzgl/")
public class LoggerManageController extends AbstractController {
   
	@Autowired 
    private LoginLogService loginLogService;
	
	@Autowired 
    private OperateLogService operateLogService;
	
	@Autowired 
	private  UserService  userService;
	
	@Autowired 
	private  UserSpaceService  userSpaceService;

	/**
	 * 登陆日志展示
	*/
	@RequestMapping("logginLogIndex")
	public String logginLogIndex(LoginLog loginLog,Model m,LogSearchVo lsv){
		PageList<LoginLog> pageList=loginLogService.getLoginLogList(loginLog,getLogIndex(lsv.getSearchUserName(), lsv.getSearchName(),lsv.getSysRoleIdVo()));
		m.addAttribute("orgId", CurrentUserContext.getCurrentSpace().getOrgId());
		m.addAttribute("pageList", pageList);
		m.addAttribute("page", pageList.getPage());
		m.addAttribute("lsv", lsv);
		m.addAttribute("sysRoleId", loginLog.getSysRoleId());
		m.addAttribute("sysRoleIdNow", CurrentUserContext.getCurrentSpace().getSysRoleId());
		return viewName("logginLogIndex");
	}
	
	/**
	 * 操作日志展示
	*/
	@RequestMapping("operateLogIndex")
	public String operateLogIndex(OperateLog operateLog,Model m,LogSearchVo lsv){
		PageList<OperateLog> pageList = operateLogService.getOperateLogIndex(operateLog, 
				getLogIndex(lsv.getSearchUserName(), lsv.getSearchName(),lsv.getSysRoleIdVo()));
		List<OperateLog> oList=operateLogService.getSysRoleIdList(pageList);
		m.addAttribute("oList", oList);
		m.addAttribute("orgId", CurrentUserContext.getCurrentSpace().getOrgId());
		m.addAttribute("pageList", pageList);
		m.addAttribute("page", pageList.getPage());
		m.addAttribute("lsv", lsv);
		m.addAttribute("sysRoleIdNow", CurrentUserContext.getCurrentSpace().getSysRoleId());
		return viewName("operateLogIndex");
	}
	
	/**
	 * 根据条件模糊查询日志列表
	*/
	public List<Integer> getLogIndex(String loginname,String name,Integer sysRoleIdVo){
		List<Integer> ids = new ArrayList<Integer>();
		Map<String,Object> paramMap = new HashMap<String, Object>();
        if (loginname==null) {
        	paramMap.put("loginname", "%%");
		}else {
			paramMap.put("loginname", "%"+loginname+"%");
		}
		if (name==null) {
			paramMap.put("name", "%%");
		}else {
			paramMap.put("name", "%"+name+"%");
		}
		if (sysRoleIdVo==null) {
			List<User> uList=null;
			User user=new User();
			user.addAlias("u");
			user.addCustomCulomn("u.id");
			user.addJoin(QueryObject.JOINTYPE.INNER, "Login l").on("u.name like :name and l.loginname like :loginname and u.id=l.id");
			user.addCustomCondition("", paramMap);
			uList=userService.findAll(user);
			for (User u:uList) {
				ids.add(u.getId());
			}
		}else {
			paramMap.put("sysRoleId", sysRoleIdVo);
			UserSpace userSpace=new UserSpace();
			userSpace.addAlias("us");
			userSpace.addCustomCulomn("us.userId");
			userSpace.addJoin(QueryObject.JOINTYPE.LEFT, "Login l").on("us.userId=l.id");
			userSpace.addCustomCondition("and us.username like :name and l.loginname like :loginname and us.sysRoleId=:sysRoleId", paramMap);
			List<UserSpace> usList=userSpaceService.findAll(userSpace);
			for (UserSpace us:usList) {
				ids.add(us.getUserId());
			}
		}
		
		return ids;
		
	}
	
}
