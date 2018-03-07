/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tmser.tr.activity.bo.SchoolActivity;
import com.tmser.tr.activity.service.SchoolActivityService;
import com.tmser.tr.history.dao.HistoryDao;
import com.tmser.tr.history.service.HistoryCount;
import com.tmser.tr.history.vo.HistoryColumn;
import com.tmser.tr.history.vo.SearchVo;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.service.UserService;

/**
 * <pre>
 *  校级教研历史统计服务
 * </pre>
 *
 * @author zpp
 * @version $Id: LessonPlanHistoryServiceImpl.java, v 1.0 2016年5月24日 下午4:24:00 zpp Exp $
 */
@Service("schoolactivity")
public class SchoolActivityHistoryCountImpl implements HistoryCount {
	
	@Resource
	private SchoolActivityService schoolActivityService;
	@Resource
	private UserService userService;
	@Resource
	private HistoryDao historyDao;
	
	public List<HistoryColumn> columns = new ArrayList<HistoryColumn>();

	public SchoolActivityHistoryCountImpl(){
		columns.add(new HistoryColumn("xjjy", "校际教研",new String[]{"发起数","已参与数"}, 100));
		columns.add(new HistoryColumn("qy_xjjy", "校际教研",new String[]{"发起数","已参与数"}, 100));
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
		if(supports(code)){
			//统计校际教研本人的撰写数
			SchoolActivity sa = new SchoolActivity();
			sa.setSchoolYear(currentYear);
			sa.setOrganizeUserId(userId);
			sa.setStatus(1);
			int countzx = schoolActivityService.count(sa);
			//统计校际教研本人的参与数
			User user = userService.findOne(userId);
			if(user!=null){
				SearchVo searchVo = new SearchVo();
				searchVo.setUserId(userId);
				searchVo.setSchoolYear(currentYear);
				Integer countcy = historyDao.getSchoolActivityHistoryCount_join(searchVo);
				return new Integer[]{countzx,countcy};
			}else{
				return new Integer[]{countzx,0};
			}
		}
		return new Integer[]{0,0};
	}
	
}
