/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.logger.dao;

import java.util.List;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.back.logger.bo.OperateLog;

 /**
 * 后台操作日志 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: OperateLog.java, v 1.0 2015-09-17 Generate Tools Exp $
 */
public interface OperateLogDao extends BaseDAO<OperateLog, Long>{
   /**
    * 后台操作日志展示
	*/
	public List<OperateLog> getOperateLogList(String sysRoleIds,String ids);
}