/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.feedback.service;

import java.util.List;

import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.feedback.bo.Recieve;
import com.tmser.tr.feedback.vo.FeedBackVo;
import com.tmser.tr.uc.bo.UserSpace;

/**
 * 用户反馈信息 服务类
 * <pre>
 *@author lijianghu
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Recieve.java, v 1.0 2015-09-15 Generate Tools Exp $
 */

public interface RecieveService extends BaseService<Recieve, Integer>{

	/**
	 * 分页查询反馈列表
	 * @param us
	 * @param model
	 * @return
	 */
	PageList<Recieve> getFeedbackListByPage(UserSpace us, Recieve model);
	/**
	 * 根据pid反馈详情信息
	 * @param re
	 * @return
	 */
	List<FeedBackVo> getReplyDetail(Recieve re);
	/**
	 * 查询反馈详情信息
	 * @param recieve
	 * @return
	 */
	Recieve getRecieveDetail(Recieve recieve);
	/**
	 * 保存反馈信息
	 * @param re
	 */
	void saveRecieve(Recieve re);

}
