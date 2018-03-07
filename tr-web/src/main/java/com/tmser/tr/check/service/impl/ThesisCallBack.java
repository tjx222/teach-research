package com.tmser.tr.check.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.tmser.tr.check.service.AbstractCheckedCallback;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.thesis.bo.Thesis;
import com.tmser.tr.thesis.dao.ThesisDao;
import com.tmser.tr.utils.StringUtils;

/**
 * 教学文章回调
 * @author wangyao
 *
 */
@Service
@Lazy(false)
public class ThesisCallBack extends AbstractCheckedCallback{

	@Autowired
	private ThesisDao ThesisDao;
	/**
	 * 评论提交回调
	 */
	@Override
	public void checkSuccessCallback(Integer resid, Integer restype,String content) {
		Thesis thesis = new Thesis();
		if(support(restype)){
			thesis.setId(resid);
			thesis.setIsScan(Thesis.SCAN);//已经查阅
			if(StringUtils.isNotBlank(content)){
				thesis.setScanUp(Thesis.SCAN);//查阅意见已更新
			}
			thesis.addCustomCulomn(" scanCount = scanCount+1");
			thesis.setLastupDttm(new Date());
			ThesisDao.update(thesis);
		}
	}

	/**
	 * 支持回调
	 */
	@Override
	public boolean support(Integer restype) {
		return Integer.valueOf(ResTypeConstants.JIAOXUELUNWEN).equals(restype);
	}

	/**
	 * @return
	 * @see com.tmser.tr.check.service.AbstractCheckedCallback#getType()
	 */
	@Override
	protected Integer getType() {
		return ResTypeConstants.JIAOXUELUNWEN;
	}
	
}
