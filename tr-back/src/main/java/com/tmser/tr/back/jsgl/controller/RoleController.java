package com.tmser.tr.back.jsgl.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.back.jsgl.service.RoleManageService;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.meta.bo.Menu;
import com.tmser.tr.manage.meta.service.MenuService;
import com.tmser.tr.uc.bo.Role;
import com.tmser.tr.uc.bo.RoleMenu;
import com.tmser.tr.uc.bo.RoleType;
import com.tmser.tr.uc.service.RoleMenuService;
import com.tmser.tr.uc.service.RoleService;
import com.tmser.tr.uc.service.RoleTypeService;

/**
 * 
 * <pre>
 *
 * </pre>
 *
 * @author 川子
 * @version $Id: RoleController.java, v 1.0 2015年9月2日 下午3:48:11 川子 Exp $
 */
@Controller
@RequestMapping("/jy/back/jsgl")
public class RoleController extends AbstractController{

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleManageService roleManageService;
	
	@Autowired
	private RoleTypeService roleTypeService;
	
	@Autowired
	private RoleMenuService roleMenuService;
	
	@Autowired
	private MenuService menuService;
	/**
	 * 角色列表
	 * @param m
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("show_role")
	public String roleList(Role role,Model m){
		if(role.getSolutionId() == null){
			role.setSolutionId(0);
		}
		role.pageSize(20);
		String roleName = role.getRoleName();
		role.addOrder(" id desc");
		if(StringUtils.isNotEmpty(role.getRoleName())){
			role.setRoleName(SqlMapping.LIKE_PRFIX +roleName+ SqlMapping.LIKE_PRFIX);
		}
		role.setRoleName(roleName);
		m.addAttribute("data",roleService.findByPage(role));
		m.addAttribute("model", role);
		return viewName("/js/show_role");
	}
	/**
	 * 查看角色
	 * @param id
	 * @param m
	 * @return
	 */
	@RequestMapping("toSeeRole")
	public String toSeeRole(Integer id,Model m){
		List<Menu> menulist = new ArrayList<Menu>();
		Role role = roleService.findOne(id);
		RoleMenu roleMenu = new RoleMenu();
		roleMenu.setRoleId(role.getId());
		roleMenu.setIsDel(RoleMenu.NORMAL);
		List<RoleMenu> rmList = roleMenuService.findAll(roleMenu);
		for (RoleMenu roleMenu2 : rmList) {
			Menu findOne = menuService.findOne(roleMenu2.getMenuId());
			menulist.add(findOne);
		}
		m.addAttribute("role", role);
		m.addAttribute("menulist", menulist);
		return viewName("/js/toSeeRole");
	}
	/**
	 * 去添加和修改页面
	 * @param id
	 * @param m
	 * @return
	 */
	@RequestMapping("goAddOrEdit")
	public String goAddOrUpdate(Role model,Model m){
		
		RoleType roleType = new RoleType();
		if(model.getSolutionId() != null && model.getSolutionId() != 0){
			roleType.buildCondition(" and usePosition != :position ")
			.put("position", RoleType.APPLICATION_SYSTEM);
		}
		
		List<RoleType> rtList = roleTypeService.findAll(roleType);
		m.addAttribute("rtList", rtList);
		if(model.getId() != null){
			model = roleService.findOne(model.getId());
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setRoleId(model.getId());
			roleMenu.setIsDel(RoleMenu.NORMAL);
			List<RoleMenu> findRm = roleMenuService.findAll(roleMenu);
			String str = "";
			String perm = "";
			for (RoleMenu roleMenu2 : findRm) {
				str += roleMenu2.getMenuId()+",";
				perm += roleMenu2.getPermBname()+",";
			}
			if(str.length()>0){
				str = str.substring(0, str.length()-1);
				perm = perm.substring(0, perm.length()-1);
			}
			m.addAttribute("str", str);
			m.addAttribute("perm", perm);
		}
		m.addAttribute("role", model);
		
		return viewName("/js/saveOrUpdateRole");
	}
	
	/**
	 * 根据角色类型id查询所对应的角色类型权限
	 * @param id
	 * @return
	 */
	@RequestMapping("getPermByRoleTypeId")
	@ResponseBody
	public List<Menu> getPermByRoleTypeId(Integer id){
		List<Menu> mList = new ArrayList<Menu>();
		RoleType rt = this.roleTypeService.getRoleTypeByCode(id);
		RoleType roleType = roleTypeService.findOne(rt.getId());
		String perms = roleType.getRoleTypePerm();
		String[] str = perms != null ? perms.split(",") : new String[]{};
		
		for (int i = 0; i < str.length; i++) {
			Integer vid = Integer.valueOf(str[i]);
			Menu m = menuService.findOne(vid);
			if(m != null){
				mList.add(m);
			}
			
		}
		return mList;
	}
	
	/**
	 * 添加和修改角色
	 * @param role
	 * @return
	 */
	@RequestMapping("addOrUpdateRole")
	@ResponseBody
	public JuiResult addOrUpdateRole(Role role,
			@RequestParam(value="menuIds",required=false)List<Integer> pv){
			JuiResult rs = new JuiResult();
			try {
				int i = roleManageService.addOrEditNeed(role, pv);
				if(i == 1){
					rs.setMessage("该角色名称已存在，请重新添加！");
				}else{
					rs.setRel("role");
					rs.setMessage("保存成功！");
				}
			} catch (Exception e) {
				rs.setMessage("保存失败！");
				logger.error("角色保存失败", e);
			}
			return rs;
	}

	/**
	 * 删除角色
	 * @param id
	 * @param m
	 * @return
	 */
	@RequestMapping("delRole")
	@ResponseBody
	public JuiResult delRole(Integer id,Model m){
		JuiResult rs = new JuiResult();
		try {
			roleService.delete(id);
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setRoleId(id);
			List<RoleMenu> rmlist = roleMenuService.findAll(roleMenu);
			for (RoleMenu roleMenu2 : rmlist) {
				roleMenuService.delete(roleMenu2.getId());
			}
			rs.setRel("role");
			rs.setCallbackType("");
			rs.setMessage("删除成功！");
		} catch (Exception e) {
			rs.setMessage("删除失败！");
			logger.error("角色删除失败！",e);
		}
		return rs;
	}
	
	@RequestMapping("jy")
	@ResponseBody
	public JuiResult jy(Boolean del,Integer id){
		JuiResult rs = new JuiResult();
		Role model = new Role();
		model.setIsDel(del);
		model.setId(id);
		roleService.update(model);
		rs.setMessage("操作成功！");
		return rs;
	}
	
	/***
	 * 方案角色同步
	 * @param sid
	 * @param m
	 * @return
	 */
	@RequestMapping("tongBu")
	@ResponseBody
	public JuiResult tongBu(Integer qid,Model m){
		JuiResult rs = new JuiResult();
		try {
			if(roleManageService.syncRole(qid)){
				rs.setRel("role");
				rs.setCallbackType("");
				rs.setMessage("同步刷新成功！");	
			}else{
				rs.setRel("role");
				rs.setCallbackType("");
				rs.setMessage("当前已是最新数据，无需同步！");
			}
		} catch (Exception e) {
			rs.setMessage("同步失败！");
			logger.error("方案权限同步失败！",e);
		}
		return rs;
	}
}
