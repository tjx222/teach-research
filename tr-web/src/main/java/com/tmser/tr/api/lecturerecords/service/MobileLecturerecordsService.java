/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.api.lecturerecords.service;

import java.util.List;

import com.tmser.tr.api.lecturerecords.vo.LectureRecordsMapping;
import com.tmser.tr.common.vo.Result;

/**
 * <pre>
 *
 * </pre>
 *
 * @author zpp
 * @version $Id: MobileLecturerecordsService.java, v 1.0 2016年4月15日 下午5:37:50
 *          zpp Exp $
 */
public interface MobileLecturerecordsService {
	/**
	 * 通过用户ID和类型ID（校内或校外）来获取听课记录列表信息
	 * 
	 * @param userid
	 * @param optime
	 * @return
	 */
	List<LectureRecordsMapping> findInfoList(Integer userid, String optime);

	/**
	 * 听课记录信息保存
	 * 
	 * @param lectureinfo
	 * @return
	 */
	Result saveLecture(String lectureinfo);

	/**
	 * 听课记录信息修改
	 * 
	 * @param lectureinfo
	 * @return
	 */
	Result updateLecture(String lectureinfo);

	/**
	 * 听课记录信息删除
	 * 
	 * @param lectureinfo
	 * @return
	 */
	Result deleteLecture(Integer lectureid);

	/**
	 * 听课记录信息分享
	 * 
	 * @param lectureinfo
	 * @return
	 */
	Result shareLecture(Integer lectureid);

	/**
	 * 修改听课记录回复和评论的状态
	 * @param lectureid
	 * @param type
	 * @return
	 */
	Result uplecupsta(Integer lectureid, String type);

}
