/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.jxtx.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.back.jxtx.service.JXTXBaseManageService;
import com.tmser.tr.back.logger.LoggerModule;
import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaConstants;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.bo.BookSync;
import com.tmser.tr.manage.meta.service.BookService;

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
public class JCManageController extends AbstractController {

  @Autowired
  private JXTXBaseManageService jXTXBaseManageService;
  @Autowired
  private BookService bookService;

  /**
   * 同步教材入口
   * 
   * @param m
   * @param book
   * @return
   */
  @RequestMapping("/add_jc")
  public String addJc(Model m, Book book) {
    List<Book> booklist = bookService.findBooksOfJys(book);
    m.addAttribute("booklist", booklist);
    m.addAttribute("book", book);
    return viewName("jcgl/add_jc");
  }

  /**
   * 删除教材关联信息
   * 
   * @param id
   * @return
   */
  @RequestMapping("/delete_jc")
  @ResponseBody
  public JuiResult deleteJc(Integer id) {
    JuiResult rs = new JuiResult();
    try {
      bookService.deleteBookSyncById(id);
      LoggerUtils.updateLogger(LoggerModule.JXTX, "教学体系——教材管理——移除选中教材，教材ID：" + id);
      rs.setMessage("移除成功！");
    } catch (Exception e) {
      rs.setStatusCode(JuiResult.FAILED);
      rs.setMessage("移除失败！");
      logger.error("教材移除操作失败", e);
    }
    return rs;
  }

  /**
   * 修改教材入口
   * 
   * @param m
   * @param pr
   * @return
   */
  @RequestMapping("/edit_jc")
  public String editJc(Model m, Integer id) {
    List<Meta> bclist = MetaUtils.getMetaByPid(MetaConstants.BOOKEDTION_METADATA_ID);
    m.addAttribute("bclist", bclist);
    BookSync book = new BookSync();
    book.setId(id);
    book = bookService.findOneBookSync(book);
    m.addAttribute("book", book);
    return viewName("jcgl/edit_jc");
  }

  /**
   * 修改教材关联
   * 
   * @param m
   * @param pr
   * @return
   */
  @RequestMapping("/edit_gl")
  public String editGl(Model m, BookSync book) {
    book = bookService.findOneBookSync(book);
    BookSync bkmodel = new BookSync();
    bkmodel.setGradeLevelId(book.getGradeLevelId());
    bkmodel.setSubjectId(book.getSubjectId());
    bkmodel.setPublisherId(book.getPublisherId());
    bkmodel.setPhaseId(book.getPhaseId());
    List<BookSync> booklist = bookService.findBookSync(bkmodel);
    m.addAttribute("booklist", booklist);
    m.addAttribute("book", book);
    return viewName("jcgl/edit_gl");
  }

  /**
   * 修改教材关联
   * 
   * @param m
   * @param pr
   * @return
   */
  @RequestMapping("/save_gl")
  @ResponseBody
  public JuiResult editGl(Book book) {
    JuiResult rs = new JuiResult();
    try {
      if (StringUtils.isNotEmpty(book.getComId()) && StringUtils.isNotEmpty(book.getRelationComId())) {
        Book b1 = bookService.findOne(book.getComId());
        Book b2 = bookService.findOne(book.getRelationComId());
        if (b1 != null && b2 != null && !b2.getComId().equals(b1.getRelationComId())) {
          b1.setGradeLevelId(book.getGradeLevelId());
          bookService.addRelation(b2, b1);
        }
        rs.setMessage("关联成功！");
      } else {
        rs.setStatusCode(JuiResult.FAILED);
        rs.setMessage("关联失败！");
      }
    } catch (Exception e) {
      rs.setStatusCode(JuiResult.FAILED);
      rs.setMessage("关联失败！");
      logger.error("关联关系保存失败", e);
    }
    return rs;
  }

  /**
   * 自动教材关联
   * 
   * @param m
   * @param pr
   * @return
   */
  @RequestMapping("/auto_gl")
  @ResponseBody
  public JuiResult autoGl(Book model) {
    JuiResult rs = JuiResult.forwardInstance();
    if (model.getSubjectId() != null && model.getPhaseId() != null && model.getPublisherId() != null
        && null != model.getGradeLevelId()) {
      model.setComType(1);
      model.setSaleType(2);
      List<Book> books = bookService.findAll(model);
      for (Book bk : books) {
        if (StringUtils.isEmpty(bk.getRelationComId()) && bk.getFasciculeId() != 178) {
          for (Book k : books) {
            if (StringUtils.isEmpty(k.getRelationComId()) && k.getBookEdtionId().equals(bk.getBookEdtionId())
                && !k.getFasciculeId().equals(bk.getFasciculeId())) {
              bookService.addRelation(bk, k);
            }
          }
        }
      }
    }
    return rs;
  }

  /**
   * 教材管理入口地址
   * 
   * @param m
   * @return
   */
  @RequestMapping("/jcgl_index")
  public String jcglIndex(Model m) {
    List<Map<String, Object>> list = jXTXBaseManageService.findJCtree();
    m.addAttribute("list", list);
    return viewName("jcgl/tree_index");
  }

  /**
   * 教材管理列表
   * 
   * @param m
   * @param xdid
   * @param xkid
   * @return
   */
  @RequestMapping("/jcgl_list")
  public String jcglList(Model m, BookSync book) {
    m.addAttribute("book", book);
    List<BookSync> booklist = bookService.findBookSync(book);
    m.addAttribute("booklist", booklist);
    return viewName("jcgl/jc_index");
  }

  /**
   * 保存教材信息
   * 
   * @param books
   * @param book
   * @return
   */
  @RequestMapping("/save_jc")
  @ResponseBody
  public JuiResult saveJc(String[] bookAddIds, BookSync book) {
    JuiResult rs = new JuiResult();
    try {
      if (StringUtils.isNotEmpty(book.getComId())) {
        rs.setMessage("修改成功！");
      } else {
        rs.setMessage("保存成功！");
      }
      bookService.saveJc(bookAddIds, book);
      rs.setStatusCode(JuiResult.SUCCESS);
    } catch (Exception e) {
      rs.setStatusCode(JuiResult.FAILED);
      if (StringUtils.isNotEmpty(book.getComId())) {
        rs.setMessage("修改失败！");
      } else {
        rs.setMessage("保存失败！");
      }
      logger.error("级联关系保存失败", e);
    }

    return rs;
  }
}
