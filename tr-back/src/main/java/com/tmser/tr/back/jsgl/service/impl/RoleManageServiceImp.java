package com.tmser.tr.back.jsgl.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.back.jsgl.service.RoleManageService;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.manage.meta.dao.MenuDao;
import com.tmser.tr.uc.bo.Role;
import com.tmser.tr.uc.bo.RoleMenu;
import com.tmser.tr.uc.dao.RoleDao;
import com.tmser.tr.uc.service.RoleMenuService;

/**
 * 
 * <pre>
 *
 * </pre>
 *
 * @author 川子
 * @version $Id: RoleServiceImp.java, v 1.0 2015年9月22日 下午1:54:06 川子 Exp $
 */
@Service
@Transactional
public class RoleManageServiceImp extends AbstractService<Role, Integer> implements RoleManageService {

  @Autowired
  private RoleMenuService roleMenuService;

  @Autowired
  private RoleDao roleDao;

  @Autowired
  private MenuDao menuDao;

  @Override
  public BaseDAO<Role, Integer> getDAO() {
    return roleDao;
  }

  @Override
  public int addOrEditNeed(Role role, List<Integer> pv) {
    List<RoleMenu> mnlist = new ArrayList<RoleMenu>();
    role.setIsDel(false);
    if (role.getId() != null) {// 更新
      roleDao.update(role);
      // 先删除-role_menu ---再批量添加
      RoleMenu roleMenu = new RoleMenu();
      roleMenu.setRoleId(role.getId());
      List<RoleMenu> oldMenulist = roleMenuService.findAll(roleMenu);
      // 再批量添加
      add: for (Integer mid : pv) {
        RoleMenu roleMenu3 = new RoleMenu();
        roleMenu3.setRoleId(role.getId());
        roleMenu3.setMenuId(mid);
        Iterator<RoleMenu> rmIt = oldMenulist.iterator();
        while (rmIt.hasNext()) {
          RoleMenu rm = rmIt.next();
          if (rm.getMenuId().equals(mid)) { // 更新已经存在的memu
            if (RoleMenu.DELETE == rm.getIsDel()) {
              RoleMenu model = new RoleMenu();
              model.setId(rm.getId());
              model.setIsDel(RoleMenu.NORMAL);
              roleMenuService.update(model);
            }
            rmIt.remove();
            continue add;
          }
        }
        roleMenu3.setPermBname(menuDao.get(mid).getName());
        roleMenu3.setIsDel(RoleMenu.NORMAL);
        mnlist.add(roleMenu3);
      }

      roleMenuService.batchSave(mnlist); // 新增的
      for (RoleMenu rm : oldMenulist) { // 删除多于的
        roleMenuService.delete(rm.getId());
      }
      return 2;
    } else {
      Role rl = new Role();
      rl.setSolutionId(role.getSolutionId());
      rl.addCustomCulomn("id");
      rl.setRoleName(role.getRoleName());
      Role rs = roleDao.getOne(rl);
      if (rs != null) {
        return 1;
      } else {
        if (role.getSolutionId() != null) {
          rl = new Role();
          rl.setSysRoleId(role.getSysRoleId());
          rl.setSolutionId(0);
          rl.addCustomCulomn("id");
          Role defaultRole = roleDao.getOne(rl);
          if (defaultRole != null) {
            role.setRelId(defaultRole.getId());
          }
        } else {
          role.setSolutionId(0);
        }
        role = roleDao.insert(role);
        Integer id = role.getId();
        if (pv != null) {
          for (Integer mid : pv) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(id);
            roleMenu.setMenuId(mid);
            roleMenu.setPermBname(menuDao.get(mid).getName());
            roleMenu.setIsDel(RoleMenu.NORMAL);
            mnlist.add(roleMenu);
          }
          roleMenuService.batchSave(mnlist);
        }
        return 3;
      }
    }

  }

  @Override
  public boolean syncRole(Integer solutionId) {
    Role role = new Role();
    role.setSolutionId(0);
    role.setIsDel(false);
    List<Role> systemRoleList = findAll(role);
    role.setSolutionId(solutionId);
    role.setIsDel(null);
    List<Role> solutionRoleList = findAll(role);
    for (int j = 0; j < solutionRoleList.size(); j++) {
      for (Role r : systemRoleList) {
        if (r.getId().equals(systemRoleList.get(j).getRelId())) {
          systemRoleList.remove(j);
          break;
        }
      }
    }

    if (systemRoleList.size() == 0) {// 没有要同步的角色
      return false;
    } else {
      List<RoleMenu> roleMenuToAdd = new ArrayList<RoleMenu>();
      for (Role r : systemRoleList) {
        Integer roleId = r.getId();
        r.setRelId(roleId);
        r.setId(null);
        r.setSolutionId(solutionId);
        save(r);

        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setRoleId(roleId);
        roleMenu.setIsDel(RoleMenu.NORMAL);
        List<RoleMenu> sysMenulist = roleMenuService.findAll(roleMenu);
        for (RoleMenu rm : sysMenulist) {
          rm.setRoleId(r.getId());
          rm.setId(null);
          roleMenuToAdd.add(rm);
        }
      }

      roleMenuService.batchSave(roleMenuToAdd);
    }
    return true;
  }

}
