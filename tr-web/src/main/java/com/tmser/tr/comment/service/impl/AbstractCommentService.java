package com.tmser.tr.comment.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tmser.tr.comment.service.CommentCallback;
import com.tmser.tr.comment.service.CommentCallbackAble;
import com.tmser.tr.common.bo.QueryObject;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.utils.SpringContextHolder;

/**
 * 
 * <pre>
 * 评论回调抽象实现类
 * </pre>
 *
 * @author tmser
 * @version $Id: AbstractCommentService.java, v 1.0 2016年11月4日 上午9:46:27 tmser
 *          Exp $
 */
public abstract class AbstractCommentService<T extends QueryObject> extends AbstractService<T, Integer>
    implements CommentCallbackAble {

  private static volatile List<CommentCallback> registedCallbacks;

  /**
   * 获取注册的评论回调器
   * 
   * @return
   */
  @Override
  public List<CommentCallback> registedCommentCallbacks() {
    if (registedCallbacks == null) {
      registedCallbacks = Collections.unmodifiableList(SpringContextHolder.getBeansForType(CommentCallback.class));
    }
    return registedCallbacks;
  }

  /**
   * 注册评论回调
   */
  @Override
  public void registCommentCallback(CommentCallback callback) {
    List<CommentCallback> callbacks = new ArrayList<>();
    if (registedCallbacks != null) {
      registedCallbacks.addAll(registedCallbacks);
    }
    callbacks.add(callback);
    registedCallbacks = Collections.unmodifiableList(callbacks);
  }

}
