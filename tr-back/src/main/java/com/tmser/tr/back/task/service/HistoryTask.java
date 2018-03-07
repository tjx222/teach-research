/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.task.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.bo.UsermenuHistory;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.service.UsermenuHistoryService;

/**
 * <pre>
 *  历史学年功能备份收集
 * </pre>
 *
 * @author tmser
 * @version $Id: HistoryTask.java, v 1.0 2016年6月16日 上午11:11:31 tmser Exp $
 */
public class HistoryTask implements SchoolYearTask{
	
	private static final Logger logger = LoggerFactory.getLogger(HistoryTask.class);
	
	@Autowired
	private UsermenuHistoryService usermenuHistoryService;
	
	@Autowired
	private UserSpaceService userSpaceService;
	
	/**
	 * 收集历史资源栏目
	 */
	public void collectHistoryColumn(Integer historyYear){
		final int oldSchoolYear = historyYear - 1;
		UserSpace smodel = new UserSpace();
		smodel.setSchoolYear(oldSchoolYear);
		smodel.addCustomCulomn("distinct userId");
		smodel.pageSize(500);
		smodel.getPage().setNeedTotal(false);
		smodel.addOrder("id ASC");
		
		PageList<UserSpace> userPages = userSpaceService.findByPage(smodel);
		List<UsermenuHistory> uhs = new ArrayList<UsermenuHistory>(500);
		logger.info("history tast start! ...");
		ForkJoinPool pool = new ForkJoinPool(); 
		while(userPages != null && userPages.getDatalist().size() > 0){
			int size = userPages.getDatalist().size();
			uhs.clear();
			pool.submit(new Computor(userPages.getDatalist(), oldSchoolYear, 0, size));
			smodel.currentPage(userPages.nextPage()); //切换到下一页
			userPages = userSpaceService.findByPage(smodel);
			logger.info("history runing...,current page is {}",userPages.getCurrentPage());
			LoggerUtils.insertLogger("parse history menu page {} complete.",userPages.getCurrentPage());
		}
		logger.info("history tast end! ...");
		pool.shutdown();
	}


	
	class Computor extends RecursiveAction{
		
		/**
		 * <pre>
		 *
		 * </pre>
		 */
		private static final long serialVersionUID = 6541061740340730431L;

		private final int start;
		
		private final int end;
		
		private final List<UserSpace> userPages;
		
		private final Integer historyYear;
		
		Computor( List<UserSpace> userPages,Integer historyYear,int start,int end){
			this.start = start;
			this.end = end;
			this.userPages = userPages;
			this.historyYear = historyYear;
		}
		
		/**
		 * @return
		 * @see java.util.concurrent.RecursiveTask#compute()
		 */
		@Override
		protected void compute() {
			int length = end - start;
			if(length < 21 ){
				List<UsermenuHistory> uhs = new ArrayList<UsermenuHistory>(length);
				UsermenuHistory uh = null;
				for(int i= start; i < end; i++){
					UserSpace us = userPages.get(i);
					if((uh = usermenuHistoryService.createUserHistory(us.getUserId(), historyYear)) != null){
						uhs.add(uh);
					}
				}
				usermenuHistoryService.batchInsert(uhs);
			}else{
				int split = start + length / 2;
				Computor startCompute = new Computor(userPages, historyYear, start, split);
				Computor endCompute = new Computor(userPages, historyYear,split,end);
				
				startCompute.fork();
				endCompute.fork();
			}
			
		}
	}



	/**
	 * @return
	 * @see com.tmser.tr.back.task.service.task.SchoolYearTask#name()
	 */
	@Override
	public String name() {
		return "历年资源-上学年用户功能收集";
	}



	/**
	 * 
	 * @see com.tmser.tr.back.task.service.task.SchoolYearTask#execute()
	 */
	@Override
	public void execute(Integer historyYear) {
		collectHistoryColumn(historyYear);
	}



	/**
	 * @return
	 * @see com.tmser.tr.back.task.service.SchoolYearTask#desc()
	 */
	@Override
	public String desc() {
		return "学年更新后启动收集用户上学年的功能";
	}

}
