/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.LoginLog;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.RequestUtils;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.uc.service.LoginLogService;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.uc.dao.LoginLogDao;
/**
 * 登录日志 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: LoginLog.java, v 1.0 2015-05-13 Generate Tools Exp $
 */
@Service
@Transactional
public class LoginLogServiceImpl extends AbstractService<LoginLog, Long> implements LoginLogService {

	@Autowired
	private LoginLogDao loginLogDao;
	
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<LoginLog, Long> getDAO() {
		return loginLogDao;
	}

    /**
     * 记录登录
     * @param userid
     * @param type
     * @return
     */
	@Override
	public void addHistroy(UserSpace cus,int type){
		if(cus == null ) return;
        LoginLog model = new LoginLog();
        model.setUserId(cus.getUserId());
        model.setSpaceId(cus.getId());
        model.setIp(RequestUtils.getIpAddr(WebThreadLocalUtils.getRequest()));
        model.setGradeId(cus.getGradeId());
        model.setSubjectId(cus.getSubjectId());
        model.setType(type);
        model.setSysRoleId(cus.getSysRoleId());
        try{
        	loginLogDao.insert(model);
        }catch(Exception e){
        	logger.info("add login history failed",e);
        }
 	}

	/**
	 * 登录日志展示
	 * @return
	 * @see com.tmser.tr.uc.service.LoginLogService#llist()
	 */
	@Override
	public PageList<LoginLog> getLoginLogList(LoginLog loginLog,List<Integer> ids) {
		UserSpace us=(UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); //用户空间
		Integer sysRoleId = us.getSysRoleId();
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("ids", ids);
		//判断身份进入
		if(sysRoleId.intValue() == SysRole.ADMIN.getId().intValue()){//超级管理员
			List<Integer> roleids = Arrays.asList(new Integer[]{SysRole.ADMIN.getId(),
					SysRole.QYGLY.getId(),SysRole.YWGLY.getId(),SysRole.XXGLY.getId()});
			paramMap.put("roleId", roleids);
            loginLog.addCustomCondition("and sysRoleId in(:roleId) and userId in(:ids)",paramMap);
		}else if(sysRoleId.intValue() == SysRole.YWGLY.getId().intValue()){//运维管理员
			List<Integer> roleids = Arrays.asList(new Integer[]{
					SysRole.QYGLY.getId(),SysRole.YWGLY.getId(),SysRole.XXGLY.getId()});
			paramMap.put("roleId", roleids);
			loginLog.addCustomCondition("and sysRoleId in(:roleId) and userId in(:ids)", paramMap);
		}else{//学校管理员
			loginLog.setSysRoleId(1390);
		}
		
		loginLog.addOrder("loginTime desc");
		PageList<LoginLog> pageList=loginLogDao.listPage(loginLog);
		return pageList;
	}
}
