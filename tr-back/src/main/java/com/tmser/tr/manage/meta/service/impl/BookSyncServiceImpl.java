package com.tmser.tr.manage.meta.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.manage.meta.bo.BookSync;
import com.tmser.tr.manage.meta.dao.BookSyncDao;
import com.tmser.tr.manage.meta.service.BookSyncService;

@Service
@Transactional
public class BookSyncServiceImpl extends AbstractService<BookSync, Integer> implements BookSyncService {

  @Autowired
  private BookSyncDao bookSyncDao;

  @Override
  public BaseDAO<BookSync, Integer> getDAO() {
    return bookSyncDao;
  }

  @Override
  public List<BookSync> getBookSyncByOrg(Integer orgId, List<Integer> areaIds, Integer gradeId, Integer subjectId,
      Integer phaseId, Integer publisherId) {
    return bookSyncDao.getBookSyncsByOrg(orgId, areaIds, gradeId, subjectId, phaseId, publisherId);

  }

}
