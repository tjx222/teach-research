/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.tmser.tr.activity.bo.ActivityTracks;
import com.tmser.tr.activity.service.ActivityCoordinateControlService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * <pre>
 * 活动的整理教案协同控制Service
 * </pre>
 *
 * @author wangdawei
 * @version $Id: ActivityCoordinateControlServiceImpl.java, v 1.0 2015年7月15日
 *          下午5:24:12 wangdawei Exp $
 */
@Service
public class ActivityCoordinateControlServiceImpl implements ActivityCoordinateControlService {

  @Resource(name = "cacheManger")
  private CacheManager cacheManager;

  private Cache resZhengliPowerCache;

  @PostConstruct
  public void init() {
    resZhengliPowerCache = cacheManager.getCache("resZhengliPowerCache");
  }

  /**
   * 获取主备教案资源的整理的控制权
   * 
   * @param activityId
   * @param userId
   * @return
   * @see com.tmser.tr.activity.service.ActivityCoordinateControlService#getPowerOfZhengli_activity(java.lang.Integer,
   *      java.lang.Integer)
   */
  @Override
  public boolean getPowerOfZhengli(String resId) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    if (resId != null && !"".equals(resId)) {
      ValueWrapper element = resZhengliPowerCache.get(resId);
      if (element == null) { // 没有则新增
        resZhengliPowerCache.put(resId, userSpace.getUserId());
        return true;
      } else {
        Integer userId = (Integer) element.get();
        if (userId.intValue() == userSpace.getUserId().intValue()) { // 控制权指向的是自己
          resZhengliPowerCache.put(resId, userId);
          return true;
        } else {
          return false;
        }
      }
    }
    return false;
  }

  /**
   * 判断活动的主备教案或整理教案是否正在被整理中
   * 
   * @param zhubeiList
   * @param zhengliList
   * @return
   * @see com.tmser.tr.activity.service.ActivityCoordinateControlService#mainUserIsZhengliing(java.util.List,
   *      java.util.List)
   */
  @Override
  public <T> boolean mainUserIsZhengliing(List<T> zhubeiList, List<T> zhengliList) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    ValueWrapper element = null;
    for (Object obj : zhubeiList) {
      if (obj instanceof ActivityTracks) {
        element = resZhengliPowerCache.get(((ActivityTracks) obj).getResId());
      }
      if (element != null) {
        Integer userId = (Integer) element.get();
        if (userId.intValue() != userSpace.getUserId().intValue()) {
          return true;
        }
      }
    }
    for (Object obj : zhengliList) {
      if (obj instanceof ActivityTracks) {
        element = resZhengliPowerCache.get(((ActivityTracks) obj).getResId());
      }
      if (element != null) {
        Integer userId = (Integer) element.get();
        if (userId.intValue() != userSpace.getUserId().intValue()) {
          return true;
        }
      }
    }
    return false;
  }

}
