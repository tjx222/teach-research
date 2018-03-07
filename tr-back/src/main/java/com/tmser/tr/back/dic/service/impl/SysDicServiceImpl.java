/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */

package com.tmser.tr.back.dic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.back.dic.service.SysDicService;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.manage.meta.bo.SysDic;
import com.tmser.tr.manage.meta.dao.SysDicDao;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 3020mt
 * @version $Id: SysDicServiceImpl.java, v 1.0 2017年12月13日 下午4:42:20 3020mt Exp $
 */
@Service
@Transactional
public class SysDicServiceImpl extends AbstractService<SysDic, Integer> implements SysDicService {
  @Autowired
  private SysDicDao sysDicDao;

  /**
   * @return
   * @see com.tmser.tr.common.service.BaseService#getDAO()
   */
  @Override
  public BaseDAO<SysDic, Integer> getDAO() {
    return sysDicDao;
  }

}
