/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.schconfig.teach.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.back.schconfig.teach.service.PhaseSubjectManageService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaProvider.OrgTypeMetaProvider;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.AreaService;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: SchPublisherController.java, v 1.0 2017年12月12日 上午11:36:09
 *          tmser Exp $
 */
@Controller
@RequestMapping("/jy/back/schconfig")
public class SchSubjectController extends AbstractController {

  @Autowired
  private OrganizationService orgService;
  @Autowired
  private PhaseSubjectManageService phaseSubjectManageService;
  @Autowired
  private AreaService areaService;

  @RequestMapping("/subject/index")
  public String index(Model m) {
    UserSpace currentUserSpace = CurrentUserContext.getCurrentSpace();
    Organization org = (Organization) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_ORG);
    Integer sysRoleId = currentUserSpace.getSysRoleId();
    m.addAttribute("showSch", true);
    m.addAttribute("showArea", true);
    if (SysRole.XXGLY.getId().equals(sysRoleId)) {

      m.addAttribute("showArea", false);
      return getphaseByorgId(org.getId(), m);
    }
    return viewName("subject/index");
  }

  @RequestMapping("/subject/{orgid}")
  public String getphaseByorgId(@PathVariable("orgid") Integer orgId, Model m) {
    @SuppressWarnings("unchecked")
    List<MetaRelationship> metaRelationships = (List<MetaRelationship>) listPhase(orgId, null).getData();
    m.addAttribute("orgId", orgId);
    m.addAttribute("XDlist", metaRelationships);
    return viewName("subject/subjectIndex");
  }

  @RequestMapping("subject/phase")
  public String getphaseByAreaId(Integer areaId, Model m) {
    @SuppressWarnings("unchecked")
    List<MetaRelationship> sysConfigs = (List<MetaRelationship>) listPhase(null, areaId).getData();
    m.addAttribute("XDlist", sysConfigs);
    m.addAttribute("areaId", areaId);
    return viewName("subject/arearIndex");
  }

  @RequestMapping("/phase/list")
  @ResponseBody
  public JuiResult listPhase(Integer orgId, Integer areaId) {
    JuiResult result = new JuiResult();
    if (orgId != null) {
      Organization org = orgService.findOne(orgId);
      OrgTypeMetaProvider orgTypeMetaProvider = MetaUtils.getOrgTypeMetaProvider();

      MetaRelationship sysConfig = orgTypeMetaProvider.getMetaRelationship(org.getSchoolings());
      List<MetaRelationship> phaseList = orgTypeMetaProvider.listAllPhase(sysConfig.getId());
      result.setData(phaseList);

    }
    if (areaId != null) {
      result.setData(MetaUtils.getPhaseMetaProvider().listAll());
    }
    return result;
  }

  @RequestMapping("subject/list")
  public String listSubject(Integer phase, Integer orgId, Model m) {
    String areaIds = orgService.findOne(orgId).getAreaIds();
    List<Meta> subjectList = MetaUtils.getPhaseSubjectMetaProvider().listAllSubject(orgId, phase,
        StringUtils.toIntegerArray(areaIds.substring(1, areaIds.lastIndexOf(",")), ","));
    m.addAttribute("subjectList", subjectList);
    m.addAttribute("orgId", orgId);
    m.addAttribute("phaseId", phase);
    return viewName("subject/list");
  }

  @RequestMapping("/subject/toadd")
  public String toAddSubject(Integer phaseId, String orgId, Model m) {
    List<Meta> unAddedList = phaseSubjectManageService.getUnAddList(orgId, phaseId);
    m.addAttribute("orgId", orgId);
    m.addAttribute("unAddedList", unAddedList);
    m.addAttribute("phaseId", phaseId);
    return viewName("subject/subjectAdd");
  }

  @RequestMapping("/subject/area/list")
  public String listSubject1(Integer phase, Integer areaId, Model m) {
    List<Integer> areaIds = areaService.getAreaIds(areaId);
    Integer[] idArr = new Integer[areaIds.size()];
    areaIds.toArray(idArr);
    List<Meta> subjectList = MetaUtils.getPhaseSubjectMetaProvider().listAllSubject(null, phase, idArr);
    m.addAttribute("areaId", areaId);
    m.addAttribute("subjectList", subjectList);
    m.addAttribute("phaseId", phase);
    return viewName("subject/arealist");
  }

  /**
   * 
   * @param phaseId
   *          学段id
   * @param areaId
   *          区域id
   * @param m
   *          Model
   * @return String
   */
  @RequestMapping("/subject/area/toadd")
  public String toAddAreaSubject(Integer phaseId, Integer areaId, Model m) {
    List<Meta> unAddedList = phaseSubjectManageService.getUnAddList(areaId, phaseId);
    m.addAttribute("areaId", areaId);
    m.addAttribute("unAddedList", unAddedList);
    m.addAttribute("phaseId", phaseId);
    return viewName("subject/areasubjectAdd");
  }

  /**
   * 
   * @param phaseId
   *          学段id
   * @param orgId
   *          学校id
   * @param m
   *          Model
   * @return String
   */
  @RequestMapping("/subject/toadd/custom")
  public String toAddCustomSubject(Integer phaseId, String orgId, Model m) {
    m.addAttribute("orgId", orgId);
    m.addAttribute("phaseId", phaseId);
    return viewName("subject/customadd");
  }

  /**
   * 
   * @param phaseId
   *          学段id
   * @param areaId
   *          区域od
   * @param m
   *          Model
   * @return String
   */
  @RequestMapping("/subject/toadd/area/custom")
  public String toAddAreaCustomSubject(Integer phaseId, Integer areaId, Model m) {
    m.addAttribute("areaId", areaId);
    m.addAttribute("phaseId", phaseId);
    return viewName("subject/areacustomadd");
  }

  /**
   * 
   * @param phaseId
   *          学段id
   * @param orgId
   *          学校id
   * @param name
   *          名称
   * @return JuiResult
   */
  @RequestMapping("/subject/exist")
  @ResponseBody
  public JuiResult isSubjectNameExist(Integer phaseId, String orgId, String name) {
    JuiResult result = new JuiResult();
    Meta model = phaseSubjectManageService.get(orgId, name);
    if (model != null) {
      result.setData(model);
    }
    return result;
  }

  /**
   * 
   * @param phaseId
   *          学段id
   * @param areaId
   *          区域id
   * @param name
   *          名称
   * @return JuiResult
   */
  @RequestMapping("/subject/area/exist")
  @ResponseBody
  public JuiResult isAreaSubjectNameExist(Integer phaseId, Integer areaId, String name) {
    JuiResult result = new JuiResult();
    Meta model = phaseSubjectManageService.get(areaId, name);
    if (model != null) {
      result.setData(model);
    }
    return result;
  }

  /**
   * 
   * @param phaseId
   *          学段id
   * @param areaId
   *          区域id
   * @param name
   *          名称
   * @param descs
   *          描述
   * @return JuiResult
   */
  @RequestMapping("/subject/area/custom/save")
  @ResponseBody
  public JuiResult saveAreaCustomSubject(Integer phaseId, Integer areaId, String name, String descs) {
    JuiResult result = new JuiResult();
    try {
      phaseSubjectManageService.saveCustom(phaseId, areaId, name, descs);
    } catch (Exception e) {
      logger.error("区域保存自定义学科失败", e);
      result.setStatusCode(JuiResult.FAILED);
    }
    return result;
  }

  /**
   * 
   * @param phaseId
   *          学段id
   * @param orgId
   *          学校id
   * @param name
   *          名称
   * @param descs
   *          描述
   * @return JuiResult
   */
  @RequestMapping("/subject/custom/save")
  @ResponseBody
  public JuiResult saveCustomSubject(Integer phaseId, Integer orgId, String name, String descs) {
    JuiResult result = new JuiResult();
    try {
      phaseSubjectManageService.saveCustom(phaseId, String.valueOf(orgId), name, descs);
      result.setStatusCode(JuiResult.SUCCESS);
      result.setCallbackType(JuiResult.CB_CLOSE);
    } catch (Exception e) {
      logger.error("学校保存自定义学科失败", e);
      result.setStatusCode(JuiResult.FAILED);
    }
    return result;
  }

  /**
   * 
   * @param subjectAddIds
   *          学科ids
   * @param phaseId
   *          学段id
   * @param orgId
   *          学校id
   * @return JuiResult
   */
  @RequestMapping("/subject/save")
  @ResponseBody
  public JuiResult save(String subjectAddIds, Integer phaseId, Integer orgId) {
    JuiResult result = new JuiResult();
    try {
      phaseSubjectManageService.save(subjectAddIds, phaseId, orgId);
    } catch (Exception e) {
      logger.error("学校保存学科失败", e);
      result.setStatusCode(JuiResult.FAILED);
    }
    return result;
  }

  /**
   * 
   * @param subjectAddIds
   *          学科ids
   * @param phaseId
   *          学段id
   * @param areaId
   *          区域id
   * @return JuiResult
   */
  @RequestMapping("/subject/area/save")
  @ResponseBody
  public JuiResult saveAreaSubject(Integer[] subjectAddIds, Integer phaseId, Integer areaId) {
    JuiResult re = new JuiResult();
    try {
      phaseSubjectManageService.save(subjectAddIds, phaseId, areaId);
    } catch (Exception e) {
      logger.error("区域保存学科失败", e);
      re.setStatusCode(JuiResult.FAILED);
    }
    return re;
  }

  /**
   * 
   * @param id
   *          学科id
   * @param phaseId
   *          学段id
   * @param orgId
   *          学校id
   * @return JuiResult
   */
  @RequestMapping("/subject/delete/{id}")
  @ResponseBody
  public JuiResult delete(@PathVariable Integer id, Integer phaseId, Integer orgId) {
    JuiResult re = new JuiResult();
    try {
      phaseSubjectManageService.delete(id, phaseId, String.valueOf(orgId));
    } catch (Exception e) {
      logger.error("删除学科失败", e);
      re.setStatusCode(JuiResult.FAILED);
    }
    return re;
  }

  /**
   * 
   * @param id
   *          学科id
   * @param phaseId
   *          学段id
   * @param areaId
   *          区域id
   * @return JuiResult
   */
  @RequestMapping("/subject/area/delete/{id}")
  @ResponseBody
  public JuiResult deleteAreaSubject(@PathVariable Integer id, Integer phaseId, Integer areaId) {
    JuiResult result = new JuiResult();
    try {
      phaseSubjectManageService.delete(id, phaseId, areaId);
    } catch (Exception e) {
      logger.error("删除学科失败", e);
      result.setStatusCode(JuiResult.FAILED);
    }
    return result;
  }

}
