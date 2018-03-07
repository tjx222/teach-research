/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.recordbag.service;

import java.util.List;

import org.springframework.ui.Model;

import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.recordbag.bo.Record;
import com.tmser.tr.recordbag.bo.Recordbag;

/**
 * 成长档案袋 服务类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Recordbag.java, v 1.0 2015-04-13 Generate Tools Exp $
 */

public interface RecordbagService extends BaseService<Recordbag, Integer> {
		
		public void initRecordBags();
	
		public List<Recordbag> findAll();//查找当前工作区间，当前用户的档案袋列表
		
		public List<Recordbag> findAll(String userID);//通过userID查找该老师的所有档案袋
		
		@Override
		public List<Recordbag> findAll(Recordbag rdmodel);//通过userID和查询条件查找该老师的所有档案袋
		
		public void initPlan(LessonPlan plan, Recordbag bag, Model m);//初始化plan
		
		public void  initActivity(LessonPlan plan, Recordbag bag, Model m,int id,List<Record> recordList,int type );//初始化Activity
		
		public void  initThesis(LessonPlan plan, Recordbag bag, Model m,int id,List<Record> recordList,int type );//初始化Thesis
		
		public void initPlainSummary(LessonPlan plan, Recordbag bag, Model m, int id,List<Record> recordList, int type);//初始化PlainSummary
		
		public void initLecture(LessonPlan plan, Recordbag bag, Model m, int id,List<Record> recordList, int type);//初始化PlainSummary
		
		public void  savePlan(Integer one,Integer id, Integer type,String desc,Recordbag bag);
		
		public void  saveActivity(Integer one,Integer id, Integer type,String desc,Recordbag bag);
		
		public void  saveThesis(Integer one,Integer id, Integer type,String desc,Recordbag bag);
		
		public void  saveLecture(Integer one,Integer id, Integer type,String desc,Recordbag bag);
		
		public void  saveSummary(Integer one,Integer id, Integer type,String desc,Recordbag bag);
		
		public void  saveLessonPlan(Record record);
		
		public int  saveActivity(Record record,int flag,int id);
		
		public void  saveThesis(Record record,int id);
		
		public void  savePlainSummary(Record record,int id);
		
		public int  saveLectureRecords(Record record,int flag,int id);

		/**
		 * 根据名称简介创建档案袋
		 * @param name
		 * @param desc
		 * @return
		 */
		Recordbag addBag(String name, String desc);
		
		/**
		 * 删除精选资源
		 * @param rid
		 * @return 被删除的记录
		 */
		Record deleteBagRecord(Integer rid);
		
		/**
		 * 保存自定义资源
		 * @param record
		 * @return
		 */
		Record saveSelfRecord(Record record);
		
		/**
		 * 批量更新成长档案袋资源
		 * @param schoolYear
		 */
		void batchUpdateResCount(Integer schoolYear);
}
