/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.yhgl.service;

import java.util.List;
import java.util.Map;

import com.tmser.tr.common.page.PageList;
import com.tmser.tr.uc.bo.Login;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserManagescope;
import com.tmser.tr.uc.bo.UserSpace;

/**
 * <pre>
 * 后台用户管理接口类
 * </pre>
 * 
 * @author zpp
 * @version $Id: BackUserManageService.java, v 1.0 2015年9月24日 下午11:28:09 zpp Exp
 *          $
 */
public interface BackUserManageService {

	/**
	 * 更新学年验证查询
	 * 
	 * @param orgId
	 * @return
	 */
	Map<String, Object> findUpUsers(Integer orgId,Boolean force);

	/**
	 * 保存学年更新
	 * 
	 * @param userIds
	 * @param orgId
	 * @param needUpdateGrade
	 *            是否循环年级
	 */
	String saveUpSchYear(List<Integer> userIds, Integer orgId, boolean needUpdateGrade);

	/**
	 * 获取系统用户集合（分页）
	 * 
	 * @param search_name
	 * @param search_role
	 * @param page
	 * @return
	 */
	PageList<User> getSysUsers(User user);

	/**
	 * 获取区域用户集合（分页）
	 * 
	 * @param search_name
	 * @param search_role
	 * @param page
	 * @return
	 */
	Map<String, Object> getAreaUsers(Integer areaId, User user, Integer roleId);

	/**
	 * 保存用户账号
	 * 
	 * @param login
	 * @param user
	 * @param userType
	 * @return
	 */
	User saveUserAccount(Integer userType, Login login, User user);

	/**
	 * 更新用户账号
	 * 
	 * @param login
	 * @param user
	 */
	void updateUserAccount(Login login, User user);

	/**
	 * 通过机构ID获得其部门、学段、角色信息
	 * 
	 * @param orgId
	 * @return
	 */
	Map<String, Object> findSpaceDataByOrgId(Integer orgId);

	/**
	 * 保存用户角色类型
	 * 
	 * @param us
	 * @param deptIds
	 * @return
	 */
	int saveSchUserRole(UserSpace us, String[] deptIds);

	/**
	 * 授权用户的管理范围
	 * 
	 * @param userId
	 * @param roleId
	 * @param orgIdsStr
	 */
	void savePowerScope(Integer userId, Integer roleId, String orgIdsStr);
	
	/**
	 * 授权用户的管理范围(区域)
	 * 
	 * @param userId
	 * @param roleId
	 * @param orgIdsStr
	 */
	void savePowerScopeArea(Integer userId, Integer roleId, String areaIdsStr);

	/**
	 * 根据用户id获取管理范围
	 * 
	 * @param userId
	 * @return
	 */
	List<UserManagescope> getManagescopeByUserId(Integer userId);

	/**
	 * 用户重置密码
	 * 
	 * @param id
	 */
	void resetUserPass(Integer id);

	/**
	 * 冻结用户的操作
	 * 
	 * @param id
	 * @param enable
	 */
	void djUser(Integer id, Integer enable);

	/**
	 * 查找区域用户身份信息
	 * 
	 * @param userSpa
	 * @return
	 */
	Map<String, Object> findAreaRoleInfo(UserSpace userSpa);

	/**
	 * 保存区域角色类型
	 * 
	 * @param us
	 * @param deptIds
	 */
	void saveAreaUserRole(UserSpace userSpace, Integer editId);

	/**
	 * 查看用户身份详情
	 * 
	 * @param us
	 * @return
	 */
	Map<String, Object> detailUserRole(UserSpace us);

	/**
	 * 删除系统用户
	 * 
	 * @param userId
	 */
	void deleteSysUser(Integer userId);

	/**
	 * 删除用户身份
	 * 
	 * @param us
	 */
	void delUserRole(UserSpace us);

	/**
	 * 增加专家用户空间
	 * 
	 * @param user
	 */
	void saveExpertUserSpace(User user, User newUser);

	/**
	 * 通过us获得身份的相关信息
	 * 
	 * @param us
	 * @return
	 */
	Map<String, Object> findSpaceDataById(UserSpace us);

	/**
	 * 根据角色元数据Id获取空间地址
	 * 
	 * @param sysRoleId
	 * @return
	 */
	String getHomeUrl(Integer sysRoleId);

}
