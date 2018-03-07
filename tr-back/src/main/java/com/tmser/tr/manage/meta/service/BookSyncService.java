package com.tmser.tr.manage.meta.service;

import java.util.List;

import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.manage.meta.bo.BookSync;

public interface BookSyncService extends BaseService<BookSync, Integer> {
  /**
   * 获取学校配置的BookSync
   * 
   * @param orgId
   *          学校id
   * @param areaIds
   *          学校上级区域ids
   * @param gradeId
   *          年级id
   * @param subjectId
   *          学科id
   * @param phaseId
   *          学段id
   * @param publisherId
   *          出版社id
   * @return list of BookSync
   */
  List<BookSync> getBookSyncByOrg(Integer orgId, List<Integer> areaIds, Integer gradeId, Integer subjectId,
      Integer phaseId, Integer publisherId);

}
