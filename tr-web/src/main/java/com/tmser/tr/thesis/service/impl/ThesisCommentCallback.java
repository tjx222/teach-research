package com.tmser.tr.thesis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.comment.service.AbstractCommentCallback;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.thesis.bo.Thesis;
import com.tmser.tr.thesis.dao.ThesisDao;

/**
 * <pre>
 *教学文章评论回调函数
 * </pre>
 *
 * 
 */
@Service
@Lazy(false)
@Transactional
public class ThesisCommentCallback extends AbstractCommentCallback{

	@Autowired
	ThesisDao thesisiDao;
	
	/**
	 * @param resid
	 * @param restype
	 * @see com.tmser.tr.comment.service.CommentCallback#commentSuccessCallback(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void commentSuccessCallback(Integer resid,String content) {
			Thesis thesis = thesisiDao.get(resid);
			if(thesis!=null){
				thesis.setIsComment(1);
				thesisiDao.update(thesis);
			}
	}

	/**
	 * @param restype
	 * @return
	 * @see com.tmser.tr.comment.service.CommentCallback#support(java.lang.Integer)
	 */
	@Override
	public boolean support(Integer restype) {
		return restype==ResTypeConstants.JIAOXUELUNWEN;
	}
}