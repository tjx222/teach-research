/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.jxtx.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.back.jxtx.service.JXTXBaseManageService;
import com.tmser.tr.common.utils.WebUtils;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.bo.BookChapter;
import com.tmser.tr.manage.meta.bo.BookSync;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.meta.bo.PublishRelationship;
import com.tmser.tr.manage.meta.service.BookChapterService;
import com.tmser.tr.manage.meta.service.BookService;
import com.tmser.tr.utils.FileUtils;
import com.tmser.tr.utils.ZipUtils;

/**
 * <pre>
 * 教学体系基础数据关系维护service实现类
 * </pre>
 * 
 * @author zpp
 * @version $Id: JXTXBaseManageServiceImpl.java, v 1.0 2015年8月24日 下午4:21:53 zpp
 *          Exp $
 */
@Service
@Transactional
public class JXTXBaseManageServiceImpl implements JXTXBaseManageService {
  private final static Logger logger = LoggerFactory.getLogger(JXTXGradeManageServiceImpl.class);

  @Autowired
  private BookChapterService bookChapterService;

  @Autowired
  private BookService bookService;

  private final static Pattern FILTER_PATTERN = Pattern.compile("[\\*<>\\\\\\/\\?\\|:\"]");

  protected boolean createAndDownRes(String bookId, String parentId, File banben) {
    List<BookChapter> chapters = bookChapterService.listBookChapterWithChildState(bookId, parentId);
    boolean rs = false;
    try {
      if (chapters != null && chapters.size() > 0) {
        for (BookChapter bc : chapters) {
          // ##是有子文件夹的，#&是没有子文件夹的
          File bcFile = null;
          boolean hasChild = Boolean.valueOf(bc.getFlago());
          if (hasChild) {
            String nm = bc.getChapterId();
            Integer index = nm.length() - 4 > 0 ? nm.length() - 4 : 0;
            bcFile = new File(banben, filterFilename(bc.getChapterName()) + "+(##" + nm.substring(index) + ")");
          } else {
            bcFile = new File(banben, filterFilename(bc.getChapterName()) + "+(#&" + bc.getChapterId() + ")");
          }

          if (bcFile.mkdirs()) {
            if (hasChild) {
              createAndDownRes(bookId, bc.getChapterId(), bcFile);
            }
          }
        }
        rs = true;
      }
    } catch (Exception e) {
      logger.error("download resource failed！", e);
    }
    return rs;
  }

  /**
   * 删除下载成功的目录节点信息
   * 
   * @param comId
   * @see com.tmser.tr.back.jxtx.service.JXTXBaseManageService#delExportChapter(java.lang.String)
   */
  @Override
  public void delExportChapter(String comId) {
    File f = new File(WebUtils.getBackRootPath(), "upload/bookchapter/" + comId);
    if (f.exists()) {
      FileUtils.delFile(f.getAbsolutePath());
    }
  }

  /**
   * 导出目录信息
   * 
   * @param comId
   * @param response
   * @throws Exception
   * @see com.tmser.tr.back.jxtx.service.JXTXBaseManageService#exportChapter(java.lang.String,
   *      javax.servlet.http.HttpServletResponse)
   */
  @Override
  public File exportChapter(String bookId) {
    Book book = bookService.findOne(bookId);
    if (book != null) {
      File f = new File(WebUtils.getBackRootPath(), "upload/bookchapter/" + bookId);
      if (!f.exists()) {// 不存在此目录
        f.mkdirs();// 创建此目录
      }
      File banben = new File(f, filterFilename(book.getComName()));
      if (!banben.exists()) {// 书籍的根目录文件夹不存在
        banben.mkdirs();// 创建书籍的根目录文件夹
      }
      if (createAndDownRes(bookId, "-1", banben)) {
        logger.info("目录创建完成！");
        // 将目录文件打包下载
        try {
          return ZipUtils.compress(banben);
        } catch (Exception e) {
          logger.error("--文件压缩下载失败--", e);
        }
      }
    }
    return null;
  }

  protected String filterFilename(String oldName) {
    String s = oldName;
    if (!StringUtils.isEmpty(oldName)) {
      Matcher m = FILTER_PATTERN.matcher(oldName);
      StringBuffer sb = new StringBuffer();
      while (m.find()) {
        m.appendReplacement(sb, "");
      }
      m.appendTail(sb);
      s = sb.toString().trim();
    }
    return s;
  }

  /**
   * 查询学段学科树形结构
   * 
   * @return
   * @see com.tmser.tr.back.jxtx.service.JXTXBaseManageService#findXDXKtree()
   */
  @Override
  public List<Map<String, Object>> findXDXKtree() {
    List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
    List<MetaRelationship> xdlist = MetaUtils.getPhaseMetaProvider().listAll();
    for (MetaRelationship mrTemp : xdlist) {
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("id", mrTemp.getId());
      map.put("name", mrTemp.getName());
      MetaRelationship xdxk = MetaUtils.getPhaseSubjectMetaProvider().getMetaRelationshipByPhaseId(mrTemp.getId());
      if (xdxk != null) {
        String ids_xk = xdxk.getIds();
        if (StringUtils.isNotEmpty(ids_xk)) {
          String[] split = ids_xk.split(",");
          List<Meta> xklist = new ArrayList<Meta>();
          for (String str : split) {
            if (StringUtils.isNotEmpty(str)) {
              Meta meta = MetaUtils.getPhaseMetaProvider().getMeta(Integer.parseInt(str));
              xklist.add(meta);
            }
          }
          map.put("child", xklist);
        }
      }
      resultMap.add(map);
    }
    return resultMap;
  }

  /**
   * 查询封装教材目录树形结构数据
   * 
   * @return
   * @see com.tmser.tr.back.jxtx.service.JXTXBaseManageService#findCatalogTree()
   */
  @Override
  public List<Map<String, Object>> findCatalogTree() {
    List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
    List<MetaRelationship> xdlist = MetaUtils.getPhaseMetaProvider().listAll();
    for (MetaRelationship mrTemp : xdlist) {// 最外层学段
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("name", mrTemp.getName());
      MetaRelationship xdxk = MetaUtils.getPhaseSubjectMetaProvider().getMetaRelationshipByPhaseId(mrTemp.getId());// 学段学科对象
      MetaRelationship xdnj = MetaUtils.getPhaseGradeMetaProvider().getMetaRelationshipByPhaseId(mrTemp.getId());// 学段年级对象
      List<Map<String, String>> xdnjlist = new ArrayList<Map<String, String>>();
      if (xdnj != null) {
        String njids = xdnj.getIds();
        if (StringUtils.isNotEmpty(njids)) {
          String[] njidsArr = njids.split(",");
          String[] njnameArr = xdnj.getDescs().split(",");
          for (int i = 0; i < njidsArr.length; i++) {
            Map<String, String> obj = new HashMap<String, String>();
            obj.put("id", njidsArr[i]);
            obj.put("name", njnameArr[i]);
            xdnjlist.add(obj);
          }
          map.put("njlist", xdnjlist);
        }
      }
      if (xdxk != null) {
        String ids_xk = xdxk.getIds();
        String xk_name = xdxk.getDescs();
        if (StringUtils.isNotEmpty(ids_xk)) {
          String[] xkIdArr = ids_xk.split(",");
          String[] xkNameArr = xk_name.split(",");
          List<Map<String, Object>> child1 = new ArrayList<Map<String, Object>>();
          for (int i = 0; i < xkIdArr.length; i++) {// 次外层学科（第二层）
            Map<String, Object> map2 = new HashMap<String, Object>();
            map2.put("name", xkNameArr[i]);
            PublishRelationship pr = new PublishRelationship();
            pr.setPhaseId(mrTemp.getId());
            pr.setSubjectId(Integer.parseInt(xkIdArr[i]));
            pr.setScope("sys");
            pr.setEnable(1);
            pr.addCustomCulomn("eid,shortName");
            List<PublishRelationship> cbslist = MetaUtils.getPublisherMetaProvider().findList(pr);
            if (cbslist != null && cbslist.size() > 0) {
              List<Map<String, Object>> child2 = new ArrayList<Map<String, Object>>();
              for (PublishRelationship prt : cbslist) {// 第三层出版社
                Map<String, Object> map3 = new HashMap<String, Object>();
                map3.put("name", prt.getShortName());
                if (xdnjlist != null && xdnjlist.size() > 0) {
                  List<Map<String, Object>> child3 = new ArrayList<Map<String, Object>>();
                  for (Map<String, String> grade : xdnjlist) {// 第四层年级
                    Map<String, Object> map4 = new HashMap<String, Object>();
                    map4.put("name", grade.get("name"));
                    BookSync book = new BookSync();
                    book.setPhaseId(mrTemp.getEid());// 学段元数据ID
                    book.setSubjectId(Integer.parseInt(xkIdArr[i]));
                    book.setPublisherId(prt.getEid());
                    book.setGradeLevelId(Integer.parseInt(grade.get("id")));
                    book.addCustomCulomn("comId,comName");
                    book.addOrder("comOrder asc");
                    List<BookSync> list = bookService.findBookSync(book);
                    map4.put("child", list);
                    child3.add(map4);
                  }
                  map3.put("child", child3);
                }
                child2.add(map3);
              }
              map2.put("child", child2);
            }
            child1.add(map2);
          }
          map.put("child", child1);
        }
      }
      resultMap.add(map);
    }
    return resultMap;
  }

  /**
   * @return
   * @see com.tmser.tr.back.jxtx.service.JXTXBaseManageService#findBookCatalogTree()
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<Map<String, Object>> findBookCatalogTree(String comId) {
    BookChapter bc = new BookChapter();
    bc.setComId(comId);
    bc.addCustomCulomn("chapterId,chapterName,parentId,orderNum,chapterLevel");
    bc.addOrder("chapterLevel asc,orderNum asc");
    List<BookChapter> listone = bookChapterService.findAll(bc);
    List<Map<String, Object>> returnMap = new ArrayList<Map<String, Object>>();

    Map<String, Object> top = new HashMap<String, Object>();
    for (BookChapter bcone : listone) {
      Map<String, Object> mapone = new HashMap<String, Object>();
      mapone.put("id", bcone.getChapterId());
      mapone.put("name", bcone.getChapterName());
      mapone.put("parentId", bcone.getParentId());
      mapone.put("orderNum", bcone.getOrderNum());
      mapone.put("chapterLevel", bcone.getChapterLevel());
      mapone.put("child", null);
      if ("-1".equals(bcone.getParentId())) {
        top.put(bcone.getChapterId(), mapone);
        returnMap.add(mapone);
      } else {
        Object parent = top.get(bcone.getParentId());
        if (parent instanceof Map) {
          Object childs = ((Map<String, Object>) parent).get("child");
          if (childs == null) {
            List<Map<String, Object>> childlist = new ArrayList<Map<String, Object>>();
            childlist.add(mapone);
            ((Map<String, Object>) parent).put("child", childlist);
          } else {
            List<Map<String, Object>> childlist = (List<Map<String, Object>>) ((Map<String, Object>) parent)
                .get("child");
            childlist.add(mapone);
          }
          top.put(bcone.getChapterId(), mapone);
        }
      }
    }
    return returnMap;
  }

  // 打包下载
  public void zipFile(File[] subs, String baseName, ZipOutputStream zos) throws IOException {
    for (int i = 0; i < subs.length; i++) {
      File f = subs[i];
      zos.putNextEntry(new ZipEntry(baseName + f.getName()));
      FileInputStream fis = new FileInputStream(f);
      byte[] buffer = new byte[1024];
      int r = 0;
      while ((r = fis.read(buffer)) != -1) {
        zos.write(buffer, 0, r);
      }
      fis.close();
      zos.flush();
      zos.close();
    }
  }

  /**
   * 查找教材管理的树形数据
   * 
   * @return
   * @see com.tmser.tr.back.jxtx.service.JXTXBaseManageService#findJCtree()
   */
  @Override
  public List<Map<String, Object>> findJCtree() {
    List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();

    List<MetaRelationship> xdlist = MetaUtils.getPhaseMetaProvider().listAll();
    for (MetaRelationship mrTemp : xdlist) {
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("id", mrTemp.getId());
      map.put("eid", mrTemp.getEid());
      map.put("name", mrTemp.getName());
      MetaRelationship xdxk = MetaUtils.getPhaseSubjectMetaProvider().getMetaRelationshipByPhaseId(mrTemp.getId());
      if (xdxk != null) {
        String ids_xk = xdxk.getIds();
        String xk_name = xdxk.getDescs();
        if (StringUtils.isNotEmpty(ids_xk)) {
          String[] split = ids_xk.split(",");
          String[] nameArr = xk_name.split(",");
          List<Map<String, Object>> xklist = new ArrayList<Map<String, Object>>();
          for (int i = 0; i < split.length; i++) {
            Map<String, Object> map2 = new HashMap<String, Object>();
            map2.put("id", split[i]);
            map2.put("name", nameArr[i]);
            PublishRelationship pr = new PublishRelationship();
            pr.setPhaseId(mrTemp.getId());
            pr.setSubjectId(Integer.parseInt(split[i]));
            pr.setScope("sys");
            pr.addCustomCulomn("eid,shortName");
            List<PublishRelationship> cbslist = MetaUtils.getPublisherMetaProvider().findList(pr);
            map2.put("child", cbslist);
            xklist.add(map2);
          }
          map.put("child", xklist);
        }
      }
      resultMap.add(map);
      MetaRelationship xdnj = MetaUtils.getPhaseGradeMetaProvider().getMetaRelationshipByPhaseId(mrTemp.getId());
      if (xdnj != null) {
        String njids = xdnj.getIds();
        if (StringUtils.isNotEmpty(njids)) {
          String[] njidsArr = njids.split(",");
          String[] njnameArr = xdnj.getDescs().split(",");
          List<Map<String, Object>> xdnjlist = new ArrayList<Map<String, Object>>();
          for (int i = 0; i < njidsArr.length; i++) {
            Map<String, Object> obj = new HashMap<String, Object>();
            obj.put("id", njidsArr[i]);
            obj.put("name", njnameArr[i]);
            xdnjlist.add(obj);
          }
          map.put("njlist", xdnjlist);
        }
      }
    }
    return resultMap;
  }

  @Override
  public List<BookChapter> findBookCatalogTreeForZtree(String comId) {
    BookChapter bc = new BookChapter();
    bc.setComId(comId);
    bc.addCustomCulomn("chapterId,chapterName,parentId,orderNum,chapterLevel");
    bc.addOrder("chapterLevel asc,orderNum asc");
    return bookChapterService.findAll(bc);
  }

}
