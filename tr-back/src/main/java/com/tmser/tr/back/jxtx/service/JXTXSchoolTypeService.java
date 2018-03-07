package com.tmser.tr.back.jxtx.service;

import java.util.Map;

import com.tmser.tr.manage.meta.bo.MetaRelationship;

/**
 * 
 * <pre>
 *
 * </pre>
 *
 * @author 川子
 * @version $Id: SchTypeManagerService.java, v 1.0 2015年8月25日 上午10:52:31 川子 Exp
 *          $
 */
public interface JXTXSchoolTypeService {

	/**
	 * 根据id删除学校类型
	 * @param id
	 */
	void delSchType(Integer id);
	
	/**
	 * 新增学校类型
	 * @param relship
	 * @return
	 */
	Map<String,Object> goSaveOrUpdate(Integer id);
	
	
	void addOrUpdateSchType(MetaRelationship mr);
	
}
