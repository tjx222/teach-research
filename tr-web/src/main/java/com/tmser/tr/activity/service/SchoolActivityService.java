/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.service;

import java.util.List;
import java.util.Map;

import com.tmser.tr.activity.bo.SchoolActivity;
import com.tmser.tr.activity.bo.SchoolActivityTracks;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.manage.resources.bo.Attach;
import com.tmser.tr.uc.bo.UserSpace;

/**
 * 校际教研活动表 服务类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author zpp
 * @version $Id: SchoolActivity.java, v 1.0 2015-05-20 zpp Exp $
 */

public interface SchoolActivityService extends BaseService<SchoolActivity, Integer> {

	/**
	 * 进入校际教研界面
	 * 
	 * @param sa
	 * @return
	 */
	Map<String, Object> findSchoolActivity(SchoolActivity sa, Integer listType);

	/**
	 * 校际教研草稿箱
	 * 
	 * @param sa
	 * @return
	 */
	PageList<SchoolActivity> findSchoolActivityDraf(SchoolActivity sa);

	/**
	 * 加载主备人列表
	 * 
	 * @param us
	 * @return
	 */
	List<UserSpace> findMainUserList(UserSpace us);

	/**
	 * 加载主备课题列表
	 * 
	 * @param userId
	 * @return
	 */
	List<LessonInfo> findChapterList(Integer userId, Integer subjectId, Integer gradeId);

	/**
	 * 查询用户作为临时的专家
	 * 
	 * @param userName
	 * @return
	 */
	List<Map<String, Object>> findUserObjectByName(String userName);

	/**
	 * 保存 校际教研集体备课活动-同备教案-主题研讨
	 * 
	 * @param sa
	 * @return
	 */
	Integer saveOrUpdateSchoolActivity(SchoolActivity sa, String resIds, Boolean haveDiscuss, Boolean haveTrack);

	/**
	 * 查询专家用户，通过ids
	 * 
	 * @param expertIds
	 * @return
	 */
	List<Map<String, Object>> findUserObjectByIds(String expertIds);

	/**
	 * 查询主题研讨活动附件
	 * 
	 * @param id
	 * @return
	 */
	List<Attach> findActivityAttach(Integer id);

	/**
	 * 删除校际教研活动
	 * 
	 * @param id
	 */
	String deleteActivity(Integer id);

	/**
	 * 将整理好的教案发送给参与人
	 * 
	 * @param id
	 */
	void sendToJoiner(Integer id);

	/**
	 * 判断活动是否已结束，如果已结束则更改状态为“结束”
	 * 
	 * @param activity
	 * @return
	 */
	public boolean ifActivityIsOver(SchoolActivity activity);

	/**
	 * 此校际教研活动下是否有讨论信息
	 * 
	 * @param id
	 * @return
	 */
	public Boolean isDiscuss(Integer id);

	/**
	 * 判断活动是否被删除，是否到参与活动的时间了
	 * 
	 * @param activityId
	 * @return
	 */
	public Map<String, String> activityIsDelete(Integer activityId);

	/**
	 * 通过学科年级查询用户
	 * 
	 * @param activity
	 * @return
	 */
	public List<UserSpace> findUserBySubjectAndGrade(SchoolActivity activity);

	/**
	 * 上传同辈教案的修改课件的保存
	 * 
	 * @param sat
	 */
	void schKjSave(SchoolActivityTracks sat);
}
