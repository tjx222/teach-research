/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.zzjg.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tmser.tr.back.logger.LoggerModule;
import com.tmser.tr.back.zzjg.service.ZZJGOrgSchoolManageService;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.ExcelBatchService;
import com.tmser.tr.common.utils.FrontCacheUtils;
import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.org.bo.Area;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.bo.OrganizationRelationship;
import com.tmser.tr.manage.org.dao.OrganizationDao;
import com.tmser.tr.manage.org.service.AreaService;
import com.tmser.tr.manage.org.service.OrganizationRelationshipService;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserManagescope;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.dao.UserManagescopeDao;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;

/**
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author wy
 * @version $Id: JXTXOrgSchoolManageServiceImpl.java, v 1.0 2015年9月1日 下午3:38:56
 *          wy Exp $
 */
@Service
@Transactional
public class ZZJGOrgSchoolManageServiceImpl extends ExcelBatchService
    implements ZZJGOrgSchoolManageService {

  private final static Logger logger = LoggerFactory
      .getLogger(ZZJGOrgSchoolManageServiceImpl.class);

  @Autowired
  private AreaService areaService;

  @Autowired
  private OrganizationDao organizationDao;

  @Autowired
  private ResourcesService resourcesService;

  @Resource(name = "cacheManger")
  private CacheManager cacheManager;

  @Autowired
  private UserManagescopeDao userManagescopeDao;

  @Autowired
  private OrganizationRelationshipService organizationRelationshipService;

  @Override
  public void saveSch(Organization org) {
    if (org != null && org.getAreaId() != null) {
      Integer areaId = org.getAreaId();
      Area area = areaService.findOne(areaId);
      Map<String, String> areaParams = getAreaIds(area);
      Map<Integer, List<Meta>> phaseGradeMap = MetaUtils
          .getOrgTypeMetaProvider().listPhaseGradeMap(org.getSchoolings());// .parsePhaseGrade(newMetaShip.getIds());
      Set<Integer> keySet = phaseGradeMap.keySet();
      StringBuilder sb = new StringBuilder(",");
      List<OrganizationRelationship> orList = new ArrayList<OrganizationRelationship>();
      for (Integer phase : keySet) {
        sb.append(phase + ",");
        OrganizationRelationship or = new OrganizationRelationship();
        or.setPhaseId(phase);
        or.setSchooling(phaseGradeMap.get(phase).size());
        if (org.getId() != null) {
          or.setOrgId(org.getId());
        }
        orList.add(or);
      }
      org.setPhaseTypes(sb.toString());// 学段类型
      org.setTreeLevel(0);// 添加学校代表的级别
      org.setAreaIds("," + areaParams.get("areaIds") + ",");// 区域层级ids
      org.setType(0);
      org.setSort(0);
      org.setEnable(Organization.ENABLE);
      org.setAreaName(areaParams.get("areaNames"));
      User user = CurrentUserContext.getCurrentUser();
      Date now = new Date();
      if (org.getId() != null) {
        Organization oldOrg = organizationDao.get(org.getId());
        if (oldOrg != null && oldOrg.getLogo() != null
            && !oldOrg.getLogo().equals(org.getLogo())) {// 说明图片改变了，则逻辑删除原来的资源文件
          resourcesService.deleteResources(oldOrg.getLogo());
        }

        if (StringUtils.isNotEmpty(org.getLogo())
            && !org.getLogo().equals(oldOrg.getLogo())) {
          resourcesService.updateTmptResources(org.getLogo());
        }

        org.setLastupId(user == null ? 0 : user.getId());
        org.setLastupDttm(now);
        int count = organizationDao.update(org);
        logger.debug("finished update org, rs is {}", count);
        if (oldOrg != null
            && !oldOrg.getSchoolings().equals(org.getSchoolings())) {
          organizationRelationshipService.deleteByOrgId(oldOrg.getId());
          organizationRelationshipService.batchInsert(orList);
        }

        // 刷新前台缓存
        FrontCacheUtils.delete(Organization.class, org.getId());
        LoggerUtils.updateLogger(LoggerModule.ZZJG,
            "组织机构管理——修改学校，学校id：" + org.getId());
        if (count < 0) {
          logger.info("更新学校数据失败");
        }
      } else {
        org.setCrtId(user == null ? 0 : user.getId());
        org.setCrtDttm(now);
        Organization saveOrg = organizationDao.insert(org);
        for (OrganizationRelationship organizationRelationship : orList) {
          organizationRelationship.setOrgId(saveOrg.getId());
          organizationRelationshipService.save(organizationRelationship);
        }
        if (org != null && StringUtils.isNotEmpty(org.getLogo())) {
          resourcesService.updateTmptResources(org.getLogo());
        }
        FrontCacheUtils.delete(Organization.class, saveOrg.getId());
        LoggerUtils.insertLogger(LoggerModule.ZZJG,
            "组织机构管理——增加学校，学校id：" + saveOrg.getId());
      }
    }
  }

  /**
   * 拼接区域层级ids
   * 
   * @param area
   * @return
   */
  private Map<String, String> getAreaIds(Area area) {
    Map<String, String> nameMap = new HashMap<String, String>();
    List<Integer> areaList = new ArrayList<Integer>();
    List<String> areaNameList = new ArrayList<String>();
    while (area.getId() != 0) {
      areaList.add(area.getId());
      areaNameList.add(area.getName());
      if (area.getParentId() == 0) {
        break;
      }
      area = areaService.findOne(area.getParentId());
    }
    Collections.reverse(areaList);
    Collections.reverse(areaNameList);
    String areaIds = StringUtils.join(areaList, ",");
    String areaNames = StringUtils.join(areaNameList, "");
    nameMap.put("areaIds", areaIds);
    nameMap.put("areaNames", areaNames);
    return nameMap;
  }

  /**
   * 查找学校和单位信息
   * 
   * @param id
   * @return
   * @see com.tmser.tr.back.zzjg.service.ZZJGOrgSchoolManageService#lookSchInfo(java.lang.Integer)
   */
  @Override
  public Map<String, Object> lookInfo(Integer id) {
    Organization newOrg = new Organization();
    MetaRelationship schShip = new MetaRelationship();
    Map<String, Object> objMap = new HashMap<String, Object>();
    if (id != null) {
      newOrg = organizationDao.get(id);
    }
    Integer dirAreaId = newOrg.getDirectAreaId();
    if (dirAreaId != null) {
      Area dirArea = areaService.findOne(dirAreaId);
      objMap.put("dirArea", dirArea);
    }
    Integer schId = newOrg.getSchoolings();
    if (schId != null) {
      schShip = MetaUtils.getMetaRelation(schId);
    }
    Integer areaId = newOrg.getAreaId();
    if (areaId != null) {
      Area area = areaService.findOne(areaId);
      objMap.put("area", area);
    }

    objMap.put("newOrg", newOrg);
    objMap.put("schShip", schShip);
    return objMap;
  }

  /**
   * 删除学校信息
   * 
   * @param id
   * @see com.tmser.tr.back.zzjg.service.ZZJGOrgSchoolManageService#delSch(java.lang.Integer)
   */
  @Override
  public void delSch(Integer id) {
    if (id != null) {
      organizationDao.delete(id);
    }
  }

  /**
   * 拼接直属学校树
   * 
   * @return
   * @see com.tmser.tr.back.zzjg.service.ZZJGOrgSchoolManageService#findDirectTree()
   */
  @Override
  public List<Area> findDirectTree() {
    List<Area> areaList = new ArrayList<Area>();
    Area area = new Area();
    area.addCustomCulomn("id,name,parentId");
    area.setLevel(1);
    areaList = areaService.findAll(area);
    area.setLevel(2);
    areaList.addAll(areaService.findAll(area));
    return areaList;
  }

  /**
   * 保存直属学校
   * 
   * @param org
   * @return
   * @see com.tmser.tr.back.zzjg.service.ZZJGOrgSchoolManageService#saveDirLel(com.tmser.tr.manage.org.bo.Organization)
   */
  @Override
  public boolean saveDirLel(Organization org) {
    boolean bl = false;
    if (org != null) {
      bl = organizationDao.update(org) > 0;
      FrontCacheUtils.delete(Organization.class, org.getId());
      LoggerUtils.updateLogger(LoggerModule.ZZJG,
          "组织机构管理——直属学校设置，直属学校id：" + org.getId());
    }
    return bl;
  }

  /**
   * 更新学校锁定状态
   * 
   * @param org
   * @return
   * @see com.tmser.tr.back.zzjg.service.ZZJGOrgSchoolManageService#updateLock(com.tmser.tr.manage.org.bo.Organization)
   */
  @Override
  public boolean updateLock(Organization org) {
    boolean bl = false;
    if (org != null) {
      bl = organizationDao.update(org) > 0;
      LoggerUtils.updateLogger(LoggerModule.ZZJG,
          "组织机构管理，学校冻结状态id：" + org.getId());
    }
    return bl;
  }

  @Override
  public Map<String, Object> finDirSchById(Integer id) {
    Map<String, Object> infoMap = new HashMap<String, Object>();
    Organization org = new Organization();
    if (id != null) {
      org = organizationDao.get(id);
      Integer directAreaId = org.getDirectAreaId();
      Area area = areaService.findOne(directAreaId);
      infoMap.put("area", area);
    }
    infoMap.put("org", org);
    return infoMap;
  }

  @Override
  public boolean delDirSchById(Integer id) {
    boolean br = false;
    Organization org = new Organization();
    if (id != null) {
      org = organizationDao.get(id);
      org.setDirectAreaId(-1);
      org.setDirLevelId(-1);
      br = organizationDao.update(org) > 0;
      LoggerUtils.updateLogger(LoggerModule.ZZJG,
          "组织机构管理——直属学校删除，直属学校id：" + id);
    }
    return br;
  }

  @Override
  public Map<String, Object> findDeptByPid(Organization org, String serchUnit) {
    Map<String, Object> deptMap = new HashMap<String, Object>();
    if (org != null) {
      deptMap = findDeptNameByPid(org.getParentId());
      org.setType(2);
      if (!StringUtils.isBlank(serchUnit)) {
        org.setName(SqlMapping.LIKE_PRFIX + serchUnit + SqlMapping.LIKE_PRFIX);
      }
      List<Organization> pageList = organizationDao.listAll(org);
      deptMap.put("pageList", pageList);
    }
    return deptMap;
  }

  /**
   * 通过学校-单位id查找部门全名称
   * 
   * @param parentId
   * @return
   */
  @Override
  public Map<String, Object> findDeptNameByPid(Integer parentId) {
    Map<String, Object> deptMap = new HashMap<String, Object>();
    if (parentId != null) {
      Organization schOrg = organizationDao.get(parentId);
      String areaName = schOrg.getAreaName();
      String schName = schOrg.getName();
      deptMap.put("schAreaName", areaName + schName);
      deptMap.put("schName", schName);
    }
    return deptMap;
  }

  @Override
  public Organization findInfoById(Integer id) {
    Organization org = new Organization();
    if (id != null) {
      org = organizationDao.get(id);
    }
    return org;
  }

  @Override
  public boolean saveDeptInfo(Organization org) {
    boolean br = false;
    if (org.getId() != null) {
      br = organizationDao.update(org) > 0;
      FrontCacheUtils.delete(Organization.class, org.getId());
      LoggerUtils.updateLogger(LoggerModule.ZZJG,
          "组织机构管理——修改部门，部门id：" + org.getId());
    } else {
      org.setType(2);
      org.setSort(0);
      org.setEnable(Organization.ENABLE);
      org.setCrtId(CurrentUserContext.getCurrentUser().getId());
      org.setCrtDttm(new Date());
      org.setTreeLevel(1);
      Organization saveOrg = organizationDao.insert(org);
      LoggerUtils.insertLogger(LoggerModule.ZZJG,
          "组织机构管理——增加部门，部门id：" + saveOrg.getId());
    }
    return br;
  }

  /**
   * 查询相关机构信息
   * 
   * @param parentId
   * @return
   * @see com.tmser.tr.back.zzjg.service.ZZJGOrgSchoolManageService#finSchById(java.lang.Integer)
   */
  @Override
  public Organization finSchById(Integer parentId) {
    Organization org = organizationDao.get(parentId);
    return org;
  }

  /**
   * 删除学校部门信息
   * 
   * @param id
   * @see com.tmser.tr.back.zzjg.service.ZZJGOrgSchoolManageService#delDept(java.lang.Integer)
   */
  @Override
  public void delDept(Integer id) {
    if (id != null) {
      organizationDao.delete(id);
      LoggerUtils.deleteLogger(LoggerModule.ZZJG, "组织机构管理——删除部门，学校id：" + id);
    }

  }

  @Override
  public Map<String, Object> finAreaById(Integer areaId) {
    String pareName = "";
    Map<String, Object> areaMap = new HashMap<String, Object>();
    Area area = new Area();
    if (areaId != null) {
      area = areaService.findOne(areaId);
      String areaName = area.getName();
      pareName = getAreaIds(area).get("areaNames");
      areaMap.put("selAreaName", pareName);
      areaMap.put("areaName", areaName);
    }
    return areaMap;
  }

  @Override
  public PageList<Organization> findUnitInfo(Integer areaId, Page page,
      String searchStr) {
    Organization orga = new Organization();
    orga.setAreaId(areaId);
    orga.setType(1);
    orga.addPage(page);
    orga.addOrder(" crtDttm desc");
    if (!StringUtils.isBlank(searchStr)) {
      orga.setName(SqlMapping.LIKE_PRFIX + searchStr + SqlMapping.LIKE_PRFIX);
    }
    PageList<Organization> pageList = organizationDao.listPage(orga);
    return pageList;
  }

  @Override
  public void saveUnit(Organization org) {
    if (org != null && org.getAreaId() != null) {
      Integer areaId = org.getAreaId();
      Area area = areaService.findOne(areaId);
      String areaIds = getAreaIds(area).get("areaIds");
      org.setAreaIds("," + areaIds + ",");
      org.setType(1);
      org.setSort(0);
      org.setEnable(Organization.ENABLE);
      org.setTreeLevel(0);
      org.setCrtId(CurrentUserContext.getCurrentUserId());
      if (org.getId() != null) {
        String pareName = getAreaIds(area).get("areaNames");
        org.setAreaName(pareName);
        int count = organizationDao.update(org);
        FrontCacheUtils.delete(Organization.class, org.getId());
        LoggerUtils.updateLogger(LoggerModule.ZZJG,
            "组织机构管理——修改单位，单位id：" + org.getId());
        if (count < 0) {
          logger.info("更新部门数据失败");
        }
      } else {
        org.setCrtDttm(new Date());
        Organization saveOrg = organizationDao.insert(org);
        LoggerUtils.insertLogger(LoggerModule.ZZJG,
            "组织机构管理——增加单位，单位id：" + saveOrg.getId());
      }
    }

  }

  @Override
  public void delUnit(Integer id) {
    if (id != null) {
      organizationDao.delete(id);
    }

  }

  @Override
  public List<Organization> valiSchInfo(Organization org) {
    List<Organization> find = organizationDao.list(org, 1);
    return find;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Area> orgFindTree() {
    Cache cache = cacheManager.getCache("areaTree");
    ValueWrapper v = cache.get("area");
    List<Area> areaList = v == null ? null : (List<Area>) v.get();
    UserSpace us = (UserSpace) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    if (areaList == null) {
      Area area = new Area();
      area.addCustomCulomn("id,parentId,name");
      areaList = areaService.findAll(area);
      cache.put("area", areaList);
    }
    if (us.getSysRoleId().intValue() == SysRole.ADMIN.getId().intValue()) { // 是超级管理员
      return areaList;
    } else { // 其他管理员，需要判断该管理员是否有管辖范围，有则只显示管辖范围的区域，没有则显示全部
      UserManagescope um = new UserManagescope();
      um.addCustomCulomn("distinct areaId");
      um.setUserId(us.getUserId());
      um.setRoleId(us.getRoleId());
      um.setOrgId(UserManagescope.DEFAULT_ORG_ID);
      List<UserManagescope> umList = userManagescopeDao.listAll(um);
      if (umList != null && umList.size() > 0) { // 有管辖范围
        ValueWrapper mapValue = cache.get("areaMap");
        Map<Integer, Area> areaMap;
        if (mapValue == null) {
          areaMap = listToMap(areaList);
          cache.put("areaMap", areaMap);
        } else {
          areaMap = (Map<Integer, Area>) mapValue.get();
        }
        areaList = getScopeAreaList(umList, areaMap);
      }
    }
    return areaList;
  }

  /**
   * 获取管辖范围内的地区层级集合
   * 
   * @param umList
   * @param areaMap
   * @return
   */
  private List<Area> getScopeAreaList(List<UserManagescope> umList,
      Map<Integer, Area> areaMap) {
    List<Area> areaList = new ArrayList<Area>();
    Set<Integer> exitsId = new HashSet<Integer>();
    for (UserManagescope um : umList) {
      Area area = areaMap.get(um.getAreaId());
      areaList.add(area);
      exitsId.add(area.getId());
      while (area.getParentId() != null || area.getParentId() != -1) {
        area = areaMap.get(area.getParentId());
        if (area == null) {
          break;
        } else {
          if (!exitsId.contains(area.getId())) {
            areaList.add(area);
            exitsId.add(area.getId());
          }
        }
      }
    }
    return areaList;
  }

  /**
   * list转map
   * 
   * @param areaList
   * @return
   */
  private Map<Integer, Area> listToMap(List<Area> areaList) {
    Map<Integer, Area> areaMap = new HashMap<Integer, Area>();
    for (Area area : areaList) {
      areaMap.put(area.getId(), area);
    }
    return areaMap;
  }

  /**
   * @param file
   * @see com.tmser.tr.back.jxtx.service.JXTXBaseManageService#batchInsertSchool(org.springframework.web.multipart.MultipartFile)
   */
  @Override
  public Map<String, String> batchInsertSchool(MultipartFile file,
      Integer orgType, Integer areaId) {
    Map<String, String> returnMap = new HashMap<String, String>();
    if (file.isEmpty()) {
      returnMap.put("flag", "warn");
      returnMap.put("msg", "文件是空的！");
    } else {
      String fileName = file.getOriginalFilename();
      String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
      if ("xls".equals(prefix) || "xlsx".equals(prefix)) {
        StringBuilder sb = new StringBuilder();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("areaId", areaId);
        params.put("orgType", orgType);
        try {
          super.importData(file.getInputStream(), params, sb);
        } catch (IOException e) {
          logger.error("", e);
          returnMap.put("flag", "warn");
          returnMap.put("msg", "读取文件失败！");
          return returnMap;
        }
        @SuppressWarnings("unchecked")
        Map<String, Organization> orgMap = (Map<String, Organization>) params
            .get("orgMap");
        if (orgMap.size() > 0) {
          sb.append("共成功添加" + orgMap.size() + "个学校！");
          LoggerUtils.insertLogger(LoggerModule.ZZJG, "组织机构——学校管理——批量添加学校机构成功");
          returnMap.put("flag", "success");
          returnMap.put("msg", sb.toString());
        } else {
          returnMap.put("flag", "warn");
          returnMap.put("msg", "文件没有可用数据或不是对应的模板文件！");
        }
      } else {
        returnMap.put("flag", "warn");
        returnMap.put("msg", "文件的格式错误，后缀名必须是xls或者xlsx的文件！");
      }
    }

    return returnMap;
  }

  enum ExcelHeader implements ExcelTitle {
    ID(32, "ID"), NAME(true, 64, "学校全称", "单位名称"), SHORTNAME("学校简称",
        "单位简称"), ORGTYPE("学校类型", "机构类型"), ENGNAME("英文名称"), CONTACTOR(
            "联系人"), PHONE(32, "联系电话"), POSTCODE(8, "邮编"), DESC(200, "学校简介",
                "单位简介"), ADDR(64, "联系地址", "地址"), EMAIL(64, "邮箱",
                    "电子邮箱"), HOMEURL(2048, "学校网址", "单位网站");

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
      return size;
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
    return 3;// 标题行所在行
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
  protected void beforeSheetParse(Sheet sheet, Map<String, Object> params,
      StringBuilder returnMsg) {
    if (params != null) {
      params.put("orgMap", new HashMap<String, Organization>());
      Integer areaId = (Integer) params.get("areaId");
      List<MetaRelationship> orgTypes = MetaUtils.getOrgTypeMetaProvider()
          .listAll();
      Map<String, MetaRelationship> xzMap = new HashMap<String, MetaRelationship>();
      for (MetaRelationship mr : orgTypes) {
        xzMap.put(mr.getName(), mr);
      }
      params.put("xzMap", xzMap);
      Area area = areaService.findOne(areaId);
      Map<String, String> areaMap = getAreaIds(area);
      params.put("areaIds", areaMap.get("areaIds"));
      params.put("areaNames", areaMap.get("areaNames"));
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
  @SuppressWarnings("unchecked")
  protected void parseRow(Map<ExcelTitle, String> rowValueMap,
      Map<String, Object> params, Row row, StringBuilder sb) {
    if (row != null) {
      int i = row.getRowNum();
      Integer areaId = (Integer) params.get("areaId");
      String areaIds = (String) params.get("areaIds");
      String areaNames = (String) params.get("areaNames");
      Integer orgType = (Integer) params.get("orgType");
      Map<String, MetaRelationship> xzMap = (Map<String, MetaRelationship>) params
          .get("xzMap");
      Map<String, Organization> orgMap = (Map<String, Organization>) params
          .get("orgMap");

      String name = rowValueMap.get(ExcelHeader.NAME);
      String shortName = rowValueMap.get(ExcelHeader.SHORTNAME);
      String type = rowValueMap.get(ExcelHeader.ORGTYPE);

      if (StringUtils.isEmpty(name) || StringUtils.isEmpty(type)) {
        sb.append("第" + (i) + "行必填项有空值，忽略此行数据。<br>");
        logger.debug("第{}行必填项有空值，忽略此行数据。", i);
        return;
      }

      if (orgMap.containsKey(name)) {
        sb.append("第" + (i) + "行学校的名称重复，忽略此行数据。<br>");
        logger.debug("第{}行学校的名称重复，忽略此行数据。", i);
        return;
      }

      Organization org = new Organization();
      org.setAreaId(areaId);// 区域id
      org.setName(name);// 全称
      Organization orgTemp = organizationDao.getOne(org);
      if (orgTemp != null) {
        sb.append("第" + (i) + "行学校的名称重复，忽略此行数据。<br>");
        logger.debug("第{}行学校的名称重复，忽略此行数据。", i);
        return;
      }
      MetaRelationship metaRelationship = xzMap.get(type);
      if (metaRelationship == null) {
        sb.append("第" + (i) + "行选择的“学校类型”不匹配，请重新认真选择，忽略此行数据。<br>");
        logger.debug("第{}行选择的“学校类型”不匹配，请重新认真选择，忽略此行数据。", i);
        return;
      }
      org.setAreaIds("," + areaIds + ",");// areaids
      org.setAreaName(areaNames);// 区域名称
      org.setOrgType(orgType);// 机构类别
      org.setCrtId(CurrentUserContext.getCurrentUser().getId());// 创建者
      org.setCrtDttm(new Date());// 创建时间
      org.setEnable(Organization.ENABLE);// 可用
      org.setShortName(StringUtils.isBlank(shortName) ? name : shortName);// 简称
      org.setType(0);//
      org.setTypeName("学校");//
      org.setSchoolings(metaRelationship.getId()); // 学校类型

      List<OrganizationRelationship> orList = new ArrayList<OrganizationRelationship>();
      Map<Integer, List<Meta>> phaseGradeMap = MetaUtils
          .getOrgTypeMetaProvider().listPhaseGradeMap(org.getSchoolings());// .parsePhaseGrade(newMetaShip.getIds());
      Set<Integer> keySet = phaseGradeMap.keySet();
      StringBuilder phaseStr = new StringBuilder(StringUtils.COMMA);
      for (Integer phase : keySet) {
        phaseStr.append(phase).append(StringUtils.COMMA);
        OrganizationRelationship or = new OrganizationRelationship();
        or.setPhaseId(phase);
        or.setSchooling(phaseGradeMap.get(phase).size());
        if (org.getId() != null) {
          or.setOrgId(org.getId());
        }
        orList.add(or);
        org.setOrsList(orList);
      }

      org.setPhaseTypes(phaseStr.toString());// 学段类型
      org.setSort(0);
      org.setTreeLevel(0);
      org.setEnglishName(rowValueMap.get(ExcelHeader.ENGNAME));// 英文名称
      org.setContactPerson(rowValueMap.get(ExcelHeader.CONTACTOR));// 联系人
      org.setPhoneNumber(rowValueMap.get(ExcelHeader.PHONE));// 联系方式
      org.setEmail(rowValueMap.get(ExcelHeader.EMAIL));// 邮箱
      org.setAddress(rowValueMap.get(ExcelHeader.ADDR));// 地址
      org.setSchWebsite(rowValueMap.get(ExcelHeader.HOMEURL));// 学校网址
      orgMap.put(name, org);

      org = organizationDao.insert(org);
      for (OrganizationRelationship organizationRelationship : org
          .getOrsList()) {
        organizationRelationship.setOrgId(org.getId());
        organizationRelationshipService.save(organizationRelationship);
      }
    }
  }

}
