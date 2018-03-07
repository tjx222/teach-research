/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.controller.ws;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.service.UCAccountSynService;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.uc.vo.UCAccountInfo;
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 *   优课账号同步接口
 * </pre>
 *
 * @author daweiwbs
 * @version $Id: AccountSynchronize.java, v 1.0 2015年8月14日 上午11:34:12 daweiwbs Exp $
 */
@Controller
@RequestMapping("/jy/ws/uc")
public class UCAccountSynchronize extends AbstractController{

	@Autowired
	private UCAccountSynService ucAccountSynService;
	
	/**
	 * 优课账号同步（手机号为账号）
	 * @param account
	 * @param name
	 * @param password
	 * @param sex
	 * @param email
	 * @return
	 */
	@RequestMapping("/ucAccountSynchro")
	@ResponseBody
	public Map<String,Object> ucAccountSynchro(UCAccountInfo ucAccountInfo){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		if(StringUtils.isBlank(ucAccountInfo.getAccount()) || StringUtils.isBlank(ucAccountInfo.getName()) || StringUtils.isBlank(ucAccountInfo.getPassword())){
			resultMap.put("status",405);
			return resultMap;
		}
		try {
			if(ucAccountInfo.getSex().intValue()==1){
				ucAccountInfo.setSex(0);
			}else if(ucAccountInfo.getSex().intValue()==2){
				ucAccountInfo.setSex(1);
			}
			//同步账号
			ucAccountSynService.ucAccountSynchronize(ucAccountInfo);
			resultMap.put("status",200);
		} catch (Exception e) {
			logger.error("广东优课账号同步出错", e);
			resultMap.put("status",500);
		}
		return resultMap;
	}
	
	/**
	 * 补充优课用户的用户空间等信息，然后跳转到首页
	 * @param orgId
	 * @param userSpaceAdd
	 * @return
	 */
	@RequestMapping(value="/complementUserInfo",method=RequestMethod.POST)
	public String complementUserInfo(UCAccountInfo ucAccountInfo){
		//完善优课账号信息
		User user = (User)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		ucAccountSynService.complementUCUserInfo(ucAccountInfo);
		if("area".equalsIgnoreCase(ucAccountInfo.getTotype()) && User.SCHOOL_USER.equals(user.getUserType())) {
			return "redirect:/jy/area/home";
		}
		return "redirect:/";
	}
}
