/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.annunciate.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tmser.tr.annunciate.bo.JyRedTitle;
import com.tmser.tr.annunciate.service.JyRedTitleService;
import com.tmser.tr.common.annotation.UseToken;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.uc.utils.CurrentUserContext;

/**
 * 红头文件表头控制器接口
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: JyRedTitle.java, v 1.0 2015-06-12 Generate Tools Exp $
 */

@Controller
@RequestMapping("/jy/annunciate")
public class JyRedTitleController extends AbstractController {

  @Autowired
  private JyRedTitleService jyRedTitleService;

  /**
   * 获取用户可查看到的所有红头文件标题
   * 
   * @return
   */
  @RequestMapping("/redTitles")
  @UseToken
  public String getReaTitles(Model m) {
    JyRedTitle search = new JyRedTitle();
    search.addOrder("crtDttm desc");
    search.setIsEnable(1);
    search.setIsDelete(0);
    List<JyRedTitle> list = jyRedTitleService.findAll(search);
    m.addAttribute("list", list);
    return viewName("annunciate_redhead");
  }

  /**
   * 新增红头文件标题
   * 
   * @param title
   * @return
   */
  @RequestMapping(value = "addredTitles", method = RequestMethod.POST)
  @UseToken
  public Result addReaTitle(@Valid JyRedTitle title, Model model) {
    Result result = new Result();
    try {
      if (title.getId() != null) {
        title.setLastupId(CurrentUserContext.getCurrentUserId());
        title.setLastupDttm(new Date());
        jyRedTitleService.update(title);
      } else {
        title.setIsEnable(1);
        title.setIsDelete(0);
        title.setOrgId(CurrentUserContext.getCurrentUser().getOrgId());
        title.setCrtId(CurrentUserContext.getCurrentUserId());
        title.setCrtDttm(new Date());
        title.setLastupId(CurrentUserContext.getCurrentUserId());
        title.setLastupDttm(new Date());
        jyRedTitleService.save(title);
      }
      result.setCode(1);
    } catch (Exception e) {
      // TODO: handle exception
      logger.error("红头保存失败");
      result.setCode(0);
    }
    return result;
  }

  /**
   * 删除红头文件标题
   * 
   * @param id
   * @return
   */
  @RequestMapping(value = "deleteredTitles")
  public Result deleteReaTitle(Integer id) {
    Result result = new Result();
    try {
      if (id != null) {
        JyRedTitle jyRedTitle = jyRedTitleService.findOne(id);
        jyRedTitle.setIsDelete(1);
        jyRedTitleService.update(jyRedTitle);
        result.setCode(1);
      }
    } catch (Exception e) {
      // TODO: handle exception
      logger.error("删除失败", e);
      result.setCode(0);
    }
    return result;
  }

  public void setJyRedTitleService(JyRedTitleService jyRedTitleService) {
    this.jyRedTitleService = jyRedTitleService;
  }
}