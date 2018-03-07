/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.thesis.service;

import java.util.List;

import org.springframework.ui.Model;

import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.thesis.bo.Thesis;
import com.tmser.tr.uc.bo.User;

/**
 * 上传教学论文 服务类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Thesis.java, v 1.0 2015-03-12 Generate Tools Exp $
 */

public interface ThesisService extends BaseService<Thesis, Integer>{

	/**
	 * 分页查询教学论文
	 * @param lp
	 * @return
	 */
	PageList<Thesis> findCourseList(Thesis thesis);
	
	//修改或保存教学论文
	public void saveThesis(Thesis thesis,Model m,String originFileName);

	/**
	 * @param thesis
	 * @param user 
	 * @return
	 */
	List<Thesis> getSubmitData(Thesis thesis, User user);

	/**
	 * 更新教学文章的状态
	 * @param resIds
	 * @param state
	 */
	boolean updateSubmitState(Integer[] resIds, Integer state);
}
