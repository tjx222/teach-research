/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.zzjg.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.tmser.tr.back.logger.LoggerModule;
import com.tmser.tr.back.logger.service.OperateLogService;
import com.tmser.tr.back.zzjg.service.ZZJGOrgSchoolManageService;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.org.bo.Area;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.bo.OrganizationRelationship;
import com.tmser.tr.manage.org.service.AreaService;
import com.tmser.tr.manage.org.service.OrganizationRelationshipService;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author wy
 * @version $Id: JXTXOrgSchoManageController.java, v 1.0 2015年9月1日 下午3:33:47 wy
 *          Exp $
 */
@Controller
@RequestMapping("/jy/back/zzjg")
public class ZZJGOrgSchoolManageController extends AbstractController {

  @Autowired
  private ZZJGOrgSchoolManageService jXTXOrgSchoolManageService;

  @Autowired
  private AreaService areaService;

  @Autowired
  private OrganizationService organizationService;

  @Autowired
  private ResourcesService resourcesService;

  @Autowired
  private OrganizationRelationshipService organizationRelationshipService;
  @Autowired
  private OperateLogService operateLogService;

  @RequestMapping("/addDept")
  public String addDept(Model model, Integer parentId) {
    Organization org = jXTXOrgSchoolManageService.finSchById(parentId);
    model.addAttribute("parentId", parentId);
    model.addAttribute("org", org);
    return viewName("shoManger/addDept");
  }

  @RequestMapping("/goOrgTree")
  public String goOrgTree(Model m) {
    return viewName("orgManager/orgTree");
  }

  /**
   * 通过名称模糊搜索
   * 
   * @param m
   * @param name
   * @return
   */
  @RequestMapping("/searCodition")
  @ResponseBody
  public Object searCodition(Model m, String name, Integer type) {
    List<Area> areaList = areaService.getAllOrgByName(name, type);
    return areaList;
  }

  @RequestMapping("/goSchTree")
  public String goSchTree(Model model, String serchUnit, Organization newOrg) {
    Organization org = (Organization) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_ORG);
    if (org != null) {
      if (newOrg.order() == null || "".equals(newOrg.order())) {
        newOrg.addOrder(" crtDttm desc");
      }
      newOrg.setParentId(org.getId());
      Map<String, Object> orgMap = jXTXOrgSchoolManageService
          .findDeptByPid(newOrg, serchUnit);
      model.addAttribute("orgList", orgMap.get("pageList"));
      // model.addAttribute("schName", orgMap.get("schName"));
      model.addAttribute("serchUnit", serchUnit);
      model.addAttribute("org", newOrg);
      return viewName("shoManger/schAndDeptInfo");
    }
    return viewName("shoManger/schoolTree");
  }

  @RequestMapping("/schInfoFind")
  public Object schInfoFind(Organization org, Model m) {
    org.pageSize(20);
    if (StringUtils.isNotEmpty(org.getName())) {
      String searchSch = org.getName();
      org.setName(SqlMapping.LIKE_PRFIX + searchSch + SqlMapping.LIKE_PRFIX);
      m.addAttribute("selAreaName", searchSch);
    }
    String areaids = org.getAreaIds();
    if (StringUtils.isNotEmpty(areaids)) {
      org.setAreaIds(
          SqlMapping.LIKE_PRFIX + "," + areaids + "," + SqlMapping.LIKE_PRFIX);
    }
    if (org.order() == null || "".equals(org.order())) {
      org.addOrder(" crtDttm desc");
    }
    PageList<Organization> schInfoList = organizationService.findByPage(org);
    org.setAreaIds(areaids);
    m.addAttribute("model", org);
    m.addAttribute("schInfoList", schInfoList);
    return viewName("shoManger/schInfoLoad");
  }

  @RequestMapping("/addSchool")
  public String addSchool(Model m, Integer areaId) {
    Map<String, Object> orgMap = jXTXOrgSchoolManageService.finAreaById(areaId);
    m.addAttribute("selAreaName", orgMap.get("selAreaName"));
    List<MetaRelationship> schTypeList = MetaUtils.getOrgTypeMetaProvider()
        .listAll();
    m.addAttribute("schTypeList", schTypeList);
    m.addAttribute("areaId", areaId);

    return viewName("shoManger/schoolAdd");
  }

  @RequestMapping("/addUnit")
  public String addUnit(Model m, Integer areaId, String selAreaName) {
    Map<String, Object> orgMap = jXTXOrgSchoolManageService.finAreaById(areaId);
    m.addAttribute("selAreaName", orgMap.get("selAreaName"));
    m.addAttribute("areaId", areaId);
    return viewName("xzjgManeger/addUnit");
  }

  /**
   * 学校批量插入
   * 
   * @param file
   * @param orgType
   * @param areaId
   * @throws IOException
   */
  @RequestMapping("/batchInsertSch")
  public void batchInsertSch(
      @RequestParam(value = "file", required = false) MultipartFile file,
      @RequestParam(value = "orgType", required = false) Integer orgType,
      @RequestParam(value = "areaId", required = false) Integer areaId,
      HttpServletResponse response) throws IOException {
    JSONObject json = new JSONObject();
    try {
      Map<String, String> map = jXTXOrgSchoolManageService
          .batchInsertSchool(file, orgType, areaId);
      json.put("flag", map.get("flag"));
      json.put("msg", map.get("msg"));
    } catch (Exception e) {
      json.put("flag", "fail");
      json.put("msg", "批量注册学校出错！");
      logger.error("批量注册学校出错！", e);
    }

    // return viewName("/shoManger/sch_batch_result");
    response.setCharacterEncoding("utf-8");
    response.setContentType("text/html; charset=utf-8");
    PrintWriter out = response.getWriter();
    out.println("<script>parent.batchschoolcallback('" + json + "')</script>");
    out.flush();
    out.close();

  }

  /**
   * 添加批量注册
   * 
   * @param m
   * @param areaId
   */
  @RequestMapping("/batchRegisterSchool")
  public String batchRegisterSchool(Model m, Integer areaId) {
    Area area = areaService.findOne(areaId);
    m.addAttribute("area", area);
    return viewName("shoManger/add_batch_school");
  }

  @RequestMapping("/delDept")
  @ResponseBody
  public Object delDept(Model model, Integer id) {
    JuiResult rs = new JuiResult();
    try {
      jXTXOrgSchoolManageService.delDept(id);
      rs.setMessage("部门删除成功！");
      rs.setStatusCode(JuiResult.SUCCESS);
    } catch (Exception e) {
      rs.setStatusCode(JuiResult.FAILED);
      rs.setMessage("部门删除失败！");
      logger.error("学校或单位部门删除失败", e);
    }
    return rs;
  }

  @RequestMapping("/delDirSch")
  @ResponseBody
  public Object delDirSch(Model model, Integer id) {
    JuiResult rs = new JuiResult();
    try {
      if (jXTXOrgSchoolManageService.delDirSchById(id)) {
        rs.setMessage("删除成功！");
        rs.setStatusCode(JuiResult.SUCCESS);
      }
    } catch (Exception e) {
      rs.setStatusCode(JuiResult.FAILED);
      rs.setMessage("删除失败！");
      logger.error("直属学校删除失败", e);
    }
    return rs;
  }

  @RequestMapping("/delSchoolInfo")
  @ResponseBody
  public Object delSchoolInfo(Model model, Integer id) {
    JuiResult rs = new JuiResult();
    try {
      Organization findOne = organizationService.findOne(id);
      if (findOne != null) {
        resourcesService.deleteResources(findOne.getLogo());
      }
      jXTXOrgSchoolManageService.delSch(id);
      LoggerUtils.deleteLogger(LoggerModule.ZZJG, "组织机构管理——删除学校，学校id：" + id);
      rs.setMessage("删除成功！");
      rs.setStatusCode(JuiResult.SUCCESS);
    } catch (Exception e) {
      rs.setStatusCode(JuiResult.FAILED);
      rs.setMessage("删除失败！");
      logger.error("学校删除失败", e);
    }
    return rs;
  }

  @RequestMapping(value = "/delUnitInfo")
  @ResponseBody
  public Object delUnitInfo(Model model, Integer id) {
    JuiResult rs = new JuiResult();
    try {
      jXTXOrgSchoolManageService.delUnit(id);
      LoggerUtils.deleteLogger(LoggerModule.ZZJG, "组织机构管理——删除单位，单位id：" + id);
      rs.setMessage("单位删除成功！");
      rs.setStatusCode(JuiResult.SUCCESS);
    } catch (Exception e) {
      rs.setStatusCode(JuiResult.FAILED);
      rs.setMessage("单位删除失败！");
      logger.error("单位删除失败", e);
    }
    return rs;
  }

  /**
   * 学校部门信息查询
   * 
   * @param pageNum
   * @param numPerPage
   * @param model
   * @param parentId
   * @return
   */
  @RequestMapping("/deptInfoFind")
  public String deptInfoFind(Model model, Organization org, String serchUnit) {
    if (org.order() == null || "".equals(org.order())) {
      org.addOrder(" crtDttm desc");
    }
    Map<String, Object> orgMap = jXTXOrgSchoolManageService.findDeptByPid(org,
        serchUnit);
    model.addAttribute("orgList", orgMap.get("pageList"));
    // model.addAttribute("schName", orgMap.get("schName"));
    model.addAttribute("parentId", org.getParentId());
    model.addAttribute("serchUnit", serchUnit);
    model.addAttribute("model", org);
    return viewName("shoManger/deptInfoLoad");
  }

  @RequestMapping("/direcInfo")
  public String direcInfo(Model model) {
    return viewName("shoManger/directlTree");
  }

  @RequestMapping("/directInfoEdit")
  public String directInfoEdit(Model model, Integer id) {
    Map<String, Object> objMap = jXTXOrgSchoolManageService.lookInfo(id);
    model.addAttribute("newOrg", objMap.get("newOrg"));
    model.addAttribute("dirArea", objMap.get("dirArea"));
    model.addAttribute("id", id);
    return viewName("shoManger/directInfoEdit");
  }

  /**
   * 直属学校信息
   * 
   * @param model
   * @param id
   * @return
   */
  @RequestMapping("/directSchInfo")
  public String directSchInfo(Model model, Integer id) {
    Map<String, Object> objMap = jXTXOrgSchoolManageService.lookInfo(id);
    model.addAttribute("newOrg", objMap.get("newOrg"));
    model.addAttribute("dirArea", objMap.get("dirArea"));
    model.addAttribute("id", id);
    return viewName("shoManger/directSchInfo");
  }

  /**
   * 直属学校机构树
   * 
   * @param model
   * @return
   */
  @RequestMapping("/directSchTree")
  @ResponseBody
  public Object directSchTree(Model model) {
    List<Area> treeList = jXTXOrgSchoolManageService.findDirectTree();
    return treeList;
  }

  @RequestMapping("/dirtSchFind")
  public String dirtSchFind(Model model, Organization orga) {
    if (orga.order() == null || "".equals(orga.order())) {
      orga.addOrder(" crtDttm desc");
    }
    PageList<Organization> dirInfoList = organizationService.findByPage(orga);
    model.addAttribute("model", orga);
    model.addAttribute("dirInfoList", dirInfoList);
    return viewName("shoManger/directInfoLoad");
  }

  @RequestMapping("/editDept")
  public String editDept(Model model, Integer id, Integer parentId) {

    Organization orgDept = jXTXOrgSchoolManageService.findInfoById(id);
    model.addAttribute("orgDept", orgDept);
    Organization orgParetDept = jXTXOrgSchoolManageService
        .findInfoById(parentId);
    model.addAttribute("orgParetDept", orgParetDept);
    model.addAttribute("schName",
        orgParetDept.getAreaName() + orgParetDept.getName());
    return viewName("shoManger/editDept");
  }

  @RequestMapping("/editSchoolInfo")
  public String editSchoolInfo(Model model, Integer id) {
    List<MetaRelationship> schTypeList = MetaUtils.getOrgTypeMetaProvider()
        .listAll();
    model.addAttribute("schTypeList", schTypeList);
    Map<String, Object> objMap = jXTXOrgSchoolManageService.lookInfo(id);
    Organization newOrg = (Organization) objMap.get("newOrg");
    model.addAttribute("xueZhilist", null);
    model.addAttribute("newOrg", newOrg);
    model.addAttribute("schShip", objMap.get("schShip"));
    model.addAttribute("xzShip", objMap.get("xzShip"));
    model.addAttribute("area", objMap.get("area"));
    model.addAttribute("id", id);

    return viewName("shoManger/schoolEdit");
  }

  /**
   * 编辑部门信息
   * 
   * @param model
   * @param schName
   * @param id
   * @return
   */
  @RequestMapping("/editUnit")
  public String editUnit(Model model, Integer id) {
    Organization orgUnit = jXTXOrgSchoolManageService.findInfoById(id);
    Map<String, Object> orgMap = jXTXOrgSchoolManageService
        .finAreaById(orgUnit.getAreaId());
    model.addAttribute("areaName", orgMap.get("areaName"));
    model.addAttribute("orgUnit", orgUnit);
    Organization org = (Organization) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_ORG);
    if (org != null) {
      model.addAttribute("selAreaName", orgMap.get("selAreaName"));
      return viewName("xzjgManeger/xzjgInfoManager");
    }
    return viewName("xzjgManeger/unitEdit");
  }

  /**
   * 行政组织结构树
   * 
   * @return
   */
  @RequestMapping("/goxzjgTree")
  public String goxzjgTree(Model model, String serchUnit, Organization newOrg) {
    Organization org = (Organization) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_ORG);
    if (org != null) {
      if (newOrg.order() == null || "".equals(newOrg.order())) {
        newOrg.addOrder(" crtDttm desc");
      }
      newOrg.setParentId(org.getId());
      Map<String, Object> orgMap = jXTXOrgSchoolManageService
          .findDeptByPid(newOrg, serchUnit);
      model.addAttribute("orgList", orgMap.get("pageList"));
      model.addAttribute("serchUnit", serchUnit);
      model.addAttribute("org", newOrg);
      return viewName("xzjgManeger/unitAndDeptInfo");
    }
    return viewName("xzjgManeger/xzjgTree");
  }

  @RequestMapping("/lockSch")
  @ResponseBody
  public Object lockSchool(Model model, Organization org) {
    JuiResult rs = new JuiResult();
    try {
      if (jXTXOrgSchoolManageService.updateLock(org)) {
        if (org.getEnable() == 0) {
          rs.setMessage("冻结成功！");
        } else {
          rs.setMessage("恭喜您，您已成功取消冻结！");
        }
        rs.setStatusCode(JuiResult.SUCCESS);
      }
    } catch (Exception e) {
      logger.error("禁用失败", e);
      rs.setStatusCode(JuiResult.FAILED);
    }

    return rs;
  }

  @RequestMapping("/lookDeptInfo")
  public String lookDeptInfo(Model model, Integer parentId, Integer id) {
    Organization orgDept = jXTXOrgSchoolManageService.findInfoById(id);
    Organization orgParetDept = jXTXOrgSchoolManageService
        .findInfoById(parentId);
    model.addAttribute("orgDept", orgDept);
    model.addAttribute("orgParetDept", orgParetDept);
    Map<String, Object> orgMap = jXTXOrgSchoolManageService
        .findDeptNameByPid(parentId);
    model.addAttribute("schAreaName", orgMap.get("schAreaName"));
    return viewName("shoManger/lookDeptInfo");
  }

  @RequestMapping("/lookdirSch")
  public String lookdirSch(Model model, Integer id) {
    Map<String, Object> infoMap = jXTXOrgSchoolManageService.finDirSchById(id);
    model.addAttribute("orgDir", infoMap.get("org"));
    model.addAttribute("areaDir", infoMap.get("area"));
    return viewName("shoManger/lookDirectSchInfo");
  }

  @RequestMapping("/lookSchoolInfo")
  public String lookSchoolInfo(Model model, Integer id) {
    Map<String, Object> objMap = jXTXOrgSchoolManageService.lookInfo(id);
    model.addAttribute("newOrg", objMap.get("newOrg"));
    model.addAttribute("schShip", objMap.get("schShip"));
    return viewName("shoManger/schoolLook");
  }

  @RequestMapping("/lookUnitInfo")
  public String lookUnitInfo(Model model, Integer id) {
    Organization newOrg = organizationService.findOne(id);
    model.addAttribute("newOrg", newOrg);
    return viewName("xzjgManeger/xzdwUnitLook");
  }

  /**
   * 行政区域树查询
   * 
   * @param m
   * @param parentId
   * @return
   */
  @ResponseBody
  @RequestMapping("/orgFindTree")
  public Object orgFindTree(Model m) {
    List<Area> areaList = jXTXOrgSchoolManageService.orgFindTree();
    return areaList;
  }

  @RequestMapping("/saveDept")
  @ResponseBody
  public Object saveDept(Model model, Organization org) {
    JuiResult rs = new JuiResult();
    try {
      if (jXTXOrgSchoolManageService.saveDeptInfo(org)) {
        rs.setStatusCode(JuiResult.SUCCESS);
        rs.setMessage("保存成功！");
      }
    } catch (Exception e) {
      logger.error("学校或单位部门保存失败", e);
      rs.setStatusCode(JuiResult.FAILED);
    }

    return rs;

  }

  @RequestMapping("/saveDirLevel")
  @ResponseBody
  public Object saveDirLevel(Model model, Organization org) {
    JuiResult rs = new JuiResult();
    try {
      if (jXTXOrgSchoolManageService.saveDirLel(org)) {
        rs.setMessage("直属学校级别保存成功！");
      }
    } catch (Exception e) {
      rs.setMessage("直属学校级别保存失败！");
      logger.error("直属学校级别保存失败", e);
    }

    return rs;
  }

  @RequestMapping("/saveSch")
  @ResponseBody
  public Object saveSch(Model m, Organization org) {
    JuiResult rs = new JuiResult();
    try {
      jXTXOrgSchoolManageService.saveSch(org);
      rs.setMessage("保存成功！");
    } catch (Exception e) {
      rs.setMessage("保存失败！");
      logger.error("学校保存失败", e);
    }

    return rs;
  }

  @RequestMapping("/saveUnit")
  @ResponseBody
  public Object saveUnit(Model m, Organization org) {
    JuiResult rs = new JuiResult();
    try {
      Organization newOrg = (Organization) WebThreadLocalUtils
          .getSessionAttrbitue(SessionKey.CURRENT_ORG);
      if (newOrg != null) {
        organizationService.update(org);
        LoggerUtils.updateLogger(LoggerModule.ZZJG,
            "组织机构管理——修改单位，单位id：" + org.getId());
      } else {
        jXTXOrgSchoolManageService.saveUnit(org);
      }
      rs.setMessage("保存成功！");
    } catch (Exception e) {
      rs.setMessage("保存失败！");
      logger.error("单位保存失败", e);
    }

    return rs;
  }

  @RequestMapping("/unitInfoFind")
  public Object unitInfoFind(Model m, Organization orga) {
    String searchUnit = orga.getName();
    if (StringUtils.isNotEmpty(searchUnit)) {
      orga.setName(SqlMapping.LIKE_PRFIX + searchUnit + SqlMapping.LIKE_PRFIX);
      m.addAttribute("searchStr", searchUnit);
    }
    if (orga.order() == null || "".equals(orga.order())) {
      orga.addOrder(" crtDttm desc");
    }
    PageList<Organization> unitInfoList = organizationService.findByPage(orga);
    m.addAttribute("model", orga);
    m.addAttribute("unitInfoList", unitInfoList);
    return viewName("xzjgManeger/xzjgInfoLoad");
  }

  @RequestMapping("/unitAddDept")
  public String unitAddDept(Model model, Integer parentId, String schName) {
    Organization org = jXTXOrgSchoolManageService.finSchById(parentId);
    model.addAttribute("parentId", parentId);
    model.addAttribute("org", org);
    return viewName("xzjgManeger/unitAddDept");
  }

  @RequestMapping("/unitDeptInfoFind")
  public String unitDeptInfoFind(Model model, Organization org,
      String serchUnit) {
    if (org.order() == null || "".equals(org.order())) {
      org.addOrder(" crtDttm desc");
    }
    Map<String, Object> orgMap = jXTXOrgSchoolManageService.findDeptByPid(org,
        serchUnit);
    model.addAttribute("orgList", orgMap.get("pageList"));
    model.addAttribute("schName", orgMap.get("schName"));
    model.addAttribute("parentId", org.getParentId());
    model.addAttribute("serchUnit", serchUnit);
    model.addAttribute("model", org);
    return viewName("xzjgManeger/unitDeptInfoLoad");
  }

  @RequestMapping("/unitEditDept")
  public String unitEditDept(Model model, Integer id, Integer parentId) {

    Organization orgDept = jXTXOrgSchoolManageService.findInfoById(id);
    model.addAttribute("orgDept", orgDept);
    Organization orgParetDept = jXTXOrgSchoolManageService
        .findInfoById(parentId);
    model.addAttribute("orgParetDept", orgParetDept);
    model.addAttribute("schName",
        orgParetDept.getAreaName() + orgParetDept.getName());
    return viewName("xzjgManeger/unitEditDept");
  }

  @RequestMapping("/valiSch")
  @ResponseBody
  public Object valiSch(Model m, Organization org, Integer sid) {
    boolean flag = false;
    Organization findOne = new Organization();
    try {
      if (null != sid) {
        findOne = organizationService.findOne(sid);
      }
      List<Organization> orga = jXTXOrgSchoolManageService.valiSchInfo(org);
      if (orga.size() == 0) {
        flag = true;
      }
      if (orga.contains(findOne)) {
        orga.remove(findOne);
        flag = orga.size() == 0;
      }
      return flag;
    } catch (Exception e) {
      logger.error("学校重复效验失败", e);
    }

    return flag;
  }

  @RequestMapping("/schInfoManager")
  public String schInfoManager(Model model, Integer orgId) {
    Organization org;
    if (orgId != null) {
      org = organizationService.findOne(orgId);
    } else {
      org = (Organization) WebThreadLocalUtils
          .getSessionAttrbitue(SessionKey.CURRENT_ORG);
    }
    if (org != null) {
      org = organizationService.findOne(org.getId());
      MetaRelationship xxTypeMeta = MetaUtils.getOrgTypeMetaProvider()
          .getMetaRelationship(org.getSchoolings());
      model.addAttribute("xxTypeMeta", xxTypeMeta);
      Map<String, Object> objMap = jXTXOrgSchoolManageService
          .lookInfo(org.getId());
      model.addAttribute("newOrg", objMap.get("newOrg"));
      model.addAttribute("area", objMap.get("area"));
      model.addAttribute("id", org.getId());
    }

    return viewName("shoManger/schoolInfoManager");
  }

  @RequestMapping("/saveSchool")
  @ResponseBody
  public Object saveSchool(Model m, Organization org, String oldLogo) {
    JuiResult rs = new JuiResult();
    try {
      if (org != null && StringUtils.isNotEmpty(org.getLogo())) {
        resourcesService.updateTmptResources(org.getLogo());
      }
      if (StringUtils.isNotEmpty(oldLogo)) {// 说明图片改变了，则逻辑删除原来的资源文件
        resourcesService.deleteResources(oldLogo);
      }
      organizationService.update(org);
      LoggerUtils.updateLogger(LoggerModule.ZZJG, "组织机构管理——修改学校，学校id：{}",
          org.getId());
      rs.setMessage("保存成功！");
    } catch (Exception e) {
      rs.setMessage("保存失败！");
      logger.error("学校保存失败", e);
    }

    return rs;
  }

  @ResponseBody
  @RequestMapping("/getPostCodeById")
  public Object getPostCodeById(Model model, Integer id) {
    Area area = areaService.findOne(id);
    return area;
  }

  @RequestMapping("/schooltype/maintenance")
  @ResponseBody
  public JuiResult maintenance() {
    JuiResult re = new JuiResult();
    re.setMessage("数据维护成功");
    Organization model = new Organization();
    model.setType(Organization.SCHOOL);
    List<Organization> orgs = organizationService.findAll(model);
    // 需要添加到org关系表的数据
    List<OrganizationRelationship> orList = new ArrayList<>();
    for (Organization org : orgs) {
      if (org.getSchoolings() == null) {
        continue;
      }
      Map<Integer, List<Meta>> phaseGradeMap = MetaUtils
          .getOrgTypeMetaProvider().listPhaseGradeMap(org.getSchoolings());
      for (Integer phase : phaseGradeMap.keySet()) {
        OrganizationRelationship or = new OrganizationRelationship();
        or.setPhaseId(phase);
        or.setSchooling(phaseGradeMap.get(phase).size());
        or.setOrgId(org.getId());
        orList.add(or);
      }
    }
    organizationRelationshipService.batchInsert(orList);
    return re;
  }

}
