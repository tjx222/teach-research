/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.schconfig.teach.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.back.jxtx.service.JXTXBaseManageService;
import com.tmser.tr.back.schconfig.teach.service.OrgCatalogManageService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.org.bo.Organization;
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
@RequestMapping("/jy/back/schconfig/catalog")
public class SchCatalogController extends AbstractController {
  @Autowired
  private OrganizationService orgService;
  @Autowired
  private OrgCatalogManageService catalogService;
  @Autowired
  private JXTXBaseManageService jcManageService;

  @RequestMapping("index")
  public String index(Model m) {
    UserSpace currentSpace = CurrentUserContext.getCurrentSpace();
    Integer sysRoleId = currentSpace.getSysRoleId();
    m.addAttribute("showSch", true);
    m.addAttribute("showArea", true);
    if (SysRole.XXGLY.getId().equals(sysRoleId)) {
      m.addAttribute("showArea", false);
      Organization org = (Organization) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_ORG);
      return treeIndex(org.getId(), m);
    }
    return viewName("index");
  }

  @RequestMapping("/publisher")
  public String treeIndex(Integer orgId, Model m) {
    Organization org = orgService.findOne(orgId);
    if (org != null && 0 == org.getType().intValue()) {
      List<Map<String, Object>> orgJCtree = catalogService.findOrgJCtree(orgId);
      m.addAttribute("list", orgJCtree);
    }
    m.addAttribute("orgId", orgId);
    return viewName("tree_index");
  }

  @RequestMapping("/publisher/area")
  public String treeIndexArea(Integer areaId, Model m) {
    List<Map<String, Object>> orgJCtree = catalogService.findAreaJCtree(areaId);
    m.addAttribute("list", orgJCtree);
    m.addAttribute("areaId", areaId);
    return viewName("tree_index");
  }

  @RequestMapping("/catalog_tree")
  public String catalogIndex(String comId, Integer publisherId, Integer areaId, Integer orgId, Model m) {
    m.addAttribute("list", jcManageService.findBookCatalogTree(comId));
    m.addAttribute("comId", comId);
    Meta meta = MetaUtils.getMeta(publisherId);
    if (orgId != null) {
      m.addAttribute("canEdit", "org".equals(meta.getScope()));

    } else if (areaId != null) {
      m.addAttribute("canEdit", "area".equals(meta.getScope()));
    } else {
      m.addAttribute("canEdit", false);
    }
    return viewName("catalog_tree");
  }

  @RequestMapping("/catalogTree")
  @ResponseBody
  public Object catalog_tree(Model m, String comId) {
    return jcManageService.findBookCatalogTreeForZtree(comId);
  }
}
