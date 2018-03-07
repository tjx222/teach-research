/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.operation.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.tmser.tr.back.operation.vo.LeaderOperationInfoVo;
import com.tmser.tr.back.operation.vo.OrgOperationInfoVo;
import com.tmser.tr.back.operation.vo.TeacherOperationInfoVo;
import com.tmser.tr.back.operationmanage.vo.SearchVo;
import com.tmser.tr.common.page.PageList;

/**
 * <pre>
 * 运营管理service
 * </pre>
 * 
 * @author daweiwbs
 * @version $Id: OperationManageService.java, v 1.0 2015年11月4日 下午5:48:30
 *          daweiwbs Exp $
 */
public interface OperationManageService {

	/**
	 * 获取地区下的学校运营统计集合
	 * 
	 * @param search
	 * @return
	 */
	PageList<OrgOperationInfoVo> getOrgOperationInfoList(SearchVo search);

	/**
	 * 获取学校下老师运营统计集合
	 * 
	 * @param search
	 * @return
	 */
	PageList<TeacherOperationInfoVo> getTeacherOperationInfoList(SearchVo search);

	/**
	 * 获取学校下管理人员的运营统计集合
	 * 
	 * @param search
	 * @return
	 */
	PageList<LeaderOperationInfoVo> toLeaderOperationInfoList(SearchVo search);

	/**
	 * 导出到excel
	 * 
	 * @param headers
	 * @param propertyNames
	 * @param orginfoList
	 * @param string
	 */
	<T> void exportToExcel(String[] headers, String[] propertyNames, List<T> objectList, String title, HttpServletResponse response);

	/**
	 * 导出学校下的老师和管理者的运营情况
	 * 
	 * @param headers1
	 * @param propertyNames1
	 * @param teacherInfoList
	 * @param headers2
	 * @param propertyNames2
	 * @param leaderInfoList
	 * @param orgName
	 * @param response
	 */
	void exportUserOperationToExcel(String[] headers1, String[] propertyNames1, List<TeacherOperationInfoVo> teacherInfoList, String[] headers2, String[] propertyNames2,
			List<LeaderOperationInfoVo> leaderInfoList, String orgName, HttpServletResponse response);

	/**
	 * 区域下各项统计数据合计
	 * 
	 * @param search
	 * @return
	 */
	OrgOperationInfoVo getTotalCountOfArea(SearchVo search);

	/**
	 * 学校下老师的各项统计数据合计
	 * 
	 * @param search
	 * @return
	 */
	TeacherOperationInfoVo getTotalCountOfTeacherOfOrg(SearchVo search);

	/**
	 * 学校下管理者的各项统计数据合计
	 * 
	 * @param search
	 * @return
	 */
	LeaderOperationInfoVo getTotalCountOfLeaderOfOrg(SearchVo search);

}
