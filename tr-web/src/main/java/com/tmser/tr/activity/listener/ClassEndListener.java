/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmser.tr.activity.bo.SchoolActivity;
import com.tmser.tr.activity.service.SchoolActivityService;
import com.tmser.tr.classapi.service.ClassOperateService;
import com.tmser.tr.common.listener.ListenableEvent;
import com.tmser.tr.common.listener.Listener;

/**
 * 
 * <pre>
 * 课堂结束
 * </pre>
 *
 * @author ljh
 * @version $Id: ClassEndListener.java, v 1.0 2017年12月26日 上午11:56:51 ljh Exp $
 */
@Component
public class ClassEndListener implements Listener {

  @Autowired
  private SchoolActivityService schoolActivityService;

  /**
   * @param event
   * @see com.tmser.tr.common.listener.Listener#lifecycleEvent(com.tmser.tr.common.listener.ListenableEvent)
   */
  @Override
  public void lifecycleEvent(ListenableEvent event) {
    if (ClassOperateService.END_CLASS_EVENT.equals(event.getType())) {
      SchoolActivity sa = new SchoolActivity();
      sa.setClassId((String) event.getData());
      SchoolActivity old_sa = schoolActivityService.findOne(sa);
      if (old_sa != null) {
        old_sa.setIsOver(true);
        schoolActivityService.update(old_sa);
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
