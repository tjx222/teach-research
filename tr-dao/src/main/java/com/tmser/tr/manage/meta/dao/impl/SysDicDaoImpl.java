package com.tmser.tr.manage.meta.dao.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.tmser.tr.common.dao.AbstractDAO;
import com.tmser.tr.common.dao.annotation.UseCache;
import com.tmser.tr.manage.meta.bo.SysDic;
import com.tmser.tr.manage.meta.dao.SysDicDao;

/**
 * 元数据表 Dao 实现类
 * @author tmser
 * @version 1.0
 * 2015-01-21
 */
@Repository
@UseCache
public class SysDicDaoImpl extends AbstractDAO<SysDic,Integer> implements SysDicDao {

  @Override
  public List<SysDic> listByIds(Collection<Integer> ids) {
    String sql = "select * from SysDic where id in (:ids)";
    Map<String, Object> args = new HashMap<>();
    args.put("ids", ids);
    return this.queryByNamedSql(sql, args, getMapper());
  }

}
