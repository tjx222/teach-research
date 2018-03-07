/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.comment.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.comment.bo.CommentInfo;
import com.tmser.tr.comment.dao.CommentInfoDao;
import com.tmser.tr.comment.service.CommentInfoService;
import com.tmser.tr.comment.service.CommentCallback;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.notice.constants.NoticeType;
import com.tmser.tr.notice.service.impl.NoticeUtils;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.utils.SessionKey;
/**
 * 评论信息 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: CommentInfo.java, v 1.0 2015-03-20 Generate Tools Exp $
 */
@Service
@Transactional
public class CommentInfoServiceImpl extends AbstractCommentService<CommentInfo> implements CommentInfoService {
	
	@Autowired
	private CommentInfoDao commentInfoDao;
	
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<CommentInfo, Integer> getDAO() {
		return commentInfoDao;
	}

	@Override
	public void saveComment(CommentInfo commentInfo) {
		this.save(commentInfo);//无论实现保存评论还是保存改评论的回复都实现回调
		notifyChecked(commentInfo.getResId(), commentInfo.getResType(),commentInfo.getContent());//保存成功之后通知回调函数
		User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		String title=null;
		if(commentInfo.getParentId()!=null){
			title= user.getName()+"回复了您的"+commentInfo.getTitle()+ResTypeConstants.resNameMap.get(commentInfo.getResType());
		}else{
			title= user.getName()+"评论了您的"+commentInfo.getTitle()+ResTypeConstants.resNameMap.get(commentInfo.getResType());
		}
		Map<String,Object> noticeInfo = new HashMap<String, Object>();
		noticeInfo.put("resId", commentInfo.getResId());
		noticeInfo.put("resType", commentInfo.getResType());
		noticeInfo.put("title", commentInfo.getTitle());
		noticeInfo.put("senderTime", new Date());
		noticeInfo.put("content",commentInfo.getContent());
		noticeInfo.put("typeName", ResTypeConstants.resNameMap.get(commentInfo.getResType()));
		noticeInfo.put("user", user);
		NoticeUtils.sendNotice(NoticeType.REVIEW, title, user.getId(), commentInfo.getAuthorId(), noticeInfo);
	}
	
	/**
	 * 通知更新查阅状态
	 * @param resid
	 * @param restype
	 */
	private void notifyChecked(Integer resid,Integer restype,String content)
	{
		List<CommentCallback> ccbs = registedCommentCallbacks();
		for(CommentCallback ccb : ccbs){
			if(ccb.support(restype)){
				ccb.commentSuccessCallback(resid, content);
			}
		}
	}
}
