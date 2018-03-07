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
import com.tmser.tr.history.service.ThesisHistory;
import com.tmser.tr.history.vo.HistoryColumn;

/**
 * <pre>
 *  教学文章历史资源统计数回调服务类
 * </pre>
 *
 * @author dell
 * @version $Id: ThesisHistoryCountImpl.java, v 1.0 2016年5月30日 下午5:37:52 dell Exp $
 */

@Service
@Transactional
public class ThesisHistoryCountImpl implements HistoryCount {
	
	@Autowired
	private  ThesisHistory thesisHistory;
	
	private List<HistoryColumn> columns = new ArrayList<HistoryColumn>();

	public ThesisHistoryCountImpl(){
		columns.add(new HistoryColumn("jxwz", "教学文章",new String[]{"撰写数"}, 70));
	}
	/**
	 * @param code 
	 * @return
	 * @see com.tmser.tr.history.service.HistoryCount#supports(java.lang.String)
	 */ 
	@Override
	public boolean supports(String code) {
		// TODO Auto-generated method stub
		if ("jxwz".equals(code)) {
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		Integer count=0;
		if (supports(code)) {
			count=thesisHistory.getThesisHistory(userId, code, currentYear);
		}
		return new Integer[]{count};
	}

}
