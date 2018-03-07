/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.recordbag.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.comment.service.AbstractCommentCallback;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.recordbag.bo.Recordbag;
import com.tmser.tr.recordbag.dao.RecordbagDao;

/**
 * <pre>
 *
 * </pre>
 *
 * @author zpp
 * @version $Id: ReThinkCommentCallback.java, v 1.0 2015年4月1日 下午4:49:31 zpp Exp $
 */
@Service
@Lazy(false)
@Transactional
public class RecordCommentCallback extends AbstractCommentCallback{
	
	@Autowired
	private RecordbagDao recordbagDao;
	
	/**
	 * @param resid
	 * @param restype
	 * @see com.tmser.tr.comment.service.CommentCallback#commentSuccessCallback(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void commentSuccessCallback(Integer resid, String content) {
			Recordbag bag = recordbagDao.get(resid);
			bag.setIsPinglun(1);//已评论
			bag.setPinglun(1);//未被查看过
			recordbagDao.update(bag);
	}
	/**
	 * @param restype
	 * @return
	 * @see com.tmser.tr.comment.service.CommentCallback#support(java.lang.Integer)
	 */
	@Override
	public boolean support(Integer restype) {
		return restype==ResTypeConstants.RECORD_BAG;
	}

}
