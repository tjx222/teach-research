package com.tmser.tr.uc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.manage.meta.bo.Menu;
import com.tmser.tr.manage.meta.dao.MenuDao;
import com.tmser.tr.uc.bo.RoleType;
import com.tmser.tr.uc.dao.RoleTypeDao;
import com.tmser.tr.uc.service.RoleTypeService;

@Service
@Transactional
public class RoleTypeServiceImpl extends AbstractService<RoleType, Integer> implements RoleTypeService{

	@Autowired
	private RoleTypeDao roleTypeDao;
	
	@Autowired
	private MenuDao menuDao;
	
	@Override
	public BaseDAO<RoleType, Integer> getDAO() {
		return roleTypeDao;
	}

	@Override
	public List<Menu> getMenuRoleTypeRoleId(Integer sysRoleId) {
		Menu m = new Menu();
		m.setSysRoleId(sysRoleId);
		List<Menu> list = menuDao.listAll(m);
		return list;
	}

	@Override
	public List<Menu> findMenuCheckById(String perms) {
		List <Menu> muList = new ArrayList<Menu>();
		String[] str = null;
		if(perms != null){
			str = perms.split(",");
		}
		for (int i = 0;str != null&& i < str.length; i++) {
			Menu menu = menuDao.get(Integer.valueOf(str[i]));
			muList.add(menu);
		}
		return muList;
	}

	@Override
	public RoleType getRoleTypeByCode(Integer code) {
		RoleType rt = new RoleType();
		rt.setCode(code);
		RoleType one = this.roleTypeDao.getOne(rt);
		return one;
	}

	@Override
	public List<Menu> findMenuListByParentId(Integer pid) {
		Menu mu = new Menu();
		mu.setParentid(pid);
		return menuDao.listAll(mu);
	}

 	@Override
	public List<Menu> getMenuByMenuIds(String... menuIdStr) {
		List<Menu> menus=new ArrayList<>();
		for(String menuId :menuIdStr){
			int id=Integer.parseInt(menuId);
			menus.add(menuDao.get(id));
		}
		return menus;
	}

}
