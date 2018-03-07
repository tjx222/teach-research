/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.jxtx.listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmser.tr.common.listener.Listenable;
import com.tmser.tr.common.listener.ListenableEvent;
import com.tmser.tr.common.listener.Listener;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.BookSync;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.meta.bo.PublishRelationship;
import com.tmser.tr.manage.meta.service.BookService;

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
public class PublisherDeleteListener implements Listener {

  @Autowired
  private BookService bookService;

  /**
   * @param event
   * @see com.tmser.tr.common.listener.Listener#lifecycleEvent(com.tmser.tr.common.listener.ListenableEvent)
   */
  @Override
  public void lifecycleEvent(ListenableEvent event) {
    if (Listenable.BEFORE_DELETE_EVENT.equals(event.getType())) {
      if (event.getData() instanceof PublishRelationship) {
        PublishRelationship pr = (PublishRelationship) event.getData();
        if (pr != null) {
          BookSync bmodel = new BookSync();
          bmodel.setPublisherId(pr.getEid());
          bmodel.setSubjectId(pr.getSubjectId());
          MetaRelationship mr = MetaUtils.getMetaRelation(pr.getPhaseId());
          if (mr == null) {
            return;
          }
          bmodel.setPhaseId(mr.getEid());
          List<BookSync> bs = bookService.findBookSync(bmodel);
          for (BookSync b : bs) {
            bookService.deleteBookSyncById(b.getId());
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
        return PublishRelationship.class.equals(o);
      }
    }
    return false;
  }

}
