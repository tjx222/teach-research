/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.jxtx.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.back.jxtx.service.JXTXBaseManageService;
import com.tmser.tr.back.jxtx.service.PublishRelationshipService;
import com.tmser.tr.back.logger.LoggerModule;
import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.common.vo.Datas;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaConstants;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.PublishRelationship;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author zpp
 * @version $Id: JXTXBaseManageController.java, v 1.0 2015年8月24日 上午11:37:35 zpp
 *          Exp $
 */
@Controller
@RequestMapping("/jy/back/jxtx")
public class JXTXBaseManageController extends AbstractController {

  @Autowired
  private JXTXBaseManageService jXTXBaseManageService;
  @Autowired
  private PublishRelationshipService publishRelationshipService;

  /**
   * 出版社管理入口地址
   * 
   * @param m
   * @return
   */
  @RequestMapping("/cbsIndex")
  public String cbsIndex(Model m) {
    List<Map<String, Object>> xklist = jXTXBaseManageService.findXDXKtree();
    m.addAttribute("xklist", xklist);
    return viewName("cbsgl/tree_index");
  }

  /**
   * 出版社管理列表
   * 
   * @param m
   * @param xdid
   * @param xkid
   * @return
   */
  @RequestMapping("/cbsglIndex")
  public String cbsglIndex(Model m, PublishRelationship pr) {
    m.addAttribute("pr", pr);
    pr.setScope("sys");
    pr.setEnable(1);
    List<PublishRelationship> cbslist = MetaUtils.getPublisherMetaProvider().findList(pr);
    m.addAttribute("cbslist", cbslist);
    return viewName("cbsgl/cbs_index");
  }

  /**
   * 添加出版社入口
   * 
   * @param m
   * @param pr
   * @return
   */
  @RequestMapping("/addCBS")
  public String addCBS(Model m, PublishRelationship pr) {

    List<Meta> cbslist = publishRelationshipService.findCBSFromSD(pr);
    m.addAttribute("cbslist", cbslist);
    m.addAttribute("pr", pr);
    return viewName("cbsgl/add_cbs");
  }

  /**
   * 修改出版社入口
   * 
   * @param m
   * @param pr
   * @return
   */
  @RequestMapping("/editCBS")
  public String editCBS(Model m, PublishRelationship pr) {
    pr = publishRelationshipService.findOne(pr.getId());
    m.addAttribute("pr", pr);
    return viewName("cbsgl/edit_cbs");
  }

  /**
   * 保存出版社信息
   * 
   * @param mr
   * @return
   */
  @RequestMapping("/saveCbs")
  @ResponseBody
  public JuiResult saveCbs(Datas publishs, PublishRelationship pr) {
    JuiResult rs = new JuiResult();
    try {
      if (pr.getId() != null) {
        rs.setMessage("修改成功！");
      } else {
        rs.setMessage("保存成功！");
      }
      publishRelationshipService.saveCbs(publishs, pr);
    } catch (Exception e) {
      rs.setStatusCode(JuiResult.FAILED);
      if (pr.getId() != null) {
        rs.setMessage("修改失败！");
      } else {
        rs.setMessage("保存失败！");
      }
      logger.error("级联关系保存失败", e);
    }

    return rs;
  }

  /**
   * 删除出版社关联信息
   * 
   * @param id
   * @return
   */
  @RequestMapping("/deleteCBS")
  @ResponseBody
  public JuiResult deleteCBS(Integer id) {
    JuiResult rs = new JuiResult();
    try {
      PublishRelationship pr = publishRelationshipService.findOne(id);
      if (pr != null) {
        publishRelationshipService.delete(id);
      }
      LoggerUtils.deleteLogger(LoggerModule.JXTX, "教学体系——出版社管理——删除（移出）出版社，出版社ID：" + id);
      rs.setMessage("删除成功！");
    } catch (Exception e) {
      rs.setStatusCode(JuiResult.FAILED);
      rs.setMessage("删除失败！");
      logger.error("出版社关联关系删除失败", e);
    }
    return rs;
  }

  /**
   * 刷新元数据缓存信息
   * 
   * @param id
   * @return
   */
  @RequestMapping("/refreshMetaCache")
  public String refreshMetaCache() {
    return "forward:/jy/back/monitor/ehcache/" + MetaConstants.META_CACHE_NAME + "/clear";
  }

}
