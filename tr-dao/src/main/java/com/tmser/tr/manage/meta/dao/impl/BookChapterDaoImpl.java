/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.meta.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tmser.tr.common.dao.AbstractDAO;
import com.tmser.tr.common.orm.AbstractMapper;
import com.tmser.tr.manage.meta.bo.BookChapter;
import com.tmser.tr.manage.meta.dao.BookChapterDao;

/**
 * 书籍的章节目录 Dao 实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: CommdityBookChapter.java, v 1.0 2015-02-04 Generate Tools Exp $
 */
@Repository
public class BookChapterDaoImpl extends AbstractDAO<BookChapter,String> implements BookChapterDao {

	@Override
	public List<BookChapter> listBookChapterWithChildState(String comId,
			String parentId) {
		String sql = "select c.chapterId,c.chapterName ,(select count(*) from BookChapter cb where cb.comId=:comId and cb.parentId=c.chapterId) chids"
				+" from BookChapter c where c.comId=:comId and c.parentId=:parentId";
		
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("comId", comId);
		args.put("parentId", parentId);
		return super.queryByNamedSql(sql, args, new AbstractMapper<BookChapter>() {
			@Override
			public BookChapter map(ResultSet rs, int rowNum) throws SQLException {
				BookChapter bc= BookChapterDaoImpl.this.mapper.mapRow(rs, rowNum);
				bc.setFlago(String.valueOf(rs.getInt("chids") > 0));
				return bc;
			}
		});
	}
}
