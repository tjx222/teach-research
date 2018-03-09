/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.jxtx.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.back.jxtx.service.PublishRelationshipService;
import com.tmser.tr.back.logger.LoggerModule;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.common.vo.Datas;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.PublishRelationship;
import com.tmser.tr.manage.meta.dao.PublishRelationshipDao;

/**
 * <pre>
 * 出版社关联关系管理的service层实现类
 * </pre>
 *
 * @author tmser
 * @version $Id: PublishRelationshipServiceImpl.java, v 1.0 2015-08-27 tmser Exp $
 */
@Service
@Transactional
public class PublishRelationshipServiceImpl extends AbstractService<PublishRelationship, Integer> implements
    PublishRelationshipService {

  @Autowired
  private PublishRelationshipDao publishRelationshipDao;

  /**
   * 通过元数据查询出出版社信息，并进行已存在过滤
   * 
   * @param pr
   * @return
   * @see com.tmser.tr.back.jxtx.service.PublishRelationshipService#findCBSFromSD(com.tmser.tr.back.bo.PublishRelationship)
   */
  @Override
  public List<Meta> findCBSFromSD(PublishRelationship pr) {
    pr.setScope("sys");
    pr.setEnable(1);
    List<PublishRelationship> isExsit = MetaUtils.getPublisherMetaProvider().findList(pr);
    String isExsitStr = ",";
    for (PublishRelationship peTemp : isExsit) {
      isExsitStr += peTemp.getEid() + ",";
    }
    List<Meta> allPublisher = MetaUtils.getPublisherMetaProvider().listAllPublisherMeta();
    List<Meta> returnList = new ArrayList<Meta>();
    if (!",".equals(isExsitStr)) {
      for (Meta im : allPublisher) {
        String sdId = "," + String.valueOf(im.getId()) + ",";
        if (!isExsitStr.contains(sdId)) {
          returnList.add(im);
        }
      }
    } else {
      returnList = allPublisher;
    }
    return returnList;
  }

  /**
   * @return
   * @see com.tmser.tr.common.service.BaseService#getDAO()
   */
  @Override
  public BaseDAO<PublishRelationship, Integer> getDAO() {
    return publishRelationshipDao;
  }

  /**
   * 保存出版社信息
   * 
   * @param pr
   * @param objArr
   * @see com.tmser.tr.back.jxtx.service.PublishRelationshipService#saveCbs(com.tmser.tr.back.bo.PublishRelationship,
   *      java.lang.Object)
   */
  @Override
  public void saveCbs(Datas publishs, PublishRelationship pr) {
    if (pr.getId() != null) {
      publishRelationshipDao.update(pr);
      LoggerUtils.updateLogger(LoggerModule.JXTX, "教学体系——出版社管理——修改出版社关联关系，关联关系ID：" + pr.getId());
    } else {
      List<PublishRelationship> publishList = publishs.getPublishs();
      for (PublishRelationship prTemp : publishList) {
    	prTemp.setScope("sys");
        prTemp.setCrtDttm(new Date());
        prTemp.setEnable(1);
      }
      publishRelationshipDao.batchInsert(publishList);
      LoggerUtils.insertLogger(LoggerModule.JXTX, "教学体系——出版社管理——批量插入出版社关联关系");
    }

  }

}
