package com.tmser.tr.webservice.service;

import com.tmser.tr.lecturerecords.bo.LectureRecords;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.plainsummary.bo.PlainSummary;
import com.tmser.tr.recordbag.bo.Recordbag;
import com.tmser.tr.webservice.vo.DataResult;
import com.tmser.tr.webservice.vo.JxParams;

/**
 * 绩效考核系统数据采集  服务类
 * @author zpp
 * @version 1.0
 * 2016-03-01
 */

public interface JxkhService{

	/**
	 * 若教师有多重身份，针对于“教案、课件、反思”模块收集的数据为各身份各模块的数据之和
	 * @param jp
	 * @param jiaoan
	 * @return
	 */
	DataResult getBeiKeRes(JxParams jp, int type,LessonPlan lp);

	/**
	 * 针对于“听课记录、个人计划（只收集个人计划的数据）、个人总结（只收集个人总结的数据）”模块，直接收集该用户的数据即可
	 * @param jp
	 * @param lecture
	 * @return
	 */
	DataResult getLectureRes(JxParams jp, Integer lecture,LectureRecords lp);

	/**
	 * 针对于“听课记录、个人计划（只收集个人计划的数据）、个人总结（只收集个人总结的数据）”模块，直接收集该用户的数据即可
	 * @param jp
	 * @param type
	 * @return
	 */
	DataResult getPlainSummarys(JxParams jp, Integer type,PlainSummary model);

	/**
	 * 针对于“成长档案袋”模块收集的数据为该用户已精选的全部资源内容
	 * @param jp
	 * @param type
	 * @return
	 */
	DataResult getRecords(JxParams jp,Recordbag bag);

	/**
	 * 集体备课为该用户可参加的活动数量
	 * @param jp
	 * @param type
	 * @return
	 */
	DataResult getActivityData(JxParams jp, Integer type);

	/**
	 * 针对于“集体担任主备人”收集的数据为该用户各身份参与的集体备课中担任主备人的数量之和
	 * @param jp
	 * @param type
	 * @return
	 */
	DataResult getActivityMainUser(JxParams jp, Integer type);

	/**
	 * 针对于校际教研模块收集的数据为该用户可参加的活动数量
	 * @param jp
	 * @param type
	 * @return
	 */
	DataResult getSchoolActivity(JxParams jp, Integer type);

	/**
	 * 获得网络分享数
	 * @param jp
	 * @return
	 */
	DataResult getWebShareDatas(JxParams jp);


}
