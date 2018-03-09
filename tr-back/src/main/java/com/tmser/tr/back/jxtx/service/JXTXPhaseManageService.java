/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.jxtx.service;

import java.util.List;

import com.tmser.tr.common.vo.Datas;
import com.tmser.tr.manage.meta.service.MetaRelationshipService;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author tmser
 * @version $Id: JXTXBaseManageService.java, v 1.0 2015年8月24日 下午4:21:09 tmser Exp
 *          $
 */
public interface JXTXPhaseManageService extends MetaRelationshipService{


	/**
	 * 保存元数据
	 * 
	 * @param meta
	 * @param type
	 */
	void savePhaseMate(Datas metas);

	/**
	 * 排序
	 * 
	 * @param phaseIds
	 * @param descs
	 * @param eid
	 * @return
	 */
	boolean sortPhaseMate(List<Integer> phaseIds);
	
}
