/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.jxtx.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.back.jxtx.service.JXTXPhaseManageService;
import com.tmser.tr.back.logger.LoggerModule;
import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.common.vo.Datas;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.meta.service.impl.MetaRelationshipServiceImpl;

/**
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author wy
 * @version $Id: JXTXPhaseManageServiceImpl.java, v 1.0 2015年8月26日 上午10:32:14 wy
 *          Exp $
 */
@Service
@Transactional
public class JXTXPhaseManageServiceImpl extends MetaRelationshipServiceImpl implements JXTXPhaseManageService {

	/**
	 * 保存学段
	 * 
	 * @param meta
	 * @param phaselist
	 * @see com.tmser.tr.back.jxtx.service.JXTXPhaseManageService#savePhaseMate(java.util.List,
	 *      java.util.List)
	 */
	@Override
	public void savePhaseMate(Datas metas) {
		for (MetaRelationship meta : metas.getMetas()) {
			if (meta.getEid() != null) {
				meta.setType(MetaRelationship.T_XD);
				MetaRelationship insertOne = super.save(meta);
				LoggerUtils.insertLogger(LoggerModule.JXTX, "组织机构管理——保存学段，元数据id：" + insertOne.getId());
			}
		}
	}

	/**
	 * 排序
	 * 
	 * @param phaseIds
	 * @param sortList
	 * @return
	 * @see com.tmser.tr.back.jxtx.service.JXTXPhaseManageService#sortPhaseMate(java.util.List,
	 *      java.util.List)
	 */
	@Override
	public boolean sortPhaseMate(List<Integer> phaseIds) {
		boolean rs = false;
		for (int i = 0; i < phaseIds.size(); i++) {
			MetaRelationship mr = new MetaRelationship();
			mr.setType(MetaRelationship.T_XD);
			mr.setId(phaseIds.get(i));
			MetaRelationship oldMeta = super.findOne(mr);
			if (oldMeta != null) {
				oldMeta.setSort(i + 1);
				rs = super.update(oldMeta) > 0;
				LoggerUtils.updateLogger(LoggerModule.JXTX, "组织机构管理——更新学段(排序)，元数据id：" + oldMeta.getId());
			}
			if (!rs)
				break;
		}
		return rs;
	}

}
