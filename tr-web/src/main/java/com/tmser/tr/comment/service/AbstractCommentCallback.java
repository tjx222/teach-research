package com.tmser.tr.comment.service;

import com.tmser.tr.comment.bo.Discuss;

public abstract class AbstractCommentCallback implements CommentCallback{
	
	/**
	 * 是否允许讨论
	 * @param activeId
	 * @return
	 */
	@Override
	public boolean canDiscuss(Integer activeId){
		return false;
	}
	
	/**
	 * 讨论保存成功后回调
	 * @param discuss
	 */
	@Override
	public void discussSuccessCallback(Discuss discuss){
	}
	
	
}
