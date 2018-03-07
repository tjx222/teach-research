/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.service;

import java.util.Map;

import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.vo.UserSearchModel;

/**
 * 用户信息 服务类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: User.java, v 1.0 2015-01-31 Generate Tools Exp $
 */

public interface UserService extends BaseService<User, Integer>{
    //上传头像
	public  void modifyPhoto(String photoPath);
	
	/**
	 * 根据用户名判断用户是否已经存在(同一个机构下的)
	 * @param userName
	 * @param orgId 
	 * @return
	 */
	public User getUserByName(String userName, Integer orgId);

	/**
	 * 后台用户展示
	 * @param orgId
	 * @return
	 */
	PageList<Map<String,Object>> findUsersBySearchModel(UserSearchModel usm);
	
	/**
	 * 根据用户类型创建新的用户
	 * @param userType
	 * @return
	 */
	User newUser(Integer id,Integer userType);

}
