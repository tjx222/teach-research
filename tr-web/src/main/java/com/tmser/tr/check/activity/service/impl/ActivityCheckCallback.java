/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.check.activity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.activity.bo.Activity;
import com.tmser.tr.activity.dao.ActivityDao;
import com.tmser.tr.check.service.AbstractCheckedCallback;
import com.tmser.tr.common.ResTypeConstants;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: ActivityCheckCallback.java, v 1.0 2015年4月22日 下午4:32:36 tmser Exp $
 */
@Service
@Lazy(false)
@Transactional
public class ActivityCheckCallback extends AbstractCheckedCallback{

	@Autowired
	private ActivityDao activityDao;
	
	/**
	 * @param resid
	 * @param restype
	 * @see com.tmser.tr.check.service.CheckedCallback#checkSuccessCallback(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void checkSuccessCallback(Integer resid, Integer restype,String content) {
		if(restype==ResTypeConstants.ACTIVITY){
			try {
				Activity activity = activityDao.get(resid);
					activity.setIsAudit(true);
					activity.setAuditUp(true);
					activityDao.update(activity);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return
	 * @see com.tmser.tr.check.service.AbstractCheckedCallback#getType()
	 */
	@Override
	protected Integer getType() {
		return ResTypeConstants.ACTIVITY;
	}

	@Override
	public boolean support(Integer restype) {
		if(restype==ResTypeConstants.ACTIVITY){
			return true;
		}else{
			return false;
		}
	}
	

}
