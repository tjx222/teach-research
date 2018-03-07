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
import com.tmser.tr.history.service.LessonPlanHistory;
import com.tmser.tr.history.vo.HistoryColumn;

/**
 * <pre>
 *  备课资源历史统计服务
 * </pre>
 *
 * @author tmser
 * @version $Id: LessonPlanHistoryServiceImpl.java, v 1.0 2016年5月18日 下午4:24:00 tmser Exp $
 */
@Service("lessonplan")
@Transactional
public class LessonPlanHistoryCountImpl implements HistoryCount {
	
	private List<HistoryColumn> columns = new ArrayList<HistoryColumn>();
	
	@Autowired
	private LessonPlanHistory lessonPlanHistory;

	public LessonPlanHistoryCountImpl(){
		columns.add(new HistoryColumn("zxja", "我的教案",new String[]{"撰写数"}, 10));
		columns.add(new HistoryColumn("sckj", "我的课件",new String[]{"撰写数"}, 20));
		columns.add(new HistoryColumn("jxfs", "教学反思",new String[]{"撰写数"}, 30));
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
		Integer count = 0;
		if(supports(code)){
			count = lessonPlanHistory.getLessonPlanHistoryCount(userId,code,currentYear);
		}
		return new Integer[]{count};
	}

}
