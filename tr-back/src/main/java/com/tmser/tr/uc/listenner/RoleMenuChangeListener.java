/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.listenner;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.tmser.tr.common.listener.Listenable;
import com.tmser.tr.common.listener.ListenableEvent;
import com.tmser.tr.common.listener.Listener;
import com.tmser.tr.uc.bo.RoleMenu;
import com.tmser.tr.uc.bo.UserMenu;
import com.tmser.tr.uc.service.UserMenuService;

/**
 * <pre>
 *  角色菜单变更监听器
 *  监听角色菜单权限变更事件，在角色菜单权限变更后，修改当前学年绑定改角色的用户菜单
 * </pre>
 *
 * @author tmser
 * @version $Id: RoleMenuChangeListener.java, v 1.0 2017年10月31日 下午2:47:55 tmser
 *          Exp $
 */
@Component
public class RoleMenuChangeListener implements Listener {

  private static final Logger logger = LoggerFactory.getLogger(RoleMenuChangeListener.class);

  @Autowired
  private UserMenuService userMenuService;

  @Resource(name = "msgThreadPool")
  private TaskExecutor excutor;

  /**
   * @param event
   * @see com.tmser.tr.common.listener.Listener#lifecycleEvent(com.tmser.tr.common.listener.ListenableEvent)
   */
  @Override
  public void lifecycleEvent(ListenableEvent event) {
    String eventType = event.getType();
    if (Listenable.AFTER_DELETE_EVENT.equals(eventType)) {
      if (event.getData() instanceof RoleMenu) {
        deleteUserMenu((RoleMenu) event.getData());
      }
    } else if (Listenable.AFTER_ADD_EVENT.equals(eventType)) {
      if (event.getData() instanceof RoleMenu) {
        addUserMenu((RoleMenu) event.getData());
      }
    } else if (Listenable.BEFORE_BATCH_INSERT_EVENT.equals(eventType)) {
      if (event.getData() instanceof List) {
        @SuppressWarnings("unchecked")
        List<RoleMenu> roleMenus = (List<RoleMenu>) event.getData();
        for (RoleMenu roleMenu : roleMenus) {
          addUserMenu(roleMenu);
        }
      }
    }
  }

  private void addUserMenu(final RoleMenu menu) {
    Runnable task = new Runnable() {
      @Override
      public void run() {
        if (menu != null && menu.getRoleId() != null && menu.getMenuId() != null) {
          UserMenu um = new UserMenu();
          um.addAlias("um");
          um.addCustomCulomn("um.userId");
          um.setSysRoleId(menu.getRoleId());
          um.addGroup(" um.userId HAVING um.userId not in (SELECT m.userId from"
              + " UserMenu m where m.sysRoleId = :roleId and m.menuId=:menuId) ");
          um.buildCondition("").put("roleId", menu.getRoleId()).put("menuId", menu.getMenuId());

          List<UserMenu> allUm = userMenuService.findAll(um);
          if (allUm != null && allUm.size() > 0) {
            logger.debug("need add usermenu nums: {}, menuId:{},roleId:{}", allUm.size(), menu.getMenuId(),
                menu.getRoleId());
            for (UserMenu userMenu : allUm) {
              userMenu.setMenuId(menu.getMenuId());
              userMenu.setDisplay(true);
              userMenu.setName(menu.getPermBname());
              userMenu.setSort(99);
              userMenu.setSysRoleId(menu.getRoleId());
            }
            userMenuService.batchSave(allUm);
          }

          logger.info("sync add usermenu over.");
        }

      }
    };
    if (excutor != null) {
      excutor.execute(task);
    } else {
      new Thread(task).start();
    }

  }

  private void deleteUserMenu(final RoleMenu menu) {
    Runnable task = new Runnable() {
      @Override
      public void run() {
        UserMenu um = new UserMenu();
        um.addCustomCulomn("id");
        um.setSysRoleId(menu.getRoleId());
        um.setMenuId(menu.getMenuId());

        List<UserMenu> allUm = userMenuService.findAll(um);
        if (allUm != null && allUm.size() > 0) {
          logger.debug("need delete usermenu nums: {}, menuId:{},roleId:{}", allUm.size(), menu.getMenuId(),
              menu.getRoleId());
          for (UserMenu userMenu : allUm) {
            userMenuService.delete(userMenu.getId());
          }

          logger.info("sync delete usermenu over.");
        }
      }
    };

    if (excutor != null) {
      excutor.execute(task);
    } else {
      new Thread(task).start();
    }

  }

  /**
   * @param supportsType
   * @return
   * @see com.tmser.tr.common.listener.Listener#supports(java.lang.Object[])
   */
  @Override
  public boolean supports(Object[] supportsType) {
    for (Object o : supportsType) {
      if (o instanceof Class) {
        return RoleMenu.class.equals(o);
      }
    }
    return false;
  }

}
