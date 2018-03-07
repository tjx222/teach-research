/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.comment.service;

import com.tmser.tr.comment.bo.CommentInfo;
import com.tmser.tr.common.service.BaseService;

/**
 * 评论信息 服务类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: CommentInfo.java, v 1.0 2015-03-20 Generate Tools Exp $
 */

public interface CommentInfoService extends BaseService<CommentInfo, Integer>{
	//设置增加评论或者回复时候回调函数
	/**
	 * 保存评论
	 * @param commentInfo
	 * @return
	 */
	public void saveComment(CommentInfo commentInfo);
	
}
