/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.classapi.listener;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmser.tr.classapi.bo.ClassUser;
import com.tmser.tr.classapi.bo.ClassVisit;
import com.tmser.tr.classapi.dao.ClassVisitDao;
import com.tmser.tr.classapi.service.ClassOperateService;
import com.tmser.tr.common.listener.ListenableEvent;
import com.tmser.tr.common.listener.Listener;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.service.UserService;

/**
 * <pre>
 *  出版社关系删除监听器
 *  监听出版社关系删除事件，在出版社关系删除后，删除相关的书籍关联信息
 * </pre>
 *
 * @author tmser
 * @version $Id: PublisherDeleteListener.java, v 1.0 2016年10月21日 下午2:47:55 tmser
 *          Exp $
 */
@Component
public class ClassOperateListener implements Listener {

  @Autowired
  private UserService userService;

  @Autowired
  private ClassVisitDao visitDao;

  /**
   * @param event
   * @see com.tmser.tr.common.listener.Listener#lifecycleEvent(com.tmser.tr.common.listener.ListenableEvent)
   */
  @Override
  public void lifecycleEvent(ListenableEvent event) {
    if (ClassOperateService.JOIN_CLASS_EVENT.equals(event.getType())) {
      if (event.getData() instanceof ClassUser) {
        ClassUser classUser = (ClassUser) event.getData();
        if (classUser != null && classUser.getUserId() != null) {
          User user = userService.findOne(classUser.getUserId());
          if (user != null) {
            ClassVisit classVisit = new ClassVisit();
            classVisit.setClassId(classUser.getClassId());
            classVisit.setOrgId(user.getOrgId());
            classVisit.setOrgName(user.getOrgName());
            classVisit.setUserName(user.getName());
            classVisit.setJoinTime(new Date());
            visitDao.insert(classVisit);
          }
        }
      }
    }
  }

  /**
   * @param supportsType
   * @return
   * @see com.tmser.tr.common.listener.Listener#supports(java.lang.Object[])
   */
  @Override
  public boolean supports(Object[] supportsType) {
    for (Object o : supportsType) {
      if (o instanceof Class) {
        return ClassOperateService.class.equals(o);
      }
    }
    return false;
  }

}
