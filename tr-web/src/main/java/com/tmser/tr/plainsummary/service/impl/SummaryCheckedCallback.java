package com.tmser.tr.plainsummary.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.tmser.tr.check.service.AbstractCheckedCallback;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.plainsummary.bo.PlainSummary;
import com.tmser.tr.plainsummary.dao.PlainSummaryDao;

@Service
@Lazy(false)
public class SummaryCheckedCallback extends AbstractCheckedCallback{
	
	@Autowired
	private PlainSummaryDao plainSummaryDao;
	
	/**
	 * 审阅成功回调
	 * @param resid
	 * @param restype
	 * @see com.tmser.tr.check.service.CheckedCallback#checkSuccessCallback(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void checkSuccessCallback(Integer resid, Integer restype,String content) {
		//只监听计划总结类型
		if(ResTypeConstants.TPPLAIN_SUMMARY_SUMMARY!=restype){
			return;
		}
		PlainSummary ps = new PlainSummary();
		ps.setIsCheck(1);
		ps.setId(resid);
		plainSummaryDao.update(ps);
	}
	
	/**
	 * 计划总结类消息
	 * @return
	 * @see com.tmser.tr.check.service.AbstractCheckedCallback#getType()
	 */
	@Override
	protected Integer getType() {
		return ResTypeConstants.TPPLAIN_SUMMARY_SUMMARY;
	}

	public void setPlainSummaryDao(PlainSummaryDao plainSummaryDao) {
		this.plainSummaryDao = plainSummaryDao;
	}

}
