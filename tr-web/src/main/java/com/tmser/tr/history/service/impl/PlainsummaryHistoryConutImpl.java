/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmser.tr.history.service.HistoryCount;
import com.tmser.tr.history.vo.HistoryColumn;
import com.tmser.tr.plainsummary.bo.PlainSummary;
import com.tmser.tr.plainsummary.dao.PlainSummaryDao;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 3020mt
 * @version $Id: PlainsummaryHistoryConutImpl.java, v 1.0 2016年5月19日 下午1:28:37 3020mt Exp $
 */
@Service("plainsummaryHistoryConut")
public class PlainsummaryHistoryConutImpl implements HistoryCount {
	@Autowired
	private PlainSummaryDao plainSummaryDao;
	public List<HistoryColumn> columns = new ArrayList<HistoryColumn>();
	
	public PlainsummaryHistoryConutImpl(){
		columns.add(new HistoryColumn("jhzj", "计划总结", new String[]{"撰写数"}, 60));
	}

	/**
	 * @param code
	 * @return
	 * @see com.tmser.tr.history.service.HistoryCount#supports(java.lang.String)
	 */
	@Override
	public boolean supports(String code) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
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
		int count = 0;
		if(supports(code)){
			PlainSummary pl = new PlainSummary();
			pl.setSchoolYear(currentYear);
			pl.setUserId(userId);
			count = plainSummaryDao.count(pl);
		}
		return new Integer[]{count};
	}

}
