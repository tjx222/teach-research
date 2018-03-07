/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.school.show.service;

import java.util.List;

import com.tmser.tr.annunciate.bo.JyAnnunciate;
import com.tmser.tr.annunciate.vo.JyAnnunciateVo;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.school.show.bo.SchoolShow;

/**
 * 为学校首页提供数据 服务类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: SchoolShow.java, v 1.0 2015-09-23 Generate Tools Exp $
 */

public interface SchoolShowService extends BaseService<SchoolShow, String>{

	public List<SchoolShow> findAllShowInfo(SchoolShow model);

   /**
    * 通知公告列表
	*/
	PageList<JyAnnunciate> getPageList(JyAnnunciateVo vo);
}
