package com.tmser.tr.uc.service;

import java.util.List;

import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.manage.meta.bo.Menu;
import com.tmser.tr.uc.bo.RoleType;

public interface RoleTypeService extends BaseService<RoleType, Integer>{

	List<Menu> getMenuRoleTypeRoleId(Integer sysRoleId);
	
	RoleType getRoleTypeByCode(Integer code);
	
	List<Menu> findMenuCheckById(String perms);
	
	List<Menu> findMenuListByParentId(Integer pid);
	
	List<Menu> getMenuByMenuIds(String... menuIdStr);
}
