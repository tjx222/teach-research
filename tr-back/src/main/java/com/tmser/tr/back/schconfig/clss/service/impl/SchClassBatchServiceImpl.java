/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.schconfig.clss.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.tmser.tr.back.schconfig.clss.service.SchClassBatchService;
import com.tmser.tr.back.schconfig.clss.service.SchClassService;
import com.tmser.tr.back.schconfig.clss.service.SchClassUserService;
import com.tmser.tr.common.service.BatchService;
import com.tmser.tr.common.service.ExcelBatchService;
import com.tmser.tr.common.utils.ExcelCascadeRecord;
import com.tmser.tr.common.utils.ExcelUtils;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaProvider.PhaseGradeMetaProvider;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.org.bo.Area;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.AreaService;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.schconfig.clss.bo.SchClass;
import com.tmser.tr.schconfig.clss.bo.SchClassUser;
import com.tmser.tr.schconfig.clss.vo.SchClassUserVo;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.SchoolYearService;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 * 班级教师批量操作
 * </pre>
 * 
 * @version $Id: SchClassBatchServiceImpl.java
 *          Exp $
 */
@Service
@Transactional
public class SchClassBatchServiceImpl extends ExcelBatchService implements SchClassBatchService {
  private static final Logger logger = LoggerFactory.getLogger(SchClassBatchServiceImpl.class);
  @Autowired
  private OrganizationService orgService;
  @Autowired
  private AreaService areaService;
  @Autowired
  private UserSpaceService userSpaceService;
  @Autowired
  private SchoolYearService schoolYearService;
  @Autowired
  private SchClassService schClassService;
  @Autowired
  private SchClassUserService schClassUserService;
  /**
   * 用户模板头部所在行
   */
  private Integer headline = 3;

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
  @SuppressWarnings("unused")
  @Override
  public void getRegisterTemplateFileStream(String templateType, Integer phaseId, Integer orgId,
      HttpServletResponse response) {
    Workbook workbook = null;
    Organization org = orgService.findOne(orgId);
    String fileName = "批量设置任课教师";
    String filepath = "";
    filepath = "/registertemplate/registertemplate_bjls.xls";
    workbook = ExcelUtils.createWorkBook(WebThreadLocalUtils.getRequest().getSession().getServletContext()
        .getRealPath(filepath));
    if (workbook == null) {
      returnErrMsg(filepath, response);
      return;
    }
    Sheet sheet = workbook.getSheet("Sheet");
    String orgStr = "学校名称：" + org.getName();
    Area area1 = areaService.findOne(org.getAreaId());
    if (area1.getParentId().intValue() != 0) {
      Area area2 = areaService.findOne(area1.getParentId());
      orgStr = "省(市)：" + area2.getName() + "     区(县)：" + area1.getName() + "     " + orgStr;
    } else {
      orgStr = "区(县)：" + area1.getName() + "     " + orgStr;
    }
    sheet.getRow(2).getCell(0).setCellValue(orgStr);

    Organization organization = orgService.findOne(orgId);
    String areaIds = organization.getAreaIds();
    Integer[] areaIdArr = com.tmser.tr.utils.StringUtils.toIntegerArray(areaIds.substring(1, areaIds.length() - 1),
        com.tmser.tr.utils.StringUtils.COMMA);
    List<Meta> listAllSubjectByPhaseId = MetaUtils.getPhaseSubjectMetaProvider().listAllSubject(orgId, phaseId,
        areaIdArr);
    int startColumn = 3;// 从第三列开始动态加载学科
    for (Meta meta : listAllSubjectByPhaseId) {
      sheet.getRow(headline).createCell(startColumn);
      sheet.getRow(headline).getCell(startColumn).setCellStyle(sheet.getRow(headline).getCell(2).getCellStyle());// 给新添列添加样式
      sheet.getRow(headline).getCell(startColumn).setCellValue(meta.getName());
      sheet.autoSizeColumn(startColumn);// 自动调整列宽
      startColumn++;
    }
    this.loadSheetStyle(sheet, startColumn);// 设置单元格格式
    Map<ExcelTitle, Column> headers = parseExcelHeader(sheet, headline);
    initSheetTeplate(workbook, sheet, org, phaseId, headers);
    try {
      if (workbook != null) {
        String ext = filepath.substring(filepath.lastIndexOf("."));
        response.reset();
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition", "attachment; filename="
            + new String(fileName.getBytes("gb2312"), "ISO-8859-1") + ext);
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

  private void loadSheetStyle(Sheet sheet, int startColumn) {
    CellRangeAddress cra0 = new CellRangeAddress(0, 0, 0, startColumn - 1);
    CellRangeAddress cra1 = new CellRangeAddress(1, 1, 0, startColumn - 1);
    CellRangeAddress cra2 = new CellRangeAddress(2, 2, 0, startColumn - 1);
    // 根据列数合并前三行单元格
    sheet.addMergedRegion(cra0);
    sheet.addMergedRegion(cra1);
    sheet.addMergedRegion(cra2);
    CellStyle cellStyle = sheet.getRow(0).getCell(0).getCellStyle();
    cellStyle.setAlignment(CellStyle.ALIGN_LEFT); // 居左
    CellStyle cellStyle1 = sheet.getRow(1).getCell(0).getCellStyle();
    cellStyle1.setAlignment(CellStyle.ALIGN_CENTER); // 居中
    CellStyle cellStyle2 = sheet.getRow(2).getCell(0).getCellStyle();
    cellStyle2.setAlignment(CellStyle.ALIGN_CENTER); // 居中
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
  public StringBuilder batchImportTeacher(Integer orgId, Integer phaseId, MultipartFile file) {
    Map<String, Object> params = new HashMap<String, Object>();
    StringBuilder resultStr = new StringBuilder(); // 反馈信息
    params.put("orgId", orgId);
    params.put("phaseId", phaseId);
    params.put("schoolYear", schoolYearService.getCurrentSchoolYear());
    try {
      this.importData(file.getInputStream(), params, resultStr);
    } catch (Exception e) {
      logger.error("read excel file failed", e);
    }

    return resultStr;
  }

  static final class ExcelHeader implements ExcelTitle {
    private static final List<ExcelTitle> titles = new ArrayList<BatchService.ExcelTitle>();
    private final boolean required;
    private final int size;
    private final String name;

    public static final String GRADE = "年级";
    public static final String CLASS = "班级";
    public static final String ADVISER = "班主任";
    static {
      titles.add(new ExcelHeader(GRADE, false));
      titles.add(new ExcelHeader(CLASS, true));
      titles.add(new ExcelHeader(ADVISER, false));
      for (Meta grade : MetaUtils.getPhaseSubjectMetaProvider().listAllSubjectMeta()) {
        titles.add(new ExcelHeader(grade.getName(), false));
      }
    }

    private ExcelHeader(final String name, final boolean required, final int size) {
      this.required = required;
      this.size = size;
      this.name = name;
    }

    private ExcelHeader(final String name, final boolean required) {
      this.required = required;
      this.size = Integer.MAX_VALUE;
      this.name = name;
    }

    public static final ExcelTitle map(String name) {
      for (ExcelTitle title : titles)
        if (((ExcelHeader) title).getName().equals(name))
          return title;
      return null;
    }

    public static final ExcelTitle[] values() {
      return titles.toArray(new ExcelTitle[titles.size()]);
    }

    @Override
    public List<String> getMapNames() {
      return Arrays.asList(getName());
    }

    @Override
    public boolean isRequired() {
      return required;
    }

    @Override
    public int size() {
      return this.size;
    }

    public int getSize() {
      return size;
    }

    public String getName() {
      return name;
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
   * 初始化学校模板
   * 
   * @param workbook
   * 
   * @param sheet
   * @param org
   * @param phaseId
   */
  private void initSheetTeplate(Workbook workbook, Sheet sheet, Organization org, Integer phaseId,
      Map<ExcelTitle, Column> headers) {
    Column cn = null;
    List<Meta> gradeMetas = MetaUtils.getOrgTypeMetaProvider().listAllGrade(org.getSchoolings(), phaseId);
    Map<Integer, String> gradeMap = new HashMap<Integer, String>();// 年级id name
                                                                   // 映射
    Map<String, Object> nameMap = new LinkedHashMap<String, Object>();// 年级
                                                                      // 下所有班级映射
    for (Meta meta : gradeMetas) {
      gradeMap.put(meta.getId(), meta.getName());
      nameMap.put(meta.getName(), new ArrayList<String>());
    }
    Integer gradeClumnIndex = null;
    if ((cn = headers.get(ExcelHeader.map(ExcelHeader.GRADE))) != null) {
      gradeClumnIndex = cn.getIndex();
      String gradenames[] = new String[gradeMetas.size() + 1];
      int i = 0;
      for (Meta meta : gradeMetas) {
        gradenames[i++] = meta.getName();
      }
      gradenames[i] = "无";
    }
    if ((cn = headers.get(ExcelHeader.map(ExcelHeader.CLASS))) != null) {
      int clumnIndex = cn.getIndex();
      SchClass classModel = new SchClass();
      classModel.setOrgId(org.getId());
      classModel.setEnable(1);
      classModel.addOrder("sort desc");
      List<SchClass> classList = schClassService.findAll(classModel);
      String classnames[] = new String[classList.size() + 1];
      int i = 0;
      for (SchClass sch : classList) {
        if (gradeMap.get(sch.getGradeId()) != null) {
          @SuppressWarnings("unchecked")
          List<String> clss = (List<String>) nameMap.get(gradeMap.get(sch.getGradeId()));
          if (clss != null) {
            clss.add(sch.getName());
          } else {
            List<String> names = new ArrayList<String>();
            names.add(sch.getName());
            nameMap.put(gradeMap.get(sch.getGradeId()), names);
          }
        }
        classnames[i++] = sch.getName();
      }
      classnames[i] = "无";
      if (gradeClumnIndex != null) {
        ExcelCascadeRecord createCascadeRecord = ExcelUtils.createCascadeRecord(workbook, nameMap);// 创建数据字典第一行
        DataValidation setDataValidationName = ExcelUtils.createDataValidation(sheet, createCascadeRecord.getName(),
            headline + 1, 500, gradeClumnIndex, gradeClumnIndex);
        sheet.addValidationData(setDataValidationName);
        for (int start = headline + 1; start < 500; start++) {
          int relationRow = start + 1;
          DataValidation setDataValidationByName = ExcelUtils.createDataValidation(sheet,
              "INDIRECT($" + ExcelUtils.indexToColumn(gradeClumnIndex + 1) + "$" + relationRow + ")", start, start,
              clumnIndex, clumnIndex);
          sheet.addValidationData(setDataValidationByName);
        }

      }
    }
  }

  /**
   * @return
   * @see com.tmser.tr.common.service.ExcelBatchService#sheetIndex()
   */
  @Override
  public int sheetIndex() {
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
   * @see com.tmser.tr.common.service.ExcelBatchService#beforeSheetParse(org.apache.poi.hssf.usermodel.Sheet,
   *      java.util.Map, java.lang.StringBuilder)
   */
  @Override
  protected void beforeSheetParse(Sheet sheet, Map<String, Object> params, StringBuilder returnMsg) {
    if (params != null) {
      // 年级班级匹配
      PhaseGradeMetaProvider phaseGradeMetaProvider = MetaUtils.getPhaseGradeMetaProvider();
      String descs = phaseGradeMetaProvider.getMetaRelationshipByPhaseId((Integer) params.get("phaseId")).getIds();
      Map<Integer, Map<Integer, String>> gradeClassMaps = new HashMap<Integer, Map<Integer, String>>();
      for (String gradeId : descs.split(",")) {
        SchClass model = new SchClass();
        model.setGradeId(Integer.valueOf(gradeId));
        model.setOrgId((Integer) params.get("orgId"));
        model.setEnable(SchClass.ENABLE);
        List<SchClass> schList = schClassService.findAll(model);
        Map<Integer, String> map = new HashMap<Integer, String>();
        if (!CollectionUtils.isEmpty(schList)) {
          for (SchClass schClass : schList) {
            map.put(schClass.getId(), schClass.getName());
          }
        }
        gradeClassMaps.put(Integer.valueOf(gradeId), map);
      }
      params.put("gradeClass", gradeClassMaps);

      List<Meta> subjectMetaList = MetaUtils.getPhaseSubjectMetaProvider().listAllSubjectByPhaseId(
          (Integer) params.get("phaseId"));
      Map<String, Integer> subjectMetaMap = new HashMap<String, Integer>();
      for (Meta meta : subjectMetaList) {
        subjectMetaMap.put(meta.getName(), meta.getId());
      }
      params.put("subjectMetaList", subjectMetaList);
      params.put("subjectMetaMap", subjectMetaMap);
      List<Meta> gradeMetaList = MetaUtils.getPhaseGradeMetaProvider().listAllGradeByPhaseId(
          (Integer) params.get("phaseId"));
      Map<String, Integer> gradeMetaMap = new HashMap<String, Integer>();
      for (Meta meta : gradeMetaList) {
        gradeMetaMap.put(meta.getName(), meta.getId());
      }
      params.put("gradeMetaList", gradeMetaList);
      params.put("gradeMetaMap", gradeMetaMap);
    }

  }

  /**
   * @param rowValueMap
   * @param params
   * @param row
   * @param returnMsg
   * @see com.tmser.tr.common.service.ExcelBatchService#parseRow(java.util.Map,
   *      java.util.Map, org.apache.poi.hssf.usermodel.HSSFRow,
   *      java.lang.StringBuilder)
   */
  @SuppressWarnings("unchecked")
  @Override
  protected void parseRow(Map<ExcelTitle, String> rowValueMap, Map<String, Object> params, Row row, StringBuilder sb) {
    if (row != null) {
      int i = row.getRowNum();

      List<Meta> subjectMetaList = (List<Meta>) params.get("subjectMetaList");
      Map<String, Integer> subjectMetaMap = (Map<String, Integer>) params.get("subjectMetaMap");
      Map<String, Integer> gradeMetaMap = (Map<String, Integer>) params.get("gradeMetaMap");
      // 数据校验
      if (!checkCulumn(i, rowValueMap, sb, params, subjectMetaMap, gradeMetaMap)) {
        return;
      }
      // 数据保存
      SchClassUserVo scVo = new SchClassUserVo();
      Integer classId = getClassId(rowValueMap, params);
      // 班主任数据
      String masterName = rowValueMap.get(ExcelHeader.map(ExcelHeader.ADVISER));
      SchClassUser master = new SchClassUser();
      master.setClassId(classId);
      master.setTchId(getTchId(masterName, params));
      master.setType(SchClassUser.T_MASTER);// 班主任
      master.setUsername(masterName);
      scVo.setMaster(master);
      scVo.setClsid(classId);
      // 其他教师数据
      List<SchClassUser> teacherList = new ArrayList<SchClassUser>();
      for (Meta meta : subjectMetaList) {
        String teacherName = rowValueMap.get(ExcelHeader.map(meta.getName()));
        Integer subjectId = subjectMetaMap.get(meta.getName());
        SchClassUser teacher = new SchClassUser();
        teacher.setClassId(classId);
        teacher.setTchId(getTchId(teacherName, params));
        teacher.setType(SchClassUser.T_TEACHER);
        teacher.setUsername(teacherName);
        teacher.setSubjectId(subjectId);
        teacherList.add(teacher);
      }
      scVo.setUserList(teacherList);
      schClassUserService.editSchClassUser(scVo);
    }
  }

  @SuppressWarnings("unchecked")
  private Integer getClassId(Map<ExcelTitle, String> rowValueMap, Map<String, Object> params) {
    String gradeName = rowValueMap.get(ExcelHeader.map(ExcelHeader.GRADE));
    String className = rowValueMap.get(ExcelHeader.map(ExcelHeader.CLASS));
    Map<String, Map<Integer, String>> gradeClassMap = (Map<String, Map<Integer, String>>) params.get("gradeClass");
    Map<String, Integer> greNames = (Map<String, Integer>) params.get("gradeMetaMap");
    Map<Integer, String> classMap = gradeClassMap.get(greNames.get(gradeName));// 当前年级对应的所有班级
    for (Integer classId : classMap.keySet()) {
      if (classMap.get(classId).equals(className)) {
        return classId;
      }
    }
    return -1;
  }

  private Integer getTchId(String userName, Map<String, Object> params) {
    Map<Integer, String> lookUpUser = new HashMap<Integer, String>();
    lookUpUser = lookUpUser((Integer) params.get("orgId"));
    for (Integer key : lookUpUser.keySet()) {
      if (lookUpUser.get(key).equals(userName)) {
        return key;
      }
    }
    return null;
  }

  private boolean checkCulumn(int i, Map<ExcelTitle, String> rowValueMap, StringBuilder sb, Map<String, Object> params,
      Map<String, Integer> subjectMetaMap, Map<String, Integer> gradeMetaMap) {
    // 校验空数据
    String gradeName = rowValueMap.get(ExcelHeader.map(ExcelHeader.GRADE));
    String className = rowValueMap.get(ExcelHeader.map(ExcelHeader.CLASS));
    if (StringUtils.isEmpty(gradeName) || StringUtils.isEmpty(className)) {
      sb.append("第" + (i) + "行必填项有空值，忽略此行数据。<br>");
      logger.debug("第{}行必填项有空值，忽略此行数据。", i);
      return false;
    }
    // 校验年级班级是否对应
    @SuppressWarnings("unchecked")
    Map<String, Map<Integer, String>> gradeClassMap = (Map<String, Map<Integer, String>>) params.get("gradeClass");
    Map<Integer, String> classMap = gradeClassMap.get(gradeMetaMap.get(gradeName));
    if (classMap != null && !classMap.containsValue(className)) {
      sb.append("第" + (i) + "行年级不对应班级，忽略此行数据。<br>");
      logger.debug("第" + (i) + "行年级不对应班级，忽略此行数据。", i);
      return false;
    }
    Map<Integer, String> lookUpUser = lookUpUser((Integer) params.get("orgId"));
    // 校验班主任是否存在
    String masterName = rowValueMap.get(ExcelHeader.map(ExcelHeader.ADVISER));
    if (StringUtils.isNotEmpty(masterName) && !lookUpUser.containsValue(masterName)) {
      sb.append("第" + (i) + "行班主任：" + masterName + "不存在，忽略此行数据。<br>");
      logger.debug("第" + (i) + "行行班主任：" + masterName + "不存在，忽略此行数据。", i);
      return false;
    }
    // 校验教师是否存在
    for (String subjectName : subjectMetaMap.keySet()) {
      String teacherName = rowValueMap.get(ExcelHeader.map(subjectName));
      if (StringUtils.isNotEmpty(teacherName) && !lookUpUser.containsValue(teacherName)) {
        sb.append("第" + (i) + "行教师:" + teacherName + "不存在，忽略此行数据。<br>");
        logger.debug("第" + (i) + "行教师:" + teacherName + "不存在，忽略此行数据", i);
        return false;
      }
    }
    return true;
  }

  private Map<Integer, String> lookUpUser(Integer orgId) {
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
    UserSpace model = new UserSpace();
    model.setOrgId(orgId);
    model.addCustomCulomn("userId,username");
    model.addGroup("userId");
    model.setSysRoleId(SysRole.TEACHER.getId());
    model.setEnable(UserSpace.ENABLE);
    model.setSchoolYear(schoolYear);
    List<UserSpace> uslist = userSpaceService.findAll(model);
    Map<Integer, String> u = new HashMap<Integer, String>();
    for (UserSpace us : uslist) {
      u.put(us.getUserId(), us.getUsername());
    }
    return u;
  }
}
