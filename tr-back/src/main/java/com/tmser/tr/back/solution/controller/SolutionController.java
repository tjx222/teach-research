package com.tmser.tr.back.solution.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.back.solution.service.OrgSolutionService;
import com.tmser.tr.back.solution.service.SolutionService;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.uc.bo.Role;
import com.tmser.tr.uc.bo.SysSolution;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author tmser
 * @version $Id: SolutionController.java, v 1.0 2015年9月18日 下午3:37:17 tmser Exp $
 */
@Controller
@RequestMapping("/jy/back/solution")
public class SolutionController extends AbstractController {

  @Autowired
  private SolutionService solutionService;
  @Autowired
  private OrganizationService ogService;
  @Autowired
  private OrgSolutionService orgSolutionService;

  /**
   * 方案首页
   * 
   * @param solution
   * @param m
   * @return
   */
  @RequestMapping("index")
  public String index(SysSolution solution, Model m) {
    m.addAttribute("model", solution);
    solution.addOrder(" crtDttm desc");
    m.addAttribute("data", solutionService.getPageSolutionList(solution));
    return viewName("index");
  }

  /**
   * 新增范围
   * 
   * @param ids
   * @param solId
   * @return
   */
  @RequestMapping("addFw")
  @ResponseBody
  public JuiResult addFw(String ids, Integer solId) {
    JuiResult jr = new JuiResult();
    if (StringUtils.isNotBlank(ids)) {
      try {
        orgSolutionService.editOrgSolution(ids, solId);
        LoggerUtils.updateLogger("编辑方案[id={}]范围为[{}]", solId, ids);
      } catch (Exception e) {
        logger.error("编辑方案适用范围失败 ", e);
      }
      jr.setRel("org_solution");
      jr.setMessage("新增成功！");
    } else {
      jr.setRel("org_solution");
      jr.setMessage("您未选择任何应用范围，请选择后重新添加！");
    }
    return jr;
  }

  /**
   * 新增或修改方案（复制权限）
   * 
   * @return
   */
  @RequestMapping("addOrEdit")
  @ResponseBody
  public JuiResult addOrUpdate(SysSolution solution, Model m) {
    User currentUser = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
    JuiResult jr = new JuiResult();
    Date now = new Date();
    try {
      if (solution.getId() != null) {
        solution.setLastupDttm(now);
        solution.setCrtId(currentUser.getId());
        solutionService.update(solution);
        LoggerUtils.updateLogger("编辑销售方案[id={}]信息", solution.getId());
      } else {
        solution.setCrtDttm(now);
        solution.setLastupDttm(now);
        solution.setCrtId(currentUser.getId());
        solution.setLastupId(currentUser.getId());
        solution.setEnable(1);
        solutionService.addSolution(solution); // 保存方案
        LoggerUtils.insertLogger("新增销售方案[id={}]", solution.getId());
      }
      jr.setRel("solution");
      jr.setMessage("保存成功！");
    } catch (Exception e) {
      jr.setMessage("保存失败！");
      logger.error("方案保存失败!", e);
    }
    return jr;
  }

  /**
   * 删除应用范围
   * 
   * @param id
   * @param m
   * @return
   */
  @RequestMapping("delFw")
  @ResponseBody
  public JuiResult delFw(Integer id, Integer sid) {
    JuiResult rs = new JuiResult();
    if (id != null && sid != null) {
      try {
        orgSolutionService.deleteSolutionFw(id, sid);
        rs.setNavTabId("norg_solution");
        rs.setCallbackType("");
        rs.setMessage(" 删除成功！");
      } catch (Exception e) {
        rs.setMessage("删除失败！");
        logger.error("应用范围删除失败！", e);
      }
    }

    return rs;
  }

  /**
   * 删除方案
   * 
   * @param id
   * @param m
   * @return
   */
  @RequestMapping("delete")
  @ResponseBody
  public JuiResult deleteSolution(Integer id, Model m) {
    Role role = new Role();
    role.setSolutionId(id);
    JuiResult rs = new JuiResult();
    try {
      solutionService.clearSolution(id);
      LoggerUtils.deleteLogger("清除销售方案及相关的信息，方案[id={}]", id);
      rs.setCallbackType("");
      rs.setMessage("删除成功！");
    } catch (Exception e) {
      rs.setMessage("删除失败！");
      logger.error("方案删除失败！", e);
    }
    return rs;
  }

  /**
   * 去新增范围列表
   * 
   * @param m
   * @return
   */
  @RequestMapping("goAddFw")
  public String goAddFw(Organization org, Model m) {
    m.addAttribute("model", org);
    return viewName("/yyfw/solutionScopeEdit");
  }

  /**
   * 获取用户的管理权限
   * 
   * @param userId
   * @param m
   */
  @RequestMapping("getSolutionScope")
  @ResponseBody
  public List<Organization> getSolutionScope(Organization or, Model m) {
    Integer sid = or.getId();
    or.addCustomCulomn("id,areaId");
    or.setId(null);
    or.buildCondition(
        " and id in (select s.orgId from SysOrgSolution s where s.solutionId = :solutionId and s.isDelete = :isDelete)")
        .put("solutionId", sid).put("isDelete", false);
    return ogService.findAll(or);
  }

  @RequestMapping("goAddOrEdit")
  public String goAddOrEdit(Integer id, Model m) {
    if (id != null) {
      SysSolution solution = solutionService.findOne(id);
      m.addAttribute("solution", solution);
    }
    return viewName("addOrEditSolu");
  }

  /**
   * 应用范围列表
   * 
   * @param id
   * @return
   */
  @RequestMapping("yyfw")
  public String yyfwList(Organization or, Model m) {
    or.pageSize(20);
    Integer sid = or.getId();
    or.setId(null);
    String orgName = or.getName();
    String areaName = or.getAreaName();

    if (StringUtils.isNotEmpty(orgName)) {
      or.setName(SqlMapping.LIKE_PRFIX + orgName + SqlMapping.LIKE_PRFIX);
    }
    if (StringUtils.isNotEmpty(areaName)) {
      or.setAreaName(SqlMapping.LIKE_PRFIX + areaName + SqlMapping.LIKE_PRFIX);
    }
    or.buildCondition(
        " and id in (select s.orgId from SysOrgSolution s where s.solutionId = :solutionId and s.isDelete = :isDelete)")
        .put("solutionId", sid).put("isDelete", false);
    PageList<Organization> oslist = ogService.findByPage(or);
    or.setName(orgName);
    or.setAreaName(areaName);
    or.setId(sid);
    m.addAttribute("data", oslist);
    m.addAttribute("model", or);
    return viewName("yyfw/yyfwIndex");
  }

}
