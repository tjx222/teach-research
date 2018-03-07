/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.recordbag.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.recordbag.bo.Record;
import com.tmser.tr.recordbag.dao.RecordDao;
import com.tmser.tr.recordbag.service.RecordService;
import com.tmser.tr.uc.utils.SessionKey;
/**
 * 精选档案 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Record.java, v 1.0 2015-04-16 Generate Tools Exp $
 */
@Service
@Transactional
public class RecordServiceImpl extends AbstractService<Record, Integer> implements RecordService {

	@Autowired
	private RecordDao recordDao;
	
	@Autowired
	private ResourcesService resourcesService;
	
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<Record, Integer> getDAO() {
		return recordDao;
	}
	
	/**
	 * @param id
	 * @see com.tmser.tr.common.service.AbstractService#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Integer id) {
		Record r = recordDao.get(id);
		if(r != null){
			resourcesService.deleteResources(r.getPath());
		}
		super.delete(id);
	}

	/**
	 * @param record
	 * @see com.tmser.tr.record.service.RecordService#updateSelfRecode(com.tmser.tr.recordbag.bo.Record)
	 */
	@Override
	public void updateSelfRecord(Record record) {
		recordDao.update(record);
		Record old = recordDao.get(record.getRecordId());
		if(old != null){
			if(old.getPath() != null
				&& !old.getPath().equals(record.getPath())){
					resourcesService.deleteResources(old.getPath());
			}
			resourcesService.updateTmptResources(record.getPath());
		}
	}

	/**
	 * @param record
	 * @see com.tmser.tr.record.service.RecordService#saveSelfRecode(com.tmser.tr.recordbag.bo.Record)
	 */
	@Override
	public void saveSelfRecord(Record record) {
		resourcesService.updateTmptResources(record.getPath());
		save(record);
	}

	/**
	 * @param bagId
	 * @param share
	 * @return
	 * @see com.tmser.tr.recordbag.service.RecordService#shareRecordByBagId(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public int shareRecordByBagId(Integer bagId, Integer share) {
		Integer schoolYear = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		return recordDao.shareRecordByBagId(bagId,share,schoolYear);
	}

}
