package com.tmser.tr.uc.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.common.bo.QueryObject.JOINTYPE;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.meta.bo.Menu;
import com.tmser.tr.manage.meta.service.MenuService;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.SessionKey;

@Controller
@RequestMapping("/jy/uc")
public class MenuController extends AbstractController {
	
	@Resource
	private MenuService menuService;
	
	
	/**
	 * 获取子菜单
	 * @param mid
	 * @return
	 */
	@RequestMapping("/listChild")
	@ResponseBody
	public Result listChildMenu(@RequestParam(value="mid",required=false) Integer mid){
		Result rs = new Result();
		rs.setCode(-1);
		if(mid != null){
			UserSpace us = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
			Menu model =  new Menu();
			model.addAlias("m");
			model.addCustomCulomn("m.*");
			model.setParentid(mid);
			model.setIsNormal(true);
			model.addJoin(JOINTYPE.INNER, "RoleMenu um").on("m.id = um.menuId and um.roleId= :roleId and um.isDel = :isDel");
			model.addOrder("m.sort");
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("roleId", us.getRoleId());
			params.put("isDel", 0);
			model.addCustomCondition("",params);
			rs.setData((Serializable) menuService.findAll(model));
			rs.setCode(0);
		}
		return  rs;
	}
	
}
