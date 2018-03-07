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

import com.tmser.tr.back.dic.service.SysDicService;
import com.tmser.tr.back.logger.LoggerModule;
import com.tmser.tr.back.schconfig.teach.service.OrgJcManageService;
import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.bo.BookSync;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.meta.bo.SysDic;
import com.tmser.tr.manage.meta.service.BookService;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.RoleService;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;

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
@RequestMapping("/jy/back/schconfig/commdity")
public class SchCommdityController extends AbstractController {
  @Autowired
  private OrgJcManageService orgJcManageService;
  @Autowired
  private BookService bookService;
  @Autowired
  private RoleService roleService;
  @Autowired
  private SysDicService sysDicService;

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
    List<Map<String, Object>> list = orgJcManageService.findOrgJCtree(orgId);
    m.addAttribute("list", list);
    m.addAttribute("orgId", orgId);
    return viewName("tree_index");
  }

  /**
   * 教材列表
   * 
   * @param book
   *          BookSync
   */
  @RequestMapping("/jcList")
  public String jcglList(BookSync book, Model m) {
    m.addAttribute("book", book);
    m.addAttribute("booklist", orgJcManageService.findBookSync(book));
    Meta meta = MetaUtils.getMeta(book.getPublisherId());
    if (book.getAreaId() != null) {
      m.addAttribute("canOverride", "area".equals(meta.getScope()));
    }
    if (book.getOrgId() != null) {
      m.addAttribute("canOverride", "org".equals(meta.getScope()));
    }
    return viewName("jc_index");
  }

  /**
   * 添加教材
   * 
   * @param bookSync
   *          BookSync
   */
  @RequestMapping("/add_jc")
  public String addJc(BookSync bookSync, Model m) {
    List<Book> booklist = orgJcManageService.findUnAddBooks(bookSync);
    m.addAttribute("phaseList", MetaUtils.getPhaseMetaProvider().listAllPhaseMeta());
    MetaRelationship phase = MetaUtils.getPhaseMetaProvider().getMetaRelationshipByPhaseId(bookSync.getPhaseId());
    m.addAttribute("gradeList", MetaUtils.getPhaseGradeMetaProvider().listAllGradeByPhaseId(phase.getId()));
    m.addAttribute("booklist", booklist);
    m.addAttribute("book", bookSync);
    return viewName("add_jc");
  }

  /**
   * 获取未添加的教材列表
   * 
   * @param bookSync
   *          BookSync
   */
  @RequestMapping("/unaddList")
  public String unaddList(Model m, BookSync bookSync) {
    List<Book> booklist = orgJcManageService.findUnAddBooks(bookSync);
    m.addAttribute("booklist", booklist);
    return viewName("unaddBook");
  }

  /**
   * 删除教材
   * 
   * @param id
   *          教材id
   * @param orgId
   *          学校id
   * @param areaId
   *          区域id
   */
  @RequestMapping("/del_jc")
  @ResponseBody
  public JuiResult deleteJc(Integer id, Integer orgId, Integer areaId) {
    JuiResult rs = new JuiResult();
    try {
      orgJcManageService.deleteBookSyncById(id, orgId, areaId);
      LoggerUtils.updateLogger(LoggerModule.JXTX, "教学体系——教材管理——移除选中教材，教材ID：{}", id);
      rs.setMessage("移除成功！");
    } catch (Exception e) {
      rs.setStatusCode(JuiResult.FAILED);
      rs.setMessage("移除失败！");
      logger.error("教材移除操作失败", e);
    }
    return rs;
  }

  /**
   * 保存教材
   * 
   * @param bookAddIds
   *          教材ids
   * @param grade
   *          年级
   * @param book
   *          BookSync
   */
  @RequestMapping("/org/save")
  @ResponseBody
  public JuiResult saveJc(String[] bookAddIds, Integer grade, BookSync book) {
    JuiResult rs = new JuiResult();
    try {
      if (StringUtils.isNotEmpty(book.getComId())) {
        rs.setMessage("修改成功！");
        logger.info("修改成功！");
      } else {
        rs.setMessage("保存成功");
        logger.info("保存教材同步失败");
      }
      bookService.saveJc(bookAddIds, book);
      rs.setStatusCode(JuiResult.SUCCESS);
    } catch (Exception e) {
      rs.setStatusCode(JuiResult.FAILED);
      if (StringUtils.isNotEmpty(book.getComId())) {
        rs.setMessage("修改失败！");
        logger.error("修改教材同步失败！", e);
      } else {
        rs.setMessage("保存失败！");
        logger.error("保存教材同步失败！", e);
      }
    }

    return rs;
  }

  /**
   * 自定义教材添加页面
   * 
   * @param book
   *          BookSync
   */
  @RequestMapping("/custom/add")
  public String toadd(Model m, BookSync book) {
    if (book.getPublisherId() != null) {
      Meta pb = MetaUtils.getMeta(book.getPublisherId());
      if (pb != null) {
        book.setPublisher(pb.getName());
      }
    }
    // 查找版次
    SysDic model = new SysDic();
    model.setParentId(196);
    m.addAttribute("edition", sysDicService.findAll(model));
    // 册别
    model.setParentId(175);
    m.addAttribute("fascicule", sysDicService.findAll(model));
    m.addAttribute("book", book);
    return viewName("customadd");
  }

  /**
   * 保存自定义教材
   * 
   * @param book
   *          BookSync
   */
  @RequestMapping("/custom/save")
  @ResponseBody
  public Object saveCustom(BookSync book) {
    JuiResult re = new JuiResult();
    try {
      bookService.saveBookAndBookSync(book);
      re.setStatusCode(JuiResult.SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      re.setStatusCode(JuiResult.FAILED);
    }
    return re;
  }

  /**
   * 区域教材tree
   * 
   * @param areaId
   *          区域id
   */
  @RequestMapping("/publisher/area")
  public String areaIndex(Integer areaId, Model m) {
    List<Map<String, Object>> list = orgJcManageService.findAreaJCtree(areaId);
    m.addAttribute("list", list);
    m.addAttribute("areaId", areaId);
    return viewName("tree_index");
  }

}
