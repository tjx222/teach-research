package com.tmser.tr.comment.service;

import com.tmser.tr.comment.bo.Discuss;

/**
 * 
 * @author 
 * 增加评论信息回调
 */
public interface CommentCallback {
	
	/**
	 * 是否允许讨论
	 * @param activeId
	 * @return
	 */
	boolean canDiscuss(Integer activeId);
	
	/**
	 * 讨论保存成功后回调
	 * @param discuss
	 */
	void discussSuccessCallback(Discuss discuss);
	
	/**
	 * 增加评论回调
	 * @param resid
	 * @param restype
	 */
	void commentSuccessCallback(Integer resid,String content);
	
	/**
	 * 是否支持回调
	 * @param restype
	 * @return
	 */
	boolean support(Integer restype);
}
