/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.service;

import java.util.List;

import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.common.service.BaseService;

/**
 * 用户空间信息 服务类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: UserSpace.java, v 1.0 2015-02-03 Generate Tools Exp $
 */

public interface UserSpaceService extends BaseService<UserSpace, Integer>{

	/**
	 * 通过学科集合和年级集合查询用户集合
	 * @param subjectIds
	 * @param gradeIds
	 * @return
	 */
	List<UserSpace> findUserBySubjectAndGrade(String subjectIds, String gradeIds);

	/**
	 * 通过用户姓名、角色、学科、年级判断空间是否已经存在(同一个机构下)
	 * @param userName
	 * @param roleId
	 * @param subjectId
	 * @param gradeId
	 * @param orgId 
	 * @param schoolYear 
	 * @return
	 */
	boolean spaceAlreadyExist(String userName, Integer roleId,
			Integer subjectId, Integer gradeId, Integer orgId, Integer schoolYear);

	/**
	 * 通过用户姓名、角色、学科、学段判断空间是否已经存在(用于同一个机构下的区域用户)
	 * @param userName
	 * @param roleId
	 * @param subjectId
	 * @param phaseId
	 * @param orgId 
	 * @param schoolYear 
	 * @return
	 */
	boolean spaceAlreadyExist2(String userName, Integer roleId,
			Integer subjectId, Integer phaseId, Integer orgId, Integer schoolYear);

}
