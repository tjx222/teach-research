/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.common.service;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tmser.tr.common.bo.QueryObject;
import com.tmser.tr.common.listener.Listenable;
import com.tmser.tr.common.listener.ListenableSupport;
import com.tmser.tr.common.page.PageList;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: AbstractService.java, v 1.0 2015年1月31日 下午2:43:08 tmser Exp $
 */
public abstract class AbstractService<E extends QueryObject, K extends Serializable> 
				extends ListenableSupport implements BaseService<E, K>,Listenable {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * @param model
	 * @return
	 * @see com.mainbo.core.service.BaseService#save(java.lang.Object)
	 */
	@Override
	public E save(E model) {
		fireListenableEvent(Listenable.BEFORE_ADD_EVENT, model);
		E e = getDAO().insert(model);
		fireListenableEvent(Listenable.AFTER_ADD_EVENT, model);
		return e;
	}

	/**
	 * @param model
	 * @see com.mainbo.core.service.BaseService#update(java.lang.Object)
	 */
	@Override
	public int update(E model) {
		fireListenableEvent(Listenable.BEFORE_UPDATE_EVENT, model);
		int rs = getDAO().update(model);
		fireListenableEvent(Listenable.AFTER_ADD_EVENT, model);
		return rs;
	}
	/**
	 * @param id
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#findOne(java.io.Serializable)
	 */
	@Override
	public E findOne(K id) {
		if(null == id)
			return null;
		return getDAO().get(id);
	}
	
	@Override
	public E findOne(E model) {
		if(null == model)
			return null;
		return getDAO().getOne(model);
	}

	/**
	 * @param model
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#listPage(java.lang.Object)
	 */
	@Override
	public PageList<E> findByPage(E model) {
		return getDAO().listPage(model);
	}

	/**
	 * @param model
	 * @param limit
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#list(java.lang.Object, int)
	 */
	@Override
	public List<E> find(E model, int limit) {
		return getDAO().list(model,limit);
	}

	/**
	 * @param model
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#listAll(java.lang.Object)
	 */
	@Override
	public List<E> findAll(E model) {
		return getDAO().listAll(model);
	}

	/**
	 * @param id
	 * @see com.tmser.tr.common.service.BaseService#delete(java.io.Serializable)
	 */
	@Override
	public void delete(K id) {
		fireListenableEvent(Listenable.BEFORE_DELETE_EVENT, id);
		getDAO().delete(id);
		fireListenableEvent(Listenable.AFTER_DELETE_EVENT, id);
	}

	/**
	 * @param model
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#count(java.lang.Object)
	 */
	@Override
	public int count(E model) {
		return getDAO().count(model);
	}

	/**
	 * @param id
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#exists(java.io.Serializable)
	 */
	@Override
	public boolean exists(K id) {
		
		return findOne(id) != null;
	}
	
	/**
	 * @return
	 * @see com.mainbo.core.service.Listenable#supportTypes()
	 */
	@Override
	public Object[] supportTypes() {
		if(getDAO() == null){
			return null;
		}
		return new Object[]{getDAO().thisBoClass()};
	}
	
}
