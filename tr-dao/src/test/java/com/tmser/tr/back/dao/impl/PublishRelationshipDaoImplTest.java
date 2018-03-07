/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.dao.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.manage.meta.dao.PublishRelationshipDao;
import com.mainbo.test.LogBackJUnit4ClassRunner;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 3020mt
 * @version $Id: PublishRelationshipDaoImplTest.java, v 1.0 2016年7月26日 下午2:28:47 3020mt Exp $
 */
@RunWith(LogBackJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/config/spring/*.xml")
public class PublishRelationshipDaoImplTest {
	
	@Autowired
	private PublishRelationshipDao publishRelationshipDao;

	/**
	 * Test method for {@link com.tmser.tr.common.dao.AbstractDAO#delete(java.io.Serializable)}.
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testDelete() {
		Assert.assertNotNull(publishRelationshipDao.get(37));
		//fail("Not yet implemented");
	}

}
