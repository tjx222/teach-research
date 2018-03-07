/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.logger.dao.impl;

import java.util.List;



import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.tmser.tr.common.dao.AbstractDAO;
import com.tmser.tr.back.logger.bo.OperateLog;
import com.tmser.tr.back.logger.dao.OperateLogDao;

/**
 * 后台操作日志 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: OperateLog.java, v 1.0 2015-09-17 Generate Tools Exp $
 */
@Repository
public class OperateLogDaoImpl extends AbstractDAO<OperateLog,Long> implements OperateLogDao {

	/**
	 * 后台操作日志展示
	 * @param sysRoleIds
	 * @param ids
	 * @return
	 * @see com.tmser.tr.back.logger.dao.OperateLogDao#getOperateLogList(java.lang.String, java.lang.String)
	 */
	@Override
	public List<OperateLog> getOperateLogList(String sysRoleIds, String ids) {
		// TODO Auto-generated method stub
		String sql="select b.id,b.userId,b.createtime,b.ip,b.type,b.message,b.module,u.sysRoleId from OperateLog b LEFT JOIN UserSpace u on b.userId=u.userId where u.sysRoleId in(?)";
		Object []args=new Object[]{sysRoleIds};
		RowMapper<OperateLog> rowMapper=getMapper(); 
		List<OperateLog> oList=query(sql, args, rowMapper);
		return oList;
	}

}
