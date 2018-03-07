/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.zzjg.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.manage.org.bo.Area;
import com.tmser.tr.manage.org.bo.Organization;

/**
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author wy
 * @version $Id: JXTXOrgSchoolManageService.java, v 1.0 2015年9月1日 下午3:36:24 wy
 *          Exp $
 */

public interface ZZJGOrgSchoolManageService {

	/**
	 * 组织机构树
	 * 
	 * @return
	 */
	List<Area> orgFindTree();

	/**
	 * 保存学校
	 * 
	 * @param org
	 * @return
	 */
	void saveSch(Organization org);

	/**
	 * 查看学校信息
	 * 
	 * @param areaId
	 * @return
	 */
	Map<String, Object> lookInfo(Integer id);

	/**
	 * 删除学校信息
	 */
	void delSch(Integer id);

	/**
	 * 查询直属机构树
	 * 
	 * @return
	 */
	List<Area> findDirectTree();

	/**
	 * 修改直属机构
	 * 
	 * @param org
	 * @return
	 */
	boolean saveDirLel(Organization org);

	/**
	 * 更新学校状态
	 * 
	 * @param org
	 * @return
	 */
	boolean updateLock(Organization org);

	/**
	 * 根据id查找直属学校
	 * 
	 * @param id
	 * @return
	 */
	Map<String, Object> finDirSchById(Integer id);

	/**
	 * 根据id更新直属区域设置
	 * 
	 * @param id
	 */
	boolean delDirSchById(Integer id);

	/**
	 * 根据学校id查询部门信息
	 * 
	 * @param parentId
	 * @return
	 */
	Map<String, Object> findDeptByPid(Organization org, String serchUnit);

	/**
	 * 根据id查找部门信息
	 * 
	 * @param id
	 * @return
	 */
	Organization findInfoById(Integer id);

	/**
	 * 保存部门信息
	 * 
	 * @param org
	 * @param deptAreaId
	 */
	boolean saveDeptInfo(Organization org);

	/**
	 * 根据学校id查找学校信息
	 * 
	 * @param parentId
	 * @return
	 */
	Organization finSchById(Integer parentId);

	/**
	 * 删除相关部门信息
	 * 
	 * @param id
	 */
	void delDept(Integer id);

	/**
	 * 通过学校-单位id查找部门全名称
	 * 
	 * @param parentId
	 * @return
	 */
	Map<String, Object> findDeptNameByPid(Integer parentId);

	/**
	 * 查询地区信息
	 * 
	 * @param areaId
	 * @return
	 */
	Map<String, Object> finAreaById(Integer areaId);

	/**
	 * 查询已添加部门信息
	 * 
	 * @param areaId
	 * @param page
	 * @param searchStr
	 * @return
	 */
	PageList<Organization> findUnitInfo(Integer areaId, Page page, String searchStr);

	/**
	 * 保存部门信息
	 * 
	 * @param org
	 */
	void saveUnit(Organization org);

	/**
	 * 删除部门信息
	 * 
	 * @param id
	 */
	void delUnit(Integer id);

	/**
	 * 效验名称是否重复
	 * 
	 * @param org
	 * @return
	 */
	List<Organization> valiSchInfo(Organization org);
	
	/**
	 * 批量创建机构
	 * @param file
	 * @param orgType
	 * @param areaId
	 * @return
	 */
	Map<String, String> batchInsertSchool(MultipartFile file, Integer orgType, Integer areaId);

}
