/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.check.lecturerecords.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.tmser.tr.check.bo.CheckInfo;
import com.tmser.tr.lecturerecords.bo.LectureRecords;

/**
 * <pre>
 *  查阅听课记录service
 * </pre>
 *
 * @author wangdawei
 * @version $Id: CheckLectureRecordsService.java, v 1.0 2016年8月18日 上午9:55:35 wangdawei Exp $
 */
public interface CheckLectureRecordsService {

	public List<Map<String,String>> getGradeList();
	public List<Map<String,String>> getSubjectList();
	public List<Map<String, Object>> getTeacherMapList(LectureRecords lr,
			Model m);
	public Map<String, Object> getLectureRecordsDataMap(LectureRecords lr);
	public LectureRecords getLectureRecordsByNum(LectureRecords lr,Model m);
	public List<LectureRecords> getOtherLectureRecordsByLessonId(
			LectureRecords lectureRecords);
	public void checkLectureRecords(CheckInfo checkInfo, String content);
	public List<Map<String, Object>> getLeaderMapList(Integer sysRoleId,
			LectureRecords lr, Model m);
}
