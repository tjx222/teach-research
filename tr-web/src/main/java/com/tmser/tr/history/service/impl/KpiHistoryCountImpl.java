/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.history.service.HistoryCount;
import com.tmser.tr.history.vo.HistoryColumn;

/**
 * <pre>
 *  kpi历史统计服务
 * </pre>
 *
 * @author wangyao
 * @version $Id: KpiHistoryCountImpl.java, v 1.0 2016年5月25日 下午4:24:00 wangyao Exp $
 */
@Service("kpi")
@Transactional
public class KpiHistoryCountImpl implements HistoryCount {
	
	private List<HistoryColumn> columns = new ArrayList<HistoryColumn>();

	public KpiHistoryCountImpl(){
		columns.add(new HistoryColumn("kpi", "绩效考核", new String[]{}, 120));
	}
	/**
	 * @param code
	 * @return
	 * @see com.tmser.tr.history.service.HistoryCount#supports(java.lang.String)
	 */
	@Override
	public boolean supports(String code) {
		for(HistoryColumn cm : columns){
			if(cm.getCode().equals(code)){
				return true;
			}
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
	 * @param code
	 * @param currentYear
	 * @return 
	 * @see com.tmser.tr.history.service.HistoryCount#count(java.lang.String, java.lang.Integer)
	 */
	@Override
	public Integer[] count(Integer userId,String code, Integer currentYear) {
		return new Integer[]{};
	}
}
