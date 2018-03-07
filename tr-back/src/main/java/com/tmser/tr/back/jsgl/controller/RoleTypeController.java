package com.tmser.tr.back.jsgl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.meta.bo.Menu;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.RoleType;
import com.tmser.tr.uc.service.RoleTypeService;

/**
 * 
 * <pre>
 *
 * </pre>
 *
 * @author 川子
 * @version $Id: RoleTypeController.java, v 1.0 2015年9月1日 下午1:28:23 川子 Exp $
 */
@Controller
@RequestMapping("/jy/back/jsgl/jslx")
public class RoleTypeController extends AbstractController{

	@Autowired
	private RoleTypeService roleTypeService;
	
	/**
	 * 角色类型展示
	 * @param m
	 * @return
	 */
	@RequestMapping("show_roleType")
	public String roleTypeList(RoleType rt,Model m){
		rt.addOrder(" sort asc ");
		List<RoleType> rtList = roleTypeService.find(rt, 50);
		m.addAttribute("rtList", rtList);
		return "/back/jsgl/jslx/show_roleType" ;
	}
	
	/**
	 * 去添加和修改页面
	 * @param id
	 * @param m
	 * @return
	 */
	@RequestMapping("goAddOrUpdate")
	public String goAddOrUpdate(Integer id,Model m){
		if(id!=null){
			RoleType roleType = this.roleTypeService.findOne(id);
			m.addAttribute("roleType", roleType);
		}
		SysRole[] values = SysRole.values();
		m.addAttribute("xtjs", values);
		return "/back/jsgl/jslx/saveOrUpdateRoleType";
	}
	
	/**
	 * 添加和修改角色类型 
	 * @param roleType
	 * @return
	 */
	@RequestMapping("addOrUpdateRoleType")
	@ResponseBody
	public JuiResult addOrUpdateRoleType(RoleType roleType){
		
		JuiResult rs = new JuiResult();
		try{
			if(roleType.getId()!=null){
				this.roleTypeService.update(roleType);
				rs.setRel("roleType");
				rs.setMessage("保存成功！");
			}else{
				RoleType rt = new RoleType();
				rt.setCode(roleType.getCode());
				List<RoleType> rtlist = roleTypeService.find(rt,1);
				if(rtlist !=null && rtlist.size()>0){
					rs.setRel("roleType");
					rs.setMessage("该角色类型已存在，请重新添加！");
				}else{
					this.roleTypeService.save(roleType);
					rs.setRel("roleType");
					rs.setMessage("保存成功！");
				}
			}
		} catch (Exception e) {
			rs.setMessage("保存失败！");
			logger.error("角色类型保存失败", e);
		}
		
		return rs;
	}

	/**
	 * 删除角色类型
	 * @param id
	 * @param m
	 * @return
	 */
	@RequestMapping("delRoleType")
	@ResponseBody
	public JuiResult delSchType(Integer id,Model m){
		JuiResult rs = new JuiResult();
		try {
			roleTypeService.delete(id);
			rs.setNavTabId("roleType");
			rs.setCallbackType("");
			rs.setMessage("删除成功！");
		} catch (Exception e) {
			rs.setMessage("删除失败！");
			logger.error("学制删除失败！",e);
		}
		return rs;
	}
	
	/**
	 * 根据sysRoleId查询menu集合
	 * @param sysRoleId
	 * @return
	 */
	@RequestMapping("getMenuRoleTypeByRoleId")
	@ResponseBody
	public List<Menu> getMenuRoleTypeRoleId(Integer sysRoleId){
		
		return roleTypeService.getMenuRoleTypeRoleId(sysRoleId);
	}
	
	/**
	 * 查看角色类型信息
	 * @return
	 */
	@RequestMapping("toSee")
	public String seeRoleType(Integer id,Model m){
		RoleType roleType = roleTypeService.findOne(id);
		String perms = roleType.getRoleTypePerm();
		List<Menu> menuList = roleTypeService.findMenuCheckById(perms);
		m.addAttribute("menuList", menuList);
		m.addAttribute("roleType", roleType);
		return "/back/jsgl/jslx/seeRoleType";
	}
	
	@RequestMapping("findMenuListByParentId")
	@ResponseBody
	public List<Menu> findMenuListByParentId(Integer pid){
		return roleTypeService.findMenuListByParentId(pid);
	}
}
