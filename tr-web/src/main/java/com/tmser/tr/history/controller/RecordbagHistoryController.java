/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.history.service.RecordbagHistory;
import com.tmser.tr.recordbag.bo.Record;
import com.tmser.tr.recordbag.bo.Recordbag;
import com.tmser.tr.recordbag.service.RecordService;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 *  历史学年记录模块-成长档案袋
 * </pre>
 *
 * @author dell
 * @version $Id: RecordbagHistoryController.java, v 1.0 2016年6月7日 下午2:10:35 dell Exp $
 */

@Controller
@RequestMapping("/jy/history")
public class RecordbagHistoryController {

	@Autowired
	private RecordbagHistory recordbagHistory;
	
	@Autowired
	private RecordService recordService;
	
	@Autowired
	private UserSpaceService userSpaceService;
	
	/**
	 * 首页
	 * param m
	*/
	@RequestMapping("/{year}/czda/index")
	public String getIndex(@PathVariable("year")Integer schoolYear,Recordbag recordbag,Model m){
		List<UserSpace> uList=userSpaceService
				.listUserSpaceBySchoolYear(CurrentUserContext.getCurrentUserId(), schoolYear);
		List<UserSpace> usList = new ArrayList<UserSpace>();
		UserSpace us = null;
		if(uList.size() > 0){
			//筛选出教师身份
			for (UserSpace userSpace : uList) {
				if(userSpace.getSysRoleId() .equals(SysRole.TEACHER.getId())){
					usList.add(userSpace);
				}
			}
			if(recordbag.getSpaceId() != null){
				for(UserSpace s : uList){
					if(recordbag.getSpaceId().equals(s.getId())){
						us = s;
						break;
					}
				}
			}else{
				us = uList.get(0);
			}
		}
		
		if(us != null){
			recordbag.setTeacherId(CurrentUserContext.getCurrentUserId().toString());
			recordbag.setGradeId(us.getGradeId());
			recordbag.setSubjectId(us.getSubjectId());
			List<Recordbag> rList=recordbagHistory.getAll(schoolYear,recordbag);
			m.addAttribute("rlist", rList);
			m.addAttribute("ulist", usList);
			m.addAttribute("recordbag", recordbag);
			m.addAttribute("schoolYear", schoolYear);
		}

		return "history/record/index";
	}
	
	/**
	 * 档案袋列表页面
	 * param m
	*/
	@RequestMapping("/{year}/czda/showList")
	public String getPage(@PathVariable("year")Integer schoolYear,Record model,Recordbag recordbag,String searchName,Model m){
		model.setBagId(recordbag.getId());
		if (StringUtils.isNotEmpty(searchName)) {
			model.setRecordName(SqlMapping.LIKE_PRFIX+searchName+SqlMapping.LIKE_PRFIX);
		}
		model.addOrder("createTime desc");
		model.setSchoolYear(schoolYear);
		model.pageSize(10);
		PageList<Record> rList = recordService.findByPage(model);	
		Integer state=0;
		for (Record record:rList.getDatalist()) {
			if (Recordbag.JXSJ.equals(recordbag.getName())||Recordbag.ZZKJ.equals(recordbag.getName())||Recordbag.JXFS.equals(recordbag.getName())) {
				recordbagHistory.showLessonPlanHistory(record);
			}else if (Recordbag.JYHD.equals(recordbag.getName())) {
				state=recordbagHistory.showActivityHistory(record);
			}else if (Recordbag.JXWZ.equals(recordbag.getName())) {
				recordbagHistory.showThesisHistory(record);
			}else if (Recordbag.JHZJ.equals(recordbag.getName())) {
				recordbagHistory.showPainSummaryHistory(record);
			}else if(Recordbag.TKJL.equals(recordbag.getName())) {
				state=2;
				recordbagHistory.showLectureRecordHistory(record);
			}else{
				recordbagHistory.showOtherHistory(record);
			}
		}
		m.addAttribute("rlist", rList);
		m.addAttribute("schoolYear", schoolYear);
		m.addAttribute("recordbag",recordbag);
		m.addAttribute("searchName",searchName);
		if (state==1) {
			return "history/record/activitylist";
		}else if (state==2) {
			return "history/record/lecturelist";
		}
		return "history/record/lessonplanlist";
	}
}
