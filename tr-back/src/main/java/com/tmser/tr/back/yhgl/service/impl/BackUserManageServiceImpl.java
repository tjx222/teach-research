/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.yhgl.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tmser.tr.back.schconfig.teach.service.PublisherManageService;
import com.tmser.tr.back.yhgl.service.BackUserManageService;
import com.tmser.tr.common.bo.QueryObject;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.FrontCacheUtils;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.bo.BookSync;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.meta.bo.PublishRelationship;
import com.tmser.tr.manage.meta.service.BookService;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.Login;
import com.tmser.tr.uc.bo.Role;
import com.tmser.tr.uc.bo.RoleType;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserManagescope;
import com.tmser.tr.uc.bo.UserRole;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.dao.UserDao;
import com.tmser.tr.uc.dao.UserManagescopeDao;
import com.tmser.tr.uc.dao.UserRoleDao;
import com.tmser.tr.uc.dao.UserSpaceDao;
import com.tmser.tr.uc.service.LoginService;
import com.tmser.tr.uc.service.PasswordService;
import com.tmser.tr.uc.service.RoleService;
import com.tmser.tr.uc.service.RoleTypeService;
import com.tmser.tr.uc.service.SchoolYearService;
import com.tmser.tr.uc.service.UserMenuService;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.Encodes;
import com.tmser.tr.utils.SecurityCode;
import com.tmser.tr.utils.SecurityCode.SecurityCodeLevel;

/**
 * <pre>
 * 后台用户管理service实现类
 * </pre>
 * 
 * @author tmser
 * @version $Id: BackUserManageServiceImpl.java, v 1.0 2015年9月24日 下午11:29:53
 *          tmser
 *          Exp $
 */
@Service
@Transactional
public class BackUserManageServiceImpl implements BackUserManageService {

  private static final Logger logger = LoggerFactory.getLogger(BackUserManageServiceImpl.class);

  @Autowired
  private UserDao userDao;
  @Autowired
  private UserSpaceDao userSpaceDao;
  @Autowired
  private LoginService loginService;
  @Autowired
  private PasswordService passwordService;
  @Autowired
  private ResourcesService resourceService;
  @Autowired
  private RoleService roleService;
  @Autowired
  private UserManagescopeDao userMangescopeDao;
  @Autowired
  private OrganizationService organizationService;
  @Autowired
  private UserRoleDao userRoleDao;
  @Autowired
  private UserMenuService userMenuService;
  @Autowired
  private RoleTypeService roleTypeService;
  @Autowired
  private SchoolYearService schoolYearService;
  @Autowired
  private BookService bookService;
  @Autowired
  private UserSpaceService userSpaceService;
  @Autowired
  private OrganizationService orgService;
  @Autowired
  private PublisherManageService publisherManageService;

  /**
   * 更新学年验证查询
   * 
   * @param orgId
   * @return
   * @see com.tmser.tr.back.yhgl.service.BackUserManageService#findUpUsers(java.lang.Integer)
   */
  @Override
  public Map<String, Object> findUpUsers(Integer orgId, Boolean force) {
    Map<String, Object> map = new HashMap<String, Object>();
    Calendar cal = Calendar.getInstance();
    int month = cal.get(Calendar.MONTH) + 1;
    int year = schoolYearService.getCurrentSchoolYear();
    // 学年跟新时间是7月1号-8月31号
    if (force || (month >= 7 && month <= 9)) {
      // 没有升级的用户id集合
      UserSpace us = new UserSpace();
      us.setOrgId(orgId);
      us.setEnable(UserSpace.ENABLE);
      us.setSchoolYear(year - 1);
      us.addCustomCulomn("distinct sp.userId,sp.username");
      us.addAlias("sp");
      us.buildCondition(
          "and sp.userId not in (select s.userId from UserSpace s where s.schoolYear = :curyear and s.orgId=:orgId)")
          .put("curyear", year).put("orgId", orgId);
      map.put("msg", "allow");
      map.put("content", userSpaceDao.listAll(us));
    } else {
      map.put("msg", "forbid");
      map.put("content", "手动更新学年只能在每年的7月1号至9月30号");
    }
    return map;
  }

  /**
   * 获取系统用户集合
   * 
   * @param search_name
   * @param search_role
   * @param page
   * @return
   * @see com.tmser.tr.back.yhgl.service.BackUserManageService#getSysUsers(java.lang.String,
   *      java.lang.Integer, com.tmser.tr.common.page.Page)
   */
  @Override
  public PageList<User> getSysUsers(User user) {
    user.setUserType(User.SYS_USER);
    user.addAlias("u");
    user.addCustomCulomn("u.*");
    if (user.getAppId() != null) {
      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("search_role", user.getAppId());
      user.addJoin(QueryObject.JOINTYPE.INNER, "UserSpace a").on("u.id = a.userId and a.roleId = :search_role");
      user.addCustomCondition("", paramMap);
    }
    user.setAppId(null);
    if (user.getName() != null && !"".equals(user.getName())) {
      user.setName(SqlMapping.LIKE_PRFIX + user.getName() + SqlMapping.LIKE_PRFIX);
    }
    user.addOrder("u." + user.order());
    PageList<User> userList = userDao.listPage(user);
    for (User temp : userList.getDatalist()) {
      Login login = loginService.findOne(temp.getId());
      temp.setFlags(login.getLoginname());
      temp.setEnable(login.getEnable());
      UserRole ur = new UserRole();
      ur.setUserId(temp.getId());
      ur = userRoleDao.getOne(ur);
      if (ur == null) {
        continue;
      }
      Role role = roleService.findOne(ur.getRoleId());
      if (role != null) {
        temp.setFlago(role.getRoleCode());
        temp.setAppId(role.getSysRoleId());
        temp.setRemark(role.getRoleName());
      }
    }
    return userList;
  }

  /**
   * 保存用户账号
   * 
   * @param login
   * @param user
   * @param userType
   * @return
   * @see com.tmser.tr.back.yhgl.service.BackUserManageService#saveUserAccount(com.tmser.tr.uc.bo.Login,
   *      com.tmser.tr.uc.bo.User)
   */
  @Override
  public User saveUserAccount(Integer userType, Login login, User user) {
    // 新增登陆信息
    String salt = SecurityCode.getSecurityCode(8, SecurityCodeLevel.Hard, false);
    String password = passwordService.encryptPassword(login.getLoginname(), "123456", salt);
    login.setPassword(password);
    login.setSalt(salt);
    if (userType.intValue() == User.SYS_USER.intValue()) {
      login.setIsAdmin(true);
    } else {
      login.setIsAdmin(false);
    }
    login.setEnable(1);
    login.setDeleted(false);
    login = loginService.save(login);
    // 新增用户信息（加入到待插入用户集合中）
    if (StringUtils.isEmpty(user.getNickname())) {
      user.setNickname(user.getName());
    }
    user.setUserType(userType);
    user.setId(login.getId());
    user.setCellphoneValid(false);
    user.setCellphoneView(false);
    user.setAppId(user.getAppId() == null ? 0 : user.getAppId());
    user.setMailValid(false);
    user.setMailView(false);
    user.setIsFamousTeacher(0);
    user.setCrtDttm(new Date());
    user.setLastupDttm(new Date());
    user.setEnable(User.ENABLE);
    // 设置头像和缩略头像
    if (user.getOriginalPhoto() != null && !"".equals(user.getOriginalPhoto())) {
      Resources resources = resourceService.findOne(user.getOriginalPhoto());
      if (resources != null) {
        user.setOriginalPhoto(resources.getPath());
        user.setPhoto(resourceService.resizeImage(resources, 134, 128));
        resourceService.updateTmptResources(resources.getId());// 更新资源状态,置为有效
      }
    }
    if (user.getUserType().intValue() == User.SCHOOL_USER.intValue()) {
      Map<String, String> map = new HashMap<String, String>();
      map.put("account", login.getLoginname());
      map.put("nickname", user.getNickname());
      map.put("username", user.getName());
      map.put("password", password);
    }
    user = userDao.insert(user);
    logger.info("add user success,id:{},name:{}", user.getId(), user.getName());
    return user;
  }

  /**
   * 更新用户账号信息
   * 
   * @param login
   * @param user
   * @see com.tmser.tr.back.yhgl.service.BackUserManageService#updateUserAccount(com.tmser.tr.uc.bo.Login,
   *      com.tmser.tr.uc.bo.User)
   */
  @Override
  public void updateUserAccount(Login login, User user) {
    User temp = userDao.get(user.getId());
    if (user.getOriginalPhoto() != null && !"".equals(user.getOriginalPhoto())
        && !"del".equals(user.getOriginalPhoto())) {
      // 删除原头像
      resourceService.deleteWebResources(temp.getOriginalPhoto());
      resourceService.deleteWebResources(temp.getPhoto());
      // 设置头像和缩略头像
      Resources resources = resourceService.findOne(user.getOriginalPhoto());
      user.setOriginalPhoto(resources.getPath());
      user.setPhoto(resourceService.resizeImage(resources, 134, 128));
      resourceService.updateTmptResources(resources.getId());// 更新资源状态,置为有效
    } else if ("del".equals(user.getOriginalPhoto())) {
      // 删除原头像
      resourceService.deleteWebResources(temp.getOriginalPhoto());
      resourceService.deleteWebResources(temp.getPhoto());
      user.setOriginalPhoto("");
      user.setPhoto("");
    } else {
      user.setOriginalPhoto(null);
    }
    if (StringUtils.isEmpty(user.getNickname())) {
      user.setNickname(user.getName());
    }
    loginService.update(login);
    if (!temp.getName().equals(user.getName())) {
      // 修改用户空间的用户名称
      updateUserSpaceUserName(user);
    }

    userDao.update(user);
    // 刷新前台用户缓存
    FrontCacheUtils.delete(User.class, user.getId());
  }

  private void updateUserSpaceUserName(User user) {
    UserSpace model = new UserSpace();
    model.setUserId(user.getId());
    List<UserSpace> uslist = userSpaceDao.listAll(model);
    for (UserSpace ustemp : uslist) {
      ustemp.setUsername(user.getName());
      userSpaceDao.update(ustemp);
      FrontCacheUtils.delete(UserSpace.class, ustemp.getId());
    }
  }

  /**
   * 获取区域用户集合（分页）
   * 
   * @param user
   * @param roleId
   * @return
   * @see com.tmser.tr.back.yhgl.service.BackUserManageService#getAreaUsers(com.tmser.tr.uc.bo.User,
   *      java.lang.Integer)
   */
  @Override
  public Map<String, Object> getAreaUsers(Integer areaId, User user, Integer roleId) {
    List<Organization> orglist = new ArrayList<Organization>();
    Map<String, Object> dataMap = new HashMap<String, Object>();
    user.addAlias("u");
    user.addCustomCulomn(" DISTINCT u.*");
    Map<String, Object> paramMap = new HashMap<String, Object>();
    if (roleId != null) {
      Role findOne = roleService.findOne(roleId);

      paramMap.put("search_role", findOne.getSysRoleId());
      user.addJoin(QueryObject.JOINTYPE.INNER, "UserSpace a").on("u.id = a.userId and a.sysRoleId = :search_role");
    }
    List<Integer> orgIds = new ArrayList<Integer>();
    if (areaId != null) {
      orglist = organizationService.getOrgByAreaId(areaId, Organization.UNIT);
    }
    for (Organization organization : orglist) {
      orgIds.add(organization.getId());
    }

    if (!CollectionUtils.isEmpty(orglist)) {
      paramMap.put("orgIds", orgIds);
      user.addCustomCondition("and u.orgId in (:orgIds)", paramMap);
    } else {
      if (areaId != null) {
        user.setOrgId(-1);
      }
      user.addCustomCondition("", paramMap);
    }
    if (StringUtils.isNotEmpty(user.getName())) {
      String name = Encodes.urlDecode(user.getName());
      user.setName(SqlMapping.LIKE_PRFIX + name + SqlMapping.LIKE_PRFIX);
      dataMap.put("searchStr", name);
    }
    user.addOrder(user.order());
    user.setUserType(User.AREA_USER);
    PageList<User> unitUserlist = userDao.listPage(user);
    for (User u : unitUserlist.getDatalist()) {
      Login logUser = loginService.findOne(u.getId());
      u.setEnable(logUser.getEnable());
      u.setNickname(logUser.getLoginname());
    }
    dataMap.put("unitUserlist", unitUserlist);
    dataMap.put("orglist", orglist);
    return dataMap;
  }

  /**
   * 通过机构ID获得其部门、学段、角色信息
   * 
   * @param orgId
   * @return
   * @see com.tmser.tr.back.yhgl.service.BackUserManageService#findSpaceDataByOrgId(java.lang.Integer)
   */
  @Override
  public Map<String, Object> findSpaceDataByOrgId(Integer orgId) {
    Map<String, Object> returnmap = new HashMap<String, Object>();
    Organization org = organizationService.findOne(orgId);
    // 所有部门
    Organization deptOrg = new Organization();
    deptOrg.setParentId(org.getId());
    deptOrg.addCustomCulomn("id,shortName,name");
    List<Organization> deptOrgs = organizationService.findAll(deptOrg);
    returnmap.put("deptOrgs", deptOrgs);
    // 所有学段
    String phaseTypes = org.getPhaseTypes();
    if (StringUtils.isNotEmpty(phaseTypes)) {
      List<MetaRelationship> phases = MetaUtils.getOrgTypeMetaProvider().listAllPhase(org.getSchoolings());
      returnmap.put("phases", phases);
    }
    // 所有角色
    List<Role> roles = roleService.findRoleListByUseOrgId(orgId, 2);
    returnmap.put("roles", roles);

    return returnmap;
  }

  /**
   * 保存用户身份信息
   * 
   * @param us
   * @param deptIds
   * @return
   * @see com.tmser.tr.back.yhgl.service.BackUserManageService#saveSchUserRole(com.tmser.tr.uc.bo.UserSpace,
   *      java.lang.String[])
   */
  @Override
  public int saveSchUserRole(UserSpace us, String[] deptIds) {
    String spacename = "";
    if (us.getGradeId() != null && us.getGradeId() != 0) {
      spacename += MetaUtils.getMeta(us.getGradeId()).getName();
    } else {
      us.setGradeId(0);
    }
    if (us.getSubjectId() != null && us.getSubjectId() != 0) {
      spacename += MetaUtils.getMeta(us.getSubjectId()).getName();
    } else {
      us.setSubjectId(0);
    }
    if (us.getRoleId() != null && us.getRoleId() != 0) {
      spacename += roleService.findOne(us.getRoleId()).getRoleName();
    }
    us.setSpaceName(spacename);
    if (deptIds != null && deptIds.length > 0) {
      String deptIdStr = "";
      for (String id : deptIds) {
        deptIdStr += id + ",";
      }
      deptIdStr = deptIdStr.substring(0, deptIdStr.length() - 1);
      us.setConDepIds(deptIdStr);// 管辖部门
    }
    UserSpace usTemp = new UserSpace();
    usTemp.setUserId(us.getUserId());
    // usTemp.setSchoolYear((Integer)
    // WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
    usTemp.setOrgId(us.getOrgId());
    usTemp.setRoleId(us.getRoleId());
    if (us.getSubjectId() != null) {
      usTemp.setSubjectId(us.getSubjectId());
    } else {
      usTemp.setSubjectId(0);
    }
    if (us.getGradeId() != null) {
      usTemp.setGradeId(us.getGradeId());
    } else {
      usTemp.setGradeId(0);
    }
    usTemp.setPhaseId(us.getPhaseId());
    UserSpace one = userSpaceDao.getOne(usTemp);
    if (us.getId() != null) {
      if (one != null) {
        UserSpace findOne = userSpaceService.findOne(us.getId());
        String newSpaceKey = "r" + usTemp.getRoleId() + "s" + usTemp.getSubjectId() + "g" + usTemp.getGradeId() + "p"
            + usTemp.getPhaseId();
        String oldSpaceKey = "r" + findOne.getRoleId() + "s" + findOne.getSubjectId() + "g" + findOne.getGradeId() + "p"
            + findOne.getPhaseId();
        if (!newSpaceKey.equals(oldSpaceKey)) {
          return 0;
        }
      }
      userSpaceDao.update(us);
      return 1;
    } else {
      if (one != null) {
        return 0;
      } else {
        us.setSpaceHomeUrl(getHomeUrl(us.getSysRoleId()));// home路径

        if (us.getUserId() != null) {
          us.setUsername(userDao.get(us.getUserId()).getName());
        }
        us.setSchoolYear(schoolYearService.getCurrentSchoolYear());
        us.setSpaceName(spacename);
        us.setEnable(1);
        us.setSort(0);
        us.setIsDefault(false);
        us.setSubjectOrder(0);
        us = userSpaceDao.insert(us);

        // 保存用户角色
        UserRole ur = new UserRole();
        ur.setUserId(us.getUserId());
        ur.setRoleId(us.getRoleId());
        UserRole urtemp = userRoleDao.getOne(ur);
        if (urtemp == null) {
          userRoleDao.insert(ur);
          // 添加菜单
          userMenuService.addUserMenus(us);
        }
        return 1;
      }
    }

  }

  /**
   * 授权用户的管理范围
   * 
   * @param userId
   * @param roleId
   * @param orgIdsStr
   * @see com.tmser.tr.back.yhgl.service.BackUserManageService#savePowerScope(java.lang.Integer,
   *      java.lang.Integer, java.lang.String)
   */
  @Override
  public void savePowerScope(Integer userId, Integer roleId, String orgIdsStr) {
    UserManagescope um = new UserManagescope();
    um.setUserId(userId);
    um.setRoleId(roleId);
    List<UserManagescope> umList = userMangescopeDao.listAll(um);
    if (umList != null && umList.size() > 0) {
      for (UserManagescope umtemp : umList) {
        userMangescopeDao.delete(umtemp.getId());
      }
    }
    List<UserManagescope> userManagescopeList = new ArrayList<UserManagescope>();
    if (!StringUtils.isBlank(orgIdsStr)) {
      String[] orgIdArray = orgIdsStr.split(",");
      for (String orgId : orgIdArray) {
        Organization org = organizationService.findOne(Integer.valueOf(orgId));
        UserManagescope temp = new UserManagescope();
        temp.setUserId(userId);
        temp.setRoleId(roleId);
        temp.setOrgId(Integer.valueOf(orgId));
        temp.setAreaId(org.getAreaId());
        temp.setOrgName(org.getName());
        userManagescopeList.add(temp);
      }
    }
    userMangescopeDao.batchInsert(userManagescopeList);
  }

  /**
   * 根据用户id获取管理范围
   * 
   * @param userId
   * @return
   * @see com.tmser.tr.back.yhgl.service.BackUserManageService#getManagescopeByUserId(java.lang.Integer)
   */
  @Override
  public List<UserManagescope> getManagescopeByUserId(Integer userId) {
    UserManagescope um = new UserManagescope();
    um.setUserId(userId);
    return userMangescopeDao.listAll(um);
  }

  /**
   * 用户重置密码
   * 
   * @param id
   * @see com.tmser.tr.back.yhgl.service.BackUserManageService#resetUserPass(java.lang.Integer)
   */
  @Override
  public void resetUserPass(Integer id) {
    Login login = loginService.findOne(id);
    String salt = login.getSalt();
    String password = passwordService.encryptPassword(login.getLoginname(), "123456", salt);
    login.setPassword(password);
    loginService.update(login);
  }

  /**
   * 冻结和取消冻结用户操作
   * 
   * @param id
   * @param enable
   * @see com.tmser.tr.back.yhgl.service.BackUserManageService#djUser(java.lang.Integer,
   *      java.lang.Integer)
   */
  @Override
  public void djUser(Integer id, Integer enable) {
    Login login = loginService.findOne(id);
    login.setEnable(enable);
    loginService.update(login);
    UserSpace us = new UserSpace();
    us.setUserId(id);
    List<UserSpace> userSpaceList = userSpaceDao.listAll(us);
    for (UserSpace userSpace : userSpaceList) {
      userSpace.setEnable(enable);
      userSpaceDao.update(userSpace);
    }
  }

  /**
   * 查找区域用户身份信息
   * 
   * @param userSpa
   * @return
   * @see com.tmser.tr.back.yhgl.service.BackUserManageService#findAreaRoleInfo(com.tmser.tr.uc.bo.UserSpace)
   */
  @Override
  public Map<String, Object> findAreaRoleInfo(UserSpace userSpa) {
    Map<String, Object> metaMapInfo = new HashMap<String, Object>();
    UserSpace findOneSpa = userSpaceDao.get(userSpa.getId());
    // 用户所属学段
    Role findOneRole = roleService.findOne(findOneSpa.getRoleId());
    if (null != findOneSpa.getDepartmentId()) {
      Organization orgFind = organizationService.findOne(findOneSpa.getDepartmentId());
      metaMapInfo.put("orgFind", orgFind);
    }
    String conDepIds = findOneSpa.getConDepIds();// 用户所属管辖部门
    if (StringUtils.isNotEmpty(conDepIds)) {
      String[] splitgx = conDepIds.split(",");
      List<String> gxbmList = new ArrayList<String>();
      for (int i = 0; i < splitgx.length; i++) {
        if (StringUtils.isNotEmpty(splitgx[i])) {
          gxbmList.add(organizationService.findOne(Integer.valueOf(splitgx[i])).getName());
        }
      }
      metaMapInfo.put("gxbmList", StringUtils.join(gxbmList, "、"));
    }
    if (null != findOneSpa.getPhaseId()) {
      MetaRelationship findOnePhase = MetaUtils.getPhaseMetaProvider().getMetaRelationship(findOneSpa.getPhaseId());
      // 通过学段找出对应学科名称
      metaMapInfo.put("subjectMeta", MetaUtils.getPhaseMetaProvider().getMeta(findOneSpa.getSubjectId()));
      metaMapInfo.put("findOnePhase", findOnePhase);
    }
    metaMapInfo.put("findOneSpa", findOneSpa);
    metaMapInfo.put("findOneRole", findOneRole);
    return metaMapInfo;
  }

  /**
   * 保存或修改区域用户角色信息
   * 
   * @param userSpace
   * @param editId
   * @see com.tmser.tr.back.yhgl.service.BackUserManageService#saveAreaUserRole(com.tmser.tr.uc.bo.UserSpace,
   *      java.lang.Integer)
   */
  @Override
  public void saveAreaUserRole(UserSpace userSpace, Integer editId) {
    // 找出已添加用户空间信息
    Integer sysRoleId = userSpace.getSysRoleId();
    RoleType roleType = new RoleType();
    roleType.setUsePosition(1);
    roleType.setCode(sysRoleId);
    List<RoleType> roleTypeList = roleTypeService.findAll(roleType);
    if (null != userSpace.getPhaseId() && userSpace.getPhaseId().intValue() != -1) {
      userSpace.setPhaseType(MetaUtils.getPhaseMetaProvider().getMetaRelationship(userSpace.getPhaseId()).getEid());
    }
    if (!CollectionUtils.isEmpty(roleTypeList)) {
      if (!roleTypeList.get(0).getIsNoXz()) {
        userSpace.setPhaseId(0);
        userSpace.setPhaseType(0);
      }
      if (!roleTypeList.get(0).getIsNoXk()) {
        userSpace.setSubjectId(0);
      }
    }
    User findOne = userDao.get(userSpace.getUserId());
    userSpace.setUsername(findOne.getName());//
    userSpace.setSort(1);
    userSpace.setIsDefault(false);
    userSpace.setEnable(UserSpace.ENABLE);
    userSpace.setGradeId(0);
    userSpace.setSubjectOrder(0);
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);// 学年
    userSpace.setOrgId(findOne.getOrgId());
    userSpace.setSchoolYear(schoolYear);
    userSpace.setSpaceHomeUrl(getHomeUrl(sysRoleId));// home路径
    userSpaceDao.insert(userSpace);
    // 保存用户角色关系
    UserRole userRole = new UserRole();
    userRole.setRoleId(userSpace.getRoleId());
    userRole.setUserId(userSpace.getUserId());
    UserRole urtemp = userRoleDao.getOne(userRole);
    if (urtemp == null) {
      userRoleDao.insert(userRole);
      // 添加菜单
      userMenuService.addUserMenus(userSpace);
    }

  }

  /**
   * 查看用户身份详情
   * 
   * @param us
   * @return
   * @see com.tmser.tr.back.yhgl.service.BackUserManageService#detailUserRole(com.tmser.tr.uc.bo.UserSpace)
   */
  @Override
  public Map<String, Object> detailUserRole(UserSpace us) {
    Map<String, Object> map = new HashMap<String, Object>();
    us = userSpaceDao.get(us.getId());
    if (us != null) {
      map.put("userName", us.getUsername());
      if (us.getRoleId() != null && us.getRoleId() != 0) {
        Role role = roleService.findOne(us.getRoleId());
        if (role != null) {
          map.put("role", role.getRoleName());
        }
      }
      MetaRelationship phase = MetaUtils.getPhaseMetaProvider().getMetaRelationship(us.getPhaseId());
      if (phase != null) {
        map.put("phase", phase.getName());
      }
      if (us.getGradeId() != null && us.getGradeId() != 0) {
        Meta sd = MetaUtils.getMeta(us.getGradeId());
        map.put("grade", sd.getName());
      }
      if (us.getSubjectId() != null && us.getSubjectId() != 0) {
        Meta sd = MetaUtils.getMeta(us.getSubjectId());
        map.put("subject", sd.getName());
      }
      map.put("spaceName", us.getSpaceName());
      map.put("schoolYear", us.getSchoolYear());
      if (us.getDepartmentId() != null && us.getDepartmentId() != 0) {
        Organization org = organizationService.findOne(us.getDepartmentId());
        map.put("department", org.getName());
      }
      if (StringUtils.isNotEmpty(us.getConDepIds())) {
        String[] deptsArr = us.getConDepIds().split(",");
        String bumen = "";
        for (int i = 0; i < deptsArr.length; i++) {
          if (StringUtils.isNotEmpty(deptsArr[i])) {
            Organization org = organizationService.findOne(Integer.parseInt(deptsArr[i]));
            if (org != null) {
              bumen += org.getName() + "、";
            }
          }
        }
        if (!"".equals(bumen)) {
          bumen = bumen.substring(0, bumen.length() - 1);
        }
        map.put("departments", bumen);
      }
    }
    return map;
  }

  /**
   * 删除系统用户
   * 
   * @param userId
   * @see com.tmser.tr.back.yhgl.service.BackUserManageService#deleteSysUser(java.lang.Integer)
   */
  @Override
  public void deleteSysUser(Integer userId) {
    // 删除登录信息
    loginService.delete(userId);
    // 删除用户的头像文件
    User user = userDao.get(userId);
    resourceService.deleteWebResources(user.getOriginalPhoto());
    resourceService.deleteWebResources(user.getPhoto());
    // 删除用户信息
    userDao.delete(userId);
    // 删除用户角色映射信息
    UserRole ur = new UserRole();
    ur.setUserId(userId);
    ur = userRoleDao.getOne(ur);
    userRoleDao.delete(ur.getId());
    // 删除用户空间
    UserSpace us = new UserSpace();
    us.setUserId(userId);
    us = userSpaceDao.getOne(us);
    userSpaceDao.delete(us.getId());
    // 删除用户的管理范围
    UserManagescope um = new UserManagescope();
    um.setUserId(userId);
    List<UserManagescope> umList = userMangescopeDao.listAll(um);
    if (umList != null && umList.size() > 0) {
      for (UserManagescope temp : umList) {
        userMangescopeDao.delete(temp.getId());
      }
    }
  }

  /**
   * 删除用户身份
   * 
   * @param us
   * @see com.tmser.tr.back.yhgl.service.BackUserManageService#delUserRole(com.tmser.tr.uc.bo.UserSpace)
   */
  @Override
  public void delUserRole(UserSpace us) {
    if (us != null && null != us.getId()) {
      us = userSpaceDao.get(us.getId());
      userSpaceDao.delete(us.getId());
      UserSpace ustemp = new UserSpace();
      ustemp.setUserId(us.getUserId());
      ustemp.setSysRoleId(us.getSysRoleId());
      ustemp.setSchoolYear((Integer) WebThreadLocalUtils.getAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
      ustemp = userSpaceDao.getOne(ustemp);
      if (ustemp == null) {// 没有相同的身份
        UserRole userRole = new UserRole();
        userRole.setRoleId(us.getRoleId());
        userRole.setUserId(us.getUserId());
        userRole = userRoleDao.getOne(userRole);
        if (userRole != null) {
          userRoleDao.delete(userRole.getId());
        }
        // 删除userMenu表的数据
        userMenuService.deleteUserMenus(us);
      }
    }
  }

  /**
   * 保存专家用户空间
   * 
   * @param user
   * @see com.tmser.tr.back.yhgl.service.BackUserManageService#saveExpertUserSpace(com.tmser.tr.uc.bo.User)
   */
  @Override
  public void saveExpertUserSpace(User user, User newUser) {
    UserSpace userSpace = new UserSpace();
    if (null == user) {
      UserSpace userSp = new UserSpace();
      userSp.setUserId(newUser.getId());
      userSp = userSpaceDao.getOne(userSp);
      if (null == userSp) {
        userSpace.setUserId(newUser.getId());
        userSpace.setUsername(newUser.getName());
        userSpace.setSort(1);
        userSpace.setIsDefault(false);
        userSpace.setEnable(UserSpace.ENABLE);
        userSpace.setGradeId(0);
        userSpace.setSubjectId(0);
        userSpace.setSubjectOrder(0);
        Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);// 学年
        userSpace.setOrgId(newUser.getOrgId());
        userSpace.setSchoolYear(schoolYear);
        userSpace.setSpaceHomeUrl(getHomeUrl(userSpace.getSysRoleId()));// home路径
        userSpaceDao.insert(userSpace);
      } else {
        userSp.setUsername(newUser.getName());
        userSp.setOrgId(newUser.getOrgId());
        userSpaceDao.update(userSp);
      }
    } else {
      // 找出已添加用户空间信息
      userSpace.setUserId(user.getId());
      userSpace.setUsername(user.getName());
      userSpace.setSort(1);
      userSpace.setIsDefault(false);
      userSpace.setEnable(UserSpace.ENABLE);
      userSpace.setGradeId(0);
      // userSpace.setSubjectId(0);
      userSpace.setSubjectOrder(0);
      Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);// 学年
      userSpace.setOrgId(user.getOrgId());
      userSpace.setSchoolYear(schoolYear);
      userSpace.setSpaceHomeUrl(getHomeUrl(userSpace.getSysRoleId()));// home路径
      userSpaceDao.insert(userSpace);

    }

  }

  /**
   * 通过us获得身份的相关信息
   * 
   * @param us
   * @return
   * @see com.tmser.tr.back.yhgl.service.BackUserManageService#findSpaceDataById(com.tmser.tr.uc.bo.UserSpace)
   */
  @Override
  public Map<String, Object> findSpaceDataById(UserSpace us) {
    us = userSpaceDao.get(us.getId());
    if (us != null) {
      Map<String, Object> map = findSpaceDataByOrgId(us.getOrgId());
      map.put("us", us);
      Organization org = orgService.findOne(us.getOrgId());
      String areaIds = org.getAreaIds();
      Integer[] areaIdArr = com.tmser.tr.utils.StringUtils.toIntegerArray(areaIds.substring(1, areaIds.length() - 1),
          com.tmser.tr.utils.StringUtils.COMMA);
      List<Meta> listAllSubject = MetaUtils.getPhaseSubjectMetaProvider().listAllSubject(us.getOrgId(), us.getPhaseId(),
          areaIdArr);
      if (us.getSysRoleId().intValue() == SysRole.XKZZ.getId().intValue()) {
        map.put("subjects", listAllSubject);
      } else if (us.getSysRoleId().intValue() == SysRole.NJZZ.getId().intValue()) {
        map.put("grades", MetaUtils.getPhaseGradeMetaProvider().listAllGradeByPhaseId(us.getPhaseId()));
      } else if (us.getSysRoleId().intValue() == SysRole.BKZZ.getId().intValue()) {
        map.put("subjects", listAllSubject);
        map.put("grades", MetaUtils.getPhaseGradeMetaProvider().listAllGradeByPhaseId(us.getPhaseId()));
      } else if (us.getSysRoleId().intValue() == SysRole.TEACHER.getId().intValue()) {
        map.put("subjects", listAllSubject);
        map.put("grades", MetaUtils.getPhaseGradeMetaProvider().listAllGradeByPhaseId(us.getPhaseId()));
        // 学段&学科--》出版社
        PublishRelationship pr = new PublishRelationship();
        pr.setPhaseId(us.getPhaseId());
        pr.setSubjectId(us.getSubjectId());
        pr.setOrgId(us.getOrgId());
        pr.setScope("org");
        map.put("cbslist", publisherManageService.listAllPubliserMetaByScope(pr));
        // 学段&学科&年级&出版社---》教材
        Book book = bookService.findOne(us.getBookId());
        if (book != null) {
          map.put("cbsId", book.getPublisherId());
          BookSync booktemp = new BookSync();
          booktemp.setPhaseId(us.getPhaseType());
          booktemp.setSubjectId(us.getSubjectId());
          booktemp.setGradeLevelId(us.getGradeId());
          booktemp.setPublisherId(book.getPublisherId());
          map.put("books", bookService.findBookSync(booktemp));
        }
      }
      return map;
    }
    return null;
  }

  /**
   * 根据角色元数据Id获取空间地址
   * 
   * @param sysRoleId
   * @return
   * @see com.tmser.tr.back.yhgl.service.BackUserManageService#getHomeUrl(java.lang.Integer)
   */
  @Override
  public String getHomeUrl(Integer sysRoleId) {
    RoleType se = roleTypeService.getRoleTypeByCode(sysRoleId);
    if (se != null) {
      return se.getHomeUrl();// home路径
    }
    return null;
  }

  /**
   * @param userId
   * @param roleId
   * @param areaIdsStr
   * @see com.tmser.tr.back.yhgl.service.BackUserManageService#savePowerScopeArea(java.lang.Integer,
   *      java.lang.Integer, java.lang.String)
   */
  @Override
  public void savePowerScopeArea(Integer userId, Integer roleId, String areaIdsStr) {
    UserManagescope um = new UserManagescope();
    um.setUserId(userId);
    um.setRoleId(roleId);
    List<UserManagescope> umList = userMangescopeDao.listAll(um);
    if (umList != null && umList.size() > 0) {
      for (UserManagescope umtemp : umList) {
        userMangescopeDao.delete(umtemp.getId());
      }
    }
    List<UserManagescope> userManagescopeList = new ArrayList<UserManagescope>();
    if (!StringUtils.isBlank(areaIdsStr)) {
      String[] areaIdArray = areaIdsStr.split(",");
      for (String areaId : areaIdArray) {
        UserManagescope temp = new UserManagescope();
        temp.setUserId(userId);
        temp.setRoleId(roleId);
        temp.setOrgId(0);
        temp.setAreaId(Integer.valueOf(areaId));
        userManagescopeList.add(temp);
      }
    }
    userMangescopeDao.batchInsert(userManagescopeList);
  }

}
