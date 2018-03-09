package com.tmser.tr.manage.meta.dao;

import java.util.Collection;
import java.util.List;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.manage.meta.bo.SysDic;

/**
 * 元数据表DAO接口
 * @author tmser
 * @version 1.0
 * 2015-01-21
 */
public interface SysDicDao extends BaseDAO<SysDic, Integer>{
  
  List<SysDic> listByIds(Collection<Integer> ids);

}