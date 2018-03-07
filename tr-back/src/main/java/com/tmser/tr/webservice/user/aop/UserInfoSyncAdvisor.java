/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.webservice.user.aop;


import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import com.tmser.tr.uc.bo.Login;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.dao.LoginDao;
import com.tmser.tr.uc.dao.UserDao;
import com.tmser.tr.webservice.user.data.UserInfo;
import com.tmser.tr.webservice.user.service.UserInfoService;

/**
 * <pre>
 *	监听用户信息更新，同步到优课平台
 *  依赖 @see MapperContainer
 * </pre>
 *
 * @author yc
 * @version $Id: BaseDaoCacheInterceptor.java, v 1.0 2015年2月16日 下午1:44:45 tmser Exp $
 */
public class UserInfoSyncAdvisor {
	@Autowired
	UserInfoService  userInfoService;
	@Autowired
	private LoginDao loginDao;
	@Autowired
	private UserDao userDao;
	
	private UserInfo oldUserInfo;
	private UserInfo newUserInfo;
	
	public void beforeSyncUserInfo(JoinPoint jp){
		try{
			//切面入口即更新了用户账号信息
			jp.getThis();
			Object[] args=jp.getArgs();
			if(args!=null&&args.length>0){
				Login login=(Login) args[0];
				newUserInfo=new UserInfo(); 
				newUserInfo.setAccount(login.getLoginname());
				newUserInfo.setCallphone(login.getCellphone());
				newUserInfo.setMail(login.getMail());
				newUserInfo.setPassword(login.getPassword());
				Login oldLogin=loginDao.get(login.getId());
				User user=userDao.get(login.getId());
				oldUserInfo=new UserInfo();
				oldUserInfo.setAccount(oldLogin.getLoginname());
				oldUserInfo.setPassword(oldLogin.getPassword());
				oldUserInfo.setAppKey(user.getAppId());
			}
			
		}catch(Exception e){
			//切面异常避免影响原有代码逻辑
			//账号信息同步允许失败
			e.printStackTrace();
		}
	}
	public void syncUserInfo(JoinPoint jp){
		try{
			//切面入口即更新了用户账号信息
			if(oldUserInfo!=null&&newUserInfo!=null){
				//判断是否是升级账号
				if(oldUserInfo.getAppKey()==UserInfo.AppId_jiaoyan||oldUserInfo.getAppKey()==UserInfo.AppId_youke){
					//判断是否需要更新
					if(newUserInfo.getMail()!=null||newUserInfo.getPassword()!=null||newUserInfo.getCallphone()!=null){
						//更新用户信息
						userInfoService.updateUserInfoToUclass(oldUserInfo, newUserInfo);
					}
				}
			}
			
		}catch(Exception e){
			//切面异常避免影响原有代码逻辑
			//账号信息同步允许失败
			e.printStackTrace();
		}
	}
	
	public UserInfo getOldUserInfo() {
		return oldUserInfo;
	}
	public void setOldUserInfo(UserInfo oldUserInfo) {
		this.oldUserInfo = oldUserInfo;
	}
	public UserInfo getNewUserInfo() {
		return newUserInfo;
	}
	public void setNewUserInfo(UserInfo newUserInfo) {
		this.newUserInfo = newUserInfo;
	}
	
}
