package com.tmser.tr.uc.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
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
import com.tmser.tr.uc.bo.UserMenu;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.UserMenuService;
import com.tmser.tr.uc.utils.SessionKey;

@Controller
@RequestMapping("/jy/uc")
public class MenuController extends AbstractController {
	
	@Resource
	private UserMenuService userMenuService;
	

	@Resource
	private MenuService menuService;
	
	@RequestMapping("/listMenu")
	@ResponseBody
	public List<UserMenu> menuList(){
		UserSpace us = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		return  userMenuService.findUserMenuByUser(us.getUserId(), us.getRoleId(),false);
		
	}
	
	@RequestMapping("/delMenu")
	@ResponseBody
	public Result delMenu(@RequestParam(value="mid",required=false) Integer mid){
		Result rs = new Result();
		rs.setCode(-1);
		UserMenu um = userMenuService.findOne(mid);
		UserSpace us = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		if(um != null && um.getUserId().equals(us.getUserId())){
			UserMenu model = new UserMenu();
			model.setId(mid);
			model.setDisplay(false);
			userMenuService.update(model);
			rs.setCode(0);
			WebThreadLocalUtils.setSessionAttrbitue(SessionKey.CURRENT_MENU_LIST,
					userMenuService.findUserMenuByUser(us.getUserId(), us.getRoleId(),true));
		}
		return  rs;
	}
	
	@RequestMapping("/addMenu")
	@ResponseBody
	public Result addMenu(@RequestParam(value="mid",required=false) Integer mid){
		Result rs = new Result();
		rs.setCode(-1);
		UserMenu um = userMenuService.findOne(mid);
		UserSpace us = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		if(um != null && um.getUserId().equals(us.getUserId())){
			UserMenu model = new UserMenu();
			model.setId(mid);
			model.setDisplay(true);
			userMenuService.update(model);
			rs.setCode(0);
			WebThreadLocalUtils.setSessionAttrbitue(SessionKey.CURRENT_MENU_LIST,
					userMenuService.findUserMenuByUser(us.getUserId(), us.getRoleId(),true));
		}
		return  rs;
	}
	
	@RequestMapping("/sortMenu")
	@ResponseBody
	public Result sortMenu(@RequestParam(value="mids",required=false) String mids){
		Result rs = new Result();
		rs.setCode(-1);
		UserSpace us = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		int um = userMenuService.sortUser(mids, us.getUserId());
		if(um > 0){
			WebThreadLocalUtils.setSessionAttrbitue(SessionKey.CURRENT_MENU_LIST,
					userMenuService.findUserMenuByUser(us.getUserId(), us.getRoleId(),true));
			rs.setCode(0);
		}
		return  rs;
	}
	
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
