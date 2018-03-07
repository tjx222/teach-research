package com.tmser.tr.back.feedback.service;

import java.util.List;

import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.feedback.bo.Recieve;
import com.tmser.tr.feedback.bo.Reply;
import com.tmser.tr.feedback.vo.FeedBackVo;
/**
 * 功能：反馈回复
 * @author lijianghu
 * 2015-9-25 
 *
 */
public interface FeedbackReplyService extends BaseService<Reply, Integer> {

	/**
	 * 获取回复详情
	 * @param re
	 * @return
	 */
	List<FeedBackVo> getReplyDetail(Recieve re);

	/**
	 * 保存回复信息
	 * @param reply
	 */
	void saveFeedbackReply(Reply reply);

}
