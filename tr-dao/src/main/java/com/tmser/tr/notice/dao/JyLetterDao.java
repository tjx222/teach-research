/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.notice.dao;

import java.util.List;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.notice.bo.JyLetter;

 /**
 * 站内信 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: JyLetter.java, v 1.0 2015-05-12 Generate Tools Exp $
 */
public interface JyLetterDao extends BaseDAO<JyLetter, Long>{
	
	/**
	 * 批量更新站内信为已阅读
	 * @param letterIds
	 * @return
	 */
	int updateToRead(List<Long> letterIds);

}