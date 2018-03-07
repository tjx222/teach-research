/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.comment.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tmser.tr.comment.bo.Discuss;
import com.tmser.tr.comment.dao.DiscussDao;
import com.tmser.tr.comment.service.CommentCallback;
import com.tmser.tr.comment.service.DiscussService;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.notice.service.bo.MessageNumCacheEntity;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.dao.UserDao;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;
/**
 * 区域教研中各活动的讨论 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author zpp
 * @version $Id: AreaActivityDiscussServiceImpl.java, v 1.0 2015-05-29 zpp Exp $
 */
@Service
@Transactional
public class DiscussServiceImpl extends AbstractCommentService<Discuss> implements DiscussService {
	@Autowired
	private DiscussDao discussDao;
	@Autowired
	private UserDao userDao;
	
	@Resource(name = "cacheManger")
	private CacheManager cacheManager;

	private Cache messageNumCache;

	@PostConstruct
	public void init() {
		messageNumCache = cacheManager.getCache("messageNumCache");
	}
	
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<Discuss, Integer> getDAO() {
		return discussDao;
	}

	/**
	 * @param aad
	 * @return
	 * @see com.tmser.tr.activity.service.AreaActivityDiscussService#saveDiscuss(com.tmser.tr.activity.bo.AreaActivityDiscuss)
	 */
	@Override
	public String saveDiscuss(Discuss aad) {
		List<CommentCallback> callbacks = registedCommentCallbacks();
		CommentCallback curcallback = null;
		for(CommentCallback callback : callbacks){
			if(callback.support(aad.getTypeId())){
				curcallback = callback;
				break;
			}
		}
		if(curcallback != null){
			if(!curcallback.canDiscuss(aad.getActivityId())){
				return "isOver";
			}
			UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); //用户空间
			aad.setCrtId(userSpace.getUserId());
			aad.setSpaceId(userSpace.getId());
			aad.setCrtDttm(new Date());
			discussDao.insert(aad);
			curcallback.discussSuccessCallback(aad);
			
			// 有新的讨论消息发布
			StringBuilder key = new StringBuilder("DIS_T_").append(aad.getTypeId()).append("_R_").append(aad.getActivityId());
			ValueWrapper valueWrapper = messageNumCache.get(key.toString());
			if (valueWrapper != null) {
				MessageNumCacheEntity entry = (MessageNumCacheEntity) valueWrapper.get();
				entry.increaseNotice(1);
			}
		}
		return "ok";
	}

	/**
	 * 查询活动下面的讨论列表
	 * @param aad
	 * @return
	 * @see com.tmser.tr.activity.service.AreaActivityDiscussService#discussIndex(com.tmser.tr.activity.bo.AreaActivityDiscuss)
	 */
	@Override
	public PageList<Discuss> discussIndex(Discuss aad,String searchName) {
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		String sql = "";
		if(!StringUtils.isEmpty(searchName)){
			User u = new User();
			u.setName(SqlMapping.LIKE_PRFIX+searchName+SqlMapping.LIKE_PRFIX);
			u.addCustomCulomn("id");
			List<User> listAll = userDao.listAll(u);
			List<Integer> userIds = new ArrayList<Integer>();
			for(User utemp : listAll){
				userIds.add(utemp.getId());
			}
			if(userIds!=null && userIds.size()>0){
				sql += " and crtId in (:userIds) ";
				paramMap.put("userIds", userIds);
			}else{
				aad.setCrtId(0);
			}
		}
		
		aad.setParentId(0);//第一层数据
		if(!"".equals(sql)){
			aad.addCustomCondition(sql, paramMap);
		}
		aad.addOrder("crtDttm desc");
		PageList<Discuss> listPage = discussDao.listPage(aad);
		for(Discuss radTemp : listPage.getDatalist()){
			Integer id = radTemp.getId();
			Discuss ards = new Discuss();
			ards.setParentId(id);
			ards.addOrder("crtDttm desc");
			List<Discuss> listAll = discussDao.listAll(ards);
			radTemp.setChildList(listAll);
		}
		return listPage;
	}

	/**
	 * @param activeType
	 * @param activeIds
	 * @return
	 * @see com.tmser.tr.comment.service.DiscussService#countDiscussByType(java.lang.Integer, java.util.List)
	 */
	@Override
	public long countDiscussByType(Integer activeType,
			List<Integer> activeIds) {
		Discuss aad = new Discuss();
		aad.setTypeId(activeType);
		aad.buildCondition(" and activityId in (:ids) ").put("ids", activeIds);
		return discussDao.count(aad);//讨论数
	}

	/**
	 * @param activeType
	 * @param activeIds
	 * @return
	 * @see com.tmser.tr.comment.service.DiscussService#countDiscussByUser(java.lang.Integer, java.util.List)
	 */
	@Override
	public long countDiscussByUser(Integer activeType,
			List<Integer> activeIds) {
		Discuss aad = new Discuss();
		aad.setTypeId(activeType);
		aad.buildCondition(" and activityId in (:ids) ").put("ids", activeIds);
		aad.addGroup(" crtId");
		return discussDao.count(aad);//参与人数
	}

	/**
	 * @param discussList
	 * @see com.tmser.tr.comment.service.DiscussService#batchSave(java.util.List)
	 */
	@Override
	public void batchSave(List<Discuss> discussList) {
		discussDao.batchInsert(discussList);
	}
	
	/**
	 * 获取讨论记录
	 * @param id
	 * @return
	 * @see com.tmser.tr.areaactivity.service.ExpertGuidanceService#getDiscussList(java.lang.Integer)
	 */
	@Override
	public PageList<Discuss> getDiscussByActiveId(Discuss discuss) {
		if(discuss != null && discuss.getActivityId() != null && discuss.getTypeId() != null){
			discuss.addOrder(" crtDttm asc");
			PageList<Discuss> discussList = discussDao.listPage(discuss);
			for(Discuss temp : discussList.getDatalist()){
				User user = userDao.get(temp.getCrtId());
				temp.setFlago(user.getName());
			}
			return discussList;
		}else{
			throw new IllegalArgumentException("discuss acticeid and typeid cann't be null");
		}
			
	}

	/**
	 * 通过用户、类型和资源id集合查询出以资源id为key的一个map
	 * @param userId
	 * @param resType
	 * @param ids
	 * @return
	 * @see com.tmser.tr.comment.service.DiscussService#findDiscussMap(java.lang.Integer, java.lang.Integer, java.util.List)
	 */
	@Override
	public Map<Integer, List<Discuss>> findDiscussMap(Integer userId,Integer resType, List<Integer> ids) {
		Discuss discuss = new Discuss();
		discuss.setTypeId(resType);
		discuss.setCrtId(userId);
		discuss.buildCondition(" and activityId in (:ids)").put("ids", ids);
		discuss.addOrder(" activityId desc,crtDttm desc");
		List<Discuss> disList = discussDao.listAll(discuss);
		Map<Integer, List<Discuss>> map = new HashMap<Integer, List<Discuss>>();
		if(!CollectionUtils.isEmpty(disList)){
			List<Discuss> keyDiscuss = new ArrayList<Discuss>();
			Integer activityId = disList.get(0).getActivityId();
			int count = 0;
			for(Discuss dis : disList){
				if(activityId.intValue() == dis.getActivityId().intValue()){
					if(count++ >= 10) continue;
					keyDiscuss.add(dis);
				}else{
					count = 0;
					map.put(activityId, keyDiscuss);
					keyDiscuss = new ArrayList<Discuss>();
					keyDiscuss.add(discuss);
					activityId = dis.getActivityId();
				}
			}
			map.put(activityId, keyDiscuss);
		}
		return map;
	}


}
