/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.check.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.check.bo.CheckInfo;
import com.tmser.tr.check.bo.CheckOpinion;
import com.tmser.tr.check.dao.CheckInfoDao;
import com.tmser.tr.check.dao.CheckOpinionDao;
import com.tmser.tr.check.service.CheckInfoService;
import com.tmser.tr.check.service.CheckOpinionService;
import com.tmser.tr.check.service.CheckedCallback;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.notice.constants.NoticeType;
import com.tmser.tr.notice.service.impl.NoticeUtils;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.UserService;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;
/**
 * 查阅意见 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: CheckOpinion.java, v 1.0 2015-03-14 Generate Tools Exp $
 */
@Service
@Transactional
public class CheckOpinionServiceImpl extends AbstractService<CheckOpinion, Integer> implements CheckOpinionService {

	@Autowired
	private CheckOpinionDao checkOpinionDao;
	
	@Autowired
	private CheckInfoDao checkInfoDao;
	
	@Autowired
	private UserService userService;
	
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<CheckOpinion, Integer> getDAO() {
		return checkOpinionDao;
	}

	/**
	 * 保存查阅意见，并通知相关部件
	 */
	@Override
	public boolean saveCheckbo(CheckInfo ck) {
		    UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); //用户空间
		    Integer schoolYear = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		    boolean needNotice = true;
		    boolean hasOpinion = true;
		    Integer	term = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);//学期
			CheckInfo checkinfo = new CheckInfo();
			checkinfo.setUserId(userSpace.getUserId());
			String content = "";
			if(ck!=null){
				content = ck.getFlago();
				checkinfo.setSchoolYear(schoolYear);
				checkinfo.setResId(ck.getResId());
				checkinfo.setResType(ck.getResType());
			}
			List<CheckInfo> rs = checkInfoDao.list(checkinfo, 1);
			if(rs !=null && rs.size() > 0){
				checkinfo =  rs.get(0);
				if(checkinfo!=null){
					hasOpinion = StringUtils.isNotEmpty(content);
					boolean needUpdate = false;
					CheckInfo ci = new CheckInfo();
					ci.setId(checkinfo.getId());
					if((checkinfo.getHasOptinion() ==  false && hasOpinion)){
						needUpdate = true;
						ci.setHasOptinion(true);
						checkinfo.setHasOptinion(true);
					}
					if(ck != null && ck.getLevel() != null){
						if(!ck.getLevel().equals(checkinfo.getLevel())){
							needUpdate = true;
							ci.setLevel(ck.getLevel());
						}
					}
					if(needUpdate){
						checkInfoDao.update(ci);
					}else{
						needNotice = false;
					}
				}
			}else{
				checkinfo.setUsername(userSpace.getUsername());
				checkinfo.setSpaceId(userSpace.getId());
				checkinfo.setAuthor(userService.findOne(ck.getAuthorId()).getName());
				checkinfo.setIsUpdate(false);
				checkinfo.setCreatetime(new Date());
				checkinfo.setHasOptinion(!(StringUtils.isEmpty(content)));
				checkinfo.setAuthorId(ck.getAuthorId());
				checkinfo.setGradeId(ck.getGradeId());
				checkinfo.setSubjectId(ck.getSubjectId());
				checkinfo.setTitle(ck.getTitle());
				checkinfo.setTerm(term);
				checkinfo.setLevel(ck.getLevel());
				checkinfo.setPhase(userSpace.getPhaseId());
				checkinfo =  checkInfoDao.insert(checkinfo);
			}
			
			if(hasOpinion){
				CheckOpinion co = new CheckOpinion();
				co.setAuthorId(ck.getAuthorId());
				co.setCheckInfoId(checkinfo.getId());
				
				co.setContent(StringUtils.isEmpty(content) ? "已查阅":content);
				co.setParentId(0);
				co.setResId(ck.getResId());
				co.setResType(ck.getResType());
				co.setUserId(userSpace.getUserId());
				co.setUsername(userSpace.getUsername());
				co.setType(CheckOpinion.TYPE_CHECK);
				co.setIsDelete(false);
				co.setIsHidden(false);
				co.setSpaceId(userSpace.getId());
				co.setOpinionId(0);
				co.setCrtTime(new Date());
				save(co);
				if(needNotice){
					notifyChecked(co.getResId(),co.getResType(),co.getContent());
					String title=userSpace.getUsername()+"查阅了您的"+ck.getTitle()+ResTypeConstants.resNameMap.get(ck.getResType());
					User user=(User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
					Map<String,Object> noticeInfo = new HashMap<String, Object>();
					noticeInfo.put("resId", ck.getResId());
					noticeInfo.put("resType", ck.getResType());
					noticeInfo.put("title", ck.getTitle());
					noticeInfo.put("senderTime", new Date());
					noticeInfo.put("content", co.getContent());
					noticeInfo.put("typeName", ResTypeConstants.resNameMap.get(ck.getResType()));
					noticeInfo.put("user", user);
					NoticeUtils.sendNotice(NoticeType.CHECK, title, user.getId(), ck.getAuthorId(), noticeInfo);
				}
			}
			
		return true;
	}
	
	
	/**
	 * 保存查阅意见，并通知相关部件
	 */
	@Override
	public CheckOpinion saveCheckReply(CheckOpinion model) {
		    UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); //用户空间
		    CheckOpinion parent = checkOpinionDao.get(model.getParentId());
		    CheckOpinion co = null;
		    if(parent !=null){
				co = new CheckOpinion();
				co.setAuthorId(parent.getUserId());
				co.setCheckInfoId(parent.getCheckInfoId());
				co.setContent(model.getContent());
				co.setParentId(model.getParentId());
				co.setResId(parent.getResId());
				co.setResType(parent.getResType());
				co.setUserId(userSpace.getUserId());
				co.setUsername(userSpace.getUsername());
				co.setSpaceId(userSpace.getId());
				co.setType(CheckOpinion.TYPE_REPLY);
				co.setIsDelete(false);
				co.setIsHidden(false);
				co.setOpinionId(model.getOpinionId());
				co.setCrtTime(new Date());
				save(co);
			}
			
		return  co;
	}
	
	
	/**
	 * 通知更新查阅状态
	 * @param resid
	 * @param restype
	 */
	private void notifyChecked(Integer resid,Integer restype,String content)
	{
		boolean needcheck = false;
		for(CheckedCallback ccb : CheckInfoService.callbackList){
			needcheck = ccb.support(restype);
			if( needcheck){
				ccb.checkSuccessCallback(resid, restype,content);
			}
		}
	}


}
