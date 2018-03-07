/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.notice.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tmser.tr.common.dao.AbstractDAO;
import com.tmser.tr.notice.bo.JyLetter;
import com.tmser.tr.notice.dao.JyLetterDao;

/**
 * 站内信 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: JyLetter.java, v 1.0 2015-05-12 Generate Tools Exp $
 */
@Repository
public class JyLetterDaoImpl extends AbstractDAO<JyLetter,Long> implements JyLetterDao {

	@Override
	public int updateToRead(List<Long> letterIds) {
		String letterStr = letterIds.toString().substring(1,
				letterIds.toString().length() - 1);
		return this.update("update jyLetter set receiverState=1 where receiverState=0 and receiverId in (?)", letterStr);
	}

}
