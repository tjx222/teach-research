/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.logger.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.back.logger.bo.OperateLog;
import com.tmser.tr.back.logger.dao.OperateLogDao;
import com.tmser.tr.back.logger.service.OperateLogService;
import com.tmser.tr.common.bo.QueryObject;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.SessionKey;
/**
 * 后台操作日志 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: OperateLog.java, v 1.0 2015-09-17 Generate Tools Exp $
 */
@Service
@Transactional
public class OperateLogServiceImpl extends AbstractService<OperateLog, Long> implements OperateLogService {

	@Autowired
	private OperateLogDao operateLogDao;
	
	@Autowired 
	private  UserSpaceService  userSpaceService;
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<OperateLog, Long> getDAO() {
		return operateLogDao;
	}

	/**
	 * 后台 日志展示
	 * @param operateLog
	 * @param ids
	 * @return
	 * @see com.tmser.tr.back.logger.service.OperateLogService#getOperateLogIndex(com.tmser.tr.back.logger.bo.OperateLog, java.lang.String)
	 */
	@Override
	public PageList<OperateLog> getOperateLogIndex(OperateLog operateLog, List<Integer> ids) {
		// TODO Auto-generated method stub
		UserSpace us=(UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); //用户空间
		Integer sysRoleId=us.getSysRoleId();
		operateLog.addOrder("createtime desc");
		operateLog.addAlias("o");
		operateLog.addCustomCulomn("o.id,o.userId,o.module,o.type,o.createtime,o.message");
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("ids", ids);
		
		//判断身份进入
		if(sysRoleId.intValue() == SysRole.ADMIN.getId().intValue()){//超级管理员
			List<Integer> roleids = Arrays.asList(new Integer[]{SysRole.ADMIN.getId(),
					SysRole.QYGLY.getId(),SysRole.YWGLY.getId(),SysRole.XXGLY.getId()});
			paramMap.put("roleId", roleids);
			operateLog.addJoin(QueryObject.JOINTYPE.LEFT, "UserSpace u").on("o.userId=u.userId");
			operateLog.addCustomCondition("and u.sysRoleId in(:roleId) and o.userId in(:ids)",paramMap);
		}else if(sysRoleId.intValue() == SysRole.YWGLY.getId().intValue()){//运维管理员
			List<Integer> roleids = Arrays.asList(new Integer[]{
					SysRole.QYGLY.getId(),SysRole.YWGLY.getId(),SysRole.XXGLY.getId()});
			paramMap.put("roleId", roleids);
			operateLog.addJoin(QueryObject.JOINTYPE.LEFT, "UserSpace u").on("o.userId=u.userId");
			operateLog.addCustomCondition("and u.sysRoleId in(:roleId) and o.userId in(:ids)", paramMap);
		}else if(sysRoleId.intValue()==SysRole.QYGLY.getId().intValue()){//区域管理员
			paramMap.put("roleId", SysRole.QYGLY.getId());
			operateLog.addJoin(QueryObject.JOINTYPE.LEFT, "UserSpace u").on("o.userId=u.userId");
			operateLog.addCustomCondition("and u.sysRoleId=:roleId and o.userId in(:ids)", paramMap);
		}else if(sysRoleId.intValue()==SysRole.XXGLY.getId().intValue()){//学校管理员
			paramMap.put("roleId", SysRole.XXGLY.getId());
			operateLog.addJoin(QueryObject.JOINTYPE.LEFT, "UserSpace u").on("o.userId=u.userId");
			operateLog.addCustomCondition("and u.sysRoleId=:roleId and o.userId in(:ids)", paramMap);
		}
		return operateLogDao.listPage(operateLog);
	}

	/**
	 * @param pageList
	 * @return
	 * @see com.tmser.tr.back.logger.service.OperateLogService#getSysRoleIdList(com.tmser.tr.common.page.PageList)
	 */
	@Override
	public List<OperateLog> getSysRoleIdList(PageList<OperateLog> pageList) {
		List<OperateLog> oList=new ArrayList<OperateLog>();
		if (pageList.getDatalist().size()!=0) {
			for (OperateLog oLog:pageList.getDatalist()) {
				UserSpace userSpace=new UserSpace();
				userSpace.setUserId(oLog.getUserId());
				List<UserSpace> uslist=userSpaceService.find(userSpace, 1);
				for (UserSpace us:uslist) {
					oLog.setSysRoleId(us.getSysRoleId());
					oList.add(oLog);	
				}
			}
		}
		return oList;
	}

}
