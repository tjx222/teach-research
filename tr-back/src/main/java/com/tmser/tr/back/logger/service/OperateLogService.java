/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.logger.service;

import java.util.List;

import com.tmser.tr.back.logger.bo.OperateLog;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.BaseService;

/**
 * 后台操作日志 服务类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: OperateLog.java, v 1.0 2015-09-17 Generate Tools Exp $
 */

public interface OperateLogService extends BaseService<OperateLog, Long>{
    /**
     * 后台操作日志展示
     * 
	*/
	public PageList<OperateLog> getOperateLogIndex(OperateLog operateLog,List<Integer> ids);
	
	/**
     * 后台操作日志给集合加入sysRoleId元素集合
     * 
	*/
	public List<OperateLog> getSysRoleIdList(PageList<OperateLog> pageList);
}
