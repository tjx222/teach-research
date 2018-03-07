/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.controller.ws;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.uc.bo.App;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.service.AppService;
import com.tmser.tr.uc.service.LoginService;
import com.tmser.tr.uc.service.SsoService;
import com.tmser.tr.uc.service.UserService;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * <pre>
 *  文轩用户对接
 * </pre>
 *
 * @author tmser
 * @version $Id: WenXuanLoginController.java, v 1.0 2015年7月16日 上午10:27:31 tmser Exp $
 */
@Controller
@RequestMapping("/jy/ws/sso")
public class LogincodeAccessController extends AbstractController{
	
	
	@Resource
	private LoginService loginService;
	
	@Resource
	private UserService userService;
	
	@Autowired
	private AppService appService;
	
	@Autowired
	private SsoService ssoService;
	
	@RequestMapping(value={"","/access"})
	public String access() {
		Map<String,String> params = new HashMap<String,String>();
		
		//String appid =  ,String appkey,@RequestParam(value="uid")String code
		Result rs = new Result();
		Enumeration<String>  names = WebThreadLocalUtils.getRequest().getParameterNames();
		while(names.hasMoreElements()){
			String name = names.nextElement();
			params.put(name, WebThreadLocalUtils.getParameter(name));
		}
		App app = null;
		try {
			app = check(ssoService.getAppId(params), ssoService.getAppkey(params));
			if(app == null){
				rs.setCode(0);
				rs.setMsg("appkey 认证失败");
				return "redirect:/";
			}
			
			if(ssoService.validate(params) && login(app,params)){
				rs.setData("/");
			}else{
				rs.setCode(0);
				rs.setMsg("用户登录失败");
			}
			
			
			
		} catch (Exception e) {
			rs.setCode(0);
			rs.setMsg("获取菜单失败");
			logger.error("login failed",e);
		}
		
		if(rs.getCode() == 1){
			Subject subject = SecurityUtils.getSubject();
			User user = userService.findOne((Integer)subject.getPrincipal());
			if(user!=null && "area".equalsIgnoreCase(params.get("totype")) && User.SCHOOL_USER.equals(user.getUserType())) {
				return "redirect:/jy/area/home";
			}
			return "forward:"+rs.getData();
		}else{
			SecurityUtils.getSubject().logout();
			return "redirect:" + (StringUtils.isEmpty(app.getLoginUrl()) ? "/": app.getLoginUrl());
		}
		
	}
	
	protected boolean login(App app,Map<String,String> params){
		User user = (User)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		try {
			Subject subject = SecurityUtils.getSubject();
			if(user != null ){
				subject.logout();
			}
			
			subject = SecurityUtils.getSubject();
			String logincode = ssoService.logincode(params);
			if(StringUtils.isNotEmpty(logincode)){
				LogincodeToken token = new LogincodeToken(app,
						LogincodeUserRealm.LOGINCODE_LOGIN_KEY,logincode,params);
				subject.login(token);
				String flag = params.get("flag"); 
				if(StringUtils.isNotEmpty(flag)){ 
					subject.getSession().setAttribute("_sess_flag_", flag);
				}
				subject.getSession().setAttribute("_login_params_", params);
				return true;
			}

			return false;
		}catch(Exception e){
			logger.error("wenxuan validate has happend error.", e);
		}
		
		return false;
	}
	
	
	protected App check(String appid,String appkey){
		if(StringUtils.isNotEmpty(appid)){
			App appModel = new App();
			appModel.setAppid(appid);
			appModel.setAppkey(appkey);
			List<App> apps = appService.find(appModel, 1);
			
			return apps !=null && apps.size() > 0 ? apps.get(0) : null;
		}
		
		return null;
	}
	
	
	
}
