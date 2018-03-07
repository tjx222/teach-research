/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.recordbag.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.check.service.AbstractCheckedCallback;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.recordbag.bo.Recordbag;
import com.tmser.tr.recordbag.dao.RecordbagDao;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: ReThinkCheckCallback.java, v 1.0 2015年3月31日 下午4:32:36 tmser Exp $
 */
@Service
@Lazy(false)
@Transactional
public class RecordCheckCallback extends AbstractCheckedCallback{

	@Autowired
	private RecordbagDao recordbagDao;
	
	/**
	 * @param resid
	 * @param restype
	 * @see com.tmser.tr.check.service.CheckedCallback#checkSuccessCallback(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void checkSuccessCallback(Integer resid, Integer restype,String content) {
		try {
			Recordbag bag = recordbagDao.get(resid);
			  bag.setStatus(0);//未被查看过
			  bag.setStatus(1);//已查阅
			  recordbagDao.update(bag);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return
	 * @see com.tmser.tr.check.service.AbstractCheckedCallback#getType()
	 */
	@Override
	protected Integer getType() {
		return ResTypeConstants.RECORD_BAG;
	}

	@Override
	public boolean support(Integer restype) {
		if(restype==ResTypeConstants.RECORD_BAG){
			return true;
		}else{
			return false;
		}
	}
	

}
