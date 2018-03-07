/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.history.service.HistoryCount;
import com.tmser.tr.history.service.RecordbagHistory;
import com.tmser.tr.history.vo.HistoryColumn;

/**
 * <pre>
 *  成长档案袋历史资源统计数回调服务类
 * </pre>
 *
 * @author dell
 * @version $Id: ThesisHistoryCountImpl.java, v 1.0 2016年6月6日 下午3:37:52 dell Exp $
 */

@Service("recordbag")
@Transactional
public class RecordbagHistoryCountImpl implements HistoryCount {
	
	@Autowired
	private  RecordbagHistory recordbagHistory;
	
	private List<HistoryColumn> columns = new ArrayList<HistoryColumn>();

	public RecordbagHistoryCountImpl(){
		columns.add(new HistoryColumn("czda", "成长档案袋",new String[]{"精选数"}, 80));
	}
	/**
	 * @param code 
	 * @return
	 * @see com.tmser.tr.history.service.HistoryCount#supports(java.lang.String)
	 */ 
	@Override
	public boolean supports(String code) {
		if ("czda".equals(code)) {
			return true;
		}
		return false;
	}

	/**
	 * @return
	 * @see com.tmser.tr.history.service.HistoryCount#getColumns()
	 */
	@Override
	public List<HistoryColumn> getColumns() {
		return columns;
	}

	/**
	 * @param userId
	 * @param code
	 * @param currentYear
	 * @return
	 * @see com.tmser.tr.history.service.HistoryCount#count(java.lang.Integer, java.lang.String, java.lang.Integer)
	 */
	@Override
	public Integer[] count(Integer userId, String code, Integer currentYear) {
		Integer count=0;
		if (supports(code)) {
			count=recordbagHistory.getRecordbagHistory(userId, currentYear);
		}
		return new Integer[]{count};
	}

}
