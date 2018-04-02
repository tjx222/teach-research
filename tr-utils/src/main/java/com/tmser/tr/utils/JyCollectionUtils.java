/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import com.tmser.tr.utils.exception.BaseException;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: JyCollectionUtils.java, v 1.0 Apr 10, 2015 11:45:02 AM tmser
 *          Exp $
 */
public class JyCollectionUtils {
  private static final Logger logger = LoggerFactory.getLogger(JyCollectionUtils.class);

  /**
   * 获取集合元素中指定属性的集合,不重复，不包含null元素
   * 
   * @param list
   * @param field
   * @return
   */
  public static <E, T> List<T> getValues(List<E> list, String field) {
    Set<T> set = new HashSet<T>();
    Method m = null;
    String getFieldMethodName = "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
    if (CollectionUtils.isEmpty(list)) {
      return new ArrayList<>();
    }
    // 遍历list获取元素属性
    for (E elem : list) {
      if (m == null) {
        Class<?> clazz = elem.getClass();
        try {
          m = clazz.getMethod(getFieldMethodName, null);
        } catch (Exception e) {
          logger.error("获取属性异常！", e);
          throw new BaseException("获取属性异常！");
        }
      }
      try {
        set.add((T) m.invoke(elem, null));
      } catch (Exception e) {
        logger.error("获取属性值失败！", e);
        throw new BaseException("获取属性值失败！");
      }
    }
    return new ArrayList<>(set);

  }

  /**
   * 获取map的value list
   * 
   * @param map
   * @return
   */
  public static <T> List<T> getMapValue(Map<? extends Object, T> map) {
    List<T> result = new ArrayList<T>();
    if (CollectionUtils.isEmpty(map)) {
      return result;
    }
    // 遍历新增元素
    for (Object key : map.keySet()) {
      result.add(map.get(key));
    }
    return result;
  }

  /**
   * 转换对象
   * 
   * @param datalist
   * @param class1
   * @return
   */
  public static <T, E> List<T> convertList(List<E> datalist, Class<T> clazz) {
    List<T> result = new ArrayList<T>();
    if (CollectionUtils.isEmpty(datalist)) {
      return result;
    }
    for (E item : datalist) {
      T t;
      try {
        t = clazz.newInstance();
        BeanUtils.copyProperties(item, t);
        result.add(t);
      } catch (Exception e) {
        logger.error("", e);
      }

    }
    return result;
  }

}
