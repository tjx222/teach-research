/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.meta.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.websocket.EncodeException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.back.logger.LoggerModule;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.manage.meta.bo.BookChapter;
import com.tmser.tr.manage.meta.dao.BookChapterDao;
import com.tmser.tr.manage.meta.dao.BookDao;
import com.tmser.tr.manage.meta.service.BookChapterService;
import com.tmser.tr.utils.Identities;

/**
 * 书籍的章节目录 服务实现类
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: CommdityBookChapter.java, v 1.0 2015-02-04 Generate Tools Exp $
 */
@Service
@Transactional
public class BookChapterServiceImpl extends AbstractService<BookChapter, String> implements BookChapterService {

  @Autowired
  private BookChapterDao commdityBookChapterDao;
  @Autowired
  private BookDao bookDao;

  /**
   * @return
   * @see com.tmser.tr.common.service.BaseService#getDAO()
   */
  @Override
  public BaseDAO<BookChapter, String> getDAO() {
    return commdityBookChapterDao;
  }

  @Override
  public List<BookChapter> listBookChapterWithChildState(String comId, String parentId) {
    return commdityBookChapterDao.listBookChapterWithChildState(comId, parentId);
  }

  /**
   * 保存章节目录数据
   * 
   * @param bc
   * @throws SQLException
   * @throws EncodeException
   * @see com.tmser.tr.manage.meta.service.BookChapterService#save_up_catalog(com.tmser.tr.manage.meta.bo.BookChapter)
   */
  @Override
  public void saveUpCatalog(BookChapter bc) throws Exception {
    if (StringUtils.isNotEmpty(bc.getChapterId())) {// 修改章节目录
      LoggerUtils.updateLogger(LoggerModule.JXTX, "教学体系——目录管理——修改目录，目录ID：" + bc.getChapterId());
      commdityBookChapterDao.update(bc);
    } else {// 新增章节目录
      // 处理章节的码字
      String chapterId = Identities.uuid2();
      bc.setChapterId(chapterId);
      // 设置章节编码
      // String parentId = bc.getParentId();//父类chapterId
      // Integer orderNum = bc.getOrderNum();//当前的排序值
      // List<ChapterOrder> ordersChain =
      // jymbservice.getChapterOrderUtils().getOrderChainByChapterIndex(parentId);
      // ChapterEncodeItems chapterEncodeItems =
      // ChapterEncodeItemsBuilder.buildByParentOrderChain(orderNum,
      // book.getEduCode(), ordersChain);
      // String chapterCode =
      // jymbservice.getEncodeService().encode(chapterEncodeItems);
      // bc.setEduCode(chapterCode);

      bc = commdityBookChapterDao.insert(bc);
      LoggerUtils.insertLogger(LoggerModule.JXTX, "教学体系——目录管理——插入目录，目录ID：" + bc.getChapterId());
    }

  }

}
