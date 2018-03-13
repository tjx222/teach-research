/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.yhgl.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.back.logger.LoggerModule;
import com.tmser.tr.back.schconfig.teach.service.PublisherManageService;
import com.tmser.tr.back.yhgl.service.BackUserManageService;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.BookSync;
import com.tmser.tr.manage.meta.bo.PublishRelationship;
import com.tmser.tr.manage.meta.service.BookService;
import com.tmser.tr.manage.org.bo.Area;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.AreaService;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.uc.bo.Login;
import com.tmser.tr.uc.bo.Role;
import com.tmser.tr.uc.bo.RoleType;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserRole;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.LoginService;
import com.tmser.tr.uc.service.RoleService;
import com.tmser.tr.uc.service.RoleTypeService;
import com.tmser.tr.uc.service.UserRoleService;
import com.tmser.tr.uc.service.UserService;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.uc.vo.UserSearchModel;
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 * 学校用户管理Controller
 * </pre>
 * 
 * @author tmser
 * @version $Id: SchoolUserController.java, v 1.0 2015年9月24日 上午11:37:35 tmser
 *          Exp $
 */
@Controller
@RequestMapping("/jy/back/yhgl")
public class SchoolUserController extends AbstractController {

  @Autowired
  private BackUserManageService userManageService;
  @Autowired
  private AreaService areaService;
  @Autowired
  private OrganizationService organizationService;
  @Autowired
  private UserService userService;
  @Autowired
  private UserSpaceService userSpaceService;

  @Autowired
  private LoginService loginService;
  @Autowired
  private RoleService roleService;

  @Autowired
  private UserRoleService userRoleService;

  @Autowired
  private RoleTypeService roleTypeService;

  @Autowired
  private BookService bookService;

  @Autowired
  private PublisherManageService publishManageService;

  /**
   * 添加或修改-学校用户
   * 
   * @return
   */
  @RequestMapping("/addOrEditSchoolUser")
  public String addOrEditSchoolUser(Model m, User user) {
    Organization org = organizationService.findOne(user.getOrgId());
    m.addAttribute("org", org);
    if (user.getId() != null) {
      Login login = loginService.findOne(user.getId());
      user = userService.findOne(user.getId());
      m.addAttribute("login", login);
      m.addAttribute("user", user);
    }
    return viewName("school/addOrEditSchoolUser");
  }

  /**
   * 增加用户身份
   * 
   * @param model
   * @param us
   * @return
   */
  @RequestMapping("/addSchUserRole")
  public String addSchUserRole(Model model, Integer userId) {
    // 通过机构ID获得部门、学段、角色
    User user = userService.findOne(userId);
    // 所有角色
    List<Role> roles = roleService.findRoleListByUseOrgId(user.getOrgId(), 2);
    UserRole umodel = new UserRole();
    umodel.setUserId(userId);
    List<UserRole> userRoles = userRoleService.findAll(umodel);
    Map<Integer, Boolean> oldRoles = new HashMap<>();
    for (UserRole userRole : userRoles) {
      oldRoles.put(userRole.getRoleId(), Boolean.TRUE);
    }
    model.addAttribute("roles", roles);
    model.addAttribute("oldRoles", oldRoles);
    model.addAttribute("user", user);
    return viewName("school/addSchUserRole");
  }

  @RequestMapping("/addSchUserSpace")
  public String addSchUserSpace(Model model, UserSpace us) {
    // 通过机构ID获得部门、学段、角色
    User user = userService.findOne(us.getUserId());
    Map<String, Object> datamap = userManageService.findSpaceDataByOrgId(user);
    model.addAttribute("deptOrgs", datamap.get("deptOrgs"));
    model.addAttribute("phases", datamap.get("phases"));
    model.addAttribute("roles", datamap.get("roles"));
    model.addAttribute("us", us);
    return viewName("school/addSchUserSpace");
  }

  /**
   * 修改用户身份
   * 
   * @param model
   * @param us
   * @return
   */
  @RequestMapping("/editSchUserSpace")
  public String editSchUserRole(Model model, UserSpace us) {
    // 通过us获得身份的相关信息
    Map<String, Object> datamap = userManageService.findSpaceDataById(us);
    model.addAttribute("data", datamap);
    return viewName("school/editSchUserSpace");
  }

  /**
   * 所有的行政区域，显示区域树所用
   * 
   * @param areaId
   * @return
   */
  @ResponseBody
  @RequestMapping("/areaTree")
  public Object areaTree() {
    List<Area> arealist = areaService.findAll(new Area());
    return arealist;
  }

  /**
   * 查看用户身份详情
   * 
   * @param model
   * @param userId
   * @return
   */
  @RequestMapping("/detailUserSpace")
  public String detailUserSpace(Model model, UserSpace us) {
    Map<String, Object> datamap = userManageService.detailUserSpace(us);
    model.addAttribute("data", datamap);
    return viewName("school/detailUserSpace");
  }

  /**
   * 冻结取消冻结账号
   * 
   * @param model
   * @param id
   * @param enable
   * @return
   */
  @RequestMapping("/djUser")
  @ResponseBody
  public JuiResult djUser(Model model, Integer id, Integer enable) {
    JuiResult rs = new JuiResult();
    try {
      userManageService.djUser(id, enable);
      rs.setStatusCode(JuiResult.SUCCESS);
      if (enable == 0) {
        rs.setMessage("冻结成功！");
        LoggerUtils.updateLogger(LoggerModule.YHGL, "用户管理——冻结用户，用户ID：" + id);
      } else {
        LoggerUtils.updateLogger(LoggerModule.YHGL, "用户管理——解冻用户，用户ID：" + id);
        rs.setMessage("恭喜您，您已成功取消冻结！");
      }
    } catch (Exception e) {
      logger.error("冻结失败", e);
      rs.setStatusCode(JuiResult.FAILED);
      rs.setMessage("操作失败！");
    }
    return rs;
  }

  /**
   * 通过学段ID获得此学段下的年级、学科列表
   */
  @RequestMapping("/findMetaByPhase")
  @ResponseBody
  public Map<String, List<Meta>> findMetaByPhase(Model m, Integer phaseId, Integer orgId) {
    Map<String, List<Meta>> datas = new HashMap<String, List<Meta>>(2);
    Organization org = organizationService.findOne(orgId);
    datas.put("grades", MetaUtils.getOrgTypeMetaProvider().listAllGrade(org.getSchoolings(), phaseId));
    datas.put("subjects", MetaUtils.getPhaseSubjectMetaProvider().listAllSubject(orgId, phaseId,
        StringUtils.toIntegerArray(org.getAreaIds().substring(1, org.getAreaIds().lastIndexOf(",")), ",")));
    return datas;
  }

  /**
   * 通过学段id\学科ID\年级ID\出版社获得书籍
   */
  @RequestMapping("/findBooks")
  @ResponseBody
  public List<BookSync> findBooks(Model m, BookSync book) {
    return bookService.findBookSync(book);
  }

  /**
   * 通过学段id\学科ID获得出版社
   */
  @RequestMapping("/findPublishs")
  @ResponseBody
  public List<Meta> findPublishs(Model m, PublishRelationship pr) {
    pr.addOrder("sort asc");
    if (pr.getAreaId() != null) {
      pr.setScope("area");
    } else if (pr.getOrgId() != null) {
      pr.setScope("org");
    }
    return publishManageService.listAllPubliserMetaByScope(pr);
  }

  /**
   * 所有的行政区域，显示区域树所用
   * 
   * @param m
   * @param type
   * @return
   */
  @ResponseBody
  @RequestMapping("/findSchool")
  public Object findSchool(Integer areaId, Integer type) {
    List<Organization> orglist = organizationService.getOrgByAreaId(areaId, type);
    return orglist;
  }

  /**
   * 查看用户信息
   * 
   * @param model
   * @param orgId
   * @param id
   * @return
   */
  @RequestMapping("/lookSchoolUser")
  public String lookSchoolUser(Model model, Integer orgId, Integer id) {
    Organization org = organizationService.findOne(orgId);
    model.addAttribute("org", org);
    Login login = loginService.findOne(id);
    User user = userService.findOne(id);
    model.addAttribute("login", login);
    model.addAttribute("user", user);
    return viewName("school/schoolUserLook");
  }

  /**
   * 查询给用户所赋所有身份信息
   * 
   * @param model
   * @param userId
   * @return
   */
  @RequestMapping("/manageSchUserSpace")
  public String manageSchUserSpace(Model model, UserSpace us) {
    us.addCustomCulomn("id,departmentId,roleId,spaceName,schoolYear,enable,phaseId");
    List<UserSpace> userSpaceList = userSpaceService.findAll(us);
    if (us.getOrgId() == null && us.getUserId() != null) {
      User u = userService.findOne(us.getUserId());
      us.setOrgId(u.getOrgId());
    }
    model.addAttribute("uslist", userSpaceList);
    model.addAttribute("us", us);
    return viewName("school/userSpaceIndex");
  }

  /**
   * 查询给用户所赋所有身份信息
   * 
   * @param model
   * @param userId
   * @return
   */
  @RequestMapping("/manageSchUserRole")
  public String manageSchUserRole(Model model, Integer userId) {
    User u = userService.findOne(userId);
    UserRole umodel = new UserRole();
    umodel.setUserId(userId);
    List<UserRole> userRoles = userRoleService.findAll(umodel);
    model.addAttribute("userRoles", userRoles);
    model.addAttribute("user", u);
    return viewName("school/userRoleIndex");
  }

  @RequestMapping("/enableUserRole")
  @ResponseBody
  public JuiResult enableUserRole(Integer id) {
    JuiResult rs = new JuiResult();
    try {
      UserSpace us = userSpaceService.findOne(id);
      UserSpace model = new UserSpace();
      model.setId(id);
      if (us.getEnable() == 1) {
        model.setEnable(0);
      } else {
        model.setEnable(1);
      }
      userSpaceService.update(model);
      rs.setStatusCode(JuiResult.SUCCESS);
      rs.setData(id);
      rs.setMessage("操作成功!");
      LoggerUtils.updateLogger(LoggerModule.YHGL, "用户管理——用户身份状态变更，用户ID：{}", id);
    } catch (Exception e) {
      logger.error("更改用户身份状态失败", e);
      rs.setStatusCode(JuiResult.FAILED);
      rs.setMessage("对不起，操作失败!");
    }
    return rs;
  }

  /**
   * 为用户重置密码
   * 
   * @param model
   * @param id
   * @return
   */
  @RequestMapping("/resetUserPass")
  @ResponseBody
  public JuiResult resetUserPass(Model model, Integer id) {
    JuiResult rs = new JuiResult();
    try {
      userManageService.resetUserPass(id);
      rs.setStatusCode(JuiResult.SUCCESS);
      rs.setMessage("恭喜您，您已成功重置密码!<br>重置后的密码为：123456");
      LoggerUtils.updateLogger(LoggerModule.YHGL, "用户管理——重置密码为123456，用户ID：{}", id);
    } catch (Exception e) {
      logger.error("重置密码失败", e);
      rs.setStatusCode(JuiResult.FAILED);
      rs.setMessage("对不起，重置密码失败!");
    }
    return rs;
  }

  /**
   * 保存学校用户
   * 
   * @return
   */
  @RequestMapping("/saveSchUser")
  @ResponseBody
  public JuiResult saveSchUser(User user, Login login) {
    JuiResult rs = new JuiResult();
    try {
      if (user.getId() == null) {// 新增
        user = userManageService.saveUserAccount(User.SCHOOL_USER, login, user);
        LoggerUtils.insertLogger(LoggerModule.YHGL, "用户管理——添加学校用户，用户ID：" + user.getId());
      } else {
        userManageService.updateUserAccount(login, user);
        LoggerUtils.updateLogger(LoggerModule.YHGL, "用户管理——修改学校用户，用户ID：" + user.getId());
      }
      rs.setStatusCode(JuiResult.SUCCESS);
      rs.setMessage("操作成功");
      rs.setCallbackType(JuiResult.CB_CLOSE);
    } catch (Exception e) {
      rs.setStatusCode(JuiResult.FAILED);
      rs.setMessage("操作失败");
      logger.error("用户操作出错", e);
    }
    return rs;
  }

  /**
   * 保存用户身份
   * 
   * @param model
   * @param userId
   * @param deptIds
   * @return
   */
  @RequestMapping("/saveSchUserSpace")
  @ResponseBody
  public JuiResult saveSchUserSpace(Model model, UserSpace us, String[] deptIds) {
    JuiResult rs = new JuiResult();
    try {
      int state = userManageService.saveSchUserSpace(us, deptIds);
      if (state == 1) {
        LoggerUtils.insertLogger(LoggerModule.YHGL, "用户管理——新增或修改学校用户身份，身份ID：" + us.getId());
        rs.setStatusCode(JuiResult.SUCCESS);
        rs.setMessage("身份操作成功！");
      } else if (state == 0) {
        rs.setStatusCode(JuiResult.FAILED);
        rs.setMessage("身份重复，请重新选择身份！");
      }
    } catch (Exception e) {
      rs.setStatusCode(JuiResult.FAILED);
      rs.setMessage("身份操作失败！");
      logger.error("--用户身份保存失败--", e);
    }
    return rs;
  }

  /**
   * 保存用户身份
   * 
   * @param model
   * @param userId
   * @param deptIds
   * @return
   */
  @RequestMapping("/saveSchUserRole")
  @ResponseBody
  public JuiResult saveSchUserRole(Model model, Integer userId, Integer[] roleIds) {
    JuiResult rs = new JuiResult();
    try {
      if (userId != null) {
        UserRole umodel = new UserRole();
        umodel.setUserId(userId);
        List<UserRole> olduserRoles = userRoleService.findAll(umodel);
        Map<Integer, UserRole> oldRolemap = new HashMap<>();
        for (UserRole userRole : olduserRoles) {
          oldRolemap.put(userRole.getRoleId(), userRole);
        }

        List<UserRole> urs = new ArrayList<>();
        for (Integer roleId : roleIds) {
          UserRole ur = new UserRole();
          ur.setUserId(userId);
          ur.setRoleId(roleId);
          if (oldRolemap.containsKey(roleId)) {
            oldRolemap.remove(roleId);
          } else {
            urs.add(ur);
          }
        }

        if (oldRolemap.size() > 0) {
          for (UserRole userRole : oldRolemap.values()) {
            userRoleService.delete(userRole.getId());
          }
        }

        if (urs.size() > 0) {
          userRoleService.batchSave(urs);
        }

        LoggerUtils.insertLogger(LoggerModule.YHGL, "用户管理——新增或修改学校用户角色，用户D：{}", userId);
        rs.setStatusCode(JuiResult.SUCCESS);
        rs.setMessage("角色操作成功！");
      }
    } catch (Exception e) {
      rs.setStatusCode(JuiResult.FAILED);
      rs.setMessage("角色操作失败！");
      logger.error("--用户角色保存失败--", e);
    }
    return rs;
  }

  /**
   * 保存用户身份
   * 
   * @param model
   * @param id
   * @return
   */
  @RequestMapping("/delUserRole")
  @ResponseBody
  public JuiResult delSchUserRole(Model model, Integer id) {
    JuiResult rs = new JuiResult();
    try {
      if (id != null) {
        userRoleService.delete(id);
        LoggerUtils.insertLogger(LoggerModule.YHGL, "用户管理——删除学校用户角色,ID：{}", id);
        rs.setStatusCode(JuiResult.SUCCESS);
        rs.setMessage("角色删除成功！");
      }
    } catch (Exception e) {
      rs.setStatusCode(JuiResult.FAILED);
      rs.setMessage("角色操作失败！");
      logger.error("--用户角色保存失败--", e);
    }
    return rs;
  }

  /**
   * 通过学校机构ID搜索所有用户信息
   * 
   * @param m
   * @param orgId
   * @return
   */
  @RequestMapping("/schoolUserIndex")
  public String schoolUserIndex(Model m, UserSearchModel usm) {
    if (StringUtils.isBlank(usm.order())) {
      usm.addOrder(" u.crtDttm desc");
    }
    PageList<Map<String, Object>> schuserlist = null;
    if (usm.getAreaId() != null && usm.getAreaId() != 0) {
      usm.setOrgIds(organizationService.getOrgIdsByAreaId(usm.getAreaId()));
      if (usm.getOrgIds() == null || usm.getOrgIds().size() == 0) {
        schuserlist = new PageList<Map<String, Object>>(null, usm.getPage());
      }
    }

    usm.setUserType(0);// 学校用户
    TreeMap<String, String> phaseMap = new TreeMap<String, String>();
    if (usm.getOrgId() != null) {
      // 所有学段
      Organization org = organizationService.findOne(usm.getOrgId());
      if (org != null) {
        String phases[] = org.getPhaseTypes().split(",");
        for (int i = 0; i < phases.length; i++) {
          if (StringUtils.isNotEmpty(phases[i])) {
            phaseMap.put(phases[i],
                MetaUtils.getPhaseMetaProvider().getMetaRelationship(Integer.parseInt(phases[i])).getName());
          }
        }
      }
      // 默认显示全部
      /*
       * if (usm.getPhaseId()==null && phaseMap.size() == 1) {
       * usm.setPhaseId(Integer.parseInt(phaseMap.firstKey())); }
       */
    }
    if (schuserlist == null) {// 无需查询
      schuserlist = userService.findUsersBySearchModel(usm);
    }

    m.addAttribute("schuserlist", schuserlist);
    m.addAttribute("model", usm);

    if (usm.getOrgId() != null) {
      // 所有角色
      List<Role> rolelist = roleService.findRoleListByUseOrgId(usm.getOrgId(), 2);
      m.addAttribute("roles", rolelist);
      // 获得当前的机构下的所有年级和学科
      /*
       * Map<String, String> subjectmap =
       * metaRelationshipService.findSubjectByOrgId(usm.getOrgId());
       * TreeMap<String, String> grademap =
       * metaRelationshipService.findGradeByOrgId(usm.getOrgId());
       */
      Map<Integer, String> appIdMap = new HashMap<>();
      appIdMap.put(0, "教研平台");

      m.addAttribute("phases", phaseMap);
      /*
       * m.addAttribute("subjects", subjectmap); m.addAttribute("grades",
       * grademap);
       */
      m.addAttribute("appIds", appIdMap);
      return viewName("school/schuserIndex");
    } else {
      return viewName("school/userIndex");
    }
  }

  /**
   * 学校用户管理入口地址
   * 
   * @param m
   * @return
   */
  @RequestMapping("/schUserIndex")
  public String schUserIndex(Model m) {
    Organization org = (Organization) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_ORG);
    if (org == null) {
      return viewName("school/schManageIndex");
    } else {
      UserSearchModel usm = new UserSearchModel();
      usm.setOrgId(org.getId());
      return schoolUserIndex(m, usm);
    }
  }

  /**
   * 通过名称搜索所有学校
   * 
   * @param m
   * @param type
   * @return
   */
  @ResponseBody
  @RequestMapping("/searAllSch")
  public Object searAllSch(String name, Integer type) {
    Map<String, Object> orglist = organizationService.getAllOrgByName(name, type);
    return orglist;
  }

  /**
   * 获得RoleType对象
   */
  @RequestMapping("/getRoleTypeObj")
  @ResponseBody
  public RoleType getRoleTypeObj(Model m, @RequestParam(value = "sysRoleId", required = true) Integer sysRoleId) {
    return roleTypeService.getRoleTypeByCode(sysRoleId);

  }
}
