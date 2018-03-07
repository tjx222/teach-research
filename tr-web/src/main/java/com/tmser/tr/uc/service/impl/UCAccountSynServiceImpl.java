/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.BookSync;
import com.tmser.tr.manage.meta.bo.Menu;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.meta.service.BookSyncService;
import com.tmser.tr.manage.meta.service.MenuService;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.Login;
import com.tmser.tr.uc.bo.Role;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserMenu;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.LoginService;
import com.tmser.tr.uc.service.PasswordService;
import com.tmser.tr.uc.service.RoleService;
import com.tmser.tr.uc.service.SchoolYearService;
import com.tmser.tr.uc.service.UCAccountSynService;
import com.tmser.tr.uc.service.UserMenuService;
import com.tmser.tr.uc.service.UserService;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.uc.vo.UCAccountInfo;
import com.tmser.tr.utils.SecurityCode;
import com.tmser.tr.utils.SecurityCode.SecurityCodeLevel;

/**
 * <pre>
 * uc账号同步Service
 * </pre>
 *
 * @author wangdawei
 * @version $Id: UCAccountSynServiceImpl.java, v 1.0 2015年8月19日 下午3:19:55
 *          wangdawei Exp $
 */
@Service
@Transactional
public class UCAccountSynServiceImpl implements UCAccountSynService {

  @Autowired
  private LoginService loginService;
  @Autowired
  private UserService userService;
  @Autowired
  private PasswordService passwordService;
  @Autowired
  private UserSpaceService userSpaceService;
  @Autowired
  private MenuService menuService;
  @Autowired
  private UserMenuService userMenuService;
  @Autowired
  private SchoolYearService schoolYearService;
  @Autowired
  private RoleService roleService;
  @Autowired
  private OrganizationService orgService;
  @Autowired
  private BookSyncService bookSyncService;

  /**
   * 优课账号同步
   * 
   * @param ucAccountInfo
   * @see com.tmser.tr.uc.service.UCAccountSynService#ucAccountSynchronize(com.tmser.tr.uc.vo.UCAccountInfo)
   */
  @Override
  public void ucAccountSynchronize(UCAccountInfo ucAccountInfo) {
    Login login = loginService.findByCellPhone(ucAccountInfo.getAccount());
    if (login == null) {// 不存在则新增
      // 新增登陆信息
      String loginName = getUniqueLoginName();
      String salt = SecurityCode.getSecurityCode(8, SecurityCodeLevel.Hard,
          false);
      String newPassword = passwordService.encryptPassword(loginName,
          ucAccountInfo.getPassword(), salt);
      login = new Login();
      login.setLoginname(loginName);
      login.setPassword(newPassword);
      login.setCellphone(ucAccountInfo.getAccount());
      login.setSalt(salt);
      login.setIsAdmin(false);
      login.setEnable(1);
      login.setDeleted(false);
      login = loginService.save(login);
      // 新增用户信息
      User user = new User();
      user.setId(login.getId());
      user.setNickname(ucAccountInfo.getName());
      user.setName(ucAccountInfo.getName());
      user.setCellphone(ucAccountInfo.getAccount());
      user.setCellphoneValid(true);
      user.setCellphoneView(true);
      user.setAppId(1);
      if (ucAccountInfo.getSex() != null) {
        user.setSex(ucAccountInfo.getSex());
      }
      if (ucAccountInfo.getEmail() != null) {
        user.setMail(ucAccountInfo.getEmail());
      }
      user.setUserType(User.SCHOOL_USER);
      user.setMailValid(false);
      user.setMailView(false);
      user.setIsFamousTeacher(0);
      user.setCrtDttm(new Date());
      user.setLastupDttm(new Date());
      userService.save(user);
    } else {// 存在则更新密码
      String newPass = passwordService.encryptPassword(login.getLoginname(),
          ucAccountInfo.getPassword(), login.getSalt());
      login.setPassword(newPass);
      loginService.update(login);
    }
  }

  /**
   * 生成唯一的非存在的用户名
   * 
   * @return
   */
  private String getUniqueLoginName() {
    String loginName = "gd_"
        + SecurityCode.getSecurityCode(8, SecurityCodeLevel.Simple, false);
    Login login = loginService.findOneByLoginName(loginName);
    if (login != null) {
      loginName = getUniqueLoginName();
    }
    return loginName;
  }

  /**
   * 完善优课账号信息
   * 
   * @param ucAccountInfo
   * @see com.tmser.tr.uc.service.UCAccountSynService#complementUCUserInfo(com.tmser.tr.uc.vo.UCAccountInfo)
   */
  @Override
  public void complementUCUserInfo(UCAccountInfo ucAccountInfo) {
    // 更新user
    User user = (User) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_USER);
    Organization org = orgService.findOne(ucAccountInfo.getOrgId());
    Integer schoolYear = schoolYearService.getCurrentSchoolYear();// 学年
    User model = new User();
    model.setId(user.getId());
    model.setOrgId(ucAccountInfo.getOrgId());
    model.setOrgName(org.getName());
    userService.update(model);
    // 创建用户空间
    Map<Integer, String> spaceMap = new HashMap<Integer, String>();
    List<UserSpace> userSpaceArray = ucAccountInfo.getUserSpaceList();
    for (UserSpace userSpace : userSpaceArray) {
      if (userSpace != null && userSpace.getSysRoleId() != null) {
        // 根据 角色+年级+科目 去重
        if (spaceMap.get(userSpace.getSysRoleId()) != null
            && (userSpace.getSysRoleId() + "_" + userSpace.getGradeId() + "_"
                + userSpace.getSubjectId())
                    .equals(spaceMap.get(userSpace.getSysRoleId()))) {
          continue;
        }
        userSpace.setUsername(user.getName());
        userSpace.setUserId(user.getId());

        String homeUrl = roleService
            .findRoleTypeBySysRoleId(userSpace.getSysRoleId()).getHomeUrl();
        userSpace.setSpaceHomeUrl(homeUrl);
        userSpace.setOrgId(ucAccountInfo.getOrgId());
        MetaRelationship mr = MetaUtils.getPhaseMetaProvider()
            .getMetaRelationshipByPhaseId(userSpace.getPhaseId());
        userSpace.setPhaseType(mr.getEid());
        userSpace.setPhaseId(mr.getId());
        Integer usePosition = null;
        if (org.getType().intValue() == (Organization.SCHOOL)) {
          usePosition = 2;
        } else {
          usePosition = 1;
        }
        List<Role> roleList = roleService
            .findRoleListByUseOrgId(ucAccountInfo.getOrgId(), usePosition); // 根据学校获取所有角色，没有就获取系统默认的学校角色集合
        for (Role r : roleList) {
          if (r.getSysRoleId().intValue() == userSpace.getSysRoleId()
              .intValue()) {
            userSpace.setRoleId(r.getId()); // 加入角色id
          }
        }
        if (userSpace.getSysRoleId().intValue() == SysRole.JYZR.getId()
            .intValue()
            || userSpace.getSysRoleId().intValue() == SysRole.JYY.getId()
                .intValue()) { // 教研主任或教研员
          userSpace.setGradeId(0);
          if (userSpace.getSysRoleId().intValue() == SysRole.JYZR.getId()
              .intValue()) {
            userSpace.setSubjectId(0);
          }
        } else if (userSpace.getSysRoleId().intValue() == SysRole.XZ.getId()
            .intValue()
            || userSpace.getSysRoleId().intValue() == SysRole.ZR.getId()
                .intValue()) {
          userSpace.setGradeId(0);
          userSpace.setSubjectId(0);
        } else if (userSpace.getSysRoleId().intValue() == SysRole.XKZZ.getId()
            .intValue()) {// 学科组长
          userSpace.setGradeId(0);
        } else if (userSpace.getSysRoleId().intValue() == SysRole.NJZZ.getId()
            .intValue()) {// 年级组长
          userSpace.setSubjectId(0);
        } else if (userSpace.getSysRoleId().intValue() == SysRole.TEACHER
            .getId().intValue()) {// 老师，需要加入bookId
          userSpace.setSpaceHomeUrl(homeUrl);
          BookSync book = getBookForRegister(userSpace.getSubjectId(),
              userSpace.getGradeId(), userSpace.getFlags());
          userSpace.setBookId(book.getComId());
        }
        userSpace.setSort(1);
        userSpace.setIsDefault(false);
        userSpace.setEnable(1);
        if (org.getType() == Organization.SCHOOL) {
          userSpace.setSchoolYear(schoolYear);
        }
        userSpaceService.save(userSpace);
        // 创建用户功能菜单权限
        if (spaceMap.get(userSpace.getSysRoleId()) == null) {
          List<Menu> menuList = menuService
              .getMenuListByRole(userSpace.getRoleId());
          for (Menu m : menuList) {
            UserMenu userMenu = new UserMenu();
            userMenu.setSysRoleId(userSpace.getRoleId());
            userMenu.setMenuId(m.getId());
            userMenu.setUserId(user.getId());
            if (userMenuService.findOne(userMenu) == null) {
              userMenu.setDisplay(true);
              userMenu.setSort(m.getSort());
              userMenu.setName(m.getName());
              userMenuService.save(userMenu);
            }
          }
        }
        spaceMap.put(userSpace.getSysRoleId(), userSpace.getSysRoleId() + "_"
            + userSpace.getGradeId() + "_" + userSpace.getSubjectId());
      }
    }
    WebThreadLocalUtils.setSessionAttrbitue(SessionKey.CURRENT_USER, null);// 更新session
  }

  /**
   * 根据年级、学科和版本获取book
   * 
   * @param subjectId
   * @param gradeId
   * @param flags
   * @return
   */
  private BookSync getBookForRegister(Integer subjectId, Integer gradeId,
      String formatName) {
    Integer term = schoolYearService.getCurrentTerm();// 学期
    BookSync book = new BookSync();
    book.setSubjectId(subjectId);
    book.setGradeLevelId(gradeId);
    book.setFormatName(formatName);
    List<BookSync> bookList = bookSyncService.findAll(book);
    if (bookList.size() > 1) {
      if (term.intValue() == 0) {// 上学期
        for (BookSync b : bookList) {
          if (b.getFasciculeId().intValue() == 176) {
            return b;
          }
        }
      } else if (term.intValue() == 1) { // 下学期
        for (BookSync b : bookList) {
          if (b.getFasciculeId().intValue() == 177) {
            return b;
          }
        }
      }
    }
    if (bookList.size() <= 0) {
      return null;
    }
    return bookList.get(0);
  }

}
