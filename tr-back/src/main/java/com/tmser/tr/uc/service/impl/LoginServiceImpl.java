/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tmser.tr.common.bo.BaseObject;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.manage.meta.bo.Menu;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.Login;
import com.tmser.tr.uc.bo.Role;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.dao.LoginDao;
import com.tmser.tr.uc.exception.UcException;
import com.tmser.tr.uc.exception.UserBlockedException;
import com.tmser.tr.uc.exception.UserNotExistsException;
import com.tmser.tr.uc.exception.UserPasswordNotMatchException;
import com.tmser.tr.uc.log.LoginLogUtils;
import com.tmser.tr.uc.service.LoginService;
import com.tmser.tr.uc.service.PasswordService;
import com.tmser.tr.uc.service.PermissionService;
import com.tmser.tr.uc.service.RoleService;
import com.tmser.tr.uc.service.UserService;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * <pre>
 * 登录服务
 * </pre>
 *
 * @author tmser
 * @version $Id: LoginServiceImpl.java, v 1.0 2015年1月31日 下午1:56:04 tmser Exp $
 */
@Service
@Transactional
public class LoginServiceImpl extends AbstractService<Login, Integer> implements LoginService {

  @Resource
  private LoginDao loginDao;

  @Resource
  private PasswordService passwordService;

  @Resource
  private RoleService roleService;

  @Resource
  private PermissionService permissionService;

  @Resource
  private UserService userService;

  @Value("#{config.getProperty('admin_user_id','0')}")
  private Integer adminId;

  /**
   * 按用户名查找
   * 
   * @param username
   * @return
   * @see com.tmser.tr.uc.service.LoginService#findByUsername(java.lang.String)
   */
  @Override
  public Login findByUsername(String username) {
    if (StringUtils.isEmpty(username)) {
      return null;
    }
    Login model = new Login();
    model.setLoginname(username);
    return loginDao.getOne(model);
  }

  /**
   * 按邮箱查找用户
   * 
   * @param email
   * @return
   * @see com.tmser.tr.uc.service.LoginService#findByEmail(java.lang.String)
   */
  @Override
  public Login findByEmail(String email) {
    if (StringUtils.isEmpty(email)) {
      return null;
    }
    Login model = new Login();
    model.setMail(email);
    return loginDao.getOne(model);
  }

  /**
   * 按手机号查找
   * 
   * @param mobilePhoneNumber
   * @return
   * @see com.tmser.tr.uc.service.LoginService#findByMobilePhoneNumber(java.lang.String)
   */
  @Override
  public Login findByCellPhone(String cellphone) {
    if (StringUtils.isEmpty(cellphone)) {
      return null;
    }
    Login model = new Login();
    model.setCellphone(cellphone);
    return loginDao.getOne(model);
  }

  /**
   * @param principal
   * @param password
   * @return
   * @throws UcException
   * @see com.tmser.tr.uc.service.LoginService#login(java.lang.String,
   *      java.lang.String)
   */
  @Override
  public Login login(String principal, String password) throws UcException {
    if (StringUtils.isEmpty(principal) || StringUtils.isEmpty(password)) {
      LoginLogUtils.log(principal, "loginError", "principal is empty");
      throw new UserNotExistsException();
    }
    // 密码如果不在指定范围内 肯定错误
    if (password.length() < Login.PASSWORD_MIN_LENGTH || password.length() > Login.PASSWORD_MAX_LENGTH) {
      LoginLogUtils.log(principal, "loginError", "password length error! password is between {} and {}",
          Login.PASSWORD_MIN_LENGTH, Login.PASSWORD_MAX_LENGTH);

      throw new UserPasswordNotMatchException();
    }

    Login login = null;

    if (maybeUsername(principal)) {
      login = findByUsername(principal);
    }

    if (login == null && maybeEmail(principal)) {
      login = findByEmail(principal);
    }

    if (login == null && maybeMobilePhoneNumber(principal)) {
      login = findByCellPhone(principal);
    }

    if (login == null || Boolean.TRUE.equals(login.getDeleted())) {
      LoginLogUtils.log(principal, "loginError", "user is not exists!");

      throw new UserNotExistsException();
    }

    passwordService.validate(login, password);

    if (BaseObject.ENABLE != login.getEnable()) {
      LoginLogUtils.log(principal, "loginError", "user is blocked!");
      throw new UserBlockedException("");// TODO 获取禁用理由
    }

    LoginLogUtils.log(principal, "loginSuccess", "");
    return login;
  }

  private boolean maybeUsername(String principal) {
    if (!principal.matches(Login.USERNAME_PATTERN)) {
      return false;
    }
    // 如果用户名不在指定范围内也是错误的
    if (principal.length() < Login.USERNAME_MIN_LENGTH || principal.length() > Login.USERNAME_MAX_LENGTH) {
      return false;
    }

    return true;
  }

  private boolean maybeEmail(String principal) {
    if (!principal.matches(Login.EMAIL_PATTERN)) {
      return false;
    }
    return true;
  }

  private boolean maybeMobilePhoneNumber(String principal) {
    if (!principal.matches(Login.MOBILE_PHONE_NUMBER_PATTERN)) {
      return false;
    }
    return true;
  }

  /**
   * @return
   * @see com.tmser.tr.common.service.BaseService#getDAO()
   */
  @Override
  public BaseDAO<Login, Integer> getDAO() {
    return loginDao;
  }

  /**
   * @param spaceid
   * @return
   * @see com.tmser.tr.uc.service.LoginService#toWorkSpace(java.lang.Integer)
   */
  @Override
  public String toWorkSpace(Integer spaceid) {
    return "/jy/back/index";
  }

  public void setLoginDao(LoginDao loginDao) {
    this.loginDao = loginDao;
  }

  public void setPasswordService(PasswordService passwordService) {
    this.passwordService = passwordService;
  }

  /**
   * @param uid
   * @return
   * @see com.tmser.tr.uc.service.LoginService#findStringRoles(java.lang.Integer)
   */
  @Override
  public Set<String> findStringRoles(Integer uid) {
    Set<String> roleSet = new HashSet<>();
    if (uid != null) {
      List<Role> rs = roleService.findRoleByUserid(uid, null);
      for (Role r : rs) {
        if (r != null)
          roleSet.add(r.getRoleCode());
      }
    }
    return roleSet;
  }

  /**
   * @param uid
   * @return
   * @see com.tmser.tr.uc.service.LoginService#findStringPermissions(java.lang.Integer)
   */
  @Override
  public Set<String> findStringPermissions(Integer uid) {
    Set<String> permissionSet = new HashSet<>();
    if (uid != null) {
      UserSpace us = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
      if (adminId.equals(us.getUserId()) && SysRole.ADMIN.getId().equals(us.getSysRoleId())) {
        // 特权用户。作为系统初始化的用户使用，拥有所有权限。
        permissionSet.add("*");
      } else {
        List<Role> rs = roleService.findRoleByUserid(uid, null);
        for (Role r : rs) {
          List<Menu> menus = roleService.findMenuByRole(r.getId());
          for (Menu menu : menus) {
            permissionSet.add(menu.getCode());
          }
        }
      }

    }
    return permissionSet;
  }

  /**
   * 根据用户登录名获取登录信息
   * 
   * @param loginname
   * @return
   * @see com.tmser.tr.uc.service.LoginService#getLoginByLoginname(java.lang.String)
   */
  @Override
  public Login getLoginByLoginname(String loginname) {
    Login login = new Login();
    login.setLoginname(loginname);
    List<Login> loginList = find(login, 1);
    if (loginList != null && loginList.size() > 0) {
      return loginList.get(0);
    }
    return null;
  }

  /**
   * @param loginname
   * @param psd
   * @return
   * @see com.tmser.tr.uc.service.LoginService#newLogin(java.lang.String,
   *      java.lang.String)
   */
  @Override
  public Login newLogin(String loginname, String psd) {
    Login l = new Login();
    l.setLoginname(loginname);
    l.setPassword(psd);
    l.setDeleted(false);
    l.setEnable(1);
    l.setIsAdmin(false);
    return l;
  }

}
