/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.lecturerecords.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.check.service.AbstractCheckedCallback;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.lecturerecords.bo.LectureRecords;
import com.tmser.tr.lecturerecords.service.LectureRecordsService;
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 *  查阅听课记录回调函数
 * </pre>
 *
 * @author wangdawei
 * @version $Id: CheckLectureRecordsCallback.java, v 1.0 2016年8月29日 下午2:51:42 wangdawei Exp $
 */
@Service
@Lazy(false)
@Transactional
public class CheckLectureRecordsCallback extends AbstractCheckedCallback{
	
	@Autowired
	private LectureRecordsService lrService;

	/**
	 * 查阅成功后回调
	 * @param resid
	 * @param restype
	 * @param content
	 * @see com.tmser.tr.check.service.CheckedCallback#checkSuccessCallback(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@Override
	public void checkSuccessCallback(Integer resid, Integer restype,
			String content) {
		LectureRecords lr = lrService.findOne(resid);
		lr.setIsScan(1);
		if(StringUtils.isNotBlank(content)){
			lr.setScanUp(1);
		}
		lr.setLastupDttm(new Date());
		lrService.update(lr);
	}
	
	@Override
	public boolean support(Integer restype) {
		//支持教案、课件、反思 （不包含其他反思）
		return restype == ResTypeConstants.LECTURE;
	}

	/**
	 * @return
	 * @see com.tmser.tr.check.service.AbstractCheckedCallback#getType()
	 */
	@Override
	protected Integer getType() {
		return null;
	}

}
