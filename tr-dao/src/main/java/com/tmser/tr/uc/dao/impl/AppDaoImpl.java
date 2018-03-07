/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.dao.impl;

import org.springframework.stereotype.Repository;

import com.tmser.tr.common.dao.AbstractDAO;
import com.tmser.tr.common.dao.annotation.UseCache;
import com.tmser.tr.uc.bo.App;
import com.tmser.tr.uc.dao.AppDao;

/**
 * 应用信息 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: App.java, v 1.0 2016-01-11 Generate Tools Exp $
 */
@Repository
@UseCache
public class AppDaoImpl extends AbstractDAO<App,Integer> implements AppDao {

}
