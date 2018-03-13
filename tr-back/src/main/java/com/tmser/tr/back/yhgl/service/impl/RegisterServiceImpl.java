/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.yhgl.service.impl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.tmser.tr.back.logger.LoggerModule;
import com.tmser.tr.back.yhgl.service.BackUserManageService;
import com.tmser.tr.back.yhgl.service.RegisterService;
import com.tmser.tr.back.yhgl.utils.LoginnameGernerator;
import com.tmser.tr.common.service.ExcelBatchService;
import com.tmser.tr.common.utils.ExcelUtils;
import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.manage.config.ConfigUtils;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.bo.BookSync;
import com.tmser.tr.manage.meta.bo.Menu;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.meta.service.BookService;
import com.tmser.tr.manage.meta.service.MenuService;
import com.tmser.tr.manage.org.bo.Area;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.AreaService;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.App;
import com.tmser.tr.uc.bo.Login;
import com.tmser.tr.uc.bo.Role;
import com.tmser.tr.uc.bo.RoleType;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserMenu;
import com.tmser.tr.uc.bo.UserRole;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.AppService;
import com.tmser.tr.uc.service.LoginService;
import com.tmser.tr.uc.service.PasswordService;
import com.tmser.tr.uc.service.RoleService;
import com.tmser.tr.uc.service.RoleTypeService;
import com.tmser.tr.uc.service.SchoolYearService;
import com.tmser.tr.uc.service.UserMenuService;
import com.tmser.tr.uc.service.UserRoleService;
import com.tmser.tr.uc.service.UserService;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.utils.DateUtils;
import com.tmser.tr.utils.SecurityCode;
import com.tmser.tr.utils.SecurityCode.SecurityCodeLevel;
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 * 注册服务实现类
 * </pre>
 * 
 * @author daweiwbs
 * @version $Id: RegisterServiceImpl.java, v 1.0 2015年8月25日 下午5:58:58 daweiwbs
 *          Exp $
 */
@Service
@Transactional
public class RegisterServiceImpl extends ExcelBatchService implements RegisterService {

  private static final Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);

  @Autowired
  private OrganizationService orgService;
  @Autowired
  private AreaService areaService;
  @Autowired
  private UserService userService;
  @Autowired
  private AppService appService;
  @Autowired
  private PasswordService passwordService;
  @Autowired
  private LoginService loginService;
  @Autowired
  private UserSpaceService userSpaceService;
  @Autowired
  private RoleTypeService roleTypeService;
  @Autowired
  private RoleService roleService;
  @Autowired
  private SchoolYearService schoolYearService;
  @Autowired
  private MenuService menuService;
  @Autowired
  private BookService bookService;
  @Autowired
  private UserRoleService userRoleService;
  @Autowired
  private BackUserManageService userManageService;

  /**
   * 用户模板头部所在行
   */
  private Integer headline = 3;

  private final static String[] SEXARR = new String[] { "男", "女" };
  private final static Map<Integer, String> SEXMAP = new HashMap<>();
  static {
    SEXMAP.put(0, "男");
    SEXMAP.put(1, "女");
  }

  private boolean needBook() {
    return Boolean.valueOf(ConfigUtils.readConfig("register.school.needbook", "true"));
  }

  /**
   * 获取批量注册模板的文件流
   * 
   * @param phaseId
   * @param orgId
   * @param response
   * @return
   * @see com.tmser.tr.back.ClassinfoService.service.RegisterService#getRegisterTemplateFileStream(java.lang.Integer,
   *      java.lang.Integer)
   */
  @Override
  public void getRegisterTemplateFileStream(String templateType, Integer phaseId, Integer orgId,
      HttpServletResponse response) {
    Workbook workbook = null;
    String fileName = "批量注册模板";
    String filepath = "";
    if ("xxyh".equals(templateType)) { // 学校用户模板
      filepath = "/registertemplate/registertemplate_xxyh.xls";
      workbook = ExcelUtils
          .createWorkBook(WebThreadLocalUtils.getRequest().getSession().getServletContext().getRealPath(filepath));
      if (workbook == null) {
        returnErrMsg(filepath, response);
        return;
      }
      Sheet sheet = workbook.getSheet("Sheet1");
      fileName = "批量导入学校用户模板";
      Organization org = orgService.findOne(orgId);
      String orgStr = "学校名称：" + org.getName();
      Area area1 = areaService.findOne(org.getAreaId());
      if (area1.getParentId().intValue() != 0) {
        Area area2 = areaService.findOne(area1.getParentId());
        orgStr = "省(市)：" + area2.getName() + "     区(县)：" + area1.getName() + "     " + orgStr;
      } else {
        orgStr = "区(县)：" + area1.getName() + "     " + orgStr;
      }
      sheet.getRow(2).getCell(0).setCellValue(orgStr);

      Map<ExcelTitle, Column> headers = parseExcelHeader(sheet, headline);
      initSheetTeplate(workbook, sheet, org, phaseId, headers);
    } else if ("qyyh".equals(templateType)) { // 区域用户模板
      filepath = "/registertemplate/registertemplate_qyyh.xls";
      workbook = ExcelUtils
          .createWorkBook(WebThreadLocalUtils.getRequest().getSession().getServletContext().getRealPath(filepath));
      if (workbook == null) {
        returnErrMsg(filepath, response);
        return;
      }
      Sheet sheet = workbook.getSheet("Sheet1");
      fileName = "批量导入区域用户模板";
      Organization org = orgService.findOne(orgId);
      String orgStr = "单位名称：" + org.getName();
      Area area1 = areaService.findOne(org.getAreaId());
      if (area1.getParentId().intValue() != 0) {
        Area area2 = areaService.findOne(area1.getParentId());
        orgStr = "省(市)：" + area2.getName() + "     区(县)：" + area1.getName() + "     " + orgStr;
      } else {
        orgStr = "区(县)：" + area1.getName() + "     " + orgStr;
      }
      sheet.getRow(2).getCell(0).setCellValue(orgStr);

      Map<ExcelTitle, Column> headers = parseExcelHeader(sheet, headline);
      initAreaSheetTeplate(sheet, org, phaseId, headers);
    } else if ("xx".equals(templateType)) { // 学校模板
      filepath = "/registertemplate/registertemplate_xx.xls";
      workbook = ExcelUtils
          .createWorkBook(WebThreadLocalUtils.getRequest().getSession().getServletContext().getRealPath(filepath));
      if (workbook == null) {
        returnErrMsg(filepath, response);
        return;
      }
      Sheet sheet = workbook.getSheet("Sheet1");
      fileName = "批量导入学校模板";
      String orgStr = "";
      Area area1 = areaService.findOne(orgId);
      if (area1.getParentId().intValue() != 0) {
        Area area2 = areaService.findOne(area1.getParentId());
        orgStr = "省(市)：" + area2.getName() + "     区(县)：" + area1.getName();
      } else {
        orgStr = "区(县)：" + area1.getName();
      }
      sheet.getRow(2).getCell(0).setCellValue(orgStr);
      List<MetaRelationship> schoolTypeMeataList = MetaUtils.getOrgTypeMetaProvider().listAll();
      sheet = setPromptOfCell(sheet, objectListToStringArray(schoolTypeMeataList, "getName", false), 4, 500, 2, 2); // 学校类型
    } else if ("zjyh".equals(templateType)) { // 专家用户模板
      filepath = "/registertemplate/registertemplate_zjyh.xls";
      workbook = ExcelUtils
          .createWorkBook(WebThreadLocalUtils.getRequest().getSession().getServletContext().getRealPath(filepath));
      if (workbook == null) {
        returnErrMsg(filepath, response);
        return;
      }
      Sheet sheet = workbook.getSheet("Sheet1");
      fileName = "批量导入专家用户模板";
      sheet = setPromptOfCell(sheet, SEXARR, 4, 500, 1, 1);
      sheet = setPromptOfCell(sheet, new String[] { "", "" }, 4, 500, 2, 2); // 单位
      sheet = setPromptOfCell(sheet, new String[] { "国家骨干", "特级骨干", "省级骨干", "市级骨干", "区县级骨干", "校级骨干", "无" }, 4, 500, 6,
          6);// 骨干教师
    }

    try {
      String ext = filepath.substring(filepath.lastIndexOf("."));
      if (workbook != null) {
        response.reset();
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition",
            "attachment; filename=" + new String(fileName.getBytes("gb2312"), "ISO-8859-1") + ext);
        workbook.write(response.getOutputStream());
      } else {
        response.getWriter().write("模板文件不存在！");
      }
    } catch (Exception e) {
      logger.error("", e);

    } finally {
      if (workbook != null) {
        try {
          workbook.close();
        } catch (IOException e) {
          logger.error("close failed", e);
        }
      }
    }
  }

  /**
   * 设置单元格的下拉框
   * 
   * @param sheet
   * @param textList
   * @param firstRow
   * @param lastRow
   * @param firstCol
   * @param lastCol
   * @return
   */
  private Sheet setPromptOfCell(Sheet sheet, String[] textList, int firstRow, int endRow, int firstCol, int endCol) {
    // 加载下拉列表内容
    DataValidation data_validation_list = ExcelUtils.createDataValidation(sheet, textList, firstRow, endRow, firstCol,
        endCol);
    sheet.addValidationData(data_validation_list);
    return sheet;
  }

  /**
   * 从对象的集合获取某个字段的数组
   * 
   * @param objectList
   * @param paramName
   * @return
   * @throws Exception
   */
  private <T> String[] objectListToStringArray(List<T> objectList, String paramName, boolean allowNull) {
    List<String> textList = new ArrayList<String>();
    Method m = null;
    try {
      for (int i = 0; i < objectList.size(); i++) {
        m = objectList.get(i).getClass().getDeclaredMethod(paramName);
        String value = (String) m.invoke(objectList.get(i));
        if (StringUtils.isNotEmpty(value)) {
          textList.add(value);
        }
      }
    } catch (Exception e) {
      logger.error("invoke {} failed", paramName);
      logger.error("", e);
    }
    if (allowNull) {
      textList.add("无");
    }
    return textList.toArray(new String[textList.size()]);
  }

  /**
   * 批量注册学校用户
   * 
   * @param orgId
   * @param phaseId
   * @param file
   * @param response
   * @see com.tmser.tr.back.ClassinfoService.service.RegisterService#batchRegiter_xxyh(java.lang.Integer,
   *      java.lang.Integer, org.springframework.web.multipart.MultipartFile,
   *      javax.servlet.http.HttpServletResponse)
   */
  @Override
  public StringBuilder batchRegiter_xxyh(Integer orgId, Integer phaseId, MultipartFile file) {
    Map<String, Object> params = new HashMap<String, Object>();
    StringBuilder resultStr = new StringBuilder(); // 反馈信息
    params.put("orgId", orgId);
    params.put("phaseId", phaseId);
    params.put("userType", "SCHOOL");
    try {
      super.importData(file.getInputStream(), params, resultStr);
    } catch (Exception e) {
      logger.error("read excel file failed", e);
    }

    return resultStr;
  }

  private boolean addSpace(List<UserSpace> batchSpaceList, StringBuilder resultStr, List<UserMenu> batchMenuList,
      String formatName, int i, User tempUser, UserSpace userSpace, Map<String, User> userTempMap,
      Map<String, String> spaceTempMap, Map<String, String> mapTempMap, Map<String, UserSpace> areadySpaceIdMap) {
    String spkey = tempUser.getId() + "_l" + userSpace.getRoleId() + "_s" + userSpace.getSubjectId() + "_g"
        + userSpace.getGradeId();
    if (spaceTempMap.get(spkey) != null) {// 空间重复
      if (userTempMap.get(tempUser.getName()).getFlago() == null) {
        resultStr.append("数据重复：第" + (i) + "行数据已存在，已自动忽略当前行身份信息;<br>");
      } else {
        resultStr.append("数据重复：第" + (i) + "行与第" + userTempMap.get(tempUser.getName()).getFlago() + "行数据重复，已自动忽略;<br>");
      }
      if (areadySpaceIdMap.get(spkey) != null) {
        areadySpaceIdMap.put(spkey, null);
      }
      areadySpaceIdMap.put(spkey + "_p" + userSpace.getPhaseId(), null);
      return false;
    } else {
      // 新增用户空间（加入到待插入空间集合中）
      userSpace.setUserId(tempUser.getId());
      if (userSpace.getSysRoleId().equals(SysRole.TEACHER.getId()) && needBook()) {// 老师，需要加入bookId
        if (StringUtils.isBlank(formatName)) {
          resultStr.append("数据错误：第" + (i) + "行中教师的教材为空,已自动忽略当前行身份信息;<br>");
          return false;
        }
        BookSync book = getBookForRegister(userSpace.getSubjectId(), userSpace.getGradeId(), formatName);
        if (book == null) {
          resultStr.append("数据错误：第" + (i) + "行中选择的教材不存在,已自动忽略当前行身份信息;<br>");
          return false;
        } else {
          userSpace.setBookId(book.getComId());
        }
      }

      batchSpaceList.add(userSpace);
      spaceTempMap.put(spkey, "");

      // 加入菜单 每个用户的每种角色加一套菜单
      if (mapTempMap.get(tempUser.getName() + "_" + userSpace.getRoleId()) == null) {
        batchAddMenu(userSpace.getRoleId(), tempUser.getId(), batchMenuList);
        mapTempMap.put(tempUser.getName() + "_" + userSpace.getRoleId(), "");
      }
    }
    return true;
  }

  /**
   * 根据年级、学科和版本获取book
   * 
   * @param subjectId
   * @param gradeId
   * @param flags
   * @return
   */
  private BookSync getBookForRegister(Integer subjectId, Integer gradeId, String formatName) {
    Integer term = schoolYearService.getCurrentTerm();// 学期
    BookSync book = new BookSync();
    book.setSubjectId(subjectId);
    book.setGradeLevelId(gradeId);
    book.setFormatName(formatName);
    book.addOrder(" bookEdtion desc");
    List<BookSync> bookList = bookService.findBookSync(book);
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
    } else if (bookList.size() == 1) {
      return bookList.get(0);
    }
    return null;
  }

  private void fillSpace(User tempUser, Integer schoolYear, Map<String, String> spaceTempMap,
      Map<String, String> mapTempMap) {
    UserSpace model = new UserSpace();
    model.setUserId(tempUser.getId());
    model.setOrgId(tempUser.getOrgId());
    model.setSchoolYear(schoolYear);
    List<UserSpace> lst = userSpaceService.findAll(model);
    String spkey = "";
    for (UserSpace us : lst) {
      spkey = tempUser.getId() + "_l" + us.getRoleId() + "_s" + us.getSubjectId() + "_g" + us.getGradeId();
      spaceTempMap.put(spkey, "");
      spaceTempMap.put(spkey + "_p" + us.getPhaseId(), "");
      // 加入菜单 每个用户的每种角色加一套菜单
      mapTempMap.put(tempUser.getName() + "_" + us.getRoleId(), "");
    }

  }

  private void parseExitsCount(String loginname, User user, Map<String, User> usercontainer,
      Map<String, String> spaceSet, Map<String, UserSpace> areadySpaceIdMap) {
    Login l = loginService.findByUsername(loginname);
    // 账号存在并且没有处理过
    if (l != null && usercontainer.get(user.getName()) == null) {
      user.setId(l.getId());
      if (userService.findOne(l.getId()) != null) {
        user.setCrtDttm(null);
        userService.update(user);
      } else {
        userService.save(user);
      }
      usercontainer.put(user.getName(), user);

      UserSpace usModel = new UserSpace();
      usModel.setUserId(user.getId());
      usModel.setSchoolYear(schoolYearService.getCurrentSchoolYear());
      List<UserSpace> userSpaceList = userSpaceService.findAll(usModel);
      for (UserSpace us : userSpaceList) {
        String key = us.getUserId() + "_l" + us.getRoleId() + "_s" + us.getSubjectId() + "_g" + us.getGradeId();
        spaceSet.put(key, "");
        spaceSet.put(key + "_p" + us.getPhaseId(), "");
        areadySpaceIdMap.put(key, us);
        areadySpaceIdMap.put(key + "_p" + us.getPhaseId(), us);
      }
    }
  }

  private void batchAddMenu(Integer roleId, Integer userId, List<UserMenu> batchMenuList) {
    List<Menu> menuList = menuService.getMenuListByRole(roleId);
    for (Menu m : menuList) {
      UserMenu userMenu = new UserMenu();
      userMenu.setSysRoleId(roleId);
      userMenu.setMenuId(m.getId());
      userMenu.setDisplay(true);
      userMenu.setSort(m.getSort());
      userMenu.setUserId(userId);
      userMenu.setName(m.getName());
      batchMenuList.add(userMenu);
    }
  }

  /**
   * 根据角色元数据Id获取空间地址
   * 
   * @param sysRoleId
   * @return
   */
  private String getHomeUrl(Integer sysRoleId) {
    return roleTypeService.getRoleTypeByCode(sysRoleId).getHomeUrl();
  }

  /**
   * 部门名称转部门id
   * 
   * @param birthdayStr
   * @return
   */
  private String bumenNamesToBumenIds(String birthdayStr, Map<String, Integer> metaMap) {
    try {
      if (StringUtils.isBlank(birthdayStr)) {
        return "0";
      }
      String[] bumenNameArray = birthdayStr.split("、");
      String bumenIdsStr = "";
      for (String bumenName : bumenNameArray) {
        Integer bumenId = metaMap.get(bumenName);
        if (bumenId == null) {
          return null;
        } else if (bumenId.intValue() == 0) {
          return "0";
        } else {
          bumenIdsStr += bumenId + ",";
        }
      }
      if (!"".equals(bumenIdsStr)) {
        return bumenIdsStr.substring(0, bumenIdsStr.length() - 1);
      }
      return null;
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 解析app id
   * 
   * @return
   */
  private Integer parseAppid(String appid) {
    if (StringUtils.isNotBlank(appid)) {
      App model = new App();
      model.setAppid(appid);
      App app = appService.findOne(model);
      if (app != null) {
        return app.getId();
      }
    }
    return 0;// 默认
  }

  /**
   * 获取学校用户元数据的Id——名称map
   * 
   * @param orgId
   * @param phaseId
   * @return
   */
  private Map<String, Integer> getMetaMap(Integer systemId, Integer phaseId, Integer orgId) {
    Map<String, Integer> metaMap = new HashMap<String, Integer>();
    Organization organization = orgService.findOne(orgId);
    String areaIds = organization.getAreaIds();
    Integer[] areaIdArr = com.tmser.tr.utils.StringUtils.toIntegerArray(areaIds.substring(1, areaIds.length() - 1),
        com.tmser.tr.utils.StringUtils.COMMA);
    List<Meta> subjects = MetaUtils.getPhaseSubjectMetaProvider().listAllSubject(orgId, phaseId, areaIdArr);
    List<Meta> grades = MetaUtils.getOrgTypeMetaProvider().listAllGrade(systemId, phaseId);

    for (Meta m : subjects) {
      metaMap.put(m.getName(), m.getId());
    }
    for (Meta m : grades) {
      metaMap.put(m.getName(), m.getId());
    }
    // 加入部门
    Organization orgTemp = new Organization();
    orgTemp.setParentId(orgId);
    orgTemp.setType(2);
    List<Organization> bumenList = orgService.findAll(orgTemp);
    for (Organization org : bumenList) {
      metaMap.put(org.getName(), org.getId());
    }
    metaMap.put("男", 0);
    metaMap.put("女", 1);
    metaMap.put("其他", 0);
    metaMap.put("暂无", 0);
    metaMap.put("无", 0);
    return metaMap;
  }

  private Map<Integer, RoleType> initRoleType(int position) {
    Map<Integer, RoleType> roleTypeMap = new HashMap<Integer, RoleType>();
    RoleType roleType = new RoleType();
    roleType.setUsePosition(position);
    List<RoleType> roleTypeList = roleTypeService.findAll(roleType);
    for (RoleType rt : roleTypeList) {
      roleTypeMap.put(rt.getCode(), rt);
    }
    return roleTypeMap;
  }

  private Map<String, Role> initRole(Integer orgId, Integer position) {
    Map<String, Role> roleMap = new HashMap<String, Role>();
    // 加入角色元数据
    List<Role> roleList = roleService.findRoleListByUseOrgId(orgId, position);
    for (Role role : roleList) {
      roleMap.put(role.getRoleName(), role);
    }
    return roleMap;
  }

  /**
   * 批量注册区域用户
   * 
   * @param orgId
   * @param file
   * @return
   * @throws Exception
   * @see com.tmser.tr.back.ClassinfoService.service.RegisterService#batchRegiter_qyyh(java.lang.Integer,
   *      org.springframework.web.multipart.MultipartFile)
   */
  @Override
  public StringBuilder batchRegiter_qyyh(Integer orgId, MultipartFile file) {
    Map<String, Object> params = new HashMap<String, Object>();
    StringBuilder resultStr = new StringBuilder(); // 反馈信息
    params.put("orgId", orgId);
    params.put("userType", "AREA");
    try {
      super.importData(file.getInputStream(), params, resultStr);
    } catch (Exception e) {
      logger.error("read excel file failed", e);
    }
    return resultStr;
  }

  private Map<String, Integer> parseAllSubjectMap() {
    Map<String, Integer> subjectMap = new LinkedHashMap<String, Integer>();
    List<MetaRelationship> subjectMetas = MetaUtils.getPhaseSubjectMetaProvider().listAll();
    for (MetaRelationship mr : subjectMetas) {
      List<Meta> subjects = MetaUtils.getPhaseSubjectMetaProvider().listAllSubject(mr.getId());
      for (Meta m : subjects) {
        subjectMap.put(m.getName(), m.getId());
      }
    }

    return subjectMap;
  }

  private boolean addAreaSpace(List<UserSpace> batchSpaceList, StringBuilder resultStr, List<UserMenu> batchMenuList,
      int i, User tempUser, UserSpace userSpace, Map<String, User> userTempMap, Map<String, String> spaceTempMap,
      Map<String, String> mapTempMap, Map<String, UserSpace> areadySpaceIdMap) {
    String key = tempUser.getId() + "_l" + userSpace.getRoleId() + "_s" + userSpace.getSubjectId() + "_g"
        + userSpace.getGradeId();
    String spkey = key + "_p" + userSpace.getPhaseId();
    if (spaceTempMap.get(spkey) != null) {// 空间重复
      if (userTempMap.get(tempUser.getName()).getFlago() == null) {
        resultStr.append("数据重复：第" + (i) + "行数据已存在，已自动忽略当前行身份信息;<br>");
      } else {
        resultStr.append("数据重复：第" + (i) + "行与第" + userTempMap.get(tempUser.getName()).getFlago() + "行数据重复，已自动忽略;<br>");
      }
      if (areadySpaceIdMap.get(spkey) != null) {
        areadySpaceIdMap.put(spkey, null);
      }
      areadySpaceIdMap.put(key, null);
      return false;
    } else {
      // 新增用户空间（加入到待插入空间集合中）
      userSpace.setUserId(tempUser.getId());
      batchSpaceList.add(userSpace);
      spaceTempMap.put(spkey, "");

      // 加入菜单 每个用户的每种角色加一套菜单
      if (mapTempMap.get(tempUser.getName() + "_" + userSpace.getRoleId()) == null) {
        batchAddMenu(userSpace.getRoleId(), tempUser.getId(), batchMenuList);
        mapTempMap.put(tempUser.getName() + "_" + userSpace.getRoleId(), "");
      }
    }
    return true;
  }

  /**
   * 获取区域用户元数据的Id——名称map
   * 
   * @param orgId
   * @param phaseId
   * @return
   */
  private Map<String, Integer> getMetaMap2(Integer orgId) {
    Map<String, Integer> metaMap = new HashMap<String, Integer>();
    // 加入部门
    Organization orgTemp = new Organization();
    orgTemp.setParentId(orgId);
    orgTemp.setType(2);
    List<Organization> bumenList = orgService.findAll(orgTemp);
    for (Organization org : bumenList) {
      metaMap.put(org.getName(), org.getId());
    }
    metaMap.put("男", 0);
    metaMap.put("女", 1);
    metaMap.put("其他", 0);
    metaMap.put("暂无", 0);
    metaMap.put("无", 0);
    return metaMap;
  }

  private Map<String, MetaRelationship> initPhase() {
    Map<String, MetaRelationship> phaseMap = new HashMap<String, MetaRelationship>();
    // 加入学段元数据
    List<MetaRelationship> xueduanMeataList = MetaUtils.getPhaseMetaProvider().listAll();
    for (MetaRelationship xueduan : xueduanMeataList) {
      phaseMap.put(xueduan.getName(), xueduan);
    }
    return phaseMap;
  }

  private String gernerateLoginname(Organization org, String realname) {
    String prefix = StringUtils.isEmpty(org.getShortName()) ? org.getName() : org.getShortName();
    return LoginnameGernerator.gerneratorLoginName(prefix, realname);
  }

  private Login createLogin(Organization org, String userName, boolean isAdmin) {
    Login login = new Login();
    login.setLoginname(gernerateLoginname(org, userName));

    int index = 0;
    List<Login> oldLogins = loginService.find(login, 1);
    String name = login.getLoginname();
    while (oldLogins.size() > 0) {
      index++;
      login.setLoginname(name + index);
      oldLogins = loginService.find(login, 1);
      if (index > 5) {
        index += 10;
      }
    }

    login.setSalt(SecurityCode.getSecurityCode(8, SecurityCodeLevel.Hard, false));
    login.setPassword(passwordService.encryptPassword(login.getLoginname(), "123456", login.getSalt()));
    login.setIsAdmin(isAdmin);
    login.setEnable(1);
    login.setDeleted(false);
    login = loginService.save(login);

    return login;
  }

  enum ExcelHeader implements ExcelTitle {
    ID(32, "账号"),
    USERNAME(true, 32, "姓名", "真实姓名"),
    ROLE(true, "职务", "职位", "身份"),
    SUBJECT("学科", "任教学科"),
    GRADE("年级", "任教年级"),
    DEPARTMENT("部门", "所属部门"),
    JURISDICTION("管辖部门"),
    BOOK("教材版本", "所教教材版本"),
    BIRTHDAY("出生日期"),
    SEX("性别"),
    TEACHAGE(4, "教龄"),
    HONORARY(32, "荣誉称号"),
    BACKBONE(20, "骨干教师"),
    IDCARD(18, "身份证号"),
    PHONE(32, "联系电话"),
    POSTCODE(8, "邮编"),
    DESC(200, "个人简介"),
    ADDR(64, "联系地址"),
    NICKNAME(16, "昵称"),
    PROFESSION(32, "职称"),
    PHASE("学段"),
    EMAIL(64, "邮箱", "电子邮箱"),
    LOGINCODE(64, "登录码"),
    APPID("应用"),
    CERCODE(17, "教师资格证编号");

    private String[] mapNames;

    private boolean required = false;

    private int size = Integer.MAX_VALUE;

    private ExcelHeader(String... header) {
      this.mapNames = header;
    }

    private ExcelHeader(boolean required, String... header) {
      this.mapNames = header;
      this.required = required;
    }

    private ExcelHeader(Integer size, String... header) {
      this.mapNames = header;
      this.size = size;
    }

    private ExcelHeader(boolean required, Integer size, String... header) {
      this.mapNames = header;
      this.required = required;
    }

    @Override
    public List<String> getMapNames() {
      return Arrays.asList(mapNames);
    }

    @Override
    public boolean isRequired() {
      return required;
    }

    public static ExcelHeader map(String mapHeader) {
      ExcelHeader header = null;
      for (ExcelHeader eh : ExcelHeader.values()) {
        if (eh.getMapNames().contains(mapHeader)) {
          header = eh;
          break;
        }
      }
      return header;
    }

    public static List<ExcelHeader> requiedHeaders() {
      List<ExcelHeader> headers = new ArrayList<ExcelHeader>();
      for (ExcelHeader eh : ExcelHeader.values()) {
        if (eh.required) {
          headers.add(eh);
        }
      }
      return headers;
    }

    /**
     * @return 内容长度限制
     * @see com.tmser.tr.common.service.BatchService.ExcelTitle#size()
     */
    @Override
    public int size() {
      return this.size;
    }
  }

  /**
   * @param templateType
   * @param areaId
   * @param phaseId
   * @param orgId
   * @param response
   * @throws Exception
   * @see com.tmser.tr.back.yhgl.service.RegisterService#exportTemplateWithUser(java.lang.String,
   *      java.lang.Integer, java.lang.Integer, java.lang.Integer,
   *      javax.servlet.http.HttpServletResponse)
   */
  @Override
  public void exportTemplateWithUser(String templateType, Integer phaseId, Integer orgId,
      HttpServletResponse response) {
    String fileName = "批量注册模板";
    String filepath = "";
    Map<String, Object> params = new HashMap<String, Object>();
    StringBuilder sb = new StringBuilder();
    Organization org = orgService.findOne(orgId);
    fileName = org.getName() + "用户信息表";
    params.put("org", org);
    if ("xxyh".equals(templateType)) { // 学校用户模板
      filepath = "/registertemplate/registertemplate_xxyh_withuser.xls";
      params.put("phaseId", phaseId);
      params.put("exportType", "xxyh");
    } else if ("qyyh".equals(templateType)) {
      filepath = "/registertemplate/registertemplate_qyyh_withuser.xls";
      params.put("exportType", "qyyh");
    }

    try {
      String ext = filepath.substring(filepath.lastIndexOf("."));
      response.reset();
      response.setContentType("application/x-msdownload");
      response.setHeader("Content-Disposition",
          "attachment; filename=" + new String(fileName.getBytes("gb2312"), "ISO-8859-1") + ext);
      exportData(response.getOutputStream(), params, sb,
          new File(WebThreadLocalUtils.getRequest().getSession().getServletContext().getRealPath(filepath)));
    } catch (IOException e) {
      logger.error("", e);
      returnErrMsg(filepath, response);
    }
  }

  private void returnErrMsg(String path, HttpServletResponse response) {
    response.setHeader("Content-type", "text/html;charset=UTF-8");
    StringBuilder rb = new StringBuilder(path).append(" 不存在!");
    try {
      response.getWriter().write(rb.toString());
    } catch (IOException e) {
      logger.error("", e);
    }
  }

  /**
   * 初始化区域用户模板
   * 
   * @param sheet
   * @param org
   * @param phaseId
   */
  private void initAreaSheetTeplate(Sheet sheet, Organization org, Integer phaseId, Map<ExcelTitle, Column> headers) {
    Column cn = null;
    if ((cn = headers.get(ExcelHeader.SEX)) != null) {
      int clumnIndex = cn.getIndex();
      sheet = setPromptOfCell(sheet, SEXARR, headline + 1, 500, clumnIndex, clumnIndex);
    }

    if ((cn = headers.get(ExcelHeader.DEPARTMENT)) != null) {
      int clumnIndex = cn.getIndex();
      Organization orgTemp = new Organization();
      orgTemp.setParentId(org.getId());
      orgTemp.setType(2);
      List<Organization> bumenList = orgService.findAll(orgTemp);
      if (bumenList != null && bumenList.size() > 0) {
        sheet = setPromptOfCell(sheet, objectListToStringArray(bumenList, "getName", true), headline + 1, 500,
            clumnIndex, clumnIndex); // 所属部门
      }
    }
    if ((cn = headers.get(ExcelHeader.ROLE)) != null) {
      int clumnIndex = cn.getIndex();
      List<Role> roleList = roleService.findRoleListByUseOrgId(org.getId(), 1);
      sheet = setPromptOfCell(sheet, objectListToStringArray(roleList, "getRoleName", false), headline + 1, 500,
          clumnIndex, clumnIndex); // 职务
    }

    if ((cn = headers.get(ExcelHeader.PHASE)) != null) {
      int clumnIndex = cn.getIndex();
      List<MetaRelationship> xueduanMeataList = MetaUtils.getPhaseMetaProvider().listAll();
      sheet = setPromptOfCell(sheet, objectListToStringArray(xueduanMeataList, "getName", false), headline + 1, 500,
          clumnIndex, clumnIndex); // 职务
    }

    if ((cn = headers.get(ExcelHeader.SUBJECT)) != null) {
      int clumnIndex = cn.getIndex();
      Set<String> subjectNameSet = parseAllSubjectMap().keySet();
      String[] subjectArray = subjectNameSet.toArray(new String[subjectNameSet.size() + 1]);
      subjectArray[subjectNameSet.size()] = "无";
      sheet = setPromptOfCell(sheet, subjectArray, headline + 1, 500, clumnIndex, clumnIndex); // 学科
    }

    if ((cn = headers.get(ExcelHeader.BACKBONE)) != null) {
      int clumnIndex = cn.getIndex();
      sheet = setPromptOfCell(sheet, new String[] { "国家骨干", "特级骨干", "省级骨干", "市级骨干", "区县级骨干", "校级骨干", "无" },
          headline + 1, 500, clumnIndex, clumnIndex);// 骨干教师
    }
  }

  /**
   * 初始化学校模板
   * 
   * @param sheet
   * @param org
   * @param phaseId
   */
  private void initSheetTeplate(Workbook workbook, Sheet sheet, Organization org, Integer phaseId,
      Map<ExcelTitle, Column> headers) {
    Column cn = null;
    Integer subjectColumnIndex = null;
    Integer gradeColumnIndex = null;
    if ((cn = headers.get(ExcelHeader.SEX)) != null) {
      int clumnIndex = cn.getIndex();
      sheet = setPromptOfCell(sheet, SEXARR, headline + 1, 500, clumnIndex, clumnIndex);
    }

    if ((cn = headers.get(ExcelHeader.DEPARTMENT)) != null) {
      int clumnIndex = cn.getIndex();
      Organization orgTemp = new Organization();
      orgTemp.setParentId(org.getId());
      orgTemp.setType(2);
      List<Organization> bumenList = orgService.findAll(orgTemp);
      if (bumenList != null && bumenList.size() > 0) {
        sheet = setPromptOfCell(sheet, objectListToStringArray(bumenList, "getName", true), headline + 1, 500,
            clumnIndex, clumnIndex); // 所属部门
      }
    }
    if ((cn = headers.get(ExcelHeader.ROLE)) != null) {
      int clumnIndex = cn.getIndex();
      List<Role> roleList = roleService.findRoleListByUseOrgId(org.getId(), 2);
      sheet = setPromptOfCell(sheet, objectListToStringArray(roleList, "getRoleName", false), headline + 1, 500,
          clumnIndex, clumnIndex); // 职务
    }

    if ((cn = headers.get(ExcelHeader.SUBJECT)) != null) {
      subjectColumnIndex = cn.getIndex();
      List<Meta> subjects = MetaUtils.getPhaseSubjectMetaProvider().listAllSubject(org.getId(), phaseId,
          StringUtils.toIntegerArray(org.getAreaIds().substring(1, org.getAreaIds().lastIndexOf(",")), ","));
      String subjectnames[] = new String[subjects.size() + 1];
      int i = 0;
      for (Meta meta : subjects) {
        subjectnames[i++] = meta.getName();
      }
      subjectnames[i] = "无";
      // MetaRelationship mr =
      // MetaUtils.getPhaseSubjectMetaProvider().getMetaRelationshipByPhaseId(phaseId);
      sheet = setPromptOfCell(sheet, subjectnames, headline + 1, 500, subjectColumnIndex, subjectColumnIndex); // 学科
    }

    if ((cn = headers.get(ExcelHeader.GRADE)) != null) {
      gradeColumnIndex = cn.getIndex();
      List<Meta> gradeMetas = MetaUtils.getOrgTypeMetaProvider().listAllGrade(org.getSchoolings(), phaseId);
      String gradenames[] = new String[gradeMetas.size() + 1];
      int i = 0;
      for (Meta meta : gradeMetas) {
        gradenames[i++] = meta.getName();
      }
      gradenames[i] = "无";
      sheet = setPromptOfCell(sheet, gradenames, headline + 1, 500, gradeColumnIndex, gradeColumnIndex); // 年级
    }
    if ((cn = headers.get(ExcelHeader.BOOK)) != null) {
      int clumnIndex = cn.getIndex();

      BookSync bk = new BookSync();
      bk.setPhaseId(MetaUtils.getPhaseMetaProvider().getMetaRelationship(phaseId).getEid());
      bk.addCustomCulomn("gradeLevel,gradeLevelId,subject,subjectId,formatName");
      List<BookSync> findSyncBooksOfJy = bookService.findBookSync(bk);
      Map<String, Object> bookMap = new HashMap<String, Object>();
      for (BookSync bookSync : findSyncBooksOfJy) {
        String key = MetaUtils.getMeta(bookSync.getGradeLevelId()).getName()
            + MetaUtils.getMeta(bookSync.getSubjectId()).getName();
        String formatName = bookSync.getFormatName();
        if (formatName != null) {
          @SuppressWarnings("unchecked")
          Set<String> bookSet = (Set<String>) bookMap.get(key);
          if (bookMap.containsKey(key)) {
            bookSet.add(formatName);
          } else {
            TreeSet<String> treeSet = new TreeSet<String>();
            treeSet.add(formatName);
            bookMap.put(key, treeSet);
          }
        }
      }
      if (subjectColumnIndex != null && gradeColumnIndex != null) {
        ExcelUtils.createCascadeRecord(workbook, bookMap);// 创建数据字典第一行
        for (int start = headline + 1; start < 500; start++) {
          int relationRow = start + 1;
          String subjectToColumn = ExcelUtils.indexToColumn(subjectColumnIndex + 1);
          String gradeToColumn = ExcelUtils.indexToColumn(gradeColumnIndex + 1);
          DataValidation setDataValidationByName = ExcelUtils.createDataValidation(sheet,
              "INDIRECT($" + gradeToColumn + "$" + relationRow + "&$" + subjectToColumn + "$" + relationRow + ")",
              start, start, clumnIndex, clumnIndex);
          sheet.addValidationData(setDataValidationByName);
        }

      }
    }
    if ((cn = headers.get(ExcelHeader.BACKBONE)) != null) {
      int clumnIndex = cn.getIndex();
      sheet = setPromptOfCell(sheet, new String[] { "国家骨干", "特级骨干", "省级骨干", "市级骨干", "区县级骨干", "校级骨干", "无" },
          headline + 1, 500, clumnIndex, clumnIndex);// 骨干教师
    }
    if ((cn = headers.get(ExcelHeader.PHASE)) != null) {
      int clumnIndex = cn.getIndex();
      List<Meta> phaseMate = MetaUtils.getPhaseMetaProvider().listAllPhaseMeta();
      String phasenames[] = new String[phaseMate.size() + 1];
      int i = 0;
      for (Meta meta : phaseMate) {
        phasenames[i++] = meta.getName();
      }
      phasenames[i] = "无";
      sheet = setPromptOfCell(sheet, phasenames, headline + 1, 500, clumnIndex, clumnIndex); // 学段
    }
  }

  /**
   * 填充用户数据
   */
  @Override
  protected void fillSheetData(Sheet sheet, Map<String, Object> params, StringBuilder resultMsg) {
    Organization org = (Organization) params.get("org");
    Integer phaseId = (Integer) params.get("phaseId");
    String orgStr = "机构名称：" + org.getName();
    Area area1 = areaService.findOne(org.getAreaId());
    if (area1.getParentId().intValue() != 0) {
      Area area2 = areaService.findOne(area1.getParentId());
      orgStr = "省(市)：" + area2.getName() + "     区(县)：" + area1.getName() + "     " + orgStr;
    } else {
      orgStr = "区(县)：" + area1.getName() + "     " + orgStr;
    }
    sheet.getRow(2).getCell(0).setCellValue(orgStr);

    Map<ExcelTitle, Column> headers = parseExcelHeader(sheet, headline);
    initSheetTeplate(sheet.getWorkbook(), sheet, org, phaseId, headers);
    int startLine = headline + 1;
    User umodel = new User();
    umodel.setOrgId(org.getId());
    List<User> userList = userService.findAll(umodel);
    UserSpace smodel = new UserSpace();
    Integer schoolYear = schoolYearService.getCurrentSchoolYear();
    Map<Integer, RoleType> roleTypeMap = initRoleType(2);
    for (User u : userList) {
      Login l = loginService.findOne(u.getId());
      if (l.getDeleted() || !(UserSpace.ENABLE == l.getEnable())) {
        continue;
      }
      smodel.setUserId(u.getId());
      if ("xxyh".equals(params.get("exportType"))) {
        smodel.setSchoolYear(schoolYear);
      }
      smodel.setEnable(UserSpace.ENABLE);
      List<UserSpace> spaceList = userSpaceService.findAll(smodel);
      if (spaceList.size() > 0) {
        for (UserSpace sp : spaceList) {
          RoleType type = roleTypeMap.get(sp.getSysRoleId());
          if (type != null && (type.getIsNoXk() || type.getIsNoNj())) {
            if (!sp.getPhaseId().equals(phaseId)) {
              continue;
            }
          }
          if (!phaseId.equals(sp.getPhaseId()) && !Integer.valueOf(0).equals(sp.getPhaseId())) {
            continue;
          }

          Row row = sheet.getRow(startLine);
          if (row == null) {
            row = sheet.createRow(startLine);
          }
          fillUserInfo(row, headers, l, u);

          insertCellData(row, headers, ExcelHeader.SUBJECT,
              MetaUtils.getPhaseSubjectMetaProvider().getMeta(sp.getSubjectId()));
          insertCellData(row, headers, ExcelHeader.GRADE,
              MetaUtils.getPhaseGradeMetaProvider().getMeta(sp.getGradeId()));
          insertCellData(row, headers, ExcelHeader.ROLE, roleService.findOne(sp.getRoleId()));
          insertCellData(row, headers, ExcelHeader.DEPARTMENT, orgService.findOne(sp.getDepartmentId()));
          insertCellData(row, headers, ExcelHeader.BOOK, getBookShortName(sp.getBookId()));
          if (!Integer.valueOf(0).equals(sp.getPhaseId())) {
            insertCellData(row, headers, ExcelHeader.PHASE,
                MetaUtils.getPhaseMetaProvider().getMetaRelationship(sp.getPhaseId()).getName());
          }
          if (StringUtils.isNotBlank(sp.getConDepIds())) {
            String[] depids = StringUtils.split(sp.getConDepIds(), StringUtils.COMMA);
            if (depids == null) {
              continue;
            }
            List<String> deplist = new ArrayList<String>();
            for (String depid : depids) {
              Organization d = orgService.findOne(Integer.valueOf(depid));
              if (d != null) {
                deplist.add(d.getName());
              }
            }

            if (deplist.size() > 0) {
              insertCellData(row, headers, ExcelHeader.JURISDICTION, StringUtils.join(deplist.iterator(), "、"));
            }
          }
          startLine++;
        }
      } else {
        Row row = sheet.getRow(startLine);
        if (row == null) {
          row = sheet.createRow(startLine);
        }
        fillUserInfo(row, headers, l, u);
        startLine++;
      }

    }
  }

  private void fillUserInfo(Row row, Map<ExcelTitle, Column> headers, Login l, User u) {
    DateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    insertCellData(row, headers, ExcelHeader.ID, l.getLoginname());
    insertCellData(row, headers, ExcelHeader.USERNAME, u.getName());
    insertCellData(row, headers, ExcelHeader.SEX, StringUtils.nullToEmpty(SEXMAP.get(u.getSex())));
    insertCellData(row, headers, ExcelHeader.BACKBONE, u.getTeacherLevel());
    insertCellData(row, headers, ExcelHeader.BIRTHDAY,
        u.getBirthday() != null ? SIMPLE_DATE_FORMAT.format(u.getBirthday()) : "");
    insertCellData(row, headers, ExcelHeader.CERCODE, u.getCercode());
    insertCellData(row, headers, ExcelHeader.IDCARD, u.getIdcard());
    insertCellData(row, headers, ExcelHeader.HONORARY, u.getHonorary());
    insertCellData(row, headers, ExcelHeader.NICKNAME, u.getNickname());
    insertCellData(row, headers, ExcelHeader.PHONE, u.getCellphone());
    insertCellData(row, headers, ExcelHeader.EMAIL, u.getMail());
    insertCellData(row, headers, ExcelHeader.POSTCODE, u.getPostcode());
    insertCellData(row, headers, ExcelHeader.PROFESSION, u.getProfession());
    insertCellData(row, headers, ExcelHeader.APPID, u.getAppId());
    insertCellData(row, headers, ExcelHeader.ADDR, u.getAddress());
    insertCellData(row, headers, ExcelHeader.DESC, u.getExplains());
    insertCellData(row, headers, ExcelHeader.TEACHAGE, u.getSchoolAge());
  }

  private void insertCellData(Row row, Map<ExcelTitle, Column> headers, ExcelTitle header, Object value) {
    Column cn = null;
    if ((cn = headers.get(header)) != null && value != null) {
      if (value instanceof Meta) {
        value = ((Meta) value).getName();
      }
      if (value instanceof Role) {
        value = ((Role) value).getRoleName();
      }
      if (value instanceof Organization) {
        value = ((Organization) value).getName();
      }
      Cell cell = row.getCell(cn.getIndex());
      if (cell == null) {
        cell = row.createCell(cn.getIndex());
      }
      cell.setCellValue(String.valueOf(value));
    }

  }

  private String getBookShortName(String bookid) {
    String name = "";
    if (StringUtils.isNotEmpty(bookid)) {
      Book b = bookService.findOne(bookid);
      if (b != null) {
        name = b.getFormatName();
      }
    }
    return name;
  }

  /**
   * @return
   * @see com.tmser.tr.common.service.ExcelBatchService#sheetIndex()
   */
  @Override
  public int sheetIndex() {
    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * @return
   * @see com.tmser.tr.common.service.ExcelBatchService#titleLine()
   */
  @Override
  public int titleLine() {
    return headline;
  }

  /**
   * @return
   * @see com.tmser.tr.common.service.ExcelBatchService#titles()
   */
  @Override
  protected ExcelTitle[] titles() {
    return ExcelHeader.values();
  }

  /**
   * @param sheet
   * @param params
   * @param returnMsg
   * @see com.tmser.tr.common.service.ExcelBatchService#endSheetParse(org.apache.poi.hssf.usermodel.Sheet,
   *      java.util.Map, java.lang.StringBuilder)
   */
  @Override
  @SuppressWarnings("unchecked")
  protected void endSheetParse(Sheet sheet, Map<String, Object> params, StringBuilder returnMsg) {
    if (params != null) {
      List<UserSpace> batchSpaceList = (List<UserSpace>) params.get("batchSpaceList"); // 待插入用户空间集合
      // List<UserMenu> batchMenuList = (List<UserMenu>)
      // params.get("batchMenuList"); // 待插入用户菜单集合
      List<User> batchUserList = (List<User>) params.get("batchUserList");// 待插入用户集合
      Map<String, UserSpace> areadySpaceIdMap = (Map<String, UserSpace>) params.get("areadySpaceIdMap");// 需要删除的用户空间id
      Map<String, String> spaceTempMap = (Map<String, String>) params.get("spaceTempMap");// 需要删除的用户空间id

      Set<Integer> deleteSpaceIdSet = new TreeSet<Integer>();// 需要删除的用户空间id
      List<UserSpace> deleteSpace = new ArrayList<UserSpace>();// 需要删除的用户空间
      for (String spaceKey : spaceTempMap.keySet()) {
        UserSpace space = areadySpaceIdMap.get(spaceKey);
        if (space != null && space.getId() != null && !deleteSpaceIdSet.contains(space.getId())) {
          deleteSpaceIdSet.add(space.getId());
          deleteSpace.add(space);
        }
      }

      if (!CollectionUtils.isEmpty(deleteSpace)) {
        for (UserSpace space : deleteSpace) {
          userManageService.delUserSpace(space);
        }
      }

      // 开始批量插入
      userService.batchSave(batchUserList);
      userSpaceService.batchSave(batchSpaceList);
      Map<String, UserRole> userRoleMap = new HashMap<>();
      for (UserSpace userSpace : batchSpaceList) {
        String roleKey = "u" + userSpace.getUserId() + "-" + userSpace.getRoleId();
        UserRole ur = new UserRole();
        ur.setUserId(userSpace.getUserId());
        ur.setRoleId(userSpace.getRoleId());
        userRoleMap.put(roleKey, ur);
      }
      if (!userRoleMap.isEmpty()) {
        userRoleService.batchSave(new ArrayList<UserRole>(userRoleMap.values()));
      }
      // userMenuService.batchSave(batchMenuList);

    }
  }

  /**
   * @param sheet
   * @param params
   * @param returnMsg
   * @see com.tmser.tr.common.service.ExcelBatchService#beforeSheetParse(org.apache.poi.hssf.usermodel.Sheet,
   *      java.util.Map, java.lang.StringBuilder)
   */
  @Override
  protected void beforeSheetParse(Sheet sheet, Map<String, Object> params, StringBuilder returnMsg) {
    if (params != null) {
      if ("AREA".equals(params.get("userType"))) {// 处理区域用户
        Integer orgId = (Integer) params.get("orgId");
        params.put("schoolYear", schoolYearService.getCurrentSchoolYear());// 学年
        params.put("batchUserList", new ArrayList<User>());// 待插入用户集合
        params.put("userTempMap", new HashMap<String, User>()); // 临时用户map<用户姓名，用户object>,用于判断待插入的数据是否重复
        params.put("batchSpaceList", new ArrayList<UserSpace>()); // 待插入用户空间集合
        params.put("spaceTempMap", new HashMap<String, String>()); // 临时空间map
        params.put("batchMenuList", new ArrayList<UserMenu>()); // 待插入用户菜单集合
        params.put("mapTempMap", new HashMap<String, String>()); // 临时菜单map
        params.put("areadySpaceIdMap", new HashMap<String, UserSpace>());// 已经存在的用户空间id
        Organization org = orgService.findOne(orgId);
        params.put("org", org);
        params.put("metaMap", getMetaMap2(orgId));
        params.put("roleMap", initRole(orgId, RoleType.APPLICATION_AREA));
        params.put("roleTypeMap", initRoleType(RoleType.APPLICATION_AREA));
        params.put("phaseMap", initPhase());

        Map<String, Integer> subjectMap = parseAllSubjectMap();
        subjectMap.put("无", 0);
        params.put("subjectMap", subjectMap);
      } else if ("SCHOOL".equals(params.get("userType"))) {
        Integer orgId = (Integer) params.get("orgId");
        Integer phaseId = (Integer) params.get("phaseId");
        params.put("schoolYear", schoolYearService.getCurrentSchoolYear());// 学年
        params.put("xueduan", MetaUtils.getPhaseMetaProvider().getMetaRelationship(phaseId));// 学段元数据
        params.put("batchUserList", new ArrayList<User>());// 待插入用户集合
        params.put("userTempMap", new HashMap<String, User>()); // 临时用户map<用户姓名，用户object>,用于判断待插入的数据是否重复
        params.put("batchSpaceList", new ArrayList<UserSpace>()); // 待插入用户空间集合
        params.put("spaceTempMap", new HashMap<String, String>()); // 临时空间map
        params.put("batchMenuList", new ArrayList<UserMenu>()); // 待插入用户菜单集合
        params.put("mapTempMap", new HashMap<String, String>()); // 临时菜单map
        Organization org = orgService.findOne(orgId);
        params.put("org", org);
        params.put("metaMap", getMetaMap(org.getSchoolings(), phaseId, orgId));
        params.put("roleMap", initRole(orgId, 2));
        params.put("roleTypeMap", initRoleType(2));
        params.put("areadySpaceIdMap", new HashMap<String, UserSpace>());// 已经存在的用户空间id
      }
    }
  }

  /**
   * @param rowValueMap
   * @param params
   * @param row
   * @param returnMsg
   * @see com.tmser.tr.common.service.ExcelBatchService#parseRow(java.util.Map,
   *      java.util.Map, org.apache.poi.hssf.usermodel.Row,
   *      java.lang.StringBuilder)
   */
  @Override
  @SuppressWarnings(value = "unchecked")
  protected void parseRow(Map<ExcelTitle, String> rowValueMap, Map<String, Object> params, Row row,
      StringBuilder resultStr) {
    int i = row.getRowNum() + 1;
    if (params != null) {
      if ("AREA".equals(params.get("userType"))) {// 处理区域用户
        Organization org = (Organization) params.get("org");
        Integer schoolYear = (Integer) params.get("schoolYear");// 学年
        List<User> batchUserList = (List<User>) params.get("batchUserList");// 待插入用户集合
        Map<String, User> userTempMap = (Map<String, User>) params.get("userTempMap"); // 临时用户map<用户姓名，用户object>,用于判断待插入的数据是否重复
        List<UserSpace> batchSpaceList = (List<UserSpace>) params.get("batchSpaceList"); // 待插入用户空间集合
        Map<String, String> spaceTempMap = (Map<String, String>) params.get("spaceTempMap"); // 临时空间map
        List<UserMenu> batchMenuList = (List<UserMenu>) params.get("batchMenuList"); // 待插入用户菜单集合
        Map<String, String> mapTempMap = (Map<String, String>) params.get("mapTempMap"); // 临时菜单map
        Map<String, Integer> metaMap = (Map<String, Integer>) params.get("metaMap");
        Map<String, Role> roleMap = (Map<String, Role>) params.get("roleMap");
        Map<Integer, RoleType> roleTypeMap = (Map<Integer, RoleType>) params.get("roleTypeMap");
        Map<String, Integer> subjectMap = (Map<String, Integer>) params.get("subjectMap");
        Map<String, MetaRelationship> phaseMap = (Map<String, MetaRelationship>) params.get("phaseMap");
        Map<String, UserSpace> areadySpaceIdMap = (Map<String, UserSpace>) params.get("areadySpaceIdMap");// 已经存在的用户空间id

        String userName = rowValueMap.get(ExcelHeader.USERNAME).replaceAll(" ", "");
        String role = rowValueMap.get(ExcelHeader.ROLE);
        String subject = rowValueMap.get(ExcelHeader.SUBJECT);
        String phase = rowValueMap.get(ExcelHeader.PHASE);
        String bumen = rowValueMap.get(ExcelHeader.DEPARTMENT);
        String cor_bumen = rowValueMap.get(ExcelHeader.JURISDICTION);
        String birthdayStr = rowValueMap.get(ExcelHeader.BIRTHDAY);
        String schoolAgeStr = rowValueMap.get(ExcelHeader.TEACHAGE);
        Integer schoolAge = null;
        Date birthday = null;
        RoleType roleType = roleTypeMap.get(roleMap.get(role).getSysRoleId());
        Integer phaseId = 0;
        Integer phaseType = null;
        String phaseName = "";
        String subjectName = "";
        if (roleType.getIsNoXz()) {// 需要有学段
          if (StringUtils.isBlank(phase)) {
            resultStr.append("数据错误：第" + (i) + "行中的‘学段’应为必填项,已自动忽略忽略该行;<br>");
            return;
          } else if (phaseMap.get(phase) == null) {
            resultStr.append("数据错误：第" + (i) + "行中的‘学段’在该机构中不存在,已自动忽略忽略该行;<br>");
            return;
          } else {
            phaseId = phaseMap.get(phase).getId();
            phaseType = phaseMap.get(phase).getEid();
            phaseName = phase;
          }

        }
        if (!StringUtils.isBlank(schoolAgeStr)) {
          try {
            schoolAge = Integer.valueOf(schoolAgeStr);
          } catch (Exception e) {
          }
        }

        if (!StringUtils.isBlank(birthdayStr)) {
          try {
            birthday = DateUtils.parseDate(birthdayStr);
          } catch (Exception e) {
            // do nothing
          }
        }
        String cor_bumen_id = bumenNamesToBumenIds(cor_bumen, metaMap);// 管辖部门名称转管辖部门id
        if (cor_bumen_id == null) {
        } else if ("0".equals(cor_bumen_id)) {
          cor_bumen_id = null;
        }
        Integer subjectId = 0;
        Integer gradeId = 0;
        if (roleType.getIsNoXk()) { // 设置学科
          if (StringUtils.isBlank(subject)) {
            resultStr.append("数据格式错误：第" + i + "行中的‘学科’应为必填,已自动忽略忽略该行;<br>");
            return;
          }
          subjectId = subjectMap.get(subject);
          if (subjectId == null) {
            resultStr.append("数据格式错误：第" + (i) + "行中的‘学科’在该校中不存在,已自动忽略忽略该行;<br>");
            return;
          }
          subjectName = subject;
        }

        String homeUrl = getHomeUrl(roleMap.get(role).getSysRoleId());
        // 新增用户空间（加入到待插入空间集合中）
        UserSpace userSpace = new UserSpace();
        userSpace.setUsername(userName);
        userSpace.setSpaceName(phaseName + subjectName + role);
        userSpace.setSpaceHomeUrl(homeUrl);
        userSpace.setOrgId(org.getId());
        userSpace.setPhaseType(phaseType);
        userSpace.setPhaseId(phaseId);
        userSpace.setSysRoleId(roleMap.get(role).getSysRoleId());
        userSpace.setRoleId(roleMap.get(role).getId());
        userSpace.setSubjectId(subjectId);
        userSpace.setGradeId(gradeId);
        userSpace.setSort(1);
        userSpace.setIsDefault(false);
        userSpace.setEnable(1);
        userSpace.setSchoolYear(schoolYear);
        userSpace.setDepartmentId(metaMap.get(bumen));
        userSpace.setConDepIds(cor_bumen_id);
        // 处理设置了用户账号情况

        User user = new User();
        user.setUserType(User.AREA_USER);
        user.setNickname(userName);
        user.setName(userName);
        user.setOrgId(org.getId());
        user.setOrgName(org.getName());
        user.setCellphoneValid(false);
        user.setCellphoneView(false);
        user.setAppId(parseAppid(rowValueMap.get(ExcelHeader.APPID)));
        user.setSex(metaMap.get(rowValueMap.get(ExcelHeader.SEX)));
        user.setMailValid(false);
        user.setMailView(false);
        user.setIsFamousTeacher(0);
        user.setProfession(rowValueMap.get(ExcelHeader.PROFESSION));
        user.setSchoolAge(schoolAge);
        user.setHonorary(rowValueMap.get(ExcelHeader.HONORARY));
        user.setBirthday(birthday);
        user.setIdcard(rowValueMap.get(ExcelHeader.IDCARD));
        user.setCellphone(rowValueMap.get(ExcelHeader.PHONE));
        user.setExplains(rowValueMap.get(ExcelHeader.DESC));
        user.setTeacherLevel(rowValueMap.get(ExcelHeader.BACKBONE));
        user.setPostcode(rowValueMap.get(ExcelHeader.POSTCODE));
        user.setAddress(rowValueMap.get(ExcelHeader.ADDR));
        user.setMail(rowValueMap.get(ExcelHeader.EMAIL));
        user.setCercode(rowValueMap.get(ExcelHeader.CERCODE));
        if (StringUtils.isBlank(rowValueMap.get(ExcelHeader.NICKNAME))) {
          user.setNickname(userName);
        } else {
          user.setNickname(rowValueMap.get(ExcelHeader.NICKNAME));
        }
        user.setCrtDttm(new Date());
        user.setLastupDttm(new Date());
        user.setFlago(String.valueOf(i));
        if (StringUtils.isNotBlank(rowValueMap.get(ExcelHeader.ID))) {
          parseExitsCount(rowValueMap.get(ExcelHeader.ID), user, userTempMap, spaceTempMap, areadySpaceIdMap);
        }

        if (userTempMap.get(userName) != null) { // 用户相同，导入其他身份
          user = userTempMap.get(userName);
        } else {// 用户与之前不同
          User tempUser = userService.getUserByName(userName, org.getId());
          if (tempUser == null) {// 用户不存在
            // 新增登陆信息
            Login login = createLogin(org, userName, false);
            // 新增用户信息（加入到待插入用户集合中）
            user.setId(login.getId());
            batchUserList.add(user);
          } else {// 用户已存在，增加身份
            fillSpace(tempUser, schoolYear, spaceTempMap, mapTempMap);
            user = tempUser;
          }
          userTempMap.put(userName, user);
        }
        addAreaSpace(batchSpaceList, resultStr, batchMenuList, i, user, userSpace, userTempMap, spaceTempMap,
            mapTempMap, areadySpaceIdMap);
      } else if ("SCHOOL".equals(params.get("userType"))) {// 处理学校用户
        if (row != null) {
          Organization org = (Organization) params.get("org");
          Integer schoolYear = (Integer) params.get("schoolYear");// 学年
          MetaRelationship xueduan = (MetaRelationship) params.get("xueduan");// 学段元数据
          List<User> batchUserList = (List<User>) params.get("batchUserList");// 待插入用户集合
          Map<String, User> userTempMap = (Map<String, User>) params.get("userTempMap"); // 临时用户map<用户姓名，用户object>,用于判断待插入的数据是否重复
          List<UserSpace> batchSpaceList = (List<UserSpace>) params.get("batchSpaceList"); // 待插入用户空间集合
          Map<String, String> spaceTempMap = (Map<String, String>) params.get("spaceTempMap"); // 临时空间map
          List<UserMenu> batchMenuList = (List<UserMenu>) params.get("batchMenuList"); // 待插入用户菜单集合
          Map<String, String> mapTempMap = (Map<String, String>) params.get("mapTempMap"); // 临时菜单map
          Map<String, Integer> metaMap = (Map<String, Integer>) params.get("metaMap");
          Map<String, Role> roleMap = (Map<String, Role>) params.get("roleMap");
          Map<Integer, RoleType> roleTypeMap = (Map<Integer, RoleType>) params.get("roleTypeMap");
          Map<String, UserSpace> areadySpaceIdMap = (Map<String, UserSpace>) params.get("areadySpaceIdMap");// 已经存在的用户空间id

          String userName = rowValueMap.get(ExcelHeader.USERNAME).replaceAll(" ", "");
          String role = rowValueMap.get(ExcelHeader.ROLE);
          String subject = rowValueMap.get(ExcelHeader.SUBJECT);
          String grade = rowValueMap.get(ExcelHeader.GRADE);

          String bumen = rowValueMap.get(ExcelHeader.DEPARTMENT);
          String cor_bumen = rowValueMap.get(ExcelHeader.JURISDICTION);
          String birthdayStr = rowValueMap.get(ExcelHeader.BIRTHDAY);
          String schoolAgeStr = rowValueMap.get(ExcelHeader.TEACHAGE);
          String formatName = rowValueMap.get(ExcelHeader.BOOK);
          Integer schoolAge = null;
          Date birthday = null;

          if (!StringUtils.isBlank(schoolAgeStr)) {
            try {
              schoolAge = Integer.valueOf(schoolAgeStr);
            } catch (Exception e) {
            }
          }
          if (!StringUtils.isBlank(birthdayStr)) {
            try {
              birthday = DateUtils.parseDate(birthdayStr);
            } catch (Exception e) {
              // do nothing
            }
          }
          String cor_bumen_id = bumenNamesToBumenIds(cor_bumen, metaMap);// 管辖部门名称转管辖部门id
          if ("0".equals(cor_bumen_id)) {
            cor_bumen_id = null;
          }
          if (roleMap.get(role) == null) {
            resultStr.append("数据格式错误：第" + (i) + "行中的‘职务’在该校中不存在,已自动忽略忽略该行,请联系管理员;<br>");
            return;
          }
          RoleType roleType = roleTypeMap.get(roleMap.get(role).getSysRoleId());
          Integer subjectId = 0;
          Integer gradeId = 0;
          if (roleType.getIsNoXk()) { // 设置学科
            if (StringUtils.isBlank(subject)) {
              resultStr.append("数据格式错误：第" + (i) + "行中的‘学科’应为必填,已自动忽略忽略该行;<br>");
              return;
            }
            subjectId = metaMap.get(subject);
            if (subjectId == null) {
              resultStr.append("数据格式错误：第" + (i) + "行中的‘学科’在该校中不存在,已自动忽略忽略该行;<br>");
              return;
            }
          }
          if (roleType.getIsNoNj()) {// 设置年级
            if (StringUtils.isBlank(grade)) {
              resultStr.append("数据格式错误：第" + (i) + "行中的‘年级’应为必填,已自动忽略忽略该行;<br>");
              return;
            }
            gradeId = metaMap.get(grade);
            if (gradeId == null) {
              resultStr.append("数据格式错误：第" + (i) + "行中的‘年级’在该校中不存在,已自动忽略忽略该行;<br>");
              return;
            }
          }
          if (subjectId == 0) {
            subject = "";
          }
          if (gradeId == 0) {
            grade = "";
          }
          String homeUrl = getHomeUrl(roleMap.get(role).getSysRoleId());
          // 新增用户空间（加入到待插入空间集合中）
          UserSpace userSpace = new UserSpace();
          userSpace.setUsername(userName);
          userSpace.setSpaceName(grade + subject + role);
          userSpace.setSpaceHomeUrl(homeUrl);
          userSpace.setOrgId(org.getId());
          userSpace.setPhaseType(xueduan.getEid());
          userSpace.setPhaseId(xueduan.getId());
          userSpace.setSysRoleId(roleMap.get(role).getSysRoleId());
          userSpace.setRoleId(roleMap.get(role).getId());
          userSpace.setSubjectId(subjectId);
          userSpace.setGradeId(gradeId);
          userSpace.setSort(1);
          userSpace.setIsDefault(false);
          userSpace.setEnable(1);
          userSpace.setSchoolYear(schoolYear);
          userSpace.setDepartmentId(metaMap.get(bumen));
          userSpace.setConDepIds(cor_bumen_id);
          // 处理设置了用户账号情况

          User user = new User();
          user.setUserType(User.SCHOOL_USER);
          user.setNickname(userName);
          user.setName(userName);
          user.setOrgId(org.getId());
          user.setOrgName(org.getName());
          user.setCellphoneValid(false);
          user.setCellphoneView(false);
          user.setAppId(parseAppid(rowValueMap.get(ExcelHeader.APPID)));
          user.setSex(metaMap.get(rowValueMap.get(ExcelHeader.SEX)));
          user.setMailValid(false);
          user.setMailView(false);
          user.setIsFamousTeacher(0);
          user.setProfession(rowValueMap.get(ExcelHeader.PROFESSION));
          user.setSchoolAge(schoolAge);
          user.setHonorary(rowValueMap.get(ExcelHeader.HONORARY));
          user.setBirthday(birthday);
          user.setIdcard(rowValueMap.get(ExcelHeader.IDCARD));
          user.setCellphone(rowValueMap.get(ExcelHeader.PHONE));
          user.setExplains(rowValueMap.get(ExcelHeader.DESC));
          user.setTeacherLevel(rowValueMap.get(ExcelHeader.BACKBONE));
          user.setPostcode(rowValueMap.get(ExcelHeader.POSTCODE));
          user.setAddress(rowValueMap.get(ExcelHeader.ADDR));
          user.setMail(rowValueMap.get(ExcelHeader.EMAIL));
          user.setCercode(rowValueMap.get(ExcelHeader.CERCODE));
          if (StringUtils.isBlank(rowValueMap.get(ExcelHeader.NICKNAME))) {
            user.setNickname(userName);
          } else {
            user.setNickname(rowValueMap.get(ExcelHeader.NICKNAME));
          }
          user.setCrtDttm(new Date());
          user.setLastupDttm(new Date());
          user.setFlago(String.valueOf(i));

          if (StringUtils.isNotBlank(rowValueMap.get(ExcelHeader.ID))) {
            parseExitsCount(rowValueMap.get(ExcelHeader.ID), user, userTempMap, spaceTempMap, areadySpaceIdMap);
          }

          if (userTempMap.get(userName) != null) { // 用户相同，导入其他身份
            user = userTempMap.get(userName);
          } else {// 用户与之前不同
            User tempUser = userService.getUserByName(userName, org.getId());
            if (tempUser == null) {// 用户不存在
              // 新增登陆信息
              Login login = createLogin(org, userName, false);
              // 新增用户信息（加入到待插入用户集合中）
              user.setId(login.getId());
              batchUserList.add(user);
            } else {// 用户已存在，增加身份
              fillSpace(tempUser, schoolYear, spaceTempMap, mapTempMap);
              user = tempUser;
            }
            userTempMap.put(userName, user);
          }
          addSpace(batchSpaceList, resultStr, batchMenuList, formatName, i, user, userSpace, userTempMap, spaceTempMap,
              mapTempMap, areadySpaceIdMap);
        }

      }
    }
  }

  /**
   * 
   * @see com.tmser.tr.back.yhgl.service.RegisterService#completeRoleInfo()
   */
  @Override
  public void saveUser(Integer roleId, Integer userId, User user, Login login) {
    if (userId == null) {// 新增
      User user_new = userManageService.saveUserAccount(User.SYS_USER, login, user);
      // 加入角色
      UserRole userrole = new UserRole();
      userrole.setUserId(user_new.getId());
      userrole.setRoleId(roleId);
      userRoleService.save(userrole);
      // 加入空间
      Role role = roleService.findOne(roleId);
      UserSpace us = new UserSpace();
      us.setUserId(user_new.getId());
      us.setUsername(user_new.getName());
      us.setSpaceHomeUrl(userManageService.getHomeUrl(role.getSysRoleId()));
      us.setSysRoleId(role.getSysRoleId());
      us.setRoleId(roleId);
      us.setEnable(UserSpace.ENABLE);
      userSpaceService.save(us);
      LoggerUtils.insertLogger(LoggerModule.YHGL, "系统用户管理——增加用户，用户id：" + user_new.getId());
    } else {
      Role role = roleService.findOne(roleId);
      UserRole ur = new UserRole();
      ur.setUserId(userId);
      UserRole userrole = userRoleService.findOne(ur);
      if (!userrole.getRoleId().equals(roleId)) {
        userrole.setRoleId(roleId);
        userRoleService.update(userrole);
      }

      UserSpace us = new UserSpace();
      us.setUserId(userId);
      us = userSpaceService.findOne(us);
      if (!(roleId.equals(us.getRoleId()) && role.getSysRoleId().equals(us.getSysRoleId()))) {
        us.setRoleId(roleId);
        us.setSysRoleId(role.getSysRoleId());
        userSpaceService.update(us);
      }

      user.setId(userId);
      login.setId(userId);
      userManageService.updateUserAccount(login, user);
      LoggerUtils.updateLogger(LoggerModule.YHGL, "系统用户管理——更新用户，用户id：" + userId);
    }

  }

}
