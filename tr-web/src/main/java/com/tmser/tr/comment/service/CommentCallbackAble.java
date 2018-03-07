package com.tmser.tr.comment.service;

import java.util.List;

/**
 * 
 * <pre>
 *  评论回调注册器
 * </pre>
 *
 * @author tmser
 * @version $Id: CommentCallbackAble.java, v 1.0 2016年11月4日 上午9:25:54 tmser Exp $
 */
public interface CommentCallbackAble {
	
	/**
	 * 获取注册的评论回调器
	 * @return
	 */
	List<CommentCallback> registedCommentCallbacks();
	
	/**
	 * 注册评论回调
	 */
	void registCommentCallback(CommentCallback callback);
}
