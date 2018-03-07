package com.tmser.tr.history.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.history.service.ActivityHistory;
import com.tmser.tr.history.service.HistoryCount;
import com.tmser.tr.history.vo.HistoryColumn;
/**
 * 
 * <pre>
 *集体备课历史资源统计数回调服务类
 * </pre>
 *
 * @author wangdawei
 * @version $Id: ActivityHistoryCountImpl.java, v 1.0 2016年5月25日 下午3:30:16 wangdawei Exp $
 */
@Service
@Transactional
public class ActivityHistoryCountImpl implements HistoryCount{

	private List<HistoryColumn> columns = new ArrayList<HistoryColumn>();
	
	@Autowired
	private ActivityHistory activityHistory;

	public ActivityHistoryCountImpl() {
		columns.add(new HistoryColumn("jtbk", "集体备课", new String[]{"撰写数","已参与数"}, 50));
	}
	
	@Override
	public boolean supports(String code) {
		if("jtbk".equals(code)){
			return true;
		}
		return false;
	}

	@Override
	public List<HistoryColumn> getColumns() {
		return columns;
	}

	@Override
	public Integer[] count(Integer userId, String code, Integer currentYear) {
		Integer issueCount = 0;
		Integer joinCount = 0;
		if(supports(code)){
			issueCount = activityHistory.getActivityHistoryCount_issue(userId,currentYear);
			joinCount = activityHistory.getActivityHistoryCount_join(userId,currentYear);
		}
		return new Integer[]{issueCount,joinCount};
	}

}
