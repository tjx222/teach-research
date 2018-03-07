/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.common.utils;

import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tmser.tr.common.bo.QueryObject;
import com.tmser.tr.manage.config.ConfigUtils;

/**
 * <pre>
 * 前端缓存管理静态工具类
 * </pre>
 *
 * @author tmser
 * @version $Id: CacheUtils.java, v 1.0 2015年10月8日 下午2:14:38 tmser Exp $
 */
public abstract class FrontCacheUtils {

  private static final Logger logger = LoggerFactory
      .getLogger(FrontCacheUtils.class);

  private final static String getFrontRoot() {
    return ConfigUtils.readConfig("front_web_url");
  }

  /**
   * 根据健值清除缓存
   * 
   * @param cacheName
   * @param key
   */
  public final static void delete(String cacheName, Object key) {
    StringBuilder url = null;
    try {
      logger.debug("start delete cache {}", cacheName);
      url = new StringBuilder(getFrontRoot()).append("/jy/ws/monitor/ehcache/")
          .append(cacheName).append("/").append(key).append("/delete");
      Request.Get(url.toString()).connectTimeout(1000).socketTimeout(1000)
          .execute().returnContent();
      logger.info("delete cache success [cachename='{}',key='{}'].", cacheName,
          key);
    } catch (Exception e) {
      logger.error("remove cache failed [url='{}',cachename='{}',key='{}'].",
          url, cacheName, key);
      logger.error("", e);
    }

  }

  /**
   * 根据实体主键清除缓存
   * 
   * @param entity
   * @param key
   */
  public final static void delete(Class<? extends QueryObject> entity,
      Object key) {
    delete(entity.getName(), key);
  }

  /**
   * 清空缓存
   * 
   * @param cacheName
   */
  public final static void clear(String cacheName) {
    StringBuilder url = null;
    try {
      logger.debug("start clear cache {}", cacheName);
      url = new StringBuilder(getFrontRoot()).append("/jy/ws/monitor/ehcache/")
          .append(cacheName).append("/clear");
      Request.Get(url.toString()).connectTimeout(1000).socketTimeout(1000)
          .execute().returnContent();
      logger.info("clear cache success [cachename='{}'].", cacheName);
    } catch (Exception e) {
      logger.error("clear cache failed [url='{}',cachename='{}'].", url,
          cacheName);
      logger.error("", e);
    }

  }

  /**
   * 清空缓存
   * 
   * @param cacheName
   */
  public final static void clear(Class<? extends QueryObject> entity) {
    clear(entity.getName());
  }

}
