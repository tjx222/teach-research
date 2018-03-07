/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.dao.impl;

import org.springframework.stereotype.Repository;

import com.tmser.tr.common.dao.AbstractDAO;
import com.tmser.tr.common.dao.annotation.UseCache;
import com.tmser.tr.uc.bo.Login;
import com.tmser.tr.uc.dao.LoginDao;

/**
 * 用户登录信息 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Login.java, v 1.0 2015-01-30 Generate Tools Exp $
 */
@Repository
@UseCache
public class LoginDaoImpl extends AbstractDAO<Login,Integer> implements LoginDao {

}
