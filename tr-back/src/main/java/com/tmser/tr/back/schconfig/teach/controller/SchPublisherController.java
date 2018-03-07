/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.schconfig.teach.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.back.schconfig.teach.service.PublisherManageService;
import com.tmser.tr.back.schconfig.vo.PublisherVo;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.meta.bo.PublishRelationship;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.AreaService;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * <pre>
 *
 * </pre>
 *
 * @author guohuawei
 * @version $Id: SchPublisherController.java, v 1.0 2017年12月12日 上午11:36:09
 *          guohuawei Exp $
 */
@Controller
@RequestMapping("/jy/back/schconfig/publisher")
public class SchPublisherController extends AbstractController {
  @Autowired
  private PublisherManageService publisherManageService;
  @Autowired
  private OrganizationService orgService;
  @Autowired
  private AreaService areaService;

  @RequestMapping("index")
  public String index(Model m) {
    UserSpace currentSpace = CurrentUserContext.getCurrentSpace();
    Integer sysRoleId = currentSpace.getSysRoleId();
    m.addAttribute("showSch", true);
    m.addAttribute("showArea", true);
    if (SysRole.XXGLY.getId().equals(sysRoleId)) {
      m.addAttribute("showArea", false);
      Organization org = (Organization) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_ORG);
      return phaseSubjectTreeIndex(org.getId(), m);
    }
    return viewName("index");
  }

  @RequestMapping("/org")
  public String phaseSubjectTreeIndex(Integer orgId, Model m) {
    m.addAttribute("xklist", publisherManageService.findXDXKtree(orgId));
    m.addAttribute("orgId", orgId);
    return viewName("tree_index");
  }

  @RequestMapping("/area")
  public String phaseSubjectTreeAreaIndex(Integer areaId, Model m) {
    m.addAttribute("xklist", publisherManageService.findXDXKtree1(areaId));
    m.addAttribute("areaId", areaId);
    return viewName("areaIndex");
  }

  @RequestMapping("/list")
  public String list(PublishRelationship model, Model m) {
    // String areaIds = orgService.findOne(model.getOrgId()).getAreaIds();
    // Integer[] areaIdArr = StringUtils.toIntegerArray(areaIds.substring(1,
    // areaIds.lastIndexOf(",")), ",");
    model.setScope("org");
    m.addAttribute("publisherList", publisherManageService.listAllPubliserMetaByScope(model));
    m.addAttribute("phaseId", model.getPhaseId());
    m.addAttribute("subjectId", model.getSubjectId());
    m.addAttribute("orgId", model.getOrgId());
    return viewName("cbs_index");
  }

  @RequestMapping("/area/list")
  public String listAera(PublishRelationship model, Model m) {
    // List<Integer> areaIds = areaService.getAreaIds(model.getAreaId());
    model.setScope("area");
    m.addAttribute("publisherList", publisherManageService.listAllPubliserMetaByScope(model));
    m.addAttribute("phaseId", model.getPhaseId());
    m.addAttribute("subjectId", model.getSubjectId());
    m.addAttribute("areaId", model.getAreaId());
    return viewName("areaCbs_index");
  }

  @RequestMapping("/toadd")
  public String toadd(PublishRelationship model, Model m) {
    model.setScope("org");
    m.addAttribute("unAddedList", publisherManageService.getUnAddList(model));
    m.addAttribute("phaseId", model.getPhaseId());
    m.addAttribute("subjectId", model.getSubjectId());
    m.addAttribute("orgId", model.getOrgId());
    return viewName("add_cbs");
  }

  @RequestMapping("/save")
  @ResponseBody
  public Object add(Integer[] publisherAddIds, PublishRelationship model) {
    Result re = Result.newInstance();
    try {
      model.setScope("org");
      publisherManageService.save(publisherAddIds, model);

    } catch (Exception e) {
      re.setCode(JuiResult.FAILED);
      logger.error("保存出版社失败", e);
    }
    return re;
  }

  @RequestMapping("/del/{id}")
  @ResponseBody
  public Object del(@PathVariable Integer id, PublishRelationship model) {
    JuiResult re = new JuiResult();
    try {
      publisherManageService.del(id, model);
    } catch (Exception e) {
      re.setStatusCode(JuiResult.FAILED);
      logger.error("删除出版社失败", e);
    }
    return re;
  }

  @RequestMapping("/toadd/custom")
  public String toaddCustom(Integer phaseId, Integer subjectId, String orgId, Model m) {
    m.addAttribute("phaseId", phaseId);
    m.addAttribute("subjectId", subjectId);
    m.addAttribute("orgId", orgId);
    return viewName("customadd");
  }

  @RequestMapping("/save/custom")
  @ResponseBody
  public Object saveCuston(PublisherVo vo) {
    publisherManageService.saveCustom(vo);
    return new JuiResult();
  }

  @RequestMapping("area/toadd")
  public String toaddarea(PublishRelationship model, Model m) {
    model.setScope("area");
    m.addAttribute("publisherList", publisherManageService.getUnAddList(model));
    m.addAttribute("phaseId", model.getPhaseId());
    m.addAttribute("subjectId", model.getSubjectId());
    m.addAttribute("areaId", model.getAreaId());
    return viewName("areaAdd_cbs");
  }

  @RequestMapping("area/save")
  @ResponseBody
  public Object addArea(Integer[] publisherAddIds, PublishRelationship model) {
    Result re = Result.newInstance();
    try {
      model.setScope("area");
      publisherManageService.save(publisherAddIds, model);

    } catch (Exception e) {
      re.setCode(JuiResult.FAILED);
      logger.error("保存出版社失败", e);
    }
    return re;
  }

  @RequestMapping("/area/toadd/custom")
  public String toaddCustom(Integer phaseId, Integer subjectId, Integer areaId, Model m) {
    m.addAttribute("phaseId", phaseId);
    m.addAttribute("subjectId", subjectId);
    m.addAttribute("areaId", areaId);
    return viewName("areaCustomadd");
  }

}
