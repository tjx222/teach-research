package com.tmser.tr.plainsummary.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.tmser.tr.comment.service.AbstractCommentCallback;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.plainsummary.bo.PlainSummary;
import com.tmser.tr.plainsummary.dao.PlainSummaryDao;

/**
 * 计划总结消息回调
 * @author wanzheng
 *
 */
@Service
@Lazy(false)
public class PlainCommentCallBack extends AbstractCommentCallback{

	@Autowired
	private PlainSummaryDao plainSummaryDao;
	/**
	 * 评论提交回调
	 */
	@Override
	public void commentSuccessCallback(Integer resid, String content) {
			PlainSummary ps = new PlainSummary();
			ps.setIsReview(1);
			ps.setId(resid);
			ps.addCustomCulomn("reviewNum=reviewNum+1");
			plainSummaryDao.update(ps);
	}

	/**
	 * 支持回调
	 */
	@Override
	public boolean support(Integer restype) {
		return ResTypeConstants.TPPLAIN_SUMMARY_PLIAN == restype ||ResTypeConstants.TPPLAIN_SUMMARY_SUMMARY == restype;
	}

	public void setPlainSummaryDao(PlainSummaryDao plainSummaryDao) {
		this.plainSummaryDao = plainSummaryDao;
	}
	
}
